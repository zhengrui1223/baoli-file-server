package service;

import com.baoli.SpringBootRunApplication;
import com.baoli.model.StockEntity;
import com.baoli.service.StockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-05-29 14:20
 ************************************************************/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRunApplication.class)
public class StockServiceTest {
    @Autowired
    private StockService stockService;

    private static final int REQUEST_COUNT = 5000;

    private static final String GOODS_NAME = "xiao mi 8";

    private static final Integer GOODS_COUNT = 200;

    private static final Integer BUY_COUNT = 1;

    private CountDownLatch downLatch = new CountDownLatch(REQUEST_COUNT);

    private CountDownLatch downLatch2 = new CountDownLatch(REQUEST_COUNT);

    @Before
    public void init() {
        StockEntity entity = new StockEntity();
        entity.setId(1);
        entity.setVersion(0);
        entity.setGoodsName(GOODS_NAME);
        entity.setGoodsCount(GOODS_COUNT);
        stockService.update(entity);
    }

    @Test
    public void secKillTest() throws InterruptedException {

        StopWatch watch = new StopWatch();
        watch.start();

        for (int i = 0; i < REQUEST_COUNT; i++) {
            new Thread(new UserRequest(GOODS_NAME, BUY_COUNT)).start();
            downLatch.countDown();
        }

        downLatch2.await();

        watch.stop();

        System.out.println(REQUEST_COUNT + "个用户并发秒杀" + GOODS_COUNT + "件商品花费时间" + watch.getTotalTimeMillis() / new Double(1000) + "秒");
    }

    private class UserRequest implements Runnable {
        private String goodsName;

        private Integer buyCount;

        public UserRequest(String goodsName, Integer buyCount) {
            this.goodsName = goodsName;
            this.buyCount = buyCount;
        }

        @Override
        public void run() {
            try {
                downLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stockService.secKillWay1(goodsName, buyCount);

            downLatch2.countDown();
        }
    }
}

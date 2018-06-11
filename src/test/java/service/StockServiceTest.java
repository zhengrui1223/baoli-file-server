package service;

import com.baoli.SpringBootRunApplication;
import com.baoli.component.redis.RedisCacheManager;
import com.baoli.model.StockEntity;
import com.baoli.service.StockService;
import com.baoli.util.Context;
import com.baoli.util.RedisUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private static final int REQUEST_COUNT = 1000;

    private static final String GOODS_NAME = "xiao mi 8";

    private static final Integer GOODS_COUNT = 50;

    private static final Integer BUY_COUNT = 1;

    private CountDownLatch downLatch = new CountDownLatch(REQUEST_COUNT);

    private CountDownLatch downLatch2 = new CountDownLatch(REQUEST_COUNT);

    private ExecutorService executorService = Executors.newFixedThreadPool(REQUEST_COUNT);

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Before
    public void init() {
        StockEntity entity = new StockEntity();
        entity.setId(1);
        entity.setVersion(0);
        entity.setGoodsName(GOODS_NAME);
        entity.setGoodsCount(GOODS_COUNT);
        stockService.update(entity);

        redisCacheManager.set(Context.GOODS_NAME, String.valueOf(GOODS_COUNT));

        Jedis jedis = RedisUtil.getInstance().getJedis();
        jedis.set(Context.GOODS_NAME, String.valueOf(GOODS_COUNT));
    }

    @Test
    public void secKillTest() throws InterruptedException {

        StopWatch watch = new StopWatch();
        watch.start();

        /*for (int i = 0; i < REQUEST_COUNT; i++) {
            new Thread(new UserRequest(GOODS_NAME, BUY_COUNT)).start();
            downLatch.countDown();
        }*/

        for (int i = 0; i < REQUEST_COUNT; i++) {
            executorService.execute(new UserRequest(GOODS_NAME, BUY_COUNT));
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
            //stockService.secKillByDBWay1(goodsName, buyCount);
            //stockService.secKillByRedis(goodsName, buyCount);
            stockService.secKillByRedis2(goodsName, buyCount);

            downLatch2.countDown();
        }
    }
}

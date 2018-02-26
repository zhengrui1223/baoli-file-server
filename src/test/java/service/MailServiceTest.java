package service;

import com.baoli.SpringBootRunApplication;
import com.baoli.component.mail.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/*******************************************
 * @Description: 测试mail工具类
 * @Author: jerry.zheng
 * @Date Created in 16:06 2018\2\26 0026        
 *******************************************/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootRunApplication.class)
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMessage("jerry.zheng@movit-tech.com", "测试主题", "测试内容");
    }

    @Test
    public void sendTemplateMail() {
        File file = new File("D:/test.jpg");
        Map<String, File> map = new HashMap<>();
        map.put("test.jpg", file);

        Map<String, Object> params = new HashMap<>();
        params.put("message", "hello world");
        mailService.sendTemplateMail("jerry.zheng@movit-tech.com", "测试主题", params , "mail/mailTemplate", map);
    }

}

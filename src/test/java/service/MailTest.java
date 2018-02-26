package service;

import com.baoli.SpringBootRunApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/*******************************************
 * @Description: java mail使用
 * @Author: jerry.zheng
 * @Date Created in 16:50 2018\2\24 0024        
 *******************************************/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootRunApplication.class)
public class MailTest {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zhengrui_java@163.com");
        message.setTo("jerry.zheng@movit-tech.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        mailSender.send(message);
    }

    @Test
    public void sendAttachment() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("zhengrui_java@163.com");
        helper.setTo("jerry.zheng@movit-tech.com");
        helper.setSubject("主题：简单邮件");
        helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);

        // let's include the infamous windows Sample file (this time copied to c:/)
        FileSystemResource res = new FileSystemResource(new File("d:/test.jpg"));
        helper.addAttachment("test.jpg", res);
        mailSender.send(mimeMessage);
    }

    @Test
    public void sendTemplateMail() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("zhengrui_java@163.com");
        helper.setTo("jerry.zheng@movit-tech.com");
        helper.setSubject("主题：模板邮件");

        Context context = new Context();
        context.setVariable("message", "hello mail!");

        String text = templateEngine.process("mail/mailTemplate", context);
        helper.setText(text, true);

        mailSender.send(mimeMessage);
    }


}

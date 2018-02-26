package com.baoli.component.mail;

import com.baoli.util.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/*******************************************
 * @Description: 基于spring boot的邮件发送工具类
 * @Author: jerry.zheng
 * @Date Created in 16:02 2018\2\26 0026        
 *******************************************/

@Component
public class MailService {

    private Log log = LogFactory.getLog(getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Config config;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendSimpleMessage(String to, String subject, String mailMsg) {
        this.sendSimpleMessage(new String[]{to}, null, subject, mailMsg);
    }

    public void sendSimpleMessage(String to, String cc, String subject, String mailMsg) {
        this.sendSimpleMessage(new String[]{to}, new String[]{cc}, subject, mailMsg);
    }

    public void sendSimpleMessage(String[] to, String subject, String mailMsg) {
        this.sendSimpleMessage(to, null, subject, mailMsg);
    }

    /**
     * 发送简单邮件
     * @param to
     * @param cc
     * @param subject
     * @param content
     */
    public void sendSimpleMessage(String[] to, String[] cc, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(config.getMailUserName());
        msg.setTo(to);
        msg.setCc(cc);
        msg.setSubject(subject);
        msg.setText(content);
        this.mailSender.send(msg);
    }

    public void sendAttachmentsMail(String to, String title, String content, Map<String, File> attachments) {
        this.sendAttachmentsMail(new String[]{to}, null, title, content, attachments);
    }

    public void sendAttachmentsMail(String to, String cc, String title, String content, Map<String, File> attachments) {
        this.sendAttachmentsMail(new String[]{to}, new String[]{cc}, title, content, attachments);
    }

    public void sendAttachmentsMail(String[] to, String title, String content, Map<String, File> attachments) {
        this.sendAttachmentsMail(to, null, title, content, attachments);
    }

    /**
     * 带附件的邮件发送
     * @param to
     * @param cc
     * @param title
     * @param content
     * @param attachments
     */
    public void sendAttachmentsMail(String[] to, String[] cc, String title, String content, Map<String, File> attachments) {

        if (to == null) {
            throw new RuntimeException("Send Mail User can not be null.");
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(config.getMailUserName());
            helper.setTo(to);

            if (cc != null && cc.length > 0) {
                helper.setCc(cc);
            }

            helper.setSubject(title);
            helper.setText(content);

            if (attachments != null && !attachments.isEmpty()) {
                for (Map.Entry<String, File> entry : attachments.entrySet()) {
                    helper.addAttachment(entry.getKey(), new FileSystemResource(entry.getValue()));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        this.mailSender.send(mimeMessage);
    }

    public void sendTemplateMail(String to, String title, Map<String, Object> params, String templateName, Map<String, File> attachments) {
        this.sendTemplateMail(new String[]{to}, null, title, params, templateName, attachments);
    }

    public void sendTemplateMail(String to, String cc, String title, Map<String, Object> params, String templateName, Map<String, File> attachments) {
        this.sendTemplateMail(new String[]{to}, new String[]{cc}, title, params, templateName, attachments);
    }

    public void sendTemplateMail(String[] to, String title, Map<String, Object> params, String templateName, Map<String, File> attachments) {
        this.sendTemplateMail(to, null, title, params, templateName, attachments);
    }

    /**
     * 带附件且读取模板的邮件发送
     * @param to 收件人
     * @param cc 抄送人
     * @param title 邮件主题
     * @param params 模板占位符
     * @param templateName 模板名称
     * @param attachments 附件
     */
    public void sendTemplateMail(String[] to, String[] cc, String title, Map<String, Object> params, String templateName, Map<String, File> attachments) {
        Context context = new Context();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> key : params.entrySet()) {
                context.setVariable(key.getKey(), key.getValue());
            }
        }
        String content = templateEngine.process(templateName, context);
        this.sendAttachmentsMail(to, cc, title, content, attachments);
    }

}

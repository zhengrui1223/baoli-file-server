package com.baoli.configuration;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/*******************************************
 * @Description: TODO
 * @Author: jerry.zheng
 * @Date Created in 17:21 2018\2\27 0027        
 *******************************************/

@Configuration
public class CustomServletContainer implements EmbeddedServletContainerCustomizer {
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        //设置系统默认错误页面
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/e/404"));
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/e/500"));
        container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST,"/e/400"));

        //设置端口
        //container.setPort("8080");

        //设置项目访问路径
        //container.setContextPath("/baoli");

        //设置session失效时间
        //container.setSessionTimeout(30);

        //...
    }
}

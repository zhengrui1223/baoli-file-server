package com.baoli.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/*******************************************
 * @Description: TODO
 * @Author: jerry.zheng
 * @Date Created in 17:35 2018\2\27 0027        
 *******************************************/

@Configuration
public class ListenerConfig {
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
}

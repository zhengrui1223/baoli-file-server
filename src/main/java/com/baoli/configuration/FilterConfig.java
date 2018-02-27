package com.baoli.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.HashMap;
import java.util.Map;

/*******************************************
 * @Description: TODO
 * @Author: jerry.zheng
 * @Date Created in 17:28 2018\2\27 0027        
 *******************************************/

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        DelegatingFilterProxy httpBasicFilter = new DelegatingFilterProxy();
        registration.setFilter(httpBasicFilter);

        Map<String,String> m = new HashMap<>();
        m.put("targetBeanName","securityFilter");
        m.put("targetFilterLifecycle","true");
        registration.setInitParameters(m);

        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}

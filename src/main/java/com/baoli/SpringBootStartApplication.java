package com.baoli;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-10-18 13:25
 ************************************************************/
public class SpringBootStartApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootRunApplication.class);
    }
}

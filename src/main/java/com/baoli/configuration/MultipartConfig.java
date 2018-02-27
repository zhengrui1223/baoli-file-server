package com.baoli.configuration;

import com.baoli.util.CustomMultipartResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;

/*******************************************
 * @Description: TODO
 * @Author: jerry.zheng
 * @Date Created in 17:28 2018\2\27 0027        
 *******************************************/

@Configuration
public class MultipartConfig {
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        CustomMultipartResolver resolver = new CustomMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setMaxInMemorySize(40960);
        //resolver.setMaxUploadSize(50*1024*1024);
        return resolver;
    }
}

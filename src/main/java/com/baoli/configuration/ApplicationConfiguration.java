package com.baoli.configuration;

import com.baoli.util.CustomMultipartResolver;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.Map;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-25 9:20
 ************************************************************/

@Configuration
public class ApplicationConfiguration extends WebMvcConfigurerAdapter implements EmbeddedServletContainerCustomizer{

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

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/e/404"));
        configurableEmbeddedServletContainer.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/e/500"));
        configurableEmbeddedServletContainer.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST,"/e/400"));
    }

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

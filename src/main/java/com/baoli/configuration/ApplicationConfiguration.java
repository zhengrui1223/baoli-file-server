package com.baoli.configuration;

import com.baoli.filter.SecurityFilter;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-25 9:20
 ************************************************************/

@Configuration
public class ApplicationConfiguration extends WebMvcConfigurerAdapter implements EmbeddedServletContainerCustomizer{
    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SecurityFilter());
        registration.addUrlPatterns("/*");
        registration.setName("securityFilter");
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

    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static*//** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/templates/fragment*//**").addResourceLocations("classpath:/templates/fragment*//**");
    }*/
}

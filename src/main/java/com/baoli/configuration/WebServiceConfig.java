package com.baoli.configuration;

import com.baoli.interceptor.CXFAuthInterceptor1;
import com.baoli.interceptor.CXFAuthInterceptor2;
import com.baoli.webservice.service.TestWebService;
import com.baoli.webservice.service.TestWebService2;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/*******************************************
 * @Description: TODO
 * @Author: jerry.zheng
 * @Date Created in 9:46 2018\2\28 0028        
 *******************************************/

@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/services/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }


    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new TestWebService());
        endpoint.publish("/test1");
        endpoint.getInInterceptors().add(new CXFAuthInterceptor1());    //用户认证, soap header
        return endpoint;
    }

    @Bean
    public Endpoint endpoint2() {
        EndpointImpl endpoint = new EndpointImpl(bus, new TestWebService2());
        endpoint.publish("/test2");
        endpoint.getInInterceptors().add(new CXFAuthInterceptor2());    //用户认证, http header
        return endpoint;
    }

}

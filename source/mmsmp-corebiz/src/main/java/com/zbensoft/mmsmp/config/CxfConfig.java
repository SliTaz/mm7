package com.zbensoft.mmsmp.config;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.UniBusiness;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.impl.UniBusinessServiceImpl;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;


@Configuration
public class CxfConfig {
    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/UniBusiness/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public UniBusiness userService() {
        return new UniBusinessServiceImpl();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), userService());
        endpoint.publish("/service");
        return endpoint;
    }
}

package com.zbensoft.mmsmp.sp.ra;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.zbensoft.mmsmp.sp.ra.mina.SPServer;

/**
 * 使用外部tomcat必须增加的类
 * @author mrcra
 *
 */
public class SpringBootStartApplication extends SpringBootServletInitializer {
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
		new SPServer().run();
		
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(MMSMPspRaSimulatorApplication.class);
    }
}

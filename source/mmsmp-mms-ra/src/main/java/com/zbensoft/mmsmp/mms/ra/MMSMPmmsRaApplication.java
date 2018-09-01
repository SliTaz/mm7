package com.zbensoft.mmsmp.mms.ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import com.zbensoft.mmsmp.mms.ra.mmsagent.ApplicationListener;


@SpringBootApplication
public class MMSMPmmsRaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MMSMPmmsRaApplication.class, args);
		
		new ApplicationListener().contextInitialized(null);
	}
}

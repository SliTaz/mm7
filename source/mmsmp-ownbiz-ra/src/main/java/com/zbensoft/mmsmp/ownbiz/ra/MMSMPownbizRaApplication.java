package com.zbensoft.mmsmp.ownbiz.ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ServletComponentScan
public class MMSMPownbizRaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MMSMPownbizRaApplication.class, args);

		
	}
}

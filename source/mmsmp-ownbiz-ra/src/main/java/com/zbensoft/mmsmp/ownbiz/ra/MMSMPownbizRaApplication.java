package com.zbensoft.mmsmp.ownbiz.ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class MMSMPownbizRaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MMSMPownbizRaApplication.class, args);

		
	}
}

package com.zbensoft.mmsmp.sp.ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.zbensoft.mmsmp.sp.ra.spagent.listener.ThreadListener;


@SpringBootApplication
@ServletComponentScan
public class MMSMPspRaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MMSMPspRaApplication.class, args);
		
		new ThreadListener().contextInitialized(null);
	}
}

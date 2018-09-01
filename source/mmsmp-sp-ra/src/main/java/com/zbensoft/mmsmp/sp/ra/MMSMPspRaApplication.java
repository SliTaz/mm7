package com.zbensoft.mmsmp.sp.ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zbensoft.mmsmp.sp.ra.spagent.listener.ThreadListener;


@SpringBootApplication
public class MMSMPspRaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MMSMPspRaApplication.class, args);
		
		new ThreadListener().contextInitialized(null);
	}
}

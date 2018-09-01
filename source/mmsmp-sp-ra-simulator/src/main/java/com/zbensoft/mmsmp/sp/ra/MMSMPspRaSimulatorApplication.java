package com.zbensoft.mmsmp.sp.ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zbensoft.mmsmp.sp.ra.mina.SPServer;


@SpringBootApplication
public class MMSMPspRaSimulatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(MMSMPspRaSimulatorApplication.class, args);
		
		new SPServer().run();
	}
}

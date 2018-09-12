package com.zbensoft.mmsmp.sp.ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.zbensoft.mmsmp.sp.ra.mina.SPServer;


@SpringBootApplication
@ServletComponentScan
public class MMSMPspRaSimulatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(MMSMPspRaSimulatorApplication.class, args);
		
		new SPServer().run();
	}
}

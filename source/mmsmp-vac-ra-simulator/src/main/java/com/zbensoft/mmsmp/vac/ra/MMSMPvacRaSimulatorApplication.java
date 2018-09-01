package com.zbensoft.mmsmp.vac.ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zbensoft.mmsmp.vac.ra.mina.vac.VACServer;

@SpringBootApplication
public class MMSMPvacRaSimulatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(MMSMPvacRaSimulatorApplication.class, args);
		new VACServer().run();
	}
}

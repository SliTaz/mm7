package com.zbensoft.mmsmp.vac.ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zbensoft.mmsmp.vac.ra.mina.listen.ThreadListener;


@SpringBootApplication
public class MMSMPvacRaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MMSMPvacRaApplication.class, args);
		new ThreadListener().contextInitialized(null);
	}
}

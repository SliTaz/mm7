package com.zbensoft.mmsmp.mms.ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.zbensoft.mmsmp.mms.ra.mina.MMSServer;


@SpringBootApplication
@ServletComponentScan
public class MMSMPmmsRaSimulatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(MMSMPmmsRaSimulatorApplication.class, args);
	}
}

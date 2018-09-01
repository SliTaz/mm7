package com.zbensoft.mmsmp.tools.war;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

public class EPaymentToolsWarApplication {

	protected static Logger logger = LoggerFactory.getLogger(EPaymentToolsWarApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EPaymentToolsWarApplication.class, args);
		logger.info("SpringBoot Start Success");
		long s = System.currentTimeMillis();
		WarMake a = new WarMake();
		a.doMake();
		System.out.println("usetime=" + (System.currentTimeMillis() - s) + "ms");
	}

}

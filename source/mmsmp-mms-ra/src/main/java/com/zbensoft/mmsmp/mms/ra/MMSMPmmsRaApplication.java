package com.zbensoft.mmsmp.mms.ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import com.zbensoft.mmsmp.mms.ra.mmsagent.ApplicationListener;
import com.zbensoft.mmsmp.mms.ra.mmsagent.Mm7ClientProxy;


@SpringBootApplication
@ServletComponentScan
public class MMSMPmmsRaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MMSMPmmsRaApplication.class, args);
		
		new ApplicationListener().contextInitialized(null);
		
		
		//进行自测的时候调用，如果和其他项目联调。则注释即可。
//		try {
//			Thread.sleep(5000);
//			Mm7ClientProxy.main(null);
//		} catch (Exception e) {
//		}
		
	}
}

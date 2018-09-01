package com.zbensoft.mmsmp.tools.war;

import java.io.File;

import com.zbensoft.mmsmp.tools.war.util.Directory;
import com.zbensoft.mmsmp.tools.war.util.FileUtils;
import com.zbensoft.mmsmp.tools.war.util.WarUtils;

public class WarModify {

	private String path = "D:\\Wangchenyang\\Work\\package\\war\\";
	private String TARGET_HOME = "D:\\Wangchenyang\\Work\\package\\e-payment\\";

	public void warmodify() {

		path = Conf.PROD_HOME + Conf.PATH_SPLIT;
		TARGET_HOME = Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT;
		copyToTmple();

		Directory.deleteDir(new File(path + Conf.FOLDER_02_NAME + "\\api\\"));
		WarUtils.unzip(path + Conf.FOLDER_02_NAME + "\\api.war", path + Conf.FOLDER_02_NAME + "\\api\\");
		WarUtils.unzip(path + Conf.FOLDER_02_NAME + "\\webservice.war", path + Conf.FOLDER_02_NAME + "\\webservice\\");
		WarUtils.unzip(path + Conf.FOLDER_02_NAME + "\\boss.war", path + Conf.FOLDER_02_NAME + "\\boss\\");
		WarUtils.unzip(path + Conf.FOLDER_02_NAME + "\\consumer.war", path + Conf.FOLDER_02_NAME + "\\consumer\\");
		WarUtils.unzip(path + Conf.FOLDER_02_NAME + "\\gov.war", path + Conf.FOLDER_02_NAME + "\\gov\\");
		WarUtils.unzip(path + Conf.FOLDER_02_NAME + "\\merchant.war", path + Conf.FOLDER_02_NAME + "\\merchant\\");//main.war
		WarUtils.unzip(path + Conf.FOLDER_02_NAME + "\\main.war", path + Conf.FOLDER_02_NAME + "\\main\\");
		doapi("01", "api.war");
		doapi("02", "api.war");
		doapi("03", "api.war");
		doapi04("04","api.war");

		doBoss();

	}


	private void copyToTmple() {
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\api.war");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\boss.war");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\consumer.war");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\gov.war");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\merchant.war");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\webservice.war");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\main.war");

		FileUtils.copyFile(TARGET_HOME + "e-payment-api\\target\\e-payment-api-0.0.1-SNAPSHOT.war", path + Conf.FOLDER_02_NAME + "\\api.war");
		FileUtils.copyFile(TARGET_HOME + "e-payment-boss\\target\\e-payment-boss-0.0.1-SNAPSHOT.war", path + Conf.FOLDER_02_NAME + "\\boss.war");
		FileUtils.copyFile(TARGET_HOME + "e-payment-consumer\\target\\e-payment-consumer-0.0.1-SNAPSHOT.war", path + Conf.FOLDER_02_NAME + "\\consumer.war");
		FileUtils.copyFile(TARGET_HOME + "e-payment-government\\target\\e-payment-government-0.0.1-SNAPSHOT.war", path + Conf.FOLDER_02_NAME + "\\gov.war");
		FileUtils.copyFile(TARGET_HOME + "e-payment-merchant\\target\\e-payment-merchant-0.0.1-SNAPSHOT.war", path + Conf.FOLDER_02_NAME + "\\merchant.war");
		FileUtils.copyFile(TARGET_HOME + "e-payment-webservice\\target\\e-payment-webservice-0.0.1-SNAPSHOT.war", path + Conf.FOLDER_02_NAME + "\\webservice.war");
		FileUtils.copyFile(TARGET_HOME + "e-payment-main\\target\\e-payment-main-0.0.1-SNAPSHOT.war", path + Conf.FOLDER_02_NAME + "\\main.war");

	}

	private void doBoss() {

		String warPath = path + Conf.FOLDER_03_NAME + "\\boss\\";
		Directory.deleteDir(new File(warPath));
		new File(warPath).mkdirs();
		FileUtils.copyFile(path + Conf.FOLDER_02_NAME + "\\boss.war", warPath + "ROOT.war");
	}
	
	private void doapi04(String fold, String name) {
		String warPath = path + Conf.FOLDER_03_NAME + "\\" + fold + "\\";
		Directory.deleteDir(new File(warPath));
		new File(warPath).mkdirs();

		FileUtils.delFile(warPath + "api.war");
		FileUtils.delFile(warPath + "cliente.war");
		FileUtils.delFile(warPath + "atencion.war");
		FileUtils.delFile(warPath + "vendedor.war");
		FileUtils.delFile(warPath + "webservice.war");

		/******修改api******/
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\application.properties");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\quartz.properties");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\license.dat");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\logback.xml");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\api\\application.properties", path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\application.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\api\\quartz.properties", path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\quartz.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\api\\license.dat", path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\license.dat");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\api\\logback.xml", path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\logback.xml");
		WarUtils.zip(warPath + name, path + Conf.FOLDER_02_NAME + "\\api\\");

		/******修改webservice******/
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\logback.xml");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\application.properties");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\encryption-server-keystore.jks");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\encryption-server-truststore.jks");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\server_signing.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\webservice\\logback.xml", path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\logback.xml");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\webservice\\application.properties", path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\application.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\webservice\\encryption-server-keystore.jks", path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\encryption-server-keystore.jks");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\webservice\\encryption-server-truststore.jks", path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\encryption-server-truststore.jks");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\webservice\\server_signing.properties", path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\server_signing.properties");
		WarUtils.zip(warPath + "webservice.war", path + Conf.FOLDER_02_NAME + "\\webservice\\");
		
		/******修改boss******/
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\boss\\WEB-INF\\classes\\application.properties");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\boss\\WEB-INF\\static\\js\\zben.js");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\boss\\application.properties", path + Conf.FOLDER_02_NAME + "\\boss\\WEB-INF\\classes\\application.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\boss\\zben.js", path + Conf.FOLDER_02_NAME + "\\boss\\WEB-INF\\classes\\static\\js\\zben.js");
		WarUtils.zip(warPath + "boss.war", path + Conf.FOLDER_02_NAME + "\\boss\\");
		
		/******修改consumer******/
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\consumer\\WEB-INF\\classes\\application.properties");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\consumer\\WEB-INF\\static\\js\\zben.js");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\consumer\\application.properties", path + Conf.FOLDER_02_NAME + "\\consumer\\WEB-INF\\classes\\application.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\consumer\\zben.js", path + Conf.FOLDER_02_NAME + "\\consumer\\WEB-INF\\classes\\static\\js\\zben.js");
		WarUtils.zip(warPath + "cliente.war", path + Conf.FOLDER_02_NAME + "\\consumer\\");
		
		/******修改gov******/
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\gov\\WEB-INF\\classes\\application.properties");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\gov\\WEB-INF\\static\\js\\zben.js");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\gov\\application.properties", path + Conf.FOLDER_02_NAME + "\\gov\\WEB-INF\\classes\\application.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\gov\\zben.js", path + Conf.FOLDER_02_NAME + "\\gov\\WEB-INF\\classes\\static\\js\\zben.js");
		WarUtils.zip(warPath + "atencion.war", path + Conf.FOLDER_02_NAME + "\\gov\\");
		
		/******修改merchant******/
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\merchant\\WEB-INF\\classes\\application.properties");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\merchant\\WEB-INF\\static\\js\\zben.js");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\merchant\\application.properties", path + Conf.FOLDER_02_NAME + "\\merchant\\WEB-INF\\classes\\application.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\merchant\\zben.js", path + Conf.FOLDER_02_NAME + "\\merchant\\WEB-INF\\classes\\static\\js\\zben.js");
		WarUtils.zip(warPath + "vendedor.war", path + Conf.FOLDER_02_NAME + "\\merchant\\");

		/******修改main******/
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\main\\WEB-INF\\static\\js\\zben.js");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\main\\zben.js", path + Conf.FOLDER_02_NAME + "\\main\\WEB-INF\\classes\\static\\js\\zben.js");
		WarUtils.zip(warPath + "ROOT.war", path + Conf.FOLDER_02_NAME + "\\main\\");
	}


	private void doapi(String fold, String name) {
		String warPath = path + Conf.FOLDER_03_NAME + "\\" + fold + "\\";
		Directory.deleteDir(new File(warPath));
		new File(warPath).mkdirs();

		FileUtils.delFile(warPath + "api.war");
		FileUtils.delFile(warPath + "cliente.war");
		FileUtils.delFile(warPath + "atencion.war");
		FileUtils.delFile(warPath + "vendedor.war");
		FileUtils.delFile(warPath + "webservice.war");

		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\application.properties");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\license.dat");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\logback.xml");

		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\application.properties", path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\application.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\license.dat", path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\license.dat");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\logback.xml", path + Conf.FOLDER_02_NAME + "\\api\\WEB-INF\\classes\\logback.xml");

		// C:\Wangchenyang\Work\package\war\01配置文件\03\webservice

		WarUtils.zip(warPath + name, path + Conf.FOLDER_02_NAME + "\\api\\");

		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\logback.xml");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\webservice\\logback.xml", path + Conf.FOLDER_02_NAME + "\\webservice\\WEB-INF\\classes\\logback.xml");

		WarUtils.zip(warPath + "webservice.war", path + Conf.FOLDER_02_NAME + "\\webservice\\");

		FileUtils.copyFile(path + Conf.FOLDER_02_NAME + "\\consumer.war", warPath + "cliente.war");
		FileUtils.copyFile(path + Conf.FOLDER_02_NAME + "\\gov.war", warPath + "atencion.war");
		FileUtils.copyFile(path + Conf.FOLDER_02_NAME + "\\merchant.war", warPath + "vendedor.war");
		FileUtils.copyFile(path + Conf.FOLDER_02_NAME + "\\main.war", warPath + "ROOT.war");
	}
}

package com.zbensoft.mmsmp.tools.war;

import java.io.File;

import com.zbensoft.mmsmp.tools.war.util.Directory;
import com.zbensoft.mmsmp.tools.war.util.FileUtils;
import com.zbensoft.mmsmp.tools.war.util.WarUtils;

public class WarModify {

	private String path = "";
	private String TARGET_HOME = "";

	public void warmodify() {

		path = Conf.PROD_HOME + Conf.PATH_SPLIT;
		TARGET_HOME = Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT;
		copyToTmple();

		Directory.deleteDir(new File(path + Conf.FOLDER_02_NAME + "\\api\\"));
		WarUtils.unzip(path + Conf.FOLDER_02_NAME + "\\api.war", path + Conf.FOLDER_02_NAME + "\\api\\");
		WarUtils.unzip(path + Conf.FOLDER_02_NAME + "\\boss.war", path + Conf.FOLDER_02_NAME + "\\boss\\");
		WarUtils.unzip(path + Conf.FOLDER_02_NAME + "\\corebiz.war", path + Conf.FOLDER_02_NAME + "\\corebiz\\");
		doapi04("04","api.war");
		doBoss();

	}


	private void copyToTmple() {
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\api.war");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\boss.war");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\corebiz.war");

		FileUtils.copyFile(TARGET_HOME + "mmsmp-api\\target\\mmsmp-api-0.0.1-SNAPSHOT.war", path + Conf.FOLDER_02_NAME + "\\api.war");
		FileUtils.copyFile(TARGET_HOME + "mmsmp-boss\\target\\mmsmp-boss-0.0.1-SNAPSHOT.war", path + Conf.FOLDER_02_NAME + "\\boss.war");
		FileUtils.copyFile(TARGET_HOME + "mmsmp-corebiz\\target\\mmsmp-corebiz-0.0.1-SNAPSHOT.war", path + Conf.FOLDER_02_NAME + "\\corebiz.war");

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
		FileUtils.delFile(warPath + "corebiz.war");

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

		/******修改boss******/
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\boss\\WEB-INF\\classes\\application.properties");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\boss\\WEB-INF\\static\\js\\zben.js");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\boss\\application.properties", path + Conf.FOLDER_02_NAME + "\\boss\\WEB-INF\\classes\\application.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\boss\\zben.js", path + Conf.FOLDER_02_NAME + "\\boss\\WEB-INF\\classes\\static\\js\\zben.js");
		WarUtils.zip(warPath + "boss.war", path + Conf.FOLDER_02_NAME + "\\boss\\");
		
		/******修改corebiz******/
		//TODO
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\application.properties");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\applicationContext-cache.xml");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\applicationContext-queue.xml");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\applicationContext-service.xml");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\applicationContext-task.xml");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\applicationContext.xml");
		FileUtils.delFile(path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\log4j.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\corebiz\\application.properties", path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\application.properties");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\corebiz\\applicationContext-cache.xml", path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\applicationContext-cache.xml");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\corebiz\\applicationContext-queue.xml", path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\applicationContext-queue.xml");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\corebiz\\applicationContext-service.xml", path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\applicationContext-service.xml");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\corebiz\\applicationContext-task.xml", path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\applicationContext-task.xml");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\corebiz\\applicationContext.xml", path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\applicationContext.xml");
		FileUtils.copyFile(path + Conf.FOLDER_01_NAME + "\\" + fold + "\\corebiz\\log4j.properties", path + Conf.FOLDER_02_NAME + "\\corebiz\\WEB-INF\\classes\\log4j.properties");
		WarUtils.zip(warPath + "corebiz.war", path + Conf.FOLDER_02_NAME + "\\corebiz\\");
		
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

	}
}

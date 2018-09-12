package com.zbensoft.mmsmp.tools.war;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.zbensoft.mmsmp.tools.war.checkConfiger.CheckConfiger;
import com.zbensoft.mmsmp.tools.war.util.FileUtils;



public class WarMake {

	public void doMake() {
		if(!CheckConfiger.check()){
			System.out.println("配置文件校验失败，无法继续打包");
			return;
		}
		
		while (new File(Conf.PROD_HOME).exists()) {
			System.out.println("删除所有临时文件");
			runScript("cmd /c rd/s/q " + Conf.PROD_HOME);
			// Directory.deleteDir(new File(Conf.PROD_HOME));
			System.out.println("删除结束");
		}

		new File(Conf.PROD_HOME).mkdirs();
		new File(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME).mkdirs();
		new File(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME).mkdirs();
		new File(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_01_NAME).mkdirs();
		new File(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_02_NAME).mkdirs();
		new File(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_03_NAME).mkdirs();

		System.out.println("复制工程");
		runScript("cmd /c xcopy " + Conf.WORK_HOME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + " " + Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + " /e");
		System.out.println("复制工程结束");

		String projectPath = Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME;
		FileUtils.delFile(projectPath + "\\pom.xml");
		FileUtils.delFile(projectPath + "\\mmsmp-api\\pom.xml");
		FileUtils.delFile(projectPath + "\\mmsmp-boss\\pom.xml");
		FileUtils.delFile(projectPath + "\\mmsmp-corebiz\\pom.xml");

		FileUtils.copyFile(projectPath + "\\pom-prod.xml", projectPath + "\\pom.xml");
		FileUtils.copyFile(projectPath + "\\mmsmp-api\\src\\main\\resources\\conf\\prod\\pom.xml", projectPath + "\\mmsmp-api\\pom.xml");
		FileUtils.copyFile(projectPath + "\\mmsmp-boss\\src\\main\\resources\\conf\\prod\\pom.xml", projectPath + "\\mmsmp-boss\\pom.xml");
		FileUtils.copyFile(projectPath + "\\mmsmp-corebiz\\src\\main\\resources\\conf\\prod\\pom.xml", projectPath + "\\mmsmp-corebiz\\pom.xml");

		System.out.println("复制现网配置");
		runScript("cmd /c xcopy " + projectPath + "\\mmsmp-tools-war\\src\\main\\resources\\conf" + " " + Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_01_NAME + " /e");
		System.out.println("复制现网配置结束");

		// while (new File(projectPath + "\\mmsmp-tools").exists()) {
		// System.out.println("删除目录");
		// runScript("cmd /c rd/s/q " + projectPath + "\\mmsmp-tools");
		// System.out.println("删除结束");
		// }
		//
		// while (new File(projectPath + "\\mmsmp-tools-war").exists()) {
		// System.out.println("删除目录");
		// runScript("cmd /c rd/s/q " + projectPath + "\\mmsmp-tools-war");
		// System.out.println("删除结束");
		// }
		System.out.println("js混淆关闭");
		
		//modify by yp
//		System.out.println("js混淆开始");
//		JsMake a = new JsMake();
//		a.doMake();
//		System.out.println("js混淆结束");

		runScript("cmd /c " + Conf.CAMMAD_PATH + " && cd " + projectPath + "&& mvn clean package -D maven.test.skip=true -P prod -e");

		System.out.println("修改为现网配置war包");
		WarModify war = new WarModify();
		war.warmodify();
		System.out.println("修改为现网配置war包结束");
		
		//modify by yp
//		a.printErrorList();
	}

	public static void runScript(String cmd) {
		System.out.println("执行命令：" + cmd);
		Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令
			BufferedReader inBr = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));
			String lineStr;
			while ((lineStr = inBr.readLine()) != null)
				// 获得命令执行后在控制台的输出信息
				System.out.println(lineStr);// 打印输出信息
			// 检查命令是否执行失败。
			if (p.waitFor() != 0) {
				if (p.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
					System.err.println("命令执行失败!");
			}
			inBr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

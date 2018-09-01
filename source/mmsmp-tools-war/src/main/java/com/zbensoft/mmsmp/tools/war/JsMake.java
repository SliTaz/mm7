package com.zbensoft.mmsmp.tools.war;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 只做了压缩，没做混淆，混淆后无法使用。
 * 
 * @author xieqiang
 *
 */
public class JsMake {

	// public static void main(String[] args) {
	// JsMake a = new JsMake();
	// a.doMake();
	// a.printErrorList();
	// }

	public static List<String> errorList = new ArrayList<>();

	public void doMake() {
		doPathNew(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT + "e-payment-boss\\src\\main\\resources\\static\\js", ".js");
		doPathNew(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT + "e-payment-consumer\\src\\main\\resources\\static\\js", ".js");
		doPathNew(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT + "e-payment-merchant\\src\\main\\resources\\static\\js", ".js");
		doPathNew(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT + "e-payment-government\\src\\main\\resources\\static\\js", ".js");
		doPathNew(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT + "e-payment-main\\src\\main\\resources\\static\\js", ".js");
		
		doPathNew(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT + "e-payment-boss\\src\\main\\resources\\conf", ".js");
		doPathNew(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT + "e-payment-consumer\\src\\main\\resources\\conf", ".js");
		doPathNew(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT + "e-payment-merchant\\src\\main\\resources\\conf", ".js");
		doPathNew(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT + "e-payment-government\\src\\main\\resources\\conf", ".js");
		doPathNew(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + Conf.PROJECT_NAME + Conf.PATH_SPLIT + "e-payment-main\\src\\main\\resources\\conf", ".js");
		
		doPathNew(Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_01_NAME, ".js");
	}

	public void printErrorList() {
		if (errorList.size() > 0) {
			System.err.println("========================== " + errorList.size() + " =====================================");
			for (String string : errorList) {
				System.err.println("混淆失败:" + string);
			}
			System.err.println("========================== " + errorList.size() + " =====================================");

			System.out.println("请使用此命令进行测试修改：");
			System.out.println(Conf.CAMMAD_PATH);
			System.out.println("cd " + Conf.PROD_HOME + Conf.PATH_SPLIT + Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + "\\e-payment\\e-payment-tools-war\\lib");
			// System.out.println("java -jar yuicompressor-2.4.9.jar infilename -o outfilename");
			System.out.println("java -jar yuicompressor-2.4.9.jar --nomunge infilename");
			System.out.println("");
			System.out.println("或者：");
			// System.out.println("String[] args = { infilename, \"-o\", outfilename };");
			System.out.println("String[] args = { \"--nomunge\", infilename};");
			System.out.println("ZBENYUICompressor.doCompressor(args);");

		}
	}

	/**
	 * --disable-optimizations 禁止优化<br/>
	 * --nomunge 只压缩, 不对局部变量进行混淆<br/>
	 * 
	 * @param modleName
	 * @param endWith
	 */
	private void doPathNew(String modleName, String endWith) {
		List<File> list = new ArrayList<File>();
		getFile(list, modleName, endWith);
		for (File file : list) {
			String[] args = { "--nomunge", file.getAbsolutePath(), "-o", file.getAbsolutePath() };
			try {
				ZBENYUICompressor.doCompressor(args);
			} catch (Exception e) {
				errorList.add(file.getPath());
			}
		}

	}
	/*
	 * private void doPath(String modleName, String endWith) { List<File> list = new ArrayList<File>(); getFile(list, modleName, endWith); for (File file : list) { String filename
	 * = file.getAbsolutePath().replaceAll(Conf.FOLDER_00_NAME, Conf.FOLDER_04_NAME); filename = file.getAbsolutePath().replaceAll(Conf.FOLDER_01_NAME, Conf.FOLDER_04_NAME); File
	 * filetmp = new File(filename); if (!filetmp.exists()) { try { filetmp.getParentFile().mkdirs(); filetmp.createNewFile(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } } String[] args = { file.getAbsolutePath(), "-o", filename }; try { ZBENYUICompressor.doCompressor(args);
	 * FileUtils.delFile(file.getAbsolutePath()); FileUtils.copyFile(filename, file.getAbsolutePath()); } catch (Exception e) { errorList.add(file.getPath()); //
	 * System.err.println("混淆失败:" + file.getPath()); filetmp.delete(); } // String cmd = "cmd /c " + Conf.CAMMAD_PATH + " && " + "cd " + Conf.PROD_HOME + Conf.PATH_SPLIT +
	 * Conf.FOLDER_00_NAME + Conf.PATH_SPLIT + // "\\e-payment\\e-payment-tools-war\\lib" + // // " && " + "java -jar yuicompressor-2.4.9.jar " + file.getAbsolutePath() + " -o " +
	 * filename; // if(!runScript(cmd)){ // filetmp.delete(); // } }
	 * 
	 * }
	 */

	public static boolean runScript(String cmd) {
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
				// if (p.exitValue() == 1){// p.exitValue()==0表示正常结束，1：非正常结束
				System.err.println("命令执行失败:" + cmd);
				return false;
				// }
			}
			inBr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private static void getFile(List<File> list, String path, String endWith) {
		// get file list where the path has
		File file = new File(path);
		// get the folder list
		File[] array = file.listFiles();

		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile()) {
				if (array[i].getName().endsWith(endWith)) {
					list.add(array[i]);
				}
			} else if (array[i].isDirectory()) {
				getFile(list, array[i].getPath(), endWith);
			}
		}
	}

}

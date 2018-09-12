package com.zbensoft.mmsmp.tools.war.checkConfiger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class CheckConfiger {
	private static String diffPath = "/mmsmp-tools-war/src/main/java/com/zbensoft/mmsmp/tools/war/checkConfiger/file/";

	public static boolean check() {
		if (!doapi()) {
			return false;
		}
//		if (!doboss()) {
//			return false;
//		}
		
		if (!docorebize()) {
			return false;
		}
//		if (!dogovernment()) {
//			return false;
//		}
//		if (!domain()) {
//			return false;
//		}
//		if (!domerchant()) {
//			return false;
//		}
//		if (!dowebservice()) {
//			return false;
//		}
		return true;
	}

	private static boolean docorebize() {
		
		
		
		if (!diff("/mmsmp-corebiz/src/main/resources/application.properties", "/mmsmp-corebiz/src/main/resources/conf/dev/application.properties", diffPath + "corebiz-application-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/application.properties", "/mmsmp-corebiz/src/main/resources/conf/prod/application.properties", diffPath + "corebiz-application-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/application.properties", "/mmsmp-corebiz/src/main/resources/conf/test/application.properties", diffPath + "corebiz-application-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/application.properties", "/mmsmp-tools-war/src/main/resources/conf/04/corebiz/application.properties", diffPath + "corebiz-application-properties-war-04-diff.txt")) {
			return false;
		}
		
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-cache.xml", "/mmsmp-corebiz/src/main/resources/conf/dev/applicationContext-cache.xml", diffPath + "corebiz-applicationContext-cache-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-cache.xml", "/mmsmp-corebiz/src/main/resources/conf/prod/applicationContext-cache.xml", diffPath + "corebiz-applicationContext-cache-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-cache.xml", "/mmsmp-corebiz/src/main/resources/conf/test/applicationContext-cache.xml", diffPath + "corebiz-applicationContext-cache-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-cache.xml", "/mmsmp-tools-war/src/main/resources/conf/04/corebiz/applicationContext-cache.xml", diffPath + "corebiz-applicationContext-cache-war-04-diff.txt")) {
			return false;
		}
		
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-queue.xml", "/mmsmp-corebiz/src/main/resources/conf/dev/applicationContext-queue.xml", diffPath + "corebiz-applicationContext-queue-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-queue.xml", "/mmsmp-corebiz/src/main/resources/conf/prod/applicationContext-queue.xml", diffPath + "corebiz-applicationContext-queue-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-queue.xml", "/mmsmp-corebiz/src/main/resources/conf/test/applicationContext-queue.xml", diffPath + "corebiz-applicationContext-queue-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-queue.xml", "/mmsmp-tools-war/src/main/resources/conf/04/corebiz/applicationContext-queue.xml", diffPath + "corebiz-applicationContext-queue-war-04-diff.txt")) {
			return false;
		}
		
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-service.xml", "/mmsmp-corebiz/src/main/resources/conf/dev/applicationContext-service.xml", diffPath + "corebiz-applicationContext-service-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-service.xml", "/mmsmp-corebiz/src/main/resources/conf/prod/applicationContext-service.xml", diffPath + "corebiz-applicationContext-service-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-service.xml", "/mmsmp-corebiz/src/main/resources/conf/test/applicationContext-service.xml", diffPath + "corebiz-applicationContext-service-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-service.xml", "/mmsmp-tools-war/src/main/resources/conf/04/corebiz/applicationContext-service.xml", diffPath + "corebiz-applicationContext-service-war-04-diff.txt")) {
			return false;
		}

		
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-task.xml", "/mmsmp-corebiz/src/main/resources/conf/dev/applicationContext-task.xml", diffPath + "corebiz-applicationContext-task-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-task.xml", "/mmsmp-corebiz/src/main/resources/conf/prod/applicationContext-task.xml", diffPath + "corebiz-applicationContext-task-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-task.xml", "/mmsmp-corebiz/src/main/resources/conf/test/applicationContext-task.xml", diffPath + "corebiz-applicationContext-task-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext-task.xml", "/mmsmp-tools-war/src/main/resources/conf/04/corebiz/applicationContext-task.xml", diffPath + "corebiz-applicationContext-task-war-04-diff.txt")) {
			return false;
		}

		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext.xml", "/mmsmp-corebiz/src/main/resources/conf/dev/applicationContext.xml", diffPath + "corebiz-applicationContext-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext.xml", "/mmsmp-corebiz/src/main/resources/conf/prod/applicationContext.xml", diffPath + "corebiz-applicationContext-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext.xml", "/mmsmp-corebiz/src/main/resources/conf/test/applicationContext.xml", diffPath + "corebiz-applicationContext-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/applicationContext.xml", "/mmsmp-tools-war/src/main/resources/conf/04/corebiz/applicationContext.xml", diffPath + "corebiz-applicationContext-war-04-diff.txt")) {
			return false;
		}
		
		
		if (!diff("/mmsmp-corebiz/src/main/resources/log4j.properties", "/mmsmp-corebiz/src/main/resources/conf/dev/log4j.properties", diffPath + "corebiz-log4j-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/log4j.properties", "/mmsmp-corebiz/src/main/resources/conf/prod/log4j.properties", diffPath + "corebiz-log4j-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/log4j.properties", "/mmsmp-corebiz/src/main/resources/conf/test/log4j.properties", diffPath + "corebiz-log4j-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-corebiz/src/main/resources/log4j.properties", "/mmsmp-tools-war/src/main/resources/conf/04/corebiz/log4j.properties", diffPath + "corebiz-log4j-properties-war-04-diff.txt")) {
			return false;
		}
		

		if (!diff("/mmsmp-corebiz/pom.xml", "/mmsmp-corebiz/src/main/resources/conf/prod/pom.xml", diffPath + "corebiz-pom-xml-conf-prod-diff.txt")) {
			return false;
		}
		return true;
	}
	private static boolean doboss() {
		if (!diff("/mmsmp-boss/src/main/resources/static/js/zben.js", "/mmsmp-boss/src/main/resources/conf/dev/zben.js", diffPath + "boss-zben-js-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-boss/src/main/resources/static/js/zben.js", "/mmsmp-boss/src/main/resources/conf/prod/zben.js", diffPath + "boss-zben-js-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-boss/src/main/resources/static/js/zben.js", "/mmsmp-boss/src/main/resources/conf/test/zben.js", diffPath + "boss-zben-js-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-boss/src/main/resources/static/js/zben.js", "/mmsmp-tools-war/src/main/resources/conf/04/boss/zben.js", diffPath + "boss-zben-js-war-04-diff.txt")) {
			return false;
		}
		
		
		
		if (!diff("/mmsmp-boss/src/main/resources/application.properties", "/mmsmp-boss/src/main/resources/conf/dev/application.properties", diffPath + "boss-application-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-boss/src/main/resources/application.properties", "/mmsmp-boss/src/main/resources/conf/prod/application.properties", diffPath + "boss-application-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-boss/src/main/resources/application.properties", "/mmsmp-boss/src/main/resources/conf/test/application.properties", diffPath + "boss-application-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-boss/src/main/resources/application.properties", "/mmsmp-tools-war/src/main/resources/conf/04/boss/application.properties", diffPath + "boss-application-properties-war-04-diff.txt")) {
			return false;
		}
		return true;
	}
	private static boolean doapi() {
		if (!diff("/mmsmp-api/src/main/resources/application.properties", "/mmsmp-api/src/main/resources/conf/dev/application.properties", diffPath + "api-application-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/application.properties", "/mmsmp-api/src/main/resources/conf/prod/application.properties", diffPath + "api-application-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/application.properties", "/mmsmp-api/src/main/resources/conf/test/application.properties", diffPath + "api-application-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/application.properties", "/mmsmp-tools-war/src/main/resources/conf/01/application.properties", diffPath + "api-application-properties-war-01-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/application.properties", "/mmsmp-tools-war/src/main/resources/conf/04/api/application.properties", diffPath + "api-application-properties-war-04-diff.txt")) {
			return false;
		}

		if (!diff("/mmsmp-api/src/main/resources/logback.xml", "/mmsmp-api/src/main/resources/conf/dev/logback.xml", diffPath + "api-logback-xml-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/logback.xml", "/mmsmp-api/src/main/resources/conf/prod/logback.xml", diffPath + "api-logback-xml-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/logback.xml", "/mmsmp-api/src/main/resources/conf/test/logback.xml", diffPath + "api-logback-xml-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/logback.xml", "/mmsmp-tools-war/src/main/resources/conf/01/logback.xml", diffPath + "api-logback-xml-war-01-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/logback.xml", "/mmsmp-tools-war/src/main/resources/conf/04/api/logback.xml", diffPath + "api-logback-xml-war-04-diff.txt")) {
			return false;
		}

		if (!diff("/mmsmp-api/src/main/resources/quartz.properties", "/mmsmp-api/src/main/resources/conf/dev/quartz.properties", diffPath + "api-quartz-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/quartz.properties", "/mmsmp-api/src/main/resources/conf/prod/quartz.properties", diffPath + "api-quartz-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/quartz.properties", "/mmsmp-api/src/main/resources/conf/test/quartz.properties", diffPath + "api-quartz-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/quartz.properties", "/mmsmp-tools-war/src/main/resources/conf/04/api/quartz.properties", diffPath + "api-quartz-properties-war-04-diff.txt")) {
			return false;
		}

		if (!diff("/mmsmp-api/src/main/resources/email-alarm.properties", "/mmsmp-api/src/main/resources/conf/prod/email-alarm.properties", diffPath + "api-email-alarm-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/email-alarm.properties", "/mmsmp-api/src/main/resources/conf/test/email-alarm.properties", diffPath + "api-email-alarm-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/email-alarm.properties", "/mmsmp-tools-war/src/main/resources/conf/04/api/email-alarm.properties", diffPath + "api-email-alarm-properties-war-04-diff.txt")) {
			return false;
		}

		if (!diff("/mmsmp-api/src/main/resources/email-epay.properties", "/mmsmp-api/src/main/resources/conf/prod/email-epay.properties", diffPath + "api-email-epay-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/email-epay.properties", "/mmsmp-api/src/main/resources/conf/test/email-epay.properties", diffPath + "api-email-epay-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/mmsmp-api/src/main/resources/email-epay.properties", "/mmsmp-tools-war/src/main/resources/conf/04/api/email-epay.properties", diffPath + "api-email-epay-properties-war-04-diff.txt")) {
			return false;
		}

		if (!diff("/mmsmp-api/pom.xml", "/mmsmp-api/src/main/resources/conf/prod/pom.xml", diffPath + "api-pom-xml-conf-dev-diff.txt")) {
			return false;
		}
		


		return true;
	}

	private static boolean diff(String filea, String fileb, String filediff) {
		List<String> listc = getDiff(filea, fileb, filediff);
		return checkResult(listc, filea, fileb, filediff);
	}

	private static boolean checkResult(List<String> listc, String filea, String fileb, String filediff) {
		if (listc != null && listc.size() > 0) {
			System.err.println(filea + " <--> " + fileb + "===diff====" + filediff);
			for (String str : listc) {
				System.err.println(str);
			}
			return false;
		}
		return true;
	}

	private static List<String> getDiff(String filea, String fileb, String filediff) {
		String path = System.getProperty("user.dir").replaceAll("mmsmp-tools-war", "");
		List<String> lista = getList(path + filea);
		List<String> listb = getList(path + fileb);
		String diff = getString(path + filediff);
		List<String> list = new ArrayList<String>();
		if (lista == null || listb == null || lista.size() != listb.size()) {
			list.add("文件不存在，或者长度不匹配");
			return list;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (!lista.get(i).equals(listb.get(i))) {
				String msg = lista.get(i) + " <--> " + listb.get(i);
				if (diff == null || !diff.contains(msg)) {
					list.add(lista.get(i) + " <--> " + listb.get(i));
				}
			}
		}
		return list;
	}

	public static List<String> getList(String path) {
		try {
			return FileUtils.readLines(new File(path), "UTF-8");
		} catch (IOException e) {
			return null;
		}
	}

	public static String getString(String path) {
		try {
			return FileUtils.readFileToString(new File(path), "UTF-8");
		} catch (IOException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		if (CheckConfiger.check()) {

		}
	}

}

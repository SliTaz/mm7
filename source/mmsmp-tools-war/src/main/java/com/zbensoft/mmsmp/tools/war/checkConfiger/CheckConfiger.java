package com.zbensoft.mmsmp.tools.war.checkConfiger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class CheckConfiger {
	private static String diffPath = "/e-payment-tools-war/src/main/java/com/zbensoft/e/payment/tools/war/checkConfiger/file/";

	public static boolean check() {
		if (!doapi()) {
			return false;
		}
		if (!doboss()) {
			return false;
		}
		if (!doconsumer()) {
			return false;
		}
		if (!dogovernment()) {
			return false;
		}
		if (!domain()) {
			return false;
		}
		if (!domerchant()) {
			return false;
		}
		if (!dowebservice()) {
			return false;
		}
		return true;
	}

	private static boolean dowebservice() {
		if (!diff("/e-payment-webservice/src/main/resources/logback.xml", "/e-payment-webservice/src/main/resources/conf/dev/logback.xml", diffPath + "webservice-logback-xml-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-webservice/src/main/resources/logback.xml", "/e-payment-webservice/src/main/resources/conf/prod/logback.xml", diffPath + "webservice-logback-xml-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-webservice/src/main/resources/logback.xml", "/e-payment-webservice/src/main/resources/conf/test/logback.xml", diffPath + "webservice-logback-xml-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-webservice/src/main/resources/logback.xml", "/e-payment-tools-war/src/main/resources/conf/01/webservice/logback.xml", diffPath + "webservice-logback-xml-war-01-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-webservice/src/main/resources/logback.xml", "/e-payment-tools-war/src/main/resources/conf/02/webservice/logback.xml", diffPath + "webservice-logback-xml-war-02-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-webservice/src/main/resources/logback.xml", "/e-payment-tools-war/src/main/resources/conf/03/webservice/logback.xml", diffPath + "webservice-logback-xml-war-03-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-webservice/src/main/resources/logback.xml", "/e-payment-tools-war/src/main/resources/conf/04/webservice/logback.xml", diffPath + "webservice-logback-xml-war-04-diff.txt")) {
			return false;
		}
		
		
		
		if (!diff("/e-payment-webservice/src/main/resources/application.properties", "/e-payment-webservice/src/main/resources/conf/dev/application.properties", diffPath + "webservice-application-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-webservice/src/main/resources/application.properties", "/e-payment-webservice/src/main/resources/conf/prod/application.properties", diffPath + "webservice-application-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-webservice/src/main/resources/application.properties", "/e-payment-webservice/src/main/resources/conf/test/application.properties", diffPath + "webservice-application-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-webservice/src/main/resources/application.properties", "/e-payment-tools-war/src/main/resources/conf/04/webservice/application.properties", diffPath + "webservice-application-properties-war-04-diff.txt")) {
			return false;
		}
		

		if (!diff("/e-payment-webservice/src/main/resources/server_signing.properties", "/e-payment-tools-war/src/main/resources/conf/04/webservice/server_signing.properties", diffPath + "webservice-server-signing-properties-war-04-diff.txt")) {
			return false;
		}
		return true;
	}
	private static boolean domerchant() {
		if (!diff("/e-payment-merchant/src/main/resources/static/js/zben.js", "/e-payment-merchant/src/main/resources/conf/dev/zben.js", diffPath + "merchant-zben-js-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-merchant/src/main/resources/static/js/zben.js", "/e-payment-merchant/src/main/resources/conf/prod/zben.js", diffPath + "merchant-zben-js-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-merchant/src/main/resources/static/js/zben.js", "/e-payment-merchant/src/main/resources/conf/test/zben.js", diffPath + "merchant-zben-js-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-merchant/src/main/resources/static/js/zben.js", "/e-payment-tools-war/src/main/resources/conf/04/merchant/zben.js", diffPath + "merchant-zben-js-war-04-diff.txt")) {
			return false;
		}
		
		
		
		if (!diff("/e-payment-merchant/src/main/resources/application.properties", "/e-payment-merchant/src/main/resources/conf/dev/application.properties", diffPath + "merchant-application-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-merchant/src/main/resources/application.properties", "/e-payment-merchant/src/main/resources/conf/prod/application.properties", diffPath + "merchant-application-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-merchant/src/main/resources/application.properties", "/e-payment-merchant/src/main/resources/conf/test/application.properties", diffPath + "merchant-application-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-merchant/src/main/resources/application.properties", "/e-payment-tools-war/src/main/resources/conf/04/merchant/application.properties", diffPath + "merchant-application-properties-war-04-diff.txt")) {
			return false;
		}
		

		if (!diff("/e-payment-merchant/pom.xml", "/e-payment-merchant/src/main/resources/conf/prod/pom.xml", diffPath + "merchant-pom-xml-conf-prod-diff.txt")) {
			return false;
		}
		
		return true;
	}
	private static boolean domain() {
		if (!diff("/e-payment-main/src/main/resources/static/js/zben.js", "/e-payment-main/src/main/resources/conf/dev/zben.js", diffPath + "main-zben-js-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-main/src/main/resources/static/js/zben.js", "/e-payment-main/src/main/resources/conf/prod/zben.js", diffPath + "main-zben-js-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-main/src/main/resources/static/js/zben.js", "/e-payment-main/src/main/resources/conf/test/zben.js", diffPath + "main-zben-js-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-main/src/main/resources/static/js/zben.js", "/e-payment-tools-war/src/main/resources/conf/04/main/zben.js", diffPath + "main-zben-js-war-04-diff.txt")) {
			return false;
		}
		
		
		
		if (!diff("/e-payment-main/src/main/resources/application.properties", "/e-payment-main/src/main/resources/conf/dev/application.properties", diffPath + "main-application-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-main/src/main/resources/application.properties", "/e-payment-main/src/main/resources/conf/prod/application.properties", diffPath + "main-application-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-main/src/main/resources/application.properties", "/e-payment-main/src/main/resources/conf/test/application.properties", diffPath + "main-application-properties-conf-test-diff.txt")) {
			return false;
		}
		return true;
	}
	
	private static boolean dogovernment() {
		if (!diff("/e-payment-government/src/main/resources/static/js/zben.js", "/e-payment-government/src/main/resources/conf/dev/zben.js", diffPath + "government-zben-js-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-government/src/main/resources/static/js/zben.js", "/e-payment-government/src/main/resources/conf/prod/zben.js", diffPath + "government-zben-js-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-government/src/main/resources/static/js/zben.js", "/e-payment-government/src/main/resources/conf/test/zben.js", diffPath + "government-zben-js-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-government/src/main/resources/static/js/zben.js", "/e-payment-tools-war/src/main/resources/conf/04/gov/zben.js", diffPath + "government-zben-js-war-04-diff.txt")) {
			return false;
		}
		
		
		
		if (!diff("/e-payment-government/src/main/resources/application.properties", "/e-payment-government/src/main/resources/conf/dev/application.properties", diffPath + "government-application-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-government/src/main/resources/application.properties", "/e-payment-government/src/main/resources/conf/prod/application.properties", diffPath + "government-application-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-government/src/main/resources/application.properties", "/e-payment-government/src/main/resources/conf/test/application.properties", diffPath + "government-application-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-government/src/main/resources/application.properties", "/e-payment-tools-war/src/main/resources/conf/04/gov/application.properties", diffPath + "government-application-properties-war-04-diff.txt")) {
			return false;
		}
		return true;
	}
	
	private static boolean doconsumer() {
		if (!diff("/e-payment-consumer/src/main/resources/static/js/zben.js", "/e-payment-consumer/src/main/resources/conf/dev/zben.js", diffPath + "consumer-zben-js-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-consumer/src/main/resources/static/js/zben.js", "/e-payment-consumer/src/main/resources/conf/prod/zben.js", diffPath + "consumer-zben-js-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-consumer/src/main/resources/static/js/zben.js", "/e-payment-consumer/src/main/resources/conf/test/zben.js", diffPath + "consumer-zben-js-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-consumer/src/main/resources/static/js/zben.js", "/e-payment-tools-war/src/main/resources/conf/04/consumer/zben.js", diffPath + "consumer-zben-js-war-04-diff.txt")) {
			return false;
		}
		
		
		
		if (!diff("/e-payment-consumer/src/main/resources/application.properties", "/e-payment-consumer/src/main/resources/conf/dev/application.properties", diffPath + "consumer-application-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-consumer/src/main/resources/application.properties", "/e-payment-consumer/src/main/resources/conf/prod/application.properties", diffPath + "consumer-application-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-consumer/src/main/resources/application.properties", "/e-payment-consumer/src/main/resources/conf/test/application.properties", diffPath + "consumer-application-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-consumer/src/main/resources/application.properties", "/e-payment-tools-war/src/main/resources/conf/04/consumer/application.properties", diffPath + "consumer-application-properties-war-04-diff.txt")) {
			return false;
		}
		

		if (!diff("/e-payment-consumer/pom.xml", "/e-payment-consumer/src/main/resources/conf/prod/pom.xml", diffPath + "consumer-pom-xml-conf-prod-diff.txt")) {
			return false;
		}
		return true;
	}
	private static boolean doboss() {
		if (!diff("/e-payment-boss/src/main/resources/static/js/zben.js", "/e-payment-boss/src/main/resources/conf/dev/zben.js", diffPath + "boss-zben-js-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-boss/src/main/resources/static/js/zben.js", "/e-payment-boss/src/main/resources/conf/prod/zben.js", diffPath + "boss-zben-js-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-boss/src/main/resources/static/js/zben.js", "/e-payment-boss/src/main/resources/conf/test/zben.js", diffPath + "boss-zben-js-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-boss/src/main/resources/static/js/zben.js", "/e-payment-tools-war/src/main/resources/conf/04/boss/zben.js", diffPath + "boss-zben-js-war-04-diff.txt")) {
			return false;
		}
		
		
		
		if (!diff("/e-payment-boss/src/main/resources/application.properties", "/e-payment-boss/src/main/resources/conf/dev/application.properties", diffPath + "boss-application-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-boss/src/main/resources/application.properties", "/e-payment-boss/src/main/resources/conf/prod/application.properties", diffPath + "boss-application-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-boss/src/main/resources/application.properties", "/e-payment-boss/src/main/resources/conf/test/application.properties", diffPath + "boss-application-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-boss/src/main/resources/application.properties", "/e-payment-tools-war/src/main/resources/conf/04/boss/application.properties", diffPath + "boss-application-properties-war-04-diff.txt")) {
			return false;
		}
		return true;
	}
	private static boolean doapi() {
		if (!diff("/e-payment-api/src/main/resources/application.properties", "/e-payment-api/src/main/resources/conf/dev/application.properties", diffPath + "api-application-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/application.properties", "/e-payment-api/src/main/resources/conf/prod/application.properties", diffPath + "api-application-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/application.properties", "/e-payment-api/src/main/resources/conf/test/application.properties", diffPath + "api-application-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/application.properties", "/e-payment-tools-war/src/main/resources/conf/01/application.properties", diffPath + "api-application-properties-war-01-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/application.properties", "/e-payment-tools-war/src/main/resources/conf/02/application.properties", diffPath + "api-application-properties-war-02-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/application.properties", "/e-payment-tools-war/src/main/resources/conf/03/application.properties", diffPath + "api-application-properties-war-03-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/application.properties", "/e-payment-tools-war/src/main/resources/conf/04/api/application.properties", diffPath + "api-application-properties-war-04-diff.txt")) {
			return false;
		}

		if (!diff("/e-payment-api/src/main/resources/logback.xml", "/e-payment-api/src/main/resources/conf/dev/logback.xml", diffPath + "api-logback-xml-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/logback.xml", "/e-payment-api/src/main/resources/conf/prod/logback.xml", diffPath + "api-logback-xml-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/logback.xml", "/e-payment-api/src/main/resources/conf/test/logback.xml", diffPath + "api-logback-xml-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/logback.xml", "/e-payment-tools-war/src/main/resources/conf/01/logback.xml", diffPath + "api-logback-xml-war-01-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/logback.xml", "/e-payment-tools-war/src/main/resources/conf/02/logback.xml", diffPath + "api-logback-xml-war-02-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/logback.xml", "/e-payment-tools-war/src/main/resources/conf/03/logback.xml", diffPath + "api-logback-xml-war-03-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/logback.xml", "/e-payment-tools-war/src/main/resources/conf/04/api/logback.xml", diffPath + "api-logback-xml-war-04-diff.txt")) {
			return false;
		}

		if (!diff("/e-payment-api/src/main/resources/quartz.properties", "/e-payment-api/src/main/resources/conf/dev/quartz.properties", diffPath + "api-quartz-properties-conf-dev-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/quartz.properties", "/e-payment-api/src/main/resources/conf/prod/quartz.properties", diffPath + "api-quartz-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/quartz.properties", "/e-payment-api/src/main/resources/conf/test/quartz.properties", diffPath + "api-quartz-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/quartz.properties", "/e-payment-tools-war/src/main/resources/conf/04/api/quartz.properties", diffPath + "api-quartz-properties-war-04-diff.txt")) {
			return false;
		}

		if (!diff("/e-payment-api/src/main/resources/email-alarm.properties", "/e-payment-api/src/main/resources/conf/prod/email-alarm.properties", diffPath + "api-email-alarm-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/email-alarm.properties", "/e-payment-api/src/main/resources/conf/test/email-alarm.properties", diffPath + "api-email-alarm-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/email-alarm.properties", "/e-payment-tools-war/src/main/resources/conf/04/api/email-alarm.properties", diffPath + "api-email-alarm-properties-war-04-diff.txt")) {
			return false;
		}

		if (!diff("/e-payment-api/src/main/resources/email-epay.properties", "/e-payment-api/src/main/resources/conf/prod/email-epay.properties", diffPath + "api-email-epay-properties-conf-prod-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/email-epay.properties", "/e-payment-api/src/main/resources/conf/test/email-epay.properties", diffPath + "api-email-epay-properties-conf-test-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/email-epay.properties", "/e-payment-tools-war/src/main/resources/conf/04/api/email-epay.properties", diffPath + "api-email-epay-properties-war-04-diff.txt")) {
			return false;
		}

		if (!diff("/e-payment-api/pom.xml", "/e-payment-api/src/main/resources/conf/prod/pom.xml", diffPath + "api-pom-xml-conf-dev-diff.txt")) {
			return false;
		}
		

		if (!diff("/e-payment-api/src/main/resources/DJHsmAPILinux.ini", "/e-payment-tools-war/src/main/resources/conf/01/DJHsmAPILinux.ini", diffPath + "api-DJHsmAPILinux-ini-war-01-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/DJHsmAPILinux.ini", "/e-payment-tools-war/src/main/resources/conf/02/DJHsmAPILinux.ini", diffPath + "api-DJHsmAPILinux-ini-war-02-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/DJHsmAPILinux.ini", "/e-payment-tools-war/src/main/resources/conf/03/DJHsmAPILinux.ini", diffPath + "api-DJHsmAPILinux-ini-war-03-diff.txt")) {
			return false;
		}
		if (!diff("/e-payment-api/src/main/resources/DJHsmAPILinux.ini", "/e-payment-tools-war/src/main/resources/conf/04/api/DJHsmAPILinux.ini", diffPath + "api-DJHsmAPILinux-ini-war-04-diff.txt")) {
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
		String path = System.getProperty("user.dir").replaceAll("e-payment-tools-war", "");
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

package com.zbensoft.mmsmp.tools.app;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class AppBuyerPayErrorCodeCheck {
	public static void main(String[] args) {
		check();

	}

	public static void check() {
		String httpRestStatusFile = System.getProperty("user.dir").replaceAll("e-payment-tools", "e-payment-api") + "\\src\\main\\java\\com\\zbensoft\\e\\payment\\api\\common\\HttpRestStatus.java";
		String toastUtilFile = "C:\\workspace\\android\\Consumer\\app\\src\\main\\java\\com\\huafu\\consumer\\util\\ToastUtil.java";

		List<String> lista = getList(httpRestStatusFile);
		List<String> listb = getList(toastUtilFile);
		boolean ispay = false;
		for (int i = 0; i < lista.size(); i++) {
			String line = lista.get(i);
			if (line.trim().startsWith("// pay start")) {
				ispay = true;
				continue;
			}
			if (line.trim().startsWith("// pay end")) {
				break;
			}
			if (ispay) {
				if (!line.trim().startsWith("PAY_")) {
					System.err.println(AppBuyerPayErrorCodeCheck.class.getSimpleName() + " AppBuyerPayErrorCodeChecke-payment-api HttpRestStatus defined error:" + line.trim());
				}
				String messageCode = line.trim().substring(0, line.trim().indexOf("("));
				if (!haveMessage(messageCode, listb)) {
					System.err.println(AppBuyerPayErrorCodeCheck.class.getSimpleName() + " ToastUtil not process:" + line.trim());
				}
			}
		}
		
	}

	private static boolean haveMessage(String messageCode, List<String> listb) {
		for (String line : listb) {
			if (line.contains("\"" + messageCode + "\".equals(code)")) {
				return true;
			}
		}
		return false;
	}

	public static List<String> getList(String path) {
		try {
			return FileUtils.readLines(new File(path), "UTF-8");
		} catch (IOException e) {
			return null;
		}
	}
}

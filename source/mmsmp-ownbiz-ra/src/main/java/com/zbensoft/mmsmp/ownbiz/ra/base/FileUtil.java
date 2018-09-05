package com.zbensoft.mmsmp.ownbiz.ra.base;

import java.io.File;
import java.io.PrintStream;

public class FileUtil {
	public static void main(String[] args) {
		File file = new File("E:/Workspaces/CX3/MmsCorebiz/lib");
		File[] files = file.listFiles();
		for (File file2 : files)
			System.out.println("$APP_LIB/" + file2.getName() + ":\\");
	}
}

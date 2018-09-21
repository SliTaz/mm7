package com.zbensoft.mmsmp.ftp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;

public class FilesHelper {
    private static final Logger logger = Logger.getLogger(FilesHelper.class);

    public FilesHelper() {
    }

    public static void writeFile(File file_name, String contents) throws Exception {
        writeFile(file_name, contents, "UTF-8");
    }

    public static void writeFile(File file_name, String contents, String encoding) throws Exception {
        try {
            FileOutputStream stream = new FileOutputStream(file_name);
            stream.write(contents.getBytes(encoding));
            stream.close();
        } catch (IOException var4) {
            logger.error("writeFile(String, String) - exception error", var4);
            throw var4;
        }
    }

    public static boolean backupFile(String old_file, String new_file) {
        return backupFile(new File(old_file), new File(new_file));
    }

    public static boolean backupFile(File old_file, File new_file) {
        return old_file.renameTo(new_file);
    }

    public static boolean backupOrderFile(File old_file, File new_file) {
        try {
            FileInputStream fis = new FileInputStream(old_file);
            FileOutputStream fos = new FileOutputStream(new_file);
            byte[] byteArr = new byte[2048];
            boolean var5 = false;

            int byteLen;
            while((byteLen = fis.read(byteArr, 0, byteArr.length)) != -1) {
                fos.write(byteArr, 0, byteLen);
            }

            fos.close();
            fis.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return true;
    }
}

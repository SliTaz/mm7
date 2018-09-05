package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteReport {
    private static final String own_repot_path = PropertiesUtil.get("common", "own_report_path");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public WriteReport() {
    }

    public static void toWrite(String xml) {
        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(own_repot_path + sdf.format(new Date()) + ".txt", true);
            pw = new PrintWriter(fw);
            pw.println(xml);
        } catch (Exception var12) {
            var12.printStackTrace();
        } finally {
            try {
                pw.close();
                fw.close();
            } catch (IOException var11) {
                var11.printStackTrace();
            }

        }

    }
}

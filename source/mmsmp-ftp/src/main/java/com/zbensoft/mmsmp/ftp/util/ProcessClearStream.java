package com.zbensoft.mmsmp.ftp.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessClearStream extends Thread {
    private InputStream inputStream;
    private String type;

    ProcessClearStream(InputStream inputStream, String type) {
        this.inputStream = inputStream;
        this.type = type;
    }

    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(this.inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = null;

            while ((line = br.readLine()) != null) {
                System.out.println(this.type + ">" + line);
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }
}

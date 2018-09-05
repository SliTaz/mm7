package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImgeUtil {
    public ImgeUtil() {
    }

    public static File createValidateFile(String validateCode, String filePath) {
        File file = new File(filePath);
        BufferedImage image = new BufferedImage(60, 25, 1);
        Graphics g = image.getGraphics();
        g.setColor(new Color(100, 100, 100));
        g.setFont(new Font("times new roman", 2, 20));
        g.fillRect(0, 0, 60, 25);
        g.setColor(Color.BLUE);
        g.drawString(validateCode, 6, 19);
        g.dispose();

        try {
            ImageIO.write(image, "jpeg", file);
            return file;
        } catch (IOException var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        createValidateFile("1234", "D:/123.jpeg");
    }

    public static String getImageStr(File file) {
        InputStream in = null;
        byte[] data = (byte[])null;

        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static boolean generateImage(String imgStr, String imgFilePath) {
        if (imgStr == null) {
            return false;
        } else {
            BASE64Decoder decoder = new BASE64Decoder();

            try {
                byte[] b = decoder.decodeBuffer(imgStr);

                for(int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {
                        b[i] = (byte)(b[i] + 256);
                    }
                }

                OutputStream out = new FileOutputStream(imgFilePath);
                out.write(b);
                out.flush();
                out.close();
                return true;
            } catch (Exception var5) {
                return false;
            }
        }
    }
}

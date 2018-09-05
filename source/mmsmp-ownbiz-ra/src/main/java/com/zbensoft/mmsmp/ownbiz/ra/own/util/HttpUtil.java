package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {
    private static Logger _log = Logger.getLogger(HttpUtil.class);

    public HttpUtil() {
    }

    public static void connectUrl(String strUrl) throws IOException {
        URL url = new URL(strUrl);
        URLConnection con = (HttpURLConnection)url.openConnection();
        con.connect();
    }

    public static String accessUrl(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection con = null;
        BufferedReader in = null;
        String strResponse = "";

        try {
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-agent", "IE/6.0");

            String strInputLine;
            for(in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); (strInputLine = in.readLine()) != null; strResponse = strResponse + strInputLine + "\n") {
                ;
            }

            _log.info("接受返回消息：\n" + strResponse + ":" + con.getResponseCode() + "：" + con.getResponseMessage());
        } catch (Exception var18) {
            _log.error(var18);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception var17) {
                ;
            }

            try {
                if (con != null) {
                    con.disconnect();
                    con = null;
                }
            } catch (Exception var16) {
                ;
            }

        }

        return strResponse.trim();
    }

    public static int notifySP(String urlAddress, byte[] bypes) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection con = null;
        BufferedReader in = null;
        String strResponse = "";
        int respCode = 0;

        try {
            con = (HttpURLConnection)url.openConnection();
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-agent", "IE/6.0");
            con.setDoOutput(true);
            con.getOutputStream().write(bypes);
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            for(String strInputLine = ""; (strInputLine = in.readLine()) != null; strResponse = strResponse + strInputLine + "\n") {
                ;
            }

            respCode = con.getResponseCode();
            _log.info("接受返回消息：\n" + strResponse + ":接收传送状态" + con.getResponseCode());
        } catch (Exception var20) {
            _log.error(var20);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception var19) {
                ;
            }

            try {
                if (con != null) {
                    con.disconnect();
                    con = null;
                }
            } catch (Exception var18) {
                ;
            }

        }

        return respCode;
    }

    public static String getMethod(String strurl) {
        URL url = null;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer sb = null;

        try {
            url = new URL(strurl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuffer("");

            String lines;
            while((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
        } catch (IOException var18) {
            _log.error(String.format("请求连接%s 失败，错误信息%s", strurl, var18.getMessage()));
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (Exception var17) {
                    ;
                }
            }

            if (connection != null) {
                try {
                    connection.disconnect();
                    connection = null;
                } catch (Exception var16) {
                    ;
                }
            }

        }

        return sb != null ? sb.toString().replaceAll("\r", "").replaceAll("\n", "") : null;
    }
}

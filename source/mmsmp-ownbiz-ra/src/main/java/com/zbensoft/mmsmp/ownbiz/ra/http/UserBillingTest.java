package com.zbensoft.mmsmp.ownbiz.ra.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserBillingTest {
	public static void main(String[] args) {
		String urlStr = "http://cmpay.dalasu.com:8883/ChinaUnicomFS/ResultNotice?messageId=PPSe48e99d44c6e49d68d30216125854d74&payStatus=0&Content=c0cd65a2aeb574b14b6fd7aa029dace6";
		try {
			URL url = new URL(urlStr);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-agent", "IE/6.0");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			String strResponse = "";
			String strInputLine;
			while ((strInputLine = in.readLine()) != null) {
				strResponse = strResponse + strInputLine + "\n";
			}
			in.close();

			System.out.println(strResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.zbensoft.mmsmp.ownbiz.ra.http;

import com.zbensoft.mmsmp.ownbiz.ra.own.util.ValidateMessageUtil;
//import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendValidateCodeHttpClient {
	public static void main(String[] args) {
//		String urlStr = "http://localhost:80/MmsOwnbiz/getVerificationCode";
//		try {
//			URL url = new URL(urlStr);
//
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setRequestMethod("POST");
//			con.setRequestProperty("User-agent", "IE/6.0");
//			con.setRequestProperty("mmsp-account-id", "123");
//			con.setRequestProperty("mmsp-user-number", "13012341234");
//			StringBuffer sb = new StringBuffer();
//			sb.append("order").append("123").append("13012341234");
//			String content = ValidateMessageUtil.getMD5String(sb.toString());
//			con.setRequestProperty("Content", content);
//			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
//
//			String strResponse = "";
//			String strInputLine;
//			while ((strInputLine = in.readLine()) != null) {
//				strResponse = strResponse + strInputLine + "\n";
//			}
//			in.close();
//			JSONObject obj = JSONObject.fromObject(strResponse);
//			System.out.println(obj.get("returnCode"));
//			System.out.println(obj.get("returnDesc"));
//			if (obj.get("returnCode").toString().equals("200")) {
//				System.out.println(obj.get("messageId"));
//			}
//			System.out.println(strResponse);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}

package com.zbensoft.mmsmp.common.ra.aaa.util;

import com.zbensoft.mmsmp.common.ra.utils.PropertiesHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import org.apache.log4j.Logger;

public class CheckXml {
	private static final Logger logger = Logger.getLogger(CheckXml.class);

	public static CheckResponse GetAAAResponse(CheckRequest re) {
		CheckResponse res = new CheckResponse();
		try {
			String url = CreateUrl(re);
			logger.info("\n\r=====>Get this url:" + CreateUrl(re));
			String returnstr = accessUrl(url);

			res = CheckResponse.CheckResponseForXml(returnstr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static String XmlForObject(CheckResponse re) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"gbk\"?>\n<returnmessage>\n");
		sb.append("<LinkID>" + re.getLinkID() + "</LinkID>\n");
		sb.append("<NeedConfirm>" + re.getNeedConfirm() + "</NeedConfirm>\n");
		sb.append("<ProductID>" + re.getProductID() + "</ProductID>\n");
		sb.append("<Result_Code>" + re.getResult_Code() + "</Result_Code>\n");
		sb.append("<ReturnMessage>" + re.getReturnMessage() + "</ReturnMessage>\n");
		sb.append("<SP_ProductID>" + re.getSP_ProductID() + "</SP_ProductID>\n");
		sb.append("<SPEC_ProductID>" + re.getSPEC_ProductID() + "</SPEC_ProductID>\n");
		sb.append("<src_SequenceNumber>" + re.getSrc_SequenceNumber() + "</src_SequenceNumber>\n");
		sb.append("<serverResult_code>" + re.getServerResult_code() + "</serverResult_code>\n");
		sb.append("<serverMessage>" + re.getServerMessage() + "</serverMessage>\n");
		sb.append("</returnmessage>");
		return sb.toString();
	}

	public static String ObjectToUrl(CheckRequest re) {
		StringBuffer url = new StringBuffer();
		String serviceType = re.getServiceType();
		if (serviceType == null) {
			serviceType = "";
		}
		url.append("serviceType=" + serviceType);

		String spid = re.getSp_id();
		if (spid == null) {
			spid = "";
		}
		url.append("&spid=" + spid);

		String sp_product_id = re.getSp_product_id();
		if (sp_product_id == null) {
			sp_product_id = "";
		}
		url.append("&sp_product_id=" + sp_product_id);

		String src_SequenceNumber = re.getSrc_SequenceNumber();
		if (src_SequenceNumber == null) {
			src_SequenceNumber = "";
		}
		url.append("&src_SequenceNumber=" + src_SequenceNumber);

		String user_number = re.getUser_number();
		if (user_number == null) {
			user_number = "";
		}
		url.append("&user_number=" + user_number);

		String service_id = re.getService_id();
		if (service_id == null) {
			service_id = "";
		}
		url.append("&service_id=" + service_id);

		String linkid = re.getLinkID();
		if (linkid == null) {
			linkid = "";
		}
		url.append("&linkid=" + linkid);

		return url.toString();
	}

	public static String accessUrl(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(10000);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("User-agent", "IE/6.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));

		String response = "";
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			//String inputLine;
			response = response + inputLine + "\n";
		}
		in.close();
		logger.info("\n\r接受AAA Servlet 返回消息：\n" + response);
		return response.trim();
	}

	public static String CreateUrl(CheckRequest Pare) {
		String sb = "";
		String userphone = Pare.getUser_number();

		String httpheadurl = PropertiesHelper.getString("vac.server.url", null);

		return httpheadurl + "?" + ObjectToUrl(Pare);
	}

	public static void connectUrl(String strUrl) throws IOException {
		URL url = new URL(strUrl);
		URLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
	}

	public static void main(String[] args) throws IOException {
	}
}

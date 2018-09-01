package com.zbensoft.mmsmp.common.ra.wo.order;

import com.zbensoft.mmsmp.common.ra.wo.util.PropertiesHelper;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class UserOrderClient {
	private static final Logger logger = Logger.getLogger(UserOrderClient.class);

	public static String userOrder(String cellphonenumber, String sp_productid, Double ondemandfee, String feetype, String servicename) {
		UserOrderRequest userOrderRequest = new UserOrderRequest();

		userOrderRequest.setService_code("ISIP02003");
		userOrderRequest.setSvcnum(cellphonenumber);
		userOrderRequest.setId_type("2");
		userOrderRequest.setSpno(PropertiesHelper.getString("spno", "0042"));
		logger.info("userOrderRequest.getSpno() is : " + userOrderRequest.getSpno());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		userOrderRequest.setReqdate(format.format(new Date()));

		userOrderRequest.setPay_type("20");

		userOrderRequest.setPay_money(ondemandfee.intValue()+"");

		userOrderRequest.setRes_id(sp_productid);
		userOrderRequest.setRes_name(servicename);

		int fee = Integer.parseInt(userOrderRequest.getPay_money());
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		if (feetype.equals("2")) {
			userOrderRequest.setPay_desc(decimalFormat.format(fee / 100.0D) + "元/次");
		}
		if (feetype.equals("2"))
			userOrderRequest.setOrder_type("1");
		else if (feetype.equals("1")) {
			userOrderRequest.setOrder_type("2");
		}

		StringBuffer buffer = new StringBuffer("");
		buffer.append(userOrderRequest.getService_code()).append("|");
		buffer.append(userOrderRequest.getSvcnum()).append("|");
		buffer.append(userOrderRequest.getSpno()).append("|");
		buffer.append(userOrderRequest.getReqdate()).append("|");
		buffer.append(userOrderRequest.getId_type()).append("|");
		buffer.append(userOrderRequest.getPay_type()).append("|");
		buffer.append(userOrderRequest.getPay_money()).append("|");
		buffer.append(userOrderRequest.getRes_id()).append("|");
		buffer.append(userOrderRequest.getRes_name()).append("|");
		buffer.append(userOrderRequest.getPay_desc()).append("|");
		buffer.append(userOrderRequest.getOrder_type());
		logger.info("没有加密的ChkValue is : " + buffer.toString());
		try {
			userOrderRequest.setChkValue(Sign.getSign(buffer.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ret = userOrder(userOrderRequest);
		UserOrderResponse response = UserOrderResponse.UserOrderResponseForXml(ret);
		return response.getFlag();
	}

	public static void main(String[] args) {
		userOrder("15601003396", "3100204702", Double.valueOf(0.0D), "2", "测试");
	}

	public static String userOrder(UserOrderRequest request) {
		String url = "http://iphone.wo.com.cn/services/httpxml/integration";
		logger.info("url is : " + url);
		Document document = UserOrderXml.createDocument(request);

		HttpURLConnection httpURL = null;
		OutputStreamWriter dos = null;
		try {
			String out_content = doc2String(document);
			System.out.println(out_content);

			URL theURL = new URL(url);
			httpURL = (HttpURLConnection) theURL.openConnection();
			httpURL.setDoInput(true);
			httpURL.setDoOutput(true);
			httpURL.setRequestMethod("POST");
			httpURL.setRequestProperty("content-type", "text/xml");

			System.out.println(document.asXML().replace("\n", "").replace("(", "（").replace(")", "）"));
			dos = new OutputStreamWriter(new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream())), "UTF-8");
			dos.write(document.asXML().replace("\n", ""));
			dos.flush();
			String ret = "";

			if (httpURL.getResponseCode() == 200) {
				DataInputStream dis = new DataInputStream(httpURL.getInputStream());
				int len = dis.available();
				byte[] buf = new byte[len];
				int r = dis.read(buf);
				int pos = r;
				while (pos < len) {
					r = dis.read(buf, pos, len - pos);
					pos += r;
				}
				ret = new String(buf, "UTF-8");
				logger.info(ret);

				return ret;
			}
			logger.info("wo门户error，返回的状态码为：" + httpURL.getResponseCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String doc2String(Document document) {
		String s = "";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}
}

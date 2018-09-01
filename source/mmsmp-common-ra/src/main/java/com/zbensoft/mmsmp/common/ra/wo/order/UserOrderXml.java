package com.zbensoft.mmsmp.common.ra.wo.order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class UserOrderXml {
	private static final Logger logger = Logger.getLogger(UserOrderXml.class);

	public static String XmlForObject(UserOrderResponse re) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
		buffer.append("<interface-result> \n");
		buffer.append("\t<head> \n");
		buffer.append("\t\t<flag>" + re.getFlag() + "</flag> \n");
		buffer.append("\t\t<detail>" + re.getDetail() + "</detail> \n");
		buffer.append("\t\t<recordsn>" + re.getRecordsn() + "</recordsn> \n");
		buffer.append("\t\t<service_code>" + re.getService_code() + "</service_code> \n");
		buffer.append("\t\t<svcnum>" + re.getSvcnum() + "</svcnum> \n");
		buffer.append("\t\t<sys_sequence>" + re.getSys_sequence() + "</sys_sequence> \n");
		buffer.append("\t\t<resp_date>" + re.getResp_date() + "</resp_date> \n");
		buffer.append("\t</head> \n");
		buffer.append("\t<content> \n");
		buffer.append("\t\t<record> \n");
		buffer.append("\t\t\t<pay_money>" + re.getPay_money() + "</pay_money> \n");
		buffer.append("\t\t\t<pay_type>" + re.getPay_type() + "</pay_type> \n");
		buffer.append("\t\t\t<sp_OrderId>" + re.getSp_OrderId() + "</sp_OrderId> \n");
		buffer.append("\t\t</record> \n");
		buffer.append("\t</content> \n");
		buffer.append("</interface-result>\n");
		return buffer.toString();
	}

	public static Document createDocument(UserOrderRequest userOrderRequest) {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element root_element = document.addElement("interface-param");
		Element head_element = root_element.addElement("head");

		Element service_code_element = head_element.addElement("service_code");
		service_code_element.setText(userOrderRequest.getService_code());

		Element svcnum_element = head_element.addElement("svcnum");
		svcnum_element.setText(userOrderRequest.getSvcnum());

		Element id_type_element = head_element.addElement("type");
		id_type_element.setText(userOrderRequest.getId_type());

		Element spno_element = head_element.addElement("spno");
		spno_element.setText(userOrderRequest.getSpno());

		Element recordsn_element = head_element.addElement("recordsn");
		recordsn_element.setText(userOrderRequest.getRecordsn() == null ? "" : userOrderRequest.getRecordsn());

		Element reqdate_element = head_element.addElement("reqdate");
		reqdate_element.setText(userOrderRequest.getReqdate());

		Element wopriv_element = head_element.addElement("wopriv");
		wopriv_element.setText(userOrderRequest.getWopriv() == null ? "" : userOrderRequest.getWopriv());

		Element ChkValue_element = head_element.addElement("ChkValue");
		ChkValue_element.setText(userOrderRequest.getChkValue());

		Element content_element = root_element.addElement("content");

		Element pay_type_element = content_element.addElement("pay_type");
		pay_type_element.setText(userOrderRequest.getPay_type());

		Element pay_money_element = content_element.addElement("pay_money");
		pay_money_element.setText(userOrderRequest.getPay_money());

		Element res_id_element = content_element.addElement("res_id");
		res_id_element.setText(userOrderRequest.getRes_id());

		Element res_name_element = content_element.addElement("res_name");
		res_name_element.setText(userOrderRequest.getRes_name() == null ? "" : userOrderRequest.getRes_name());

		Element pay_desc_element = content_element.addElement("pay_desc");
		pay_desc_element.setText(userOrderRequest.getPay_desc() == null ? "" : userOrderRequest.getPay_desc());

		Element res_url_element = content_element.addElement("res_url");
		res_url_element.setText(userOrderRequest.getRes_url() == null ? "" : userOrderRequest.getRes_url());

		Element Order_type_element = content_element.addElement("Order_type");
		Order_type_element.setText(userOrderRequest.getOrder_type());

		return document;
	}

	public static UserOrderRequest parseDocument(String content) {
		UserOrderRequest userOrderRequest = new UserOrderRequest();
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(new StringReader(content), "UTF-8");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.setXMLEncoding("UTF-8");
		Node root_node = document.selectSingleNode("interface-param");
		Node head_node = root_node.selectSingleNode("head");

		Node service_code_node = head_node.selectSingleNode("service_code");
		userOrderRequest.setService_code(service_code_node.getText());

		Node svcnum_node = head_node.selectSingleNode("svcnum");
		userOrderRequest.setSvcnum(svcnum_node.getText());

		Node type_node = head_node.selectSingleNode("type");
		userOrderRequest.setId_type(type_node.getText());

		Node spno_node = head_node.selectSingleNode("spno");
		userOrderRequest.setSpno(spno_node.getText());

		Node recordsn_node = head_node.selectSingleNode("recordsn");
		userOrderRequest.setRecordsn(recordsn_node.getText());

		Node reqdate_node = head_node.selectSingleNode("reqdate");
		userOrderRequest.setReqdate(reqdate_node.getText());

		Node wopriv_node = head_node.selectSingleNode("wopriv");
		userOrderRequest.setWopriv(wopriv_node.getText());

		Node ChkValue_node = head_node.selectSingleNode("ChkValue");
		userOrderRequest.setChkValue(ChkValue_node.getText());

		Node content_node = root_node.selectSingleNode("content");

		Node pay_type_node = content_node.selectSingleNode("pay_type");
		userOrderRequest.setPay_type(pay_type_node.getText());

		Node pay_money_node = content_node.selectSingleNode("pay_money");
		userOrderRequest.setPay_money(pay_money_node.getText());

		Node res_id_node = content_node.selectSingleNode("res_id");
		userOrderRequest.setRes_id(res_id_node.getText());

		Node res_name_node = content_node.selectSingleNode("res_name");
		userOrderRequest.setRes_name(res_name_node.getText());

		Node pay_desc_node = content_node.selectSingleNode("pay_desc");
		userOrderRequest.setPay_desc(pay_desc_node.getText());

		Node res_url_node = content_node.selectSingleNode("res_url");
		userOrderRequest.setRes_url(res_url_node.getText());

		Node Order_type_node = content_node.selectSingleNode("Order_type");
		userOrderRequest.setOrder_type(Order_type_node.getText());

		return userOrderRequest;
	}

	public static String accessUrl(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(60000);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("User-agent", "IE/6.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		String response = "";
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
//			String inputLine;
			response = response + inputLine + "\n";
		}
		in.close();
		logger.info("接受AAA Servlet 返回消息：\n" + response);
		return response.trim();
	}

	public static void connectUrl(String strUrl) throws IOException {
		URL url = new URL(strUrl);
		URLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
	}
}

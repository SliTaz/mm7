package com.zbensoft.mmsmp.common.ra.mms.xmlutil.service.impl;

import com.zbensoft.mmsmp.common.ra.mms.xmlutil.service.VaspXmlService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class VaspXmlServiceImpl implements VaspXmlService {

	private String spIpConfigFilePath;

	public void delete(String[] spIds) throws DocumentException, IOException {
		Document document = getDocument();
		for (String spId : spIds) {
			Element element = getElement(document, spId);

			if (element != null) {
				Element root = document.getRootElement();
				root.remove(element);
			}
		}
		save(document);
	}

	public String[] getSp(String spId) throws DocumentException, IOException {
		String[] result = (String[]) null;

		Document document = getDocument();
		Node node = getNode(document, spId);
		if (node != null) {
			result = new String[] { node.valueOf("@ip"), node.valueOf("@senderAddress") };
		}

		return result;
	}

	public String getSpIpConfigFilePath() {
		return this.spIpConfigFilePath;
	}

	public void saveOrUpdate(String spId, String ip, String senderAddress) throws DocumentException, IOException {
		Document document = getDocument();
		Element element = getElement(document, spId);

		if (element != null) {
			element.attribute("ip").setValue(ip);
			element.attribute("senderAddress").setValue(senderAddress);
		} else {
			Element root = document.getRootElement();
			Element newElement = root.addElement("sp");
			newElement.addAttribute("id", spId);
			newElement.addAttribute("ip", ip);
			newElement.addAttribute("senderAddress", senderAddress);
		}

		save(document);
	}

	public void delete(String spId) throws DocumentException, IOException {
		String[] spIds = { spId };
		delete(spIds);
	}

	public void setSpIpConfigFilePath(String spIpConfigFilePath) {
		this.spIpConfigFilePath = spIpConfigFilePath;
	}

	public String getIP(String spId) throws DocumentException {
		String result = null;

		Document document = getDocument();
		Node node = getNode(document, spId);
		if (node != null) {
			result = node.valueOf("@ip");
		}

		return result;
	}

	private void save(Document document) throws DocumentException, IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileWriter(new File(this.spIpConfigFilePath)), format);
		writer.write(document);
		writer.close();
	}

	private Document getDocument() throws DocumentException {
		File file = new File(this.spIpConfigFilePath);
		SAXReader reader = new SAXReader();
		return reader.read(file);
	}

	private Node getNode(Document document, String spId) throws DocumentException {
		Node result = null;
		result = document.selectSingleNode("//sp[@id='" + spId + "']");
		return result;
	}

	private Element getElement(Document document, String spId) throws DocumentException {
		Element result = null;

		Node node = getNode(document, spId);
		if (node != null) {
			result = (Element) node;
		}

		return result;
	}
}

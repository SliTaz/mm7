package com.cmcc.mm7.vasp.conf;

import java.util.HashMap;

import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.MM7Config;

public class MM7ConfigManager {

	public HashMap hashmap = new HashMap();

	public void load(String configFileName) {
		this.hashmap.clear();
		try {
			this.hashmap = readXMLFile(configFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HashMap readXMLFile(String inFile) throws Exception {
		this.hashmap.clear();
		MM7Config mm7Config = ConfigUtil.getInstance().getMM7Config();
		System.out.println(mm7Config);
		this.hashmap.putAll(mm7Config.toHashMap());
		return this.hashmap;
	}

	public static void main(String[] args) {
		MM7ConfigManager mm7c = new MM7ConfigManager();
		mm7c.load("mm7Config.xml");
	}
}

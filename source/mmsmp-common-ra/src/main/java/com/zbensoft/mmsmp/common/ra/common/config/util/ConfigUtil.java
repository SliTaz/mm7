package com.zbensoft.mmsmp.common.ra.common.config.util;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.AdminConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.AgentConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.CMPPConnect;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.CMPPSubmitMessage;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.CommonConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.Config;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.ContentInfoStates;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.FtpScanClient;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.MM7Config;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.MmsEditor;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.SGIPConnect;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.SgipSubmit;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.WapConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.WapOampConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.WebConfig;
import com.zbensoft.mmsmp.common.ra.vas.xml4j.Xml4jHandler;

public class ConfigUtil {
	private static Xml4jHandler handler;
	private static Config config;
	private static ConfigUtil configUtil = new ConfigUtil();

	public static ConfigUtil getInstance() {
		return configUtil;
	}

	public FtpScanClient getFtpScanClient() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getFtpScanClient();
	}

	public WapOampConfig getWapOampConfig() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getWapOampConfig();
	}

	public ContentInfoStates getContentInfoStates() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getContentInfoStates();
	}

	public CorebizConfig getCorebizConfig() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getCorebizConfig();
	}

	public WebConfig getWebConfig() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getWebConfig();
	}

	public WapConfig getWapConfig() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getWapConfig();
	}

	public AdminConfig getAdminConfig() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getAdminConfig();
	}

	public AgentConfig getAgentConfig() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getAgentConfig();
	}

	public CommonConfig getCommonConfig() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getCommonConfig();
	}

	public MmsEditor getMmsEditor() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getMmsEditor();
	}

	public CMPPConnect getCMPPConnect() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getCMPPConnect();
	}

	public CMPPSubmitMessage getCMPPSubmitMessage() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getCMPPSubmitMessage();
	}

	public SGIPConnect getSGIPConnect() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getSGIPConnect();
	}

	public SgipSubmit getSgipSubmit() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getSgipSubmit();
	}

	public MM7Config getMM7Config() {
		if (config == null) {
			if (handler == null) {
				handler = new Xml4jHandler("config.xml", "com.zbensoft.mmsmp.common.ra.common.config.configbean");
			}
			config = (Config) handler.getRootConfigObject();
		}
		return config.getMM7Config();
	}

	public static void main(String[] args) {
		System.out.println(getInstance().getSgipSubmit());
		System.out.println(getInstance().getSGIPConnect());
	}
}

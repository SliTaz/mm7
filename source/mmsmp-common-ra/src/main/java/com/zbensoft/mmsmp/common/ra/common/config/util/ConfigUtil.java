/*     */ package com.zbensoft.mmsmp.common.ra.common.config.util;
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

/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class ConfigUtil
/*     */ {
/*     */   private static Xml4jHandler handler;
/*     */   private static Config config;
/*  31 */   private static ConfigUtil configUtil = new ConfigUtil();
/*     */ 
/*     */   public static ConfigUtil getInstance()
/*     */   {
/*  37 */     return configUtil;
/*     */   }
/*     */ 
/*     */   public FtpScanClient getFtpScanClient()
/*     */   {
/*  46 */     if (config == null) {
/*  47 */       if (handler == null) {
/*  48 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/*  50 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/*  52 */     return config.getFtpScanClient();
/*     */   }
/*     */ 
/*     */   public WapOampConfig getWapOampConfig()
/*     */   {
/*  61 */     if (config == null) {
/*  62 */       if (handler == null) {
/*  63 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/*  65 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/*  67 */     return config.getWapOampConfig();
/*     */   }
/*     */ 
/*     */   public ContentInfoStates getContentInfoStates()
/*     */   {
/*  76 */     if (config == null) {
/*  77 */       if (handler == null) {
/*  78 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/*  80 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/*  82 */     return config.getContentInfoStates();
/*     */   }
/*     */ 
/*     */   public CorebizConfig getCorebizConfig()
/*     */   {
/*  91 */     if (config == null) {
/*  92 */       if (handler == null) {
/*  93 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/*  95 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/*  97 */     return config.getCorebizConfig();
/*     */   }
/*     */ 
/*     */   public WebConfig getWebConfig()
/*     */   {
/* 106 */     if (config == null) {
/* 107 */       if (handler == null) {
/* 108 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/* 110 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/* 112 */     return config.getWebConfig();
/*     */   }
/*     */   public WapConfig getWapConfig() {
/* 115 */     if (config == null) {
/* 116 */       if (handler == null) {
/* 117 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/* 119 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/* 121 */     return config.getWapConfig();
/*     */   }
/*     */ 
/*     */   public AdminConfig getAdminConfig()
/*     */   {
/* 129 */     if (config == null) {
/* 130 */       if (handler == null) {
/* 131 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/* 133 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/* 135 */     return config.getAdminConfig();
/*     */   }
/*     */ 
/*     */   public AgentConfig getAgentConfig()
/*     */   {
/* 143 */     if (config == null) {
/* 144 */       if (handler == null) {
/* 145 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/* 147 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/* 149 */     return config.getAgentConfig();
/*     */   }
/*     */ 
/*     */   public CommonConfig getCommonConfig()
/*     */   {
/* 157 */     if (config == null) {
/* 158 */       if (handler == null) {
/* 159 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/* 161 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/* 163 */     return config.getCommonConfig();
/*     */   }
/*     */ 
/*     */   public MmsEditor getMmsEditor()
/*     */   {
/* 170 */     if (config == null) {
/* 171 */       if (handler == null) {
/* 172 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/* 174 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/* 176 */     return config.getMmsEditor();
/*     */   }
/*     */ 
/*     */   public CMPPConnect getCMPPConnect() {
/* 180 */     if (config == null) {
/* 181 */       if (handler == null) {
/* 182 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/* 184 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/* 186 */     return config.getCMPPConnect();
/*     */   }
/*     */ 
/*     */   public CMPPSubmitMessage getCMPPSubmitMessage() {
/* 190 */     if (config == null) {
/* 191 */       if (handler == null) {
/* 192 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/* 194 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/* 196 */     return config.getCMPPSubmitMessage();
/*     */   }
/*     */   public SGIPConnect getSGIPConnect() {
/* 199 */     if (config == null) {
/* 200 */       if (handler == null) {
/* 201 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/* 203 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/* 205 */     return config.getSGIPConnect();
/*     */   }
/*     */ 
/*     */   public SgipSubmit getSgipSubmit() {
/* 209 */     if (config == null) {
/* 210 */       if (handler == null) {
/* 211 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/* 213 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/* 215 */     return config.getSgipSubmit();
/*     */   }
/*     */   public MM7Config getMM7Config() {
/* 218 */     if (config == null) {
/* 219 */       if (handler == null) {
/* 220 */         handler = new Xml4jHandler("config.xml", "com.aceway.common.config.configbean");
/*     */       }
/* 222 */       config = (Config)handler.getRootConfigObject();
/*     */     }
/* 224 */     return config.getMM7Config();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 228 */     System.out.println(getInstance().getSgipSubmit());
/* 229 */     System.out.println(getInstance().getSGIPConnect());
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.util.ConfigUtil
 * JD-Core Version:    0.6.0
 */
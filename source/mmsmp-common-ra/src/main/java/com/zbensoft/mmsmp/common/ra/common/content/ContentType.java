/*     */ package com.zbensoft.mmsmp.common.ra.common.content;
/*     */ 
/*     */ public class ContentType
/*     */ {
/*     */   public static final int INVALID = -1;
/*     */   public static final int MMS = 0;
/*     */   public static final int JPG = 1;
/*     */   public static final int GIF = 2;
/*     */   public static final int WBMP = 3;
/*     */   public static final int BMP = 4;
/*     */   public static final int PNG = 5;
/*     */   public static final int MIDI = 6;
/*     */   public static final int MMF = 7;
/*     */   public static final int AMR = 8;
/*     */   public static final int WMA = 9;
/*     */   public static final int WAV = 10;
/*     */   public static final int MP3 = 11;
/*     */   public static final int _3GP = 12;
/*     */   public static final int TEXT = 13;
/*     */   public static final int XML = 14;
/*     */   public static final int SMIL = 15;
/*     */   public static final String R_MIDI = "MIDI";
/*     */   public static final String R_MMF = "MMF";
/*     */   public static final String R_AMR = "AMR";
/*     */   public static final String R_WMA = "WMA";
/*     */   public static final String R_WAV = "WAV";
/*     */   public static final String R_MP3 = "mp3";
/*     */ 
/*     */   public static String getContentTypeStr(String fileType)
/*     */   {
/*  35 */     if (fileType.indexOf(".") >= 0) {
/*  36 */       fileType = fileType.substring(fileType.lastIndexOf(".") + 1);
/*     */     }
/*  38 */     if (fileType.equalsIgnoreCase("mms"))
/*  39 */       return null;
/*  40 */     if ((fileType.equalsIgnoreCase("jpg")) || (fileType.equalsIgnoreCase("jpeg")))
/*  41 */       return "image/jpeg";
/*  42 */     if (fileType.equalsIgnoreCase("gif"))
/*  43 */       return "image/gif";
/*  44 */     if (fileType.equalsIgnoreCase("amr"))
/*  45 */       return "audio/amr";
/*  46 */     if (fileType.equalsIgnoreCase("txt"))
/*  47 */       return "text/plain";
/*  48 */     if (fileType.equalsIgnoreCase("xml"))
/*  49 */       return "text/xml";
/*  50 */     if ((fileType.equalsIgnoreCase("smil")) || (fileType.equalsIgnoreCase("smi")))
/*  51 */       return "application/smil";
/*  52 */     if ((fileType.equalsIgnoreCase("mid")) || (fileType.equalsIgnoreCase("midi")))
/*  53 */       return "audio/midi";
/*  54 */     if ((fileType.equalsIgnoreCase("wbmp")) || (fileType.equalsIgnoreCase("bmp")))
/*  55 */       return "image/vnd.wap.wbmp";
/*  56 */     if (fileType.equalsIgnoreCase("png")) {
/*  57 */       return "image/png";
/*     */     }
/*     */ 
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean isImage(String fileType) {
/*  64 */     if (fileType.indexOf(".") >= 0) {
/*  65 */       fileType = fileType.substring(fileType.lastIndexOf(".") + 1);
/*     */     }
/*  67 */     if (fileType.equalsIgnoreCase("mms"))
/*  68 */       return false;
/*  69 */     if ((fileType.equalsIgnoreCase("jpg")) || (fileType.equalsIgnoreCase("jpeg")))
/*  70 */       return true;
/*  71 */     if (fileType.equalsIgnoreCase("gif"))
/*  72 */       return true;
/*  73 */     if (fileType.equalsIgnoreCase("amr"))
/*  74 */       return false;
/*  75 */     if (fileType.equalsIgnoreCase("txt"))
/*  76 */       return false;
/*  77 */     if (fileType.equalsIgnoreCase("xml"))
/*  78 */       return false;
/*  79 */     if ((fileType.equalsIgnoreCase("smil")) || (fileType.equalsIgnoreCase("smi")))
/*  80 */       return false;
/*  81 */     if ((fileType.equalsIgnoreCase("mid")) || (fileType.equalsIgnoreCase("midi")))
/*  82 */       return false;
/*  83 */     if ((fileType.equalsIgnoreCase("wbmp")) || (fileType.equalsIgnoreCase("bmp"))) {
/*  84 */       return true;
/*     */     }
/*  86 */     return fileType.equalsIgnoreCase("png");
/*     */   }
/*     */ 
/*     */   public static boolean isText(String fileType)
/*     */   {
/*  93 */     if (fileType.indexOf(".") >= 0) {
/*  94 */       fileType = fileType.substring(fileType.lastIndexOf(".") + 1);
/*     */     }
/*  96 */     if (fileType.equalsIgnoreCase("mms"))
/*  97 */       return false;
/*  98 */     if ((fileType.equalsIgnoreCase("jpg")) || (fileType.equalsIgnoreCase("jpeg")))
/*  99 */       return false;
/* 100 */     if (fileType.equalsIgnoreCase("gif"))
/* 101 */       return false;
/* 102 */     if (fileType.equalsIgnoreCase("amr"))
/* 103 */       return false;
/* 104 */     if (fileType.equalsIgnoreCase("txt"))
/* 105 */       return true;
/* 106 */     if (fileType.equalsIgnoreCase("xml"))
/* 107 */       return true;
/* 108 */     if ((fileType.equalsIgnoreCase("smil")) || (fileType.equalsIgnoreCase("smi")))
/* 109 */       return false;
/* 110 */     if ((fileType.equalsIgnoreCase("mid")) || (fileType.equalsIgnoreCase("midi")))
/* 111 */       return false;
/* 112 */     if ((fileType.equalsIgnoreCase("wbmp")) || (fileType.equalsIgnoreCase("bmp"))) {
/* 113 */       return false;
/*     */     }
/* 115 */     return !fileType.equalsIgnoreCase("png");
/*     */   }
/*     */ 
/*     */   public static boolean isAudio(String fileType)
/*     */   {
/* 122 */     if (fileType.indexOf(".") >= 0) {
/* 123 */       fileType = fileType.substring(fileType.lastIndexOf(".") + 1);
/*     */     }
/* 125 */     if (fileType.equalsIgnoreCase("mms"))
/* 126 */       return false;
/* 127 */     if ((fileType.equalsIgnoreCase("jpg")) || (fileType.equalsIgnoreCase("jpeg")))
/* 128 */       return false;
/* 129 */     if (fileType.equalsIgnoreCase("gif"))
/* 130 */       return false;
/* 131 */     if (fileType.equalsIgnoreCase("amr"))
/* 132 */       return true;
/* 133 */     if (fileType.equalsIgnoreCase("txt"))
/* 134 */       return true;
/* 135 */     if (fileType.equalsIgnoreCase("xml"))
/* 136 */       return true;
/* 137 */     if ((fileType.equalsIgnoreCase("smil")) || (fileType.equalsIgnoreCase("smi")))
/* 138 */       return false;
/* 139 */     if ((fileType.equalsIgnoreCase("mid")) || (fileType.equalsIgnoreCase("midi")))
/* 140 */       return true;
/* 141 */     if ((fileType.equalsIgnoreCase("wbmp")) || (fileType.equalsIgnoreCase("bmp"))) {
/* 142 */       return false;
/*     */     }
/* 144 */     return !fileType.equalsIgnoreCase("png");
/*     */   }
/*     */ 
/*     */   public static int getRingContentType(String fileType)
/*     */   {
/* 151 */     if (fileType.indexOf(".") >= 0) {
/* 152 */       fileType = fileType.substring(fileType.lastIndexOf(".") + 1);
/*     */     }
/* 154 */     if ((fileType.equalsIgnoreCase("mid")) || (fileType.equalsIgnoreCase("midi")))
/* 155 */       return 6;
/* 156 */     if (fileType.equalsIgnoreCase("mmf"))
/* 157 */       return 7;
/* 158 */     if (fileType.equalsIgnoreCase("amr"))
/* 159 */       return 8;
/* 160 */     if (fileType.equalsIgnoreCase("wma"))
/* 161 */       return 9;
/* 162 */     if (fileType.equalsIgnoreCase("wav"))
/* 163 */       return 10;
/* 164 */     if (fileType.equalsIgnoreCase("mp3")) {
/* 165 */       return 11;
/*     */     }
/* 167 */     return -1;
/*     */   }
/*     */   public static int getContentType(String fileType) {
/* 170 */     if (fileType.indexOf(".") >= 0) {
/* 171 */       fileType = fileType.substring(fileType.lastIndexOf(".") + 1);
/*     */     }
/* 173 */     if (fileType.equalsIgnoreCase("mms"))
/* 174 */       return 0;
/* 175 */     if ((fileType.equalsIgnoreCase("jpg")) || (fileType.equalsIgnoreCase("jpeg")))
/* 176 */       return 1;
/* 177 */     if (fileType.equalsIgnoreCase("gif"))
/* 178 */       return 2;
/* 179 */     if (fileType.equalsIgnoreCase("amr"))
/* 180 */       return 8;
/* 181 */     if (fileType.equalsIgnoreCase("txt"))
/* 182 */       return 13;
/* 183 */     if (fileType.equalsIgnoreCase("xml"))
/* 184 */       return 14;
/* 185 */     if ((fileType.equalsIgnoreCase("smil")) || (fileType.equalsIgnoreCase("smi")))
/* 186 */       return 15;
/* 187 */     if ((fileType.equalsIgnoreCase("mid")) || (fileType.equalsIgnoreCase("midi")))
/* 188 */       return 6;
/* 189 */     if ((fileType.equalsIgnoreCase("wbmp")) || (fileType.equalsIgnoreCase("bmp")))
/* 190 */       return 3;
/* 191 */     if (fileType.equalsIgnoreCase("png"))
/* 192 */       return 5;
/* 193 */     if (fileType.equalsIgnoreCase("mp3"))
/* 194 */       return 11;
/* 195 */     if ((fileType.equalsIgnoreCase("3gp")) || (fileType.equalsIgnoreCase("3gpp"))) {
/* 196 */       return 12;
/*     */     }
/*     */ 
/* 199 */     return -1;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.content.ContentType
 * JD-Core Version:    0.6.0
 */
/*     */ package com.zbensoft.mmsmp.common.ra.common.mmcontents;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;

import com.zbensoft.mmsmp.common.ra.common.smil.Audio;
import com.zbensoft.mmsmp.common.ra.common.smil.Image;
import com.zbensoft.mmsmp.common.ra.common.smil.Layout;
import com.zbensoft.mmsmp.common.ra.common.smil.Par;
import com.zbensoft.mmsmp.common.ra.common.smil.Region;
import com.zbensoft.mmsmp.common.ra.common.smil.RootLayout;
import com.zbensoft.mmsmp.common.ra.common.smil.Smil;
import com.zbensoft.mmsmp.common.ra.common.smil.Text;
/*     */ 
/*     */ public class ComposeMMSFileExample
/*     */ {
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  21 */     AcewayMMContents contents = new AcewayMMContents();
/*     */ 
/*  25 */     AcewayMMContent content = new AcewayMMContent();
/*  26 */     content.setContentType("image/jpeg; name=jpeg1.jpg");
/*  27 */     content.addHeader("Content-Location", "jpeg1.jpg");
/*  28 */     content.addHeader("Content-ID", "<jpeg1.jpg>");
/*     */ 
/*  30 */     content.setContent("Here is the content of jpeg".getBytes());
/*  31 */     contents.addPart(content);
/*     */ 
/*  33 */     content = new AcewayMMContent();
/*  34 */     content.setContentType("image/gif; name=gif1.gif");
/*  35 */     content.addHeader("Content-Location", "gif1.gif");
/*  36 */     content.addHeader("Content-ID", "<gif1.gif>");
/*     */ 
/*  38 */     content.setContent("Here is the content of gif".getBytes());
/*  39 */     contents.addPart(content);
/*     */ 
/*  41 */     content = new AcewayMMContent();
/*  42 */     content.setContentType("image/vnd.wap.wbmp; name=wbmp1.bmp");
/*  43 */     content.addHeader("Content-Location", "wbmp1.bmp");
/*  44 */     content.addHeader("Content-ID", "<wbmp1.bmp>");
/*     */ 
/*  46 */     content.setContent("Here is the content of wbmp".getBytes());
/*  47 */     contents.addPart(content);
/*     */ 
/*  49 */     content = new AcewayMMContent();
/*  50 */     content.setContentType("image/png; name=png1.png");
/*  51 */     content.addHeader("Content-Location", "png1.png");
/*  52 */     content.addHeader("Content-ID", "<png1.png>");
/*     */ 
/*  54 */     content.setContent("Here is the content of png".getBytes());
/*  55 */     contents.addPart(content);
/*     */ 
/*  57 */     content = new AcewayMMContent();
/*  58 */     content.setContentType("audio/midi; name=midi1.midi");
/*  59 */     content.addHeader("Content-Location", "midi1.midi");
/*  60 */     content.addHeader("Content-ID", "<midi1.midi>");
/*     */ 
/*  62 */     content.setContent("Here is the content of midi".getBytes());
/*  63 */     contents.addPart(content);
/*     */ 
/*  65 */     content = new AcewayMMContent();
/*  66 */     content.setContentType("audio/amr; name=amr1.amr");
/*  67 */     content.addHeader("Content-Location", "amr1.amr");
/*  68 */     content.addHeader("Content-ID", "<amr1.amr>");
/*     */ 
/*  70 */     content.setContent("Here is the content of amr".getBytes());
/*  71 */     contents.addPart(content);
/*     */ 
/*  73 */     content = new AcewayMMContent();
/*  74 */     content.setContentType("text/plain; name=text1.txt; charset=utf-8");
/*  75 */     content.addHeader("Content-Location", "text1.txt");
/*  76 */     content.addHeader("Content-ID", "<text1.txt>");
/*     */ 
/*  78 */     content.setContent("Here is the content of text".getBytes("UTF-8"));
/*  79 */     contents.addPart(content);
/*     */ 
/*  81 */     content = new AcewayMMContent();
/*  82 */     content.setContentType("application/smil; name=smil1.smil; charset=utf-8");
/*  83 */     content.addHeader("Content-Location", "smil1.smil");
/*  84 */     content.addHeader("Content-ID", "<smil1.smil>");
/*  85 */     System.out.println(getSmil());
/*  86 */     content.setContent(getSmil().getBytes("UTF-8"));
/*  87 */     contents.addPart(content);
/*     */ 
/*  89 */     AcewayMMContentType contentType = new AcewayMMContentType("multipart/related; type=\"application/smil\"; start=\"<smil1.smil>\"; boundary=----_Part_31253860.1181965964999");
/*     */ 
/*  91 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  92 */     contents.encode(bos, contentType);
/*     */ 
/*  94 */     FileOutputStream out = new FileOutputStream("D:\\testMMS");
/*  95 */     out.write(("Content-Type:" + contentType.toString() + "\r\n\r\n").getBytes());
/*  96 */     out.write(bos.toByteArray());
/*  97 */     bos.close();
/*     */   }
/*     */ 
/*     */   private static String getSmil() {
/* 101 */     Smil smil = new Smil();
/* 102 */     RootLayout rootLayout = new RootLayout("178", "206");
/* 103 */     Region image = new Region("Image", "0", "0", "100", "50");
/* 104 */     Region text = new Region("Text", "0", "0", "100", "50");
/*     */ 
/* 106 */     smil.setLayout(new Layout(rootLayout, image, text));
/*     */ 
/* 114 */     List parList = new ArrayList();
/* 115 */     Par par = new Par("10s", new Image("cid:image1", "Image"), new Text("cid:text1", "Text"), null);
/* 116 */     parList.add(par);
/* 117 */     par = new Par("10s", new Image("cid:image1", "Image"), new Text("cid:text1", "Text"), new Audio("cid:audio1"));
/* 118 */     parList.add(par);
/*     */ 
/* 120 */     smil.setParList(parList);
/*     */ 
/* 122 */     return smil.toString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mmcontents.ComposeMMSFileExample
 * JD-Core Version:    0.6.0
 */
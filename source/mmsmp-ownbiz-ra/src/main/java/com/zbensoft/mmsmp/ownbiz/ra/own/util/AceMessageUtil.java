

package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import com.cmcc.mm7.vasp.common.MMConstants.ContentType;
import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.common.MMContentType;
import com.zbensoft.mmsmp.common.ra.common.mmcontents.AcewayByteArray;
import com.zbensoft.mmsmp.common.ra.common.mmcontents.AcewayMMContent;
import com.zbensoft.mmsmp.common.ra.common.mmcontents.AcewayMMContentType;
import com.zbensoft.mmsmp.common.ra.common.mmcontents.AcewayMMContents;
import com.zbensoft.mmsmp.common.ra.common.smil.*;

import javax.mail.internet.ParseException;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AceMessageUtil {
    private static final String CID = "cid:";
    private static final int SIZE_100_k = 209715200;

    public AceMessageUtil() {
    }

    public static MMContent emlFileToMM7(String path) throws Exception {
        if (!path.startsWith(path)) {
            path = path + path;
        }

        AcewayMMContents contents = new AcewayMMContents();
        byte[] bt = getFileData(path);
        contents.decode(new AcewayByteArray(bt));
        return emlToMM7(contents);
    }

    private static byte[] getFileData(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        } else {
            byte[] result = new byte[(int)file.length()];
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

            for(int readedBytes = 0; readedBytes < result.length; readedBytes += in.read(result, readedBytes, in.available())) {
                ;
            }

            in.close();
            return result;
        }
    }

    public static MMContent emlFileToMM7_bak(String path) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            AcewayMMContents contents = new AcewayMMContents();
            FileInputStream fin = new FileInputStream(path);
            int len = fin.available();
            if (len > 209715200) {
                throw new Exception("eml文件大于200K");
            } else {
                byte[] emlByte = new byte[len];
                fin.read(emlByte);
                AcewayByteArray ba = new AcewayByteArray(emlByte);
                contents.decode(ba);
                return emlToMM7(contents);
            }
        } else {
            return null;
        }
    }

    public static MMContent emlToMM7(AcewayMMContents contents) {
        MMContent content = new MMContent();
        content.setContentType(ContentType.MULTIPART_RELATED);
        content.setContentID("mms");
        int partNum = contents.getNumParts();

        for(int i = 0; i < partNum; ++i) {
            AcewayMMContent aceContent = contents.getPart(i);
            byte[] bt = (byte[])null;
            if (aceContent.getContentType().indexOf("text/plain") >= 0) {
                try {
                    bt = (new String(aceContent.getContent(), "utf-8")).getBytes();
                } catch (Exception var9) {
                    var9.printStackTrace();
                }
            } else {
                bt = aceContent.getContent();
            }

            MMContent subContent = MMContent.createFromBytes(bt);
            MMContentType contentType = new MMContentType(aceContent.getContentType());
            subContent.setContentType(contentType);
            String s1 = aceContent.getHeaders().getHeader("Content-ID")[0];
            if (s1 != null && s1.length() != 0) {
                subContent.setContentID(s1);
            } else {
                subContent.setContentID("random" + (new Random()).nextInt(1000));
            }

            content.setCharset("utf-8");
            content.addSubContent(subContent);
        }

        return content;
    }

    public static void mm7ToEmlFile(MMContent srcContents, String file) throws Exception {
        AcewayMMContentType contentType = new AcewayMMContentType("multipart/related; type=\"application/smil\"; start=\"<mms.smil>\"; boundary=----_Part_1_15020296.1182758071171");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AcewayMMContents destContents = mm7ToEml(srcContents);
        destContents.encode(baos, contentType);
        FileOutputStream fos = new FileOutputStream(file);

        try {
            fos.write(baos.toByteArray());
            fos.flush();
        } finally {
            baos.close();
            fos.close();
        }

    }

    public static AcewayMMContents mm7ToEml(MMContent srcContents) throws UnsupportedEncodingException, ParseException {
        if (srcContents == null) {
            return null;
        } else {
            List<MMContent> subContents = srcContents.getSubContents();
            if (subContents == null) {
                return null;
            } else {
                AcewayMMContents destContents = new AcewayMMContents();
                Smil smil = new Smil();
                RootLayout rootLayout = new RootLayout("208", "176");
                Region image_region = new Region("Image", "0", "0", "100%", "35%");
                Region text_region = new Region("Text", "0", "0", "100%", "20%");
                Region audio_region = new Region("Audio", "0", "0", "100%", "10%");
                Region video_region = new Region("Video", "0", "0", "100%", "35%");
                smil.setLayout(new Layout(rootLayout, image_region, text_region, audio_region, video_region));
                List<Par> parList = new LinkedList();
                int count = 0;
                Iterator var12 = subContents.iterator();

                while(true) {
                    Image image_obj;
                    Text text_obj;
                    Audio audio_obj;
                    Video video_obj;
                    label63:
                    while(true) {
                        MMContent content;
                        MMContentType contentType;
                        do {
                            if (!var12.hasNext()) {
                                smil.setParList(parList);
                                AcewayMMContent smilContent = new AcewayMMContent();
                                smilContent.setContentType("application/smil; name=mms.smil; charset=utf-8");
                                smilContent.addHeader("Content-Transfer-Encoding", "8bit");
                                smilContent.addHeader("Content-ID", "<mms.smil>");
                                smilContent.setContent(smil.toString().getBytes("UTF-8"));
                                destContents.addPart(smilContent);
                                return destContents;
                            }

                            content = (MMContent)var12.next();
                            image_obj = null;
                            text_obj = null;
                            audio_obj = null;
                            video_obj = null;
                            contentType = content.getContentType();
                        } while(contentType.getSubType().indexOf("smil") >= 0);

                        if (contentType.equals(ContentType.MULTIPART_MIXED)) {
                            List<MMContent> subSubContents = content.getSubContents();
                            if (subSubContents == null) {
                                continue;
                            }

                            Iterator var20 = subSubContents.iterator();

                            while(true) {
                                if (!var20.hasNext()) {
                                    break label63;
                                }

                                MMContent subContent = (MMContent)var20.next();
                                Object[] result = createAceContentFromMM7Content(content, count++);
                                destContents.addPart((AcewayMMContent)result[0]);
                                Object obj = result[1];
                                if (obj instanceof Image) {
                                    image_obj = (Image)obj;
                                } else if (obj instanceof Text) {
                                    text_obj = (Text)obj;
                                } else if (obj instanceof Audio) {
                                    audio_obj = (Audio)obj;
                                } else if (obj instanceof Video) {
                                    video_obj = (Video)obj;
                                }
                            }
                        }

                        Object[] result = createAceContentFromMM7Content(content, count++);
                        destContents.addPart((AcewayMMContent)result[0]);
                        Object obj = result[1];
                        if (obj instanceof Image) {
                            image_obj = (Image)obj;
                        } else if (obj instanceof Text) {
                            text_obj = (Text)obj;
                        } else if (obj instanceof Audio) {
                            audio_obj = (Audio)obj;
                        } else if (obj instanceof Video) {
                            video_obj = (Video)obj;
                        }
                        break;
                    }

                    Par par = new Par("60000ms", image_obj, text_obj, audio_obj, video_obj);
                    parList.add(par);
                }
            }
        }
    }

    private static Object[] createAceContentFromMM7Content(MMContent content, int count) throws UnsupportedEncodingException, ParseException {
        AcewayMMContent aceContent = new AcewayMMContent();
        MMContentType contentType = content.getContentType();
        String contentName = null;
        String primaryType = contentType.getPrimaryType();
        Object type_obj = null;
        if (primaryType.equals(ContentType.TEXT.getPrimaryType()) || primaryType.equals(ContentType.E_MELODY.getPrimaryType()) || primaryType.equals(ContentType.I_MELODY.getPrimaryType())) {
            contentName = "text" + count + ".txt";
            type_obj = new Text("cid:" + contentName, "Text");
            aceContent.setContentType("text/plain;name=" + contentName + " ;charset=utf-8");
            aceContent.addHeader("Content-ID", contentName);
            aceContent.setContent(content.getContentAsString().getBytes("UTF-8"));
        }

        if (primaryType.equals(ContentType.JPEG.getPrimaryType()) || primaryType.equals(ContentType.GIF.getPrimaryType()) || primaryType.equals(ContentType.PNG.getPrimaryType()) || primaryType.equals(ContentType.WBMP.getPrimaryType())) {
            contentName = "image" + count + "." + contentType.getSubType();
            type_obj = new Image("cid:" + contentName, "Image");
            aceContent.setContentType(primaryType + "/" + contentType.getSubType() + ";name=" + contentName);
            aceContent.addHeader("Content-Transfer-Encoding", "8bit");
            aceContent.addHeader("Content-ID", contentName);
            aceContent.setContent(content.getContent());
        }

        if (primaryType.equals(ContentType.AMR.getPrimaryType()) || primaryType.equals(ContentType.MIDI.getPrimaryType())) {
            contentName = "audio" + count + "." + contentType.getSubType();
            type_obj = new Audio("cid:" + contentName);
            content.setContentType(primaryType + "/" + contentType.getSubType() + ";name=" + contentName);
            aceContent.addHeader("Content-Transfer-Encoding", "8bit");
            aceContent.addHeader("Content-ID", contentName);
            aceContent.setContent(content.getContent());
        }

        return new Object[]{aceContent, type_obj};
    }

    public static void main(String[] args) {
        System.out.println(ContentType.MULTIPART_MIXED.getPrimaryType());
    }
}

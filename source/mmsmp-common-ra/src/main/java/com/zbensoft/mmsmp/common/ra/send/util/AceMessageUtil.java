package com.zbensoft.mmsmp.common.ra.send.util;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.CommonConfig;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.mmcontents.AcewayByteArray;
import com.zbensoft.mmsmp.common.ra.common.mmcontents.AcewayMMContent;
import com.zbensoft.mmsmp.common.ra.common.mmcontents.AcewayMMContentType;
import com.zbensoft.mmsmp.common.ra.common.mmcontents.AcewayMMContents;
import com.zbensoft.mmsmp.common.ra.common.smil.Audio;
import com.zbensoft.mmsmp.common.ra.common.smil.Image;
import com.zbensoft.mmsmp.common.ra.common.smil.Layout;
import com.zbensoft.mmsmp.common.ra.common.smil.Par;
import com.zbensoft.mmsmp.common.ra.common.smil.Region;
import com.zbensoft.mmsmp.common.ra.common.smil.RootLayout;
import com.zbensoft.mmsmp.common.ra.common.smil.Smil;
import com.zbensoft.mmsmp.common.ra.common.smil.Text;
import com.cmcc.mm7.vasp.common.MMConstants;
import com.cmcc.mm7.vasp.common.MMConstants.ContentType;
import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.common.MMContentType;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import javax.mail.internet.ParseException;

public class AceMessageUtil {
	private static final String CID = "cid:";
	private static final int SIZE_100_k = 104857600;

	public static MMContent emlFileToMM7(String path) throws Exception {
		String contentPath = ConfigUtil.getInstance().getCommonConfig().getContentFilePath();
		if (!path.startsWith(contentPath)) {
			path = contentPath + path;
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
		}
		byte[] result = new byte[(int) file.length()];
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

		int readedBytes = 0;

		while (readedBytes < result.length) {
			readedBytes += in.read(result, readedBytes, in.available());
		}

		in.close();
		return result;
	}

	public static MMContent emlFileToMM7_bak(String path) throws Exception {
		File file = new File(path);
		if (file.exists()) {
			AcewayMMContents contents = new AcewayMMContents();
			FileInputStream fin = new FileInputStream(path);

			int len = fin.available();
			if (len > 104857600)
				throw new Exception("eml文件大于100K");
			byte[] emlByte = new byte[len];
			fin.read(emlByte);
			AcewayByteArray ba = new AcewayByteArray(emlByte);

			contents.decode(ba);
			return emlToMM7(contents);
		}
		return null;
	}

	public static MMContent emlToMM7(AcewayMMContents contents) {
		MMContent content = new MMContent();
		content.setContentType(MMConstants.ContentType.MULTIPART_RELATED);
		content.setContentID("caie");

		int partNum = contents.getNumParts();

		for (int i = 0; i < partNum - 1; i++) {
			AcewayMMContent aceContent = contents.getPart(i);

			byte[] bt = aceContent.getContent();
			MMContent subContent = MMContent.createFromBytes(bt);

			MMContentType contentType = new MMContentType(aceContent.getContentType());
			subContent.setContentType(contentType);

			String s1 = aceContent.getHeaders().getHeader("Content-ID")[0];
			subContent.setContentID("1");

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
		}

		List<MMContent> subContents = srcContents.getSubContents();
		if (subContents == null) {
			return null;
		}

		AcewayMMContents destContents = new AcewayMMContents();

		Smil smil = new Smil();

		RootLayout rootLayout = new RootLayout("208", "176");
		Region image_region = new Region("Image", "0", "0", "100%", "80%");
		Region text_region = new Region("Text", "0", "0", "100%", "20%");
		smil.setLayout(new Layout(rootLayout, image_region, text_region));

		List parList = new LinkedList();

		int count = 0;
		for (MMContent content : subContents) {
			Image image_obj = null;
			Text text_obj = null;
			Audio audio_obj = null;
			MMContentType contentType = content.getContentType();
			if (contentType.equals(MMConstants.ContentType.MULTIPART_MIXED)) {
				List<MMContent> subSubContents = content.getSubContents();
				if (subSubContents == null)
					continue;
				for (MMContent subContent : subSubContents) {
					Object[] result = createAceContentFromMM7Content(content, count++);
					destContents.addPart((AcewayMMContent) result[0]);
					Object obj = result[1];
					if ((obj instanceof Image))
						image_obj = (Image) obj;
					else if ((obj instanceof Text))
						text_obj = (Text) obj;
					else if ((obj instanceof Audio))
						audio_obj = (Audio) obj;
				}
			} else {
				Object[] result = createAceContentFromMM7Content(content, count++);
				destContents.addPart((AcewayMMContent) result[0]);
				Object obj = result[1];
				if ((obj instanceof Image))
					image_obj = (Image) obj;
				else if ((obj instanceof Text))
					text_obj = (Text) obj;
				else if ((obj instanceof Audio)) {
					audio_obj = (Audio) obj;
				}
			}
			Par par = new Par("60000ms", image_obj, text_obj, audio_obj);
			parList.add(par);
		}

		smil.setParList(parList);
		AcewayMMContent smilContent = new AcewayMMContent();
		smilContent.setContentType("application/smil; name=mms.smil; charset=utf-8");
		smilContent.addHeader("Content-Transfer-Encoding", "8bit");
		smilContent.addHeader("Content-ID", "<mms.smil>");
		smilContent.setContent(smil.toString().getBytes("UTF-8"));

		destContents.addPart(smilContent);

		return destContents;
	}

	private static Object[] createAceContentFromMM7Content(MMContent content, int count) throws UnsupportedEncodingException, ParseException {
		AcewayMMContent aceContent = new AcewayMMContent();
		MMContentType contentType = content.getContentType();
		String contentName = null;
		String primaryType = contentType.getPrimaryType();

		Object type_obj = null;
		if (primaryType.equals(MMConstants.ContentType.TEXT.getPrimaryType())) {
			contentName = "text" + count + ".txt";

			type_obj = new Text("cid:" + contentName, "Text");
			aceContent.setContentType("text/plain;name=" + contentName + " ;charset=utf-8");

			aceContent.addHeader("Content-ID", contentName);

			aceContent.setContent(new String(content.getContent(), "GBK"));
		}

		if (primaryType.equals(MMConstants.ContentType.JPEG.getPrimaryType())) {
			contentName = "image" + count + "." + contentType.getSubType();
			type_obj = new Image("cid:" + contentName, "Image");

			aceContent.setContentType(primaryType + "/" + contentType.getSubType() + ";name=" + contentName);

			aceContent.addHeader("Content-Transfer-Encoding", "base64");

			aceContent.addHeader("Content-ID", contentName);
			aceContent.setContent(content.getContent());
		}

		if (primaryType.equals(MMConstants.ContentType.AMR.getPrimaryType())) {
			contentName = "audio" + count + "." + contentType.getSubType();

			type_obj = new Audio("cid:" + contentName);

			content.setContentType(primaryType + "/" + contentType.getSubType() + ";name=" + contentName);

			aceContent.addHeader("Content-Transfer-Encoding", "8bit");

			aceContent.addHeader("Content-ID", contentName);
			aceContent.setContent(content.getContent());
		}

		return new Object[] { aceContent, type_obj };
	}
}

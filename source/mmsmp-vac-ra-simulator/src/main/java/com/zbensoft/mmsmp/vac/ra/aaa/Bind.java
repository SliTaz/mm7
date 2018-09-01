package com.zbensoft.mmsmp.vac.ra.aaa;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Date;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.vac.ra.util.DateHelper;
import com.zbensoft.mmsmp.vac.ra.util.PropertiesHelper;
import com.zbensoft.mmsmp.vac.ra.util.Utils;

public class Bind extends Header {

	private static final Logger logger = Logger.getLogger(Bind.class);
	private Integer SourceDevice_Type;// 原始端设备类型
	private String SourceDevice_ID;// 原始端设备识别号
	private Integer DestinationDevice_Type;// 目标端设备类型
	private String DestinationDevice_ID;// 目标端设备识别号
	private String Check_Source;// 该域用来对原始端进行鉴权
	private String Time_Stamp;// 时间戳由原始端设备生成
	private Integer Version;// 协议版本

	public Bind(String threadname) {
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + 78);
		this.CommandId = CMDID_Bind;
		this.Time_Stamp = getTime();
		this.SourceDevice_Type = PropertiesHelper.getInteger("vac.src.dev.type", Integer.valueOf(9));
		String srcdevids = PropertiesHelper.getString("vac.src.dev.id", "090102,090103,090104");
		String[] srcdevid = srcdevids.split(",");
		int index = Integer.parseInt(threadname.substring(threadname.length() - 1));
		if (index + 1 > srcdevid.length) {
			throw new RuntimeException("thread number cannot more than SourceDevice_ID number");
		}
		System.out.println(srcdevid[index]);
		this.SourceDevice_ID = srcdevid[index];
		this.DestinationDevice_Type = PropertiesHelper.getInteger("vac.dest.dev.type", Integer.valueOf(0));
		this.DestinationDevice_ID = PropertiesHelper.getString("vac.dest.dev.id", "000101");
		this.Check_Source = generateCheck_Source();
		this.Version = Integer.valueOf(100);
	}

	private String generateCheck_Source() {
		StringBuffer ret = new StringBuffer();
		String share_secret_key = PropertiesHelper.getString("vac.dest.share_secret_key", "123456");
		String key = this.SourceDevice_ID + share_secret_key + this.Time_Stamp;
		logger.info("generateCheck_Source() - String key=" + key);
		String md5 = encrypt16(key);
		logger.info("generateCheck_Source() - String key-md5=" + md5);
//		char[] chars = md5.toCharArray();
//		if (chars.length == 32) {
//			for (int i = 0; i < chars.length;) {
//				String oo = chars[(i++)] + chars[(i++)]+"";
//				ret.append((char) Integer.parseInt(oo, 16));
//			}
//		}
//		return ret.toString();
		return md5;
	}

	public static String encrypt32(String encryptStr) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md5.digest(encryptStr.getBytes());
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			encryptStr = hexValue.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return encryptStr;
	}
	public static String encrypt16(String encryptStr) {
		return encrypt32(encryptStr).substring(8, 24);
	}
		
	private static String md5(String key) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(key.getBytes(), 0, key.length());
			return new BigInteger(1, m.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String getTime() {
		return DateHelper.getString(new Date(), "MMddHHmmss");
	}

	public ByteBuffer serialize() throws Exception {
		ByteBuffer bb = ByteBuffer.allocate(this.TotalLength.intValue());
		bb.put(super.serialize());
		bb.putInt(this.SourceDevice_Type.intValue());
		Utils.putString(bb, this.SourceDevice_ID, 20);
		bb.putInt(this.DestinationDevice_Type.intValue());
		Utils.putString(bb, this.DestinationDevice_ID, 20);
		Utils.putString1(bb, this.Check_Source, 16);
		Utils.putString(bb, this.Time_Stamp, 10);
		bb.putInt(this.Version.intValue());
		bb.flip();
		return bb;
	}

	public ByteBuffer unserialize(ByteBuffer content) throws Exception {
		ByteBuffer bb = super.unserialize(content);
		this.SourceDevice_Type = Integer.valueOf(bb.getInt());
		this.SourceDevice_ID = Utils.getString(bb, 20);
		this.DestinationDevice_Type = Integer.valueOf(bb.getInt());
		this.DestinationDevice_ID = Utils.getString(bb, 20);
		this.Check_Source = Utils.getString(bb, 16);
		this.Time_Stamp = Utils.getString(bb, 10);
		this.Version = Integer.valueOf(bb.getInt());
		return bb;
	}

	public Integer getSourceDevice_Type() {
		return this.SourceDevice_Type;
	}

	public void setSourceDevice_Type(Integer sourceDeviceType) {
		this.SourceDevice_Type = sourceDeviceType;
	}

	public String getSourceDevice_ID() {
		return this.SourceDevice_ID;
	}

	public void setSourceDevice_ID(String sourceDeviceID) {
		this.SourceDevice_ID = sourceDeviceID;
	}

	public Integer getDestinationDevice_Type() {
		return this.DestinationDevice_Type;
	}

	public void setDestinationDevice_Type(Integer destinationDeviceType) {
		this.DestinationDevice_Type = destinationDeviceType;
	}

	public String getDestinationDevice_ID() {
		return this.DestinationDevice_ID;
	}

	public void setDestinationDevice_ID(String destinationDeviceID) {
		this.DestinationDevice_ID = destinationDeviceID;
	}

	public String getCheck_Source() {
		return this.Check_Source;
	}

	public void setCheck_Source(String checkSource) {
		this.Check_Source = checkSource;
	}

	public String getTime_Stamp() {
		return this.Time_Stamp;
	}

	public void setTime_Stamp(String timeStamp) {
		this.Time_Stamp = timeStamp;
	}

	public Integer getVersion() {
		return this.Version;
	}

	public void setVersion(Integer version) {
		this.Version = version;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" SourceDevice_Type=").append(this.SourceDevice_Type);
		sb.append(" SourceDevice_ID=").append(this.SourceDevice_ID);
		sb.append(" DestinationDevice_Type=").append(this.DestinationDevice_Type);
		sb.append(" DestinationDevice_ID=").append(this.DestinationDevice_ID);
		sb.append(" Check_Source=").append(this.Check_Source);
		sb.append(" Time_Stamp=").append(this.Time_Stamp);
		sb.append(" Version=").append(this.Version);
		sb.append(" ]");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.print(new Bind("thread0"));
	}

}

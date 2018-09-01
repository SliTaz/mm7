package com.zbensoft.mmsmp.common.ra.wo.order;

//import com.sinovatech.base.cert.SignException;
import com.sinovatech.base.cert.SignMain;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Sign {
	public static String getSign(String str) throws Exception, UnsupportedEncodingException {
		String sign = SignMain.getInstance().localSign(str);
		if (SignMain.getInstance().remoteVerify(str, sign)) {
			System.out.println(sign);

			return sign;
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			getSign("我们");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

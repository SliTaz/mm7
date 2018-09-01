package com.zbensoft.mmsmp.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ImportUtil {
	
	public static final String EF_BB_BF = "EFBBBF";
	public static final String FE_FF = "FEFF";
	
	//解决UTF-8格式的txt中的第一行的问题。会出现看不见的空格
	public static String getValueForUTF_8(String value){
		try {
			String value_hexString=toHexString(value);
			if(value_hexString.startsWith(EF_BB_BF)&&value.length()>=2){
				return value=value.substring(1,value.length());
			}
			
			if(value_hexString.startsWith(FE_FF)&&value.length()>=2){
				return value=value.substring(1,value.length());
			}
			
			return value;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return value;
		}
		
	}
	
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str.toUpperCase();
	}
}

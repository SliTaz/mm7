package com.zbensoft.mmsmp.api.common;


import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbensoft.license.read.ReadImpl;
import com.zbensoft.mmsmp.api.alarm.AlarmMangerFactory;
import com.zbensoft.mmsmp.api.alarm.util.MailUtil;
import com.zbensoft.mmsmp.api.factory.BlackWhiteListConfigFactory;
import com.zbensoft.mmsmp.api.factory.MobileSegmentConfigFactory;
import com.zbensoft.mmsmp.api.factory.ProvinceCityConfigFactory;
import com.zbensoft.mmsmp.common.config.SystemConfigKey;

public class CommonFun {
	
	
	public static int ORDER_INT=1;//订购
	public static int DIANBO_INT=2;//点播

	private static final Logger log = LoggerFactory.getLogger(CommonFun.class);

	/**
	 * 获取修改后的vid，总共8位
	 * 
	 * 
	 * @param vid
	 * @return
	 */
	public static String getRelVid(String vid) {
		if (isVid(vid)) {
			String endStr = vid.substring(1);
			if (endStr.length() < 8) {
				endStr = "00000000000".substring(0, 8 - endStr.length()) + endStr;
			}
			return vid.substring(0, 1).toUpperCase() + endStr;
		}
		return vid;
	}
	
	
	/**
	 * 获取修改后的rif，总共9位
	 * 
	 * 
	 * @param rif
	 * @return
	 */
	public static String getRelRif(String rif) {
		if (isVid(rif)) {
			String endStr = rif.substring(1);
			if (endStr.length() < 9) {
				endStr = "00000000000".substring(0, 9 - endStr.length()) + endStr;
			}
			return rif.substring(0, 1).toUpperCase() + endStr;
		}
		return rif;
	}
	//西班牙语查询
	public static String getTitleNew(String title) {
		String titleNew="";
		if(title!=null&&!title.equals("")&&!title.equals("undefined")){
			try {
				titleNew = java.net.URLDecoder.decode(title, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return titleNew;
	}
	
	

	public static boolean isVid(String vid) {
		String VID_PRE = SystemConfigFactory.getInstance().getSystemConfigStr(SystemConfigKey.VID_PRE);
		if (vid != null && vid.length() > 0) {
			String first = vid.substring(0, 1);
			String[] vidpres = VID_PRE.split(",");
			if (vidpres != null && vidpres.length > 0) {
				for (String vidpre : vidpres) {
					if (first.equalsIgnoreCase(vidpre)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param vid
	 * @return
	 */
	public static String getRelLoginName(String userName) {
		if (userName != null && userName.length() <= 3) {
			return userName;
		}
		return userName.substring(0, 3) + getRelVid(userName.substring(3));
	}

	/**
	 * 用来去掉List中空值和相同项的。
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> removeSameItem(List<String> list) {
		List<String> difList = new ArrayList<String>();
		for (String t : list) {
			// if (t != null && !difList.contains(t)) {
			// difList.add(t);
			// }
			if (isEmpty(t)) {// 是空
				continue;
			} else {
				if (difList.contains(t)) {
					continue;
				} else {
					difList.add(t);
				}
			}
		}
		return difList;
	}

	/**
	 * 判断变量是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return StringUtils.isEmpty(s);
		// if (null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s)) {
		// return true;
		// } else {
		// return false;
		// }
	}

	// 获取本地IP地址
	@SuppressWarnings("rawtypes")
	public static String getLocalIP() {
		String ipStr = "";
		try {
			Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
							String[] ips = ipStr.split(",");
							boolean hasipFlag = true; // ip是否已经存在，若存在为false ，不存在为true
							for (String ipstr : ips) { // 比较当前ip与前几次添加的ip
								if (ipstr.equals(ip.getHostAddress())) {
									hasipFlag = false;
									break;
								}
							}
							if (hasipFlag) {
								ipStr += ip.getHostAddress() + ",";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return ipStr;
	}

	// 获取客户端地址
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (!checkIP(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getRemoteAddr();
		}

		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		return ip;
	}

	private static boolean checkIP(String ip) {
		if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip) || ip.split(".").length != 4) {
			return false;
		}
		return true;
	}

	// 用户默认密码和默认支付密码规则 strLength为长度
	public static String generaPassword(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append(str).append("1");// 右补1
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str.substring(strLen - strLength);
	}

	// shell执行
	public static String runScript(String cmd) {
		StringBuffer buf = new StringBuffer();
		String rt = "-1";
		try {
			Process pos = Runtime.getRuntime().exec(cmd);
			pos.waitFor();
			InputStreamReader ir = new InputStreamReader(pos.getInputStream(), Charset.forName("UTF-8"));
			LineNumberReader input = new LineNumberReader(ir);
			String ln = "";
			while ((ln = input.readLine()) != null) {
				buf.append(ln + "\n");
			}
			rt = buf.toString();
			input.close();
			ir.close();
		} catch (java.io.IOException e) {
			rt = e.toString();
		} catch (Exception e) {
			rt = e.toString();
		}
		return rt;
	}

	public static void loadConfigStartup() {
		SystemConfigFactory.getInstance().loadConfig();
		AlarmMangerFactory.getInstance().loadConfig();
		ProvinceCityConfigFactory.getInstance().loadConfig();
		MobileSegmentConfigFactory.getInstance().loadConfig();
		BlackWhiteListConfigFactory.getInstance().loadConfig();

	}

	public static void loadConfig() {
		SystemConfigFactory.getInstance().loadConfig();
		AlarmMangerFactory.getInstance().loadConfig();
		ProvinceCityConfigFactory.getInstance().loadConfig();
		MobileSegmentConfigFactory.getInstance().loadConfig();
		BlackWhiteListConfigFactory.getInstance().loadConfig();
	}

	/**
	 * 校验登录密码格式是否正确： 6位，数字+字母
	 * 
	 * @param password
	 * @return
	 */
	public static boolean checkLoginPassword(String password) {
		if (password != null && password.length() == 6) {
			String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6}$";
			return password.matches(regex);
		}
		return false;
	}

	/**
	 * 校验支付密码格式是否正确： 6位，数字
	 * 
	 * @param password
	 * @return
	 */
	public static boolean checkPayPassword(String password) {
		if (password != null && password.length() == 6) {
			String regex = "^[0-9]{6}$";
			return password.matches(regex);
		}
		return false;
	}

	public static HttpRestStatus errorPayPassword(int count) {
		switch (count) {
		case 0:
			return HttpRestStatus.PAY_PAY_PASSWORD_ERROR0;
		case 1:
			return HttpRestStatus.PAY_PAY_PASSWORD_ERROR1;
		case 2:
			return HttpRestStatus.PAY_PAY_PASSWORD_ERROR2;
		case 3:
			return HttpRestStatus.PAY_PAY_PASSWORD_ERROR3;
		case 4:
			return HttpRestStatus.PAY_PAY_PASSWORD_ERROR4;
		case 5:
			return HttpRestStatus.PAY_PAY_PASSWORD_ERROR5;

		default:
			return HttpRestStatus.PAY_PAY_PASSWORD_ERROR0;
		}
	}

	public static HttpRestStatus errorPassword(int count) {
		switch (count) {
		case 0:
			return HttpRestStatus.PASSWORD_ERROR0;
		case 1:
			return HttpRestStatus.PASSWORD_ERROR1;
		case 2:
			return HttpRestStatus.PASSWORD_ERROR2;
		case 3:
			return HttpRestStatus.PASSWORD_ERROR3;
		case 4:
			return HttpRestStatus.PASSWORD_ERROR4;
		case 5:
			return HttpRestStatus.PASSWORD_ERROR5;

		default:
			return HttpRestStatus.PASSWORD_ERROR0;
		}
	}

	/**
	 * 校验邮箱
	 *
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		return Pattern.matches("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", email);
	}

	public static String getVcode() {
		String code = "";
		for (int i = 0; i < 5; i++) {
			code += new Random().nextInt(10);
		}
		return code;
	}

	public static String getLoginPassword() {

		String val = "";
		while (true) {
			val = "";
			Random random = new Random();
			for (int i = 0; i < 6; i++) {
				String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

				if ("char".equalsIgnoreCase(charOrNum)) // 字符串
				{
					int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
					val += (char) (choice + random.nextInt(26));
				} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
				{
					val += String.valueOf(random.nextInt(10));
				}
			}
			val = val.toLowerCase();
			if (checkLoginPassword(val)) {
				break;
			}
		}
		return val;

	}

	public static String getPayPassword() {
		String code = "";
		for (int i = 0; i < 6; i++) {
			code += new Random().nextInt(10);
		}
		return code;
	}

	public static String getBindEmailContent(String userName, String vCode, String email) {
		StringBuffer sb = new StringBuffer();
		sb.append("Sr(a): ").append(userName).append(",").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		sb.append("Bienvenido a la función Código de verificación de su Billetera Móvil.").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		sb.append("Por favor, ingrese al Sistema Billetera Móvil y registre este código en el cuadro de texto identificado como “código de verificación”: <font color=\"red\">").append(vCode).append("</font>.")
				.append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		sb.append("Registre este código antes de 30 minutos para completar el proceso.").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		// sb.append(DateUtil.convertDateToString(Calendar.getInstance().getTime(), DateUtil.DATE_FORMAT_TEN)).append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		/*
		 * sb.append("Hello: [").append(email).append("],").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE); sb.append("You are checking for email.").append(MailUtil.NEW_LINE);
		 * sb.append("Please enter this code in the verification code entry box: [").append(vCode).append("] to complete the verification. ").append(MailUtil.NEW_LINE).append(
		 * MailUtil.NEW_LINE); sb.append("Please fill in this code in 30 minutes to complete the verification code.").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		 * sb.append("If you do not operate, please ignore this message, which brought you the inconvenience please understand!").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE
		 * ); sb.append(DateUtil.convertDateToString(Calendar.getInstance().getTime(),
		 * DateUtil.DATE_FORMAT_ONE)).append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		 */
		return sb.toString();
	}
	

	public static String getRetrievePassordContent(String userName, String email, String loginPassword, String payPassword) {
		StringBuffer sb = new StringBuffer();
		sb.append("Sr(a): ").append(userName).append(",").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		sb.append("Bienvenido a la función Recuperar Contraseña.").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		sb.append("En atención a su solicitud, se envía nueva clave de acceso para ingresar al Sistema Billetera móvil.").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		if (StringUtils.isNotEmpty(loginPassword)) {
			sb.append("La contraseña de inicio de sesión es: <font color=\"red\">").append(loginPassword).append("</font>").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		}
		if (StringUtils.isNotEmpty(payPassword)) {
			sb.append("La contraseña de pago es: <font color=\"red\">").append(payPassword).append("</font>").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		}
		sb.append("IMPORTANTE: La clave es sensible a Minúsculas/Mayúsculas. Por su seguridad, No divulgue esta información.").append(MailUtil.NEW_LINE);
		sb.append("Para garantizar la seguridad, inicie sesión tan pronto como sea posible y modifique su contraseña.").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE)
				.append(MailUtil.NEW_LINE);
		// sb.append(DateUtil.convertDateToString(Calendar.getInstance().getTime(), DateUtil.DATE_FORMAT_TEN)).append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		/*
		 * sb.append("Hello: [").append(email).append("],").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		 * sb.append("Welcome to the Recover Password function.").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		 * sb.append("In response to your request, a new password is sent to enter the Mobile Wallet System.").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE); if
		 * (StringUtils.isNotEmpty(loginPassword)) {
		 * sb.append("The login password is: [").append(loginPassword).append(email).append("]").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE); } if
		 * (StringUtils.isNotEmpty(payPassword)) {
		 * sb.append("The payment password is: [").append(payPassword).append(MailUtil.NEW_LINE).append("]").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE); }
		 * sb.append("IMPORTANT: The password is case sensitive. For your safety, do not disclose this information.").append(MailUtil.NEW_LINE);
		 * sb.append("To ensure security, log in as soon as possible and modify your password.").append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE).
		 * append(MailUtil.NEW_LINE); // sb.append(DateUtil.convertDateToString(Calendar.getInstance().getTime(),
		 * DateUtil.DATE_FORMAT_ONE)).append(MailUtil.NEW_LINE).append(MailUtil.NEW_LINE);
		 */
		return sb.toString();
	}

	/**
	 * 1@123.com->******@123.com<br/>
	 * 12@123.com1->******@123.com<br/>
	 * 123@123.com->1******@123.com<br/>
	 * 1234@123.com->123******@123.com<br/>
	 * 
	 * @param email
	 * @return
	 */
	public static String getEmailWhithStar(String email) {
		if (StringUtils.isNotEmpty(email)) {
			if (email.contains("@")) {
				int index = email.indexOf("@");
				if (index < 2) {
					email = "******" + email.substring(index);
				} else if (index < 4) {
					email = email.substring(0, 1) + "******" + email.substring(index);
				} else {
					email = email.substring(0, 3) + "******" + email.substring(index);
				}
			}
		}
		return email;
	}

	private static ConcurrentHashMap<String, Long> isUpdateTokenMap = new ConcurrentHashMap<>();

	/**
	 * 判断是否更新token时间，防止频繁更新一次
	 * 
	 * @param redisKey
	 * @return
	 */
	public static boolean isUpdateTokenTime(String redisKey) {
		if (isUpdateTokenMap.containsKey(redisKey)) {
			Long value = isUpdateTokenMap.get(redisKey);
			if (value != null) {
				int REDIS_TOKEN_EXPIRE_UPDATE_TIME_MS = SystemConfigFactory.getInstance().getSystemConfigInt(SystemConfigKey.REDIS_TOKEN_EXPIRE_UPDATE_TIME_MS);
				if ((System.currentTimeMillis() - value) < REDIS_TOKEN_EXPIRE_UPDATE_TIME_MS) {
					return false;
				}
			}
		}
		isUpdateTokenMap.put(redisKey, System.currentTimeMillis());
		return true;
	}

	/**
	 * 当前在线用户数
	 * 
	 * @return
	 */
	public static long getOnlineUserCount() {
		long count = 0;
		ConcurrentHashMap<String, Long> isUpdateTokenMapTmp = new ConcurrentHashMap<>();
		isUpdateTokenMapTmp.putAll(isUpdateTokenMap);
		if (isUpdateTokenMapTmp.keySet().size() > 0) {
			int REDIS_TOKEN_EXPIRE_TIME_SEC = SystemConfigFactory.getInstance().getSystemConfigInt(SystemConfigKey.REDIS_TOKEN_EXPIRE_TIME_SEC);
			for (Iterator<String> iterator = isUpdateTokenMapTmp.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				Long value = isUpdateTokenMapTmp.get(key);
				if (value != null) {
					if ((System.currentTimeMillis() - value) < (REDIS_TOKEN_EXPIRE_TIME_SEC * 1000)) {
						count++;
					} else {
						isUpdateTokenMap.remove(key);
					}
				}
			}
		}
		return count;
	}

	/**
	 * 
	 * @param versionLocal
	 * @param versionServer
	 * @return versionLocal > versionServer, return true
	 */
	public static boolean isLimitVersionComparison(String versionLocal, String versionServer) {
		// 2017-11-22：xieqiang：适配以前版本，如果没有上传版本号，不给登录，让用户是用最新的版本
		if (StringUtils.isEmpty(versionLocal)) {
			return true;
		}
		String version1 = versionLocal;
		String version2 = versionServer;
		if (version1 == null || version1.length() == 0 || version2 == null || version2.length() == 0)
			throw new IllegalArgumentException("Invalid parameter!");

		String[] v1s = version1.split("\\.");
		String[] v2s = version2.split("\\.");
		int v2 = 0;
		int v1 = 0;
		for (int i = 3; i >= 0; i--) {
			if (v1s != null && i < v1s.length && !StringUtils.isEmpty(v1s[i])) {
				if (i == 2) {
					v1 += Integer.valueOf(v1s[i]) * (1000);
				}
				if (i == 1) {
					v1 += Integer.valueOf(v1s[i]) * (1000 * 1000);
				}
				if (i == 0) {
					v1 += Integer.valueOf(v1s[i]) * (1000 * 1000 * 1000);
				}
			}
			if (v2s != null && i < v2s.length && !StringUtils.isEmpty(v2s[i])) {
				if (i == 2) {
					v2 += Integer.valueOf(v2s[i]) * (1000);
				}
				if (i == 1) {
					v2 += Integer.valueOf(v2s[i]) * (1000 * 1000);
				}
				if (i == 0) {
					v2 += Integer.valueOf(v2s[i]) * (1000 * 1000 * 1000);
				}
			}
		}
		if (v1 <= v2) {
			return true;
		}
		return false;
	}

	
	public static Integer getSexFromStr(String sexStr) {
		if (sexStr != null) {
			if ("F".equalsIgnoreCase(sexStr)) {
				return MessageDef.USER_SEX.FEMALE;
			} else  {
				return MessageDef.USER_SEX.MALE;
			}
			
		}
		return null;
	}
	

	
	
	//随机数
	public static String getRand(int len) {
		String code = "";
		for (int i = 0; i < len; i++) {
			code += new Random().nextInt(10);
		}
		return code;
	}
	

	public static  String formatMoney(Double money){
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
	    String moneyHasFormat = 	decimalFormat.format(money);
		moneyHasFormat = moneyHasFormat.replaceAll("\\.", ":");
		moneyHasFormat = moneyHasFormat.replaceAll(",", ".");
		moneyHasFormat = moneyHasFormat.replaceAll(":", ",");
		return  moneyHasFormat;
	}
	
}

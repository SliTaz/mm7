package com.zbensoft.mmsmp.common.util;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPUtil {
	private static Logger log = LoggerFactory.getLogger(IPUtil.class);

	public static String getLocalIP(String startsWith) {
		List<String> ipList = getLocalIPList();
		if (ipList != null && ipList.size() > 0) {
			for (String ipAddress : ipList) {
				if (ipAddress.startsWith(startsWith)) {
					return ipAddress;
				}
			}
		}
		return null;
	}

	public static List<String> getLocalIPList() {
		List<String> ipList = new ArrayList<>();
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
							ipList.add(ip.getHostAddress());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return ipList;
	}

	public static String convertIpToLongString(String ip) throws UnknownHostException {
		return toHexString(InetAddress.getByName(ip).getAddress());
	}

	/**
	 * To hex string.
	 * 
	 * @param binary
	 *            the byte array to convert
	 * 
	 * @return the string the converted hex string
	 */
	public static String toHexString(byte[] binary) {
		if (binary != null) {
			StringBuffer str = new StringBuffer(binary.length * 2);
			for (int i = 0; i < binary.length; i++) {
				int v = (binary[i] << 24) >>> 24;
				str.append((v < 0x10 ? "0" : "") + Integer.toHexString(v));
			}
			return str.toString();
		}
		return null;
	}
	
	public static void main(String[] args) throws UnknownHostException {
		convertIpToLongString("192.168.1.102");
	}
}

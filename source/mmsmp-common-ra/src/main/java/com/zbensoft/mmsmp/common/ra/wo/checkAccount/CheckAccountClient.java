package com.zbensoft.mmsmp.common.ra.wo.checkAccount;

import com.zbensoft.mmsmp.common.ra.wo.hibernate.StatisticsBilling;
import com.zbensoft.mmsmp.common.ra.wo.order.Sign;
import com.zbensoft.mmsmp.common.ra.wo.util.PropertiesHelper;
//import com.sinovatech.base.cert.SignException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.apache.log4j.Logger;

public class CheckAccountClient {
	private static final Logger logger = Logger.getLogger(CheckAccountClient.class);
	private CheckAccountService checkAccountService;
	private static char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	public void check(String url) {
		CheckAccountRequest request = new CheckAccountRequest();
		request.setSPNO(PropertiesHelper.getString("spno", null));
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(5, -1);

		request.setREQDATE(format.format(calendar.getTime()));

		request.setRDPWD(generateRandomCode(6));
		try {
			request.setSIGN(URLEncoder.encode(Sign.getSign("SPNO=" + request.getSPNO() + "&REQDATE=" + request.getREQDATE() + "&RDPWD=" + request.getRDPWD()), "UTF-8"));
			logger.info("没有加密之前的签名为：SPNO=" + request.getSPNO() + "&REQDATE=" + request.getREQDATE() + "&RDPWD=" + request.getRDPWD());
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		url = url + "&SPNO=" + request.getSPNO() + "&REQDATE=" + request.getREQDATE() + "&RDPWD=" + request.getRDPWD() + "&SIGN=" + request.getSIGN();
		logger.info(url);
		try {
			URL theURL = new URL(url);
			HttpURLConnection httpURL = null;
			httpURL = (HttpURLConnection) theURL.openConnection();
			httpURL.setDoInput(true);
			httpURL.setDoOutput(true);
			httpURL.setRequestMethod("POST");
			httpURL.setRequestProperty("content-type", "text/xml");

			String ret = "";

			if (httpURL.getResponseCode() == 200) {
				getBillFromWO(httpURL.getInputStream(), request.getREQDATE());
			} else
				logger.info("httpURL.getResponseCode() is : " + httpURL.getResponseCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getBillFromWO(InputStream input, String date) {
		logger.info("====开始获取对账清单=====");
		String fileName = "";
		String billFromWOPath = PropertiesHelper.getString("check.account.filePath", null);
		fileName = billFromWOPath + date + ".txt";
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		BufferedWriter writer = null;
		StringBuilder builder = new StringBuilder();
		String temp = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName, true));
			while ((temp = reader.readLine()) != null) {
				builder.append(temp).append("\r\n");
				if (!temp.startsWith("RECORD_END")) {
					verificatiomOfAccount(temp);
				}
			}

			writer.write(builder.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
		}
		logger.info("====结束获取对账清单=====");
	}

	private void verificatiomOfAccount(String temp) {
		int success = 0;
		int fail = 0;
		int ignore = 0;
		temp = "0042,10004341109,20100117144022,131855,20100117143922,U139002999211,chengMing, 13818189316,300,00";
		String[] ss = temp.split(",");
		String sequenceId = ss[1];
		if (!this.checkAccountService.isExistedStatisticsBilling(sequenceId)) {
			StatisticsBilling billing = new StatisticsBilling();
			billing.setSpno(ss[0]);
			billing.setSpOrderid(ss[1]);
			billing.setWopaytime(ss[2]);
			billing.setSprequestsequenceid(ss[3]);
			billing.setRequesttime(ss[4]);
			billing.setUserid(ss[5]);
			billing.setUername(ss[6]);
			billing.setUserphonenumber(ss[7]);
			billing.setFee(Integer.valueOf(ss[8] == null ? 0 : Integer.parseInt(ss[8])));
			billing.setPaytype(ss[9]);
			billing.setCreateDate(new Date());
			this.checkAccountService.add(billing);
			logger.info("save to StatisticsBilling,,,," + temp);
		} else {
			ignore++;
		}
		logger.info("成功处理" + success + "条；" + "处理失败" + fail + "条；" + "忽略处理" + ignore + "条；");
	}

	public static void main(String[] args) {
		new CheckAccountClient().check(null);
	}

	public static String generateRandomCode(int codeCount) {
		Random random = new Random();
		StringBuffer randomCode = new StringBuffer();

		for (int i = 0; i < codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(10)]);
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}

	public CheckAccountService getCheckAccountService() {
		return this.checkAccountService;
	}

	public void setCheckAccountService(CheckAccountService checkAccountService) {
		this.checkAccountService = checkAccountService;
	}
}

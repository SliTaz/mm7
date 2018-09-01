package com.zbensoft.mmsmp.common.ra.send.util;

import com.zbensoft.mmsmp.common.ra.send.sgipsms.ClientHandler;
import com.zbensoft.mmsmp.common.ra.send.sgipsms.Deliver;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl.TcpClientImpl;
import java.util.Random;
import org.apache.log4j.Logger;

public class SmsSenderUtil {
	private static final Logger logger = Logger.getLogger(SmsSenderUtil.class);

	private static char[] codeSequence = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	public static void deliver2SP(String url, String spNumber, int messagecoding, String messagecontent, String reserve, String userNumber, int TP_pid, int TP_udhi) {
		logger.info("=====>the url is : " + url);

		if ((url != null) && (!"".equals(url))) {
			String host = url.split(":")[0];
			String port = url.split(":")[1];

			Deliver de = new Deliver();
			de.Sequence_Number_1 = Integer.valueOf(generateRandomCode(6) >> 0);
			de.Sequence_Number_2 = Integer.valueOf(generateRandomCode(9));
			de.Sequence_Number_3 = Integer.valueOf(generateRandomCode(9));
			messagecoding = 0;
			de.MessageCoding = ((byte) messagecoding);
			de.MessageContent = messagecontent;
			de.Reserve = reserve;
			de.SPNumber = spNumber;
			de.TP_pid = ((byte) TP_pid);
			de.TP_udhi = ((byte) TP_udhi);
			if (!userNumber.startsWith("86")) {
				userNumber = "86" + userNumber;
			}
			de.UserNumber = userNumber;
			try {
				TcpClientImpl tcp = new TcpClientImpl();
				ClientHandler handler = new ClientHandler(tcp, de);
				tcp.setDataHandler(handler, 1024);
				tcp.connect(host, Integer.parseInt(port));
				tcp.send(handler.login());
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("=====>deliver msg: " + de);
		} else {
			logger.info("=====>error! sp ipaddress or port is empty");
		}
	}

	public static int generateRandomCode(int codeCount) {
		Random random = new Random();
		StringBuffer randomCode = new StringBuffer();

		for (int i = 0; i < codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(9)]);
			randomCode.append(strRand);
		}
		return Integer.parseInt(randomCode.toString());
	}
}

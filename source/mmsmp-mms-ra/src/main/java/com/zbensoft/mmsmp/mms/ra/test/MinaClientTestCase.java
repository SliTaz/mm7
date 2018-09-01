package com.zbensoft.mmsmp.mms.ra.test;

import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.mms.ra.mmsagent.MinaClientProxy;
import java.io.PrintStream;

public class MinaClientTestCase {
	public static void main(String[] args) {
		MinaClientProxy proxy = new MinaClientProxy();
		proxy.setHost("127.0.0.1");
		proxy.setPort(8008);

		for (int i = 0; i < 100; i++)
			try {
				System.out.println(i);

				MO_MMDeliverSPMessage msg = new MO_MMDeliverSPMessage();
				proxy.deliver(msg);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}
}

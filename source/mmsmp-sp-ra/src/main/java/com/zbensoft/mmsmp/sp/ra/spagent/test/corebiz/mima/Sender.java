package com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz.mima;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import org.apache.mina.core.session.IoSession;

public class Sender {
	public static IoSession session;

	public static int send(AbstractMessage message) {
		if (message == null) {
			return 0;
		}
		session.write(message);
		return 1;
	}
}

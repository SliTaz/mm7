package com.zbensoft.mmsmp.common.ra.send.sgipsms;

import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.IClientHandler;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl.TcpClientImpl;
import java.io.Serializable;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

public class ClientHandler implements IClientHandler {
	private static final Logger logger = Logger.getLogger(ClientHandler.class);
	TcpClientImpl tcp;
	Deliver de;
	int i = 0;

	public ClientHandler(TcpClientImpl _tcp, Deliver _de) {
		this.tcp = _tcp;
		this.de = _de;
	}

	public void onConnect(String paramString, int paramInt) {
	}

	public void onDisconnect() {
	}

	public void onReceiveMsg(byte[] bs) {
		try {
			Header header = new Header();
			header.unserialize(ByteBuffer.wrap(bs));

			if (header.Command_ID.intValue() == -2147483647) {
				BindResp resp = new BindResp();
				resp.unserialize(ByteBuffer.wrap(bs));

				if (resp.Result == 0)
					try {
						this.tcp.send(this.de.serialize().array());
					} catch (Exception e) {
						this.tcp.disconnect();
					}
				else {
					try_5_login();
				}
			}
			if (header.Command_ID.intValue() == -2147483644)
				release_conn();
		} catch (Exception e) {
			this.tcp.disconnect();
		}
	}

	private void try_5_login() throws InterruptedException {
		if (this.i++ < 5) {
			Thread.sleep(5000L);
			this.tcp.send(login());
		}
	}

	private void release_conn() {
		try {
			this.tcp.send(new UnBind().serialize().array());
		} catch (Exception localException) {
		} finally {
			if (this.tcp.isConnected())
				this.tcp.disconnect();
		}
	}

	public void onReceiveMsg(Serializable paramSerializable) {
	}

	public void onSendedMsg(byte[] paramArrayOfByte) {
	}

	public void onSendedMsg(Serializable paramSerializable) {
	}

	public int slice(byte[] paramArrayOfByte) {
		return paramArrayOfByte.length;
	}

	public byte[] login() {
		Bind bind = new Bind();
		return bind.serialize().array();
	}
}

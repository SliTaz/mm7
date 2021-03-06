package com.zbensoft.mmsmp.vac.ra.mina.vac;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.zbensoft.mmsmp.vac.ra.aaa.Bind;
import com.zbensoft.mmsmp.vac.ra.aaa.BindResp;
import com.zbensoft.mmsmp.vac.ra.aaa.CheckPriceConfirmResp;
import com.zbensoft.mmsmp.vac.ra.aaa.CheckPriceResp;
import com.zbensoft.mmsmp.vac.ra.aaa.Handset;
import com.zbensoft.mmsmp.vac.ra.aaa.HandsetResp;
import com.zbensoft.mmsmp.vac.ra.aaa.Header;
import com.zbensoft.mmsmp.vac.ra.aaa.Unbind;
import com.zbensoft.mmsmp.vac.ra.aaa.UnbindResp;
import com.zbensoft.mmsmp.vac.ra.util.PropertiesHelper;

public class VACClientHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(VACClientHandler.class);

	public static final int _C = PropertiesHelper.getVacAaaIdleC().intValue();
	public static final int _T = PropertiesHelper.getVacAaaIdleT().intValue();
	public static final int _N = PropertiesHelper.getVacAaaIdleN().intValue();
	private long lastReceived = System.currentTimeMillis();
	private String threadname;
	private VACConnectManager connectManager = null;

	public VACClientHandler(String threadname) {
		this.threadname = threadname;
	}

	private VACConnectManager getConnectManagerInstance() {
		if (this.connectManager == null) {
			this.connectManager = new VACConnectManager(this.threadname);
		}
		return this.connectManager;
	}

	public void sessionOpened(IoSession session) {
		session.getConfig().setIdleTime(IdleStatus.WRITER_IDLE, _C);
		session.getConfig().setIdleTime(IdleStatus.READER_IDLE, _T);

		init_sender(session);
		do_login(session);
	}

	private void init_sender(IoSession session) {
		IoSession session1 = (IoSession) VACSender.sessionMap.get(this.threadname);
		if (session1 != null) {
			VACSender.sessionMap.remove(this.threadname);
		}
		VACSender.sessionMap.put(this.threadname, session);
	}

	private void sender_start() {
		Boolean flag = (Boolean) VACSender.flagMap.get(this.threadname);
		if (flag == null)
			VACSender.flagMap.put(this.threadname, Boolean.valueOf(true));
	}

	public static void main(String[] args) {
		new VACClientHandler("qqq").sender_start();
	}

	private void do_login(IoSession session) {
		logger.info("do_login ...");

		Bind bind = new Bind(this.threadname);
		do_write(session, bind);
	}

	public void sessionClosed(IoSession session) {
		logger.error("Close connection, start ConnectManager.", null);
		getConnectManagerInstance().start();
	}

	public void sessionIdle(IoSession session, IdleStatus status) {
		if (status == IdleStatus.WRITER_IDLE) {
			logger.info("writer idle, send handset.");
			Handset handset = new Handset();
			do_write(session, handset);
		}
		if (status == IdleStatus.READER_IDLE) {
			logger.info("reader idle, send handset.");
			Handset handset = new Handset();
			do_write(session, handset);
		}
		if (session.getIdleCount(IdleStatus.READER_IDLE) >= _N - 1)
			session.close(true);
	}

	public void messageReceived(IoSession session, Object message) {
		IoBuffer buf = (IoBuffer) message;
		logger.info("IoBuffer posioton=" + buf.position() + ", capacity=" + buf.capacity() + ", limit=" + buf.limit());
		ByteBuffer bb = buf.buf();
		logger.info("ByteBuffer posioton=" + bb.position() + ", capacity=" + bb.capacity() + ", limit=" + bb.limit());

		Header header = new Header();
		do_read(bb, header);
		switch (header.getCommandId().intValue()) {
		case -2147483647:
			BindResp o = new BindResp();
			do_read(bb, o);
			if (o.getResult_Code() == BindResp.SUCCESS) {
				logger.info("login successful.");
				sender_start();
			} else {
				logger.warn("login failed: " + o);
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException localInterruptedException) {
				}
				do_login(session);
			}
			break;
		case -2147483643:
			this.lastReceived = System.currentTimeMillis();

			CheckPriceResp checkPriceResp = new CheckPriceResp();
			do_read(bb, checkPriceResp);
			VACSender.putResponse(checkPriceResp);
			break;
		case -2147483642:
			CheckPriceConfirmResp checkPriceConfirmResp = new CheckPriceConfirmResp();
			do_read(bb, checkPriceConfirmResp);
			VACSender.putResponse(checkPriceConfirmResp);
			break;
		case 268435459:
			logger.info("in handset :last Received CheckPriceResp time is : " + this.lastReceived + " and delta time : " + (System.currentTimeMillis() - this.lastReceived));

			if (System.currentTimeMillis() - this.lastReceived > 30000L) {
				logger.warn("we didn't receive checkprice resp for 30 secs ");
			}

			Handset handset = new Handset();
			do_read(bb, handset);
			HandsetResp ret = new HandsetResp();
			do_write(session, ret);
			break;
		case -2147483645:
			logger.info("in handset Resp :last Received CheckPriceResp time is : " + this.lastReceived + " and delta time : " + (System.currentTimeMillis() - this.lastReceived));

			if (System.currentTimeMillis() - this.lastReceived > 30000L) {
				logger.error("we didn't receive checkprice resp for 30 secs ");
			}

			HandsetResp handsetResp = new HandsetResp();
			do_read(bb, handsetResp);
			break;
		case 268435458:
			Unbind unbind = new Unbind();
			do_read(bb, unbind);
			UnbindResp unbindRespRet = new UnbindResp();
			do_write(session, unbindRespRet);
			break;
		default:
			Header x = new Header();
			do_read(bb, x);
			logger.warn(x);
			logger.warn(buf.getHexDump());
		}
	}

	private void do_write(IoSession session, Header o) {
		try {
			IoBuffer buffer = IoBuffer.wrap(o.serialize());
			session.write(buffer);
		} catch (Exception e) {
			logger.error("do_write(IoSession, Header)", e);
		}
		log(o, true);
	}

	private void log(Header o, boolean flag) {
		if (("Handset".equals(o.getClass().getSimpleName())) || ("HandsetResp".equals(o.getClass().getSimpleName()))) {
			if (flag)
				logger.info(o.getClass().getName() + " => " + o.getSequenceId());
			else
				logger.info(o.getClass().getName() + " <= " + o.getSequenceId());
		} else if (!"Header".equals(o.getClass().getSimpleName()))
			logger.info(o);
	}

	private void do_read(ByteBuffer bb, Header o) {
		try {
			o.unserialize(bb.duplicate());
		} catch (Exception e) {
			logger.error("do_read(ByteBuffer, Header)", e);
		}
		log(o, false);
	}

}

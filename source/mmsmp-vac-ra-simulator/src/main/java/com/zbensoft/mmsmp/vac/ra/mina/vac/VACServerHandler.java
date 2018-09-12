package com.zbensoft.mmsmp.vac.ra.mina.vac;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.zbensoft.mmsmp.common.ra.vac.aaa.message.Common;
import com.zbensoft.mmsmp.vac.ra.aaa.Bind;
import com.zbensoft.mmsmp.vac.ra.aaa.BindResp;
import com.zbensoft.mmsmp.vac.ra.aaa.CheckPrice;
import com.zbensoft.mmsmp.vac.ra.aaa.CheckPriceConfirm;
import com.zbensoft.mmsmp.vac.ra.aaa.CheckPriceConfirmResp;
import com.zbensoft.mmsmp.vac.ra.aaa.CheckPriceResp;
import com.zbensoft.mmsmp.vac.ra.aaa.Handset;
import com.zbensoft.mmsmp.vac.ra.aaa.HandsetResp;
import com.zbensoft.mmsmp.vac.ra.aaa.Header;
import com.zbensoft.mmsmp.vac.ra.aaa.TLV;
import com.zbensoft.mmsmp.vac.ra.aaa.Unbind;
import com.zbensoft.mmsmp.vac.ra.aaa.UnbindResp;
import com.zbensoft.mmsmp.vac.ra.util.PropertiesHelper;

public class VACServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(VACServerHandler.class);

	public static final int _C = PropertiesHelper.getVacAaaIdleC().intValue();
	public static final int _T = PropertiesHelper.getVacAaaIdleT().intValue();
	public static final int _N = PropertiesHelper.getVacAaaIdleN().intValue();
	private long lastReceived = System.currentTimeMillis();
	
	
	private static Map<String,String> orderRelationMap=new HashMap<String,String>();
	
	// private String threadname;
	// private VACConnectManager connectManager = null;

	public VACServerHandler() {
	}

	// private VACConnectManager getConnectManagerInstance() {
	// if (this.connectManager == null) {
	// this.connectManager = new VACConnectManager(this.threadname);
	// }
	// return this.connectManager;
	// }

	// public void sessionOpened(IoSession session) {
	// session.getConfig().setIdleTime(IdleStatus.WRITER_IDLE, _C);
	// session.getConfig().setIdleTime(IdleStatus.READER_IDLE, _T);
	//
	// init_sender(session);
	// do_login(session);
	// }

	// private void init_sender(IoSession session) {
	// IoSession session1 = (IoSession) VACSender.sessionMap.get(this.threadname);
	// if (session1 != null) {
	// VACSender.sessionMap.remove(this.threadname);
	// }
	// VACSender.sessionMap.put(this.threadname, session);
	// }

	// private void sender_start() {
	// Boolean flag = (Boolean) VACSender.flagMap.get(this.threadname);
	// if (flag == null)
	// VACSender.flagMap.put(this.threadname, Boolean.valueOf(true));
	// }

	// public static void main(String[] args) {
	// new VACServerHandler("qqq").sender_start();
	// }

	// private void do_login(IoSession session) {
	// logger.info("do_login ...");
	//
	// Bind bind = new Bind(this.threadname);
	// do_write(session, bind);
	// }

	// public void sessionClosed(IoSession session) {
	// logger.error("Close connection, start ConnectManager.", null);
	// getConnectManagerInstance().start();
	// }

	// public void sessionIdle(IoSession session, IdleStatus status) {
	// if (status == IdleStatus.WRITER_IDLE) {
	// logger.info("writer idle, send handset.");
	// Handset handset = new Handset();
	// do_write(session, handset);
	// }
	// if (status == IdleStatus.READER_IDLE) {
	// logger.info("reader idle, send handset.");
	// Handset handset = new Handset();
	// do_write(session, handset);
	// }
	// if (session.getIdleCount(IdleStatus.READER_IDLE) >= _N - 1)
	// session.close(true);
	// }

	public void messageReceived(IoSession session, Object message) {
		IoBuffer buf = (IoBuffer) message;
		logger.info("IoBuffer posioton=" + buf.position() + ", capacity=" + buf.capacity() + ", limit=" + buf.limit());
		ByteBuffer bb = buf.buf();
		logger.info("ByteBuffer posioton=" + bb.position() + ", capacity=" + bb.capacity() + ", limit=" + bb.limit());

		Header header = new Header();
		do_read(bb, header);
		switch (header.getCommandId().intValue()) {
		case Header.CMDID_Bind:// -2147483647:
			Bind bind = new Bind("thread0");
			do_read(bb, bind);
			BindResp bindResp = new BindResp();
			bindResp.setResult_Code(BindResp.SUCCESS);
			do_write(session, bindResp);
			break;
		case Header.CMDID_CheckPrice:

			CheckPrice checkPrice = new CheckPrice(0, "thread0");
			do_read(bb, checkPrice);
			CheckPriceResp checkPriceResp = new CheckPriceResp();
			checkPriceResp.setResult_Code(0);
			if(checkPrice.getOperation_Type()==CheckPrice.ORDER_RELATION){
				checkPriceResp.setResult_Code(1201);
				
				//增加判断 第一次为1201.第二次为1200
				String phoneNumber=checkPrice.getOA();
				logger.info("phoneNumber:"+phoneNumber);
				boolean flag_contains=orderRelationMap.containsKey(phoneNumber);
				if(!flag_contains){
					checkPriceResp.setResult_Code(1201);
					orderRelationMap.put(phoneNumber, "true");
				}else{
					checkPriceResp.setResult_Code(1200);
					try {
						orderRelationMap.remove(phoneNumber);
					} catch (Exception e) {}
				}
				logger.info("Result_Code:"+checkPriceResp.getResult_Code());
			}
			
			
					
			checkPriceResp.setSequenceId(checkPrice.getSequenceId());
			checkPriceResp.setLinkID(new TLV(Common.TAG.LinkID, (short) 20, "2018tt123213443215"));
			checkPriceResp.setNeedConfirm((byte) 1);
			do_write(session, checkPriceResp);
			break;
		case Header.CMDID_CheckPriceConfirm:

			CheckPriceConfirm checkPriceConfirm = new CheckPriceConfirm(0, "thread0");
			do_read(bb, checkPriceConfirm);
			CheckPriceConfirmResp checkPriceConfirmResp = new CheckPriceConfirmResp();
			checkPriceConfirmResp.setResult_Code(0);
			checkPriceConfirmResp.setSequenceId(checkPriceConfirm.getSequenceId());
			do_write(session, checkPriceConfirmResp);
			break;
		case Header.CMDID_Handset:
			logger.info("in handset :last Received CheckPriceResp time is : " + this.lastReceived + " and delta time : " + (System.currentTimeMillis() - this.lastReceived));

			if (System.currentTimeMillis() - this.lastReceived > 30000L) {
				logger.warn("we didn't receive checkprice resp for 30 secs ");
			}

			Handset handset = new Handset();
			do_read(bb, handset);
			HandsetResp ret = new HandsetResp();
			do_write(session, ret);
			break;
		case Header.CMDID_HandsetResp:
			logger.info("in handset Resp :last Received CheckPriceResp time is : " + this.lastReceived + " and delta time : " + (System.currentTimeMillis() - this.lastReceived));

			if (System.currentTimeMillis() - this.lastReceived > 30000L) {
				logger.error("we didn't receive checkprice resp for 30 secs ");
			}

			HandsetResp handsetResp = new HandsetResp();
			do_read(bb, handsetResp);
			break;
		case Header.CMDID_UnBind:
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

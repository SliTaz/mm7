package com.zbensoft.mmsmp.corebiz.handle.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.CheckRequest;
import com.zbensoft.mmsmp.common.ra.common.message.CheckResponse;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MMHttpSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SPMMHttpMessage;
import com.zbensoft.mmsmp.common.ra.common.message.WOCheckRequest;
import com.zbensoft.mmsmp.common.ra.common.message.WOCheckResponse;
import com.zbensoft.mmsmp.common.ra.send.sgipsms.SmsSenderDto;
import com.zbensoft.mmsmp.common.ra.send.util.XmlElementReplace;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.DemandDto;
import com.zbensoft.mmsmp.corebiz.cache.DataCache;
import com.zbensoft.mmsmp.corebiz.dao.DaoUtil;
import com.zbensoft.mmsmp.corebiz.message.MT_MMHttpSPMessage_Report;
import com.zbensoft.mmsmp.corebiz.message.MmsDBListener;
import com.zbensoft.mmsmp.corebiz.message.MmsHistoryMessage;
import com.zbensoft.mmsmp.corebiz.message.MmsUpdateListener;
import com.zbensoft.mmsmp.corebiz.message.MmsUserMessage;
import com.zbensoft.mmsmp.corebiz.route.IMessageRouter;
import com.zbensoft.mmsmp.corebiz.service.mm7.SubmitReq;
import com.zbensoft.mmsmp.corebiz.service.mm7.SubmitResp;
import com.zbensoft.mmsmp.corebiz.util.HttpRequestHelper;
import com.zbensoft.mmsmp.corebiz.util.StringUtil;

public class MmsBusinessHandlerImpl {
	static final Logger logger = Logger.getLogger(MmsBusinessHandlerImpl.class);

	static final String SERVICEN_NUMBER = "10655565";

	static final String MMSMH = "MMS-MH";
	static final Integer WHITE_FLAG = Integer.valueOf(2);
	static final Integer BLACK_FLAG = Integer.valueOf(1);

	IMessageRouter messageRouter;

//	DaoUtil daoUtil;

	DataCache dataCache;
	ConcurrentHashMap<String, AbstractMessage> dataMap;
	ConcurrentHashMap<String, MT_MMHttpSPMessage_Report> mmsReportDataMap;
	ConcurrentHashMap<String, AbstractMessage> commonMap;
	MmsDBListener mmsDBListener;
	MmsUpdateListener mmsUpdateListener;
	boolean debug = false;

	public MmsBusinessHandlerImpl() {
		String value = System.getProperty("debug");
		if ((value != null) && (value.equalsIgnoreCase("true"))) {
			this.debug = true;
		}
	}

	public void doHandler(AbstractMessage message) {
		try {
			if ((message instanceof MO_MMDeliverSPMessage)) {
				doMO((MO_MMDeliverSPMessage) message);
			} else if ((message instanceof MT_SPMMHttpMessage)) {
				doMT((MT_SPMMHttpMessage) message);
			} else if ((message instanceof MT_ReportMessage)) {
				doMH((MT_ReportMessage) message);
			} else if ((message instanceof MO_ReportMessage)) {
				doMR((MO_ReportMessage) message);
			} else if ((message instanceof WOCheckResponse)) {
				doCR((WOCheckResponse) message);
			} else if ((message instanceof CheckResponse)) {
				doCR((CheckResponse) message);
			}
		} catch (Exception ex) {
			logger.info("mms business exception:" + ex.getMessage());
			logger.error("mms business exception:", ex);
		}
	}

	public void doMO(MO_MMDeliverSPMessage msg) {
		String gmsgid = msg.getGlobalMessageid();
		String sendto = msg.getTo().get(0).toString();
		String phone = StringUtil.getPhone11(msg.getSender());

		sendto = sendto.replace("[", "");
		sendto = sendto.replace("]", "");

		MT_SMMessage smsmt = new MT_SMMessage();
		smsmt.setGlobalMessageid(gmsgid);
		smsmt.setRcvAddresses(new String[] { "86" + phone });
		smsmt.setServiceCode("10655565");
		smsmt.setLinkId(String.valueOf(System.currentTimeMillis()));

		DemandDto dto = this.dataCache.getDemandDtoBySendto(sendto);

		if ((dto == null) || (dto.getServiceid() == null) || ("".equals(dto.getServiceid().trim()))) {
			logger.info("mmsmo service not exists [gmsgid:" + gmsgid + ",phone:" + phone + ",sendto:" + sendto + "]");

			smsmt.setSmsText(this.dataCache.queryErrorMsgTemplate() + "发送失败，请检查接入号是否正确！");
			sendSmsMessage(smsmt);
			saveMmsmoHistory(msg, "1");
			return;
		}
		if ((dto.getSpreporturl() == null) || ("".equals(dto.getSpreporturl().trim()))) {
			logger.info("mmsmo spurl is empty [gmsgid:" + gmsgid + ",phone:" + phone + ",sendto:" + sendto + "]");
			saveMmsmoHistory(msg, "1");
			return;
		}

		msg.setSendurl(dto.getSpreporturl());
		msg.setServicesId(dto.getServiceid());
		msg.setProductId(dto.getProductId());
		msg.setSpid(dto.getSpid());
		msg.setNeedConfirm(dto.getNeedConfirm());
		msg.setCheckType(4);

		if (this.dataCache.isWhiteUser(phone, dto.getProductId(), null)) {
			logger.info("mmsmo user is white list[gmsgid:" + gmsgid + ",phone:" + phone + ",productId:"
					+ dto.getProductId() + "]");
			this.messageRouter.doRouter(msg);

			// SmsSenderDto ssd = this.dataCache.getDianBoByNumber(sendto);
			String smsText = "";
			//
			// if ((ssd == null) || (ssd.getVasid() == null))
			// {
			// smsText = this.dataCache.queryErrorMsgTemplate() + "你好，没有这个业务";
			// }
			// else
			// {
			smsText = this.dataCache.queryUpOndemandMessage();
			smsText = smsText.replace("{0}", "test1");// ssd.getServicename());
			smsText = smsText.replace("{1}", "test2");// ssd.getFee());
			// }

			smsmt.setSmsText(smsText);
			sendSmsMessage(smsmt);

		} else {
			this.dataMap.put(gmsgid, msg);

			logger.info("mmsmo vac auth(4) request[gmsgid:" + gmsgid + ",phone:" + phone + ",sendto:" + sendto
					+ ",needconfirm:" + msg.getNeedConfirm() + "]");

			CheckRequest cr4 = new CheckRequest(phone, msg.getProductId(), msg.getSpid(), null, null, "4", null);
			cr4.setGlobalMessageid(gmsgid);
			cr4.setResponse(true);

			this.messageRouter.doRouter(cr4);
		}
	}

	public void doMH(MT_ReportMessage msg) {
		String gmsgid = msg.getGlobalMessageid();
		String messageid = msg.getMessageid();
		String reqid = msg.getReqId();
		String spurl = msg.getSpUrl();
		String status = msg.getStatus();
		String content = msg.getContent();
		String mmsCode = msg.getMsgType();

		logger.info("mmsmh submit result transfer[gmsgid:" + gmsgid + ",messageid:" + messageid + ",reqid:" + reqid
				+ ",status:" + status + "]");

		MO_ReportMessage mr = new MO_ReportMessage();
		mr.setGlobalMessageid(gmsgid);
		mr.setReportUrl(spurl);
		mr.setMessageId(messageid);
		mr.setContent(content);

		if ((reqid == null) || ("".equals(reqid.trim()))) {
			logger.info("mmsmh submit result reqid is null[gmsgid:" + gmsgid + ",messageid:" + messageid + ",reqid:"
					+ reqid + ",status:" + status + "]");

		} else if (this.mmsReportDataMap.get(gmsgid) == null) {
			logger.info("mmsmh submit result match failure[gmsgid:" + gmsgid + ",messageid:" + messageid + ",reqid:"
					+ reqid + ",status:" + status + "]");

		} else {

			logger.info("mmsmh submit result match success[gmsgid:" + gmsgid + ",messageid:" + messageid + ",reqid:"
					+ reqid + ",status:" + status + "]");

			String gmhid = "MMS-MH" + reqid;

			MT_MMHttpSPMessage_Report mt = (MT_MMHttpSPMessage_Report) this.mmsReportDataMap.get(gmsgid);
			mt.setSourceGlobalMessageid(gmsgid);
			mt.setGlobalMessageid(gmhid);
			mt.setGlobalCreateTime(mt.getGlobalCreateTime());
			mt.setGlobalMessageTime(System.currentTimeMillis());
			mt.setReqId(reqid);

			mr.setServiceCode(mt.getSp_productid());

			this.mmsReportDataMap.put(gmhid, mt);
		}

		this.messageRouter.doRouter(mr);

		removeReportMessage(gmsgid);

		try {
			long btime = System.currentTimeMillis();

			MmsHistoryMessage mhm = new MmsHistoryMessage();
			mhm.setStatus(status);
			mhm.setMessageId(messageid);
			mhm.setReqId(reqid);
			mhm.setMmsGreCode(mmsCode);
			mhm.setReceiveDate(new Date());
			this.mmsUpdateListener.put(mhm);

			logger.info("mmsmh save mh history success[gmsgid:" + gmsgid + ",messageid:" + messageid + ",reqid:" + reqid
					+ ",status:" + status + ",mmsCode:" + mmsCode + ",exectime:" + (System.currentTimeMillis() - btime)
					+ "]");
		} catch (Exception ex) {
			logger.error("mmsmh submit result update", ex);
		}
	}

	public void doMR(MO_ReportMessage mr) {
		String gmsgid = mr.getGlobalMessageid();
		String reqid = mr.getCorrelator();
		String phone = StringUtil.getPhone11(mr.getRecipient());

		String mmscode = mr.getStatus();

		String gmhid = "MMS-MH" + reqid;

		if (this.mmsReportDataMap.get(gmhid) != null) {
			MT_MMHttpSPMessage_Report mt = (MT_MMHttpSPMessage_Report) this.mmsReportDataMap.get(gmhid);
			String mtmessageid = mt.getMessageid();
			String sequence = mt.getSequence();
			String spid = mt.getSpid();
			String sendnumber = "";

			logger.info("mmsmr status report transfer[gmsgid:" + mt.getGlobalMessageid() + ",mtmessageid:" + mtmessageid
					+ ",gmrid :" + gmsgid + ",phone:" + phone + ",sequence:" + sequence + ",mmscode:" + mmscode + "]");

			mr.setServiceCode(mt.getSp_productid());
			mr.setGlobalMessageid(mt.getGlobalMessageid());
			mr.setReportUrl(mt.getGlobalReportUrl());
			mr.setContent(StringUtil.getReportString(String.valueOf(new Random().nextInt()), sendnumber, phone,
					StringUtil.getTime0800(), mtmessageid, mmscode));
			this.messageRouter.doRouter(mr);

			if ((sequence != null) && (!sequence.trim().equals(""))) {
				logger.info("mmsmt on demand vac auth(6) request[gmsgid:" + mt.getSourceGlobalMessageid() + ",phone:"
						+ phone + ",sequence:" + sequence + ",sp_productid:" + mt.getSp_productid() + ",spid:" + spid
						+ ",serviceid" + mt.getServiceid() + "]");

				CheckRequest cr6 = new CheckRequest(phone, mt.getSp_productid(), spid, mt.getServiceid(), sequence, "6",
						null);
				cr6.setGlobalMessageid(mt.getSourceGlobalMessageid());
				cr6.setResponse(false);

				this.messageRouter.doRouter(cr6);
			}

			removeReportMessage(gmhid);
		} else {
			logger.info("mmsmr status report match failure[gmsgid:" + gmsgid + ",matchkey:" + gmhid + ",reqid:" + reqid
					+ "]");
		}

		try {
			long btime = System.currentTimeMillis();
			//TODO MO_ReportMessage 入库更新;Already processed;edit
			//this.daoUtil.getDemandDao().updateSpMMSSendRecord("5", reqid, mmscode);
            HttpRequestHelper.updateSpMMSSendRecord("5", reqid, mmscode);
			logger.info("mmsmr save mr history success[gmsgid:" + gmsgid + ",reqid:" + reqid + "mmsCode:" + mmscode
					+ ",exectime:" + (System.currentTimeMillis() - btime) + "]");
		} catch (Exception ex) {
			logger.error("mmsmr report status update", ex);
		}
	}

	public void doMT(MT_SPMMHttpMessage msg) {

		SubmitReq submit= new SubmitReq();
		String gmsgid="";
		String spid="";
		String productid="";
		String linkid="";
		String messageid="";
		String phone="";
		String charge="";
		String sendAdress="";
		String uniqueid="";
		String serviceid="";
		int productSize=0;
		int mmsmtType=0;
		int isPresent=0;
		MT_MMHttpSPMessage mt=new MT_MMHttpSPMessage();
		MO_ReportMessage mr=new MO_ReportMessage();
		InputStream io=null;
		ByteArrayOutputStream os=null;
		ByteArrayInputStream iocontent=null;
		try {
			submit.parser(new String(msg.getContentbyte()));
		} catch (Exception ex) {
			logger.info((new StringBuilder("mmsmt parse content byte failure[gmsgid:")).append(msg.getGlobalMessageid())
					.append("]").toString());
			logger.error((new StringBuilder("mmsmt parse content byte exception[gmsgid:"))
					.append(msg.getGlobalMessageid()).append("]").toString(), ex);
			return;
		}
		gmsgid = msg.getGlobalMessageid();
		spid = submit.getVASPID();
		productid = submit.getServiceCode();
		String products[] = productid.split(",");
		linkid = submit.getLinkedID() != null ? submit.getLinkedID().trim() : "";
		messageid = msg.getMessageid();
		phone = StringUtil.getPhone11(submit.getTo());
		charge = StringUtil.getPhone11(msg.getChargetNumber());
		sendAdress = submit.getSenderAddress();
		String zskey = (new StringBuilder("ZS")).append(phone).append(products[0]).toString();
		String ztkey = (new StringBuilder("ZT")).append(products[0]).toString();
		uniqueid = null;
		serviceid = null;
		String vaspid = null;
		String zsuniqueid = null;
		productSize = products.length;
		mmsmtType = linkid == null || "".equals(linkid) ? 1 : 0;
		isPresent = 0;
		mt.setAuthorization(msg.getAuthorization());
		mt.setContent_Transfer_Encoding(msg.getContent_Transfer_Encoding());
		mt.setMime_Version(msg.getMime_Version());
		mt.setMM7APIVersion(msg.getMM7APIVersion());
		mt.setSOAPAction(msg.getSOAPAction());
		mt.setContentType(msg.getContentType());
		mt.setGlobalMessageid(gmsgid);
		mt.setGlobalCreateTime(msg.getGlobalCreateTime());
		mt.setGlobalMessageTime(System.currentTimeMillis());
		mt.setMessageid(msg.getMessageid());
		mt.setSp_productid(products[0]);
		mt.setLinkId(linkid);
		mt.setSpid(spid);
		mt.setPhone(phone);
		mt.setSourceType(mmsmtType);
		mt.setContentid(msg.getContentid());
		if (gmsgid.startsWith("WO") && commonMap.get(ztkey) != null) {
			logger.info((new StringBuilder("mmsmt send paused user[gmsgid:")).append(gmsgid).append(",to:")
					.append(submit.getTo()).append(",productid:").append(products[0]).append("]").toString());
			WOCheckResponse worsp = (WOCheckResponse) commonMap.get(ztkey);
			String value[] = worsp.getMessageid().split("[|]");
			MmsUserMessage user = new MmsUserMessage();
			user.setUserNumber(phone);
			user.setChargeNumber(charge);
			user.setContentId(Long.parseLong(value[0]));
			user.setServiceId(Long.parseLong(value[1]));
			user.setContentName(value[2]);
			user.setContentFile(value[3]);
			user.setVasId(value[4]);
			user.setVaspId(value[5]);
			user.setProductId(value[6]);
			user.setSendAddress(sendAdress);
			// TODO 插入用户信息;Already processed;pending 临时表，没有表结构
//			daoUtil.getSmsDAO().insertPauseUser(user);
			logger.info((new StringBuilder("mmsmt save paused user[gmsgid:")).append(gmsgid).append(",to:")
					.append(submit.getTo()).append(",productid:").append(products[0]).append(",contentid:")
					.append(user.getContentId()).append("]").toString());
			return;
		}
		if (charge != null && !"".equals(charge)) {
			mt.setChargetNumber(charge);
			isPresent = 1;
			logger.info((new StringBuilder("mmsmt present by own[gmsgid:")).append(gmsgid).append(",to:")
					.append(submit.getTo()).append(",presenter:").append(charge).append(",productid:")
					.append(products[0]).append("]").toString());
		} else if (commonMap.get(zskey) != null) {
			WOCheckResponse worsp = (WOCheckResponse) commonMap.get(zskey);
			charge = worsp.getChargetNumber();
			mt.setChargetNumber(charge);
			commonMap.remove(zskey);
			isPresent = 2;
			logger.info((new StringBuilder("mmsmt present by sp[gmsgid:")).append(gmsgid).append(",to:")
					.append(submit.getTo()).append(",presenter:").append(charge).append(",productid:")
					.append(products[0]).append("]").toString());
		}
		mr.setGlobalMessageid(msg.getGlobalMessageid());
		mr.setTransactionID(submit.getTransactionID());
		mr.setMessageId(msg.getMessageid());
		String mrUrl = dataCache.getSpurlByProductid(products[0]);
		if (mrUrl == null || mrUrl.length() == 0) {
			logger.info((new StringBuilder("mmsmt url does not exist[gmsgid:")).append(gmsgid).append(",to:")
					.append(submit.getTo()).append(",vaspid:").append(submit.getVASPID()).append(",productid:")
					.append(products[0]).append("]").toString());
			String str = SubmitResp.getSubmitResp(submit.getTransactionID(), msg.getMessageid(), "3000",
					"sp receive report url doesn't exist");
			mr.setContent(str);
			messageRouter.doRouter(mr);
			return;
		}
		logger.info((new StringBuilder("mmsmt match url success[gmsgid:")).append(gmsgid).append(",to:")
				.append(submit.getTo()).append(",vaspid:").append(submit.getVASPID()).append(",productid:")
				.append(products[0]).append(",reporturl:").append(mrUrl).append("]").toString());
		mt.setGlobalReportUrl(mrUrl);
		mr.setReportUrl(mrUrl);
		uniqueid = dataCache.getUniqueidByProductid(products[0]);
		if (uniqueid == null || uniqueid.length() == 0) {
			logger.info((new StringBuilder("mmsmt productid does not exist[gmsgid:")).append(gmsgid).append(",to:")
					.append(submit.getTo()).append(",vaspid:").append(submit.getVASPID()).append(",productid:")
					.append(products[0]).append("]").toString());
			String str = SubmitResp.getSubmitResp(submit.getTransactionID(), msg.getMessageid(), "2003",
					"product doesn't exist");
			mr.setContent(str);
			messageRouter.doRouter(mr);
			return;
		}
		String vasIdAndvaspid = dataCache.getServiceIdByProductid(products[0]);
		if (vasIdAndvaspid == null || vasIdAndvaspid.length() == 0) {
			logger.info((new StringBuilder("mmsmt serviceid does not exist[gmsgid:")).append(gmsgid).append(",to:")
					.append(submit.getTo()).append(",vaspid:").append(submit.getVASPID()).append(",productid:")
					.append(productid).append("]").toString());
			String str = SubmitResp.getSubmitResp(submit.getTransactionID(), msg.getMessageid(), "2004",
					"spid doesn't exist");
			mr.setContent(str);
			messageRouter.doRouter(mr);
			return;
		}
		if (productSize == 2) {
			zsuniqueid = dataCache.getUniqueidByProductid(products[1]);
			if (zsuniqueid == null || zsuniqueid.length() == 0) {
				logger.info((new StringBuilder("mmsmt zsproductid does not exist[gmsgid:")).append(gmsgid)
						.append(",to:").append(submit.getTo()).append(",vaspid:").append(submit.getVASPID())
						.append(",productid:").append(products[0]).append("]").toString());
				String str = SubmitResp.getSubmitResp(submit.getTransactionID(), msg.getMessageid(), "2003",
						"zs product doesn't exist");
				mr.setContent(str);
				messageRouter.doRouter(mr);
				return;
			}
		}
		serviceid = vasIdAndvaspid.split("#")[0];
		vaspid = vasIdAndvaspid.split("#")[1];
		mt.setServiceid(serviceid);
		mt.setVaspid(vaspid);
		mt.setUniqueid(uniqueid);
		try {
			if (productSize == 2) {
				//TODO 数据库调用，待修改;Already processed;edit
//				daoUtil.getSmsDAO().saveUserZSMTRecord(phone, charge, submit.getTransactionID(), vaspid, zsuniqueid,msg.getMessageid(), products[1]);
				HttpRequestHelper.saveUserZSMTRecord(phone, charge, submit.getTransactionID(), vaspid, zsuniqueid,msg.getMessageid(), products[1]);
			} else {
				MmsHistoryMessage mmsHM = new MmsHistoryMessage();
				mmsHM.setGlobalMessageId(msg.getGlobalMessageid());
				mmsHM.setMessageId(msg.getMessageid());
				mmsHM.setTransactionId(submit.getTransactionID());
				mmsHM.setUserPhone(phone);
				mmsHM.setSpId(vaspid);
				mmsHM.setServiceId(uniqueid);
				mmsHM.setServiceCode(products[0]);
				mmsHM.setReceiveDate(new Date());
				mmsHM.setType(0);
				mmsDBListener.put(mmsHM);
			}
			logger.info((new StringBuilder("mmsmt save mt history success[gmsgid:")).append(gmsgid).append(",to:")
					.append(submit.getTo()).append(",address:").append(submit.getSenderAddress()).append("]")
					.toString());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			String str = SubmitResp.getSubmitResp(submit.getTransactionID(), msg.getMessageid(), "2005",
					"system is busy now, please try again laster");
			mr.setContent(str);
			messageRouter.doRouter(mr);
			logger.info((new StringBuilder("mmsmt save mt history failure[gmsgid:")).append(gmsgid).append(",to:")
					.append(submit.getTo()).append(",address:").append(submit.getSenderAddress()).append("]")
					.toString());
			return;
		}
		io = null;
		os = null;
		iocontent = null;
		try {
			mt.setServicecodes(serviceid);
			iocontent = new ByteArrayInputStream(msg.getContentbyte());
			io = XmlElementReplace.ReplaceServiceCode(iocontent, serviceid);
			os = new ByteArrayOutputStream();
			byte data[] = new byte[10240];
			int len;
			while ((len = io.read(data)) > 0) {
				os.write(data, 0, len);
				mt.setContentByte(os.toByteArray());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info((new StringBuilder("mmsmt servicecode replace failure[gmsgid:")).append(gmsgid)
					.append(",phone:").append(phone).append(",productid:").append(productid).append(",serviceid:")
					.append(serviceid).append("]]").toString());
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				iocontent.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				io.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		logger.info((new StringBuilder("mmsmt servicecode replace success[gmsgid:")).append(gmsgid).append(",phone:")
				.append(phone).append(",productid:").append(productid).append(",serviceid:").append(serviceid)
				.append("]").toString());
		if (productSize == 2){
			if (dataCache.isExistOrderRelation(charge, uniqueid)) {
				logger.info((new StringBuilder("mmsmt user is zsservice list[gmsgid:")).append(gmsgid).append(",phone:")
						.append(phone).append(",productid:").append(productid).append(",sendadress:").append(sendAdress)
						.append("serviceid:").append(serviceid).append("]").toString());
				messageRouter.doRouter(mt);
				mmsReportDataMap.put(gmsgid, getMMHTttpSpMessageReport(mt));
				return;
			} else {
				logger.info((new StringBuilder("mmsmt user is not order zsservice list[gmsgid:")).append(gmsgid)
						.append(",phone:").append(phone).append(",productid:").append(productid).append(",sendadress:")
						.append(sendAdress).append("serviceid:").append(serviceid).append("]").toString());
				String str = SubmitResp.getSubmitResp(submit.getTransactionID(), msg.getMessageid(), "2005",
						"system is not exist order relation,zs service is faild");
				mr.setContent(str);
				messageRouter.doRouter(mr);
				return;
			}
		}
		int white_type = dataCache.isWhiteUserMMS_MT(phone, productid, sendAdress);
		if (white_type == 2 || white_type == 1 || white_type == 0 || white_type == 30) {
			logger.info((new StringBuilder("mmsmt user is white list[gmsgid:")).append(gmsgid).append(",phone:")
					.append(phone).append(",productid:").append(productid).append(",sendadress:").append(sendAdress)
					.append(", white_type :").append(white_type).append("]").toString());
			messageRouter.doRouter(mt);
			mmsReportDataMap.put(gmsgid, getMMHTttpSpMessageReport(mt));
			return;
		}
		if (white_type == 31) {
			boolean exist = dataCache.isThridOrderExist(phone, productid);
			logger.info((new StringBuilder("mmsmt user is white list[gmsgid:")).append(gmsgid).append(",phone:")
					.append(phone).append(",productid:").append(productid).append(",sendadress:").append(sendAdress)
					.append(", white_type :").append(white_type).append(", thridOrderExist:").append(exist).append(" ]")
					.toString());
			if (!exist) {
				String str = StringUtil.getReportString(
						(new StringBuilder(String.valueOf((new Random()).nextInt(0x186a0)))).toString(), "10655565",
						phone, StringUtil.getTime0800(), messageid, 11201, "OrderRelation Doesn't Exist");
				mr.setContent(str);
				messageRouter.doRouter(mr);
				return;
			} else {
				messageRouter.doRouter(mt);
				mmsReportDataMap.put(gmsgid, getMMHTttpSpMessageReport(mt));
				return;
			}
		}
		int quota = Integer.parseInt(dataCache.getSysParams(mmsmtType != 0 ? "MT_ORDER_QUOTA" : "MT_ONDEMAND_QUOTA", "0"));
		if (quota <= 0) {
			logger.info((new StringBuilder("mmsmt on ")).append(mmsmtType != 0 ? "order" : "demand")
					.append(" quota less zero[gmsgid:").append(gmsgid).append(",phone:").append(phone).append(",quota:")
					.append(quota).append("]").toString());
			return;
		}
		//TODO 去掉业务能里限制校验;Already processed;is ok
		String capacity = dataCache.getMmsmtLimitByServiceid(serviceid);
		if (capacity != null && "2".equals(capacity))
		{
			logger.info((new StringBuilder("mmsmt service beyond number limited [gmsgid:")).append(gmsgid)
					.append(",phone:").append(phone).append(",productid:").append(productid).append(",serviceid:")
					.append(serviceid).append("]").toString());
			String str = StringUtil.getReportString(
					(new StringBuilder(String.valueOf((new Random()).nextInt(0x186a0)))).toString(), "10655565", phone,
					StringUtil.getTime0800(), messageid, 11007, "MT MMS COUNT BEYOND LIMIT");
			mr.setContent(str);
			messageRouter.doRouter(mr);
			return;
		}
		switch (mmsmtType) {
	
		case 0: // '\0'
			if (!linkid.toUpperCase().startsWith("ACE")) {
				String num = isPresent <= 0 ? phone : charge;
				CheckRequest cr5 = new CheckRequest(num, null, spid, serviceid, null, "5", linkid);
				cr5.setGlobalMessageid(mt.getGlobalMessageid());
				messageRouter.doRouter(cr5);
				logger.info((new StringBuilder("mmsmt on demand vac auth(5) request[gmsgid:")).append(gmsgid)
						.append(",phone:").append(num).append(",linkid:").append(linkid).append("]").toString());
				break;
			}
			String woKey = (new StringBuilder("WO")).append(linkid).append(phone).toString();
			if (commonMap.get(woKey) != null) {
				logger.info((new StringBuilder("mmsmt on demand wo auth(2) request[gmsgid:")).append(gmsgid)
						.append(",phone:").append(phone).append(",linkid:").append(linkid).append("]").toString());
				WOCheckResponse worsp = (WOCheckResponse) commonMap.get(woKey);
				WOCheckRequest woreq = worsp.getWoRequest();
				woreq.setGlobalMessageid(mt.getGlobalMessageid());
				woreq.setReqType(2);
				messageRouter.doRouter(woreq);
				commonMap.remove(woKey);
			} else {
				logger.info((new StringBuilder("mmsmt on demand match wo auth(1) request failure[gmsgid:"))
						.append(gmsgid).append(",wokey:").append(woKey).append("]").toString());
				return;
			}
			break;

		case 1: // '\001'
			//TODO iphone 特殊处理;Already processed;is ok
			boolean isIphone = false;
//			isIphone=daoUtil.getMm7dao().isFromIphoneOrder(phone, productid);
			logger.info("isIphone:"+isIphone);
			
			if (isIphone) {
				logger.info((new StringBuilder("mmsmt on order wo auth(needn't) request[gmsgid:")).append(gmsgid)
						.append(",phone:").append(phone).append(",productid:").append(productid).append("]")
						.toString());
				messageRouter.doRouter(mt);
				mmsReportDataMap.put(gmsgid, getMMHTttpSpMessageReport(mt));
				return;
			}
			String num = isPresent <= 0 ? phone : charge;
			CheckRequest cr7 = new CheckRequest(num, productid, spid, null, null, "7", null);
			cr7.setGlobalMessageid(mt.getGlobalMessageid());
			messageRouter.doRouter(cr7);
			logger.info((new StringBuilder("mmsmt on order vac auth(7) request[gmsgid:")).append(gmsgid)
					.append(",phone:").append(num).append(",productid:").append(productid).append("]").toString());
			break;
		default:
			break;

		}
		dataMap.put(gmsgid, mt);
		return;

	}

	public void doCR(WOCheckResponse rsp) {
		String gmsgid = rsp.getWoRequest().getGlobalMessageid();

		if (this.dataMap.get(gmsgid) == null) {
			logger.info("mmsmt wo auth(" + rsp.getReqType() + ") response match key failure[gmsgid:" + gmsgid + "]");
			return;
		}

		AbstractMessage msg = (AbstractMessage) this.dataMap.get(gmsgid);
		int rspCode = Integer.parseInt(rsp.getReturnStr());

		if ((msg instanceof MT_MMHttpSPMessage)) {
			MT_MMHttpSPMessage mmsmt = (MT_MMHttpSPMessage) msg;

			String messageid = mmsmt.getMessageid();

			if (20000 == rspCode) {
				logger.info("mmsmt wo auth(" + rsp.getReqType() + ") response success[gmsgid:" + gmsgid + ",phone:"
						+ mmsmt.getPhone() + ",result:" + (rspCode == 20000 ? "success" : "failue") + "]");

				this.messageRouter.doRouter(mmsmt);
			} else {
				logger.info("mmsmt wo auth(" + rsp.getReqType() + ") response failure[gmsgid:" + gmsgid + ",phone:"
						+ mmsmt.getPhone() + ",code:" + rspCode + ",messageid:" + messageid + "]");

				// this.daoUtil.getSmsDAO().updateMmsGrsCode("4", messageid,
				// rspCode);

				recycleMessage(gmsgid);
			}
		} else {
			logger.info("mmsmt wo auth(" + rsp.getReqType() + ") response match class error[gmsgid:" + gmsgid
					+ ",classtype:" + msg.getClass().getName() + "]");
		}
	}

	public void doCR(CheckResponse rsp) {

		String gmsgid="";
		AbstractMessage msg=null;
		String rspCode="";
		MO_MMDeliverSPMessage mmsmo=null;
		String phone="";
		String linkid="";
		String sendto="";
		MT_SMMessage smsmt=null;
		InputStream io=null;
		ByteArrayOutputStream os=null;
		gmsgid = rsp.getCRequest().getGlobalMessageid();
		if ("6".equals(rsp.getCRequest().getServiceType())) {
			String result = rsp.getResult_Code() == null || !"0".equals(rsp.getResult_Code()) ? "failure" : "success";
			logger.info((new StringBuilder("mms vac auth(6) response ")).append(result).append("[gmsgid:")
					.append(gmsgid).append(",phone:").append(rsp.getCRequest().getUser_number()).append(",rspcode:")
					.append(rsp.getResult_Code()).append("]").toString());
			recycleMessage(gmsgid);
			return;
		}
		if (gmsgid == null || dataMap.get(gmsgid) == null) {
			logger.info((new StringBuilder("mms vac auth(")).append(rsp.getCRequest().getServiceType())
					.append(") response match failure[gmsgid:").append(gmsgid).append("]").toString());
			return;
		}
		msg = (AbstractMessage) dataMap.get(gmsgid);
		if (msg instanceof MO_MMDeliverSPMessage) {
			rspCode = rsp.getResult_Code();
			mmsmo = (MO_MMDeliverSPMessage) msg;
			phone = StringUtil.getPhone11(rsp.getCRequest().getUser_number());
			linkid = rsp.getLinkID();
			sendto = mmsmo.getTo().get(0).toString();
			sendto = sendto.replace("[", "");
			sendto = sendto.replace("]", "");

			if (!"4".equals(rsp.getCRequest().getServiceType())) {
				smsmt = new MT_SMMessage();
				smsmt.setGlobalMessageid(gmsgid);
				smsmt.setRcvAddresses(new String[] { (new StringBuilder("86")).append(phone).toString() });
				smsmt.setServiceCode("10655565");
				saveMmsmoHistory(mmsmo, rspCode);
				if (rspCode == null || rspCode.trim().equals("") || rspCode.toLowerCase().equals("null")) {
					logger.info((new StringBuilder("mmsmo vac auth(4) response failure[gmsgid:")).append(gmsgid)
							.append(",phone:").append(phone).append(",desc:code is null]").toString());
					smsmt.setSmsText((new StringBuilder(String.valueOf(dataCache.queryErrorMsgTemplate())))
							.append("系统响应超时，请稍后再试").toString());
					sendSmsMessage(smsmt);
					recycleMessage(gmsgid);
					return;
				}
				if (!rspCode.equals("0")) {
					logger.info((new StringBuilder("mmsmo vac auth(4) response failure[gmsgid:")).append(gmsgid)
							.append(",phone:").append(phone).append(",rspcode:").append(rspCode).append("]")
							.toString());
					smsmt.setSmsText((new StringBuilder(String.valueOf(dataCache.queryErrorMsgTemplate())))
							.append("系统响应超时，请稍后再试").toString());
					sendSmsMessage(smsmt);
					recycleMessage(gmsgid);
					return;
				}
				if (linkid == null || linkid.length() <= 0 || "NULL".equalsIgnoreCase(linkid.toUpperCase())) {
					logger.info((new StringBuilder("mmsmo vac auth(4) response failure[gmsgid:")).append(gmsgid)
							.append(",phone:").append(phone).append(",desc:linkid is null]").toString());
					smsmt.setSmsText((new StringBuilder(String.valueOf(dataCache.queryErrorMsgTemplate())))
							.append("系统响应超时，请稍后再试").toString());
					sendSmsMessage(smsmt);
					recycleMessage(gmsgid);
					return;
				}
				logger.info((new StringBuilder("mmsmo vac auth(4) response success[gmsgid:")).append(gmsgid)
						.append(",phone:").append(phone).append(",linkid:").append(linkid).append("]").toString());
				mmsmo.setLinkId(linkid);
				smsmt.setLinkId(linkid);
				io = null;
				os = null;
				int len = 0;
				try {
					io = XmlElementReplace.replaceLinkId(new ByteArrayInputStream(mmsmo.getContentByte()), linkid);
					os = new ByteArrayOutputStream();
					byte data[] = new byte[1024];
					while ((len = io.read(data)) > 0) {
						os.write(data, 0, len);
						mmsmo.setContentByte(os.toByteArray());
						logger.info((new StringBuilder("mmsmo linkid replace success[gmsgid:")).append(gmsgid)
								.append(",phone:").append(phone).append(",linkid:").append(linkid).append("]")
								.toString());
					}
				} catch (Exception ex) {
					logger.error("mmsmo linkid replace exception", ex);
					logger.info((new StringBuilder("mmsmo linkid replace failure[gmsgid:")).append(gmsgid)
							.append(",phone:").append(phone).append(",linkid:").append(linkid).append("]").toString());
				} finally {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						io.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				logger.info((new StringBuilder("mmsmo message original transfer[gmsgid:")).append(gmsgid)
						.append(",phone:").append(phone).append(",linkid:").append(linkid).append("]").toString());
				messageRouter.doRouter(mmsmo);
				if (mmsmo.getNeedConfirm() != null && mmsmo.getNeedConfirm().equals("1")) {
					logger.info((new StringBuilder("mmsmo vac auth(5) request[gmsgid:")).append(gmsgid)
							.append(",phone:").append(phone).append(",linkid:").append(linkid).append("]").toString());
					CheckRequest cr5 = new CheckRequest(phone, mmsmo.getProductId(), mmsmo.getSpid(),
							mmsmo.getServicesId(), null, "5", linkid);
					cr5.setGlobalMessageid(gmsgid);
					cr5.setResponse(false);
					messageRouter.doRouter(cr5);
				} else {
					recycleMessage(gmsgid);
				}
				SmsSenderDto ssd = dataCache.getDianBoByNumber(sendto);
				String smsText = "";
				if (ssd == null || ssd.getVasid() == null)
					smsText = (new StringBuilder(String.valueOf(dataCache.queryErrorMsgTemplate()))).append("你好，没有这个业务")
							.toString();
				else if ("0".equals(rsp.getNeedConfirm())) {
					smsText = dataCache.queryUpOndemandMessage();
					smsText = smsText.replace("{0}", ssd.getServicename());
					smsText = smsText.replace("{1}", ssd.getFee());
				} else if ("1".equals(rsp.getNeedConfirm())) {
					smsText = dataCache.queryOndemandMessage();
					smsText = smsText.replace("{0}", ssd.getServicename());
					smsText = smsText.replace("{1}", ssd.getFee());
				}
				smsmt.setSmsText(smsText);
				sendSmsMessage(smsmt);
			} else if ("5".equals(rsp.getCRequest().getServiceType())) {
				logger.info((new StringBuilder("mmsmo vac auth(5) repsonse[gmsgid:")).append(gmsgid).append(",phone:")
						.append(phone).append(",code:").append(rspCode).append(",linkid:").append(linkid).append("]")
						.toString());
				recycleMessage(gmsgid);
			} else {
				logger.info((new StringBuilder("mmsmo vac auth(")).append(rsp.getCRequest().getServiceType())
						.append(") repsonse type error[gmsgid:").append(gmsgid).append("]").toString());
			}
		} else if (msg instanceof MT_MMHttpSPMessage) {
			MT_MMHttpSPMessage mmsmt = (MT_MMHttpSPMessage) msg;
			String phone1 = rsp.getCRequest().getUser_number();
			String messageid = mmsmt.getMessageid();
			int rspCode1 = Integer.parseInt(rsp.getResult_Code());
			MO_ReportMessage mmsmr = new MO_ReportMessage();
			mmsmr.setMessageId(mmsmt.getMessageid());
			mmsmr.setReportUrl(mmsmt.getGlobalReportUrl());
			int mtType = mmsmt.getSourceType();
			switch (mtType) {
			case 0: // '\0'
				if (rspCode1 == 0) {
					logger.info((new StringBuilder("mmsmt on demand vac auth(5) response success[gmsgid:"))
							.append(gmsgid).append(",phone:").append(phone1).append(",result:").append(rspCode1)
							.append(",needConfirm:").append(rsp.getNeedConfirm()).append(",sequence:")
							.append(rsp.getSrc_SequenceNumber()).append("]").toString());
					if ("1".equals(rsp.getNeedConfirm())){
						mmsmt.setSequence(rsp.getSrc_SequenceNumber());
					}
					messageRouter.doRouter(mmsmt);
					recycleMessage(gmsgid);
					mmsReportDataMap.put(gmsgid, getMMHTttpSpMessageReport(mmsmt));
				} else {
					logger.info((new StringBuilder("mmsmt on demand vac auth(5) response failure[gmsgid:"))
							.append(gmsgid).append(",phone:").append(phone1).append(",result:").append(rspCode1)
							.append(",serverResult_code:").append(rsp.getServerResult_code()).append("]").toString());
					String str = StringUtil.getReportString(
							(new StringBuilder(String.valueOf((new Random()).nextInt(0x186a0)))).toString(), "10655565",
							phone1, StringUtil.getTime0800(), mmsmt.getMessageid(), rspCode1, "Auth Failed");
					mmsmr.setContent(str);
					messageRouter.doRouter(mmsmr);
					//TODO 数据库更新修改;Already processed;edit
//					daoUtil.getSmsDAO().updateMmsGrsCode("4", messageid,(new StringBuilder(String.valueOf(rspCode1))).toString());
					HttpRequestHelper.updateMmsGrsCode("4", messageid,(new StringBuilder(String.valueOf(rspCode1))).toString());
					recycleMessage(gmsgid);
				}
				break;

			case 1: // '\001'
				if (rspCode1 == 0) {
					logger.info((new StringBuilder("mmsmt on order vac auth(7) response success[gmsgid:"))
							.append(gmsgid).append(",phone:").append(phone1).append(",result:").append(rspCode1)
							.append("]").toString());
					messageRouter.doRouter(mmsmt);
					recycleMessage(gmsgid);
					mmsReportDataMap.put(gmsgid, getMMHTttpSpMessageReport(mmsmt));
				} else if (1 == rspCode1) {
					logger.info((new StringBuilder("mmsmt on order vac auth(7) response failure[gmsgid:"))
							.append(gmsgid).append(",phone:").append(phone1).append(",result:").append(rspCode1)
							.append("serverResult_code:").append(rsp.getServerResult_code()).append("]").toString());
					String str = StringUtil.getReportString(
							(new StringBuilder(String.valueOf((new Random()).nextInt(0x186a0)))).toString(), "10655565",
							phone1, StringUtil.getTime0800(), messageid, 11201, "OrderRelation Doesn't Exist");
					mmsmr.setContent(str);
					messageRouter.doRouter(mmsmr);
					//TODO 数据库更新修改;Already processed;edit
//					daoUtil.getSmsDAO().updateMmsGrsCode("4", messageid,(new StringBuilder(String.valueOf(rspCode1))).toString());
					HttpRequestHelper.updateMmsGrsCode("4", messageid,(new StringBuilder(String.valueOf(rspCode1))).toString());
					recycleMessage(gmsgid);
				} else {
					logger.info((new StringBuilder("mmsmt on order vac auth(7) response failure[gmsgid:"))
							.append(gmsgid).append(",phone:").append(phone1).append(",result:").append(rspCode1)
							.append("serverResult_code:").append(rsp.getServerResult_code()).append("]").toString());
					String str = StringUtil.getReportString(
							(new StringBuilder(String.valueOf((new Random()).nextInt(0x186a0)))).toString(), "10655565",
							phone1, StringUtil.getTime0800(), messageid, 1005, "Auth Failed");
					mmsmr.setContent(str);
					messageRouter.doRouter(mmsmr);
					//TODO 数据库更新修改;Already processed;edit
//					daoUtil.getSmsDAO().updateMmsGrsCode("4", messageid,(new StringBuilder(String.valueOf(rspCode1))).toString());
					HttpRequestHelper.updateMmsGrsCode("4", messageid,(new StringBuilder(String.valueOf(rspCode1))).toString());
					recycleMessage(gmsgid);
				}
				break;
			}
		} else {
			logger.info((new StringBuilder("mms doCR(CheckResponse) class error")).append(msg.getClass().getName())
					.toString());
			logger.error((new StringBuilder("mms doCR(CheckResponse) class error")).append(msg.getClass().getName())
					.toString());
		}
	}

	void recycleMessage(String msgid) {
		try {
			this.dataMap.remove(msgid);

			logger.info("recycleMessage msgid : " + msgid);
		} catch (Exception ex) {
			logger.error("recycleMessage msgid : " + msgid + "\t" + ex.getMessage());
		}
	}

	public void removeReportMessage(String msgid) {
		try {
			this.mmsReportDataMap.remove(msgid);
			logger.info("removeReportMessage msgid : " + msgid);
		} catch (Exception ex) {
			logger.error("removeReportMessage msgid : " + msgid + "\t" + ex.getMessage());
		}
	}

	void sendSmsMessage(MT_SMMessage sms) {
		try {
			logger.info("mmsmo short message service[gmsgid:" + sms.getGlobalMessageid() + ",phone:"
					+ sms.getRcvAddresses()[0] + ",text:" + sms.getSmsText() + "]");

			this.messageRouter.doRouter(sms);
		} catch (Exception localException) {
		}
	}

	void saveMmsmoHistory(MO_MMDeliverSPMessage mmsmo, String sCorrect) {
		try {
			long btime = System.currentTimeMillis();

			MO_MMDeliverMessage msg = new MO_MMDeliverMessage();
			msg.setSender(mmsmo.getSender());
			String sendto = mmsmo.getTo().get(0).toString();
			sendto = sendto.replace("[", "");
			sendto = sendto.replace("]", "");
			int isCorrect = Integer.parseInt(sCorrect);
			//TODO 保存MO MMS 消息
//			this.daoUtil.getLogDao().saveMoMMSMsg(msg, sendto, Integer.valueOf(isCorrect));

			logger.info("mmsmo save mo history success[gmsgid:" + mmsmo.getGlobalMessageid() + ",phone:"
					+ mmsmo.getSender() + ",exectime:" + (System.currentTimeMillis() - btime) + "]");
		} catch (Exception ex) {
			logger.error("mmsmo save mo history exception", ex);
		}
	}

	public MT_MMHttpSPMessage_Report getMMHTttpSpMessageReport(MT_MMHttpSPMessage mt) {
		MT_MMHttpSPMessage_Report mtmr = new MT_MMHttpSPMessage_Report();
		mtmr.setGlobalMessageid(mt.getGlobalMessageid());
		mtmr.setGlobalMessageTime(System.currentTimeMillis());
		mtmr.setServiceid(mt.getServiceid());
		mtmr.setMessageid(mt.getMessageid());
		mtmr.setGlobalReportUrl(mt.getGlobalReportUrl());
		mtmr.setGlobalCreateTime(mt.getGlobalCreateTime());
		mtmr.setSourceGlobalMessageid(mt.getGlobalMessageid());
		mtmr.setSequence(mt.getSequence());
		mtmr.setReqId(mt.getReqId());

		mtmr.setSpid(new String(mt.getSpid().toCharArray()));
		mtmr.setSp_productid(new String(mt.getSp_productid().toCharArray()));

		return mtmr;
	}

	public MT_MMHttpSPMessage getMMHTttpSpMessage(MT_MMHttpSPMessage msg) {
		MT_MMHttpSPMessage mt = new MT_MMHttpSPMessage();

		mt.setGlobalMessageid(msg.getGlobalMessageid());
		mt.setSequence(msg.getSequence());

		mt.setSpid(new String(msg.getSpid().toCharArray()));
		mt.setSp_productid(new String(msg.getSp_productid().toCharArray()));

		mt.setGlobalReportUrl(msg.getGlobalReportUrl());
		mt.setServiceid(msg.getServiceid());
		mt.setMessageid(msg.getMessageid());
		mt.setGlobalMessageTime(System.currentTimeMillis());

		return mt;
	}

	public void setMessageRouter(IMessageRouter messageRouter) {
		this.messageRouter = messageRouter;
	}

//	public void setDaoUtil(DaoUtil daoUtil) {
//		this.daoUtil = daoUtil;
//	}

	public void setDataCache(DataCache dataCache) {
		this.dataCache = dataCache;
	}

	public void setDataMap(ConcurrentHashMap<String, AbstractMessage> dataMap) {
		this.dataMap = dataMap;
	}

	public void setCommonMap(ConcurrentHashMap<String, AbstractMessage> commonMap) {
		this.commonMap = commonMap;
	}

	public void setMmsDBListener(MmsDBListener mmsDBListener) {
		this.mmsDBListener = mmsDBListener;
	}

	public void setMmsUpdateListener(MmsUpdateListener mmsUpdateListener) {
		this.mmsUpdateListener = mmsUpdateListener;
	}

	public void setMmsReportDataMap(ConcurrentHashMap<String, MT_MMHttpSPMessage_Report> mmsReportDataMap) {
		this.mmsReportDataMap = mmsReportDataMap;
	}
}

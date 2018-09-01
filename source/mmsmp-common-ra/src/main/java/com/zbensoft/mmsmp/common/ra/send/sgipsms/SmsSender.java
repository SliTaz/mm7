package com.zbensoft.mmsmp.common.ra.send.sgipsms;

import com.zbensoft.mmsmp.common.ra.MM7.sp.ReceiveServlet;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.AdminConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.SGIPConnect;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.SgipSubmit;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrderHis;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrderId;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueueClientInf;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueueClientTcpImpl;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sgip.SGIPSMSProxy;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.send.util.ErrorProperties;
import com.zbensoft.mmsmp.common.ra.send.util.SplitMsg;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl.TcpClientImpl;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.SysParameter;
import com.zbensoft.mmsmp.common.ra.vas.sjb.messagehandler.SendNotificationMessageHandler;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Report;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_SubmitResp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class SmsSender extends SGIPSMSProxy {
	private static final String SERVICE_NUMBER = "10655565";
	private static final String CANCEL_ALL_COMMAND = "00000";
	private static final String CANCEL_COMMAND = "0000";
	private static final String CONFIRM_COMMAND = "Y";
	private static final Logger logger = Logger.getLogger(SmsSender.class);
	private SgipSubmit sgipSubmit;
	private SGIPConnect sgipConnect;
	private boolean isConnect = false;
	HashMap<Long, String> map = new HashMap();
	private SmsSenderDao smsSenderDao;
	private String MO_ERROR_MESSAGE;
	private MessageQueue MO_queue;
	private SendNotificationMessageHandler sendNotificationMessageHandler;

	public SmsSender() {
		super(new Resource("config").getParameter("SGIPConnect/*").set("block_send", true));
		init();
	}

	public boolean init() {
		try {
			this.sgipSubmit = ConfigUtil.getInstance().getSgipSubmit();
			this.sgipConnect = ConfigUtil.getInstance().getSGIPConnect();
			try {
				this.isConnect = connect(this.sgipConnect.getLogin_name(), this.sgipConnect.getLogin_pass());
			} catch (Exception e) {
				logger.error("", e);
			}
			startService(this.sgipConnect.getLocalHost(), this.sgipConnect.getLocalPort());
			if (this.isConnect) {
				logger.info("=====>connect smsgw successful");
				return true;
			}
			logger.error("=====>connect smsgw failed");
		} catch (Exception e) {
			logger.error("", e);
		}
		return false;
	}

	public String send(MT_SMMessage msg) {
		String spNumber = this.sgipSubmit.getSpNumber();
		int agentFlag = this.sgipSubmit.getAgentFlag();
		String expireTime = this.sgipSubmit.getExpireTime();
		String scheduleTime = this.sgipSubmit.getScheduleTime();
		int reportFlag = this.sgipSubmit.getReportFlag();
		int tp_pid = this.sgipSubmit.getTp_pid();
		int tp_udhi = this.sgipSubmit.getTp_udhi();
		int messageCoding = this.sgipSubmit.getMessageCoding();
		int messageType = this.sgipSubmit.getMessageType();
		String corpId = this.sgipSubmit.getCorpId();
		String serviceType = this.sgipSubmit.getServiceType();
		String givenValue = this.sgipSubmit.getGivenValue();
		long srcNode = this.sgipSubmit.getSrcNode();
		int morelatetoMTFla = 0;

		String msgContext = msg.getSmsText();
		String[] splitMsgs = SplitMsg.split(msgContext, this.sgipSubmit.getMessageLength());

		boolean flag = false;

		String chargeNumber = msg.getRcvAddresses()[0];

		String[] desTermId = msg.getRcvAddresses();
		boolean isMonthOrder = true;
		int feeType = getFeeType(isMonthOrder);
		int priority = 1;
		int sgipMsgPriority = 1;
		String feeValue = "000000";
		long msgID = 0L;
//		SGIP_Submit submit;
		for (int k = 0; k < splitMsgs.length; k++) {
			try {
				if (k > 0) {
					feeType = 1;
					morelatetoMTFla = 1;
				}
				SGIP_Submit submit = new SGIP_Submit(srcNode, spNumber, "000000000000000000000", desTermId, corpId, serviceType, feeType, feeValue, givenValue, agentFlag, morelatetoMTFla, sgipMsgPriority, expireTime,
						scheduleTime, reportFlag, tp_pid, tp_udhi, messageCoding, messageType, splitMsgs[k], "");
				logger.info("=====>send content to smsc:" + splitMsgs[k]);

				SGIP_SubmitResp res = (SGIP_SubmitResp) super.send(submit);
				msgID = getSubmitSeq(submit);

				this.map.put(Long.valueOf(msgID), msg.getMtTranId());
			} catch (Exception e) {
				flag = true;
				logger.error("=====>send sms to smsc error:" + msgContext, e);
				return msgID+"";
			}
		}
		if (flag) {
			return null;
		}
		return msgID+"";
	}

	public SGIP_Command onDeliver(SGIP_Deliver msg) {
		logger.info("=====>receive Message: " + msg);
		try {
			MO_SMMessage mo_sms = new MO_SMMessage();
			mo_sms.setSmsText(msg.getMessageContent());
			mo_sms.setLinkId(msg.getLinkId());
			mo_sms.setSendAddress(msg.getUserNumber());
			mo_sms.setVasId(msg.getSPNumber());

			this.MO_queue.addMessage(mo_sms, Integer.valueOf(MessageQueue.PRIORITY_COMMON));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("=====>onDeliver error....");
		}

		return super.onDeliver(msg);
	}

	private void save_linkid(String link_id) {
		ReceiveServlet.put_linkid(link_id);
	}

	private void notify_mo(SGIP_Deliver msg) {
		MO_SMMessage mo_sms = new MO_SMMessage();
		mo_sms.setSmsText(msg.getMessageContent());
		mo_sms.setLinkId(msg.getLinkId());
		mo_sms.setSendAddress(msg.getUserNumber());
		mo_sms.setSourceType(1);

		String host = ConfigUtil.getInstance().getAdminConfig().getNotifyMessageIP();
		int port = ConfigUtil.getInstance().getCorebizConfig().getMoQueueListenPort();
		MessageQueueClientInf messageQueueClientInf = new MessageQueueClientTcpImpl(host, port);
		messageQueueClientInf.send(mo_sms);
	}

	private void send_touser(String msg, String send_addr) {
		MT_SMMessage message = new MT_SMMessage();
		message.setMtTranId(System.currentTimeMillis()+"");
		message.setSendAddress("10655565");
		message.setSmsText(msg);
		message.setRcvAddresses(new String[] { send_addr });
		send(message);
	}

	private void dispatchMessage(SGIP_Deliver deliver, String url, String vasid) {
		logger.info("=====>the url is : " + url);

		if ((url != null) && (!"".equals(url))) {
			String host = url.split(":")[0];
			String port = url.split(":")[1];

			Deliver de = new Deliver();
			de.Sequence_Number_1 = Integer.valueOf((int) (deliver.getSeqno_1() >> 0));
			de.Sequence_Number_2 = Integer.valueOf(deliver.getSeqno_2());
			de.Sequence_Number_3 = Integer.valueOf(deliver.getSeqno_3());
			de.MessageCoding = ((byte) deliver.getMessageCoding());
			de.MessageContent = deliver.getMessageContent();
			de.Reserve = deliver.getLinkId();
			de.SPNumber = vasid;
			de.TP_pid = ((byte) deliver.getTP_pid());
			de.TP_udhi = ((byte) deliver.getTP_udhi());
			de.UserNumber = deliver.getUserNumber();
			de.ContentLength = deliver.getMessageLength();
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

	private Op_Type getOperateType(SGIP_Deliver msg) {
		String phoneNumber = msg.getUserNumber();
		if (phoneNumber.startsWith("86")) {
			phoneNumber = phoneNumber.substring(2);
		}
		String spNumber = msg.getSPNumber();

		if (spNumber.length() > 13) {
			logger.error("VasId too long( less than 13 digits ): " + spNumber);
			return new Op_Type(enum_optype.OTHER);
		}

		if ((msg.getMessageContent() == null) || (msg.getMessageContent().equals(""))) {
			logger.error("Mo SMS Content can't be empty");
			return new Op_Type(enum_optype.OTHER);
		}

		String MessageContent = msg.getMessageContent().toLowerCase();
		if ((MessageContent != null) && (MessageContent.length() > 2) && (MessageContent.startsWith("cx"))) {
			logger.info("Mo sms by spNumber, starts with CX......");
			MessageContent = MessageContent.substring(2, MessageContent.length());
		}

		this.smsSenderDao.saveMoMsg(MessageContent, phoneNumber, spNumber);

		if ("10655565".length() == spNumber.length()) {
			logger.info("=====>msg.getMessageContent():" + msg.getMessageContent());
			String product_id = "";

			if ("0000".equals(MessageContent)) {
				return new Op_Type(enum_optype.CANCEL);
			}

			if ("00000".equals(MessageContent)) {
				return new Op_Type(enum_optype.CANCEL_ALL);
			}

			SmsSenderDto dt1 = new SmsSenderDto();
			try {
				dt1 = this.smsSenderDao.getOrderAndProduct_id(MessageContent);
			} catch (Exception e) {
				logger.info("=====>not find product in order table by content:" + MessageContent);
			}
			product_id = dt1.getSp_productid();
			if ((product_id != null) && (product_id.trim().length() > 0)) {
				String service_id = dt1.getServiceId();
				return new Op_Type(enum_optype.ORDER).set_product_id(product_id).set_service_id(service_id).set_smsSenderDto(dt1).set_cmdtext(MessageContent);
			}

			if ("Y".equalsIgnoreCase(MessageContent)) {
				String smsText = this.smsSenderDao.getLatestMoOrderMsg(phoneNumber, 0L);
				logger.info("=====>confirm command :  smsText :  " + smsText);
				SmsSenderDto dto2 = new SmsSenderDto();
				try {
					dto2 = this.smsSenderDao.getOrderAndProduct_id(smsText);
				} catch (Exception e) {
					logger.info("=====>not find product in getLatestMoOrderMsg:" + MessageContent);
				}
				String productId = dto2.getSp_productid();
				logger.info("=====>confirm command :   productId :  " + productId);
				if ((productId != null) && (productId.trim().length() > 0)) {
					String serviceId = dto2.getServiceId();
					logger.info("=====>confirm command :  serviceId   " + serviceId);
					return new Op_Type(enum_optype.CONFIRM).set_product_id(productId).set_service_id(serviceId).set_smsSenderDto(dto2).set_cmdtext(MessageContent);
				}
				return new Op_Type(enum_optype.OTHER);
			}

			SmsSenderDto dto3 = new SmsSenderDto();
			try {
				dto3 = this.smsSenderDao.getDianBoAndProduct_id(MessageContent);
			} catch (Exception e) {
				logger.info("=====>not find product in dianbo table by content:" + MessageContent);
			}
			String pid = dto3.getSp_productid();
			logger.info("=====>msg.getMessageContent():" + MessageContent + "_____query db get pid:__" + pid + "___");
			if ((pid != null) && (!"".equals(pid))) {
				String service_id = dto3.getServiceId();
				return new Op_Type(enum_optype.DIANBO).set_product_id(pid).set_service_id(service_id).set_smsSenderDto(dto3);
			}

			SmsSenderDto dto = new SmsSenderDto();
			try {
				dto = this.smsSenderDao.getCancelOrderAndProduct_id(MessageContent);
			} catch (Exception e) {
				logger.info("=====>not find product in getCancelOrderAndProduct_id by content:" + MessageContent);
			}
			product_id = dto.getSp_productid();
			if ((product_id != null) && (product_id.trim().length() > 0)) {
				String service_id = this.smsSenderDao.getService_idByProduct_id(product_id);
				return new Op_Type(enum_optype.CANCEL).set_product_id(product_id).set_service_id(service_id).set_smsSenderDto(dto);
			}

			return new Op_Type(enum_optype.OTHER);
		}

		if (spNumber.length() > "10655565".length()) {
			String pida = "";
			SmsSenderDto dto = new SmsSenderDto();
			try {
				dto = this.smsSenderDao.getDianBoByNumber(spNumber, MessageContent);
			} catch (Exception e) {
				logger.info("=====>not find sp_productid in dianbo table");
			}
			pida = dto.getSp_productid();
			if ((pida != null) && (pida != "")) {
				String service_id = dto.getServiceId();
				return new Op_Type(enum_optype.DIANBO).set_product_id(pida).set_service_id(service_id).set_smsSenderDto(dto);
			}

			try {
				dto = this.smsSenderDao.getDingzhiByNumber(spNumber, MessageContent);
			} catch (Exception e) {
				logger.info("=====>not find sp_productid in dingzhi table");
			}
			pida = dto.getSp_productid();
			if ((pida != null) && (pida.trim().length() > 0)) {
				String service_id = dto.getServiceId();
				return new Op_Type(enum_optype.ORDER).set_product_id(pida).set_service_id(service_id).set_smsSenderDto(dto).set_cmdtext(MessageContent);
			}
			try {
				dto = this.smsSenderDao.getTuidingByNumber(spNumber, MessageContent);
			} catch (Exception e) {
				logger.info("=====>not find sp_productid in tuiding table");
			}
			pida = dto.getSp_productid();
			if ((pida != null) && (pida.trim().length() > 0)) {
				String service_id = dto.getServiceId();
				return new Op_Type(enum_optype.CANCEL).set_product_id(pida).set_service_id(service_id).set_smsSenderDto(dto);
			}
			return new Op_Type(enum_optype.ORDER);
		}

		return new Op_Type(enum_optype.ORDER);
	}

	public SGIP_Command onReport(SGIP_Report msg) {
		logger.info("=====>recevice smsc report message:" + msg);

		long seqID = getReportSeq(msg);
		int state = msg.getState();

		return super.onReport(msg);
	}

	private String changeStatus(int state) {
		String status = null;
		switch (state) {
		case 0:
			status = "5";
			break;
		case 1:
			status = "8";
			break;
		case 2:
			status = "7";
		}

		return status;
	}

	public String getReportSeq(long seqID) {
		String value = null;
		synchronized (this.map) {
			value = (String) this.map.remove(Long.valueOf(seqID));
		}
		return value;
	}

	private long getReportSeq(SGIP_Command msg) {
		SGIP_Report report = (SGIP_Report) msg;

		long seqID = report.getSeq_1() + report.getSeq_2() + report.getSeq_3();
		return seqID;
	}

	private int getFeeType(boolean isMonthOrder) {
		int feeType = 0;
		if (isMonthOrder)
			feeType = 3;
		else {
			feeType = 2;
		}
		return feeType;
	}

	private long getSubmitSeq(SGIP_Command msg) {
		SGIP_Submit submit = (SGIP_Submit) msg;

		long seqID = submit.getSeqno_1() + submit.getSeqno_2() + submit.getSeqno_3();
		return seqID;
	}

	public String GetClientMessage(String errorCode) {
		if (errorCode.equals("110"))
			return this.smsSenderDao.queryErrorMsgTemplate() + "您发送的地址无效!";
		if (errorCode.equals("1003"))
			return this.smsSenderDao.queryErrorMsgTemplate() + "您已经欠费!";
		if (errorCode.equals("1200"))
			return this.smsSenderDao.queryErrorMsgTemplate() + "您已经定制该业务";
		if ((errorCode.equals("1201")) || (errorCode.equals("2101")))
			return this.smsSenderDao.queryErrorMsgTemplate() + "业务不存在";
		if ((errorCode.equals("3101")) || (errorCode.equals("2202"))) {
			return this.smsSenderDao.queryErrorMsgTemplate() + "您的余额不足";
		}
		String errorStr = ErrorProperties.getString("1" + errorCode, "");
		return "系统繁忙！编号：1" + errorCode + "，详细请咨询客服：010-64853134";
	}

	public void addUserOrder(String cellphonenumber, int vasserviceId) {
		UserInfo userInfo = new UserInfo();
		UserOrderHis orderHis = new UserOrderHis();
		int hisID = this.smsSenderDao.getUserOrderHisId().intValue();

		if (this.smsSenderDao.isNumberExist(cellphonenumber, Integer.valueOf(vasserviceId))) {
			String _msg = this.smsSenderDao.queryErrorMsgTemplate() + "您已经定购，无需重复定购！";
			String send_addr = cellphonenumber;
			send_touser(_msg, send_addr);
		} else {
			userInfo.setCellphonenumber(cellphonenumber);

			SysParameter sysParam = this.smsSenderDao.querySysParamById("PLATFORM_AREA");
			String provinceCode = sysParam.getValue().split("@")[0];

			Map segs = null;
			try {
				segs = this.smsSenderDao.getAllSegmentsForCode();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String[] temp = (String[]) null;

			if (segs.size() != 0) {
				String seg = cellphonenumber.substring(0, 7);
				Object obj = segs.get(seg);
				String province = "";
				if (obj != null) {
					province = (String) obj;
				} else {
					userInfo.setProvincecode(provinceCode);
					userInfo.setCitycode(provinceCode + "99");
				}

				if ((province != null) && (!"".equals(province))) {
					temp = province.split("@");
					userInfo.setProvincecode(temp[0]);
					userInfo.setCitycode(temp[1]);
				}
			} else {
				userInfo.setProvincecode(provinceCode);
				userInfo.setCitycode(provinceCode + "99");
			}

			userInfo.setStatus("1");
			userInfo.setDeleteFlag("0");
			userInfo.setCreatedate(new Date());

			if (!this.smsSenderDao.isCellphonenumberExisted(cellphonenumber)) {
				this.smsSenderDao.saveUserInfo(userInfo);
			}

			orderHis.setUniqueid(Integer.valueOf(hisID));
			orderHis.setServiceuniqueid(Integer.valueOf(vasserviceId));
			orderHis.setCellphonenumber(cellphonenumber);
			orderHis.setChargeparty(cellphonenumber);
			orderHis.setOrderdate(new Date());
			orderHis.setOrdermethod("1");
			logger.info("新增用户订购关系成功！");
			try {
				Vasservice vasservice = this.smsSenderDao.queryVasService(new Integer(orderHis.getServiceuniqueid().intValue()));
				orderHis.setFeetype(vasservice.getFeetype());
				if (vasservice.getFeetype().equals("1"))
					orderHis.setFee(Double.valueOf(vasservice.getOrderfee() != null ? vasservice.getOrderfee().toString() : "0"));
				else if (vasservice.getFeetype().equals("2")) {
					orderHis.setFee(Double.valueOf(vasservice.getOndemandfee() != null ? vasservice.getOndemandfee().toString() : "0"));
				}
				this.smsSenderDao.saveOrderHis(orderHis);

				UserOrder order = new UserOrder();
				UserOrderId id = new UserOrderId();
				id.setCellphonenumber(orderHis.getCellphonenumber());
				id.setServiceuniqueid(orderHis.getServiceuniqueid());
				order.setId(id);
				order.setChargeparty(orderHis.getChargeparty());
				order.setFee(orderHis.getFee());
				order.setFeetype(orderHis.getFeetype());
				order.setOrderdate(orderHis.getOrderdate());
				order.setOrderhisid(Integer.valueOf(hisID));
				order.setOrdermethod(orderHis.getOrdermethod());
				order.setPriority(Integer.valueOf(2));
				order.setStatus(Integer.valueOf(0));

				order.setUserarea(temp == null ? null : temp[0]);
				this.smsSenderDao.saveUserOrder(order);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	public void setSmsSenderDao(SmsSenderDao smsSenderDao) {
		this.smsSenderDao = smsSenderDao;
	}

	public SmsSenderDao getSmsSenderDao() {
		return this.smsSenderDao;
	}

	public SendNotificationMessageHandler getSendNotificationMessageHandler() {
		return this.sendNotificationMessageHandler;
	}

	public void setSendNotificationMessageHandler(SendNotificationMessageHandler sendNotificationMessageHandler) {
		this.sendNotificationMessageHandler = sendNotificationMessageHandler;
	}

	public MessageQueue getMO_queue() {
		return this.MO_queue;
	}

	public void setMO_queue(MessageQueue mo_queue) {
		this.MO_queue = mo_queue;
	}

	class Op_Type {
		SmsSender.enum_optype type;
		String cmdtext;
		String service_id;
		String product_id;
		private Vasservice vasservice;
		private SmsSenderDto smsSenderDto;

		Op_Type(SmsSender.enum_optype _o) {
			this.type = _o;
		}

		Op_Type set_service_id(String _o) {
			this.service_id = _o;
			return this;
		}

		Op_Type set_product_id(String _o) {
			this.product_id = _o;
			return this;
		}

		Op_Type set_cmdtext(String _o) {
			this.cmdtext = _o;
			return this;
		}

		Op_Type set_smsSenderDto(SmsSenderDto smsSenderDto) {
			this.smsSenderDto = smsSenderDto;
			return this;
		}

		public Vasservice getVasservice() {
			return this.vasservice;
		}

		public void setVasservice(Vasservice vasservice) {
			this.vasservice = vasservice;
		}

		public SmsSenderDto getSmsSenderDto() {
			return this.smsSenderDto;
		}

		public void setSmsSenderDto(SmsSenderDto smsSenderDto) {
			this.smsSenderDto = smsSenderDto;
		}

		public String getCmdtext() {
			return this.cmdtext;
		}

		public void setCmdtext(String cmdtext) {
			this.cmdtext = cmdtext;
		}
	}

	static enum enum_optype {
		ORDER, DIANBO, CANCEL, CANCEL_ALL, CONFIRM, OTHER;
	}
}

package com.zbensoft.mmsmp.vac.ra.mina.util;

import java.util.Map;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.message.CheckRequest;
import com.zbensoft.mmsmp.common.ra.common.message.CheckResponse;
import com.zbensoft.mmsmp.vac.ra.aaa.CheckPriceConfirmResp;
import com.zbensoft.mmsmp.vac.ra.aaa.CheckPriceResp;
import com.zbensoft.mmsmp.vac.ra.aaa.Header;
import com.zbensoft.mmsmp.vac.ra.mina.listen.CheckMessageQuence;

public class MessageProcessor {

	private static final Logger logger = Logger.getLogger(MessageProcessor.class);
	private CheckMessageQuence cMessageQuence;

	public MessageProcessor(CheckMessageQuence messageQuence) {
		this.cMessageQuence = messageQuence;
	}

	public MessageProcessor() {
	}

	public static void dealCheckRequest(CheckRequest cRequest, String threadname) {
		String user_number = cRequest.getUser_number();
		if (user_number == null) {
			user_number = "";
		}
		String sp_product_id = cRequest.getSp_product_id();
		if (sp_product_id == null) {
			sp_product_id = "";
		}
		String sp_id = cRequest.getSp_id();
		if (sp_id == null) {
			sp_id = "";
		}
		String service_id = cRequest.getService_id();
		if (service_id == null) {
			service_id = "";
		}
		String src_SequenceNumber = cRequest.getSrc_SequenceNumber();
		if (src_SequenceNumber == null) {
			src_SequenceNumber = "";
		}
		String ServiceType = cRequest.getServiceType();
		if (ServiceType == null) {
			ServiceType = "";
		}
		String LinkID = cRequest.getLinkID();
		if (LinkID == null) {
			LinkID = "";
		}

		Integer messageid = cRequest.getMessageid();
		if (ServiceType.equals("0"))
			Check.check_price(src_SequenceNumber, user_number, service_id, sp_id, null, null, messageid, threadname);
		else if (ServiceType.equals("1"))
			Check.user_order(src_SequenceNumber, user_number, sp_product_id, sp_id, messageid, threadname);
		else if (ServiceType.equals("2"))
			Check.user_unorder(src_SequenceNumber, user_number, sp_product_id, service_id, sp_id, false, messageid, threadname);
		else if (ServiceType.equals("3"))
			Check.user_unorder(src_SequenceNumber, user_number, sp_product_id, service_id, sp_id, true, messageid, threadname);
		else if (ServiceType.equals("4"))
			Check.user_onceorder(src_SequenceNumber, user_number, sp_product_id, sp_id, messageid, threadname);
		else if (ServiceType.equals("5"))
			Check.check_price(src_SequenceNumber, user_number, service_id, sp_id, LinkID, Integer.valueOf(2), messageid, threadname);
		else if (ServiceType.equals("6"))
			Check.check_priceconfirm(src_SequenceNumber, 0, messageid, threadname);
		else if (ServiceType.equals("7"))
			Check.check_relation(src_SequenceNumber, user_number, sp_product_id, sp_id, messageid, threadname);
	}

	public void dealCheckResponse(Header resp) {
		Integer messageid = Integer.valueOf(0);
		CheckPriceResp cpr = new CheckPriceResp();
		CheckPriceConfirmResp cpcr = new CheckPriceConfirmResp();
		if ((resp instanceof CheckPriceResp)) {
			cpr = (CheckPriceResp) resp;
			messageid = cpr.getSequenceId();
		} else if ((resp instanceof CheckPriceConfirmResp)) {
			cpcr = (CheckPriceConfirmResp) resp;
			messageid = cpcr.getSequenceId();
		} else {
			logger.info("the cpronse message is not instanceof CheckPricecpr or CheckPriceConfirmcpr");
			return;
		}

		Map map = this.cMessageQuence.getRequestMap();
		CheckRequest request = (CheckRequest) map.get(messageid);

		CheckResponse response = new CheckResponse();
		if (request != null) {
			map.remove(messageid);
			response.setCRequest(request);
			String ServiceType = request.getServiceType();
			if (ServiceType.equals("0")) {
				response.setNeedConfirm(String.valueOf(cpr.getNeedConfirm()));
				response.setLinkID(cpr.getLinkID_String());
				response.setProductID(cpr.getProductID_String());
				response.setServerResult_code(String.valueOf(cpr.getResult_Code()));
				response.setServerMessage(cpr.getReturnMessage_String());
				response.setSP_ProductID(cpr.getSP_ProductID_String());
				response.setSPEC_ProductID(cpr.getSPEC_ProductID_String());
				response.setSrc_SequenceNumber(request.getSrc_SequenceNumber());
				response.setMessageid(cpr.getSequenceId());
				int isjiaquan = cpr.getResult_Code().intValue();
				if (isjiaquan == 0) {
					response.setResult_Code("0");
					response.setReturnMessage("鉴权批价成功");
				} else {
					response.setResult_Code("1");
					response.setReturnMessage("鉴权批价失败");
				}

			} else if (ServiceType.equals("1")) {
				response.setNeedConfirm(String.valueOf(cpr.getNeedConfirm()));
				response.setLinkID(cpr.getLinkID_String());
				response.setProductID(cpr.getProductID_String());
				response.setServerResult_code(String.valueOf(cpr.getResult_Code()));
				response.setServerMessage(cpr.getReturnMessage_String());
				response.setSP_ProductID(cpr.getSP_ProductID_String());
				response.setSPEC_ProductID(cpr.getSPEC_ProductID_String());

				response.setSrc_SequenceNumber(request.getSrc_SequenceNumber());
				response.setMessageid(cpr.getSequenceId());
				int isdingzhi = cpr.getResult_Code().intValue();
				if (isdingzhi == 0) {
					response.setResult_Code("0");
					response.setReturnMessage("定购成功");
				} else if (isdingzhi == 1200) {
					response.setResult_Code("1");
					response.setReturnMessage("您已经定制过此产品");
				} else {
					response.setResult_Code("1");
					response.setReturnMessage("定购失败");
				}
			} else if (ServiceType.equals("2")) {
				if (cpr == null)
					;
				int isunorder = cpr.getResult_Code().intValue();
				response.setNeedConfirm(String.valueOf(cpr.getNeedConfirm()));
				response.setLinkID(cpr.getLinkID_String());
				response.setProductID(cpr.getProductID_String());
				response.setServerResult_code(String.valueOf(cpr.getResult_Code()));
				response.setServerMessage(cpr.getReturnMessage_String());
				response.setSP_ProductID(cpr.getSP_ProductID_String());
				response.setSPEC_ProductID(cpr.getSPEC_ProductID_String());

				response.setSrc_SequenceNumber(request.getSrc_SequenceNumber());
				response.setMessageid(cpr.getSequenceId());
				if (isunorder == 0) {
					response.setResult_Code("0");
					response.setReturnMessage("退定成功");
				} else {
					response.setResult_Code("1");
					response.setReturnMessage("退定失败");
				}
			} else if (ServiceType.equals("3")) {
				int isallunoder = cpr.getResult_Code().intValue();
				response.setNeedConfirm(String.valueOf(cpr.getNeedConfirm()));
				response.setLinkID(cpr.getLinkID_String());
				response.setProductID(cpr.getProductID_String());
				response.setServerResult_code(String.valueOf(cpr.getResult_Code()));
				response.setServerMessage(cpr.getReturnMessage_String());
				response.setSP_ProductID(cpr.getSP_ProductID_String());
				response.setSPEC_ProductID(cpr.getSPEC_ProductID_String());

				response.setSrc_SequenceNumber(request.getSrc_SequenceNumber());
				response.setMessageid(cpr.getSequenceId());
				if (isallunoder == 0) {
					response.setResult_Code("0");
					response.setReturnMessage("退定所有成功");
				} else {
					response.setResult_Code("1");
					response.setReturnMessage("退定所有失败");
				}
			} else if (ServiceType.equals("4")) {
				response.setNeedConfirm(String.valueOf(cpr.getNeedConfirm()));
				response.setLinkID(cpr.getLinkID_String());
				response.setProductID(cpr.getProductID_String());
				response.setServerResult_code(String.valueOf(cpr.getResult_Code()));
				response.setServerMessage(cpr.getReturnMessage_String());
				response.setSP_ProductID(cpr.getSP_ProductID_String());
				response.setSPEC_ProductID(cpr.getSPEC_ProductID_String());
				response.setSrc_SequenceNumber(request.getSrc_SequenceNumber());
				response.setMessageid(cpr.getSequenceId());
				int isdianbo = cpr.getResult_Code().intValue();
				if (isdianbo == 0) {
					response.setResult_Code("0");
					response.setReturnMessage("Ondemand Success");
				} else {
					response.setResult_Code("1");
					response.setReturnMessage("OnDemand Failed");
				}
			} else if (ServiceType.equals("5")) {
				int isdianbojiquan = cpr.getResult_Code().intValue();
				response.setNeedConfirm(String.valueOf(cpr.getNeedConfirm()));
				response.setLinkID(cpr.getLinkID_String());
				response.setProductID(cpr.getProductID_String());
				response.setServerResult_code(String.valueOf(cpr.getResult_Code()));
				response.setServerMessage(cpr.getReturnMessage_String());
				response.setSP_ProductID(cpr.getSP_ProductID_String());
				response.setSPEC_ProductID(cpr.getSPEC_ProductID_String());
				response.setSrc_SequenceNumber(request.getSrc_SequenceNumber());
				response.setServerResult_code(String.valueOf(cpr.getResult_Code()));
				response.setMessageid(cpr.getSequenceId());
				if (isdianbojiquan == 0) {
					response.setResult_Code("0");
					response.setReturnMessage("点播的鉴权计费成功");
				} else {
					response.setResult_Code("1");
					response.setReturnMessage("点播的鉴权计费失败");
				}

			} else if (ServiceType.equals("6")) {
				response.setMessageid(cpcr.getSequenceId());
				if (cpcr.getResult_Code().intValue() == 0) {
					response.setResult_Code("0");
					response.setReturnMessage("点播成功");
				} else {
					response.setResult_Code("-1");
					response.setReturnMessage("点播二次鉴权失败");
				}

			} else if (ServiceType.equals("7")) {
				response.setNeedConfirm(String.valueOf(cpr.getNeedConfirm()));
				response.setLinkID(cpr.getLinkID_String());
				response.setProductID(cpr.getProductID_String());
				response.setServerResult_code(String.valueOf(cpr.getResult_Code()));
				response.setServerMessage(cpr.getReturnMessage_String());
				response.setSP_ProductID(cpr.getSP_ProductID_String());
				response.setSPEC_ProductID(cpr.getSPEC_ProductID_String());

				response.setSrc_SequenceNumber(request.getSrc_SequenceNumber());
				response.setServerResult_code(String.valueOf(cpr.getResult_Code()));
				response.setMessageid(cpr.getSequenceId());
				int isrelation = cpr.getResult_Code().intValue();
				logger.info("==========>:isrelation:" + isrelation);
				if (isrelation == 1201) {
					response.setResult_Code("1");
					response.setReturnMessage("定购关系不存在");
				} else if ((isrelation == 0) || (isrelation == 1200)) {
					response.setResult_Code("0");
					response.setReturnMessage("定购关系已存在");
				} else {
					response.setResult_Code("-1");
					response.setReturnMessage("查询定购关系异常");
				}

			}

		}

		String outstr = CheckXml.XmlForObject(response);
		logger.info("the check result : \r\n" + outstr);

		if (request.getGlobalMessageid().toLowerCase().startsWith("admin"))
			CheckMessageQuence.resp = outstr;
		else
			try {
				response.setGlobalMessageid(request.getGlobalMessageid());
				this.cMessageQuence.getResponseQuence().put(response);
				logger.info("put response to response quence success,   messageid = " + request.getGlobalMessageid());
			} catch (InterruptedException e) {
				logger.info("put response to response quence failed,   messageid = " + messageid, e);
			}
	}

	public static void main(String[] args) {
		System.out.println(null instanceof CheckPriceConfirmResp);
	}
}

package com.zbensoft.mmsmp.corebiz.handle.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.CheckRequest;
import com.zbensoft.mmsmp.common.ra.common.message.CheckResponse;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyResponse;
import com.zbensoft.mmsmp.common.ra.common.message.WOCheckRequest;
import com.zbensoft.mmsmp.common.ra.common.message.WOCheckResponse;
import com.zbensoft.mmsmp.common.ra.send.util.SmsSenderUtil;
import com.zbensoft.mmsmp.common.ra.utils.DateHelper;
import com.zbensoft.mmsmp.common.ra.vacNew.util.Utility;
import com.zbensoft.mmsmp.common.ra.vas.sjb.unibusiness.Constants;
import com.zbensoft.mmsmp.corebiz.cache.DataCache;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.OrderRelationRequest;
import com.zbensoft.mmsmp.corebiz.handle.impl.sms.SmsSenderDto;
import com.zbensoft.mmsmp.corebiz.message.OrderRelationMessage;
import com.zbensoft.mmsmp.corebiz.route.IMessageRouter;
import com.zbensoft.mmsmp.corebiz.util.HttpRequestHelper;
import com.zbensoft.mmsmp.log.COREBIZ_LOG;

public class UniBusinessHandlerImpl {
	static final Logger logger = Logger.getLogger(UniBusinessHandlerImpl.class);

	IMessageRouter messageRouter;
	// TODO 本类中所有使用doa的地方都未进行修改，请检查
	// DaoUtil daoUtil;

	DataCache dataCache;

	ConcurrentHashMap<String, AbstractMessage> dataMap;
	ConcurrentHashMap<String, AbstractMessage> commonMap;

	public void doHandler(AbstractMessage message) {
		if ((message instanceof OrderRelationMessage)) {
			orderRelationManage((OrderRelationMessage) message);
		} else if ((message instanceof WOCheckResponse)) {
			doWoAgent((WOCheckResponse) message);
		} else if ((message instanceof CheckResponse)) {
			doVacAgent((CheckResponse) message);
		} else if ((message instanceof OrderRelationUpdateNotifyResponse)) {
			updateOrder((OrderRelationUpdateNotifyResponse) message);
		}
	}

	public void orderRelationManage(OrderRelationMessage orderRelationMessage) {
		this.dataMap.put(orderRelationMessage.getGlobalMessageid(), orderRelationMessage);

		COREBIZ_LOG.INFO("starting to handler OrderRelationMessage......");
		OrderRelationRequest orderRelationRequest = orderRelationMessage.getOrderRelationRequest();
		String channel = "2";
		int status = 2;
		Vasservice vasservice = null;
		try {
			channel = orderRelationRequest.getChannel();
			status = Integer.valueOf(orderRelationRequest.getStatus()).intValue();

			// vasservice =
			// this.daoUtil.getSmsSenderDao().getVASServiceByServiceCode(orderRelationRequest.getProductID());
			List<Vasservice> servList=HttpRequestHelper.getNormalStatus();
			if(servList!=null&&servList.size()>0){
				for(int i=0;i<servList.size();i++){
					try {
						if(servList.get(i).getVasserviceReserveInfo().getSpProductid().equals(orderRelationRequest.getProductID())){
							vasservice=servList.get(i);
							break;
						}
					} catch (Exception e) {
						
					}
				}
			}
			
			if (vasservice == null) {
				vasservice = new Vasservice();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			COREBIZ_LOG.ERROR("channel : " + channel + " status : " + status + " vasservice: " + vasservice);
		}

		boolean inWhiteList = this.dataCache.isWhiteUser(orderRelationRequest.getUserPhone(),
				orderRelationRequest.getProductID(), vasservice.getVasid());
		COREBIZ_LOG.INFO("is the user in whitelist: " + inWhiteList + " globalMessageid="
				+ orderRelationMessage.getGlobalMessageid());

		int intChannel = Integer.parseInt(channel);

		if ((intChannel == 1) || (intChannel == 2)) {
			if (intChannel == 1)
				COREBIZ_LOG.INFO("order from web....");
			else
				COREBIZ_LOG.INFO("order from wap....");
			int feetype = 0;
			try {
				if (Constants.OrderRelation_Status_Order == status) {
					orderRelationMessage.getOrderRelationRequest().setFeeType(1);
					feetype = 1;
				} else if (Constants.OrderRelation_Status_Demand == status) {
					orderRelationMessage.getOrderRelationRequest().setFeeType(4);
					feetype = 2;
				} else if (Constants.OrderRelation_Status_Cancel == status) {
					orderRelationMessage.getOrderRelationRequest().setFeeType(2);
					feetype = 1;
				} else {
					COREBIZ_LOG.ERROR("unknown web order type...");
					return;
				}

				if (!inWhiteList) {
					CheckRequest checkRequest = new CheckRequest();
					checkRequest.setSp_product_id(orderRelationRequest.getProductID());
					checkRequest.setSp_id(orderRelationRequest.getSpCode());
					checkRequest.setService_id(orderRelationRequest.getServiceId());

					checkRequest.setUser_number(orderRelationRequest.getChargeparty());
					checkRequest.setServiceType(String.valueOf(orderRelationRequest.getFeeType()));
					checkRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
					this.messageRouter.doRouter(checkRequest);
					COREBIZ_LOG.INFO("order relation check vacAgent ......[chargeparty:"
							+ orderRelationRequest.getChargeparty() + " globalMessageid:"
							+ orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
				} else {
					if (orderRelationRequest.getUserType() == 2) {
						WOCheckResponse wr = new WOCheckResponse();
						wr.setChargetNumber(orderRelationRequest.getChargeparty());
						this.commonMap.put(
								"ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID(), wr);
						COREBIZ_LOG.INFO("赠送缓存计费号码给彩信下行用key=======ZS" + orderRelationRequest.getUserPhone()
								+ orderRelationRequest.getProductID());
					}

					saveOrder(orderRelationMessage.getGlobalMessageid(), feetype);

					if ((Constants.OrderRelation_Status_Order == status)
							|| (Constants.OrderRelation_Status_Cancel == status)) {
						String orderNotifyUrl = this.dataCache.getSpurlByVaspid((orderRelationRequest.getSpCode()));// this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(orderRelationRequest.getSpCode());

						COREBIZ_LOG.INFO("the relation notifysp url,  spid:" + orderRelationRequest.getSpCode()
								+ "     url:" + orderNotifyUrl);

						OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();

						orderRelationUpdateNotifyRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
						orderRelationUpdateNotifyRequest.setNotifySPURL(orderNotifyUrl);
						orderRelationUpdateNotifyRequest
								.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
						if (status == 0) {
							orderRelationUpdateNotifyRequest.setContent(vasservice.getOrdercode());
						} else {
							orderRelationUpdateNotifyRequest.setContent(vasservice.getCancelordercode());
						}
						orderRelationUpdateNotifyRequest
								.setEffectiveDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
						orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
						orderRelationUpdateNotifyRequest
								.setExpireDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
						orderRelationUpdateNotifyRequest.setLinkId("");
						orderRelationUpdateNotifyRequest.setServiceType("31");
						orderRelationUpdateNotifyRequest.setProductId(orderRelationRequest.getProductID());
						orderRelationUpdateNotifyRequest.setSpId(orderRelationRequest.getSpCode());
						orderRelationUpdateNotifyRequest
								.setTime_stamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
						orderRelationUpdateNotifyRequest.setUpdateDesc("");
						orderRelationUpdateNotifyRequest.setUpdateTime("");
						orderRelationUpdateNotifyRequest
								.setUpdateType(Integer.valueOf(orderRelationRequest.getFeeType()));
						orderRelationUpdateNotifyRequest.setUserId(orderRelationRequest.getUserPhone());
						orderRelationUpdateNotifyRequest
								.setUserIdType(Integer.valueOf(orderRelationRequest.getFeeType()));

						this.messageRouter.doRouter(orderRelationUpdateNotifyRequest);
						COREBIZ_LOG.INFO("send order relation to spAgent ......[user:"
								+ orderRelationRequest.getUserPhone() + "   globalMessageid:"
								+ orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
					} else if (Constants.OrderRelation_Status_Demand == status) {
						String url = HttpRequestHelper.getSmsSenderUrl(orderRelationRequest.getServiceId());// this.daoUtil.getSmsSenderDao().getSmsSenderUrl(orderRelationRequest.getServiceId());

						String vasId = HttpRequestHelper.getServiceIDbyProductid(orderRelationRequest.getProductID());// this.daoUtil.getSmsSenderDao().getService_idByProduct_id(orderRelationRequest.getProductID());

						MO_SMMessage mosms = new MO_SMMessage();

						mosms.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
						mosms.setSendAddress(orderRelationRequest.getUserPhone());
						mosms.setVasId(vasId);
						mosms.setSmsText(vasservice.getOndemandcode());
						mosms.setLinkId("");
						mosms.setNotirySPURL(url);

						mosms.setSequence_Number_1(Integer.valueOf(111));
						mosms.setSequence_Number_2(Integer.valueOf(222));
						mosms.setSequence_Number_3(Integer.valueOf(333));
						mosms.setMessageCoding((byte) 15);
						mosms.setContentLength(vasservice.getOndemandcode().length());
						this.messageRouter.doRouter(mosms);
						COREBIZ_LOG.INFO("send demand to spAgent ......[user:" + orderRelationRequest.getUserPhone()
								+ "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:"
								+ status + "]");
					} else {
						COREBIZ_LOG.INFO("unknown order type...[status: status:" + status + "]");
					}
				}
			} catch (Throwable ex) {
				COREBIZ_LOG.ERROR("", ex);
			}
		} else if (intChannel == 3) {
			COREBIZ_LOG.INFO("order from iphone.....");
			int reqtype = 0;
			int feetype = 0;
			if (Constants.OrderRelation_Status_Order == status) {
				reqtype = 2;
				feetype = 1;
			} else if (Constants.OrderRelation_Status_Demand == status) {
				reqtype = 1;
				feetype = 2;
			} else {
				COREBIZ_LOG.ERROR("unknown web order type...");
				return;
			}
			try {
				if (!inWhiteList) {
					WOCheckRequest wc = new WOCheckRequest();
					wc.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());

					wc.setUserPhone(orderRelationRequest.getChargeparty());
					wc.setSp_productID(orderRelationRequest.getProductID());
					wc.setServicename(orderRelationRequest.getServiceName());
					wc.setFee(orderRelationRequest.getFee());
					wc.setFeeType(String.valueOf(feetype));
					wc.setPeroid(orderRelationRequest.getPeroid());
					wc.setReqType(reqtype);
					this.messageRouter.doRouter(wc);
					COREBIZ_LOG.INFO("order relation check woAgent......[chargeparty:"
							+ orderRelationRequest.getChargeparty() + "   globalMessageid:"
							+ orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
				} else {
					if (orderRelationRequest.getUserType() == 2) {
						WOCheckResponse wr = new WOCheckResponse();
						wr.setChargetNumber(orderRelationRequest.getChargeparty());
						this.commonMap.put(
								"ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID(), wr);
						COREBIZ_LOG.INFO("赠送缓存计费号码给彩信下行用key=======ZS" + orderRelationRequest.getUserPhone()
								+ orderRelationRequest.getProductID());
					}

					saveOrder(orderRelationMessage.getGlobalMessageid(), feetype);

					if (Constants.OrderRelation_Status_Order == status) {

						String orderNotifyUrl = this.dataCache.getSpurlByVaspid((orderRelationRequest.getSpCode()));// this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(orderRelationRequest.getSpCode());

						COREBIZ_LOG.INFO("the relation notifysp url,  spid:" + orderRelationRequest.getSpCode()
								+ "     url:" + orderNotifyUrl);

						Calendar calendar = Calendar.getInstance();
						calendar.add(2, orderRelationRequest.getPeroid());

						OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();

						orderRelationUpdateNotifyRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
						orderRelationUpdateNotifyRequest.setNotifySPURL(orderNotifyUrl);
						orderRelationUpdateNotifyRequest
								.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
						if (status == 0) {
							orderRelationUpdateNotifyRequest.setContent(vasservice.getOrdercode());
						} else {
							orderRelationUpdateNotifyRequest.setContent(vasservice.getCancelordercode());
						}
						orderRelationUpdateNotifyRequest
								.setEffectiveDate(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
						orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
						orderRelationUpdateNotifyRequest
								.setExpireDate(DateHelper.getString(calendar.getTime(), "yyyyMMddHHmmss"));
						orderRelationUpdateNotifyRequest.setLinkId("");
						orderRelationUpdateNotifyRequest.setServiceType("31");
						orderRelationUpdateNotifyRequest.setProductId(orderRelationRequest.getProductID());
						orderRelationUpdateNotifyRequest.setSpId(orderRelationRequest.getSpCode());
						orderRelationUpdateNotifyRequest
								.setTime_stamp(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
						orderRelationUpdateNotifyRequest.setUpdateDesc("");
						orderRelationUpdateNotifyRequest
								.setUpdateTime(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
						orderRelationUpdateNotifyRequest
								.setUpdateType(Integer.valueOf(orderRelationRequest.getFeeType()));
						orderRelationUpdateNotifyRequest.setUserId(orderRelationRequest.getUserPhone());
						this.messageRouter.doRouter(orderRelationUpdateNotifyRequest);

						COREBIZ_LOG.INFO("send order relation to spAgent ......[user:"
								+ orderRelationRequest.getUserPhone() + "   globalMessageid:"
								+ orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
					} else if (Constants.OrderRelation_Status_Demand == orderRelationRequest.getStatus()) {
						String url = HttpRequestHelper.getSmsSenderUrl(orderRelationRequest.getServiceId());// this.daoUtil.getSmsSenderDao().getSmsSenderUrl(orderRelationRequest.getServiceId());

						String vasId = HttpRequestHelper.getServiceIDbyProductid(orderRelationRequest.getProductID());// this.daoUtil.getSmsSenderDao().getService_idByProduct_id(orderRelationRequest.getProductID());
						String linkid = "ACE" + SmsSenderUtil.generateRandomCode(5);
						COREBIZ_LOG.INFO("linkid generated by aceway is : " + linkid);

						MO_SMMessage mosms = new MO_SMMessage();

						mosms.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
						mosms.setSendAddress(orderRelationRequest.getUserPhone());
						mosms.setVasId(vasId);
						mosms.setSmsText(vasservice.getOndemandcode());
						mosms.setLinkId(linkid);
						mosms.setNotirySPURL(url);

						mosms.setSequence_Number_1(Integer.valueOf(111));
						mosms.setSequence_Number_2(Integer.valueOf(222));
						mosms.setSequence_Number_3(Integer.valueOf(333));
						mosms.setMessageCoding((byte) 15);
						mosms.setContentLength(vasservice.getOndemandcode().length());
						this.messageRouter.doRouter(mosms);

						COREBIZ_LOG.INFO("send demand to spAgent ......[user:" + orderRelationRequest.getUserPhone()
								+ "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:"
								+ status + "]");
					}
				}
			} catch (Throwable ex) {
				COREBIZ_LOG.ERROR("", ex);
			}

		} else if (intChannel == 6) {
			COREBIZ_LOG.INFO(" iphone cannel  order relation...");

			saveOrder(orderRelationMessage.getGlobalMessageid(), 1);

			String orderNotifyUrl = this.dataCache.getSpurlByVaspid((orderRelationRequest.getSpCode()));// this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(orderRelationRequest.getSpCode());

			COREBIZ_LOG.INFO("the relation notifysp url,  spid:" + orderRelationRequest.getSpCode() + "     url:"
					+ orderNotifyUrl);

			Calendar calendar = Calendar.getInstance();
			calendar.add(2, orderRelationRequest.getPeroid());

			OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();

			orderRelationUpdateNotifyRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
			orderRelationUpdateNotifyRequest.setNotifySPURL(orderNotifyUrl);
			orderRelationUpdateNotifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));

			orderRelationUpdateNotifyRequest.setContent(vasservice.getCancelordercode());
			orderRelationUpdateNotifyRequest.setEffectiveDate(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
			orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
			orderRelationUpdateNotifyRequest.setExpireDate(DateHelper.getString(calendar.getTime(), "yyyyMMddHHmmss"));
			orderRelationUpdateNotifyRequest.setLinkId("");
			orderRelationUpdateNotifyRequest.setServiceType("31");
			orderRelationUpdateNotifyRequest.setProductId(orderRelationRequest.getProductID());
			orderRelationUpdateNotifyRequest.setSpId(orderRelationRequest.getSpCode());
			orderRelationUpdateNotifyRequest.setTime_stamp(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
			orderRelationUpdateNotifyRequest.setUpdateDesc("");
			orderRelationUpdateNotifyRequest.setUpdateTime(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
			orderRelationUpdateNotifyRequest.setUpdateType(Integer.valueOf(2));
			orderRelationUpdateNotifyRequest.setUserId(orderRelationRequest.getUserPhone());
			this.messageRouter.doRouter(orderRelationUpdateNotifyRequest);

			COREBIZ_LOG.INFO("send order relation to spAgent ......[user:" + orderRelationRequest.getUserPhone()
					+ "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
		} else {
			COREBIZ_LOG.ERROR("channel Error...");
		}
	}

	public void doWoAgent(WOCheckResponse response) {
		COREBIZ_LOG.INFO("=====> receive WOCheckResponse from woAgent !  code:" + response.getReturnStr());

		OrderRelationMessage orderRelationMessage = (OrderRelationMessage) this.dataMap
				.get(response.getGlobalMessageid());
		OrderRelationRequest orderRelationRequest = orderRelationMessage.getOrderRelationRequest();
		int status = Integer.valueOf(orderRelationRequest.getStatus()).intValue();
		Vasservice vasservice = new Vasservice();// this.daoUtil.getSmsSenderDao().getVASServiceByServiceCode(orderRelationRequest.getProductID());
		List<Vasservice> servList=HttpRequestHelper.getNormalStatus();
		if(servList!=null&&servList.size()>0){
			for(int i=0;i<servList.size();i++){
				try {
					if(servList.get(i).getVasserviceReserveInfo().getSpProductid().equals(orderRelationRequest.getProductID())){
						vasservice=servList.get(i);
						break;
					}
				} catch (Exception e) {
					
				}
			}
		}
		if (Constants.OrderRelation_Status_Order == status) {
			COREBIZ_LOG.INFO("order relation  status" + status);
			if (response.getReturnStr().equals("20000")) {
				COREBIZ_LOG.INFO("=====>order relation check woAgent successfully!");

				if (orderRelationRequest.getUserType() == 2) {
					WOCheckResponse wr = new WOCheckResponse();
					wr.setChargetNumber(orderRelationRequest.getChargeparty());
					this.commonMap.put("ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID(),
							wr);
					COREBIZ_LOG.INFO("赠送缓存计费号码给彩信下行用key=======ZS" + orderRelationRequest.getUserPhone()
							+ orderRelationRequest.getProductID());
				}

				saveOrder(response.getGlobalMessageid(), 1);

				String orderNotifyUrl = this.dataCache.getSpurlByVaspid((orderRelationRequest.getSpCode()));// this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(orderRelationRequest.getSpCode());

				COREBIZ_LOG.INFO("the relation notifysp url,  spid:" + orderRelationRequest.getSpCode() + "     url:"
						+ orderNotifyUrl);

				Calendar calendar = Calendar.getInstance();
				calendar.add(2, orderRelationRequest.getPeroid());

				OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();

				orderRelationUpdateNotifyRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
				orderRelationUpdateNotifyRequest.setNotifySPURL(orderNotifyUrl);
				orderRelationUpdateNotifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
				if (status == 0) {
					orderRelationUpdateNotifyRequest.setContent(vasservice.getOrdercode());
				} else {
					orderRelationUpdateNotifyRequest.setContent(vasservice.getCancelordercode());
				}
				orderRelationUpdateNotifyRequest.setEffectiveDate(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
				orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
				orderRelationUpdateNotifyRequest
						.setExpireDate(DateHelper.getString(calendar.getTime(), "yyyyMMddHHmmss"));
				orderRelationUpdateNotifyRequest.setLinkId("");
				orderRelationUpdateNotifyRequest.setServiceType("31");
				orderRelationUpdateNotifyRequest.setProductId(orderRelationRequest.getProductID());
				orderRelationUpdateNotifyRequest.setSpId(orderRelationRequest.getSpCode());
				orderRelationUpdateNotifyRequest.setTime_stamp(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
				orderRelationUpdateNotifyRequest.setUpdateDesc("");
				orderRelationUpdateNotifyRequest.setUpdateTime(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
				orderRelationUpdateNotifyRequest.setUpdateType(Integer.valueOf(1));
				orderRelationUpdateNotifyRequest.setUserId(orderRelationRequest.getUserPhone());
				orderRelationUpdateNotifyRequest.setUserIdType(Integer.valueOf(1));
				this.messageRouter.doRouter(orderRelationUpdateNotifyRequest);
				COREBIZ_LOG.INFO("[orderNotifyUrl:" + orderNotifyUrl + "]");
			} else {
				String _msg = "订购失败";
				String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
				MT_SMMessage mtsmm = new MT_SMMessage();
				mtsmm.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
				mtsmm.setSmsText(_msg);
				mtsmm.setRcvAddresses(userPhoneArr);
				this.messageRouter.doRouter(mtsmm);
				COREBIZ_LOG.INFO(
						"=====>order relation check woAgent failed! response.returnStr()" + response.getReturnStr());
			}
		} else if (Constants.OrderRelation_Status_Demand == status) {
			COREBIZ_LOG
					.INFO("demand  status==" + status + "vasservice.getOndemandfee:" + orderRelationRequest.getFee());

			String wo_money = response.getReturnStr();
			double wo_money_double = Double.parseDouble(wo_money);

			double fee = orderRelationRequest.getFee();
			COREBIZ_LOG.INFO("[wo_money_double:" + wo_money_double + " fee:" + fee + "]");
			if (wo_money_double < fee) {
				COREBIZ_LOG.INFO("=====>demand fails, your money less fertile, please recharge");
				COREBIZ_LOG.INFO("=====>wo_money_double" + wo_money_double + "fee==" + fee);

				String _msg = "您定购失败！,沃币不足，请及时充值";
				String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
				MT_SMMessage mtsmm = new MT_SMMessage();
				mtsmm.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
				mtsmm.setSmsText(_msg);
				mtsmm.setRcvAddresses(userPhoneArr);
				this.messageRouter.doRouter(mtsmm);
			} else {
				COREBIZ_LOG.INFO("=====>demand check woAgent successfully!");

				if (orderRelationRequest.getUserType() == 2) {
					WOCheckResponse wr = new WOCheckResponse();
					wr.setChargetNumber(orderRelationRequest.getChargeparty());
					this.commonMap.put("ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID(),
							wr);
					COREBIZ_LOG.INFO("赠送缓存计费号码给彩信下行用key=======ZS" + orderRelationRequest.getUserPhone()
							+ orderRelationRequest.getProductID());
				}

				saveOrder(response.getGlobalMessageid(), 2);

				String url = HttpRequestHelper.getSmsSenderUrl(orderRelationRequest.getServiceId());// this.daoUtil.getSmsSenderDao().getSmsSenderUrl(orderRelationRequest.getServiceId());
				String vasId = HttpRequestHelper.getServiceIDbyProductid(orderRelationRequest.getProductID());// this.daoUtil.getSmsSenderDao().getService_idByProduct_id(orderRelationRequest.getProductID());
				String linkid = "ACE" + SmsSenderUtil.generateRandomCode(5);
				COREBIZ_LOG.INFO("linkid generated by aceway is : " + linkid);

				MO_SMMessage mosms = new MO_SMMessage();

				mosms.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
				mosms.setSendAddress(orderRelationRequest.getUserPhone());
				mosms.setVasId(vasId);
				mosms.setSmsText(vasservice.getOndemandcode());
				mosms.setLinkId(linkid);
				mosms.setNotirySPURL(url);

				mosms.setSequence_Number_1(Integer.valueOf(111));
				mosms.setSequence_Number_2(Integer.valueOf(222));
				mosms.setSequence_Number_3(Integer.valueOf(333));
				mosms.setMessageCoding((byte) 15);
				mosms.setContentLength(vasservice.getOndemandcode().length());

				this.messageRouter.doRouter(mosms);

				this.commonMap.put("WO" + linkid + orderRelationRequest.getUserPhone(), response);
				COREBIZ_LOG.INFO("缓存给彩信下行用key=======WO" + linkid + orderRelationRequest.getUserPhone());
			}
		}
	}

	public void doVacAgent(CheckResponse response) {
		COREBIZ_LOG.INFO("=====> receive CheckResponse from vacAgent !  code:" + response.getResult_Code());

		OrderRelationMessage orderRelationMessage = (OrderRelationMessage) this.dataMap
				.get(response.getCRequest().getGlobalMessageid());
		OrderRelationRequest orderRelationRequest = orderRelationMessage.getOrderRelationRequest();
		int status = Integer.valueOf(orderRelationRequest.getStatus()).intValue();
		Vasservice vasservice = new Vasservice();// this.daoUtil.getSmsSenderDao().getVASServiceByServiceCode(orderRelationRequest.getProductID());
		List<Vasservice> servList=HttpRequestHelper.getNormalStatus();
		if(servList!=null&&servList.size()>0){
			for(int i=0;i<servList.size();i++){
				try {
					if(servList.get(i).getVasserviceReserveInfo().getSpProductid().equals(orderRelationRequest.getProductID())){
						vasservice=servList.get(i);
						break;
					}
				} catch (Exception e) {
					
				}
			}
		}
		
		if ((response.getResult_Code().equals("0")) || (response.getResult_Code().equals("1201"))) {
			COREBIZ_LOG.INFO("=====>order relation check vacAgent successfully!");
			if (orderRelationRequest.getUserType() == 2) {
				WOCheckResponse wr = new WOCheckResponse();
				wr.setChargetNumber(orderRelationRequest.getChargeparty());
				this.commonMap.put("ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID(),
						wr);
				COREBIZ_LOG.INFO("赠送缓存计费号码给彩信下行用key=======ZS" + orderRelationRequest.getUserPhone()
						+ orderRelationRequest.getProductID());
			}

			if ((Constants.OrderRelation_Status_Order == status) || (Constants.OrderRelation_Status_Cancel == status)) {

				saveOrder(response.getCRequest().getGlobalMessageid(), 1);

				String orderNotifyUrl = HttpRequestHelper.getSmsSenderUrl(orderRelationRequest.getServiceId());// this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(orderRelationRequest.getSpCode());

				COREBIZ_LOG.INFO("the relation notifysp url,  spid:" + orderRelationRequest.getSpCode() + "     url:"
						+ orderNotifyUrl);

				OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();

				orderRelationUpdateNotifyRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
				orderRelationUpdateNotifyRequest.setNotifySPURL(orderNotifyUrl);
				orderRelationUpdateNotifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
				if (status == 0) {
					orderRelationUpdateNotifyRequest.setContent(vasservice.getOrdercode());
				} else {
					orderRelationUpdateNotifyRequest.setContent(vasservice.getCancelordercode());
				}
				orderRelationUpdateNotifyRequest
						.setEffectiveDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
				orderRelationUpdateNotifyRequest
						.setExpireDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				orderRelationUpdateNotifyRequest.setLinkId("");
				orderRelationUpdateNotifyRequest.setServiceType("31");
				orderRelationUpdateNotifyRequest.setProductId(orderRelationRequest.getProductID());
				orderRelationUpdateNotifyRequest.setSpId(orderRelationRequest.getSpCode());
				orderRelationUpdateNotifyRequest
						.setTime_stamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				orderRelationUpdateNotifyRequest.setUpdateDesc("");
				orderRelationUpdateNotifyRequest.setUpdateTime("");
				orderRelationUpdateNotifyRequest.setUserId(orderRelationRequest.getUserPhone());
				orderRelationUpdateNotifyRequest.setUpdateType(Integer.valueOf(orderRelationRequest.getFeeType()));
				orderRelationUpdateNotifyRequest.setUserIdType(Integer.valueOf(orderRelationRequest.getFeeType()));

				this.messageRouter.doRouter(orderRelationUpdateNotifyRequest);

				COREBIZ_LOG.INFO("send order relation to spAgent ......[user:" + orderRelationRequest.getUserPhone()
						+ "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status
						+ "]");
			} else if (Constants.OrderRelation_Status_Demand == status) {

				saveOrder(response.getCRequest().getGlobalMessageid(), 2);

				String url = HttpRequestHelper.getSmsSenderUrl(orderRelationRequest.getServiceId());// this.daoUtil.getSmsSenderDao().getSmsSenderUrl(orderRelationRequest.getServiceId());
				String vasId = HttpRequestHelper.getServiceIDbyProductid(orderRelationRequest.getProductID());// this.daoUtil.getSmsSenderDao().getService_idByProduct_id(orderRelationRequest.getProductID());

				MO_SMMessage mosms = new MO_SMMessage();

				mosms.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
				mosms.setSendAddress(orderRelationRequest.getUserPhone());
				mosms.setVasId(vasId);
				mosms.setSmsText(vasservice.getOndemandcode());
				mosms.setLinkId(response.getLinkID());
				mosms.setNotirySPURL(url);

				mosms.setSequence_Number_1(Integer.valueOf(111));
				mosms.setSequence_Number_2(Integer.valueOf(222));
				mosms.setSequence_Number_3(Integer.valueOf(333));
				mosms.setMessageCoding((byte) 15);
				mosms.setContentLength(vasservice.getOndemandcode().length());

				this.messageRouter.doRouter(mosms);

				COREBIZ_LOG.INFO("send demand to spAgent ......[user:" + orderRelationRequest.getUserPhone()
						+ "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status
						+ "]");
			}
		} else if ((response.getServerResult_code().equals("1200"))
				&& (Constants.OrderRelation_Status_Order == status)) {
			COREBIZ_LOG.INFO("=====>order relation check vacAgent failed==>order relation is exists !");

			String _msg = "您已经订购！,无须重复订购";
			String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
			MT_SMMessage mtsmm = new MT_SMMessage();
			mtsmm.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
			mtsmm.setSmsText(_msg);
			mtsmm.setRcvAddresses(userPhoneArr);

			this.messageRouter.doRouter(mtsmm);
		} else {
			COREBIZ_LOG.INFO("=====>order relation check vacAgent fail! response.serverResult_code:"
					+ response.getServerResult_code());

			String _msg = "";
			if (Constants.OrderRelation_Status_Order == status) {
				_msg = "订购失败";
			} else if (Constants.OrderRelation_Status_Demand == status)
				_msg = "点播失败";
			else
				_msg = "退订失败";
			String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
			MT_SMMessage mtsmm = new MT_SMMessage();
			mtsmm.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
			mtsmm.setSmsText(_msg);
			mtsmm.setRcvAddresses(userPhoneArr);
			this.messageRouter.doRouter(mtsmm);
		}
	}

	public void saveOrder(String globalMessageid, int feetype) {
		OrderRelationRequest orderRelationRequest = ((OrderRelationMessage) this.dataMap.get(globalMessageid))
				.getOrderRelationRequest();
		String _msg = "";
		String _msg1 = "";
		
		
		// TODO delete
		// this.daoUtil.getSmsSenderDao().getProductInfo(orderRelationRequest.getProductID(),feetype);
//		SmsSenderDto smsSenderDto =null;
		
		// TODO Finihs dao-->http
		SmsSenderDto spBean =  HttpRequestHelper.getVasSpCpInfo(orderRelationRequest.getProductID());// this.daoUtil.getSmsDAO().getSPInfoByProdId(orderRelationRequest.getProductID());
		if ((Constants.OrderRelation_Status_Order == orderRelationRequest.getStatus())
				|| (Constants.OrderRelation_Status_Demand == orderRelationRequest.getStatus())) {
			SaveOrderRaltion(orderRelationRequest.getUserPhone(), orderRelationRequest.getChargeparty(),
					orderRelationRequest.getProductID(), orderRelationRequest.getSpCode(),
					orderRelationRequest.getChannel(), "", "1", orderRelationRequest.getPeroid(),spBean.getFee());

			if (orderRelationRequest.getUserType() == 2) {
				_msg = "您赠送给" + orderRelationRequest.getUserPhone() + "[" + spBean.getServicename() + "]成功！,资费:"
						+ spBean.getFee() + "元";
				_msg1 = orderRelationRequest.getChargeparty() + "赠送给您" + "[" + spBean.getServicename() + "]成功";
			} else if (1 == feetype) {

				_msg = "您已成功订购" + spBean.getVaspname() + "的" + spBean.getServicename() + "服务，"
						+ spBean.getFee() + "元，由联通代收。客服电话" + spBean.getBusinessphone();
			} else if (2 == feetype) {
				_msg = "尊敬的用户，感谢您点播" + spBean.getVaspname() + "的" + spBean.getServicename() + "服务，"
						+ spBean.getFee() + "元，由联通代收。客服电话" + spBean.getBusinessphone() + "。";
			} else {
				_msg = "尊敬的用户，感谢您使用" + spBean.getVaspname() + "的" + spBean.getServicename() + "服务，"
						+ spBean.getFee() + "元，由联通代收。客服电话" + spBean.getBusinessphone() + "。";
			}
		} else if (Constants.OrderRelation_Status_Cancel == orderRelationRequest.getStatus()) {
			COREBIZ_LOG.INFO(
					"=====>cancel order relation notify sp successfully! status:" + orderRelationRequest.getStatus());
			String productInfoId = HttpRequestHelper.getServiceIDbyProductid(orderRelationRequest.getProductID());// Integer.valueOf(this.daoUtil.getSmsSenderDao().getUniqueid(orderRelationRequest.getProductID())).intValue();
			// this.daoUtil.getOrderRelationDAO().delOrderRelation(orderRelationRequest.getUserPhone(),
			// Integer.valueOf(uniqueid));
			HttpRequestHelper.delOrderRelation(orderRelationRequest.getUserPhone(),productInfoId);

			_msg = "您已成功退订" + spBean.getVaspname() + "的" + spBean.getServicename() + "服务，" + spBean.getFee()
					+ "元，由联通代收。客服电话" + spBean.getBusinessphone();
		}

		if (orderRelationRequest.getUserType() == 2) {
			String[] charPhoneArr = { orderRelationRequest.getChargeparty() };
			MT_SMMessage userMtsmm = new MT_SMMessage();
			userMtsmm.setGlobalMessageid(globalMessageid);
			userMtsmm.setSmsText(_msg);
			userMtsmm.setRcvAddresses(charPhoneArr);
			this.messageRouter.doRouter(userMtsmm);

			String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
			MT_SMMessage charMtsmm = new MT_SMMessage();
			charMtsmm.setGlobalMessageid(globalMessageid);
			charMtsmm.setSmsText(_msg1);
			charMtsmm.setRcvAddresses(userPhoneArr);
			this.messageRouter.doRouter(charMtsmm);
		} else {
			String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
			MT_SMMessage mtsmm = new MT_SMMessage();
			mtsmm.setGlobalMessageid(globalMessageid);
			mtsmm.setSmsText(_msg);
			mtsmm.setRcvAddresses(userPhoneArr);
			this.messageRouter.doRouter(mtsmm);
		}
	}

	public void SaveOrderRaltion(String userPhone, String chargeparty, String productId, String spId, String channel,
			String spOrderId, String notifySpFlag, int period,String fee) {
		Vasservice service = null;// this.daoUtil.getSmsSenderDao().getVASServiceByServiceCode(productId);
		List<Vasservice> servList=HttpRequestHelper.getNormalStatus();
		if(servList!=null&&servList.size()>0){
			for(int i=0;i<servList.size();i++){
				try {
					if(servList.get(i).getVasserviceReserveInfo().getSpProductid().equals(productId)){
						service=servList.get(i);
						break;
					}
				} catch (Exception e) {
					
				}
			}
		}
		if (service == null) {
			COREBIZ_LOG.INFO("productId not exists!");
		}
		String productInfoId = HttpRequestHelper.getServiceIDbyProductid(productId);// this.daoUtil.getSmsSenderDao().getUniqueid(productId);
		UserOrder userOrder = HttpRequestHelper.getOrderRelation(userPhone, productInfoId);// this.daoUtil.getSmsSenderDao().getOrderRelation(userPhone,
												// uniqueid);
		if ((userOrder != null) && (service.getFeetype().equals("1"))) {
			COREBIZ_LOG.INFO("order relation already exists!");

		} else {
			String provinceCode = "";// this.daoUtil.getSmsSenderDao().getAreaCodeByUserPhone(userPhone);
			String cityCode = "";// this.daoUtil.getSmsSenderDao().getCityCodeByUserPhone(userPhone);
			// this.daoUtil.getSmsSenderDao().AddOrderRelation(userPhone,
			// chargeparty, service, provinceCode, cityCode, channel, spOrderId,
			// notifySpFlag, String.valueOf(period));
			HttpRequestHelper.saveOrderMessage(userPhone,spId,productInfoId,fee,notifySpFlag);
		}
	}

	public void updateOrder(OrderRelationUpdateNotifyResponse response) {
		if (response.getResultCode() == 0) {
			COREBIZ_LOG.INFO("=====>order relation notify sp successfully!");
			OrderRelationRequest orderRelationRequest = ((OrderRelationMessage) this.dataMap
					.get(response.getGlobalMessageid())).getOrderRelationRequest();
			String uniqueid = HttpRequestHelper.getServiceIDbyProductid(orderRelationRequest.getProductID());// this.daoUtil.getSmsSenderDao().getUniqueid(orderRelationRequest.getProductID());
			// this.daoUtil.getSmsDAO().updateOrderRelation(orderRelationRequest.getUserPhone(),uniqueid);
			COREBIZ_LOG.INFO("update userPhone" + orderRelationRequest.getUserPhone() + " serviceuniqueid:" + uniqueid);
		}
	}

	public void setMessageRouter(IMessageRouter messageRouter) {
		this.messageRouter = messageRouter;
	}

	// public void setDaoUtil(DaoUtil daoUtil) {
	// this.daoUtil = daoUtil;
	// }

	public void setDataCache(DataCache dataCache) {
		this.dataCache = dataCache;
	}

	public void setDataMap(ConcurrentHashMap<String, AbstractMessage> dataMap) {
		this.dataMap = dataMap;
	}

	public ConcurrentHashMap<String, AbstractMessage> getCommonMap() {
		return this.commonMap;
	}

	public void setCommonMap(ConcurrentHashMap<String, AbstractMessage> commonMap) {
		this.commonMap = commonMap;
	}
}

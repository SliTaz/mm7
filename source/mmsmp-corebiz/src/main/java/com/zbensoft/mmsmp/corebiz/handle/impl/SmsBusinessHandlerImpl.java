package com.zbensoft.mmsmp.corebiz.handle.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
import com.zbensoft.mmsmp.corebiz.cache.DataCache;
import com.zbensoft.mmsmp.corebiz.dao.DaoUtil;
import com.zbensoft.mmsmp.corebiz.handle.impl.sms.EnumOptype;
import com.zbensoft.mmsmp.corebiz.handle.impl.sms.OperatorType;
import com.zbensoft.mmsmp.corebiz.handle.impl.sms.SmsSenderDto;
import com.zbensoft.mmsmp.corebiz.handle.impl.sms.Utility;
import com.zbensoft.mmsmp.corebiz.route.IMessageRouter;
import com.zbensoft.mmsmp.corebiz.util.HttpRequestHelper;

/**
 *  短消息处理类
 * @author mrcra
 *
 */
public class SmsBusinessHandlerImpl {
	static final Logger logger = Logger.getLogger(SmsBusinessHandlerImpl.class);

	IMessageRouter messageRouter;

//	DaoUtil daoUtil;

	DataCache dataCache;//系统变量 缓存

	ConcurrentHashMap<String, AbstractMessage> dataMap;

	private static final String CANCEL_ALL_COMMAND = "00000";

	private static final String CANCEL_COMMAND = "0000";

	private static final String CONFIRM_COMMAND = "Y";

	private static final int ERR = -1;
	private static final int FAIL = 1;
	private static final int SUCCESS = 0;

	public void doHandler(AbstractMessage msg) {
		if ((msg instanceof MO_SMMessage)) {
			MO_SMMessage mosms = (MO_SMMessage) msg;
			mosms.setGlobalMessageTime(System.currentTimeMillis());

			String SERVICE_NUMBER = this.dataCache.getSysParams("ACC_NUMBER");
			String SMS_MO_MAPREDUCE = this.dataCache.getSysParams("SMS_MO_MAPREDUCE", "OFF");

			String phoneNumber = mosms.getSendAddress();
			String spNumber = mosms.getVasId();
			String smstext = mosms.getSmsText().toLowerCase().trim();

			logger.info("smsmo receive one message [messageid:" + mosms.getGlobalMessageid() + ",usernumber:"
					+ mosms.getSendAddress() + ",SMS_MO_MAPREDUCE:" + SMS_MO_MAPREDUCE + "]");

			if (SMS_MO_MAPREDUCE.equals("ON")) {
				if (("0000".equals(mosms.getSmsText())) && (spNumber.length() > SERVICE_NUMBER.length())) {
					//TODO
					SmsSenderDto dto = new SmsSenderDto();//this.daoUtil.getSmsDAO().getDto(spNumber);

					if (dto.getProducts().size() == 0) {
						doHandleBranch(msg);
						return;
					}

					for (SmsSenderDto product : dto.getProducts()) {
						MO_SMMessage subMo = new MO_SMMessage();
						subMo.setGlobalCreateTime(mosms.getGlobalCreateTime());
						subMo.setGlobalMessageTime(mosms.getGlobalMessageTime());
						subMo.setVasId(mosms.getVasId());
						subMo.setGlobalMessageid(mosms.getGlobalMessageid() + product.getSp_productid());
						subMo.setSendAddress(mosms.getSendAddress());
						subMo.setSmsText(product.getCancelordercode());
						logger.info("smsmo split 0000 message [parentmessageid:" + mosms.getGlobalMessageid()
								+ "messageid:" + subMo.getGlobalMessageid() + ",usernumber:" + mosms.getSendAddress()
								+ ",parent smstext:" + smstext + ", smstext:" + subMo.getSmsText() + ",vasid:"
								+ subMo.getVasId() + "]");

						doHandleBranch(subMo);
					}

				} else if ("00000".equals(mosms.getSmsText())) {//退订?
					//TODO
					List<String[]> productcmds = new ArrayList<>();//this.daoUtil.getSmsDAO().getProduct00000(phoneNumber);

					if ((productcmds == null) || (productcmds.size() == 0)) {

						sendMTMsgToQuence("不存在订购关系，无需退订！", mosms.getSendAddress());
						return;
					}

					for (String[] productcmd : productcmds) {

						MO_SMMessage subMo = new MO_SMMessage();
						subMo.setGlobalCreateTime(mosms.getGlobalCreateTime());
						subMo.setGlobalMessageTime(mosms.getGlobalMessageTime());
						subMo.setVasId(productcmd[2]);
						subMo.setGlobalMessageid(mosms.getGlobalMessageid() + productcmd[0]);
						subMo.setSendAddress(mosms.getSendAddress());
						subMo.setSmsText(productcmd[1]);

						logger.info("smsmo split 00000 message [parentmessageid:" + mosms.getGlobalMessageid()
								+ "messageid:" + subMo.getGlobalMessageid() + ",usernumber:" + mosms.getSendAddress()
								+ ",parent smstext:" + smstext + ", smstext:" + subMo.getSmsText() + ",vasid:"
								+ subMo.getVasId() + "]");

						doHandleBranch(subMo);
					}

				} else {
					doHandleBranch(msg);
				}

			} else {
				doHandleBranch(msg);
			}

		} else {
			doHandleBranch(msg);
		}
	}

	public void doHandleBranch(AbstractMessage msg) {
		try {
			long start = System.currentTimeMillis();

			if ((msg instanceof MO_SMMessage)) {

				MO_SMMessage mosms = (MO_SMMessage) msg;
				mosms.setGlobalMessageTime(System.currentTimeMillis());

				OperatorType optype = getOperatorType(mosms);

				if ((optype.getType() != EnumOptype.OTHER) && (optype.getSpid() != null)) {
					logger.info("smsmo vas service match result success optype:" + optype.getType() + " [gmessageid:"
							+ mosms.getGlobalMessageid() + ",spid:" + optype.getSpid() + ",sp_product_id:"
							+ optype.getProduct_id() + ",servicename:" + optype.getServicename() + ",uniqueid:"
							+ optype.getUniqueid() + ",fee:" + optype.getFee() + "]");
					mosms.setVaspId(optype.getSpid());

					mosms.setServiceCode(optype.getSpid() + "#" + optype.getService_id() + "#" + optype.getProduct_id()
							+ "#" + optype.getServicename() + "#" + optype.getFee() + "#" + optype.getUniqueid() + "#"
							+ optype.getVaspname() + "#" + optype.getBusinessphone());
				} else {
					logger.info("smsmo vas service match result failure optype:" + optype.getType() + "[gmessageid:"
							+ mosms.getGlobalMessageid() + ",spid:" + optype.getSpid() + ",sp_product_id:"
							+ optype.getProduct_id() + ",servicename:" + optype.getServicename() + ",uniqueid:"
							+ optype.getUniqueid() + "fee:" + optype.getFee() + "]");
				}
				boolean inWhiteList = checkIsWhiteList(mosms,optype);//查询是否在白名单
				this.dataMap.put(mosms.getGlobalMessageid(), mosms);

				switch (optype.getType()) {//业务分类处理
				
				case TAKE_NUMBER:
					logger.info("smsmo take number bussiness message route to ownbiz queue");
					this.messageRouter.doRouter(mosms);
					return;

				case OTHER:
					String _msg = this.dataCache.getSysParams("ACC_OR_CODE_ERROR");
					sendMTMsgToQuence(_msg, mosms.getSendAddress());
					return;

				case DIANBO:
					if (!inWhiteList) {
						logger.info("smsmo demand vac auth(4) request[gmessageid:" + mosms.getGlobalMessageid()
								+ "userphone:" + mosms.getSendAddress() + " product_id:" + optype.getProduct_id()
								+ " spid :" + optype.getSpid() + "]");

						CheckRequest dianborequest = new CheckRequest(mosms.getSendAddress(), optype.getProduct_id(),
								optype.getSpid(), null, null, "4", null);
						dianborequest.setGlobalMessageid(mosms.getGlobalMessageid());
						this.messageRouter.doRouter(dianborequest);
					} else {//白名单，直接下发点拨资费
						logger.info("smsmo demand white user [gmessageid:" + mosms.getGlobalMessageid() + "userphone:"
								+ mosms.getSendAddress() + " product_id:" + optype.getProduct_id() + " spid :"
								+ optype.getSpid() + "]");

						String linkid = getRandomLinkid();
						mosms.setLinkId(linkid);
						dealDianbo(mosms);
					}
					break;

				case ORDER:
					if (!inWhiteList) {
						logger.info("smsmo vac auth(7) request[gmessageid:" + mosms.getGlobalMessageid() + "userphone:"
								+ mosms.getSendAddress() + " product_id:" + optype.getProduct_id() + " spid :"
								+ optype.getSpid() + "]");

						CheckRequest orderrequest = new CheckRequest(mosms.getSendAddress(), optype.getProduct_id(),
								optype.getSpid(), null, null, "7", null);
						orderrequest.setGlobalMessageid(mosms.getGlobalMessageid());
						this.messageRouter.doRouter(orderrequest);
					} else {
						logger.info("smsmo order white user [gmessageid:" + mosms.getGlobalMessageid() + "userphone:"
								+ mosms.getSendAddress() + " product_id:" + optype.getProduct_id() + " spid :"
								+ optype.getSpid() + "]");

						dealOrder(mosms.getGlobalMessageid());
					}

					break;

				case CONFIRM:
					if (!inWhiteList) {
						logger.info("smsmo order vac auth(1) confrimrequest[gmessageid:" + mosms.getGlobalMessageid()
								+ "userphone:" + mosms.getSendAddress() + " product_id:" + optype.getProduct_id()
								+ " spid :" + optype.getSpid() + "]");

						CheckRequest confirmrequest = new CheckRequest(mosms.getSendAddress(), optype.getProduct_id(),
								optype.getSpid(), null, null, "1", null);
						confirmrequest.setGlobalMessageid(mosms.getGlobalMessageid());
						this.messageRouter.doRouter(confirmrequest);
					} else {
						logger.info("smsmo order white user confrim[gmessageid:" + mosms.getGlobalMessageid()
								+ "userphone:" + mosms.getSendAddress() + " product_id:" + optype.getProduct_id()
								+ " spid :" + optype.getSpid() + "]");

						dealConfirm(mosms.getGlobalMessageid());
					}

					break;

				case CANCEL:
					if (!inWhiteList) {
						logger.info("smsmo order vac auth(2) cancel  normal request[gmessageid:"
								+ mosms.getGlobalMessageid() + "userphone:" + mosms.getSendAddress() + " product_id:"
								+ optype.getProduct_id() + " spid :" + optype.getSpid() + "]");

						CheckRequest cancelrequest = new CheckRequest(mosms.getSendAddress(), optype.getProduct_id(),
								optype.getSpid(), null, null, "2", null);
						cancelrequest.setGlobalMessageid(mosms.getGlobalMessageid());
						this.messageRouter.doRouter(cancelrequest);
					} else {
						logger.info("smsmo order white user cancel normal [gmessageid:" + mosms.getGlobalMessageid()
								+ "userphone:" + mosms.getSendAddress() + " product_id:" + optype.getProduct_id()
								+ " spid :" + optype.getSpid() + "]");

						dealCancelOrder(mosms.getGlobalMessageid());
					}

					break;

				case CANCEL_SERVICE:
					if (!inWhiteList) {
						logger.info("smsmo order vac auth(2) cancel 0000 request[gmessageid:"
								+ mosms.getGlobalMessageid() + "userphone:" + mosms.getSendAddress() + " serviceid:"
								+ optype.getService_id() + " spid :" + optype.getSpid() + "]");

						CheckRequest cancelrequest = new CheckRequest(mosms.getSendAddress(), null, optype.getSpid(),
								optype.getService_id(), null, "2", null);
						cancelrequest.setGlobalMessageid(mosms.getGlobalMessageid());
						this.messageRouter.doRouter(cancelrequest);
					} else {
						logger.info("smsmo order white user cancel 0000[gmessageid:" + mosms.getGlobalMessageid()
								+ "userphone:" + mosms.getSendAddress() + " serviceid:" + optype.getService_id()
								+ " spid :" + optype.getSpid() + "]");

						dealCancelOrderService(mosms.getGlobalMessageid());
					}

					break;

				case CANCEL_ALL:
					if (!inWhiteList) {
						logger.info("smsmo order vac auth(3) cancel 00000 request[gmessageid:"
								+ mosms.getGlobalMessageid() + "userphone:" + mosms.getSendAddress() + " product_ids:"
								+ optype.getProduct_id() + " spid :" + optype.getSpid() + "]");

						CheckRequest cancelrequest = new CheckRequest(mosms.getSendAddress(), optype.getProduct_id(),
								optype.getSpid(), null, null, "3", null);
						cancelrequest.setGlobalMessageid(mosms.getGlobalMessageid());
						this.messageRouter.doRouter(cancelrequest);

					} else {
						logger.info("smsmo order white user cancel 00000 [gmessageid:" + mosms.getGlobalMessageid()
								+ "userphone:" + mosms.getSendAddress() + " product_ids:" + optype.getProduct_id()
								+ " spid :" + optype.getSpid() + "]");

						dealCancelOrderAll(mosms.getGlobalMessageid());
					}

					break;

				default:
					logger.info("smsmo command type match failure [gmessageid:" + mosms.getGlobalMessageid()
							+ "userphone:" + mosms.getSendAddress() + " product_id:" + optype.getProduct_id()
							+ " spid :" + optype.getSpid() + "]");

					String error_msg = this.dataCache.getSysParams("MO_ERROR_MESSAGE");
					sendMTMsgToQuence(error_msg, mosms.getSendAddress());
					break;
				}

			} else if ((msg instanceof CheckResponse)) {

				CheckResponse cresponse = (CheckResponse) msg;
				CheckRequest crequest = cresponse.getCRequest();

				String gmsgid = cresponse.getGlobalMessageid();

				if ((gmsgid == null) || (!this.dataMap.containsKey(gmsgid))) {
					logger.info("sms vac auth(" + cresponse.getCRequest().getServiceType()
							+ ") response match failure[gmsgid:" + gmsgid + "]");
					return;
				}

				int returncode = Integer.parseInt(cresponse.getResult_Code());

				MO_SMMessage mosms = (MO_SMMessage) this.dataMap.get(cresponse.getGlobalMessageid());

				if ("4".equals(crequest.getServiceType())) {

					if (returncode == 0) {
						String linkid = cresponse.getLinkID();

						if ((linkid != null) && (!"".equals(linkid))) {

							logger.info("sms vac auth(4) response success[gmsid:" + mosms.getGlobalMessageid()
									+ ",userphone: " + mosms.getSendAddress() + ", linkid:" + cresponse.getLinkID()
									+ ",returncode:" + returncode + ",ChargetNumber:" + crequest.getChargetNumber()
									+ "]");

							mosms.setLinkId(linkid);
							dealDianbo(mosms);

						} else {
							logger.info("sms vac auth(4) response check(1003) failure[gmsid:"
									+ mosms.getGlobalMessageid() + ",userphone: " + mosms.getSendAddress() + ", linkid:"
									+ cresponse.getLinkID() + ",returncode:" + returncode + ",ChargetNumber:"
									+ crequest.getChargetNumber() + "]");
							sendMTMsgToQuence(getClientMessage("1003"), mosms.getSendAddress());

							this.dataMap.remove(cresponse.getGlobalMessageid());
						}

					} else {
						logger.info("sms vac auth(4) response check returncode(~0) failure[gmsid:"
								+ mosms.getGlobalMessageid() + ",userphone: " + mosms.getSendAddress() + ", linkid:"
								+ cresponse.getLinkID() + ",returncode:" + returncode + ",ChargetNumber:"
								+ crequest.getChargetNumber() + "]");

						String[] values = mosms.getServiceCode().split("#");
						String clientMsg = this.dataCache.getSysParams("COREBIZ_SEND_CLIENT_MESSAGE");
						clientMsg = clientMsg.replace("{0}", getClientMessage(cresponse.getServerResult_code()));
						clientMsg = clientMsg.replace("{1}", values[3]);
						//TODO 获取CP Info;finish
						SmsSenderDto dto = HttpRequestHelper.getVasSpCpInfo(mosms.getVasId());//this.daoUtil.getSmsDAO().getCPInfo(mosms.getVasId());
						clientMsg = clientMsg.replace("{3}", dto.getVaspname());
						clientMsg = clientMsg.replace("{4}",
								dto.getBusinessphone() == null ? "" : dto.getBusinessphone());
						sendMTMsgToQuence(clientMsg, mosms.getSendAddress());

						this.dataMap.remove(cresponse.getGlobalMessageid());

					}

				} else if ("7".equals(crequest.getServiceType())) {

					if (returncode == 0) {
						logger.info("sms vac auth(7) response check orderrelation already exist[gmsid:"
								+ mosms.getGlobalMessageid() + ",userphone: " + mosms.getSendAddress() + ",returncode:"
								+ returncode + ",ChargetNumber:" + crequest.getChargetNumber() + "]");

						String _msg = this.dataCache.getSysParams("ERROR_MSG") + "您已经定购，无需重复定购！";
						sendMTMsgToQuence(_msg, mosms.getSendAddress());
						this.dataMap.remove(cresponse.getGlobalMessageid());

					} else if (returncode == 1) {
						dealOrder(mosms.getGlobalMessageid());

					} else {

						logger.info("sms vac auth(7) response check returncode(~0) failure[gmsid:"
								+ mosms.getGlobalMessageid() + ",userphone: " + mosms.getSendAddress() + ",returncode:"
								+ returncode + ",ChargetNumber:" + crequest.getChargetNumber() + "]");

						sendMTMsgToQuence("order error", mosms.getSendAddress());
						this.dataMap.remove(cresponse.getGlobalMessageid());

					}

				} else if ("1".equals(crequest.getServiceType())) {

					if (returncode == 0) {
						logger.info("sms vac auth(1) response check returncode(0) sucess[gmsid:"
								+ mosms.getGlobalMessageid() + ",userphone: " + mosms.getSendAddress() + ",returncode:"
								+ returncode + ",ChargetNumber:" + crequest.getChargetNumber() + "]");

						dealConfirm(mosms.getGlobalMessageid());

					} else {
						logger.info("sms vac auth(1) response check returncode(~0) failure[gmsid:"
								+ mosms.getGlobalMessageid() + ",userphone: " + mosms.getSendAddress() + ",returncode:"
								+ returncode + ",ChargetNumber:" + crequest.getChargetNumber() + "]");

						sendMTMsgToQuence(getClientMessage(cresponse.getServerResult_code()), mosms.getSendAddress());

					}

				} else if ("2".equals(crequest.getServiceType())) {
					if (returncode == 0) {
						if (crequest.getSp_product_id() != null) {
							logger.info("sms vac auth(2) response check normal returncode(0) sucess[gmsid:"
									+ mosms.getGlobalMessageid() + ",userphone: " + mosms.getSendAddress()
									+ ",returncode:" + returncode + ",ChargetNumber:" + crequest.getChargetNumber()
									+ "]");

							dealCancelOrder(mosms.getGlobalMessageid());
						} else if (crequest.getService_id() != null) {
							logger.info("sms vac auth(2) response check 0000 returncode(0) sucess[gmsid:"
									+ mosms.getGlobalMessageid() + ",userphone: " + mosms.getSendAddress()
									+ ",returncode:" + returncode + ",ChargetNumber:" + crequest.getChargetNumber()
									+ "]");

							dealCancelOrderService(mosms.getGlobalMessageid());
						}

					} else {

						sendMTMsgToQuence(getClientMessage(cresponse.getServerResult_code()), mosms.getSendAddress());
					}

				} else if ("3".equals(crequest.getServiceType())) {
					if (returncode == 0) {
						logger.info("sms vac auth(3) response check 00000 returncode(0) sucess[gmsid:"
								+ mosms.getGlobalMessageid() + ",userphone: " + mosms.getSendAddress() + ",returncode:"
								+ returncode + ",ChargetNumber:" + crequest.getChargetNumber() + "]");

						dealCancelOrderAll(mosms.getGlobalMessageid());

					} else {
						logger.info("sms vac auth(3) response check 00000 returncode(~0) failure[gmsid:"
								+ mosms.getGlobalMessageid() + ",userphone: " + mosms.getSendAddress() + ",returncode:"
								+ returncode + ",ChargetNumber:" + crequest.getChargetNumber() + "]");

						sendMTMsgToQuence(getClientMessage(cresponse.getServerResult_code()), mosms.getSendAddress());
					}

				} else {
					logger.info("the CheckResponse message is error,[messageid:" + mosms.getGlobalMessageid() + "]");

					this.dataMap.remove(gmsgid);
				}

			} else if ((msg instanceof OrderRelationUpdateNotifyResponse)) {
				try {
					OrderRelationUpdateNotifyResponse notifyResponse = (OrderRelationUpdateNotifyResponse) msg;
					int returncode = notifyResponse.getResultCode();

					logger.info("sp return code :" + returncode + "   updatetype:"
							+ notifyResponse.getOrderRequest().getUpdateType());

					if ((returncode == 0) && ("1".equals(notifyResponse.getOrderRequest().getUpdateType()))) {
						MO_SMMessage mosms = (MO_SMMessage) this.dataMap.remove(notifyResponse.getGlobalMessageid());
						//TODO 更新用户订单信息,edit
						HttpRequestHelper.updateOrderMessage(mosms);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return;
		} catch (Exception e) {
			logger.error("上行短信的核心处理方法错误：" + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private boolean checkIsWhiteList(MO_SMMessage mosms,OperatorType optype) {
		boolean inWhiteList=true;
		if (("0000".equals(mosms.getSmsText())) || ("00000".equals(mosms.getSmsText()))) {

			if (optype.getProduct_id() != null) {
				String[] productids = optype.getProduct_id().split(",");
				for (int i = 0; i < productids.length; i++) {
					inWhiteList = this.dataCache.isWhiteUser(mosms.getSendAddress(), productids[i],
							mosms.getVasId());
					if (!inWhiteList) {
						break;
					}

				}

			}
		} else {
			inWhiteList = this.dataCache.isWhiteUser(mosms.getSendAddress(), optype.getProduct_id(),
					mosms.getVasId());
		}

		logger.info("smsmo check phone is white user " + inWhiteList + " [gmessageid:"
				+ mosms.getGlobalMessageid() + "userphone:" + mosms.getSendAddress() + "]");
		return inWhiteList;
	}

	String[] str = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	private String getRandomLinkid() {
		String randomLinkid = "";
		for (int i = 0; i < 6; i++) {
			int index = new Random().nextInt(10);
			randomLinkid = randomLinkid + this.str[index];
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		randomLinkid = randomLinkid + sdf.format(new Date());
		return randomLinkid;
	}

	private void dealCancelOrderAll(String messageid) {
		MO_SMMessage mosms = (MO_SMMessage) this.dataMap.remove(messageid);
		if (mosms == null) {
			logger.info("smsmo get cancel 00000 message from datamap is null,messageid:" + messageid);
			return;
		}

		String _msg = "退订所有成功！";
		sendMTMsgToQuence(_msg, mosms.getSendAddress());

		OrderRelationUpdateNotifyRequest notifyRequest = new OrderRelationUpdateNotifyRequest();
		notifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
		notifyRequest.setContent(mosms.getSmsText());
		notifyRequest.setEffectiveDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		notifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
		notifyRequest.setExpireDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		notifyRequest.setLinkId("");
		notifyRequest.setServiceType("31");
		notifyRequest.setTime_stamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		notifyRequest.setUpdateDesc("");
		notifyRequest.setUpdateTime("");
		notifyRequest.setUpdateType(Integer.valueOf(2));
		notifyRequest.setUserId(mosms.getSendAddress());
		notifyRequest.setUserIdType(Integer.valueOf(1));

		try {
			//TODO 更具手机号码查询所有业务;Already processed;is ok
			List<Vasservice> vasList = new ArrayList<>();
			Vasservice vasservice=new Vasservice();
			vasservice.setVaspid("vaspid0013010012345");
			vasservice.setServicecode("servicecode12333");
			vasservice.setVasid("223dd");
//			vasList.add(vasservice);
			
			vasList=HttpRequestHelper.getNormalStatus();//this.daoUtil.getSmsDAO().getVasservice(mosms.getSendAddress());

			if (vasList != null) {
				for (int i = 0; i < vasList.size(); i++) {
					Vasservice vas = (Vasservice) vasList.get(i);
					
					String notifyUrl = this.dataCache.getSpurlByVaspid((vas.getVaspid()));//this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(vas.getVaspid());
					notifyRequest.setNotifySPURL(notifyUrl);
					notifyRequest.setProductId(vas.getServicecode());
					notifyRequest.setSpId(vas.getVaspid());
					notifyRequest.setGlobalMessageid(mosms.getGlobalMessageid() + i);

					this.messageRouter.doRouter(notifyRequest);

					logger.info("smsmo notify sp orderrelation cancel 00000 request message [gmessage:" + messageid
							+ ",userphone:" + mosms.getSendAddress() + ",product: " + vas.getServicecode()
							+ ",notifyurl:" + notifyUrl + "]");

				}

			} else {
				logger.info("smsmo notify sp orderrelation cancel 00000 match failure [gmessage:" + messageid
						+ ",userphone:" + mosms.getSendAddress() + "]");
			}
		} catch (Exception e) {
			logger.info("cancel_all notify sp error!!!!", e);
		}

		cancelOrderRaltion(mosms, 3);
	}

	private void dealCancelOrderService(String messageid) {
		MO_SMMessage mosms = (MO_SMMessage) this.dataMap.remove(messageid);
		if (mosms == null) {
			logger.info("smsmo get cancel 0000 message from datamap is null,messageid:" + messageid);
			return;
		}

		try {
			//TODO 通过接入号获取spId和sp名称;Already processed;is ok
			String[] spidandvasname = {"vaspid0013010012345","testvsp"};
			spidandvasname=HttpRequestHelper.getServiceNameByAcc(mosms.getVasId());//this.daoUtil.getSmsDAO().getServiceNameByAcc(mosms.getVasId());

			String _msg = "退订成功！";
			sendMTMsgToQuence(_msg, mosms.getSendAddress());
			//TODO 通过spID 获取通知url;Already processed;is ok
			String notifyUrl = this.dataCache.getSpurlByVaspid(spidandvasname[0]);// this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(spidandvasname[0]);

			OrderRelationUpdateNotifyRequest notifyRequest = new OrderRelationUpdateNotifyRequest();
			notifyRequest.setNotifySPURL(notifyUrl);
			notifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
			notifyRequest.setContent(mosms.getSmsText());
			notifyRequest.setEffectiveDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			notifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
			notifyRequest.setExpireDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			notifyRequest.setLinkId("");
			notifyRequest.setServiceType("31");
			notifyRequest.setSpId(spidandvasname[0]);
			notifyRequest.setTime_stamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			notifyRequest.setUpdateDesc("");
			notifyRequest.setUpdateTime("");
			notifyRequest.setUpdateType(Integer.valueOf(2));
			notifyRequest.setUserId(mosms.getSendAddress());
			notifyRequest.setUserIdType(Integer.valueOf(1));
			//TODO 通过接入号查询sp_productid;Already processed;is ok
			List<String> sp_productidList = new ArrayList<>();
//			sp_productidList.add("testsp_productid_001");
			sp_productidList=HttpRequestHelper.getProductId(mosms.getVasId());//this.daoUtil.getSmsDAO().getProductId(mosms.getVasId());
			
			String products = null;
			if (sp_productidList != null) {
				for (int i = 0; i < sp_productidList.size(); i++) {
					notifyRequest.setProductId((String) sp_productidList.get(i));
					notifyRequest.setGlobalMessageid(mosms.getGlobalMessageid() + i);
					this.messageRouter.doRouter(notifyRequest);
					products = products + (String) sp_productidList.get(i);
				}

				logger.info("smsmo notify sp orderrelation cancel 0000 request message [gmessage:" + messageid
						+ ",userphone:" + mosms.getSendAddress() + ",products: " + products + ",notifyurl:" + notifyUrl
						+ "]");

			} else {
				logger.info("smsmo notify sp orderrelation cancel 0000 match product failure [gmessage:" + messageid
						+ ",userphone:" + mosms.getSendAddress() + ",notifyurl:" + notifyUrl + "]");
			}
		} catch (Exception e) {
			logger.error("cancel_service notify sp error!!!!", e);
		}

		cancelOrderRaltion(mosms, 2);
	}

	private void dealCancelOrder(String messageid)
   {
     MO_SMMessage mosms = (MO_SMMessage)this.dataMap.remove(messageid);
     if (mosms == null)
     {
       logger.info("smsmo get cancel normal message from datamap is null,messageid:" + messageid);
       return;
     }
     
 
     String[] values = mosms.getServiceCode().split("#");
     //TODO 获取用户信息
     UserOrder userOrder = new UserOrder();//this.daoUtil.getSmsSenderDao().getOrderRelation(mosms.getSendAddress(), values[5]);
     if (userOrder == null)
     {
       logger.info("smsmo cancel orderrelation not exist[gmessage:" + mosms.getGlobalMessageid() + ",userphone:" + mosms.getSendAddress() + ",serviceuniqid:" + values[5] + "]");
       
       return;
     }
     
 
     String _msg = this.dataCache.getSysParams("COREBIZ_SEND_CANCEL_MESSAGE");
     _msg = _msg.replace("{0}", values[3]);
     _msg = _msg.replace("{1}", String.valueOf(Double.parseDouble(values[4]) / 100.0D));
     
 
     //TODO 查询CpInfo;finish
     SmsSenderDto dto = HttpRequestHelper.getVasSpCpInfo(mosms.getVasId());//this.daoUtil.getSmsDAO().getCPInfo(mosms.getVasId());
     _msg = _msg.replace("{2}", dto.getVaspname());
     _msg = _msg.replace("{3}", dto.getBusinessphone() == null ? "" : dto.getBusinessphone());
     
     sendMTMsgToQuence(_msg, mosms.getSendAddress());
     
     //TODO 获取通知url;processing; is ok
     String notifyUrl = HttpRequestHelper.getSpReportUrlByServiceCode(values[0]);//this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(values[0]);
     logger.info("smsmo notify sp orderrelation cancel normal request message [gmessage:" + messageid + ",userphone:" + mosms.getSendAddress() + ",notifyurl:" + notifyUrl + "]");
     
 
 
     OrderRelationUpdateNotifyRequest notifyRequest = new OrderRelationUpdateNotifyRequest();
     notifyRequest.setNotifySPURL(notifyUrl);
     notifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
     notifyRequest.setContent(mosms.getSmsText());
     notifyRequest.setEffectiveDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
     notifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
     notifyRequest.setExpireDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
     notifyRequest.setLinkId("");
     notifyRequest.setServiceType("31");
     notifyRequest.setProductId(values[2]);
     notifyRequest.setSpId(values[0]);
     notifyRequest.setTime_stamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
     notifyRequest.setUpdateDesc("");
     notifyRequest.setUpdateTime("");
     notifyRequest.setUpdateType(Integer.valueOf(2));
     notifyRequest.setUserId(mosms.getSendAddress());
     notifyRequest.setUserIdType(Integer.valueOf(1));
     
     notifyRequest.setGlobalMessageid(mosms.getGlobalMessageid());
     
     this.messageRouter.doRouter(notifyRequest);
     
 
     cancelOrderRaltion(mosms, 1);
   }

	private void cancelOrderRaltion(MO_SMMessage mosms, int type) {
		if (type == 1) {
			String[] values = mosms.getServiceCode().split("#");
			//TODO 获取服务;Already processed;is ok
			Vasservice service = null;
//			service=this.daoUtil.getSmsSenderDao().getVASServiceByServiceCode(values[2]);
			
			List<Vasservice> servList=HttpRequestHelper.getNormalStatus();
			if(servList!=null&&servList.size()>0){
				for(int i=0;i<servList.size();i++){
					try {
						if(servList.get(i).getVasserviceReserveInfo().getSpProductid().equals(values[2])){
							service=servList.get(i);
							break;
						}
					} catch (Exception e) {
						
					}
				}
			}

			if (service == null) {
				logger.info("smsmo cancel product not exist[gmessage:" + mosms.getGlobalMessageid() + ",userphone:"
						+ mosms.getSendAddress() + "]");
			}
			//TODO 查询订购关系;Already processed;is ok
			UserOrder userOrder = null;
			userOrder=HttpRequestHelper.getOrderRelation(mosms.getSendAddress(), values[5]);//this.daoUtil.getSmsSenderDao().getOrderRelation(mosms.getSendAddress(), values[5]);
			if (userOrder == null) {
				logger.info("smsmo cancel orderrelation not exist[gmessage:" + mosms.getGlobalMessageid()
						+ ",userphone:" + mosms.getSendAddress() + "]");
			} else {
				//TODO 删除订购关系;Already processed;is ok
//				this.daoUtil.getSmsSenderDao().delOrderRelation(mosms.getSendAddress(), values[5]);
				HttpRequestHelper.delOrderRelation(mosms.getSendAddress(), values[5]);
			}

		} else if (type == 2) {
			
			
			String spProductId=mosms.getServiceCode().split("#")[2];
			String productInfoId = HttpRequestHelper.getServuniqueIdbySpproductid(spProductId);
			
			//TODO 退订删除;Already processed;is ok
//			this.daoUtil.getSmsDAO().delServiceOrderRelation(mosms.getSendAddress(), mosms.getVasId());
			HttpRequestHelper.delServiceOrderRelation(mosms.getSendAddress(), productInfoId);

		} else if (type == 3) {
			//TODO 退订删除;Already processed;edit
//			this.daoUtil.getSmsDAO().delAllOrderRelation(mosms.getSendAddress());
			HttpRequestHelper.delAllOrderRelation(mosms.getSendAddress());
		}
	}

	/**
	 * 点拨 下发短信资费 ；点拨消息通知
	 * @param mosms
	 */
	private void dealDianbo(MO_SMMessage mosms) {
		String messageid = mosms.getGlobalMessageid();

		this.dataMap.remove(messageid);

		String[] values = mosms.getServiceCode().split("#");

		String _msg = this.dataCache.getSysParams("UP_ONDEMAND_TIP");
//		_msg="{0}make{1}test{2}in{3}line";//TODO 测试数据，待删除;Already processed;is ok
		_msg = _msg.replace("{0}", values[3]);
		_msg = _msg.replace("{1}", String.valueOf(Double.parseDouble(values[4]) / 100.0D));//资费

//		SmsSenderDto dto = this.dataCache.getSmsSenderDtoMap(mosms.getVasId());
//		if (dto != null) {
//			_msg = _msg.replace("{2}", dto.getVaspname());
//			_msg = _msg.replace("{3}", dto.getBusinessphone() == null ? "" : dto.getBusinessphone());
//		}
//		mosms.setSendAddress("15895861272");
//		mosms.setSequence_Number_1(111);
//		mosms.setSequence_Number_2(222);
//		mosms.setSequence_Number_3(333);
		String send_addr = mosms.getSendAddress();
		sendMTMsgToQuence(_msg, send_addr);

		logger.info("smsmo send demand success smsmt message [gmessage:" + messageid + ",userphone:" + send_addr
				+ ",servicecode:" + mosms.getServiceCode() + "]");
		
		//TODO 测试数据，待删除;Already processed;edit
//		String provinceCode ="025091"; 
//		provinceCode=this.daoUtil.getSmsSenderDao().getAreaCodeByUserPhone(send_addr);
//		provinceCode = HttpRequestHelper.getAreaCodeByUserPhone(send_addr);
		
		//TODO 测试数据，待删除;Already processed;edit
//		String cityCode = "0250911";
//		cityCode=this.daoUtil.getSmsSenderDao().getCityCodeByUserPhone(send_addr);
		
		//TODO 入库待处理;Already processed;edit
//		this.daoUtil.getSmsDAO().saveDemandMessage(mosms, provinceCode, cityCode);
		HttpRequestHelper.saveDemandMessage(mosms);
		
		//TODO 获取ip和port;待处理;Already processed;edit
		String url = "192.168.1.116:39095";
//		url=this.daoUtil.getSmsSenderDao().getSmsSenderUrl(values[1]);
//		url=HttpRequestHelper.getSmsSenderUrl(values[1]);//
		url=HttpRequestHelper.getSmsSenderUrl(values[0]);//由于values的第二个属性为null,为产品描述remark;故用第一个字段，是vaspid的值。

		if ((url != null) && (values[0] != null)) {
			mosms.setNotirySPURL(url);
			this.messageRouter.doRouter(mosms);// 通知SP进行点播

			logger.info("smsmo notify sp demand mosms message [gmessage:" + messageid + ",userphone:" + send_addr
					+ ",notifyurl:" + url + ", linkid :" + mosms.getLinkId() + ",serviceid:" + values[1] + ",vaspid:"
					+ mosms.getVaspId() + "]");

		} else {
			logger.info("smsmo notify sp demand url match failure[gmessage:" + messageid + ",userphone:" + send_addr
					+ ",notifyurl:" + url + ", linkid :" + mosms.getLinkId() + ",serviceid:" + values[1] + ",vaspid:"
					+ mosms.getVaspId() + "]");
		}
	}

	private void dealOrder(String messageid)
   {
     MO_SMMessage mosms = (MO_SMMessage)this.dataMap.get(messageid);
     
     if (mosms == null)
     {
       logger.info("smsmo get message from datamap is null,messageid:" + messageid);
       return;
     }
     
     String[] values = mosms.getServiceCode().split("#");
     
     //TODO 数据库查询;Already processed;is ok
//     String confirmmsg = "确认，请回复Y。";
     String confirmmsg=HttpRequestHelper.getConfirmmsgByProductid(values[2]);//this.daoUtil.getSmsDAO().getConfirmmsgByProductid(values[2]);
     logger.info("confirmmsg:"+confirmmsg);
     if ((confirmmsg == null) || (confirmmsg.trim().length() == 0)) {
    	 //TODO 改为从系统配置中取出默认回复语句;Already processed;is ok
//       confirmmsg = "111{0}222{1}";
       confirmmsg=this.dataCache.getSysParams("COREBIZ_SEND_ORDER_MESSAGE");//this.daoUtil.getSmsSenderDao().queryMsg("COREBIZ_SEND_ORDER_MESSAGE");
       confirmmsg = confirmmsg.replace("{0}", values[3]);
       confirmmsg = confirmmsg.replace("{1}", String.valueOf(Double.parseDouble(values[4]) / 100.0D));
       
 
       //TODO 改为从内存中获取CPInfo;Already processed;is ok
//       SmsSenderDto dto = new SmsSenderDto();//this.daoUtil.getSmsDAO().getCPInfo(mosms.getVasId());
//       dto.setVaspname("testVaspname");
       SmsSenderDto dto=HttpRequestHelper.getVasSpCpInfo(mosms.getVasId());
       
       confirmmsg = confirmmsg.replace("{2}", dto.getVaspname());
       confirmmsg = confirmmsg.replace("{3}", dto.getBusinessphone() == null ? "" : dto.getBusinessphone());
       
       logger.info("confirmmsg:"+confirmmsg);
     }
     
 
     String send_addr = mosms.getSendAddress();
     sendMTMsgToQuence(confirmmsg, send_addr);
     
     logger.info("smsmo send one confirm prepare sms message [gmessage:" + messageid + ",userphone:" + send_addr + "]");
   }

	private void dealConfirm(String messageid)
   {
     MO_SMMessage mosms = (MO_SMMessage)this.dataMap.get(messageid);
     
 
     if (mosms == null)
     {
       logger.info("smsmo get message from datamap is null,messageid:" + messageid);
       return;
     }
     
 
 
     String[] values = mosms.getServiceCode().split("#");
     String _msg = this.dataCache.getSysParams("COREBIZ_SEND_SECONDCONFIRM_MESSAGE");//this.daoUtil.getSmsSenderDao().queryMsg("COREBIZ_SEND_SECONDCONFIRM_MESSAGE");
     _msg = _msg.replace("{0}", values[3]);
     _msg = _msg.replace("{1}", String.valueOf(Double.parseDouble(values[4]) / 100.0D));
     _msg = _msg.replace("{2}", values.length >= 7 ? values[6] : "");
     String clientTel = values.length >= 8 ? values[7] : "";
     _msg = _msg.replace("{3}", clientTel == null ? "" : clientTel);
     
 
 
 
 
     String send_addr = mosms.getSendAddress();
     sendMTMsgToQuence(_msg, send_addr);
     
     logger.info("smsmo send confirm success sms message [gmessage:" + messageid + ",userphone:" + send_addr + ",servicecode:" + mosms.getServiceCode() + "]");
     
 
 
     String notifyUrl = this.dataCache.getSpurlByVaspid(values[0]);//this.daoUtil.getSmsDAO().getSpurlByVaspid(values[0]);
     logger.info("smsmo notify sp orderrelation request message [gmessage:" + messageid + ",userphone:" + send_addr + ",notifyurl:" + notifyUrl + "]");
     
 
     OrderRelationUpdateNotifyRequest notifyRequest = new OrderRelationUpdateNotifyRequest();
     notifyRequest.setNotifySPURL(notifyUrl);
     notifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
     notifyRequest.setContent(mosms.getSmsText());
     notifyRequest.setEffectiveDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
     notifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
     notifyRequest.setExpireDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
     notifyRequest.setLinkId("");
     notifyRequest.setServiceType("31");
     notifyRequest.setProductId(values[2]);
     notifyRequest.setSpId(values[0]);
     notifyRequest.setTime_stamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
     notifyRequest.setUpdateDesc("");
     notifyRequest.setUpdateTime("");
     notifyRequest.setUpdateType(Integer.valueOf(1));
     notifyRequest.setUserId(mosms.getSendAddress());
     notifyRequest.setUserIdType(Integer.valueOf(1));
     notifyRequest.setGlobalMessageid(mosms.getGlobalMessageid());
     this.messageRouter.doRouter(notifyRequest);
     System.out.println("need Delete----------------------->send succ");
     //TODO 保存订单信息
     saveOrderMessage(mosms);
   }

	private void saveOrderMessage(MO_SMMessage mosms) {
		String[] values = mosms.getServiceCode().split("#");

		// TODO pending;is ok
		Vasservice service = null;//this.daoUtil.getSmsSenderDao().getVASServiceByServiceCode(values[2]);
		
		List<Vasservice> servList=HttpRequestHelper.getNormalStatus();
		if(servList!=null&&servList.size()>0){
			for(int i=0;i<servList.size();i++){
				try {
					if(servList.get(i).getVasserviceReserveInfo().getSpProductid().equals(values[2])){
						service=servList.get(i);
						break;
					}
				} catch (Exception e) {
					
				}
			}
		}
		
		if (service == null) {
			logger.info("smsmo order product not exist[gmessage:" + mosms.getGlobalMessageid() + ",userphone:"
					+ mosms.getSendAddress() + "]");
		}

		// TODO pending;is ok
		UserOrder userOrder = new UserOrder();//this.daoUtil.getSmsSenderDao().getOrderRelation(mosms.getSendAddress(), values[5]);
		userOrder=HttpRequestHelper.getOrderRelation(mosms.getSendAddress(), values[2]);
				
		if (userOrder != null&&(userOrder.getId()!=null&&userOrder.getId().length()>0)) {
			logger.info("smsmo order orderrelation already exist[gmessage:" + mosms.getGlobalMessageid() + ",userphone:"
					+ mosms.getSendAddress() + "]");
		} else {
			// TODO pending;is ok
			String[] areaCode=HttpRequestHelper.getAreaCodeByUserPhone(mosms.getSendAddress());
			if(areaCode!=null&&areaCode.length==2){
				String provinceCode =areaCode[0];
				String cityCode =areaCode[1];
				//TODO not need saveOrderMessage
//				this.daoUtil.getSmsDAO().saveOrderMessage(mosms, provinceCode, cityCode, "1");
			}
			String phoneNumber=mosms.getSendAddress();
			String spInfoId=mosms.getVaspId();
			String productInfoId = HttpRequestHelper.getServuniqueIdbySpproductid(values[2]);
			String fee=values[4];
			String notifyflag="1";
			
			HttpRequestHelper.saveOrderMessage(phoneNumber,spInfoId,productInfoId,fee,notifyflag);

			logger.info("smsmo order orderrelation save db sucess[gmessage:" + mosms.getGlobalMessageid()
					+ ",userphone:" + mosms.getSendAddress() + "]");
		}
	}

	public static void main(String[] args) {
//		SmsBusinessHandlerImpl impl = (SmsBusinessHandlerImpl) ctx.getBean("smsBusinessHandlerImpl");
//		MO_SMMessage mosms = new MO_SMMessage();
//		mosms.setSendAddress("13268034426");
//		mosms.setSmsText("00000");
//		mosms.setVasId("1065556500104");
//
//		impl.doHandler(mosms);
	}

	/**
	 * 发送 MT_SMM Message 
	 * @param _msg
	 * @param send_addr
	 */
	private void sendMTMsgToQuence(String _msg, String send_addr) {
		MT_SMMessage mtsms = new MT_SMMessage();
		mtsms.setMtTranId(String.valueOf(System.currentTimeMillis()));
		mtsms.setSendAddress(this.dataCache.getSysParams("ACC_NUMBER"));
		mtsms.setRcvAddresses(new String[] { send_addr });
		mtsms.setSmsText(_msg);
		this.messageRouter.doRouter(mtsms);
	}

	private OperatorType getOperatorType(MO_SMMessage mosms) {
		String SERVICE_NUMBER = this.dataCache.getSysParams("ACC_NUMBER");
		String TAKE_ACC_NUMBER = this.dataCache.getSysParams("TAKE_ACC_NUMBER");

		String phoneNumber = mosms.getSendAddress();
		String vasId = mosms.getVasId();//3010012345
		String smstext = mosms.getSmsText().toLowerCase().trim();

		logger.info("smsmo set operator type[gmessageid:" + mosms.getGlobalMessageid() + ",smstext:" + smstext
				+ ",phoneNumber:" + phoneNumber + ",spNumber:" + vasId + "]");

//		TODO 测试删除，后续需要增加数据;Already processed;is ok
//		this.daoUtil.getSmsSenderDao().saveMoMsg(smstext, phoneNumber, vasId);
		HttpRequestHelper.saveMoMsg(smstext, phoneNumber, vasId);
		logger.info("smsmo save database [gmessageid:" + mosms.getGlobalMessageid());

		if ((smstext == null) || ("".equals(smstext.trim()))) {
			logger.info("smsmo content is empty [gmessageid:" + mosms.getGlobalMessageid() + ",spnumber:" + vasId
					+ ",smstext:" + smstext + "]");
			return new OperatorType(EnumOptype.OTHER);
		}

		if (!vasId.startsWith(SERVICE_NUMBER)) {
			logger.info("smsmo vasid is wrong [gmessageid:" + mosms.getGlobalMessageid() + ",spnumber:" + vasId
					+ ",smstext:" + smstext + "]");
			return new OperatorType(EnumOptype.OTHER);
		}

		if ((TAKE_ACC_NUMBER != null) && (!TAKE_ACC_NUMBER.trim().equals(""))
				&& (vasId.startsWith(TAKE_ACC_NUMBER))) {
			logger.info("smsmo take number command [gmessageid:" + mosms.getGlobalMessageid() + ",spnumber:" + vasId
					+ ",smstext:" + smstext + "]");

			OperatorType operatorType = new OperatorType(EnumOptype.TAKE_NUMBER);
			operatorType.setSpid(this.dataCache.getSysParams("UNIONVASPID"));
			return operatorType;
		}

		if ("00000".equals(smstext)) {
			//TODO pending;edit
			String sp_productids = "128398";//this.daoUtil.getSmsDAO().getProductIds(phoneNumber);
			sp_productids=HttpRequestHelper.getProductIds(phoneNumber);
			
			if (sp_productids == null) {
				logger.info("smsmo cancel 00000 vas service is null [gmessageid:" + mosms.getGlobalMessageid()
						+ ",spnumber:" + vasId + ",smstext:" + smstext + "]");
				return new OperatorType(EnumOptype.OTHER);
			}

			logger.info("smsmo cancel all command [gmessageid:" + mosms.getGlobalMessageid() + ",spnumber:" + vasId
					+ ",smstext:" + smstext + ",products:" + sp_productids + "]");

			OperatorType optype = new OperatorType(EnumOptype.CANCEL_ALL);
			optype.setProduct_id(sp_productids);
			return optype;
		}
		//TODO 判断逻辑修改待验证;Same
		//if (("0000".equals(smstext)) && (vasId.length() > SERVICE_NUMBER.length())) {
		if (("0000".equals(smstext)) && (vasId.length() >= SERVICE_NUMBER.length())) {
			//TODO 通过产品接入号查询业务信息;Already processed;is ok
			SmsSenderDto dto = new SmsSenderDto();
//			dto.setServiceId("testiwoeioewo1231233");
//			dto.setVaspid("vaspid0013010012345");
//			dto.setSp_productid("128398");
//			dto.setVaspname("sete");
//			dto.setProducts(testList);
			
			dto=HttpRequestHelper.getVasSpCpInfo(vasId);//this.daoUtil.getSmsDAO().getVasSpCpInfo(vasId);
			
			List<SmsSenderDto> testList=new ArrayList<>();
			SmsSenderDto dto2 = new SmsSenderDto();
			testList.add(dto2);
			dto.setProducts(testList);

			if (dto.getProducts().size() == 0) {
				logger.info("smsmo cancel 0000 vas service is null [gmessageid:" + mosms.getGlobalMessageid()
						+ ",spnumber:" + vasId + ",smstext:" + smstext + "]");
				return new OperatorType(EnumOptype.OTHER);
			}

			logger.info("smsmo cancel current command [gmessageid:" + mosms.getGlobalMessageid() + ",spnumber:"
					+ vasId + ",smstext:" + smstext + ",products:" + dto.getSp_productid() + "]");

			OperatorType optype = new OperatorType(EnumOptype.CANCEL_SERVICE);
			optype.setCmdtext(smstext);
			optype.setService_id(dto.getServiceId());
			optype.setSpid(dto.getVaspid());
			optype.setProduct_id(dto.getSp_productid());

			optype.setVaspname(dto.getVaspname());
			optype.setBusinessphone(dto.getBusinessphone() == null ? "" : dto.getBusinessphone());
			return optype;
		}

		//TODO 存在疑惑;need confirmed
		//if ((SERVICE_NUMBER.equals(vasId)) && ("Y".equalsIgnoreCase(smstext))) {
		if (("Y".equalsIgnoreCase(smstext))) {

			logger.info("smsmo confirm command [gmessageid:" + mosms.getGlobalMessageid() + ",spnumber:" + vasId
					+ ",smstext:" + smstext + "]");
			//TODO 通过手机号码和服务好查询短信内容和sp接入号;Already processed;is ok
			String[] smsText = {"order001","3010012345"};
			smsText=HttpRequestHelper.getLatestMoOrderMsgText(phoneNumber, 0L, vasId);//this.daoUtil.getSmsDAO().getLatestMoOrderMsgText(phoneNumber, 0L, SERVICE_NUMBER);
			
			Vasservice cofirmVas = this.dataCache.getOrderProduct(smsText[0] + smsText[1], null);

			if (cofirmVas == null) {
				logger.info("smsmo confirm vas service is null [gmessageid:" + mosms.getGlobalMessageid() + ",spnumber:"
						+ smsText[1] + ",smstext:" + smsText[0] + "]");
				return new OperatorType(EnumOptype.OTHER);
			}

			OperatorType optype = new OperatorType(EnumOptype.CONFIRM);
			optype.setCmdtext(smsText[0]);
			optype.setProduct_id(cofirmVas.getVasserviceReserveInfo().getSpProductid());
			optype.setService_id(cofirmVas.getServicedesc());
			optype.setSpid(cofirmVas.getVaspid());
			optype.setServicename(cofirmVas.getServicename());
			optype.setFee(cofirmVas.getOrderfee());
			optype.setUniqueid(cofirmVas.getUniqueid());
			//TODO 查询cp信息;Already processed;is ok
			SmsSenderDto dto = new SmsSenderDto();
			dto.setVaspname("testsp");
			dto=HttpRequestHelper.getVasSpCpInfo(smsText[1]);//this.daoUtil.getSmsDAO().getCPInfo(smsText[1]);
			
			optype.setVaspname(dto.getVaspname());
			optype.setBusinessphone(dto.getBusinessphone() == null ? "" : dto.getBusinessphone());

			return optype;
		}

		if (this.dataCache.getVasidsByOwner(vasId) != null) {
			logger.info("smsmo owner service command[gmessageid:" + mosms.getGlobalMessageid() + ",spnumber:" + vasId
					+ ",smstext:" + smstext + "]");

			Vasservice vasservice = this.dataCache.getOwnerProduct(smstext, vasId);
			OperatorType optype = new OperatorType(EnumOptype.OTHER);

			if (vasservice == null) {
				logger.info("smsmo owner service vas service is null[gmessageid:" + mosms.getGlobalMessageid()
						+ ",spnumber:" + vasId + ",smstext:" + smstext + "]");
				return optype;
			}
			if ("1".equals(vasservice.getVassmsid())) {
				optype = new OperatorType(EnumOptype.DIANBO);
				optype.setFee(vasservice.getOndemandfee());

			} else if ("2".equals(vasservice.getVassmsid())) {
				optype = new OperatorType(EnumOptype.ORDER);
				optype.setFee(vasservice.getOrderfee());

			} else if ("3".equals(vasservice.getVassmsid())) {
				optype = new OperatorType(EnumOptype.CANCEL);
				optype.setFee(vasservice.getOrderfee());
			}

			optype.setCmdtext(smstext);
			optype.setProduct_id(vasservice.getVasserviceReserveInfo().getSpProductid());
			optype.setService_id(vasservice.getServicedesc());
			optype.setSpid(vasservice.getVaspid());
			optype.setServicename(vasservice.getServicename());
			optype.setUniqueid(vasservice.getUniqueid());

			return optype;
		}

		logger.info("smsmo sp service command[gmessageid:" + mosms.getGlobalMessageid() + ",spnumber:" + vasId
				+ ",smstext:" + smstext + "]");

		Vasservice vasservice = this.dataCache.getSpProduct(smstext, vasId);
		OperatorType optype = new OperatorType(EnumOptype.OTHER);

		if (vasservice == null) {
			logger.info("smsmo sp service vas service is null[gmessageid:" + mosms.getGlobalMessageid() + ",spnumber:"
					+ vasId + ",smstext:" + smstext + "]");
			return optype;
		}
		if ("1".equals(vasservice.getVassmsid())) {
			optype = new OperatorType(EnumOptype.DIANBO);
			optype.setFee(vasservice.getOndemandfee());
		} else if ("2".equals(vasservice.getVassmsid())) {
			optype = new OperatorType(EnumOptype.ORDER);
			optype.setFee(vasservice.getOrderfee());
		} else if ("3".equals(vasservice.getVassmsid())) {
			optype = new OperatorType(EnumOptype.CANCEL);
			optype.setFee(vasservice.getOrderfee());
		}

		optype.setCmdtext(smstext);
		optype.setProduct_id(vasservice.getVasserviceReserveInfo().getSpProductid());
		optype.setService_id(vasservice.getServicedesc());
		optype.setSpid(vasservice.getVaspid());
		optype.setServicename(vasservice.getServicename());
		optype.setUniqueid(vasservice.getUniqueid());

		return optype;
	}

	public String getClientMessage(String errorCode) {//错误吗获取，需要从数据库改到内存
		logger.info("get error  code : " + errorCode);
		if (errorCode == null) {
			return "未知错误";
		}
		if (errorCode.equals("110"))
			return "ErrorCode:110, ErrorMsg:您发送的地址无效!";
		if (errorCode.equals("1003"))
			return "ErrorCode:110, ErrorMsg:您已经欠费!";
		if (errorCode.equals("1200"))
			return "ErrorCode:110, ErrorMsg:您已经定制该业务";
		if (errorCode.equals("1201"))
			return "ErrorCode:110, ErrorMsg:订购关系不存在";
		if (errorCode.equals("2101"))
			return "ErrorCode:110, ErrorMsg:业务不存在";
		if ((errorCode.equals("3101")) || (errorCode.equals("2202")))
			return "ErrorCode:110, ErrorMsg:您的余额不足";
		if (errorCode.equals("-1")) {
			return "业务处理延时";
		}
		return "处理失败！编号：" + errorCode;
	}

	public void setMessageRouter(IMessageRouter messageRouter) {//没找到调用位置
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
	public ConcurrentHashMap<String, AbstractMessage> getDataMap() {
		return this.dataMap;
	}
}

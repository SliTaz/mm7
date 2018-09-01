package com.zbensoft.mmsmp.common.ra.vas.sjb.unibusiness;

import com.zbensoft.mmsmp.common.ra.aaa.util.CheckResponse;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.AdminConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.SgipSubmit;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ServiceCapacity;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vas;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import com.zbensoft.mmsmp.common.ra.common.message.SendNotificationMessage;
import com.zbensoft.mmsmp.common.ra.common.notifymessageclient.NotifyMessageClientTcpImpl;
import com.zbensoft.mmsmp.common.ra.send.sgipsms.SmsSenderDao;
import com.zbensoft.mmsmp.common.ra.send.sgipsms.SmsSenderDto;
import com.zbensoft.mmsmp.common.ra.send.util.SmsSenderUtil;
import com.zbensoft.mmsmp.common.ra.utils.DateHelper;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.BusinessManageDAO;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.ContentInfoDAO;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.OrderRelationDAO;
import com.zbensoft.mmsmp.common.ra.vas.sjb.ismp.client.ISMPClient;
import com.zbensoft.mmsmp.common.ra.vas.sjb.messagehandler.SendNotificationMessageHandler;
import com.zbensoft.mmsmp.common.ra.wo.order.UserOrderResponse;
import com.zbensoft.mmsmp.common.ra.ckp.server.OrderRelationClient;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import javax.jws.WebService;
import org.apache.log4j.Logger;

@WebService(serviceName = "UniBusinessService", targetNamespace = "http://unibusiness.sjb.vas.aceway.com", endpointInterface = "com.aceway.vas.sjb.unibusiness.UniBusiness")
public class UniBusinessServiceImpl implements UniBusiness {
	private static Logger logger = Logger.getLogger(UniBusinessServiceImpl.class);
	private BusinessManageDAO businessManageDAO;
	private OrderRelationDAO orderRelationDAO;
	private ContentInfoDAO contentInfoDAO;
	private SmsSenderDao smsSenderDao;
	private SendNotificationMessageHandler sendNotificationMessageHandler;
	private SgipSubmit sgipSubmit = ConfigUtil.getInstance().getSgipSubmit();

	public SendNotificationMessageHandler getSendNotificationMessageHandler() {
		return this.sendNotificationMessageHandler;
	}

	public void setSendNotificationMessageHandler(SendNotificationMessageHandler sendNotificationMessageHandler) {
		this.sendNotificationMessageHandler = sendNotificationMessageHandler;
	}

	public Response serviceCapabilityManage(ServiceCapabilityManageRequest request) {
		logger.debug("received serviceCapabilityManage request :" + request.toString());

		Response res = new Response();
		try {
			ServiceCapacity serviceCapacity = this.businessManageDAO.getServiceCapacityByCAPACITYID(request.getServiceCapabilityID());
			if (serviceCapacity == null) {
				res.setReturnCode(Constants.ResponseCode_IDError);
				return res;
			}

			int operate = request.getOperate();

			if (operate == Constants.ManageRequest_Operate_Apply) {
				serviceCapacity.setStatus("1");
			} else if (operate == Constants.ManageRequest_Operate_Cancel) {
				serviceCapacity.setStatus("4");
			} else if (operate == Constants.ManageRequest_Operate_Normal) {
				serviceCapacity.setStatus("2");
			} else if (operate == Constants.ManageRequest_Operate_Pause) {
				serviceCapacity.setStatus("3");
			} else if (operate == Constants.ManageRequest_Operate_PreCancel) {
				serviceCapacity.setStatus("5");
			} else {
				res.setReturnCode(Constants.ResponseCode_StatusError);
				return res;
			}

			this.businessManageDAO.updateByCapacity(serviceCapacity);

			res.setReturnCode(Constants.ResponseCode_Sucess);
			return res;
		} catch (Throwable ex) {
			ex.printStackTrace();

			res.setReturnCode(Constants.ResponseCode_OtherError);
		}
		return res;
	}

	public Response orderRelationManage(OrderRelationRequest request3) {
		logger.info("received  orderRelationManage request : " + request3.toString());

		Response res = new Response();
		HttpURLConnection httpURL = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		String userPhone = "";
		if ((request3.getUserPhone() != null) && (request3.getUserPhone().length() >= 11)) {
			userPhone = request3.getUserPhone().substring(request3.getUserPhone().length() - 11);
		} else {
			res.codeType = Constants.ResponseCodeType_MMSMP;
			res.setReturnCode(Constants.ResponseCode_UserPhoneError);
			res.desc = "用户号码不合法";
			return res;
		}

		String channel = request3.getChannel();
		if (channel == null) {
			res.codeType = Constants.ResponseCodeType_MMSMP;
			res.returnCode = Constants.ResponseCode_ChannelTypeIsNullError;
			res.desc = "没有指定渠道类型";
			return res;
		}

		int intChannel = Integer.parseInt(channel);
		Response localResponse2;
//		byte[] b;
		switch (intChannel) {
		case 1:
			try {
				int feeType;
				if (Constants.OrderRelation_Status_Order == request3.getStatus()) {
					request3.setFeeType(1);
					feeType = 1;
				} else {
//					int feeType;
					if (Constants.OrderRelation_Status_Demand == request3.getStatus()) {
						request3.setFeeType(4);
						feeType = 2;
					} else {
						res.codeType = Constants.ResponseCodeType_MMSMP;
						res.setReturnCode(Constants.ResponseCode_StatusError);
						res.desc = "stat非法";
						localResponse2 = res;
						return localResponse2;
					}
				}
//				int feeType;
				String temp = request3.getAaaURL();
				request3.setAaaURL(temp + "?sp_product_id=" + request3.getProductID() + "&service_id=" + request3.getServiceId() + "&spid=" + request3.getSpCode() + "&user_number=" + request3.getUserPhone()
						+ "&serviceType=" + request3.getFeeType() + "&src_SequenceNumber=&linkid=");
				logger.info(request3.getAaaURL());

				URL theURL = new URL(request3.getAaaURL());
				httpURL = (HttpURLConnection) theURL.openConnection();
				httpURL.setDoInput(true);
				httpURL.setDoOutput(true);
				httpURL.setRequestMethod("POST");
				httpURL.setRequestProperty("content-type", "text/xml");

				dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
				if (httpURL.getResponseCode() == 200) {
					dis = new DataInputStream(new BufferedInputStream(httpURL.getInputStream()));
					int len = dis.available();
					byte[] b = new byte[len];
					int r = dis.read(b);
					int pos = r;
					while (pos < len) {
						r = dis.read(b, pos, len - pos);
						pos += r;
					}

					String str = new String(b);
					CheckResponse response = CheckResponse.CheckResponseForXml(str);
					if (response.getResult_Code().equals("0")) {
						res.codeType = Constants.ResponseCodeType_MMSMP;
						res.setReturnCode(Constants.ResponseCode_Sucess);
						res.desc = "恭喜您，订购成功";

						Vasservice vasservice = this.smsSenderDao.getVASServiceByServiceCode(request3.getProductID());
						String url = this.smsSenderDao.getSmsSenderUrl(request3.getServiceId());

						int result = -1;
						String orderNotifyUrl = this.smsSenderDao.getRelationNotifyUrl(request3.getSpCode());
						if (Constants.OrderRelation_Status_Order == request3.getStatus()) {
							result = OrderRelationClient.notifySp(orderNotifyUrl, System.currentTimeMillis()+"", request3.getUserPhone(), DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "test",
									DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "", "31", request3.getProductID(), request3.getSpCode(), DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "", 1,
									request3.getUserPhone(), 1);
						} else if ((response.getLinkID() != null) && (!"".equals(response.getLinkID())) && (Constants.OrderRelation_Status_Demand == request3.getStatus())) {
							SmsSenderUtil.deliver2SP(url, this.smsSenderDao.getService_idByProduct_id(request3.getProductID()), 8, vasservice.getOndemandcode(), response.getLinkID(), request3.getUserPhone(),
									this.sgipSubmit.getTp_pid(), this.sgipSubmit.getTp_udhi());
						} else {
							logger.error("vac linkid is null ! notify sp fail!");
						}

						if ((result == 0) || (Constants.OrderRelation_Status_Demand == request3.getStatus())) {
							SaveOrderRaltion(request3.getUserPhone(), request3.getProductID(), request3.getSpCode(), "1", "", "0");
							logger.info("=====>order relation notify sp  url :  " + orderNotifyUrl);
						} else {
							SaveOrderRaltion(request3.getUserPhone(), request3.getProductID(), request3.getSpCode(), "1", "", "1");
						}

						SmsSenderDto smsSenderDto = this.smsSenderDao.getProductInfo(request3.getProductID(), feeType);
						String _msg = "您定购[" + smsSenderDto.getServicename() + "]成功！,资费:" + smsSenderDto.getFee() + "元";
						String[] userPhoneArr = { request3.getUserPhone() };
						System.out.println(_msg);
						sendMessage(_msg, userPhoneArr);
					} else if (response.getServerResult_code().equals("1200")) {
						res.codeType = Constants.ResponseCodeType_VAC;
						res.setReturnCode(response.getServerResult_code());
						res.desc = "您已经订购，无须重复订购";
					} else {
						res.codeType = Constants.ResponseCodeType_VAC;
						res.setReturnCode(response.getServerResult_code());
						res.desc = "订购失败";
					}
				} else {
					logger.error("sp return error message: [HTTP/1.1 " + httpURL.getResponseCode() + "] " + httpURL.getResponseMessage());
				}
			} catch (Throwable ex) {
				logger.error("", ex);
			} finally {
				if (dis != null)
					try {
						dis.close();
					} catch (Exception localException4) {
					}
				if (dos != null)
					try {
						dos.close();
					} catch (Exception localException5) {
					}
				if (httpURL != null) {
					httpURL.disconnect();
				}
			}
			break;
		case 2:
			try {
				int feeType;
				if (Constants.OrderRelation_Status_Order == request3.getStatus()) {
					request3.setFeeType(1);
					feeType = 1;
				} else {
//					int feeType;
					if (Constants.OrderRelation_Status_Demand == request3.getStatus()) {
						request3.setFeeType(4);
						feeType = 2;
					} else {
						res.codeType = Constants.ResponseCodeType_MMSMP;
						res.setReturnCode(Constants.ResponseCode_StatusError);
						res.desc = "stat非法";
						localResponse2 = res;
						return localResponse2;
					}
				}
//				int feeType;
				String temp = request3.getAaaURL();
				request3.setAaaURL(temp + "?sp_product_id=" + request3.getProductID() + "&service_id=" + request3.getServiceId() + "&spid=" + request3.getSpCode() + "&user_number=" + request3.getUserPhone()
						+ "&serviceType=" + request3.getFeeType() + "&src_SequenceNumber=&linkid=");
				logger.info(request3.getAaaURL());

				URL theURL = new URL(request3.getAaaURL());
				httpURL = (HttpURLConnection) theURL.openConnection();
				httpURL.setDoInput(true);
				httpURL.setDoOutput(true);
				httpURL.setRequestMethod("POST");
				httpURL.setRequestProperty("content-type", "text/xml");

				dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
				if (httpURL.getResponseCode() == 200) {
					dis = new DataInputStream(new BufferedInputStream(httpURL.getInputStream()));
					int len = dis.available();
					byte[] b = new byte[len];
					int r = dis.read(b);
					int pos = r;
					while (pos < len) {
						r = dis.read(b, pos, len - pos);
						pos += r;
					}

					String str = new String(b);
					CheckResponse response = CheckResponse.CheckResponseForXml(str);
					if (response.getResult_Code().equals("0")) {
						res.codeType = Constants.ResponseCodeType_MMSMP;
						res.setReturnCode(Constants.ResponseCode_Sucess);
						res.desc = "恭喜您，订购成功";

						String orderNotifyUrl = this.smsSenderDao.getRelationNotifyUrl(request3.getSpCode());

						int result = -1;

						Vasservice vasservice = this.smsSenderDao.getVASServiceByServiceCode(request3.getProductID());
						String url = this.smsSenderDao.getSmsSenderUrl(request3.getServiceId());

						if (Constants.OrderRelation_Status_Order == request3.getStatus()) {
							result = OrderRelationClient.notifySp(orderNotifyUrl, System.currentTimeMillis()+"", request3.getUserPhone(), DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "test",
									DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "", "31", request3.getProductID(), request3.getSpCode(), DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "", 1,
									request3.getUserPhone(), 1);
						} else if ((response.getLinkID() != null) && (!"".equals(response.getLinkID())) && (Constants.OrderRelation_Status_Demand == request3.getStatus())) {
							SmsSenderUtil.deliver2SP(url, this.smsSenderDao.getService_idByProduct_id(request3.getProductID()), 8, vasservice.getOndemandcode(), response.getLinkID(), request3.getUserPhone(),
									this.sgipSubmit.getTp_pid(), this.sgipSubmit.getTp_udhi());
						} else {
							logger.error("vac linkid is null ! notify sp fail!");
						}

						if ((result == 0) || (Constants.OrderRelation_Status_Demand == request3.getStatus())) {
							SaveOrderRaltion(request3.getUserPhone(), request3.getProductID(), request3.getSpCode(), "2", "", "0");
							logger.info("=====>order relation notify sp  url :  " + orderNotifyUrl);
						} else {
							SaveOrderRaltion(request3.getUserPhone(), request3.getProductID(), request3.getSpCode(), "2", "", "1");
							logger.info("=====>order relation notify sp  url :  " + orderNotifyUrl);
						}

						SmsSenderDto smsSenderDto = this.smsSenderDao.getProductInfo(request3.getProductID(), feeType);
						String _msg = "您定购[" + smsSenderDto.getServicename() + "]成功！,资费:" + smsSenderDto.getFee() + "元";
						String[] userPhoneArr = new String[] { request3.getUserPhone() };
						sendMessage(_msg, userPhoneArr);
					} else if (response.getServerResult_code().equals("1200")) {
						res.codeType = Constants.ResponseCodeType_VAC;
						res.setReturnCode(response.getServerResult_code());
						res.desc = "您已经订购，无须重复订购";
					} else {
						res.codeType = Constants.ResponseCodeType_VAC;
						res.setReturnCode(response.getServerResult_code());
						res.desc = "订购失败";
					}
				} else {
					logger.error("sp return error message: [HTTP/1.1 " + httpURL.getResponseCode() + "] " + httpURL.getResponseMessage());
				}
			} catch (Throwable ex) {
				logger.error("", ex);
			} finally {
				if (dis != null)
					try {
						dis.close();
					} catch (Exception localException12) {
					}
				if (dos != null)
					try {
						dos.close();
					} catch (Exception localException13) {
					}
				if (httpURL != null) {
					httpURL.disconnect();
				}
			}
			break;
		case 3:
			try {
				String orderNotifyUrl = this.smsSenderDao.getRelationNotifyUrl(request3.getSpCode());
				int result = -1;

				Vasservice vasservice = this.smsSenderDao.getVASServiceByServiceCode(request3.getProductID());
				String url = this.smsSenderDao.getSmsSenderUrl(request3.getServiceId());

				if (Constants.OrderRelation_Status_Order == request3.getStatus()) {
					result = OrderRelationClient.notifySp(orderNotifyUrl, System.currentTimeMillis()+"", request3.getUserPhone(), DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "test",
							DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "", "31", request3.getProductID(), request3.getSpCode(), DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "", 1, request3.getUserPhone(),
							1);
				} else if (Constants.OrderRelation_Status_Demand == request3.getStatus()) {
					String linkid = "ACE" + System.currentTimeMillis();
					logger.info("linkid generated by aceway is : " + linkid);
					SmsSenderUtil.deliver2SP(url, this.smsSenderDao.getService_idByProduct_id(request3.getProductID()), 8, vasservice.getOndemandcode(), linkid, request3.getUserPhone(), this.sgipSubmit.getTp_pid(),
							this.sgipSubmit.getTp_udhi());
				}
				Response localResponse1;
				if ((Constants.OrderRelation_Status_Order != request3.getStatus()) && (Constants.OrderRelation_Status_Demand != request3.getStatus())) {
					res.codeType = Constants.ResponseCodeType_MMSMP;
					res.setReturnCode(Constants.ResponseCode_StatusError);
					res.desc = "stat非法";
					localResponse1 = res;
					return localResponse1;
				}
//				String url;
//				Vasservice vasservice;
//				int result;
//				String orderNotifyUrl;
				if (Constants.OrderRelation_Status_Demand == request3.getStatus()) {
					request3.setFeeType(4);
					int feeType = 2;

					SaveOrderRaltion(request3.getUserPhone(), request3.getProductID(), request3.getSpCode(), "3", "", "0");

					if (!this.smsSenderDao.getNeedConfm(request3.getProductID())) {
						URL theURL = new URL(request3.getAaaURL());
						httpURL = (HttpURLConnection) theURL.openConnection();
						httpURL.setDoInput(true);
						httpURL.setDoOutput(true);
						httpURL.setRequestMethod("POST");
						httpURL.setRequestProperty("content-type", "text/xml");

						dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
						dos.write(request3.getWoInfo().getBytes());
						dos.flush();
						if (httpURL.getResponseCode() == 200) {
							dis = new DataInputStream(new BufferedInputStream(httpURL.getInputStream()));
							int len = dis.available();
							byte[] b = new byte[len];
							int r = dis.read(b);
							int pos = r;
							while (pos < len) {
								r = dis.read(b, pos, len - pos);
								pos += r;
							}

							String str = new String(b);
							UserOrderResponse response = UserOrderResponse.UserOrderResponseForXml(str);

							if (response.getFlag().equals("20000")) {
								res.codeType = Constants.ResponseCodeType_MMSMP;
								res.setReturnCode(Constants.ResponseCode_Sucess);
								res.desc = "恭喜您，点播成功";

								SmsSenderDto smsSenderDto = this.smsSenderDao.getProductInfo(request3.getProductID(), feeType);
								String _msg = "您点播[" + smsSenderDto.getServicename() + "]成功！,资费:" + smsSenderDto.getFee() + "元";
								String[] userPhoneArr = { request3.getUserPhone() };
								sendMessage(_msg, userPhoneArr);
							} else {
								res.codeType = Constants.ResponseCodeType_WO;
								res.setReturnCode(response.getFlag());
								res.desc = "点播失败";
							}
						} else {
							res.codeType = Constants.ResponseCodeType_WO;
							res.desc = "点播失败";
							logger.error("sp return error message: [HTTP/1.1 " + httpURL.getResponseCode() + "] " + httpURL.getResponseMessage());
						}
					} else {
						res.codeType = Constants.ResponseCodeType_MMSMP;
						res.setReturnCode(Constants.ResponseCode_Sucess);
						res.desc = "点播成功";
					}

					localResponse1 = res;
					return localResponse1;
				}
//				String url;
//				Vasservice vasservice;
//				int result;
//				String orderNotifyUrl;
				request3.setFeeType(1);
				int feeType = 1;

				if (result == 0) {
					SaveOrderRaltion(request3.getUserPhone(), request3.getProductID(), request3.getSpCode(), "3", "", "0");
					logger.info("=====>order relation notify sp  url :  " + orderNotifyUrl);
				} else {
					SaveOrderRaltion(request3.getUserPhone(), request3.getProductID(), request3.getSpCode(), "3", "", "1");
					logger.info("=====>order relation notify sp  url :  " + orderNotifyUrl);
				}

				URL theURL = new URL(request3.getAaaURL());
				httpURL = (HttpURLConnection) theURL.openConnection();
				httpURL.setDoInput(true);
				httpURL.setDoOutput(true);
				httpURL.setRequestMethod("POST");
				httpURL.setRequestProperty("content-type", "text/xml");

				dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
				dos.write(request3.getWoInfo().getBytes());
				dos.flush();
				if (httpURL.getResponseCode() == 200) {
					dis = new DataInputStream(new BufferedInputStream(httpURL.getInputStream()));
					int len = dis.available();
					byte[] b = new byte[len];
					int r = dis.read(b);
					int pos = r;
					while (pos < len) {
						r = dis.read(b, pos, len - pos);
						pos += r;
					}

					String str = new String(b);
					UserOrderResponse response = UserOrderResponse.UserOrderResponseForXml(str);

					if (response.getFlag().equals("20000")) {
						res.codeType = Constants.ResponseCodeType_MMSMP;
						res.setReturnCode(Constants.ResponseCode_Sucess);
						res.desc = "恭喜您，订购成功";

						this.smsSenderDao.updateOrderRelation(request3.getUserPhone(), request3.getProductID(), response.getSp_OrderId());

						SmsSenderDto smsSenderDto = this.smsSenderDao.getProductInfo(request3.getProductID(), feeType);
						String _msg = "您定购[" + smsSenderDto.getServicename() + "]成功！,资费:" + smsSenderDto.getFee() + "元";
						String[] userPhoneArr = { request3.getUserPhone() };
						sendMessage(_msg, userPhoneArr);
					} else {
						res.codeType = Constants.ResponseCodeType_WO;
						res.setReturnCode(response.getFlag());
						res.desc = "订购失败";
					}
				} else {
					logger.error("sp return error message: [HTTP/1.1 " + httpURL.getResponseCode() + "] " + httpURL.getResponseMessage());
				}
			} catch (Throwable ex) {
				logger.error("", ex);
			} finally {
				if (dis != null)
					try {
						dis.close();
					} catch (Exception localException42) {
					}
				if (dos != null)
					try {
						dos.close();
					} catch (Exception localException43) {
					}
				if (httpURL != null) {
					httpURL.disconnect();
				}
			}
			break;
		case 6:
			try {
				String orderNotifyUrl = this.smsSenderDao.getRelationNotifyUrl(request3.getSpCode());
				int result = OrderRelationClient.notifySp(orderNotifyUrl, System.currentTimeMillis()+"", request3.getUserPhone(), DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "test",
						DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "", "31", request3.getProductID(), request3.getSpCode(), DateHelper.getString(new Date(), "yyyyMMddHHmmss"), "", 2, request3.getUserPhone(), 1);

				if (result == 0) {
					logger.info("=====>order relation notify sp successfully!");
				} else {
					logger.info("=====>order relation notify sp failed : " + result);
					res.codeType = Constants.ResponseCodeType_SP;
					res.setReturnCode(result+"");
					res.desc = "操作失败";
//					b = res;
//					return b;
					return res;
				}
//				int result;
//				String orderNotifyUrl;
				if (Constants.OrderRelation_Status_Cancel != request3.getStatus()) {
					res.codeType = Constants.ResponseCodeType_MMSMP;
					res.setReturnCode(Constants.ResponseCode_StatusError);
					res.desc = "stat非法";
//					b = res;
//					return b;
					return res;
				}
//				int result;
//				String orderNotifyUrl;
				CancelOrderRaltion(request3.getUserPhone(), request3.getProductID(), "3");

				SmsSenderDto smsSenderDto = this.smsSenderDao.getProductInfo(request3.getProductID(), 1);
				String _msg = "您取消定购[" + smsSenderDto.getServicename() + "]成功！";
				String[] userPhoneArr = { request3.getUserPhone() };
				sendMessage(_msg, userPhoneArr);
			} catch (Throwable ex) {
				logger.error("", ex);
			} finally {
				if (dis != null)
					try {
						dis.close();
					} catch (Exception localException32) {
					}
				if (dos != null)
					try {
						dos.close();
					} catch (Exception localException33) {
					}
				if (httpURL != null) {
					httpURL.disconnect();
				}
			}
			break;
		case 4:
		case 5:
		default:
			res.codeType = Constants.ResponseCodeType_MMSMP;
			res.returnCode = Constants.ResponseCode_ChannelTypeError;
			res.desc = "未知渠道";
			return res;
		}

		return res;
	}

	public Response produceManage(ProductManageRequest request2) {
		logger.debug("received produceManage request :" + request2.toString());

		Response res = new Response();
		try {
			Vasservice product = this.businessManageDAO.getVASServiceByServiceCode(request2.getProductID());
			if (product == null) {
				if (Integer.valueOf(request2.getProductType()).intValue() == Constants.OrderType_Product) {
					res.setReturnCode(Constants.ResponseCode_ProductCodeError);
					return res;
				}
				if (Integer.valueOf(request2.getProductType()).intValue() == Constants.OrderType_Package) {
					res.setReturnCode(Constants.ResponseCode_PackageCodeError);
					return res;
				}
			} else {
				int operate = request2.getOperate();

				if (operate == Constants.ManageRequest_Operate_Apply) {
					product.setStatus("1");
				} else if (operate == Constants.ManageRequest_Operate_Cancel) {
					product.setStatus("4");
				} else if (operate == Constants.ManageRequest_Operate_Normal) {
					product.setStatus("2");
				} else if (operate == Constants.ManageRequest_Operate_Pause) {
					product.setStatus("3");
				} else if (operate == Constants.ManageRequest_Operate_PreCancel) {
					product.setStatus("5");
				} else {
					res.setReturnCode(Constants.ResponseCode_StatusError);
					return res;
				}

				this.businessManageDAO.updateByProduct(product);

				res.setReturnCode(Constants.ResponseCode_Sucess);
				return res;
			}
		} catch (Throwable ex) {
			ex.printStackTrace();

			res.setReturnCode(Constants.ResponseCode_OtherError);
		}
		return res;
	}

	public Response serviceManage(ServiceManageRequest request1) {
		logger.debug("received serviceManage request :" + request1.toString());

		Response res = new Response();
		try {
			Vas vas = this.businessManageDAO.getVASByVASID(request1.getServiceID());
			if (vas == null) {
				res.setReturnCode(Constants.ResponseCode_IDError);
				return res;
			}

			int operate = request1.getOperate();

			if (operate == Constants.ManageRequest_Operate_Apply) {
				vas.setStatus("1");
			} else if (operate == Constants.ManageRequest_Operate_Cancel) {
				vas.setStatus("4");
			} else if (operate == Constants.ManageRequest_Operate_Normal) {
				vas.setStatus("2");
			} else if (operate == Constants.ManageRequest_Operate_Pause) {
				vas.setStatus("3");
			} else if (operate == Constants.ManageRequest_Operate_PreCancel) {
				vas.setStatus("5");
			} else {
				res.setReturnCode(Constants.ResponseCode_StatusError);
				return res;
			}

			this.businessManageDAO.updateByVas(vas);

			res.setReturnCode(Constants.ResponseCode_Sucess);
			return res;
		} catch (Throwable ex) {
			ex.printStackTrace();

			res.setReturnCode(Constants.ResponseCode_OtherError);
		}
		return res;
	}

	public BusinessManageDAO getBusinessManageDAO() {
		return this.businessManageDAO;
	}

	public void setBusinessManageDAO(BusinessManageDAO businessManageDAO) {
		this.businessManageDAO = businessManageDAO;
	}

	public OrderRelationDAO getOrderRelationDAO() {
		return this.orderRelationDAO;
	}

	public void setOrderRelationDAO(OrderRelationDAO orderRelationDAO) {
		this.orderRelationDAO = orderRelationDAO;
	}

	public ContentInfoDAO getContentInfoDAO() {
		return this.contentInfoDAO;
	}

	public void setContentInfoDAO(ContentInfoDAO contentInfoDAO) {
		this.contentInfoDAO = contentInfoDAO;
	}

	public Response reverseUnsubscribeManage(ReverseUnsubscribeManageRequest request1) {
		logger.debug("received reverseUnsubscribeManage request :" + request1.toString());
		String userId = request1.getPhone();
		int userIdType = request1.getUserType().intValue();
		int idType = request1.getProductType().intValue();
		String id = request1.getProductID();
		ISMPClient ismpClient = new ISMPClient();
		int result = ismpClient.withDrawUserOrder(userId, Integer.valueOf(userIdType), Integer.valueOf(idType), id);
		Response response = new Response();
		response.setReturnCode("0");
		if (result == 0) {
			try {
				this.orderRelationDAO.delOrderRelation(userId, id);
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.setReturnCode(Constants.ResponseCode_OtherError);
				return response;
			}
			response.setReturnCode(Constants.ResponseCode_Sucess);
		} else {
			response.setReturnCode(Constants.ResponseCode_OtherError);
		}
		return response;
	}

	public void SaveOrderRaltion(String userPhone, String productId, String spId, String channel, String spOrderId, String notifySpFlag) {
		Vasservice service = this.smsSenderDao.getVASServiceByServiceCode(productId);
		if (service == null) {
			logger.info("=====>产品ID不存在");
		}
		String uniqueid = this.smsSenderDao.getUniqueid(productId);
		UserOrder userOrder = this.smsSenderDao.getOrderRelation(userPhone, uniqueid);
		if ((userOrder != null) && (service.getFeetype().equals("1"))) {
			logger.info("=====>定制关系已经存在");
		} else {
			String provinceCode = this.smsSenderDao.getAreaCodeByUserPhone(userPhone);
			String cityCode = this.smsSenderDao.getCityCodeByUserPhone(userPhone);
			this.smsSenderDao.AddOrderRelation(userPhone, service, provinceCode, cityCode, channel, spOrderId, notifySpFlag);
		}
	}

	public void CancelOrderRaltion(String userPhone, String productId, String channel) {
		Vasservice service = this.smsSenderDao.getVASServiceByServiceCode(productId);
		if (service == null) {
			logger.info("=====>产品ID不存在");
		}
		String uniqueid = this.smsSenderDao.getUniqueid(productId);
		UserOrder userOrder = this.smsSenderDao.getOrderRelation(userPhone, uniqueid);
		if (userOrder == null) {
			logger.info("=====>定制关系不存在");
		} else {
			this.smsSenderDao.CancelOrderRelation(userPhone, service, userOrder, channel);
		}
	}

	public void setSmsSenderDao(SmsSenderDao smsSenderDao) {
		this.smsSenderDao = smsSenderDao;
	}

	public static boolean sendMessage(String smstext, String[] receiveaddresses) {
		try {
			SendNotificationMessage notificatonMsg = new SendNotificationMessage();
			notificatonMsg.setPhoneNumber(checkPhones(receiveaddresses));
			notificatonMsg.setSendContent(smstext);
			notificatonMsg.setMtType("4");
			String host = ConfigUtil.getInstance().getAdminConfig().getNotifyMessageIP();
			String port = ConfigUtil.getInstance().getAdminConfig().getNotifyMessagePort();
			NotifyMessageClientTcpImpl notifyMsgClient = new NotifyMessageClientTcpImpl(host, Integer.parseInt(port));

			int result = notifyMsgClient.send(notificatonMsg);
			if (result == 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static String[] checkPhones(String[] receiveaddresses) {
		for (int i = 0; i < receiveaddresses.length; i++) {
			if (!receiveaddresses[i].startsWith("86")) {
				receiveaddresses[i] = ("86" + receiveaddresses[i]);
			}
		}
		return receiveaddresses;
	}

	public static void main(String[] args) {
		sendMessage("您的验证码为：1212", new String[] { "8615901405008" });
	}
}

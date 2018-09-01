package com.zbensoft.mmsmp.common.ra.vas.sjb.messagehandler;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.SendNotificationMessage;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
import com.zbensoft.mmsmp.common.ra.common.queue.messagehandler.AbstractMessageHandler;
import com.zbensoft.mmsmp.common.ra.vas.sjb.common.MakeMessage;
import com.zbensoft.mmsmp.common.ra.vas.sjb.controller.DataCenter;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.BusinessManageDAO;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.ContentInfoDAO;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.LoadUserOrderDao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendNotificationMessageHandler extends AbstractMessageHandler {
	private static final Log logger = LogFactory.getLog(SendNotificationMessageHandler.class);
	private static final int USER_PRIORITY_VIP = 1;
	private static final int USER_PRIORITY_common = 2;
	private static final int USER_PRIORITY_experience = 3;
	private MessageQueue MT_queue;
	private LoadUserOrderDao loadUserOrderDao;
	private ContentInfoDAO contentInfoDao;
	private BusinessManageDAO businessManageDao;
	private DataCenter dataCenter;
	private CorebizConfig config = ConfigUtil.getInstance().getCorebizConfig();
	private static int reqNum = 10;

	public LoadUserOrderDao getLoadUserOrderDao() {
		return this.loadUserOrderDao;
	}

	public void setLoadUserOrderDao(LoadUserOrderDao loadUserOrderDao) {
		this.loadUserOrderDao = loadUserOrderDao;
	}

	public boolean handleable(Object notifyMessage) {
		if ((notifyMessage instanceof SendNotificationMessage)) {
			return true;
		}
		return false;
	}

	public void handleMessage(Object notifyMessage) {
		logger.info("begin notification message");
		try {
			SendNotificationMessage notifyMsg = (SendNotificationMessage) notifyMessage;
			logger.info("right!!!--------------- sendtype: \t" + notifyMsg.getSendType());
			AbstractMessage msg;
			if (notifyMsg.getSendType() == 1) {
				logger.info("This is BY_PHONENUMBER to Send!");
				if (notifyMsg.getServiceType() == 1) {
					Vasservice vasService = this.businessManageDao.getVASServiceByServiceCode(notifyMsg.getServiceCode());

					setReqNum(vasService.getProducttype());

					List contentInfo = this.contentInfoDao.getContentByContentId(notifyMsg.getContentId(), false);
					logger.info("<<<<<<<<<<<<<<<<<< dianbo");
					if ((contentInfo != null) && (contentInfo.size() > 0)) {
						logger.info("<<<<<<<<<<<<<<<there is contents dianbo");
						ContentInfo content = (ContentInfo) contentInfo.get(0);

						String mtranId = MakeMessage.generateStreamingNum(Integer.toString(vasService.getUniqueid().intValue()));
						List userList = this.loadUserOrderDao.getUserInfoForDemand(notifyMsg.getPhoneNumber(), mtranId, content.getContentid().intValue(), vasService.getUniqueid().intValue(), notifyMsg.getMtType());
						if (userList.size() <= 0) {
							UserInfo userInfo = new UserInfo();
							userInfo.setCellphonenumber(notifyMsg.getPhoneNumber()[0]);
							userList.add(userInfo);
						}

						List mtMessageList = MakeMessage.getMessage(vasService, (ContentInfo) contentInfo.get(0), userList, null, mtranId, this.dataCenter);

						for (Iterator localIterator1 = mtMessageList.iterator(); localIterator1.hasNext();) {
							msg = (AbstractMessage) localIterator1.next();
							msg.setLinkId(notifyMsg.getLinkId());
							this.MT_queue.addMessage(msg, Integer.valueOf(MessageQueue.PRIORITY_VIP));
						}
					}
				} else {
					Vasservice vasService = this.businessManageDao.getVASServiceByServiceCode(notifyMsg.getServiceCode());

					List contentInfo = this.contentInfoDao.getContentByContentId(notifyMsg.getContentId(), false);
					if ((contentInfo != null) && (contentInfo.size() > 0)) {
						String mtranId = MakeMessage.generateStreamingNum(Integer.toString(vasService.getUniqueid().intValue()));
						List userList = this.loadUserOrderDao.getUserInfo(vasService.getUniqueid(), ((ContentInfo) contentInfo.get(0)).getContentid(), mtranId, notifyMsg.getPhoneNumber(), notifyMsg.getMtType());
						try {
							List<AbstractMessage>  mtMessageList = MakeMessage.getMessage(vasService, (ContentInfo) contentInfo.get(0), userList, null, mtranId, this.dataCenter);

							for (AbstractMessage msgTmp : mtMessageList)
								this.MT_queue.addMessage(msgTmp, Integer.valueOf(MessageQueue.PRIORITY_VIP));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else if (notifyMsg.getSendType() == 2) {
				logger.info("This is BY_PRODUCTID to Send!");

				Vasservice vasService = this.businessManageDao.getVASServiceByServiceCode(notifyMsg.getServiceCode());

				List contentInfo = this.contentInfoDao.getNeedSendContentList(vasService.getUniqueid().intValue(), false, false);
				if ((contentInfo != null) && (contentInfo.size() > 0)) {
					createMtMessage(vasService, notifyMsg, contentInfo, Integer.valueOf(1));
					createMtMessage(vasService, notifyMsg, contentInfo, Integer.valueOf(2));
					createMtMessage(vasService, notifyMsg, contentInfo, Integer.valueOf(3));
				}
			} else if (notifyMsg.getSendType() == 3) {
				logger.info("This is BY_STATUS to Send!");

				Vasservice vasService = this.businessManageDao.getVASServiceByServiceCode(notifyMsg.getServiceCode());

				List contentInfo = this.contentInfoDao.getContentByContentId(notifyMsg.getContentId(), false);
				if ((contentInfo != null) && (contentInfo.size() > 0)) {
					String mtranId = MakeMessage.generateStreamingNum(Integer.toString(vasService.getUniqueid().intValue()));
					String[] province = notifyMsg.getProvince();
					List provinceList = new ArrayList();
					String[] arrayOfString1;
					arrayOfString1 = province;
					int localAbstractMessage1_length = arrayOfString1.length;
					for (int msg_int = 0; msg_int < localAbstractMessage1_length; msg_int++) {
						String area = arrayOfString1[msg_int];
						provinceList.add(area);
					}

					int k = 0;
					while (provinceList.size() > 0) {
						String area = (String) provinceList.get(k);

						List userList = this.loadUserOrderDao.getUserOrderRecordBySendStatus(vasService.getUniqueid(), ((ContentInfo) contentInfo.get(0)).getContentid(), reqNum, mtranId, area, notifyMsg.getStatus(),
								notifyMsg.getMtType());
						if (userList.size() == 0) {
							provinceList.remove(k);
						} else {
							if (userList.size() < reqNum) {
								provinceList.remove(k);
							}

							List<AbstractMessage> mtMessageList = MakeMessage.getMessage(vasService, (ContentInfo) contentInfo.get(0), userList, null, mtranId, this.dataCenter);

							for (AbstractMessage msg_tmp : mtMessageList) {
								this.MT_queue.addMessage(msg_tmp, Integer.valueOf(MessageQueue.PRIORITY_VIP));
							}

							k++;
							if (k >= provinceList.size())
								k = 0;
						}
					}
				}
			} else {
				logger.info("starting to send sms to user.....");
				String mtContent = notifyMsg.getSendContent();
				MT_SMMessage mtMessage = new MT_SMMessage();

				mtMessage.setRcvAddresses(notifyMsg.getPhoneNumber());

				mtMessage.setMtTranId(notifyMsg.getLinkId());
				mtMessage.setServiceCode(notifyMsg.getServiceCode());
				mtMessage.setSmsText(mtContent);

				mtMessage.setOperatorsType(3);
				this.MT_queue.addMessage(mtMessage, Integer.valueOf(MessageQueue.PRIORITY_COMMON));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SendNotificationMessageHandler is Error:" + e.getMessage());
		}
	}

	private void setReqNum(String producttype) {
		if (producttype.equalsIgnoreCase("m"))
			reqNum = this.config.getMmsReqNum();
		else if (producttype.equalsIgnoreCase("e"))
			reqNum = this.config.getMmeReqNum();
		else if (producttype.equalsIgnoreCase("s"))
			reqNum = this.config.getSmsReqNum();
		else if (producttype.equalsIgnoreCase("p"))
			reqNum = this.config.getWapReqNum();
	}

	private void createMtMessage(Vasservice vasService, SendNotificationMessage notifyMsg, List<ContentInfo> contentInfo, Integer priority) {
		String mtranId = MakeMessage.generateStreamingNum(Integer.toString(vasService.getUniqueid().intValue()));
		List userList = this.loadUserOrderDao.getUserOrderRecord(vasService.getUniqueid(), Integer.valueOf(reqNum), mtranId, notifyMsg.getProvince()[0], priority,
				Integer.toString(((ContentInfo) contentInfo.get(0)).getContentid().intValue()), true);

		List<AbstractMessage>  mtMessageList = MakeMessage.getMessage(vasService, (ContentInfo) contentInfo.get(0), userList, null, mtranId, this.dataCenter);

		for (AbstractMessage msg : mtMessageList)
			this.MT_queue.addMessage(msg, Integer.valueOf(MessageQueue.PRIORITY_VIP));
	}

	public ContentInfoDAO getContentInfoDao() {
		return this.contentInfoDao;
	}

	public void setContentInfoDao(ContentInfoDAO contentInfoDao) {
		this.contentInfoDao = contentInfoDao;
	}

	public BusinessManageDAO getBusinessManageDao() {
		return this.businessManageDao;
	}

	public void setBusinessManageDao(BusinessManageDAO businessManageDao) {
		this.businessManageDao = businessManageDao;
	}

	public MessageQueue getMT_queue() {
		return this.MT_queue;
	}

	public void setMT_queue(MessageQueue mt_queue) {
		this.MT_queue = mt_queue;
	}

	public DataCenter getDataCenter() {
		return this.dataCenter;
	}

	public void setDataCenter(DataCenter dataCenter) {
		this.dataCenter = dataCenter;
	}
}

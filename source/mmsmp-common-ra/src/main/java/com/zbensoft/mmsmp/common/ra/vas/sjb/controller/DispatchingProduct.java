package com.zbensoft.mmsmp.common.ra.vas.sjb.controller;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MEMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_WapPushMessage;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
import com.zbensoft.mmsmp.common.ra.vas.sjb.common.MakeMessage;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.BusinessManageDAO;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.ContentInfoDAO;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.LoadUserOrderDao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DispatchingProduct implements Runnable {
	private static final int USER_PRIORITY_VIP = 1;
	private static final int USER_PRIORITY_common = 2;
	private static final int USER_PRIORITY_experience = 3;
	private static final Log logger = LogFactory.getLog(DispatchingProduct.class);

	static int dbReqNum = 10;
	static int mmsReqNum = ConfigUtil.getInstance().getCorebizConfig().getMmsReqNum();
	static int mmeReqNum = ConfigUtil.getInstance().getCorebizConfig().getMmeReqNum();
	static int smsReqNum = ConfigUtil.getInstance().getCorebizConfig().getSmsReqNum();
	static int wapReqNum = ConfigUtil.getInstance().getCorebizConfig().getWapReqNum();
	boolean isSameQueue = ConfigUtil.getInstance().getCorebizConfig().isSameQueue();
	private MessageQueue queue;
	private List<ContentInfo> contentList;
	private Vasservice vasservice;
	private LoadUserOrderDao loadUserOrderDao;
	private ContentInfoDAO contentInfoDAO;
	private BusinessManageDAO businessManageDAO;
	private List<String> areaList;
	private DataCenter dataCenter;
	private String batchID;
	private String contenIDString = "";

	private boolean isNational = false;
	private Map<String, String> sysParamsMap;

	public DispatchingProduct(MessageQueue queue, List<ContentInfo> contentList, Vasservice vasservice, List<String> areaList, LoadUserOrderDao loadUserOrderDao, ContentInfoDAO contentInfoDAO,
			BusinessManageDAO businessManageDAO, DataCenter dataCenter) {
		this.queue = queue;
		this.contentList = contentList;
		this.vasservice = vasservice;
		this.areaList = areaList;
		this.dataCenter = dataCenter;
		this.sysParamsMap = dataCenter.getSysParamsMap();
		boolean adapter = !"0".equals(((String) this.sysParamsMap.get("ADAPTER_SWITCH")).split("@")[0]);
		this.isNational = ("0000".equals(((String) this.sysParamsMap.get("PLATFORM_AREA")).split("@")[0]));
		if (!adapter) {
			this.isNational = true;
		}
		this.loadUserOrderDao = loadUserOrderDao;

		this.contentInfoDAO = contentInfoDAO;
		this.businessManageDAO = businessManageDAO;

		this.batchID = MakeMessage.generateStreamingNum(String.valueOf(vasservice.getUniqueid()));
		dbReqNum = getReqNum(vasservice.getProducttype());

		this.contenIDString = Integer.toString(((ContentInfo) contentList.get(0)).getContentid().intValue());

		beginLoadData();
	}

	private int getReqNum(String producttype) {
		if (producttype.equalsIgnoreCase("m"))
			return mmsReqNum;
		if (producttype.equalsIgnoreCase("e"))
			return mmeReqNum;
		if (producttype.equalsIgnoreCase("s"))
			return smsReqNum;
		if (producttype.equalsIgnoreCase("p")) {
			return wapReqNum;
		}

		return dbReqNum;
	}

	public void run() {
		try {
			List cyAreaList = new ArrayList();
			cyAreaList.addAll(this.areaList);
			loadData(1, cyAreaList);

			cyAreaList.clear();
			cyAreaList.addAll(this.areaList);
			loadData(2, cyAreaList);

			cyAreaList.clear();
			cyAreaList.addAll(this.areaList);
			loadData(3, cyAreaList);
		} catch (Throwable th) {
			th.printStackTrace();
		}

		logger.info("load userDate over. serviceID: " + this.vasservice.getUniqueid());

		afterLoadData();
	}

	private void loadData(int priority, List<String> cyAreaList) {
		int k = 0;

		while (cyAreaList.size() > 0) {
			String area = (String) cyAreaList.get(k);

			List userInfos = this.loadUserOrderDao.getUserOrderRecord(this.vasservice.getUniqueid(), Integer.valueOf(dbReqNum), this.batchID, area, Integer.valueOf(priority), this.contenIDString, this.isNational);

			if (userInfos.size() == 0) {
				cyAreaList.remove(k);

				if (k >= cyAreaList.size()) {
					k = 0;
				}

			} else {
				logger.debug("get user data success .area is:" + (String) cyAreaList.get(k) + ". count is: " + userInfos.size());
				Iterator<AbstractMessage> localIterator2;
				for (Iterator localIterator1 = this.contentList.iterator(); localIterator1.hasNext(); localIterator2.hasNext()) {
					ContentInfo cont = (ContentInfo) localIterator1.next();

					String status = this.businessManageDAO.getContentStatus(cont.getContentid());
					if (!status.equalsIgnoreCase("4")) {
						if (this.contentList.size() != 1)
							break;
						return;
					}

					List<AbstractMessage> list = MakeMessage.getMessage(this.vasservice, cont, userInfos, area, this.batchID, this.dataCenter);

					localIterator2 = list.iterator();
//					continue;
					AbstractMessage message = (AbstractMessage) localIterator2.next();

					if (this.isSameQueue) {
						this.queue.addMessage(message, Integer.valueOf(MessageQueue.PRIORITY_COMMON));
					} else if ((message instanceof MT_MEMessage))
						this.queue.addMessage(message, Integer.valueOf(MessageQueue.PRIORITY_LOW));
					else if ((message instanceof MT_SMMessage))
						this.queue.addMessage(message, Integer.valueOf(MessageQueue.PRIORITY_COMMON_SMS));
					else if ((message instanceof MT_WapPushMessage))
						this.queue.addMessage(message, Integer.valueOf(MessageQueue.PRIORITY_COMMON_WAP));
					else {
						this.queue.addMessage(message, Integer.valueOf(MessageQueue.PRIORITY_COMMON));
					}

				}

				k++;
				if (k >= cyAreaList.size())
					k = 0;
			}
		}
	}

	private void beginLoadData() {
		this.businessManageDAO.updateProductDealStatus(Integer.valueOf(2), this.vasservice.getUniqueid());

		this.contentInfoDAO.updateFlag(this.contentList, "2");
	}

	private void afterLoadData() {
		this.businessManageDAO.updateProductDealStatus(Integer.valueOf(0), this.vasservice.getUniqueid());

		this.contentInfoDAO.updateFlag(this.contentList, "1");
	}
}

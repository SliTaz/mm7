package com.zbensoft.mmsmp.common.ra.vas.sjb.common;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.CommonConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfoAdapter;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfoRelation;
import com.zbensoft.mmsmp.common.ra.common.db.entity.SysChannels;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MEMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_WapPushMessage;
import com.zbensoft.mmsmp.common.ra.vas.sjb.controller.DataCenter;
import com.zbensoft.mmsmp.common.ra.vas.sjb.util.DateString;
import com.zbensoft.mmsmp.common.ra.vas.sjb.util.ValidateOperators;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;

public class MakeMessage {
	private static final Logger log = Logger.getLogger(MakeMessage.class);

	public static String contentPath = ConfigUtil.getInstance().getCommonConfig().getContentFilePath();

	public static String wappushURL = ConfigUtil.getInstance().getCorebizConfig().getWappushUrl();

	public static boolean isSameProduct = ConfigUtil.getInstance().getCorebizConfig().isSameProduct();

	public static boolean terminalAdapter = ConfigUtil.getInstance().getCorebizConfig().isTerminalAdapter();

	public static boolean operatorsAdapter = ConfigUtil.getInstance().getCorebizConfig().isTerminalAdapter();

	public static boolean isAdapter = false;

	public static boolean isNational = false;

	private static int count = 0;

	public static List<AbstractMessage> getMessage(Vasservice vasservice, ContentInfo contentInfo, List<UserInfo> userInfoList, String area, String streamingNum, DataCenter dataCenter) {
		List mmsPhoneList = new LinkedList();
		List mmePhoneList = new LinkedList();
		List cm_mmsPhoneList = new LinkedList();
		List te_mmsPhoneList = new LinkedList();
		List uc_mmsPhoneList = new LinkedList();
		List cm_smsPhoneList = new LinkedList();
		List te_smsPhoneList = new LinkedList();
		List uc_smsPhoneList = new LinkedList();
		List cm_wapPhoneList = new LinkedList();
		List te_wapPhoneList = new LinkedList();
		List uc_wapPhoneList = new LinkedList();

		List phoneList = new LinkedList();
		String dateString = DateString.getTime();
		int cm_mmsMultiNum = 1;
		int cm_smsMultiNum = 1;
		int cm_wapMultiNum = 1;
		int te_mmsMultiNum = 1;
		int te_smsMultiNum = 1;
		int te_wapMultiNum = 1;
		int te_mmeMultiNum = 1;
		int uc_mmsMultiNum = 1;
		int uc_smsMultiNum = 1;
		int uc_wapMultiNum = 1;
		String cm_mmsMtUrl = null;
		String cm_smsMtUrl = null;
		String cm_wapMtUrl = null;
		String te_mmsMtUrl = null;
		String te_smsMtUrl = null;
		String te_wapMtUrl = null;
		String uc_mmsMtUrl = null;
		String uc_smsMtUrl = null;
		String uc_wapMtUrl = null;
		String mmFile = contentInfo.getContentpath();
		List messageList = new LinkedList();
		Map sysParamsMap = dataCenter.getSysParamsMap();
		Map sysChannelMap = dataCenter.getSysChannelMap();
		LinkedList userList = (LinkedList) userInfoList;
		isAdapter = !"0".equals(((String) sysParamsMap.get("ADAPTER_SWITCH")).split("@")[0]);
		isNational = "0000".equals(((String) sysParamsMap.get("PLATFORM_AREA")).split("@")[0]);
		SysChannels cm_mmsChannel = (SysChannels) sysChannelMap.get("cmm");
		if (cm_mmsChannel != null) {
			cm_mmsMultiNum = cm_mmsChannel.getMultiNum();
			cm_mmsMtUrl = cm_mmsChannel.getChannelURL();
		}
		SysChannels cm_smsChannel = (SysChannels) sysChannelMap.get("cms");
		if (cm_smsChannel != null) {
			cm_smsMultiNum = cm_smsChannel.getMultiNum();
			cm_smsMtUrl = cm_smsChannel.getChannelURL();
		}
		SysChannels cm_wapChannel = (SysChannels) sysChannelMap.get("cmp");
		if (cm_wapChannel != null) {
			cm_wapMultiNum = cm_wapChannel.getMultiNum();
			cm_wapMtUrl = cm_wapChannel.getChannelURL();
		}
		SysChannels te_mmeChannel = (SysChannels) sysChannelMap.get("tee");
		if (te_mmeChannel != null) {
			te_mmeMultiNum = te_mmeChannel.getMultiNum();
		}
		SysChannels te_mmsChannel = (SysChannels) sysChannelMap.get("tem");
		if (te_mmsChannel != null) {
			te_mmsMultiNum = te_mmsChannel.getMultiNum();
			te_mmsMtUrl = te_mmsChannel.getChannelURL();
		}
		SysChannels te_smsChannel = (SysChannels) sysChannelMap.get("tes");
		if (cm_smsChannel != null) {
			te_smsMultiNum = te_smsChannel.getMultiNum();
			te_smsMtUrl = te_smsChannel.getChannelURL();
		}
		SysChannels te_wapChannel = (SysChannels) sysChannelMap.get("tep");
		if (cm_wapChannel != null) {
			te_wapMultiNum = te_wapChannel.getMultiNum();
			te_wapMtUrl = te_wapChannel.getChannelURL();
		}

		SysChannels uc_mmsChannel = (SysChannels) sysChannelMap.get("ucm");
		if (te_mmsChannel != null) {
			uc_mmsMultiNum = uc_mmsChannel.getMultiNum();
			uc_mmsMtUrl = uc_mmsChannel.getChannelURL();
		}
		SysChannels uc_smsChannel = (SysChannels) sysChannelMap.get("ucs");
		if (cm_smsChannel != null) {
			uc_smsMultiNum = uc_smsChannel.getMultiNum();
			uc_smsMtUrl = uc_smsChannel.getChannelURL();
		}
		SysChannels uc_wapChannel = (SysChannels) sysChannelMap.get("ucp");
		if (cm_wapChannel != null) {
			uc_wapMultiNum = uc_wapChannel.getMultiNum();
			uc_wapMtUrl = uc_wapChannel.getChannelURL();
		}
		log.info("-------------------product type" + vasservice.getProducttype());
//		MT_MMMessage msg;
		if (vasservice.getProducttype().equalsIgnoreCase("m")) {
			if (isSameProduct) {
				log.info("---------------userListSize" + userList.size());
				while (userList.size() > 0) {
					UserInfo userInfo = (UserInfo) userList.getFirst();
					userList.removeFirst();
					String phoneNumber = userInfo.getCellphonenumber();
					String terminaltype = userInfo.getTerminaltype();
					if (isAdapter) {
						log.info("--------need to adapt!");

						ContentInfoAdapter temp1 = null;
						ContentInfoRelation temp2 = null;
						if ((userInfo.getTerminaltype() != null) && (contentInfo.getAdapterContentMap() != null)) {
							temp1 = (ContentInfoAdapter) contentInfo.getAdapterContentMap().get(Integer.valueOf(userInfo.getTerminaltype()));
						}
						if (temp1 != null) {
							if ((temp1.getProvinceContentMap() != null) && (area != null)) {
								temp2 = (ContentInfoRelation) temp1.getProvinceContentMap().get(area);
							}
							if (temp2 != null)
								mmFile = temp2.getContentpath();
							else {
								mmFile = temp1.getContentpath();
							}
						} else if (isNational) {
							if ((area != null) && (contentInfo.getProvinceContent() != null)) {
								ContentInfoRelation temp3 = (ContentInfoRelation) contentInfo.getProvinceContent().get(area);
								if (temp3 != null)
									mmFile = temp3.getContentpath();
								else
									mmFile = contentInfo.getContentpath();
							} else {
								mmFile = contentInfo.getContentpath();
							}
						} else if ((area != null) && (contentInfo.getProvinceContent() != null)) {
							ContentInfoRelation temp3 = (ContentInfoRelation) contentInfo.getProvinceContent().get(area);
							if (temp3 != null)
								mmFile = temp3.getContentpath();
							else
								mmFile = contentInfo.getContentpath();
						} else {
							mmFile = contentInfo.getContentpath();
						}

						if (terminalAdapter) {
							if ("3".equals(terminaltype)) {
								mmePhoneList.add(phoneNumber);
								if (mmePhoneList.size() == te_mmeMultiNum) {
									MT_MEMessage msg = new MT_MEMessage();
									msg.setServiceId(vasservice.getUniqueid().intValue());
									if (te_mmeMultiNum > 1) {
										msg.setGroupSend(true);
									}
									msg.setMtTranId(streamingNum);
									msg.setSendAddress(vasservice.getVasid());
									msg.setServiceCode(vasservice.getServicecode());
									msg.setVasId(vasservice.getVasid());
									msg.setVaspId(vasservice.getVaspid());
									msg.setTimeStamp(dateString);
									msg.setSubject(contentInfo.getContentname());
									msg.setContentid(contentInfo.getContentid());
									msg.setProvinceCode(area);
									msg.setMmFile(mmFile);
									msg.setRcvAddresses((String[]) mmePhoneList.toArray(new String[te_mmeMultiNum]));
									messageList.add(msg);
									mmePhoneList.clear();
								}
							} else if (ValidateOperators.isChinaMobile(phoneNumber)) {
								cm_mmsPhoneList.add(phoneNumber);
								if (cm_mmsPhoneList.size() == cm_mmsMultiNum) {
									MT_MMMessage msg = new MT_MMMessage();
									msg.setServiceId(vasservice.getUniqueid().intValue());
									if (cm_mmsMultiNum > 1) {
										msg.setGroupSend(true);
									}
									msg.setOperatorsType(1);
									msg.setMtTranId(streamingNum);
									msg.setSendAddress(vasservice.getVasid());
									msg.setServiceCode(vasservice.getServicecode());
									msg.setVasId(vasservice.getVasid());
									msg.setVaspId(vasservice.getVaspid());
									msg.setTimeStamp(dateString);
									msg.setSubject(contentInfo.getContentname());
									msg.setContentid(contentInfo.getContentid());
									msg.setMmFile(mmFile);
									msg.setRcvAddresses((String[]) cm_mmsPhoneList.toArray(new String[cm_mmsMultiNum]));
									msg.setMtUrl(cm_mmsMtUrl);
									messageList.add(msg);
									cm_mmsPhoneList.clear();
								}
							} else if (ValidateOperators.isChinaTelecom(phoneNumber)) {
								te_mmsPhoneList.add(phoneNumber);
								if (te_mmsPhoneList.size() == te_mmsMultiNum) {
									MT_MMMessage msg = new MT_MMMessage();
									msg.setServiceId(vasservice.getUniqueid().intValue());
									if (te_mmsMultiNum > 1) {
										msg.setGroupSend(true);
									}
									msg.setOperatorsType(2);
									msg.setMtTranId(streamingNum);
									msg.setSendAddress(vasservice.getVasid());
									msg.setServiceCode(vasservice.getServicecode());
									msg.setVasId(vasservice.getVasid());
									msg.setVaspId(vasservice.getVaspid());
									msg.setTimeStamp(dateString);
									msg.setSubject(contentInfo.getContentname());
									msg.setContentid(contentInfo.getContentid());
									msg.setMmFile(mmFile);
									msg.setRcvAddresses((String[]) te_mmsPhoneList.toArray(new String[te_mmsMultiNum]));
									msg.setMtUrl(te_mmsMtUrl);
									messageList.add(msg);
									cm_mmsPhoneList.clear();
								}
							} else if (ValidateOperators.isChinaUnicom(phoneNumber)) {
								log.info("---------------------=======");
								uc_mmsPhoneList.add(phoneNumber);
								if (uc_mmsPhoneList.size() == uc_mmsMultiNum) {
									MT_MMMessage msg = new MT_MMMessage();
									msg.setServiceId(vasservice.getUniqueid().intValue());
									if (uc_mmsMultiNum > 1) {
										msg.setGroupSend(true);
									}
									msg.setOperatorsType(3);
									msg.setMtTranId(streamingNum);
									msg.setSendAddress(vasservice.getVasid());
									msg.setServiceCode(vasservice.getServicecode());
									msg.setVasId(vasservice.getVasid());
									msg.setVaspId(vasservice.getVaspid());
									msg.setTimeStamp(dateString);
									msg.setSubject(contentInfo.getContentname());
									msg.setContentid(contentInfo.getContentid());
									msg.setMmFile(mmFile);
									msg.setRcvAddresses((String[]) uc_mmsPhoneList.toArray(new String[uc_mmsMultiNum]));
									msg.setMtUrl(uc_mmsMtUrl);
									messageList.add(msg);
									cm_mmsPhoneList.clear();
								}
							}

						} else if ((phoneNumber.startsWith("133")) || (phoneNumber.startsWith("153"))) {
							mmePhoneList.add(phoneNumber);
							if (mmePhoneList.size() == te_mmeMultiNum) {
								MT_MEMessage msg = new MT_MEMessage();
								msg.setServiceId(vasservice.getUniqueid().intValue());
								if (te_mmeMultiNum > 1) {
									msg.setGroupSend(true);
								}
								msg.setMtTranId(streamingNum);
								msg.setSendAddress(vasservice.getVasid());
								msg.setServiceCode(vasservice.getServicecode());
								msg.setVasId(vasservice.getVasid());
								msg.setVaspId(vasservice.getVaspid());
								msg.setTimeStamp(dateString);
								msg.setSubject(contentInfo.getContentname());
								msg.setContentid(contentInfo.getContentid());
								msg.setProvinceCode(area);
								msg.setMmFile(mmFile);
								msg.setRcvAddresses((String[]) mmePhoneList.toArray(new String[te_mmeMultiNum]));
								messageList.add(msg);
								mmePhoneList.clear();
							}
						} else if (ValidateOperators.isChinaMobile(phoneNumber)) {
							cm_mmsPhoneList.add(phoneNumber);
							if (cm_mmsPhoneList.size() == cm_mmsMultiNum) {
								MT_MMMessage msg = new MT_MMMessage();
								msg.setServiceId(vasservice.getUniqueid().intValue());
								if (cm_mmsMultiNum > 1) {
									msg.setGroupSend(true);
								}
								msg.setOperatorsType(1);
								msg.setMtTranId(streamingNum);
								msg.setSendAddress(vasservice.getVasid());
								msg.setServiceCode(vasservice.getServicecode());
								msg.setVasId(vasservice.getVasid());
								msg.setVaspId(vasservice.getVaspid());
								msg.setTimeStamp(dateString);
								msg.setSubject(contentInfo.getContentname());
								msg.setContentid(contentInfo.getContentid());
								msg.setMmFile(mmFile);
								msg.setRcvAddresses((String[]) cm_mmsPhoneList.toArray(new String[cm_mmsMultiNum]));
								msg.setMtUrl(cm_mmsMtUrl);
								messageList.add(msg);
								cm_mmsPhoneList.clear();
							}
						} else if (ValidateOperators.isChinaTelecom(phoneNumber)) {
							te_mmsPhoneList.add(phoneNumber);
							if (te_mmsPhoneList.size() == te_mmsMultiNum) {
								MT_MMMessage msg = new MT_MMMessage();
								msg.setServiceId(vasservice.getUniqueid().intValue());
								if (te_mmsMultiNum > 1) {
									msg.setGroupSend(true);
								}
								msg.setOperatorsType(2);
								msg.setMtTranId(streamingNum);
								msg.setSendAddress(vasservice.getVasid());
								msg.setServiceCode(vasservice.getServicecode());
								msg.setVasId(vasservice.getVasid());
								msg.setVaspId(vasservice.getVaspid());
								msg.setTimeStamp(dateString);
								msg.setSubject(contentInfo.getContentname());
								msg.setContentid(contentInfo.getContentid());
								msg.setMmFile(mmFile);
								msg.setRcvAddresses((String[]) te_mmsPhoneList.toArray(new String[te_mmsMultiNum]));
								msg.setMtUrl(te_mmsMtUrl);
								messageList.add(msg);
								cm_mmsPhoneList.clear();
							}
						} else if (ValidateOperators.isChinaUnicom(phoneNumber)) {
							log.info("---------------------=======");
							uc_mmsPhoneList.add(phoneNumber);
							if (uc_mmsPhoneList.size() == uc_mmsMultiNum) {
								MT_MMMessage msg = new MT_MMMessage();
								msg.setServiceId(vasservice.getUniqueid().intValue());
								if (uc_mmsMultiNum > 1) {
									msg.setGroupSend(true);
								}
								msg.setOperatorsType(3);
								msg.setMtTranId(streamingNum);
								msg.setSendAddress(vasservice.getVasid());
								msg.setServiceCode(vasservice.getServicecode());

								msg.setVasId(vasservice.getVasid());
								msg.setVaspId(vasservice.getVaspid());
								msg.setTimeStamp(dateString);
								msg.setSubject(contentInfo.getContentname());
								msg.setContentid(contentInfo.getContentid());
								msg.setMmFile(mmFile);
								msg.setRcvAddresses((String[]) uc_mmsPhoneList.toArray(new String[uc_mmsMultiNum]));
								msg.setMtUrl(uc_mmsMtUrl);

								log.info("$$$$$$$$$print out the msg");
								log.info("service id is" + vasservice.getUniqueid());
								log.info("MtTranId is" + streamingNum);
								log.info("ServiceCode is" + vasservice.getServicecode());
								log.info("VasId is" + vasservice.getVasid());
								log.info("VaspId is" + vasservice.getVaspid());
								log.info("Subject is" + contentInfo.getContentname());
								log.info("Contentid is" + contentInfo.getContentid());
								log.info("MmFile is" + mmFile);
								log.info("MtUrl is" + uc_mmsMtUrl);

								messageList.add(msg);
								cm_mmsPhoneList.clear();
							}
						}
					} else {
						log.info("no need to adapt!");
						if (terminalAdapter) {
							if ("3".equals(terminaltype)) {
								mmePhoneList.add(phoneNumber);
								if (mmePhoneList.size() == te_mmeMultiNum) {
									MT_MEMessage msg = new MT_MEMessage();
									msg.setServiceId(vasservice.getUniqueid().intValue());
									if (te_mmeMultiNum > 1) {
										msg.setGroupSend(true);
									}
									msg.setMtTranId(streamingNum);
									msg.setSendAddress(vasservice.getVasid());
									msg.setServiceCode(vasservice.getServicecode());
									msg.setVasId(vasservice.getVasid());
									msg.setVaspId(vasservice.getVaspid());
									msg.setTimeStamp(dateString);
									msg.setSubject(contentInfo.getContentname());
									msg.setContentid(contentInfo.getContentid());
									msg.setProvinceCode(area);
									msg.setMmFile(mmFile);
									msg.setRcvAddresses((String[]) mmePhoneList.toArray(new String[te_mmeMultiNum]));
									messageList.add(msg);
									mmePhoneList.clear();
								}
							} else if (ValidateOperators.isChinaMobile(phoneNumber)) {
								cm_mmsPhoneList.add(phoneNumber);
								if (cm_mmsPhoneList.size() == cm_mmsMultiNum) {
									MT_MMMessage msg = new MT_MMMessage();
									msg.setServiceId(vasservice.getUniqueid().intValue());
									if (cm_mmsMultiNum > 1) {
										msg.setGroupSend(true);
									}
									msg.setOperatorsType(1);
									msg.setMtTranId(streamingNum);
									msg.setSendAddress(vasservice.getVasid());
									msg.setServiceCode(vasservice.getServicecode());
									msg.setVasId(vasservice.getVasid());
									msg.setVaspId(vasservice.getVaspid());
									msg.setTimeStamp(dateString);
									msg.setSubject(contentInfo.getContentname());
									msg.setContentid(contentInfo.getContentid());
									msg.setMmFile(mmFile);
									msg.setRcvAddresses((String[]) cm_mmsPhoneList.toArray(new String[cm_mmsMultiNum]));
									msg.setMtUrl(cm_mmsMtUrl);
									messageList.add(msg);
									cm_mmsPhoneList.clear();
								}
							} else if (ValidateOperators.isChinaTelecom(phoneNumber)) {
								te_mmsPhoneList.add(phoneNumber);
								if (te_mmsPhoneList.size() == te_mmsMultiNum) {
									MT_MMMessage msg = new MT_MMMessage();
									msg.setServiceId(vasservice.getUniqueid().intValue());
									if (te_mmsMultiNum > 1) {
										msg.setGroupSend(true);
									}
									msg.setOperatorsType(2);
									msg.setMtTranId(streamingNum);
									msg.setSendAddress(vasservice.getVasid());
									msg.setServiceCode(vasservice.getServicecode());
									msg.setVasId(vasservice.getVasid());
									msg.setVaspId(vasservice.getVaspid());
									msg.setTimeStamp(dateString);
									msg.setSubject(contentInfo.getContentname());
									msg.setContentid(contentInfo.getContentid());
									msg.setMmFile(mmFile);
									msg.setRcvAddresses((String[]) te_mmsPhoneList.toArray(new String[te_mmsMultiNum]));
									msg.setMtUrl(te_mmsMtUrl);
									messageList.add(msg);
									cm_mmsPhoneList.clear();
								}
							} else if (ValidateOperators.isChinaUnicom(phoneNumber)) {
								uc_mmsPhoneList.add(phoneNumber);
								if (uc_mmsPhoneList.size() == uc_mmsMultiNum) {
									MT_MMMessage msg = new MT_MMMessage();
									msg.setServiceId(vasservice.getUniqueid().intValue());
									if (uc_mmsMultiNum > 1) {
										msg.setGroupSend(true);
									}
									msg.setOperatorsType(3);
									msg.setMtTranId(streamingNum);
									msg.setSendAddress(vasservice.getVasid());
									msg.setServiceCode(vasservice.getServicecode());
									msg.setVasId(vasservice.getVasid());
									msg.setVaspId(vasservice.getVaspid());
									msg.setTimeStamp(dateString);
									msg.setSubject(contentInfo.getContentname());
									msg.setContentid(contentInfo.getContentid());
									msg.setMmFile(mmFile);
									msg.setRcvAddresses((String[]) uc_mmsPhoneList.toArray(new String[uc_mmsMultiNum]));
									msg.setMtUrl(uc_mmsMtUrl);
									messageList.add(msg);
									cm_mmsPhoneList.clear();
								}
							}

						} else if ((phoneNumber.startsWith("133")) || (phoneNumber.startsWith("153"))) {
							mmePhoneList.add(phoneNumber);
							if (mmePhoneList.size() == te_mmeMultiNum) {
								MT_MEMessage msg = new MT_MEMessage();
								msg.setServiceId(vasservice.getUniqueid().intValue());
								if (te_mmeMultiNum > 1) {
									msg.setGroupSend(true);
								}
								msg.setMtTranId(streamingNum);
								msg.setSendAddress(vasservice.getVasid());
								msg.setServiceCode(vasservice.getServicecode());
								msg.setVasId(vasservice.getVasid());
								msg.setVaspId(vasservice.getVaspid());
								msg.setTimeStamp(dateString);
								msg.setSubject(contentInfo.getContentname());
								msg.setContentid(contentInfo.getContentid());
								msg.setProvinceCode(area);
								msg.setMmFile(contentInfo.getContentpath());
								msg.setRcvAddresses((String[]) mmePhoneList.toArray(new String[te_mmeMultiNum]));
								messageList.add(msg);
								mmePhoneList.clear();
							}
						} else {
							log.info("------------------mms user------------");
							if (ValidateOperators.isChinaMobile(phoneNumber)) {
								cm_mmsPhoneList.add(phoneNumber);
								if (cm_mmsPhoneList.size() == cm_mmsMultiNum) {
									MT_MMMessage msg = new MT_MMMessage();
									msg.setServiceId(vasservice.getUniqueid().intValue());
									if (cm_mmsMultiNum > 1) {
										msg.setGroupSend(true);
									}
									msg.setOperatorsType(1);
									msg.setMtTranId(streamingNum);
									msg.setSendAddress(vasservice.getVasid());
									msg.setServiceCode(vasservice.getServicecode());
									msg.setVasId(vasservice.getVasid());
									msg.setVaspId(vasservice.getVaspid());
									msg.setTimeStamp(dateString);
									msg.setSubject(contentInfo.getContentname());
									msg.setContentid(contentInfo.getContentid());
									msg.setMmFile(mmFile);
									msg.setRcvAddresses((String[]) cm_mmsPhoneList.toArray(new String[cm_mmsMultiNum]));
									msg.setMtUrl(cm_mmsMtUrl);
									messageList.add(msg);
									cm_mmsPhoneList.clear();
								}
							} else if (ValidateOperators.isChinaTelecom(phoneNumber)) {
								te_mmsPhoneList.add(phoneNumber);
								if (te_mmsPhoneList.size() == te_mmsMultiNum) {
									MT_MMMessage msg = new MT_MMMessage();
									msg.setServiceId(vasservice.getUniqueid().intValue());
									if (te_mmsMultiNum > 1) {
										msg.setGroupSend(true);
									}
									msg.setOperatorsType(2);
									msg.setMtTranId(streamingNum);
									msg.setSendAddress(vasservice.getVasid());
									msg.setServiceCode(vasservice.getServicecode());
									msg.setVasId(vasservice.getVasid());
									msg.setVaspId(vasservice.getVaspid());
									msg.setTimeStamp(dateString);
									msg.setSubject(contentInfo.getContentname());
									msg.setContentid(contentInfo.getContentid());
									msg.setMmFile(mmFile);
									msg.setRcvAddresses((String[]) te_mmsPhoneList.toArray(new String[te_mmsMultiNum]));
									msg.setMtUrl(te_mmsMtUrl);
									messageList.add(msg);
									cm_mmsPhoneList.clear();
								}
							} else if (ValidateOperators.isChinaUnicom(phoneNumber)) {
								uc_mmsPhoneList.add(phoneNumber);
								if (uc_mmsPhoneList.size() == uc_mmsMultiNum) {
									MT_MMMessage msg = new MT_MMMessage();
									msg.setServiceId(vasservice.getUniqueid().intValue());
									if (uc_mmsMultiNum > 1) {
										msg.setGroupSend(true);
									}
									msg.setOperatorsType(3);
									msg.setMtTranId(streamingNum);
									msg.setSendAddress(vasservice.getVasid());
									msg.setServiceCode(vasservice.getServicecode());
									msg.setVasId(vasservice.getVasid());
									msg.setVaspId(vasservice.getVaspid());
									msg.setTimeStamp(dateString);
									msg.setSubject(contentInfo.getContentname());
									msg.setContentid(contentInfo.getContentid());
									msg.setMmFile(mmFile);
									msg.setRcvAddresses((String[]) uc_mmsPhoneList.toArray(new String[uc_mmsMultiNum]));
									msg.setMtUrl(uc_mmsMtUrl);
									messageList.add(msg);
									cm_mmsPhoneList.clear();
								}
							}
						}
					}
				}

				if (cm_mmsPhoneList.size() > 0) {
					log.debug("没有达到群发因子数量!");
					log.debug("mmsPhoneList.size()======" + cm_mmsPhoneList.size());
					MT_MMMessage msg = new MT_MMMessage();
					msg.setServiceId(vasservice.getUniqueid().intValue());
					if (cm_mmsMultiNum > 1) {
						msg.setGroupSend(true);
					}
					msg.setOperatorsType(1);
					msg.setMtTranId(streamingNum);
					msg.setSendAddress(vasservice.getVasid());
					msg.setServiceCode(vasservice.getServicecode());
					msg.setVasId(vasservice.getVasid());
					msg.setVaspId(vasservice.getVaspid());
					msg.setTimeStamp(dateString);
					msg.setSubject(contentInfo.getContentname());
					msg.setContentid(contentInfo.getContentid());
					msg.setMmFile(mmFile);
					msg.setMtUrl(cm_mmsMtUrl);
					String[] address = (String[]) cm_mmsPhoneList.toArray(new String[cm_mmsPhoneList.size()]);
					msg.setRcvAddresses(address);
					mmsPhoneList.clear();
					messageList.add(msg);
				}
				if (te_mmsPhoneList.size() > 0) {
					log.debug("没有达到群发因子数量!");
					log.debug("mmsPhoneList.size()======" + te_mmsPhoneList.size());
					MT_MMMessage msg = new MT_MMMessage();
					msg.setServiceId(vasservice.getUniqueid().intValue());
					if (te_mmsMultiNum > 1) {
						msg.setGroupSend(true);
					}
					msg.setOperatorsType(2);
					msg.setMtTranId(streamingNum);
					msg.setSendAddress(vasservice.getVasid());
					msg.setServiceCode(vasservice.getServicecode());
					msg.setVasId(vasservice.getVasid());
					msg.setVaspId(vasservice.getVaspid());
					msg.setTimeStamp(dateString);
					msg.setSubject(contentInfo.getContentname());
					msg.setContentid(contentInfo.getContentid());
					msg.setMmFile(mmFile);
					msg.setMtUrl(te_mmsMtUrl);
					String[] address = (String[]) te_mmsPhoneList.toArray(new String[te_mmsPhoneList.size()]);
					msg.setRcvAddresses(address);
					mmsPhoneList.clear();
					messageList.add(msg);
				}
				if (uc_mmsPhoneList.size() > 0) {
					log.debug("没有达到群发因子数量!");
					log.debug("mmsPhoneList.size()======" + uc_mmsPhoneList.size());
					MT_MMMessage msg = new MT_MMMessage();
					msg.setServiceId(vasservice.getUniqueid().intValue());
					if (uc_mmsMultiNum > 1) {
						msg.setGroupSend(true);
					}
					msg.setOperatorsType(3);
					msg.setMtTranId(streamingNum);
					msg.setSendAddress(vasservice.getVasid());
					msg.setServiceCode(vasservice.getServicecode());
					msg.setVasId(vasservice.getVasid());
					msg.setVaspId(vasservice.getVaspid());
					msg.setTimeStamp(dateString);
					msg.setSubject(contentInfo.getContentname());
					msg.setContentid(contentInfo.getContentid());
					msg.setMmFile(mmFile);
					msg.setMtUrl(uc_mmsMtUrl);
					String[] address = (String[]) uc_mmsPhoneList.toArray(new String[uc_mmsPhoneList.size()]);
					msg.setRcvAddresses(address);
					mmsPhoneList.clear();
					messageList.add(msg);
				}
				if (mmePhoneList.size() > 0) {
					MT_MEMessage msg = new MT_MEMessage();
					msg.setServiceId(vasservice.getUniqueid().intValue());
					if (te_mmeMultiNum > 1) {
						msg.setGroupSend(true);
					}
					msg.setMtTranId(streamingNum);
					msg.setSendAddress(vasservice.getVasid());
					msg.setServiceCode(vasservice.getServicecode());
					msg.setVasId(vasservice.getVasid());
					msg.setVaspId(vasservice.getVaspid());
					msg.setTimeStamp(dateString);
					msg.setSubject(contentInfo.getContentname());
					msg.setContentid(contentInfo.getContentid());
					msg.setProvinceCode(area);
					msg.setMmFile(mmFile);
					msg.setRcvAddresses((String[]) mmePhoneList.toArray(new String[mmePhoneList.size()]));
					messageList.add(msg);
					mmePhoneList.clear();
				}
			} else {
				while (userList.size() > 0) {
//					UserInfo userInfo;
//					String phoneNumber;
					String terminaltype;
//					ContentInfoAdapter temp1;
//					ContentInfoRelation temp2;
//					ContentInfoRelation temp3;
//					ContentInfoRelation temp3;
//					MT_MEMessage msg;
//					MT_MMMessage msg;
//					MT_MMMessage msg;
//					MT_MMMessage msg;
//					MT_MEMessage msg;
//					MT_MMMessage msg;
//					MT_MMMessage msg;
//					MT_MMMessage msg;
//					MT_MEMessage msg;
//					MT_MMMessage msg;
//					MT_MMMessage msg;
//					MT_MMMessage msg;
//					MT_MEMessage msg;
//					MT_MMMessage msg;
//					MT_MMMessage msg;
//					MT_MMMessage msg;
//					MT_MMMessage msg;
//					String[] address;
//					MT_MMMessage msg;
//					String[] address;
//					MT_MMMessage msg;
//					String[] address;
//					MT_MEMessage msg;
					UserInfo userInfo = (UserInfo) userList.getFirst();
					userList.removeFirst();
					String phoneNumber = userInfo.getCellphonenumber();
					if (isAdapter) {
						log.debug("需要适配!");

						ContentInfoAdapter temp1 = null;
						ContentInfoRelation temp2 = null;
						if ((userInfo.getTerminaltype() != null) && (contentInfo.getAdapterContentMap() != null)) {
							temp1 = (ContentInfoAdapter) contentInfo.getAdapterContentMap().get(Integer.valueOf(userInfo.getTerminaltype()));
						}
						if (temp1 != null) {
							if ((temp1.getProvinceContentMap() != null) && (area != null)) {
								temp2 = (ContentInfoRelation) temp1.getProvinceContentMap().get(area);
							}
							if (temp2 != null)
								mmFile = temp2.getContentpath();
							else {
								mmFile = temp1.getContentpath();
							}
						} else if (isNational) {
							if ((area != null) && (contentInfo.getProvinceContent() != null)) {
								ContentInfoRelation temp3 = (ContentInfoRelation) contentInfo.getProvinceContent().get(area);
								if (temp3 != null)
									mmFile = temp3.getContentpath();
								else
									mmFile = contentInfo.getContentpath();
							} else {
								mmFile = contentInfo.getContentpath();
							}
						} else if ((area != null) && (contentInfo.getProvinceContent() != null)) {
							ContentInfoRelation temp3 = (ContentInfoRelation) contentInfo.getProvinceContent().get(area);
							if (temp3 != null)
								mmFile = temp3.getContentpath();
							else
								mmFile = contentInfo.getContentpath();
						} else {
							mmFile = contentInfo.getContentpath();
						}

						if (ValidateOperators.isChinaMobile(phoneNumber)) {
							MT_MMMessage msg = new MT_MMMessage();
							msg.setServiceId(vasservice.getUniqueid().intValue());
							msg.setMtTranId(streamingNum);
							msg.setSendAddress(vasservice.getVasid());
							msg.setServiceCode(vasservice.getServicecode());
							msg.setVasId(vasservice.getVasid());
							msg.setVaspId(vasservice.getVaspid());
							msg.setTimeStamp(dateString);
							msg.setSubject(contentInfo.getContentname());
							msg.setContentid(contentInfo.getContentid());
							msg.setMmFile(mmFile);
							msg.setRcvAddresses(new String[] { phoneNumber });
							msg.setMtUrl(cm_mmsMtUrl);
							msg.setOperatorsType(1);
							messageList.add(msg);
						} else if (ValidateOperators.isChinaTelecom(phoneNumber)) {
							MT_MMMessage msg = new MT_MMMessage();
							msg.setServiceId(vasservice.getUniqueid().intValue());
							msg.setMtTranId(streamingNum);
							msg.setSendAddress(vasservice.getVasid());
							msg.setServiceCode(vasservice.getServicecode());
							msg.setVasId(vasservice.getVasid());
							msg.setVaspId(vasservice.getVaspid());
							msg.setTimeStamp(dateString);
							msg.setSubject(contentInfo.getContentname());
							msg.setContentid(contentInfo.getContentid());
							msg.setMmFile(mmFile);
							msg.setRcvAddresses(new String[] { phoneNumber });
							msg.setMtUrl(te_mmsMtUrl);
							msg.setOperatorsType(2);
							messageList.add(msg);
						} else if (ValidateOperators.isChinaUnicom(phoneNumber)) {
							MT_MMMessage msg = new MT_MMMessage();
							msg.setServiceId(vasservice.getUniqueid().intValue());
							msg.setMtTranId(streamingNum);
							msg.setSendAddress(vasservice.getVasid());
							msg.setServiceCode(vasservice.getServicecode());
							msg.setVasId(vasservice.getVasid());
							msg.setVaspId(vasservice.getVaspid());
							msg.setTimeStamp(dateString);
							msg.setSubject(contentInfo.getContentname());
							msg.setContentid(contentInfo.getContentid());
							msg.setMmFile(mmFile);
							msg.setRcvAddresses(new String[] { phoneNumber });
							msg.setMtUrl(uc_mmsMtUrl);
							msg.setOperatorsType(3);
							messageList.add(msg);
						}

					} else if (ValidateOperators.isChinaMobile(phoneNumber)) {
						cm_mmsPhoneList.add(phoneNumber);
						if (cm_mmsPhoneList.size() == cm_mmsMultiNum) {
							MT_MMMessage msg = new MT_MMMessage();
							msg.setServiceId(vasservice.getUniqueid().intValue());
							if (cm_mmsMultiNum > 1) {
								msg.setGroupSend(true);
							}
							msg.setOperatorsType(1);
							msg.setMtTranId(streamingNum);
							msg.setSendAddress(vasservice.getVasid());
							msg.setServiceCode(vasservice.getServicecode());
							msg.setVasId(vasservice.getVasid());
							msg.setVaspId(vasservice.getVaspid());
							msg.setTimeStamp(dateString);
							msg.setSubject(contentInfo.getContentname());
							msg.setContentid(contentInfo.getContentid());
							msg.setMmFile(mmFile);
							msg.setRcvAddresses((String[]) cm_mmsPhoneList.toArray(new String[cm_mmsMultiNum]));
							msg.setMtUrl(cm_mmsMtUrl);
							messageList.add(msg);
							cm_mmsPhoneList.clear();
						}
					} else if (ValidateOperators.isChinaTelecom(phoneNumber)) {
						te_mmsPhoneList.add(phoneNumber);
						if (te_mmsPhoneList.size() == te_mmsMultiNum) {
							MT_MMMessage msg = new MT_MMMessage();
							msg.setServiceId(vasservice.getUniqueid().intValue());
							if (te_mmsMultiNum > 1) {
								msg.setGroupSend(true);
							}
							msg.setOperatorsType(2);
							msg.setMtTranId(streamingNum);
							msg.setSendAddress(vasservice.getVasid());
							msg.setServiceCode(vasservice.getServicecode());
							msg.setVasId(vasservice.getVasid());
							msg.setVaspId(vasservice.getVaspid());
							msg.setTimeStamp(dateString);
							msg.setSubject(contentInfo.getContentname());
							msg.setContentid(contentInfo.getContentid());
							msg.setMmFile(mmFile);
							msg.setRcvAddresses((String[]) te_mmsPhoneList.toArray(new String[te_mmsMultiNum]));
							msg.setMtUrl(te_mmsMtUrl);
							messageList.add(msg);
							cm_mmsPhoneList.clear();
						}
					} else if (ValidateOperators.isChinaUnicom(phoneNumber)) {
						uc_mmsPhoneList.add(phoneNumber);
						if (uc_mmsPhoneList.size() == uc_mmsMultiNum) {
							MT_MMMessage msg = new MT_MMMessage();
							msg.setServiceId(vasservice.getUniqueid().intValue());
							if (uc_mmsMultiNum > 1) {
								msg.setGroupSend(true);
							}
							msg.setOperatorsType(3);
							msg.setMtTranId(streamingNum);
							msg.setSendAddress(vasservice.getVasid());
							msg.setServiceCode(vasservice.getServicecode());
							msg.setVasId(vasservice.getVasid());
							msg.setVaspId(vasservice.getVaspid());
							msg.setTimeStamp(dateString);
							msg.setSubject(contentInfo.getContentname());
							msg.setContentid(contentInfo.getContentid());
							msg.setMmFile(mmFile);
							msg.setRcvAddresses((String[]) uc_mmsPhoneList.toArray(new String[uc_mmsMultiNum]));
							msg.setMtUrl(uc_mmsMtUrl);
							messageList.add(msg);
							cm_mmsPhoneList.clear();
						}
					}
				}

				if (cm_mmsPhoneList.size() > 0) {
					log.debug("没有达到群发因子数量!");
					log.debug("mmsPhoneList.size()======" + cm_mmsPhoneList.size());
					MT_MMMessage msg = new MT_MMMessage();
					msg.setServiceId(vasservice.getUniqueid().intValue());
					if (cm_mmsMultiNum > 1) {
						msg.setGroupSend(true);
					}
					msg.setOperatorsType(1);
					msg.setMtTranId(streamingNum);
					msg.setSendAddress(vasservice.getVasid());
					msg.setServiceCode(vasservice.getServicecode());
					msg.setVasId(vasservice.getVasid());
					msg.setVaspId(vasservice.getVaspid());
					msg.setTimeStamp(dateString);
					msg.setSubject(contentInfo.getContentname());
					msg.setContentid(contentInfo.getContentid());
					msg.setMmFile(mmFile);
					msg.setMtUrl(cm_mmsMtUrl);
					String[] address = (String[]) cm_mmsPhoneList.toArray(new String[cm_mmsPhoneList.size()]);
					msg.setRcvAddresses(address);
					mmsPhoneList.clear();
					messageList.add(msg);
				}
				if (te_mmsPhoneList.size() > 0) {
					log.debug("没有达到群发因子数量!");
					log.debug("mmsPhoneList.size()======" + te_mmsPhoneList.size());
					MT_MMMessage msg = new MT_MMMessage();
					msg.setServiceId(vasservice.getUniqueid().intValue());
					if (te_mmsMultiNum > 1) {
						msg.setGroupSend(true);
					}
					msg.setOperatorsType(2);
					msg.setMtTranId(streamingNum);
					msg.setSendAddress(vasservice.getVasid());
					msg.setServiceCode(vasservice.getServicecode());
					msg.setVasId(vasservice.getVasid());
					msg.setVaspId(vasservice.getVaspid());
					msg.setTimeStamp(dateString);
					msg.setSubject(contentInfo.getContentname());
					msg.setContentid(contentInfo.getContentid());
					msg.setMmFile(mmFile);
					msg.setMtUrl(te_mmsMtUrl);
					String[] address = (String[]) te_mmsPhoneList.toArray(new String[te_mmsPhoneList.size()]);
					msg.setRcvAddresses(address);
					mmsPhoneList.clear();
					messageList.add(msg);
				}
				if (uc_mmsPhoneList.size() > 0) {
					log.debug("没有达到群发因子数量!");
					log.debug("mmsPhoneList.size()======" + uc_mmsPhoneList.size());
					MT_MMMessage msg = new MT_MMMessage();
					msg.setServiceId(vasservice.getUniqueid().intValue());
					if (uc_mmsMultiNum > 1) {
						msg.setGroupSend(true);
					}
					msg.setOperatorsType(3);
					msg.setMtTranId(streamingNum);
					msg.setSendAddress(vasservice.getVasid());
					msg.setServiceCode(vasservice.getServicecode());
					msg.setVasId(vasservice.getVasid());
					msg.setVaspId(vasservice.getVaspid());
					msg.setTimeStamp(dateString);
					msg.setSubject(contentInfo.getContentname());
					msg.setContentid(contentInfo.getContentid());
					msg.setMmFile(mmFile);
					msg.setMtUrl(uc_mmsMtUrl);
					String[] address = (String[]) uc_mmsPhoneList.toArray(new String[uc_mmsPhoneList.size()]);
					msg.setRcvAddresses(address);
					mmsPhoneList.clear();
					messageList.add(msg);
				}
			}

		}

		if (vasservice.getProducttype().equalsIgnoreCase("e")) {
			MT_MEMessage msg = new MT_MEMessage();
			msg.setServiceId(vasservice.getUniqueid().intValue());
			msg.setGroupSend(true);
			msg.setMtTranId(streamingNum);
			msg.setSendAddress(vasservice.getVasid());
			msg.setServiceCode(vasservice.getServicecode());
			msg.setVasId(vasservice.getVasid());
			msg.setVaspId(vasservice.getVaspid());
			msg.setTimeStamp(dateString);
			msg.setSubject(contentInfo.getContentname());
			msg.setContentid(contentInfo.getContentid());
			msg.setProvinceCode(area);

			if (isAdapter) {
				for (UserInfo userInfo : userInfoList) {
					ContentInfoAdapter temp1 = null;
					ContentInfoRelation temp2 = null;

					if ((userInfo.getTerminaltype() != null) && (contentInfo.getAdapterContentMap() != null)) {
						temp1 = (ContentInfoAdapter) contentInfo.getAdapterContentMap().get(Integer.valueOf(userInfo.getTerminaltype()));
					}

					if (temp1 != null) {
						if ((temp1.getProvinceContentMap() != null) && (userInfo.getProvincecode() != null)) {
							temp2 = (ContentInfoRelation) temp1.getProvinceContentMap().get(userInfo.getProvincecode());
						}
						if (temp2 != null)
							mmFile = temp2.getContentpath();
						else {
							mmFile = temp1.getContentpath();
						}

					} else if ((userInfo.getProvincecode() != null) && (contentInfo.getProvinceContent() != null)) {
						ContentInfoRelation temp3 = (ContentInfoRelation) contentInfo.getProvinceContent().get(userInfo.getProvincecode());
						if (temp3 != null)
							mmFile = temp3.getContentpath();
						else
							mmFile = contentInfo.getContentpath();
					} else {
						mmFile = contentInfo.getContentpath();
					}

					String fullMMFile = contentPath + mmFile;

					if (msg.getMmFile() == null) {
						phoneList.add(userInfo.getCellphonenumber());
						msg.setMmFile(mmFile);
					} else if (fullMMFile.equalsIgnoreCase(msg.getMmFile())) {
						phoneList.add(userInfo.getCellphonenumber());
					} else {
						int size = phoneList.size();
						if (size > 0) {
							String[] address = (String[]) phoneList.toArray(new String[size]);
							msg.setRcvAddresses(address);
							messageList.add(msg);
						}

						msg = new MT_MEMessage();
						msg.setServiceId(vasservice.getUniqueid().intValue());
						if (te_mmeMultiNum > 1) {
							msg.setGroupSend(true);
						}
						msg.setMtTranId(streamingNum);
						msg.setSendAddress(vasservice.getVasid());
						msg.setServiceCode(vasservice.getServicecode());
						msg.setVasId(vasservice.getVasid());
						msg.setVaspId(vasservice.getVaspid());
						msg.setTimeStamp(dateString);

						msg.setSubject(contentInfo.getContentname());
						msg.setContentid(contentInfo.getContentid());

						msg.setMmFile(mmFile);

						phoneList.clear();
						phoneList.add(userInfo.getCellphonenumber());
					}

				}

				int size = phoneList.size();
				if (size > 0) {
					String[] address = (String[]) phoneList.toArray(new String[size]);
					msg.setRcvAddresses(address);
					messageList.add(msg);
				}

			} else {
				if (area != null) {
					ContentInfoRelation tempContent = (ContentInfoRelation) contentInfo.getProvinceContent().get(area);
					if (tempContent != null) {
						mmFile = tempContent.getContentpath();
					} else
						mmFile = contentInfo.getContentpath();
				} else {
					mmFile = contentInfo.getContentpath();
				}
				msg.setMmFile(mmFile);
				LinkedList usersList = (LinkedList) userInfoList;
				while (usersList.size() > 0) {
					for (int i = 0; i < te_mmeMultiNum; i++)
						try {
							UserInfo userInfo = (UserInfo) usersList.getFirst();
							usersList.removeFirst();
							phoneList.add(userInfo.getCellphonenumber());
							if (usersList.size() != 0)
								;
						} catch (NoSuchElementException e) {
						}
					int size = phoneList.size();
					if (size > 0) {
						msg.setRcvAddresses((String[]) phoneList.toArray(new String[size]));
						messageList.add(msg);
						phoneList.clear();
						msg = new MT_MEMessage();
						msg.setServiceId(vasservice.getUniqueid().intValue());
						if (te_mmeMultiNum > 1) {
							msg.setGroupSend(true);
						}
						msg.setMtTranId(streamingNum);
						msg.setSendAddress(vasservice.getVasid());
						msg.setServiceCode(vasservice.getServicecode());
						msg.setVasId(vasservice.getVasid());
						msg.setVaspId(vasservice.getVaspid());
						msg.setTimeStamp(dateString);
						msg.setSubject(contentInfo.getContentname());
						msg.setContentid(contentInfo.getContentid());
						msg.setProvinceCode(area);
						msg.setMmFile(mmFile);
					}
				}
			}

		} else if (vasservice.getProducttype().equalsIgnoreCase("s")) {
			while (userList.size() > 0) {
				UserInfo userInfo = (UserInfo) userList.getFirst();
				userList.removeFirst();
				String phoneNumber = userInfo.getCellphonenumber();
				if (ValidateOperators.isChinaMobile(phoneNumber)) {
					cm_smsPhoneList.add(phoneNumber);
					if (cm_smsPhoneList.size() == cm_smsMultiNum) {
						MT_SMMessage msg = new MT_SMMessage();
						msg.setServiceId(vasservice.getUniqueid().intValue());
						msg.setRcvAddresses((String[]) cm_smsPhoneList.toArray(new String[cm_smsMultiNum]));
						if (cm_smsMultiNum > 1) {
							msg.setGroupSend(true);
						}
						msg.setOperatorsType(1);
						msg.setMtTranId(streamingNum);
						msg.setSendAddress(vasservice.getVasid());
						msg.setServiceCode(vasservice.getServicecode());
						msg.setVasId(vasservice.getVasid());
						msg.setVaspId(vasservice.getVaspid());
						msg.setTimeStamp(dateString);
						msg.setProvinceCode(area);
						msg.setContentid(contentInfo.getContentid());
						msg.setSmsText(contentInfo.getSmsText());
						msg.setMtUrl(cm_smsMtUrl);
						messageList.add(msg);
						cm_smsPhoneList.clear();
					}
				} else if (ValidateOperators.isChinaTelecom(phoneNumber)) {
					te_smsPhoneList.add(phoneNumber);
					if (te_smsPhoneList.size() == te_smsMultiNum) {
						MT_SMMessage msg = new MT_SMMessage();
						msg.setServiceId(vasservice.getUniqueid().intValue());
						msg.setRcvAddresses((String[]) te_smsPhoneList.toArray(new String[te_smsMultiNum]));
						if (te_smsMultiNum > 1) {
							msg.setGroupSend(true);
						}
						msg.setOperatorsType(2);
						msg.setMtTranId(streamingNum);
						msg.setSendAddress(vasservice.getVasid());
						msg.setServiceCode(vasservice.getServicecode());
						msg.setVasId(vasservice.getVasid());
						msg.setVaspId(vasservice.getVaspid());
						msg.setTimeStamp(dateString);
						msg.setProvinceCode(area);
						msg.setContentid(contentInfo.getContentid());
						msg.setSmsText(contentInfo.getSmsText());
						msg.setMtUrl(te_smsMtUrl);
						messageList.add(msg);
						te_smsPhoneList.clear();
					}
				} else if (ValidateOperators.isChinaUnicom(phoneNumber)) {
					uc_smsPhoneList.add(phoneNumber);
					if (uc_smsPhoneList.size() == uc_smsMultiNum) {
						MT_SMMessage msg = new MT_SMMessage();
						msg.setServiceId(vasservice.getUniqueid().intValue());
						msg.setRcvAddresses((String[]) uc_smsPhoneList.toArray(new String[uc_smsMultiNum]));
						if (te_smsMultiNum > 1) {
							msg.setGroupSend(true);
						}
						msg.setOperatorsType(3);
						msg.setMtTranId(streamingNum);
						msg.setSendAddress(vasservice.getVasid());
						msg.setServiceCode(vasservice.getServicecode());
						msg.setVasId(vasservice.getVasid());
						msg.setVaspId(vasservice.getVaspid());
						msg.setTimeStamp(dateString);
						msg.setProvinceCode(area);
						msg.setContentid(contentInfo.getContentid());
						msg.setSmsText(contentInfo.getSmsText());
						msg.setMtUrl(uc_smsMtUrl);
						messageList.add(msg);
						uc_smsPhoneList.clear();
					}
				}
			}
			if (cm_smsPhoneList.size() > 0) {
				log.debug("没有达到群发因子数量!");
				log.debug("mmsPhoneList.size()======" + cm_smsPhoneList.size());
				MT_SMMessage msg = new MT_SMMessage();
				msg.setServiceId(vasservice.getUniqueid().intValue());
				msg.setRcvAddresses((String[]) cm_smsPhoneList.toArray(new String[cm_smsPhoneList.size()]));
				if (cm_smsMultiNum > 1) {
					msg.setGroupSend(true);
				}
				msg.setOperatorsType(1);
				msg.setMtTranId(streamingNum);
				msg.setSendAddress(vasservice.getVasid());
				msg.setServiceCode(vasservice.getServicecode());
				msg.setVasId(vasservice.getVasid());
				msg.setVaspId(vasservice.getVaspid());
				msg.setTimeStamp(dateString);
				msg.setProvinceCode(area);
				msg.setContentid(contentInfo.getContentid());
				msg.setSmsText(contentInfo.getSmsText());
				msg.setMtUrl(cm_smsMtUrl);
				messageList.add(msg);
				cm_smsPhoneList.clear();
			}
			if (te_smsPhoneList.size() > 0) {
				log.debug("没有达到群发因子数量!");
				log.debug("mmsPhoneList.size()======" + te_smsPhoneList.size());
				MT_SMMessage msg = new MT_SMMessage();
				msg.setServiceId(vasservice.getUniqueid().intValue());
				msg.setRcvAddresses((String[]) te_smsPhoneList.toArray(new String[te_smsPhoneList.size()]));
				if (te_smsMultiNum > 1) {
					msg.setGroupSend(true);
				}
				msg.setOperatorsType(2);
				msg.setMtTranId(streamingNum);
				msg.setSendAddress(vasservice.getVasid());
				msg.setServiceCode(vasservice.getServicecode());
				msg.setVasId(vasservice.getVasid());
				msg.setVaspId(vasservice.getVaspid());
				msg.setTimeStamp(dateString);
				msg.setProvinceCode(area);
				msg.setContentid(contentInfo.getContentid());
				msg.setSmsText(contentInfo.getSmsText());
				msg.setMtUrl(te_smsMtUrl);
				messageList.add(msg);
				te_smsPhoneList.clear();
			}
			if (uc_smsPhoneList.size() > 0) {
				log.debug("没有达到群发因子数量!");
				log.debug("mmsPhoneList.size()======" + uc_smsPhoneList.size());
				MT_SMMessage msg = new MT_SMMessage();
				msg.setServiceId(vasservice.getUniqueid().intValue());
				msg.setRcvAddresses((String[]) uc_smsPhoneList.toArray(new String[uc_smsPhoneList.size()]));
				if (te_smsMultiNum > 1) {
					msg.setGroupSend(true);
				}
				msg.setOperatorsType(3);
				msg.setMtTranId(streamingNum);
				msg.setSendAddress(vasservice.getVasid());
				msg.setServiceCode(vasservice.getServicecode());
				msg.setVasId(vasservice.getVasid());
				msg.setVaspId(vasservice.getVaspid());
				msg.setTimeStamp(dateString);
				msg.setProvinceCode(area);
				msg.setContentid(contentInfo.getContentid());
				msg.setSmsText(contentInfo.getSmsText());
				msg.setMtUrl(uc_smsMtUrl);
				messageList.add(msg);
				uc_smsPhoneList.clear();
			}

		}

		if (vasservice.getProducttype().equalsIgnoreCase("p")) {
			while (userList.size() > 0) {
				UserInfo userInfo = (UserInfo) userList.getFirst();
				userList.removeFirst();
				String phoneNumber = userInfo.getCellphonenumber();
				if (ValidateOperators.isChinaMobile(phoneNumber)) {
					cm_wapPhoneList.add(phoneNumber);
					if (cm_wapPhoneList.size() == cm_wapMultiNum) {
						MT_WapPushMessage msg = new MT_WapPushMessage();
						msg.setServiceId(vasservice.getUniqueid().intValue());
						msg.setRcvAddresses((String[]) cm_wapPhoneList.toArray(new String[cm_wapMultiNum]));
						if (cm_wapMultiNum > 1) {
							msg.setGroupSend(true);
						}
						msg.setOperatorsType(1);
						msg.setMtTranId(streamingNum);
						msg.setSendAddress(vasservice.getVasid());
						msg.setServiceCode(vasservice.getServicecode());
						msg.setVasId(vasservice.getVasid());
						msg.setVaspId(vasservice.getVaspid());
						msg.setTimeStamp(dateString);
						msg.setProvinceCode(area);
						msg.setContentid(contentInfo.getContentid());
						msg.setPushText(contentInfo.getContentname());
						msg.setPushUrl(wappushURL + "?method=cDetail&cid=" + contentInfo.getContentid().toString());
						msg.setMtUrl(cm_wapMtUrl);
						messageList.add(msg);
						cm_wapPhoneList.clear();
					}
				} else if (ValidateOperators.isChinaTelecom(phoneNumber)) {
					te_wapPhoneList.add(phoneNumber);
					if (te_wapPhoneList.size() == te_wapMultiNum) {
						MT_WapPushMessage msg = new MT_WapPushMessage();
						msg.setServiceId(vasservice.getUniqueid().intValue());
						msg.setRcvAddresses((String[]) te_wapPhoneList.toArray(new String[te_wapMultiNum]));
						if (te_wapMultiNum > 1) {
							msg.setGroupSend(true);
						}
						msg.setOperatorsType(2);
						msg.setMtTranId(streamingNum);
						msg.setSendAddress(vasservice.getVasid());
						msg.setServiceCode(vasservice.getServicecode());
						msg.setVasId(vasservice.getVasid());
						msg.setVaspId(vasservice.getVaspid());
						msg.setTimeStamp(dateString);
						msg.setProvinceCode(area);
						msg.setContentid(contentInfo.getContentid());
						msg.setPushText(contentInfo.getContentname());
						msg.setPushUrl(wappushURL + "?method=cDetail&cid=" + contentInfo.getContentid().toString());
						msg.setMtUrl(te_wapMtUrl);
						messageList.add(msg);
						cm_wapPhoneList.clear();
					}
				} else if (ValidateOperators.isChinaUnicom(phoneNumber)) {
					uc_wapPhoneList.add(phoneNumber);
					if (uc_wapPhoneList.size() == uc_wapMultiNum) {
						MT_WapPushMessage msg = new MT_WapPushMessage();
						msg.setServiceId(vasservice.getUniqueid().intValue());
						msg.setRcvAddresses((String[]) uc_wapPhoneList.toArray(new String[uc_wapMultiNum]));
						if (uc_wapMultiNum > 1) {
							msg.setGroupSend(true);
						}
						msg.setOperatorsType(3);
						msg.setMtTranId(streamingNum);
						msg.setSendAddress(vasservice.getVasid());
						msg.setServiceCode(vasservice.getServicecode());
						msg.setVasId(vasservice.getVasid());
						msg.setVaspId(vasservice.getVaspid());
						msg.setTimeStamp(dateString);
						msg.setProvinceCode(area);
						msg.setContentid(contentInfo.getContentid());
						msg.setPushText(contentInfo.getContentname());
						msg.setPushUrl(wappushURL + "?method=cDetail&cid=" + contentInfo.getContentid().toString());
						msg.setMtUrl(uc_wapMtUrl);
						messageList.add(msg);
						cm_wapPhoneList.clear();
					}
				}
			}
			if (cm_wapPhoneList.size() > 0) {
				log.debug("没有达到群发因子数量!");
				log.debug("mmsPhoneList.size()======" + cm_wapPhoneList.size());
				MT_WapPushMessage msg = new MT_WapPushMessage();
				msg.setServiceId(vasservice.getUniqueid().intValue());
				msg.setRcvAddresses((String[]) cm_wapPhoneList.toArray(new String[cm_wapPhoneList.size()]));
				if (cm_wapMultiNum > 1) {
					msg.setGroupSend(true);
				}
				msg.setOperatorsType(1);
				msg.setMtTranId(streamingNum);
				msg.setSendAddress(vasservice.getVasid());
				msg.setServiceCode(vasservice.getServicecode());
				msg.setVasId(vasservice.getVasid());
				msg.setVaspId(vasservice.getVaspid());
				msg.setTimeStamp(dateString);
				msg.setProvinceCode(area);
				msg.setContentid(contentInfo.getContentid());
				msg.setPushText(contentInfo.getContentname());
				msg.setPushUrl(wappushURL + "?method=cDetail&cid=" + contentInfo.getContentid().toString());
				msg.setMtUrl(cm_wapMtUrl);
				messageList.add(msg);
				cm_wapPhoneList.clear();
			}
			if (te_wapPhoneList.size() > 0) {
				log.debug("没有达到群发因子数量!");
				log.debug("mmsPhoneList.size()======" + te_wapPhoneList.size());
				MT_WapPushMessage msg = new MT_WapPushMessage();
				msg.setServiceId(vasservice.getUniqueid().intValue());
				msg.setRcvAddresses((String[]) te_wapPhoneList.toArray(new String[te_wapPhoneList.size()]));
				if (te_wapMultiNum > 1) {
					msg.setGroupSend(true);
				}
				msg.setOperatorsType(2);
				msg.setMtTranId(streamingNum);
				msg.setSendAddress(vasservice.getVasid());
				msg.setServiceCode(vasservice.getServicecode());
				msg.setVasId(vasservice.getVasid());
				msg.setVaspId(vasservice.getVaspid());
				msg.setTimeStamp(dateString);
				msg.setProvinceCode(area);
				msg.setContentid(contentInfo.getContentid());
				msg.setPushText(contentInfo.getContentname());
				msg.setPushUrl(wappushURL + "?method=cDetail&cid=" + contentInfo.getContentid().toString());
				msg.setMtUrl(te_wapMtUrl);
				messageList.add(msg);
				cm_wapPhoneList.clear();
			}
			if (uc_wapPhoneList.size() > 0) {
				log.debug("没有达到群发因子数量!");
				log.debug("mmsPhoneList.size()======" + uc_wapPhoneList.size());
				MT_WapPushMessage msg = new MT_WapPushMessage();
				msg.setServiceId(vasservice.getUniqueid().intValue());
				msg.setRcvAddresses((String[]) uc_wapPhoneList.toArray(new String[uc_wapPhoneList.size()]));
				if (uc_wapMultiNum > 1) {
					msg.setGroupSend(true);
				}
				msg.setOperatorsType(3);
				msg.setMtTranId(streamingNum);
				msg.setSendAddress(vasservice.getVasid());
				msg.setServiceCode(vasservice.getServicecode());
				msg.setVasId(vasservice.getVasid());
				msg.setVaspId(vasservice.getVaspid());
				msg.setTimeStamp(dateString);
				msg.setProvinceCode(area);
				msg.setContentid(contentInfo.getContentid());
				msg.setPushText(contentInfo.getContentname());
				msg.setPushUrl(wappushURL + "?method=cDetail&cid=" + contentInfo.getContentid().toString());
				msg.setMtUrl(uc_wapMtUrl);
				messageList.add(msg);
				cm_wapPhoneList.clear();
			}

		}

		return messageList;
	}

	public static String generateStreamingNum(String serviceID) {
		return serviceID + DateString.getTime();
	}
}

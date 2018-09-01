 package com.zbensoft.mmsmp.vas.sjb.controller;
 
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ServMtMode;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ServMtModeId;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.SysChannels;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.SysParameters;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.UserBlackWhiteList;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.Vas;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasp;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
 import com.zbensoft.mmsmp.vas.sjb.data.BusinessManageDAO;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.log4j.Logger;
 
 public class DataCenter
 {
   private static final Logger log = Logger.getLogger(DataCenter.class);
   private Map<Integer, Vasservice> serviceMapById = new HashMap();
   private Map<String, Vasp> spMapById = new HashMap();
   private Map<String, UserBlackWhiteList> whiteUserMapById = new HashMap();
   private Map<String, Vas> vasMapById = new HashMap();
   private Map<ServMtModeId, ServMtMode> servMtModeMap = new HashMap();
   private Map<String, String> sysParamsMap = new HashMap();
   private BusinessManageDAO businessManageDAO;
   private Map<String, SysChannels> sysChannelMap = new HashMap();
   
   public void refreshVASService() {
     List<Vasservice> servList = this.businessManageDAO.findNormalStatus();
     Vasservice serv = null;
     Map<Integer, Vasservice> serviceMapById = new HashMap();
     for (int i = 0; i < servList.size(); i++) {
       serv = (Vasservice)servList.get(i);
       serviceMapById.put(serv.getUniqueid(), serv);
     }
     this.serviceMapById = serviceMapById;
   }
   
 
 
   public void refreshSP()
   {
     List<Vasp> servList = this.businessManageDAO.findSpInfo();
     Vasp vasp = null;
     Map<String, Vasp> spMapById = new HashMap();
     for (int i = 0; i < servList.size(); i++) {
       vasp = (Vasp)servList.get(i);
       spMapById.put(vasp.getVaspid(), vasp);
     }
     this.spMapById = spMapById;
   }
   
   public void refreshWhiteUser() {
     List<UserBlackWhiteList> userList = this.businessManageDAO.findWhiteUserInfo();
     UserBlackWhiteList user = null;
     Map<String, UserBlackWhiteList> whiteUserMapById = new HashMap();
     for (int i = 0; i < userList.size(); i++) {
       user = (UserBlackWhiteList)userList.get(i);
       whiteUserMapById.put(user.getProductId() + user.getCellphoneNumber(), user);
     }
     this.whiteUserMapById = whiteUserMapById;
   }
   
 
 
   public void refreshVas()
   {
     List<Vas> vasList = this.businessManageDAO.findVasInfo();
     Vas vas = null;
     Map<String, Vas> vasMapById = new HashMap();
     for (int i = 0; i < vasList.size(); i++) {
       vas = (Vas)vasList.get(i);
       vasMapById.put(vas.getVasid(), vas);
     }
     this.vasMapById = vasMapById;
   }
   
 
   public void refreshServMTMode()
   {
     Map<ServMtModeId, ServMtMode> servMtModeMap = new HashMap();
     List<ServMtMode> servMtModes = this.businessManageDAO.findAllServMod();
     ServMtMode servMtMode = null;
     for (int i = 0; i < servMtModes.size(); i++) {
       servMtMode = (ServMtMode)servMtModes.get(i);
       servMtModeMap.put(servMtMode.getId(), servMtMode);
     }
     this.servMtModeMap = servMtModeMap;
   }
   
 
 
 
 
 
   public DataCenter(BusinessManageDAO businessManageDAO)
   {
     this.businessManageDAO = businessManageDAO;
     
 
     refreshVASService();
     
     refreshSysParams();
     
     refreshSP();
     
     refreshVas();
     
     refreshWhiteUser();
   }
   
 
 
 
 
 
 
   public Vasservice findService(Integer id)
   {
     return (Vasservice)this.serviceMapById.get(id);
   }
   
   public ServMtMode findServMtMode(ServMtModeId servMtModeId) { return (ServMtMode)this.servMtModeMap.get(servMtModeId); }
   
 
 
 
 
 
 
 
 
 
   public void refreshAll()
   {
     refreshVASService();
     
     refreshSysParams();
     
     refreshSP();
     
     refreshVas();
     
     refreshWhiteUser();
   }
   
 
 
 
 
   public void refreshSysParams()
   {
     Map<String, String> sysParamsMap = new HashMap();
     List<SysParameters> sysParams = this.businessManageDAO.findAllSysParams();
     SysParameters sysParam = null;
     for (int i = 0; i < sysParams.size(); i++) {
       sysParam = (SysParameters)sysParams.get(i);
       sysParamsMap.put(sysParam.getName(), sysParam.getValue());
     }
     this.sysParamsMap = sysParamsMap;
   }
   
 
 
 
   public void refreshSysChannel()
   {
     Map<String, SysChannels> sysChannelMap = new HashMap();
     String area = (String)this.sysParamsMap.get("PLATFORM_AREA");
     List<SysChannels> sysChannel = this.businessManageDAO.findAllChannel(area.split("@")[0]);
     SysChannels sysChan = null;
     for (int i = 0; i < sysChannel.size(); i++) {
       sysChan = (SysChannels)sysChannel.get(i);
       
 
 
 
 
       sysChannelMap.put(sysChan.getOperators() + sysChan.getChannelType(), sysChan);
     }
     this.sysChannelMap = sysChannelMap;
   }
   
 
   public Map<Integer, Vasservice> getServiceMapById()
   {
     return this.serviceMapById;
   }
   
   public void setServiceMapById(Map<Integer, Vasservice> serviceMapById) { this.serviceMapById = serviceMapById; }
   
   public Map<ServMtModeId, ServMtMode> getServMtModeMap() {
     return this.servMtModeMap;
   }
   
   public void setServMtModeMap(Map<ServMtModeId, ServMtMode> servMtModeMap) { this.servMtModeMap = servMtModeMap; }
   
 
 
   public Map<String, String> getSysParamsMap()
   {
     return this.sysParamsMap;
   }
   
 
 
   public void setSysParamsMap(Map<String, String> sysParamsMap)
   {
     this.sysParamsMap = sysParamsMap;
   }
   
 
   public Map<String, SysChannels> getSysChannelMap()
   {
     return this.sysChannelMap;
   }
   
 
 
   public void setSysChannelMap(Map<String, SysChannels> sysChannelMap)
   {
     this.sysChannelMap = sysChannelMap;
   }
   
   public Map<String, Vasp> getSpMapById() {
     return this.spMapById;
   }
   
   public void setSpMapById(Map<String, Vasp> spMapById) {
     this.spMapById = spMapById;
   }
   
   public Map<String, Vas> getVasMapById() {
     return this.vasMapById;
   }
   
   public void setVasMapById(Map<String, Vas> vasMapById) {
     this.vasMapById = vasMapById;
   }
   
   public Map<String, UserBlackWhiteList> getWhiteUserMapById() {
     return this.whiteUserMapById;
   }
   
   public void setWhiteUserMapById(Map<String, UserBlackWhiteList> whiteUserMapById) {
     this.whiteUserMapById = whiteUserMapById;
   }
 }






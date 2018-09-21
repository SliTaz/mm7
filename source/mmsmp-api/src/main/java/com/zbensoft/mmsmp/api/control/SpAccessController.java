package com.zbensoft.mmsmp.api.control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.zbensoft.mmsmp.api.common.*;
import com.zbensoft.mmsmp.common.util.DateUtil;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.pagehelper.PageHelper;
import com.zbensoft.mmsmp.api.service.api.ProvinceCityService;
import com.zbensoft.mmsmp.api.service.api.SpAccessService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.db.domain.ProvinceCity;
import com.zbensoft.mmsmp.db.domain.SpAccess;
import com.zbensoft.mmsmp.db.domain.SpInfo;

import io.swagger.annotations.ApiOperation;


@RequestMapping(value = "/spAccess")
@RestController
public class SpAccessController {
    @Autowired
    SpAccessService spAccessService;
    @Autowired
    SpInfoService spInfoService;
    @Autowired
    ProvinceCityService provinceCityService;

    @Resource
    private LocaleMessageSourceService localeMessageSourceService;

    //查询通知，支持分页
    @PreAuthorize("hasRole('R_SPA_Q')")
    @ApiOperation(value = "Query SpAccess，Support paging", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseRestEntity<List<SpAccess>> selectPage(
            @RequestParam(required = false) String id, @RequestParam(required = false) String spAccessNumberExtend, @RequestParam(required = false) String spAccessNumber,
            @RequestParam(required = false) String companyName, @RequestParam(required = false) String companyCode,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length) {
        SpAccess SpAccess = new SpAccess();

        if ((companyCode == null || "".equals(companyCode)) && (companyName == null || "".equals(companyName))) {
            SpAccess.setSpInfoId(id);
            SpAccess.setSpAccessNumberExtend(spAccessNumberExtend);
            SpAccess.setSpAccessNumber(spAccessNumber);
            List<SpAccess> list = new ArrayList<SpAccess>();
            // 分页 start
            if (start != null && length != null) {// 需要进行分页
                /*
                 * 第一个参数是第几页；第二个参数是每页显示条数。
                 */
                int pageNum = PageHelperUtil.getPageNum(start, length);
                int pageSize = PageHelperUtil.getPageSize(start, length);
                PageHelper.startPage(pageNum, pageSize);
                list = spAccessService.selectPage(SpAccess);

            } else {
                list = spAccessService.selectPage(SpAccess);
            }

            int count = spAccessService.count(SpAccess);
            // 分页 end

            if (list == null || list.isEmpty()) {
                return new ResponseRestEntity<List<SpAccess>>(new ArrayList<SpAccess>(), HttpRestStatus.NOT_FOUND);
            }

            List<SpAccess> listNew = new ArrayList<SpAccess>();
            for (SpAccess bean : list) {
                SpInfo bankInfo = spInfoService.selectByPrimaryKey(bean.getSpInfoId());
                if (bankInfo != null) {
                    bean.setCompanyCode(bankInfo.getCompanyCode());
                    bean.setCompanyName(bankInfo.getCompanyName());
                }
                ProvinceCity provinceCity = provinceCityService.selectByPrimaryKey(bean.getSpCity());
                if (provinceCity != null) {
                    bean.setProvinceCityName(provinceCity.getProvinceCityName());
                }
                listNew.add(bean);
            }

            return new ResponseRestEntity<List<SpAccess>>(listNew, HttpRestStatus.OK, count, count);
        } else {
            SpInfo spInfo = new SpInfo();
            spInfo.setCompanyCode(companyCode);
            spInfo.setCompanyName(companyName);
            List<SpInfo> spInfoList = spInfoService.selectPage(spInfo);
            if (spInfoList.size() == 0) {
                return new ResponseRestEntity<List<SpAccess>>(new ArrayList<SpAccess>(), HttpRestStatus.NOT_FOUND);
            } else {
                List<SpAccess> listNews = new ArrayList<SpAccess>();
                int count = 0;
                for (SpInfo spInfo2 : spInfoList) {
                    SpAccess.setSpInfoId(spInfo2.getSpInfoId());
                    SpAccess.setSpAccessNumberExtend(spAccessNumberExtend);
                    SpAccess.setSpAccessNumber(spAccessNumber);
                    List<SpAccess> list = new ArrayList<SpAccess>();
                    // 分页 start
                    if (start != null && length != null) {// 需要进行分页
                        /*
                         * 第一个参数是第几页；第二个参数是每页显示条数。
                         */
                        int pageNum = PageHelperUtil.getPageNum(start, length);
                        int pageSize = PageHelperUtil.getPageSize(start, length);
                        PageHelper.startPage(pageNum, pageSize);
                        list = spAccessService.selectPage(SpAccess);

                    } else {
                        list = spAccessService.selectPage(SpAccess);
                    }

                    count = spAccessService.count(SpAccess);
                    // 分页 end

                    List<SpAccess> listNew = new ArrayList<SpAccess>();
                    for (SpAccess bean : list) {
                        SpInfo bankInfo = spInfoService.selectByPrimaryKey(bean.getSpInfoId());
                        if (bankInfo != null) {
                            bean.setCompanyCode(bankInfo.getCompanyCode());
                            bean.setCompanyName(bankInfo.getCompanyName());
                        }
                        ProvinceCity provinceCity = provinceCityService.selectByPrimaryKey(bean.getSpCity());
                        if (provinceCity != null) {
                            bean.setProvinceCityName(provinceCity.getProvinceCityName());
                        }
                        listNew.add(bean);
                    }
                    listNews.addAll(listNew);
                }
                return new ResponseRestEntity<List<SpAccess>>(listNews, HttpRestStatus.OK, count, count);
            }
        }
    }

    //查询通知
    @PreAuthorize("hasRole('R_SPA_Q')")
    @ApiOperation(value = "Query SpAccess", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseRestEntity<SpAccess> selectByPrimaryKey(@PathVariable("id") String id) {
        SpAccess spAccess = spAccessService.selectByPrimaryKey(id);
        if (spAccess == null) {
            return new ResponseRestEntity<SpAccess>(HttpRestStatus.NOT_FOUND);
        }

        return new ResponseRestEntity<SpAccess>(spAccess, HttpRestStatus.OK);
    }

    //新增通知
    @PreAuthorize("hasRole('R_SPA_E')")
    @ApiOperation(value = "Add SpAccess", notes = "")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseRestEntity<Void> create(@Valid @RequestBody SpAccess SpAccess, BindingResult result, UriComponentsBuilder ucBuilder) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
                    HttpRestStatusFactory.createStatusMessage(list));
        }

        SpAccess bean = spAccessService.selectByPrimaryKey(SpAccess.getSpInfoId());
        if (bean != null) {
            return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT, localeMessageSourceService.getMessage("common.create.conflict.message"));
        }

        spAccessService.insert(SpAccess);
        //新增日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, SpAccess, CommonLogImpl.SP);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/SpAccess/{id}").buildAndExpand(SpAccess.getSpInfoId()).toUri());
        return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED, localeMessageSourceService.getMessage("common.create.created.message"));
    }

    //修改通知信息
    @PreAuthorize("hasRole('R_SPA_E')")
    @ApiOperation(value = "Edit SpAccess", notes = "")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseRestEntity<SpAccess> update(@PathVariable("id") String id, @Valid @RequestBody SpAccess spAccess, BindingResult result) {

        SpAccess currentSpAccess = spAccessService.selectByPrimaryKey(id);

        if (currentSpAccess == null) {
            return new ResponseRestEntity<SpAccess>(HttpRestStatus.NOT_FOUND, localeMessageSourceService.getMessage("common.update.not_found.message"));
        }

        currentSpAccess.setSpInfoId(id);
        currentSpAccess.setEffTime(spAccess.getEffTime());
        currentSpAccess.setExpTime(spAccess.getExpTime());
        currentSpAccess.setIsTrust(spAccess.getIsTrust());
        currentSpAccess.setOrderKey(spAccess.getOrderKey());
        currentSpAccess.setReportMessageUrl(spAccess.getReportMessageUrl());
        currentSpAccess.setSpAccessNumber(spAccess.getSpAccessNumber());
        currentSpAccess.setSpAccessNumberExtend(spAccess.getSpAccessNumberExtend());
        currentSpAccess.setSpCity(spAccess.getSpCity());
        currentSpAccess.setSpOrderNotifyUrl(spAccess.getSpOrderNotifyUrl());
        currentSpAccess.setSynOrderFunc(spAccess.getSynOrderFunc());
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();

            return new ResponseRestEntity<SpAccess>(currentSpAccess, HttpRestStatusFactory.createStatus(list), HttpRestStatusFactory.createStatusMessage(list));
        }

        spAccessService.updateByPrimaryKey(currentSpAccess);
        //修改日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentSpAccess, CommonLogImpl.SP);
        return new ResponseRestEntity<SpAccess>(currentSpAccess, HttpRestStatus.OK, localeMessageSourceService.getMessage("common.update.ok.message"));
    }


    //删除指定通知
    @PreAuthorize("hasRole('R_SPA_E')")
    @ApiOperation(value = "Delete SpAccess", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseRestEntity<SpAccess> delete(@PathVariable("id") String id) {

        SpAccess spAccess = spAccessService.selectByPrimaryKey(id);
        if (spAccess == null) {
            return new ResponseRestEntity<SpAccess>(HttpRestStatus.NOT_FOUND);
        }

        spAccessService.deleteByPrimaryKey(id);
        //删除日志开始
        SpAccess delBean = new SpAccess();
        delBean.setSpInfoId(id);
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean, CommonLogImpl.SP);
        //删除日志结束
        return new ResponseRestEntity<SpAccess>(HttpRestStatus.NO_CONTENT);
    }


    @ApiOperation(value = "Delete ProductInfo", notes = "")
    @RequestMapping(value = "/sysSaveOrUpdateAccess", method = RequestMethod.POST)
    public ResponseRestEntity<ProductInfo> saveOrUpdateAccess(@RequestParam String api_key, @RequestBody ServiceInfo serviceInfo) {

        if (!api_key.equals(MessageDef.FTP.SP_SYSC_API_KEY))
            return new ResponseRestEntity(HttpRestStatus.NO_AUTHORITY);

        if (1 == serviceInfo.updateType) {
            SpAccess spAccess = buildSpAccess(serviceInfo);
            spAccessService.insertSelective(spAccess);
        }

        if (2 == serviceInfo.updateType) {
            SpAccess spAccess = buildSpAccess(serviceInfo);
            spAccessService.updateByPrimaryKeySelective(spAccess);
        }

        return new ResponseRestEntity<ProductInfo>(HttpRestStatus.NO_CONTENT);
    }


    private SpAccess buildSpAccess(ServiceInfo serviceInfo) {
        SpAccess spAccess = new SpAccess();
        String accessNo = DataUtil.generateServiceAccessNumber(this.getSpExpandCode(serviceInfo.spID), this.getMaxServiceAccessNumber(serviceInfo.spID));
        spAccess.setSpInfoId(serviceInfo.spID);
        spAccess.setSpAccessNumber(accessNo);
        spAccess.setCompanyName(serviceInfo.serviceName);
        spAccess.setEffTime(DateUtil.convertStringToDate(serviceInfo.effDate));
        spAccess.setExpTime(DateUtil.convertStringToDate(serviceInfo.expDate));
        spAccess.setSynOrderFunc(serviceInfo.synOrderFunc);
        spAccess.setSpOrderNotifyUrl(serviceInfo.spOrderUrl);
        return spAccess;
    }

    private String getMaxServiceAccessNumber(String spid) {
        String maxAccess = spAccessService.getMaxServiceAccessNumber(spid);
        return maxAccess != null && !"".equals(maxAccess) ? maxAccess : "00";
    }

    private String getSpExpandCode(String spid) {
//        try {
//            String sql = "select sp_extend_num from vasp_reserve_info a, vasp b where a.spid = b.vaspid and b.vaspid = '" + spid + "' ";
//            List list = this.getJdbcTemplate().query(sql, new RowMapper() {
//                public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//                    return rs.getString("sp_extend_num");
//                }
//            });
//            if (list != null && list.size() != 0 && list.get(0) != null) {
//                return list.get(0).toString();
//            } else {
//                logger.info("sp_extend_num error：sp扩展码不存在！生成业务扩展码错误！");
//                return null;
//            }
//        } catch (DataAccessException var4) {
//            var4.printStackTrace();
//            return null;
//        }
        return "1065556500901";
    }

    static class ServiceInfo {
        public String recordSequenceID;
        public int updateType;
        public String spID;
        public String spName;
        public String serviceType;
        public int groupService;
        public String serviceCompose;
        public String serviceID;
        public String serviceName;
        public int serviceCredit;
        public int serviceStatus;
        public String introURL;
        public String wapintropic;
        public String servicecolumn;

        public String getRecordSequenceID() {
            return recordSequenceID;
        }

        public void setRecordSequenceID(String recordSequenceID) {
            this.recordSequenceID = recordSequenceID;
        }

        public int getUpdateType() {
            return updateType;
        }

        public void setUpdateType(int updateType) {
            this.updateType = updateType;
        }

        public String getSpID() {
            return spID;
        }

        public void setSpID(String spID) {
            this.spID = spID;
        }

        public String getSpName() {
            return spName;
        }

        public void setSpName(String spName) {
            this.spName = spName;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public int getGroupService() {
            return groupService;
        }

        public void setGroupService(int groupService) {
            this.groupService = groupService;
        }

        public String getServiceCompose() {
            return serviceCompose;
        }

        public void setServiceCompose(String serviceCompose) {
            this.serviceCompose = serviceCompose;
        }

        public String getServiceID() {
            return serviceID;
        }

        public void setServiceID(String serviceID) {
            this.serviceID = serviceID;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public int getServiceCredit() {
            return serviceCredit;
        }

        public void setServiceCredit(int serviceCredit) {
            this.serviceCredit = serviceCredit;
        }

        public int getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(int serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public String getIntroURL() {
            return introURL;
        }

        public void setIntroURL(String introURL) {
            this.introURL = introURL;
        }

        public String getWapintropic() {
            return wapintropic;
        }

        public void setWapintropic(String wapintropic) {
            this.wapintropic = wapintropic;
        }

        public String getServicecolumn() {
            return servicecolumn;
        }

        public void setServicecolumn(String servicecolumn) {
            this.servicecolumn = servicecolumn;
        }

        public String getEnterurl() {
            return enterurl;
        }

        public void setEnterurl(String enterurl) {
            this.enterurl = enterurl;
        }

        public String getConfirmurl() {
            return confirmurl;
        }

        public void setConfirmurl(String confirmurl) {
            this.confirmurl = confirmurl;
        }

        public String getPriceurl() {
            return priceurl;
        }

        public void setPriceurl(String priceurl) {
            this.priceurl = priceurl;
        }

        public String getFreeurl() {
            return freeurl;
        }

        public void setFreeurl(String freeurl) {
            this.freeurl = freeurl;
        }

        public int getNeedConfmBack() {
            return needConfmBack;
        }

        public void setNeedConfmBack(int needConfmBack) {
            this.needConfmBack = needConfmBack;
        }

        public int getCheckType() {
            return checkType;
        }

        public void setCheckType(int checkType) {
            this.checkType = checkType;
        }

        public String getEffDate() {
            return effDate;
        }

        public void setEffDate(String effDate) {
            this.effDate = effDate;
        }

        public String getExpDate() {
            return expDate;
        }

        public void setExpDate(String expDate) {
            this.expDate = expDate;
        }

        public int getwAPServiceType() {
            return wAPServiceType;
        }

        public void setwAPServiceType(int wAPServiceType) {
            this.wAPServiceType = wAPServiceType;
        }

        public String getSpOrderUrl() {
            return spOrderUrl;
        }

        public void setSpOrderUrl(String spOrderUrl) {
            this.spOrderUrl = spOrderUrl;
        }

        public int getSynOrderFunc() {
            return synOrderFunc;
        }

        public void setSynOrderFunc(int synOrderFunc) {
            this.synOrderFunc = synOrderFunc;
        }

        public int getSpPsedoFlag() {
            return spPsedoFlag;
        }

        public void setSpPsedoFlag(int spPsedoFlag) {
            this.spPsedoFlag = spPsedoFlag;
        }

        public String getResv1() {
            return resv1;
        }

        public void setResv1(String resv1) {
            this.resv1 = resv1;
        }

        public String getResv2() {
            return resv2;
        }

        public void setResv2(String resv2) {
            this.resv2 = resv2;
        }

        public String enterurl;
        public String confirmurl;
        public String priceurl;
        public String freeurl;
        public int needConfmBack;
        public int checkType;
        public String effDate;
        public String expDate;
        public int wAPServiceType;
        public String spOrderUrl;
        public int synOrderFunc;
        public int spPsedoFlag;
        public String resv1;
        public String resv2;
    }
}

package com.zbensoft.mmsmp.ftp.sysc.productInfoFtp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.zbensoft.mmsmp.ftp.util.SpFilter;
import org.apache.log4j.Logger;

public class ProductInfo {
    private static final Logger logger = Logger.getLogger(ProductInfo.class);
    public String recordSequenceID;
    public int updateType;
    public String productID;
    public String spec_ProductID;
    public String sp_ProductID;
    public String serviceID;
    public String spID;
    public int productStatus;
    public int productType;
    public String productName;
    public int confirm;
    public int presenter;
    public String confirmPrompt;
    public String successPrompt;
    public String cancelPrompt;
    public String popularizestart;
    public String popularizestop;
    public int freeUseFlag;
    public int freeUseTime;
    public String chargeDes;
    public String billingId;
    public String discount_des;
    public String discountId;
    public int needConfm;
    public int maxUseTimes;
    public int maxUseTime;
    public int sendnum;
    public String ordercommand;
    public int orderCmdmatch;
    public String orderAcc;
    public int orderAccmatch;
    public String cancelcommand;
    public int cancelCmdmatch;
    public String cancelAcc;
    public int cancelAccmatch;
    public String requestcmd;
    public int requestCmdmatch;

    public static Logger getLogger() {
        return logger;
    }

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

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getSpec_ProductID() {
        return spec_ProductID;
    }

    public void setSpec_ProductID(String spec_ProductID) {
        this.spec_ProductID = spec_ProductID;
    }

    public String getSp_ProductID() {
        return sp_ProductID;
    }

    public void setSp_ProductID(String sp_ProductID) {
        this.sp_ProductID = sp_ProductID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getSpID() {
        return spID;
    }

    public void setSpID(String spID) {
        this.spID = spID;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public int getPresenter() {
        return presenter;
    }

    public void setPresenter(int presenter) {
        this.presenter = presenter;
    }

    public String getConfirmPrompt() {
        return confirmPrompt;
    }

    public void setConfirmPrompt(String confirmPrompt) {
        this.confirmPrompt = confirmPrompt;
    }

    public String getSuccessPrompt() {
        return successPrompt;
    }

    public void setSuccessPrompt(String successPrompt) {
        this.successPrompt = successPrompt;
    }

    public String getCancelPrompt() {
        return cancelPrompt;
    }

    public void setCancelPrompt(String cancelPrompt) {
        this.cancelPrompt = cancelPrompt;
    }

    public String getPopularizestart() {
        return popularizestart;
    }

    public void setPopularizestart(String popularizestart) {
        this.popularizestart = popularizestart;
    }

    public String getPopularizestop() {
        return popularizestop;
    }

    public void setPopularizestop(String popularizestop) {
        this.popularizestop = popularizestop;
    }

    public int getFreeUseFlag() {
        return freeUseFlag;
    }

    public void setFreeUseFlag(int freeUseFlag) {
        this.freeUseFlag = freeUseFlag;
    }

    public int getFreeUseTime() {
        return freeUseTime;
    }

    public void setFreeUseTime(int freeUseTime) {
        this.freeUseTime = freeUseTime;
    }

    public String getChargeDes() {
        return chargeDes;
    }

    public void setChargeDes(String chargeDes) {
        this.chargeDes = chargeDes;
    }

    public String getBillingId() {
        return billingId;
    }

    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    public String getDiscount_des() {
        return discount_des;
    }

    public void setDiscount_des(String discount_des) {
        this.discount_des = discount_des;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public int getNeedConfm() {
        return needConfm;
    }

    public void setNeedConfm(int needConfm) {
        this.needConfm = needConfm;
    }

    public int getMaxUseTimes() {
        return maxUseTimes;
    }

    public void setMaxUseTimes(int maxUseTimes) {
        this.maxUseTimes = maxUseTimes;
    }

    public int getMaxUseTime() {
        return maxUseTime;
    }

    public void setMaxUseTime(int maxUseTime) {
        this.maxUseTime = maxUseTime;
    }

    public int getSendnum() {
        return sendnum;
    }

    public void setSendnum(int sendnum) {
        this.sendnum = sendnum;
    }

    public String getOrdercommand() {
        return ordercommand;
    }

    public void setOrdercommand(String ordercommand) {
        this.ordercommand = ordercommand;
    }

    public int getOrderCmdmatch() {
        return orderCmdmatch;
    }

    public void setOrderCmdmatch(int orderCmdmatch) {
        this.orderCmdmatch = orderCmdmatch;
    }

    public String getOrderAcc() {
        return orderAcc;
    }

    public void setOrderAcc(String orderAcc) {
        this.orderAcc = orderAcc;
    }

    public int getOrderAccmatch() {
        return orderAccmatch;
    }

    public void setOrderAccmatch(int orderAccmatch) {
        this.orderAccmatch = orderAccmatch;
    }

    public String getCancelcommand() {
        return cancelcommand;
    }

    public void setCancelcommand(String cancelcommand) {
        this.cancelcommand = cancelcommand;
    }

    public int getCancelCmdmatch() {
        return cancelCmdmatch;
    }

    public void setCancelCmdmatch(int cancelCmdmatch) {
        this.cancelCmdmatch = cancelCmdmatch;
    }

    public String getCancelAcc() {
        return cancelAcc;
    }

    public void setCancelAcc(String cancelAcc) {
        this.cancelAcc = cancelAcc;
    }

    public int getCancelAccmatch() {
        return cancelAccmatch;
    }

    public void setCancelAccmatch(int cancelAccmatch) {
        this.cancelAccmatch = cancelAccmatch;
    }

    public String getRequestcmd() {
        return requestcmd;
    }

    public void setRequestcmd(String requestcmd) {
        this.requestcmd = requestcmd;
    }

    public int getRequestCmdmatch() {
        return requestCmdmatch;
    }

    public void setRequestCmdmatch(int requestCmdmatch) {
        this.requestCmdmatch = requestCmdmatch;
    }

    public String getRequestAcc() {
        return requestAcc;
    }

    public void setRequestAcc(String requestAcc) {
        this.requestAcc = requestAcc;
    }

    public int getRequestAccmatch() {
        return requestAccmatch;
    }

    public void setRequestAccmatch(int requestAccmatch) {
        this.requestAccmatch = requestAccmatch;
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

    public int getVACSub() {
        return VACSub;
    }

    public void setVACSub(int VACSub) {
        this.VACSub = VACSub;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    public String getProductCity() {
        return productCity;
    }

    public void setProductCity(String productCity) {
        this.productCity = productCity;
    }

    public String getProductPeriodGrade() {
        return productPeriodGrade;
    }

    public void setProductPeriodGrade(String productPeriodGrade) {
        this.productPeriodGrade = productPeriodGrade;
    }

    public String getProductServiceGrade() {
        return productServiceGrade;
    }

    public void setProductServiceGrade(String productServiceGrade) {
        this.productServiceGrade = productServiceGrade;
    }

    public int getProductCredit() {
        return productCredit;
    }

    public void setProductCredit(int productCredit) {
        this.productCredit = productCredit;
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

    public String requestAcc;
    public int requestAccmatch;
    public String effDate;
    public String expDate;
    public int VACSub;
    public int notifyType;
    public String productCity;
    public String productPeriodGrade;
    public String productServiceGrade;
    public int productCredit;
    public String resv1;
    public String resv2;

    public ProductInfo() {
    }

    public static List<ProductInfo> parseFile(File file, List<String> spIdList) throws Exception {
        List<ProductInfo> list = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
        String header = br.readLine();
        String[] strs = new String[49];

        String str;
        while((str = br.readLine()) != null) {
            for(int i = 0; i < strs.length; ++i) {
                strs[i] = "";
            }

            String[] strsTemp = str.split("\t");

            for(int i = 0; i < strsTemp.length; ++i) {
                strs[i] = strsTemp[i];
            }

            ProductInfo productInfo = new ProductInfo();
            productInfo.recordSequenceID = strs[0].trim();
            productInfo.updateType = Integer.parseInt("".equals(strs[1].trim()) ? "0" : strs[1].trim());
            productInfo.productID = strs[2].trim();
            productInfo.spec_ProductID = strs[3].trim();
            productInfo.sp_ProductID = strs[4].trim();
            productInfo.serviceID = strs[5].trim();
            productInfo.spID = strs[6].trim();
            productInfo.productStatus = Integer.parseInt("".equals(strs[7].trim()) ? "0" : strs[7].trim());
            productInfo.productType = Integer.parseInt("".equals(strs[8].trim()) ? "0" : strs[8].trim());
            productInfo.productName = strs[9].trim();
            productInfo.confirm = Integer.parseInt("".equals(strs[10].trim()) ? "0" : strs[10].trim());
            productInfo.presenter = Integer.parseInt("".equals(strs[11].trim()) ? "0" : strs[11].trim());
            productInfo.confirmPrompt = strs[12].trim();
            productInfo.successPrompt = strs[13].trim();
            productInfo.cancelPrompt = strs[14].trim();
            productInfo.popularizestart = strs[15].trim();
            productInfo.popularizestop = strs[16].trim();
            productInfo.freeUseFlag = Integer.parseInt("".equals(strs[17].trim()) ? "0" : strs[17].trim());
            productInfo.freeUseTime = Integer.parseInt("".equals(strs[18].trim()) ? "0" : strs[18].trim());
            productInfo.chargeDes = strs[19].trim();
            productInfo.billingId = strs[20].trim();
            productInfo.discount_des = strs[21].trim();
            productInfo.discountId = strs[22].trim();
            productInfo.needConfm = Integer.parseInt("".equals(strs[23].trim()) ? "0" : strs[23].trim());
            productInfo.maxUseTimes = Integer.parseInt("".equals(strs[24].trim()) ? "0" : strs[24].trim());
            productInfo.maxUseTime = Integer.parseInt("".equals(strs[25].trim()) ? "0" : strs[25].trim());
            productInfo.sendnum = Integer.parseInt("".equals(strs[26].trim()) ? "0" : strs[26].trim());
            productInfo.ordercommand = strs[27].trim();
            productInfo.orderCmdmatch = Integer.parseInt("".equals(strs[28].trim()) ? "0" : strs[28].trim());
            productInfo.orderAcc = strs[29].trim();
            productInfo.orderAccmatch = Integer.parseInt("".equals(strs[30].trim()) ? "0" : strs[30].trim());
            productInfo.cancelcommand = strs[31].trim();
            productInfo.cancelCmdmatch = Integer.parseInt("".equals(strs[32].trim()) ? "0" : strs[32].trim());
            productInfo.cancelAcc = strs[33].trim();
            productInfo.cancelAccmatch = Integer.parseInt("".equals(strs[34].trim()) ? "0" : strs[34].trim());
            productInfo.requestcmd = strs[35].trim();
            productInfo.requestCmdmatch = Integer.parseInt("".equals(strs[36].trim()) ? "0" : strs[36].trim());
            productInfo.requestAcc = strs[37].trim();
            productInfo.requestAccmatch = Integer.parseInt("".equals(strs[38].trim()) ? "0" : strs[38].trim());
            productInfo.effDate = strs[39].trim();
            productInfo.expDate = strs[40].trim();
            productInfo.VACSub = Integer.parseInt("".equals(strs[41].trim()) ? "0" : strs[41].trim());
            productInfo.notifyType = Integer.parseInt("".equals(strs[42].trim()) ? "0" : strs[42].trim());
            productInfo.productCity = strs[43].trim();
            productInfo.productPeriodGrade = strs[44].trim();
            productInfo.productServiceGrade = strs[45].trim();
            productInfo.productCredit = Integer.parseInt("".equals(strs[46].trim()) ? "0" : strs[46].trim());
            productInfo.resv1 = strs[47].trim();
            productInfo.resv2 = strs[48].trim();
            if (SpFilter.spFilter(productInfo.spID, spIdList)) {
                logger.debug("readInFile(File) - String productInfo=" + productInfo);
                logger.info("productInfo:" + productInfo.productID);
                list.add(productInfo);
            }
        }

        br.close();
        return list;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append(" [");
        sb.append(" recordSequenceID=" + this.recordSequenceID);
        sb.append(" updateType=" + this.updateType);
        sb.append(" productID=" + this.productID);
        sb.append(" spec_ProductID=" + this.spec_ProductID);
        sb.append(" sp_ProductID=" + this.sp_ProductID);
        sb.append(" serviceID=" + this.serviceID);
        sb.append(" spID=" + this.spID);
        sb.append(" productStatus=" + this.productStatus);
        sb.append(" productType=" + this.productType);
        sb.append(" productName=" + this.productName);
        sb.append(" confirm=" + this.confirm);
        sb.append(" presenter=" + this.presenter);
        sb.append(" confirmPrompt=" + this.confirmPrompt);
        sb.append(" successPrompt=" + this.successPrompt);
        sb.append(" cancelPrompt=" + this.cancelPrompt);
        sb.append(" popularizestart=" + this.popularizestart);
        sb.append(" popularizestop=" + this.popularizestop);
        sb.append(" freeUseFlag=" + this.freeUseFlag);
        sb.append(" freeUseTime=" + this.freeUseTime);
        sb.append(" chargeDes=" + this.chargeDes);
        sb.append(" billingId=" + this.billingId);
        sb.append(" discount_des=" + this.discount_des);
        sb.append(" discountId=" + this.discountId);
        sb.append(" needConfm=" + this.needConfm);
        sb.append(" maxUseTimes=" + this.maxUseTimes);
        sb.append(" maxUseTime=" + this.maxUseTime);
        sb.append(" sendnum=" + this.sendnum);
        sb.append(" ordercommand=" + this.ordercommand);
        sb.append(" orderCmdmatch=" + this.orderCmdmatch);
        sb.append(" orderAcc=" + this.orderAcc);
        sb.append(" orderAccmatch=" + this.orderAccmatch);
        sb.append(" cancelcommand=" + this.cancelcommand);
        sb.append(" cancelCmdmatch=" + this.cancelCmdmatch);
        sb.append(" cancelAcc=" + this.cancelAcc);
        sb.append(" cancelAccmatch=" + this.cancelAccmatch);
        sb.append(" requestcmd=" + this.requestcmd);
        sb.append(" requestCmdmatch=" + this.requestCmdmatch);
        sb.append(" requestAcc=" + this.requestAcc);
        sb.append(" requestAccmatch=" + this.requestAccmatch);
        sb.append(" effDate=" + this.effDate);
        sb.append(" expDate=" + this.expDate);
        sb.append(" VACSub=" + this.VACSub);
        sb.append(" notifyType=" + this.notifyType);
        sb.append(" productCity=" + this.productCity);
        sb.append(" productPeriodGrade=" + this.productPeriodGrade);
        sb.append(" productServiceGrade=" + this.productServiceGrade);
        sb.append(" productCredit=" + this.productCredit);
        sb.append(" resv1=" + this.resv1);
        sb.append(" resv2=" + this.resv2);
        sb.append(" ]");
        return sb.toString();
    }
}

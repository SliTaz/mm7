

package com.zbensoft.mmsmp.ownbiz.ra.own.entity;

import java.io.Serializable;

public class MmsUserEntity implements Serializable {
    private long id;
    private long contentId;
    private long serviceId;
    private String vasId;
    private String vaspId;
    private String sendAddress;
    private String productId;
    private String userNumber;
    private String chargeNumber;
    private String contentName;
    private String contentFile;
    private String createDate;
    private int sendCount;

    public MmsUserEntity() {
    }

    public int getSendCount() {
        return this.sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContentId() {
        return this.contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public long getServiceId() {
        return this.serviceId;
    }

    public String getSendAddress() {
        return this.sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getVasId() {
        return this.vasId;
    }

    public void setVasId(String vasId) {
        this.vasId = vasId;
    }

    public String getVaspId() {
        return this.vaspId;
    }

    public void setVaspId(String vaspId) {
        this.vaspId = vaspId;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserNumber() {
        return this.userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getChargeNumber() {
        return this.chargeNumber;
    }

    public void setChargeNumber(String chargeNumber) {
        this.chargeNumber = chargeNumber;
    }

    public String getContentName() {
        return this.contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentFile() {
        return this.contentFile;
    }

    public void setContentFile(String contentFile) {
        this.contentFile = contentFile;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

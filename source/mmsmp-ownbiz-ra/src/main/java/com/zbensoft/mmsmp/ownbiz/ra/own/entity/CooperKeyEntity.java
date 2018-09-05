

package com.zbensoft.mmsmp.ownbiz.ra.own.entity;

import java.io.Serializable;

public class CooperKeyEntity implements Serializable {
    private int keyId;
    private String cooperId;
    private String cooperName;
    private String cooperKey;
    private String IPS;
    private String notifyUrl;
    private String remark;
    private String serviceTel;
    private String appName;
    private String sourceType;
    private String telNotifyUrl;
    private int cooperCode = -1;

    public CooperKeyEntity() {
    }

    public int getKeyId() {
        return this.keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public String getCooperId() {
        return this.cooperId;
    }

    public void setCooperId(String cooperId) {
        this.cooperId = cooperId;
    }

    public String getCooperName() {
        return this.cooperName;
    }

    public void setCooperName(String cooperName) {
        this.cooperName = cooperName;
    }

    public String getCooperKey() {
        return this.cooperKey;
    }

    public void setCooperKey(String cooperKey) {
        this.cooperKey = cooperKey;
    }

    public String getIPS() {
        return this.IPS;
    }

    public void setIPS(String iPS) {
        this.IPS = iPS;
    }

    public String getNotifyUrl() {
        return this.notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServiceTel() {
        return this.serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTelNotifyUrl() {
        return this.telNotifyUrl;
    }

    public void setTelNotifyUrl(String telNotifyUrl) {
        this.telNotifyUrl = telNotifyUrl;
    }

    public int getCooperCode() {
        return this.cooperCode;
    }

    public void setCooperCode(int cooperCode) {
        this.cooperCode = cooperCode;
    }
}



package com.zbensoft.mmsmp.ownbiz.ra.own.entity;

import java.io.Serializable;

public class PreUserEntity implements Serializable {
    private long contentId = -1L;
    private int users = 0;
    private int code = -1;

    public PreUserEntity() {
    }

    public long getContentId() {
        return this.contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public int getUsers() {
        return this.users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String toString() {
        return "ContentId:" + this.contentId + ",Users:" + this.users + ",rCode:" + this.code;
    }
}

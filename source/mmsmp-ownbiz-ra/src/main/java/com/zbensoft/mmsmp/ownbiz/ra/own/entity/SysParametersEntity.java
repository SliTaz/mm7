

package com.zbensoft.mmsmp.ownbiz.ra.own.entity;

import java.io.Serializable;

public class SysParametersEntity implements Serializable {
    private String key;
    private String value;
    private String desc;

    public SysParametersEntity() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

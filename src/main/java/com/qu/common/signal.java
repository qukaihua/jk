package com.qu.common;

/**
 * Created by 瞿凯华 on 2018/6/6 0006.
 */
public enum signal {
       SUCCESS(0,"success"),
        ERROR(1,"error");
    int code;
    String desc;

    signal(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

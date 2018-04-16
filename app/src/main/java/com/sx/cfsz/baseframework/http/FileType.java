package com.sx.cfsz.baseframework.http;

/***       Author  shy 
 *         Time   2018/4/13 0013    13:59      */

public enum FileType {
    IMAGE("image"),
    AUDIO("audio");

    private String value;

    FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

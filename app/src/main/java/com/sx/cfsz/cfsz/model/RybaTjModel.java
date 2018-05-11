package com.sx.cfsz.cfsz.model;

import java.util.List;

/***       Author  shy
 *         Time   2018/5/9 0009    16:32      */

public class RybaTjModel {


    /**
     * code : 1
     * data : [{"name":"张继科","num":"1"},{"name":"田芳","num":"1"},{"name":"龚婉婷","num":"0"},{"name":"陆薇","num":"1"},{"name":"小李","num":"2"},{"name":"小明","num":"2"}]
     * message : 人员办案数量统计
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 张继科
         * num : 1
         */

        private String name;
        private String num;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}

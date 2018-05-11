package com.sx.cfsz.cfsz.model;

import java.util.List;

/***       Author  shy
 *         Time   2018/5/8 0008    17:00      */

public class ColumnLineModel {


    /**
     * code : 1
     * data : [{"ywc":0,"ycs":0},{"ywc":0,"ycs":0},{"ywc":0,"ycs":0},{"ywc":0,"ycs":0},{"ywc":5,"ycs":5},{"ywc":0,"ycs":0},{"ywc":0,"ycs":0},{"ywc":0,"ycs":0},{"ywc":0,"ycs":0},{"ywc":0,"ycs":0},{"ywc":0,"ycs":0},{"ywc":0,"ycs":0}]
     * message : 每个月的统计
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
         * ywc : 0
         * ycs : 0
         */

        private int ywc;
        private int ycs;

        public int getYwc() {
            return ywc;
        }

        public void setYwc(int ywc) {
            this.ywc = ywc;
        }

        public int getYcs() {
            return ycs;
        }

        public void setYcs(int ycs) {
            this.ycs = ycs;
        }
    }
}

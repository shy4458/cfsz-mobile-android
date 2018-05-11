package com.sx.cfsz.cfsz.model;

/***       Author  shy          统计分析各案件数量
 *         Time   2018/5/8 0008    16:26      */

public class AjxqgkModel {


    /**
     * code : 1
     * data : {"rwzsl":18,"ajzsl":3,"xzsl":0,"zpsl":0,"scrwsl":0,"yqrwsl":0}
     * message : 案件情况
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * rwzsl : 18
         * ajzsl : 3
         * xzsl : 0
         * zpsl : 0
         * scrwsl : 0
         * yqrwsl : 0
         */

        private int rwzsl;
        private int ajzsl;
        private int xzsl;
        private int zpsl;
        private int scrwsl;
        private int yqrwsl;

        public int getRwzsl() {
            return rwzsl;
        }

        public void setRwzsl(int rwzsl) {
            this.rwzsl = rwzsl;
        }

        public int getAjzsl() {
            return ajzsl;
        }

        public void setAjzsl(int ajzsl) {
            this.ajzsl = ajzsl;
        }

        public int getXzsl() {
            return xzsl;
        }

        public void setXzsl(int xzsl) {
            this.xzsl = xzsl;
        }

        public int getZpsl() {
            return zpsl;
        }

        public void setZpsl(int zpsl) {
            this.zpsl = zpsl;
        }

        public int getScrwsl() {
            return scrwsl;
        }

        public void setScrwsl(int scrwsl) {
            this.scrwsl = scrwsl;
        }

        public int getYqrwsl() {
            return yqrwsl;
        }

        public void setYqrwsl(int yqrwsl) {
            this.yqrwsl = yqrwsl;
        }
    }
}

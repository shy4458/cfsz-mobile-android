package com.sx.cfsz.cfsz.model;

/***       Author  shy 
 *         Time   2018/4/18 0018    17:54      */

public class RwNumberModel {


    /**
     * code : 1
     * data : {"dzx":0,"ywc":0,"zxz":0}
     * message : 数据量
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
         * dzx : 0
         * ywc : 0
         * zxz : 0
         */

        private int dzx;
        private int ywc;
        private int zxz;

        public int getDzx() {
            return dzx;
        }

        public void setDzx(int dzx) {
            this.dzx = dzx;
        }

        public int getYwc() {
            return ywc;
        }

        public void setYwc(int ywc) {
            this.ywc = ywc;
        }

        public int getZxz() {
            return zxz;
        }

        public void setZxz(int zxz) {
            this.zxz = zxz;
        }
    }
}

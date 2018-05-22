package com.sx.cfsz.cfsz.model;

import java.util.List;

/***       Author  shy
 *         Time   2018/5/14 0014    10:07      */

public class CspmModel {
    /**
     * code : 1
     * data : [{"user_name":"张继科","user_id":"2","time_out":"2小时"},{"user_name":"田芳","user_id":"3","time_out":"2小时"},{"user_name":"张继科","user_id":"2","time_out":"0小时"}]
     * message : 超时排名
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
         * user_name : 张继科
         * user_id : 2
         * time_out : 2小时
         */

        private String user_name;
        private String user_id;
        private String time_out;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTime_out() {
            return time_out;
        }

        public void setTime_out(String time_out) {
            this.time_out = time_out;
        }
    }
}

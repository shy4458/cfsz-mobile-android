package com.sx.cfsz.cfsz.model;

import java.io.Serializable;
import java.util.List;

/***       Author  shy
 *         Time   2018/4/18 0018    17:15      */

public class RwSsModel implements Serializable{

    /**
     * code : 1
     * data : {"records":[{"task_id":"992344155000668160","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国民生银行北京正义路支行","task_type":"银行轮候查封","task_content":"洗劫银行","task_lat":"39.914699","task_lng":"116.405972","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344155369766912","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国建设银行股份有限公司北京市分行","task_type":"银行解抵押","task_content":"杀人","task_lat":"39.953661","task_lng":"116.107968","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344155403321344","task_num":"(2017)京0105执22796号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"肯德基","task_type":"银行轮候查封","task_content":"z自首","task_lat":"39.662494","task_lng":"116.081876","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344155432681472","task_num":"(2018)京0105执3152号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国建设银行股份有限公司北京市分行","task_type":"银行解网签","task_content":"抢银行","task_lat":"39.953661","task_lng":"116.107968","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344155462041600","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国民生银行北京正义路支行","task_type":"银行轮候查封","task_content":"杀人","task_lat":"39.914699","task_lng":"116.405972","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344155491401728","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国民生银行北京正义路支行","task_type":"银行轮候查封","task_content":"杀人","task_lat":"39.914699","task_lng":"116.405972","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344516239294464","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国民生银行北京正义路支行","task_type":"银行轮候查封","task_content":"洗劫银行","task_lat":"39.914699","task_lng":"116.405972","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344516264460288","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国建设银行股份有限公司北京市分行","task_type":"银行解抵押","task_content":"杀人","task_lat":"39.953661","task_lng":"116.107968","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null}],"totalCount":8,"currentPage":1,"limit":10,"start":0,"totalPages":1}
     * message : 待执行的任务
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

    public static class DataBean implements Serializable{
        /**
         * records : [{"task_id":"992344155000668160","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国民生银行北京正义路支行","task_type":"银行轮候查封","task_content":"洗劫银行","task_lat":"39.914699","task_lng":"116.405972","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344155369766912","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国建设银行股份有限公司北京市分行","task_type":"银行解抵押","task_content":"杀人","task_lat":"39.953661","task_lng":"116.107968","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344155403321344","task_num":"(2017)京0105执22796号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"肯德基","task_type":"银行轮候查封","task_content":"z自首","task_lat":"39.662494","task_lng":"116.081876","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344155432681472","task_num":"(2018)京0105执3152号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国建设银行股份有限公司北京市分行","task_type":"银行解网签","task_content":"抢银行","task_lat":"39.953661","task_lng":"116.107968","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344155462041600","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国民生银行北京正义路支行","task_type":"银行轮候查封","task_content":"杀人","task_lat":"39.914699","task_lng":"116.405972","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344155491401728","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国民生银行北京正义路支行","task_type":"银行轮候查封","task_content":"杀人","task_lat":"39.914699","task_lng":"116.405972","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344516239294464","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国民生银行北京正义路支行","task_type":"银行轮候查封","task_content":"洗劫银行","task_lat":"39.914699","task_lng":"116.405972","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null},{"task_id":"992344516264460288","task_num":"(2017)京0105执13878号","plan_time_start":null,"plan_time_end":"2018-05-03 13:23:18","actual_time_end":null,"sealup_time_end":null,"sealup_time_start":null,"task_address":"中国建设银行股份有限公司北京市分行","task_type":"银行解抵押","task_content":"杀人","task_lat":"39.953661","task_lng":"116.107968","now_id":"2","now_name":"白云龙","image":null,"old_id":null,"old_name":null,"task_status":"4","is_timeout":1,"feedback_msg":null,"feedback_pic":null,"feedback_video":null,"red_sign":"0","task_sfbq":null,"order":null,"result":null,"task_result_message":null}]
         * totalCount : 8
         * currentPage : 1
         * limit : 10
         * start : 0
         * totalPages : 1
         */

        private int totalCount;
        private int currentPage;
        private int limit;
        private int start;
        private int totalPages;
        private List<RecordsBean> records;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean implements Serializable{
            /**
             * task_id : 992344155000668160
             * task_num : (2017)京0105执13878号
             * plan_time_start : null
             * plan_time_end : 2018-05-03 13:23:18
             * actual_time_end : null
             * sealup_time_end : null
             * sealup_time_start : null
             * task_address : 中国民生银行北京正义路支行
             * task_type : 银行轮候查封
             * task_content : 洗劫银行
             * task_lat : 39.914699
             * task_lng : 116.405972
             * now_id : 2
             * now_name : 白云龙
             * image : null
             * old_id : null
             * old_name : null
             * task_status : 4
             * is_timeout : 1
             * feedback_msg : null
             * feedback_pic : null
             * feedback_video : null
             * red_sign : 0
             * task_sfbq : null
             * order : null
             * result : null
             * task_result_message : null
             */

            private String task_id;
            private String task_num;
            private String plan_time_start;
            private String plan_time_end;
            private String actual_time_end;
            private String sealup_time_end;
            private String sealup_time_start;
            private String task_address;
            private String task_type;
            private String task_content;
            private String task_lat;
            private String task_lng;
            private String now_id;
            private String now_name;
            private String image;
            private String old_id;
            private String old_name;
            private String task_status;
            private int is_timeout;
            private String feedback_msg;
            private String feedback_pic;
            private String feedback_video;
            private String red_sign;
            private String task_sfbq;
            private String order;
            private String result;
            private String task_result_message;

            public String getTask_id() {
                return task_id;
            }

            public void setTask_id(String task_id) {
                this.task_id = task_id;
            }

            public String getTask_num() {
                return task_num;
            }

            public void setTask_num(String task_num) {
                this.task_num = task_num;
            }

            public String getPlan_time_start() {
                return plan_time_start;
            }

            public void setPlan_time_start(String plan_time_start) {
                this.plan_time_start = plan_time_start;
            }

            public String getPlan_time_end() {
                return plan_time_end;
            }

            public void setPlan_time_end(String plan_time_end) {
                this.plan_time_end = plan_time_end;
            }

            public String getActual_time_end() {
                return actual_time_end;
            }

            public void setActual_time_end(String actual_time_end) {
                this.actual_time_end = actual_time_end;
            }

            public String getSealup_time_end() {
                return sealup_time_end;
            }

            public void setSealup_time_end(String sealup_time_end) {
                this.sealup_time_end = sealup_time_end;
            }

            public String getSealup_time_start() {
                return sealup_time_start;
            }

            public void setSealup_time_start(String sealup_time_start) {
                this.sealup_time_start = sealup_time_start;
            }

            public String getTask_address() {
                return task_address;
            }

            public void setTask_address(String task_address) {
                this.task_address = task_address;
            }

            public String getTask_type() {
                return task_type;
            }

            public void setTask_type(String task_type) {
                this.task_type = task_type;
            }

            public String getTask_content() {
                return task_content;
            }

            public void setTask_content(String task_content) {
                this.task_content = task_content;
            }

            public String getTask_lat() {
                return task_lat;
            }

            public void setTask_lat(String task_lat) {
                this.task_lat = task_lat;
            }

            public String getTask_lng() {
                return task_lng;
            }

            public void setTask_lng(String task_lng) {
                this.task_lng = task_lng;
            }

            public String getNow_id() {
                return now_id;
            }

            public void setNow_id(String now_id) {
                this.now_id = now_id;
            }

            public String getNow_name() {
                return now_name;
            }

            public void setNow_name(String now_name) {
                this.now_name = now_name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getOld_id() {
                return old_id;
            }

            public void setOld_id(String old_id) {
                this.old_id = old_id;
            }

            public String getOld_name() {
                return old_name;
            }

            public void setOld_name(String old_name) {
                this.old_name = old_name;
            }

            public String getTask_status() {
                return task_status;
            }

            public void setTask_status(String task_status) {
                this.task_status = task_status;
            }

            public int getIs_timeout() {
                return is_timeout;
            }

            public void setIs_timeout(int is_timeout) {
                this.is_timeout = is_timeout;
            }

            public String getFeedback_msg() {
                return feedback_msg;
            }

            public void setFeedback_msg(String feedback_msg) {
                this.feedback_msg = feedback_msg;
            }

            public String getFeedback_pic() {
                return feedback_pic;
            }

            public void setFeedback_pic(String feedback_pic) {
                this.feedback_pic = feedback_pic;
            }

            public String getFeedback_video() {
                return feedback_video;
            }

            public void setFeedback_video(String feedback_video) {
                this.feedback_video = feedback_video;
            }

            public String getRed_sign() {
                return red_sign;
            }

            public void setRed_sign(String red_sign) {
                this.red_sign = red_sign;
            }

            public String getTask_sfbq() {
                return task_sfbq;
            }

            public void setTask_sfbq(String task_sfbq) {
                this.task_sfbq = task_sfbq;
            }

            public String getOrder() {
                return order;
            }

            public void setOrder(String order) {
                this.order = order;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public String getTask_result_message() {
                return task_result_message;
            }

            public void setTask_result_message(String task_result_message) {
                this.task_result_message = task_result_message;
            }
        }
    }
}

package com.sx.cfsz.cfsz.model;

/***       Author  shy 
 *         Time   2018/5/15 0015    14:05      */

public class LoginModel {


    /**
     * code : 1
     * data : {"userId":"2","userOtherId":"125830085","userName":"张继科","userPwd":"123123","userPost":3,"userStatus":1,"userHeadpic":"","userPhone":"","userEmail":""}
     * message : 成功
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
         * userId : 2
         * userOtherId : 125830085
         * userName : 张继科
         * userPwd : 123123
         * userPost : 3
         * userStatus : 1
         * userHeadpic :
         * userPhone :
         * userEmail :
         */

        private String userId;
        private String userOtherId;
        private String userName;
        private String userPwd;
        private int userPost;
        private int userStatus;
        private String userHeadpic;
        private String userPhone;
        private String userEmail;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserOtherId() {
            return userOtherId;
        }

        public void setUserOtherId(String userOtherId) {
            this.userOtherId = userOtherId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPwd() {
            return userPwd;
        }

        public void setUserPwd(String userPwd) {
            this.userPwd = userPwd;
        }

        public int getUserPost() {
            return userPost;
        }

        public void setUserPost(int userPost) {
            this.userPost = userPost;
        }

        public int getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        public String getUserHeadpic() {
            return userHeadpic;
        }

        public void setUserHeadpic(String userHeadpic) {
            this.userHeadpic = userHeadpic;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }
    }
}

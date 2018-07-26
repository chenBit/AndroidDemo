package com.example.nsky.studydemo.bean;

/**
 * Created by zj on 2016/10/11.
 */

public class XHVerifyResult {
    public int status;
    public String msg;
    public VerifyContent content;

    public class VerifyContent {
        public String meid;
        public String iccid;
        public String imei1;
        public String userName;
        public String userCode;
        public String phone;
        public String distributeTime;
        public String userId;
    }

    @Override
    public String toString() {
        return "XHVerifyResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", content=" + content +
                '}';
    }
}

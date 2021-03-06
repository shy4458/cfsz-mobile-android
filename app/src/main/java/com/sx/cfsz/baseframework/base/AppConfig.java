package com.sx.cfsz.baseframework.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.Calendar;

public class AppConfig {


    public static final String PERF_NAME = "sx.pref";
    public static final String IMG_LIST = "img_list"; //第几张图片
    public static final String POSITION = "position"; //第几张图片
    public static final String PIC_PATH = "pic_path"; //图片路径
    public static final int MAX_SELECT_PIC_NUM = 8; // 最多上传8张图片
    public static final int REQUEST_CODE_MAIN = 10; //请求码
    public static final int RESULT_CODE_VIEW_IMG = 11; //查看大图页面的结果码

    public static final int STETADZX = 1;
    public static final int STETAZXZ = 2;
    public static final int STETAYWC = 3;

    public static final int ROWSNUMBER = 20;

    //法院接口
//    public static final String IP = "http://114.255.39.85:80/";

    //调式接口ip
    public static final String IP = "http://192.168.120.115:9000/";

    //测试接口
//    public static final String IP = "http://10.100.105.5:7020/";

    //领导接口
    //http://cyfytest.beikongyun.com/
    //http://192.168.40.157:59000/
//    public static final String IP = "http://cyfytest.beikongyun.com/";
//    public static final String IP = "http://192.168.40.157:59000/";

    //登陆 http://192.168.120.115:9000/user/login?userName=张继科&userPwd=123123
    //登录后token http://192.168.120.115:9000/task/token?userToken=&userName=
    public static final String TOKEN = "user/token?userToken=";
    //NAME
    public static final String USERNAME = "&userName=";
    public static final String LOGIN = "user/login?";
    //用户名
    public static final String NAME = "userName=";
    //密码
    public static final String PWD = "&userPwd=";
    //头像 "http://192.168.120.115:9000/user/IoReadImage?imgName="
    public static final String PIC = "user/IoReadImage?imgName=";
    //userId
    public static final String USERID = "?user_id=";
    //每个状态下的总数  http://192.168.120.115:9000/task/Appidsjl
    public static final String NUMBER = "task/Appidsjl?user_id=";
    //待执行
    //http://192.168.120.115:9000/task/Appdzx?page=1&rows=10
    public static final String DZX = "task/Appdzx?user_id=";
    //执行中
    public static final String ZXZ = "task/Appzxz?user_id=";
    //已完成
    public static final String YWC = "task/Appywc?user_id=";
    //当前页
    public static final String PAGE = "page=";
    //每页数据量
    public static final String ROWS = "&rows=";
    //待执行添加到执行 http://192.168.120.115:9000/task/AppdzxDAOzxz?task_id-任务的id
    public static final String TIANJIA = "task/AppdzxDAOzxz?task_ids=";
    //搜索
    //http://192.168.120.115:9000/task/Appsrw?参数（必传user_id;非必传case_id（案件号）；非必传ks_time(开始时间）；非必传js_time(结束时间)）。
    public static final String SJSEARCH = "task/Appsrw?user_id=";
    //开始时间
    public static final String KS_TIME = "&ks_time=";
    //结束时间
    public static final String JS_TIME = "&js_time=";
    //案件号
    public static final String CASE_ID = "task/Appsrw?user_id=";
    //多选提交  http://192.168.120.115:9000/task/AppdzxDAOzxz?task_ids=
    public static final String ALLSUBMIT = "task/AppdzxDAOzxz?task_ids=";
    //去红点 单选  http://192.168.120.115:9000/task/Appidred?task_id=（去红点，单条操作）
    public static final String REMORED = "task/Appidred?task_id=";
    public static final String REDS = "&red_signs=";
    public static final String RED = "&red_sign=";
    //上传照片
    public static final String IMG = "task/scfkzp";
    //上传视频
    public static final String VIDEO = "task/scfksp";
    //反馈
    public static final String FEED = "task/AppzxzDAOywc?";
    //ID
    public static final String ID = "user_id=";
    //任务ID
    public static final String RWID = "&task_id=";
    //反馈内容
    public static final String FEEDCONTENT = "&feedback_msg=";
    //照片
    public static final String ZPURL = "&feedback_pic=";
    //视频
    public static final String SPURL = "&feedback_video=";
    //封印开始时间
    public static final String FYQSSJ = "&sealup_time_start=";
    //封印结束时间
    public static final String FYJSSJ = "&sealup_time_end=";
    //执行结果
    public static final String RESULT = "&result_type=";
    //照片 http://192.168.120.115:9000/task/fkzp?imgName=993324095430983680.jpg
    public static final String DQZP = "task/fkzp?imgName=";
    //视频  http://192.168.120.115:9000/task/fksp?videoName=992343926926999553.mp4
    public static final String DQSP = "task/fksp?videoName=";
    //统计查询中的案件概括详情
    public static final String TJSJ = "task/ajqk";
    //每个月统计
    public static final String TJYUE = "task/gytj";
    //人员办案统计
    public static final String RYBATJ = "task/rybasltj";
    //案件类型
    public static final String AJLXTJ = "task/ajlxtj";
    //超时排名
    public static final String CSPM = "task/cspm";
    //版本号
    public static final String BB = "task/selectversion";
    //更新地址
    public static final String PATH = "task/com.sx.cfsz.apk";

    public static int getPackageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String getDate() {
        Calendar c = Calendar.getInstance();//
        long mYear = c.get(Calendar.YEAR); // 获取当前年份
        long mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        long mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期

        int mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
        int mHour = c.get(Calendar.HOUR_OF_DAY);//时
        int mMinute = c.get(Calendar.MINUTE);//分

        String strDay = mDay + "";
        if (strDay.length() == 1) {
            strDay = "0" + strDay;
        }
        String strMonth = mMonth + "";
        if (strMonth.length() == 1) {
            strMonth = "0" + strMonth;
        }

        return mYear + "-" + strMonth + "-" + strDay + "";
    }

    public static String getDateMonth() {
        Calendar c = Calendar.getInstance();//
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期

        int mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
        int mHour = c.get(Calendar.HOUR_OF_DAY);//时
        int mMinute = c.get(Calendar.MINUTE);//分
        String sMonth = mMonth + "";
        if (sMonth.length() == 1) {
            sMonth = "0" + sMonth;
        }
        return mYear + "" + sMonth + "01";
    }

    //获取一个月的天数并拼接
    public static String getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        int maxDate = a.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mYear = a.get(Calendar.YEAR); // 获取当前年份
        int mMonth = a.get(Calendar.MONTH) + 1;// 获取当前月份

        String sMonth = "" + mMonth;
        if (sMonth.length() == 1) {
            sMonth = "0" + sMonth;
        }

        return mYear + "" + sMonth + "" + maxDate;
    }
}

package com.sx.cfsz.cfsz.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.sx.cfsz.baseframework.base.BaseApplication;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import java.util.List;

/***       Author  shy
 *         Time   2018/6/7 0007    10:38      */

public class MsgReceiver extends XGPushBaseReceiver {

    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {

    }

    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
        String content = xgPushClickedResult.getContent();
        Log.e("////ClickedResult", content);
        boolean isApp = isAppRunning(BaseApplication.context(), "com.sx.cfsz");
        if (isApp) {
            //正在运行 跳转至页面

        } else {
            //启动App 登陆
        }

    }

    // 通知展示
    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        MainActivity mainActivity = (MainActivity) MainActivity.sInstance;
        if (!"下线通知".equals(xgPushShowedResult.getTitle())) {
            boolean isApp = isAppRunning(BaseApplication.context(), "com.sx.cfsz");
            if (isApp) {
                //前台刷新
                mainActivity.xgRefresh();
            } else {
                //不在前台，启动登陆
            }
        } else {
            mainActivity.xx();
        }
    }

    //是否正在运行
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

}

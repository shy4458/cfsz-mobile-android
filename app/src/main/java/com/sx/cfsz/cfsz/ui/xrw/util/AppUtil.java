package com.sx.cfsz.cfsz.ui.xrw.util;

import android.os.Environment;

import java.io.File;


public class AppUtil {
    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            return sdDir.toString();
        } else {
            return null;
        }
    }
    public static String getAppDir(){
        String appDir = AppUtil.getSDPath();
        appDir += "/" + "xc";
        File file = new File(appDir);
        if(!file.exists()){
            file.mkdir();
        }
        appDir +="/"+"videocompress";
        file = new File(appDir);
        if(!file.exists()){
            file.mkdir();
        }
        return appDir;
    }
}

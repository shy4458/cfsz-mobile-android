package com.sx.cfsz.cfsz.presenter;

import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.common.UpdateDownloadService;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.model.UserModel;
import com.sx.cfsz.cfsz.ui.xrw.activity.UserActivity;

import java.io.IOException;

import okhttp3.Response;

/***       Author  shy
 *         Time   2018/5/22 0022    9:22      */

public class UserActivityPresenter {

    UserActivity activity;

    public UserActivityPresenter(UserActivity activity) {
        this.activity = activity;
    }

    public void update() {
        String url = AppConfig.IP + AppConfig.BB;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            UserModel userModel = gson.fromJson(str, UserModel.class);
                            activity.sucessBB(userModel.getData());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}

package com.sx.cfsz.cfsz.presenter;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.baseframework.util.UIUtils;
import com.sx.cfsz.cfsz.model.LoginModel;
import com.sx.cfsz.cfsz.ui.LoginActivity;

import java.io.IOException;

import okhttp3.Response;

/***       Author  shy
 *         Time   2018/4/17 0017    13:56      */

public class LoginPresenter {

    private LoginActivity activity;

    public LoginPresenter(LoginActivity activity) {
        this.activity = activity;
    }

    public void login(String name, String pwd) {
        String url = AppConfig.IP + AppConfig.LOGIN + AppConfig.NAME + name + AppConfig.PWD + pwd;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null) {
                    if (response != null && response.isSuccessful()) {
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            LoginModel loginModel = gson.fromJson(str, LoginModel.class);
                            if (loginModel.getData() != null) {
                                if (loginModel.getData().getUserStatus() == 1) {
                                    //登陆成功  //保存userId
                                    if (loginModel.getData().getUserPost() == 2) {
                                        activity.logMsg(loginModel.getMessage());
                                    } else {
//                                        String cookie = response.header("Set-Cookie");
//                                        String[] split = cookie.split(";");
//                                        for (int i = 0; i < split.length; i++) {
//                                            String[] split1 = split[i].split("=");
//                                            if ("JSESSIONID".equals(split1[0])) {
//                                                BaseApplication.set("Set-Cookie", split1[1]);
                                                activity.success(loginModel);
//                                                break;
//                                            }
//                                        }
                                    }

                                } else {
                                    //登陆不成功
                                    activity.logMsg(loginModel.getMessage());
                                }

                            } else {
                                activity.logMsg(loginModel.getMessage());
                            }

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}

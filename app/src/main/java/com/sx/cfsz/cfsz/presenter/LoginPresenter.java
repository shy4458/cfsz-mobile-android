package com.sx.cfsz.cfsz.presenter;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
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
                if (e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            LoginModel loginModel = gson.fromJson(str, LoginModel.class);
                            activity.success(loginModel);

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}

package com.sx.cfsz.cfsz.presenter;

import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.ui.MainActivity;

import java.io.IOException;

import okhttp3.Response;

/***       Author  shy
 *         Time   2018/5/20 0020    14:41      */

public class MainActivityPresenter {

    MainActivity activity;

    public MainActivityPresenter(MainActivity activity) {
        this.activity = activity;
    }

    public void intTS(String userId, String token) {
        String url = AppConfig.IP + AppConfig.TOKEN + token + AppConfig.USERNAME + userId;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String string = response.body().string();

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }


                    }
                }
            }
        });


    }

}

package com.sx.cfsz.cfsz.presenter;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.model.RwModel;
import com.sx.cfsz.cfsz.model.SubmitModel;
import com.sx.cfsz.cfsz.ui.xrw.activity.DzxDetailsActivity;

import java.io.IOException;

import okhttp3.Response;

/***       Author  shy
 *         Time   2018/4/23 0023    16:18      */

public class DzxDetailsPresenter {

    DzxDetailsActivity activity;

    public DzxDetailsPresenter(DzxDetailsActivity activity) {
        this.activity = activity;
    }

    public void submit(String task_id, String red_sign) {
        String url = AppConfig.IP + AppConfig.TIANJIA + task_id + AppConfig.REDS + red_sign;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null) {
                    if (response != null && response.isSuccessful()) {
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            SubmitModel submitModel = gson.fromJson(str, SubmitModel.class);
                            if (submitModel.getCode() != -3) {
                                activity.success(submitModel);
                            }else {

                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //去掉红点
    public void remoRed(String task_id, String red_sign) {
        String url = AppConfig.IP + AppConfig.REMORED + task_id + AppConfig.RED + red_sign;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null) {
                    if (response != null && response.isSuccessful()) {
                        try {
                            String str = response.body().string();
                            activity.successRed(str);

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}

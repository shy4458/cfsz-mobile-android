package com.sx.cfsz.cfsz.presenter;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.model.RwModel;
import com.sx.cfsz.cfsz.ui.xrw.activity.SearchActivity;

import java.io.IOException;

import okhttp3.Response;

/***       Author  shy
 *         Time   2018/4/25 0025    14:38      */

public class SearchActivityPresenter {

    SearchActivity activity;

    public SearchActivityPresenter(SearchActivity activity) {
        this.activity = activity;
    }
    //时间维度搜索
    public void sjSearch(String qssj, String jzsj) {
        String url = AppConfig.IP + AppConfig.SJSEARCH + AppConfig.KS_TIME + qssj + AppConfig.JS_TIME + jzsj;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if ( e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            RwModel model = gson.fromJson(str, RwModel.class);
                            activity.success(model);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    //案件号模糊搜索
    public void ajhSearch(String ajh) {
        String url = AppConfig.IP + AppConfig.CASE_ID + ajh;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if ( e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            RwModel model = gson.fromJson(str, RwModel.class);
                            activity.success(model);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}

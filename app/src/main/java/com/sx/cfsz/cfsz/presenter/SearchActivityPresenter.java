package com.sx.cfsz.cfsz.presenter;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.model.RwSsModel;
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
    //一起搜索
    public void search(String userId, String caseId, String qssj, String jzsj){
        String url = AppConfig.IP + AppConfig.SJSEARCH + userId + "&case_id=" + caseId + AppConfig.KS_TIME + qssj + AppConfig.JS_TIME + jzsj;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if ( e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            RwSsModel model = gson.fromJson(str, RwSsModel.class);
                            activity.success(model);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    //时间维度搜索
    public void sjSearch(String qssj, String jzsj) {
        String url = AppConfig.IP + AppConfig.SJSEARCH  + BaseApplication.get("userId", "")+ AppConfig.KS_TIME + qssj + AppConfig.JS_TIME + jzsj;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if ( e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            RwSsModel model = gson.fromJson(str, RwSsModel.class);
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
        String url = AppConfig.IP + AppConfig.CASE_ID + BaseApplication.get("userId", "") + "&case_id=" + ajh;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if ( e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            RwSsModel model = gson.fromJson(str, RwSsModel.class);
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

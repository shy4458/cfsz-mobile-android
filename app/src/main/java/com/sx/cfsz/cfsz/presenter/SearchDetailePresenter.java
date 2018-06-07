package com.sx.cfsz.cfsz.presenter;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.model.RwSsModel;
import com.sx.cfsz.cfsz.ui.xrw.activity.SearchDetailedActivity;
import java.io.IOException;
import okhttp3.Response;

/***       Author  shy
 *         Time   2018/6/7 0007    9:17      */

public class SearchDetailePresenter  {

    SearchDetailedActivity activity;

    public SearchDetailePresenter(SearchDetailedActivity activity) {
        this.activity = activity;
    }

    public void getData(String userId, String caseId, String qssj, String jzsj) {
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
}

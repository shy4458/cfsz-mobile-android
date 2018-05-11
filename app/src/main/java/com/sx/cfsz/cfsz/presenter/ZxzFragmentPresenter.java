package com.sx.cfsz.cfsz.presenter;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.model.RwModel;
import com.sx.cfsz.cfsz.ui.xrw.fragment.ZxzFragment;

import java.io.IOException;

import okhttp3.Response;

/***       Author  shy
 *         Time   2018/4/19 0019    11:09      */

public class ZxzFragmentPresenter {
    ZxzFragment fragment;
    public ZxzFragmentPresenter(ZxzFragment fragment) {
        this.fragment = fragment;
    }

    public void getData(int page, int rows) {
        String url = AppConfig.IP + AppConfig.ZXZ + AppConfig.PAGE + page + AppConfig.ROWS + rows;
        HttpUtils.getAsync(url, fragment.getActivity(), new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            RwModel rwModel = gson.fromJson(str, RwModel.class);
                            fragment.success(rwModel);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}

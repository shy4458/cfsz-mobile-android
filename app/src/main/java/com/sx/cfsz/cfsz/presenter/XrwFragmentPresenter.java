package com.sx.cfsz.cfsz.presenter;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.model.RwModel;
import com.sx.cfsz.cfsz.model.RwNumberModel;
import com.sx.cfsz.cfsz.ui.xrw.fragment.XrwFragment;

import java.io.IOException;

import okhttp3.Response;

/***       Author  shy
 *         Time   2018/4/18 0018    15:50      */

public class XrwFragmentPresenter {

    private XrwFragment fragment;

    public XrwFragmentPresenter(XrwFragment fragment) {
        this.fragment = fragment;
    }

//    //待执行 1
//    //执行中 2
//    //已完成 3
//    public void getData(int steta,int page,int rows) {
//        //TODO
//        String url = "";
//        if (steta == AppConfig.STETADZX) {
//            url = AppConfig.IP + AppConfig.DZX   + BaseApplication.get("userId", "") + "&" + AppConfig.PAGE + page +AppConfig.ROWS + rows;
//        } else if (steta == AppConfig.STETAZXZ) {
//            url = AppConfig.IP + AppConfig.ZXZ   + BaseApplication.get("userId", "") + "&";
//        } else if (steta == AppConfig.STETAYWC) {
//            url = AppConfig.IP + AppConfig.YWC  + BaseApplication.get("userId", "") + "&";
//        }
//
//        HttpUtils.getAsync(url, fragment.getActivity(), new OnRequestResult() {
//            @Override
//            public void result(Exception e, Response response) {
//                if (e == null) {
//                    if (response != null && response.isSuccessful()) {
//                        try {
//                            String str = response.body().string();
//                            Gson gson = new Gson();
//                            RwModel rwModel = gson.fromJson(str, RwModel.class);
//
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                } else {
//                    //异常 e
//                }
//            }
//        });
//    }

    public void getNumber() {
        String url = AppConfig.IP + AppConfig.NUMBER + BaseApplication.get("userId", "");

        HttpUtils.getAsync(url, fragment.getActivity(), new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            RwNumberModel rwNumberModel = gson.fromJson(str, RwNumberModel.class);
                            fragment.successNumber(rwNumberModel);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}

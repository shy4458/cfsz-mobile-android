package com.sx.cfsz.cfsz.presenter;

import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.model.AjxqgkModel;
import com.sx.cfsz.cfsz.model.GytjModel;
import com.sx.cfsz.cfsz.model.RybaTjModel;
import com.sx.cfsz.cfsz.ui.tjfx.TjfxFragment;

import java.io.IOException;

import okhttp3.Response;

/***       Author  shy
 *         Time   2018/5/8 0008    16:12      */

public class TjfxFragmentPresenter {

    TjfxFragment fragment;

    public TjfxFragmentPresenter(TjfxFragment fragment) {
        this.fragment = fragment;
    }

    public void getData() {
        //各项总数
        String url = AppConfig.IP + AppConfig.TJSJ;
        HttpUtils.getAsync(url, fragment.getActivity(), new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            AjxqgkModel ajxqgkModel = gson.fromJson(str, AjxqgkModel.class);
                            fragment.successAjNumber(ajxqgkModel);

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        //每个月统计
        String urlgytj = AppConfig.IP + AppConfig.TJYUE;
        HttpUtils.getAsync(urlgytj, fragment.getActivity(), new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            GytjModel gytjModel = gson.fromJson(str, GytjModel.class);
                            fragment.successGrtj(gytjModel);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        //人员办案
        String urlrybatj = AppConfig.IP + AppConfig.RYBATJ;
        HttpUtils.getAsync(urlrybatj, fragment.getActivity(), new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            RybaTjModel rybaTjModel = gson.fromJson(str, RybaTjModel.class);
                            fragment.successRybrtj(rybaTjModel);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}

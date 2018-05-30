package com.sx.cfsz.cfsz.presenter;

import android.app.ProgressDialog;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.model.RwModel;
import com.sx.cfsz.cfsz.model.SubmitModel;
import com.sx.cfsz.cfsz.ui.MainActivity;
import com.sx.cfsz.cfsz.ui.xrw.fragment.DzxFragment;

import java.io.IOException;

import okhttp3.Response;

/***       Author  shy
 *         Time   2018/4/19 0019    10:33      */

public class DzxFragmentPresenter {

    int state;
    DzxFragment fragment;
    private MainActivity activity;

    public DzxFragmentPresenter(DzxFragment fragment) {
    this.fragment =fragment;

    }

    /**
     * @param page
     * @param rows
     * @param state 0 下拉刷新 1正常加载
     */
    public void getData(int page,int rows,int state){
        activity = (MainActivity) fragment.getActivity();
        this.state =state;
        if (state == 1) {
            activity.showDialog(fragment.getActivity());
        }

        String url = AppConfig.IP + AppConfig.DZX + AppConfig.PAGE + page + AppConfig.ROWS + rows;
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
    //多选提交
    public void submitAll(String allTastId,String allRed) {
        String url  = AppConfig.IP + AppConfig.ALLSUBMIT + allTastId + AppConfig.REDS + allRed;
        HttpUtils.getAsync(url, fragment.getActivity(), new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null){
                    if (response != null && response.isSuccessful()){
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            SubmitModel submitModel = gson.fromJson(str, SubmitModel.class);
                            fragment.allSubmitSuccess(submitModel);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    public void dialogDismis() {
        if (this.state == 1) {
            activity.disDialog();
        }
    }
}

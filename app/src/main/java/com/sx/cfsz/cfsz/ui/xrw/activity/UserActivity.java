package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.common.UpdateDownloadService;
import com.sx.cfsz.baseframework.http.FileInfo;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.ui.MainActivity;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/***       Author  shy              我的
 *         Time   2018/4/11 0011    15:06      */

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        BaseApplication.addList(this);
        initView();
    }

    private void initView() {
        Button bTc = findViewById(R.id.bTc);
        LinearLayout bb = findViewById(R.id.ll_banben);
        bTc.setOnClickListener(this);
        bb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bTc:
                BaseApplication.clenList();
                break;

            case R.id.ll_banben:

                break;
        }
    }

    public void update() {
//        UpdateDownloadService.newBuilder(UserActivity.this)
//                .setTitle("")
//                .setSavePath("")
//                .setFileName("")
//                .setImage("")
//                .setDownloadUrl("")
//                .start();
    }
}

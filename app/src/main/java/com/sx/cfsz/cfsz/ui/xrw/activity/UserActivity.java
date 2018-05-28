package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.common.UpdateDownloadService;
import com.sx.cfsz.baseframework.util.UIUtils;
import com.sx.cfsz.cfsz.dagger.component.DaggerUserActivityComponent;
import com.sx.cfsz.cfsz.dagger.module.UserActivityModule;
import com.sx.cfsz.cfsz.presenter.UserActivityPresenter;
import com.sx.cfsz.cfsz.ui.LoginActivity;
import com.sx.cfsz.cfsz.ui.MainActivity;

import java.io.File;

import javax.inject.Inject;

/***       Author  shy              我的
 *         Time   2018/4/11 0011    15:06      */

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    UserActivityPresenter presenter;
    private static final int BB = 55;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BB:
                    int bb = (int) msg.obj;
                    int packageCode = AppConfig.getPackageCode(UserActivity.this);
                    if (bb > packageCode) {



                    } else {
                        UIUtils.showToast(UserActivity.this, "您当前版本为最新版本.");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        BaseApplication.addList(this);
        DaggerUserActivityComponent.builder().userActivityModule(new UserActivityModule(this)).build().in(this);
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
                presenter.update();
                break;
        }
    }

    public void sucessBB(int bb) {
        Message msg = Message.obtain();
        msg.what = BB;
        msg.obj = bb;
        mHandler.sendMessage(msg);
    }
}

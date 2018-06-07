package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sx.cfsz.BuildConfig;
import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.cfsz.dagger.component.DaggerUserActivityComponent;
import com.sx.cfsz.cfsz.dagger.module.UserActivityModule;
import com.sx.cfsz.cfsz.presenter.UserActivityPresenter;
import com.sx.cfsz.cfsz.ui.LoginActivity;
import com.tencent.android.tpush.XGPushManager;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

/***       Author  shy              我的
 *         Time   2018/4/11 0011    15:06      */

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    UserActivityPresenter presenter;
    private static final int BB = 55;


    private CircleImageView civ;
    private TextView tvName;
    private TextView tvBbh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        BaseApplication.addList(this);
        DaggerUserActivityComponent.builder().userActivityModule(new UserActivityModule(this)).build().in(this);
        initView();
        initData();
    }

    private void initData() {
        Picasso.with(this).load(AppConfig.IP + AppConfig.PIC + BaseApplication.get("UserHeadpic","")).placeholder(R.drawable.default_head).into(civ);
        tvName.setText(BaseApplication.get("userName",""));
    }

    private void initView() {
        Button bTc = findViewById(R.id.bTc);
        LinearLayout bb = findViewById(R.id.ll_banben);
        civ = findViewById(R.id.civHead_user);
//         = findViewById(R.id.ll_gywm);
        tvName = findViewById(R.id.tv_name);
        tvBbh = findViewById(R.id.tv_bbh);
        tvBbh.setText(BuildConfig.VERSION_NAME);
        bTc.setOnClickListener(this);
        bb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bTc:
                BaseApplication.removeUserId();
                XGPushManager.unregisterPush(BaseApplication.context());
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
                BaseApplication.clenList();

                break;

            case R.id.ll_banben:

                break;
        }
    }


}

package com.sx.cfsz.cfsz.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.cfsz.ui.tjfx.TjfxFragment;
import com.sx.cfsz.cfsz.ui.xrw.SearchActivity;
import com.sx.cfsz.cfsz.ui.xrw.UserActivity;
import com.sx.cfsz.cfsz.ui.xrw.XrwFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView civHead;
    private TextView tvTitle;
    private ImageView ivSs;
    private ImageView ivSelece;
    private FrameLayout mainF;
    private RadioGroup mainRg;
    private RadioButton rbXrw;
    private RadioButton rbJstx;
    private RadioButton rbTjfx;
    private RadioButton rbFgbl;
    private LinearLayout llDx;
    private TextView tvYse;
    private TextView tvNo;

    private int position = 0;
    private XrwFragment xrwFragment;
    private TjfxFragment tjfxFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseApplication.addList(this);
        initView();
        setTabSelection(0);
        initListener();

    }

    private void initView() {
        civHead = findViewById(R.id.civHead);
        tvTitle = findViewById(R.id.tvTitle);
        ivSs = findViewById(R.id.ivSs);
        ivSelece = findViewById(R.id.ivSelect);
        mainF = findViewById(R.id.mainF);
        mainRg = findViewById(R.id.mainRg);
        rbXrw = findViewById(R.id.rbXrw);
        rbTjfx = findViewById(R.id.rbTjfx);
        rbJstx = findViewById(R.id.rbJstx);
        rbFgbl = findViewById(R.id.rbFgbl);

        llDx = findViewById(R.id.llDx);
        tvYse = findViewById(R.id.tvYse);
        tvNo = findViewById(R.id.tvNo);

    }

    private void initListener() {
        ivSs.setOnClickListener(this);
        civHead.setOnClickListener(this);
        ivSelece.setOnClickListener(this);
        tvYse.setOnClickListener(this);
        tvNo.setOnClickListener(this);
        mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbXrw:
                        setTabSelection(0);
                        break;
                    case R.id.rbTjfx:
                        setTabSelection(1);
                        break;
                }
            }
        });
    }

    public void setTabSelection(int position) {
        //记录position
        this.position = position;
        //更改导航栏按钮状态
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                rbXrw.setChecked(true);
                settingsTitle(0);
                if (xrwFragment == null) {
                    xrwFragment = new XrwFragment();
                    transaction.add(R.id.mainF, xrwFragment, "xrwFragment");
                } else {
                    transaction.show(xrwFragment);

                }
                break;

            case 1:
                settingsTitle(1);
                rbTjfx.setChecked(true);
                if (tjfxFragment == null) {
                    tjfxFragment = new TjfxFragment();
                    transaction.add(R.id.mainF, tjfxFragment, "tjfxFragment");
                } else {

                    transaction.show(tjfxFragment);

                }
                break;
        }
        transaction.commit();
    }

    public void settingsTitle(int i){
        if ( i == 0){
            tvTitle.setText("新任务");
            ivSs.setVisibility(View.VISIBLE);
            ivSelece.setVisibility(View.VISIBLE);
        }else {
            tvTitle.setText("统计分析");
            ivSs.setVisibility(View.GONE);
            ivSelece.setVisibility(View.GONE);
        }

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (xrwFragment != null)
            transaction.hide(xrwFragment);
        if (tjfxFragment != null)
            transaction.hide(tjfxFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        XrwFragment xrwFragment = (XrwFragment) getSupportFragmentManager().findFragmentByTag("xrwFragment");
        switch (v.getId()){

            case R.id.ivSs:
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                break;
            case R.id.civHead:
                startActivity(new Intent(MainActivity.this,UserActivity.class));
                break;
            case R.id.ivSelect:
                setGoneOrVisi(0);
                xrwFragment.seceleAll(1);
                break;

            case R.id.tvNo:
                setGoneOrVisi(1);
                xrwFragment.seceleAll(0);
                break;

            case R.id.tvYse:
                setGoneOrVisi(1);
                xrwFragment.seceleAll(0);
                break;
        }
    }

    public void setGoneOrVisi(int i){
        if ( i == 0 ){
            mainRg.setVisibility(View.GONE);
            llDx.setVisibility(View.VISIBLE);
            civHead.setVisibility(View.GONE);
            ivSs.setVisibility(View.GONE);
            ivSelece.setVisibility(View.GONE);
            tvTitle.setText("选择任务");
        }else if ( i == 1 ){
            mainRg.setVisibility(View.VISIBLE);
            llDx.setVisibility(View.GONE);
            civHead.setVisibility(View.VISIBLE);
            ivSs.setVisibility(View.VISIBLE);
            ivSelece.setVisibility(View.VISIBLE);
            tvTitle.setText("新任务");
        }
    }
}

package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.cfsz.dagger.component.DaggerYwcDetaileComponent;
import com.sx.cfsz.cfsz.dagger.module.YwcDetailsModule;
import com.sx.cfsz.cfsz.presenter.YwcDetailsPresenter;
import com.sx.cfsz.cfsz.ui.adapter.GridViewAdapter;
import com.sx.cfsz.cfsz.ui.adapter.LvMp4Adapter;
import com.sx.cfsz.cfsz.ui.adapter.YwcGVAdapter;
import com.sx.cfsz.cfsz.ui.adapter.YwcSPLVAdapter;
import com.sx.cfsz.cfsz.ui.xrw.PictureSelectorConfig;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.jzvd.JZVideoPlayer;

/***       Author  shy              反馈
 *         Time   2018/4/12 0012    14:41      */

public class YwcDetailsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Inject
    YwcDetailsPresenter presenter;

    private ImageView ivBack;
    private TextView tvAjh;
    private TextView tvAddres;
    private TextView tvQssj;
    private TextView tvJzsj;
    private TextView tvZpr;
    private TextView tvRwnr;
    private ImageView ivNavige;
    private TextView tvCzlx;
    private TextView tvDcQssj;
    private TextView tvDcJzsj;
    private TextView tvMsxx;
    private GridView gridView;
    private ListView listView;
    private LinearLayout llBqsj;
    private String feedback_pic;
    private String feedback_video;

    private ArrayList<String> zpList = new ArrayList<>();
    private ArrayList<String> spList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ywc_details);
        DaggerYwcDetaileComponent.builder().ywcDetailsModule(new YwcDetailsModule(this)).build().in(this);
        Intent intent = getIntent();
        initView();

        String task_num = intent.getStringExtra("Task_num");
        String task_address = intent.getStringExtra("Task_address");
        String plan_time_start = intent.getStringExtra("Plan_time_start");
        String plan_time_end = intent.getStringExtra("Plan_time_end");
        String task_content = intent.getStringExtra("Task_content");
        String oldName = intent.getStringExtra("oldName");
        String task_id = intent.getStringExtra("Task_id");
        String task_lat = intent.getStringExtra("Task_lat");
        String task_lng = intent.getStringExtra("Task_lng");
        String red_sign = intent.getStringExtra("Red_sign");
        String task_sfbq = intent.getStringExtra("Task_sfbq");
        String feedback_msg = intent.getStringExtra("Feedback_msg");
        feedback_pic = intent.getStringExtra("Feedback_pic");
        feedback_video = intent.getStringExtra("Feedback_video");
        String sealup_time_start = intent.getStringExtra("Sealup_time_start");
        String sealup_time_end = intent.getStringExtra("Sealup_time_end");
        String task_type = intent.getStringExtra("Task_type");

        tvAjh.setText(task_num);
        tvAddres.setText(task_address);
        tvQssj.setText(plan_time_start);
        tvJzsj.setText(plan_time_end);
        tvRwnr.setText(task_content);
        tvMsxx.setText(feedback_msg);

        tvCzlx.setText(task_type);
        tvDcQssj.setText(splitSj(sealup_time_start));
        tvDcJzsj.setText(splitSj(sealup_time_end));
        if (oldName == null) {
            tvZpr.setText("未转派");
        } else {
            tvZpr.setText(oldName);
        }

        intData();
    }

    private void initView() {
        BaseApplication.addList(this);
        ivBack = findViewById(R.id.iv_ywc_back);
        tvAjh = findViewById(R.id.tv_ywc_ajh);
        tvAddres = findViewById(R.id.tv_ywc_address);
        tvQssj = findViewById(R.id.tv_ywc_qssj);
        tvJzsj = findViewById(R.id.tv_ywc_jzsj);
        tvZpr = findViewById(R.id.tv_ywc_zpr);
        tvRwnr = findViewById(R.id.tv_ywc_rwnr);
        ivNavige = findViewById(R.id.iv_ywc_daohang);
        tvCzlx = findViewById(R.id.tv_ywc_czlx);
        tvDcQssj = findViewById(R.id.tv_ywc_dcQssj);
        tvDcJzsj = findViewById(R.id.tv_ywc_dcJzsj);
        tvMsxx = findViewById(R.id.tvMsxx);
        gridView = findViewById(R.id.ywc_gridView);
        listView = findViewById(R.id.ywc_lvMp4);
        llBqsj = findViewById(R.id.ll_ywc_bqsj);

    }

    private void intData() {
        if (!"".equals(feedback_pic)) {
            String[] zp = feedback_pic.split(",");
            for (int i = 0; i < zp.length; i++) {
                zpList.add(AppConfig.IP + AppConfig.DQZP + zp[i]);
            }
        }
        if (!"".equals(feedback_video)) {
            String[] sp = feedback_video.split(",");
            for (int i = 0; i < sp.length; i++) {
                spList.add(sp[i]);
            }
        }
        intLister();
    }

    private void intLister() {
        YwcGVAdapter ywcGVAdapter = new YwcGVAdapter(YwcDetailsActivity.this, zpList);
        gridView.setAdapter(ywcGVAdapter);

        YwcSPLVAdapter ywcSPLVAdapter = new YwcSPLVAdapter(YwcDetailsActivity.this, spList);
        listView.setAdapter(ywcSPLVAdapter);

        gridView.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        HttpUtils.cancleAllCall(this);
        super.onDestroy();
    }
    public String splitSj(String sj){
        if (sj != null){
            String substring = sj.substring(0, 10);
            return substring;
        }
        return "";
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(YwcDetailsActivity.this, PlusImageActivity.class);
        intent.putExtra("shanchu","000");
        intent.putStringArrayListExtra(AppConfig.IMG_LIST, zpList);
        intent.putExtra(AppConfig.POSITION, position);
        startActivityForResult(intent, AppConfig.REQUEST_CODE_MAIN);
    }
}

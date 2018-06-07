package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.util.UIUtils;
import com.sx.cfsz.cfsz.dagger.component.DaggerSearchActivityComponent;
import com.sx.cfsz.cfsz.dagger.component.DaggerSearchDetaileComponent;
import com.sx.cfsz.cfsz.dagger.module.SearchActivityModule;
import com.sx.cfsz.cfsz.dagger.module.SearchDetailedModule;
import com.sx.cfsz.cfsz.model.RwSsModel;
import com.sx.cfsz.cfsz.presenter.SearchActivityPresenter;
import com.sx.cfsz.cfsz.presenter.SearchDetailePresenter;
import com.sx.cfsz.cfsz.ui.adapter.SearchLvAdapter;

import java.util.List;

import javax.inject.Inject;

/***       Author  shy 
 *         Time   2018/6/6 0006    15:03      */

public class SearchDetailedActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private List<RwSsModel.DataBean.RecordsBean> recordsList;
    private TextView tvNumber;
    private ListView lvSsjg;

    @Inject
    SearchDetailePresenter presenter;
    private String caseId;
    private String ssQssj;
    private String ssJzsj;
    private static final int SSSD = 800;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SSSD:
                    RwSsModel model = (RwSsModel) msg.obj;
                    recordsList = model.getData().getRecords();
                    tvNumber.setText("共搜索到" + recordsList.size() + "条任务！");
                    lvSsjg.setAdapter(new SearchLvAdapter(SearchDetailedActivity.this, recordsList));

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_search_deaile);
        DaggerSearchDetaileComponent.builder().searchDetailedModule(new SearchDetailedModule(this)).build().in(this);
        intView();
        initData();

    }

    private void intView() {
        LinearLayout llBack = findViewById(R.id.llSdBack);
        llBack.setOnClickListener(this);
        tvNumber = findViewById(R.id.tv_number);
        lvSsjg = findViewById(R.id.lv_ssjg);
        lvSsjg.setOnItemClickListener(this);

        Intent intent = getIntent();
        caseId = intent.getStringExtra("caseId");
        ssQssj = intent.getStringExtra("ssQssj");
        ssJzsj = intent.getStringExtra("ssJzsj");

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getData(BaseApplication.get("userId", ""), caseId, ssQssj, ssJzsj);
    }

    private void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llSdBack:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (recordsList.get(position).getTask_status()) {
            case "4":   //待执行
                Intent dzxIntent = new Intent(SearchDetailedActivity.this, DzxDetailsActivity.class);
                String dzxName = recordsList.get(position).getOld_name();
                if (dzxName != null) {
                    dzxIntent.putExtra("oldName", dzxName);
                }
                dzxIntent.putExtra("Task_num", recordsList.get(position).getTask_num());
                dzxIntent.putExtra("Task_address", recordsList.get(position).getTask_address());
                dzxIntent.putExtra("Plan_time_start", recordsList.get(position).getPlan_time_start());
                dzxIntent.putExtra("Plan_time_end", recordsList.get(position).getPlan_time_end());
                dzxIntent.putExtra("Task_content", recordsList.get(position).getTask_content());
                dzxIntent.putExtra("Task_id", recordsList.get(position).getTask_id());
                dzxIntent.putExtra("Task_lat", recordsList.get(position).getTask_lat());
                dzxIntent.putExtra("Task_lng", recordsList.get(position).getTask_lng());
                dzxIntent.putExtra("Red_sign", recordsList.get(position).getRed_sign());
                startActivityForResult(dzxIntent, 20);
                break;
            case "5":   //执行中
                Intent zxzIntent = new Intent(SearchDetailedActivity.this, ZxzDetailsActivity.class);
                String zxzName = recordsList.get(position).getOld_name();
                if (zxzName != null) {
                    zxzIntent.putExtra("oldName", zxzName);
                }
                zxzIntent.putExtra("Task_num", recordsList.get(position).getTask_num());
                zxzIntent.putExtra("Task_address", recordsList.get(position).getTask_address());
                zxzIntent.putExtra("Plan_time_start", recordsList.get(position).getPlan_time_start());
                zxzIntent.putExtra("Plan_time_end", recordsList.get(position).getPlan_time_end());
                zxzIntent.putExtra("Task_content", recordsList.get(position).getTask_content());
                zxzIntent.putExtra("Task_lat", recordsList.get(position).getTask_lat());
                zxzIntent.putExtra("Task_lng", recordsList.get(position).getTask_lng());
                zxzIntent.putExtra("Result", recordsList.get(position).getResult());
                zxzIntent.putExtra("Task_sfbq", recordsList.get(position).getTask_sfbq());
                zxzIntent.putExtra("Task_id", recordsList.get(position).getTask_id());
                zxzIntent.putExtra("Red_sign", recordsList.get(position).getRed_sign());
                startActivityForResult(zxzIntent, 30);
                break;
            case "6":   //已完成
                Intent ywcintent = new Intent(SearchDetailedActivity.this, YwcDetailsActivity.class);
                String ywcName = recordsList.get(position).getOld_name();
                if (ywcName != null) {
                    ywcintent.putExtra("oldName", ywcName);
                }

                ywcintent.putExtra("Task_num", recordsList.get(position).getTask_num());
                ywcintent.putExtra("Task_address", recordsList.get(position).getTask_address());
                ywcintent.putExtra("Plan_time_start", recordsList.get(position).getPlan_time_start());
                ywcintent.putExtra("Plan_time_end", recordsList.get(position).getPlan_time_end());
                ywcintent.putExtra("Task_content", recordsList.get(position).getTask_content());
                ywcintent.putExtra("Task_id", recordsList.get(position).getTask_id());
                ywcintent.putExtra("Task_lat", recordsList.get(position).getTask_lat());
                ywcintent.putExtra("Task_lng", recordsList.get(position).getTask_lng());
                ywcintent.putExtra("Red_sign", recordsList.get(position).getRed_sign());
                ywcintent.putExtra("Task_sfbq", recordsList.get(position).getTask_sfbq());
                ywcintent.putExtra("Sealup_time_start", recordsList.get(position).getSealup_time_start());
                ywcintent.putExtra("Sealup_time_end", recordsList.get(position).getSealup_time_end());
                ywcintent.putExtra("Feedback_msg", recordsList.get(position).getFeedback_msg());
                ywcintent.putExtra("Feedback_pic", recordsList.get(position).getFeedback_pic());
                ywcintent.putExtra("Feedback_video", recordsList.get(position).getFeedback_video());
                ywcintent.putExtra("Task_type", recordsList.get(position).getTask_type());
                ywcintent.putExtra("Task_result_message", recordsList.get(position).getTask_result_message());
                startActivity(ywcintent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //单个执行后返回到本界面并刷新
        if (requestCode == 20 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String state = bundle.getString("state");
            if ("20".equals(state)) {
                //执行后
//                presenter.getData(1, AppConfig.ROWSNUMBER,1);
            }
        }
        if (requestCode == 30 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String state = bundle.getString("state");
            if ("30".equals(state)) {
                //反馈后
//                presenter.getData(1, AppConfig.ROWSNUMBER,1);
            }
        }
    }


    public void success(RwSsModel model) {
        Message msg = Message.obtain();
        msg.what = SSSD;
        msg.obj = model;
        mHandler.sendMessage(msg);
    }
}

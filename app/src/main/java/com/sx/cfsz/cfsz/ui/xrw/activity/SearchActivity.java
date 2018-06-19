package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.util.UIUtils;
import com.sx.cfsz.cfsz.dagger.component.DaggerSearchActivityComponent;
import com.sx.cfsz.cfsz.dagger.module.SearchActivityModule;
import com.sx.cfsz.cfsz.model.RwModel;
import com.sx.cfsz.cfsz.model.RwSsModel;
import com.sx.cfsz.cfsz.presenter.SearchActivityPresenter;
import com.sx.cfsz.cfsz.ui.adapter.YwcLvAdapter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/***       Author  shy              搜索
 *         Time   2018/4/11 0011    10:02      */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    @Inject
    SearchActivityPresenter presenter;
    private static final int SS = 110;
    private LinearLayout llBack;
    private TextView tvQssj;
    private TextView tvJzsj;
    private EditText etName;
    private Button bSs;
    private Calendar calendar;
    String qssj = "";
    String jzsj = "";
    private Button bqc;
    private String userId;
    private String caseId;
    private String ssQssj;
    private String ssjzsj;
    private LinearLayout llQssj, llJzsj;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SS:
                    RwSsModel model = (RwSsModel) msg.obj;
                    List<RwSsModel.DataBean.RecordsBean> recordsList = model.getData().getRecords();
                    if (recordsList.size() != 0) {
                        Intent intent = new Intent(SearchActivity.this, SearchDetailedActivity.class);
                        intent.putExtra("ssQssj",ssQssj);
                        intent.putExtra("ssJzsj",ssjzsj);
                        intent.putExtra("caseId",caseId);
                        startActivity(intent);

                    } else {
                        UIUtils.showToast(SearchActivity.this, "没有符合条件的任务");
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
        setContentView(R.layout.activity_search);
        BaseApplication.addList(this);
        DaggerSearchActivityComponent.builder().searchActivityModule(new SearchActivityModule(this)).build().in(this);
        initView();
//        initData();
        initLisenter();
    }

    private void initData() {
        qssj = get7sj();
        jzsj = getsj();
    }

    private void initLisenter() {
        llBack.setOnClickListener(this);
        llQssj.setOnClickListener(this);
        llJzsj.setOnClickListener(this);
        bqc.setOnClickListener(this);
        bSs.setOnClickListener(this);
        calendar = Calendar.getInstance();

    }

    private void initView() {
        llBack = findViewById(R.id.llSearchBack);
        llQssj = findViewById(R.id.llSearchQssj);
        tvQssj = findViewById(R.id.tvSearchQssj);
        llJzsj = findViewById(R.id.llSearchJzsj);
        tvJzsj = findViewById(R.id.tvSearchJzsj);
        etName = findViewById(R.id.etSearchName);
        bqc = findViewById(R.id.bqc);
        bSs = findViewById(R.id.bSs);
//        tvQssj.setText(get7sj());
//        tvJzsj.setText(getsj());

//        LinearLayout ll1 = findViewById(R.id.ll_1);
//        LinearLayout ll3 = findViewById(R.id.ll_3);
//        TextView tv2 = findViewById(R.id.tv_2);
//        TextView tv4 = findViewById(R.id.tv_4);
//        LinearLayout ll5 = findViewById(R.id.ll_5);
//        TextView tv6 = findViewById(R.id.tv_6);
//        Intent intent = getIntent();
//        state = intent.getIntExtra("state", -2);
//        if (state == 0) {
//            //按时间搜索
//            ll5.setVisibility(View.GONE);
//            tv6.setVisibility(View.GONE);
//
//        } else if (state == 1) {
//            ll1.setVisibility(View.GONE);
//            ll3.setVisibility(View.GONE);
//            tv2.setVisibility(View.GONE);
//            tv4.setVisibility(View.GONE);
//
//        } else if (state == -2) {
//
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llSearchBack:
                finish();
                break;

            case R.id.llSearchQssj:
                showDatePickerDialogqs();
                break;

            case R.id.llSearchJzsj:
                showDatePickerDialogjz();
                break;

            case R.id.bSs:
                userId = BaseApplication.get("userId", "");
                caseId = etName.getText().toString();
                ssQssj = snQssj(tvQssj.getText() + "");
                ssjzsj = snJzsj(tvJzsj.getText() + "");

                presenter.search(userId, caseId,ssQssj,ssjzsj);

//                if (state == 0) {
//                    presenter.sjSearch(qssj + " 00:00:00", jzsj + " 00:00:00");
//                } else if (state == 1) {
//                    String ajh = etName.getText().toString();
//                    if (!"".equals(ajh)) {
//                        presenter.ajhSearch(ajh);
//                    } else {
//                        UIUtils.showToast(SearchActivity.this, "请输入案件号");
//                    }
//                }
                break;

            case R.id.bqc:
                etName.setText("");
                tvQssj.setText("");
                tvJzsj.setText("");

                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        //待执行
//        if (requestCode == 20 && resultCode == RESULT_OK) {
//            Bundle bundle = data.getExtras();
//            String state = bundle.getString("state");
//            if ("20".equals(state)) {
//                if (this.state == 0) {
//                    //刷新时间搜索数据
//
//                } else if (this.state == 1) {
//                    //案件号搜索
////                    presenter.ajhSearch(ajh);
//                }
//            }
//        }
//        //执行中
//        if (requestCode == 30 && resultCode == RESULT_OK) {
//            Bundle bundle = data.getExtras();
//            String state = bundle.getString("state");
//            if ("30".equals(state)) {
//                if (this.state == 0) {
//                    //刷新时间搜索数据
//
//                } else if (this.state == 1) {
//                    //案件号搜索
////                    presenter.ajhSearch(ajh);
//                }
//            }
//        }
    }

    //起始时间dialog
    private void showDatePickerDialogqs() {
        Calendar cal = Calendar.getInstance();
        final DatePickerDialog qsDialog = new DatePickerDialog(this, null,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        //手动设置按钮
        qsDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = qsDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();

                String qsMonth = "";
                if (month < 10) {
                    qsMonth = "0" + month;
                } else {
                    qsMonth = month + "";
                }
                qssj = year + "-" + qsMonth + "-" + day;
                tvQssj.setText(qssj);
            }
        });
        //取消按钮，如果不需要直接不设置即可
        qsDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                qsDialog.dismiss();
            }
        });
        qsDialog.show();

    }

    //截至时间dialog
    private void showDatePickerDialogjz() {
        Calendar cal = Calendar.getInstance();
        final DatePickerDialog jzDialog = new DatePickerDialog(this, null,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        //手动设置按钮
        jzDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = jzDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();

                String jzMonth = "";
                if (month < 10) {
                    jzMonth = "0" + month;
                } else {
                    jzMonth = month + "";
                }
                jzsj = year + "-" + jzMonth + "-" + day;
                tvJzsj.setText(jzsj);
            }
        });
        //取消按钮，如果不需要直接不设置即可
        jzDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                jzDialog.dismiss();
            }
        });

        jzDialog.show();
    }

    //过去7天的日期
    public String get7sj() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        Date d = c.getTime();
        String day = format.format(d);
        System.out.println("过去七天：" + day);
        return day;
    }

    //当前时间
    public String getsj() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(day));

        return df.format(day);
    }

    public void success(RwSsModel model) {
        Message msg = Message.obtain();
        msg.what = SS;
        msg.obj = model;
        mHandler.sendMessage(msg);
    }

    public String snQssj(String sj) {
        if ("".equals(sj)) {
            return "";
        }
        return tvQssj.getText() + " 00:00:00";
    }

    public String snJzsj(String sj) {
        if ("".equals(sj)) {
            return "";
        }
        return tvJzsj.getText() + " 24:00:00";
    }

    @Override
    protected void onDestroy() {
        HttpUtils.cancleAllCall(this);
        super.onDestroy();
    }


}

package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.util.UIUtils;
import com.sx.cfsz.cfsz.dagger.component.DaggerSearchActivityComponent;
import com.sx.cfsz.cfsz.dagger.module.SearchActivityModule;
import com.sx.cfsz.cfsz.model.RwModel;
import com.sx.cfsz.cfsz.presenter.SearchActivityPresenter;
import com.sx.cfsz.cfsz.ui.adapter.YwcLvAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/***       Author  shy              搜索
 *         Time   2018/4/11 0011    10:02      */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    SearchActivityPresenter presenter;
    private static final int SS = 110;
    private ImageView ivBack;
    private TextView tvQssj;
    private TextView tvJzsj;
    private EditText etName;
    private Button bSs;
    private Calendar calendar;
    private ImageView ivQssj;
    private ImageView ivJzsj;
    String qssj = "";
    String jzsj = "";
    private LinearLayout llQssj, llJzsj;
    private int state;
    private LinearLayout llAjh;
    private LinearLayout llSjss;
    private ListView lvSearch;
    private Button bAjhSs;
    private String ajh;
    private List<RwModel.DataBean.RecordsBean> recordsList;



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SS:
                    RwModel model = (RwModel) msg.obj;
                    recordsList = model.getData().getRecords();
                    if (recordsList.size() != 0){
                        YwcLvAdapter adapter = new YwcLvAdapter(SearchActivity.this, recordsList);
                        lvSearch.setAdapter(adapter);
                    }else {
                        UIUtils.showToast(SearchActivity.this,"没有符合条件的任务");
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
        initData();
        initLisenter();
    }

    private void initData() {
        qssj = get7sj();
        jzsj = getsj();
    }

    private void initLisenter() {
        ivBack.setOnClickListener(this);
        ivQssj.setOnClickListener(this);
        llQssj.setOnClickListener(this);
        tvQssj.setOnClickListener(this);
        ivJzsj.setOnClickListener(this);
        llJzsj.setOnClickListener(this);
        tvJzsj.setOnClickListener(this);
        bSs.setOnClickListener(this);
        bAjhSs.setOnClickListener(this);
        calendar = Calendar.getInstance();
        lvSearch.setOnItemClickListener(this);
    }

    private void initView() {
        ivBack = findViewById(R.id.ivSearchBack);
        ivQssj = findViewById(R.id.ivSearchQssj);
        llQssj = findViewById(R.id.llSearchQssj);
        tvQssj = findViewById(R.id.tvSearchQssj);
        ivJzsj = findViewById(R.id.ivSearchJzsj);
        llJzsj = findViewById(R.id.llSearchJzsj);
        tvJzsj = findViewById(R.id.tvSearchJzsj);
        etName = findViewById(R.id.etSearchName);
        llAjh = findViewById(R.id.ll_anjianhao);
        llSjss = findViewById(R.id.ll_sjss);
        lvSearch = findViewById(R.id.lv_search);

        bSs = findViewById(R.id.bSjss);
        bAjhSs = findViewById(R.id.bAjhSs);
        tvQssj.setText(get7sj());
        tvJzsj.setText(getsj());

        Intent intent = getIntent();
        state = intent.getIntExtra("state", -2);
        if (state == 0) {
            //按时间搜索
            llAjh.setVisibility(View.GONE);
            llSjss.setVisibility(View.VISIBLE);
            bSs.setVisibility(View.VISIBLE);
        } else if (state == 1) {
            llAjh.setVisibility(View.VISIBLE);
            llSjss.setVisibility(View.GONE);
            bSs.setVisibility(View.GONE);
        } else if (state == -2) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSearchBack:
                finish();
                break;

            case R.id.ivSearchQssj:
                showDatePickerDialogqs();
                break;

            case R.id.llSearchQssj:
                showDatePickerDialogqs();
                break;

            case R.id.ivSearchJzsj:
                showDatePickerDialogjz();
                break;

            case R.id.llSearchJzsj:
                showDatePickerDialogjz();
                break;

            case R.id.bSjss:
                presenter.sjSearch(qssj + " 00:00:00", jzsj + " 00:00:00");
                break;

            case R.id.bAjhSs:
                ajh = etName.getText().toString();
                if (!"".equals(ajh)){
                    presenter.ajhSearch(ajh);
                }else {
                    UIUtils.showToast(SearchActivity.this, "请输入案件号");
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (recordsList.get(position).getTask_status()){
            case "4":   //待执行

                Intent dzxIntent = new Intent(SearchActivity.this, DzxDetailsActivity.class);
                String dzxoldName = recordsList.get(position).getOld_name();
                if (dzxoldName != null) {
                    dzxIntent.putExtra("oldName", dzxoldName);
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
                startActivityForResult(dzxIntent,20);

                break;
            case "5":   //执行中

                Intent zxzIntent = new Intent(SearchActivity.this, ZxzDetailsActivity.class);
                String zxzoldName = recordsList.get(position).getOld_name();
                if (zxzoldName != null) {
                    zxzIntent.putExtra("oldName", zxzoldName);
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
                zxzIntent.putExtra("Task_id",recordsList.get(position).getTask_id());
                startActivityForResult(zxzIntent,30);

                break;
            case "6":   //已完成

                Intent intent = new Intent(SearchActivity.this, YwcDetailsActivity.class);
                String oldName = recordsList.get(position).getOld_name();
                if (oldName != null) {
                    intent.putExtra("oldName", oldName);
                }
                intent.putExtra("Task_num", recordsList.get(position).getTask_num());
                intent.putExtra("Task_address", recordsList.get(position).getTask_address());
                intent.putExtra("Plan_time_start", recordsList.get(position).getPlan_time_start());
                intent.putExtra("Plan_time_end", recordsList.get(position).getPlan_time_end());
                intent.putExtra("Task_content", recordsList.get(position).getTask_content());
                intent.putExtra("Task_id", recordsList.get(position).getTask_id());
                intent.putExtra("Task_lat", recordsList.get(position).getTask_lat());
                intent.putExtra("Task_lng", recordsList.get(position).getTask_lng());
                intent.putExtra("Red_sign", recordsList.get(position).getRed_sign());
                intent.putExtra("Task_sfbq", recordsList.get(position).getTask_sfbq());
                intent.putExtra("Sealup_time_start",recordsList.get(position).getSealup_time_start());
                intent.putExtra("Sealup_time_end",recordsList.get(position).getSealup_time_end());
                intent.putExtra("Feedback_msg",recordsList.get(position).getFeedback_msg());
                intent.putExtra("Feedback_pic",recordsList.get(position).getFeedback_pic());
                intent.putExtra("Feedback_video",recordsList.get(position).getFeedback_video());
                startActivity(intent);

                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //待执行
        if (requestCode == 20 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String state = bundle.getString("state");
            if ("20".equals(state)){
                if (this.state == 0){
                    //刷新时间搜索数据

                }else if (this.state == 1){
                    //案件号搜索
                    presenter.ajhSearch(ajh);
                }
            }
        }
        //执行中
        if (requestCode == 30 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String state = bundle.getString("state");
            if ("30".equals(state)){
                if (this.state == 0){
                    //刷新时间搜索数据

                }else if (this.state == 1){
                    //案件号搜索
                    presenter.ajhSearch(ajh);
                }
            }
        }
    }


    //起始时间dialog
    private void showDatePickerDialogqs() {
//        new DatePickerDialog(SearchActivity.this, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                qssj = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
//                tvQssj.setText(qssj);
//            }
//        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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

    public void success(RwModel model) {
        Message msg = Message.obtain();
        msg.what = SS;
        msg.obj = model;
        mHandler.sendMessage(msg);
    }
    @Override
    protected void onDestroy() {
        HttpUtils.cancleAllCall(this);
        super.onDestroy();
    }


}

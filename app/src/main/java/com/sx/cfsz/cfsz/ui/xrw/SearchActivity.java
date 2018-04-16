package com.sx.cfsz.cfsz.ui.xrw;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.BaseApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***       Author  shy              搜索
 *         Time   2018/4/11 0011    10:02      */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack;
    private TextView tvQssj;
    private TextView tvJzsj;
    private EditText etName;
    private Button bCz;
    private Button bSs;
    private Calendar calendar;
    private ImageView ivQssj;
    private ImageView ivJzsj;
    String qssj = "";
    String jzsj = "";
    private LinearLayout llQssj,llJzsj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BaseApplication.addList(this);
        initView();
        initLisenter();
    }

    private void initLisenter() {
        ivBack.setOnClickListener(this);
        ivQssj.setOnClickListener(this);
        llQssj.setOnClickListener(this);
        tvQssj.setOnClickListener(this);
        ivJzsj.setOnClickListener(this);
        llJzsj.setOnClickListener(this);
        tvJzsj.setOnClickListener(this);
        bCz.setOnClickListener(this);
        bSs.setOnClickListener(this);
        calendar = Calendar.getInstance();
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
        bCz = findViewById(R.id.bCz);
        bSs = findViewById(R.id.bSs);
        tvQssj.setText(get7sj());
        tvJzsj.setText(getsj());
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

            case R.id.bCz:
                tvQssj.setText(get7sj());
                tvJzsj.setText(getsj());
                etName.clearComposingText();
                break;
            case R.id.bSs:


                break;
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
                if (month < 10){
                    qsMonth = "0" + month;
                }else {
                    qsMonth = month + "";
                }
                qssj = year + "/" + qsMonth + "/" + day;
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
                if (month < 10){
                    jzMonth = "0" + month;
                }else {
                    jzMonth= month + "";
                }
                jzsj = year + "/" + jzMonth + "/" + day;
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(df.format(day));

        return df.format(day);
    }
}

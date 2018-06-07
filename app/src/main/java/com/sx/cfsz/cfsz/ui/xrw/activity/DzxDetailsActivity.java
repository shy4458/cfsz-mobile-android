package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.util.UIUtils;
import com.sx.cfsz.cfsz.dagger.component.DaggerDzxDetailsComponent;
import com.sx.cfsz.cfsz.dagger.module.DzxDetailsModule;
import com.sx.cfsz.cfsz.model.SubmitModel;
import com.sx.cfsz.cfsz.presenter.DzxDetailsPresenter;

import java.io.File;

import javax.inject.Inject;

/***       Author  shy              待执行添加到执行
 *         Time   2018/4/12 0012    14:41      */

public class DzxDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    DzxDetailsPresenter presenter;
    private static final int SUBMIT = 100;
    private static final int SUCCRED = 1000;

    private LinearLayout ivBack;
    private TextView tvImplement;
    private TextView tvAjh;
    private TextView tvAddres;
    private TextView tvQssj;
    private TextView tvJzsj;
    private TextView tvZpr;
    private TextView tvRwnr;
    private String task_id;
    private String task_lat;
    private String task_lng;
    private ImageView ivNavige;
    private String red_sign;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUBMIT:
                    SubmitModel submitModel = (SubmitModel) msg.obj;
                    UIUtils.showToast(DzxDetailsActivity.this,submitModel.getMessage());
                    Intent intent = new Intent();
                    intent.putExtra("state","20");
                    DzxDetailsActivity.this.setResult(RESULT_OK,intent);
                    finish();
                    break;
                case SUCCRED:
                    String str = (String) msg.obj;


                    break;
                default:
                    break;
            }
        }
    };
    private String task_address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dzx_details);
        BaseApplication.addList(this);
        DaggerDzxDetailsComponent.builder().dzxDetailsModule(new DzxDetailsModule(this)).build().in(this);

        initView();
        Intent intent = getIntent();
        String task_num = intent.getStringExtra("Task_num");
        task_address = intent.getStringExtra("Task_address");
        String plan_time_start = intent.getStringExtra("Plan_time_start");
        String plan_time_end = intent.getStringExtra("Plan_time_end");
        String task_content = intent.getStringExtra("Task_content");
        String oldName = intent.getStringExtra("oldName");
        task_id = intent.getStringExtra("Task_id");
        task_lat = intent.getStringExtra("Task_lat");
        task_lng = intent.getStringExtra("Task_lng");
        red_sign = intent.getStringExtra("Red_sign");
        if (oldName == null) {
            tvZpr.setText("未转派");
        } else {
            tvZpr.setText(oldName);
        }
        tvAjh.setText(task_num);
        tvAddres.setText(task_address);
        tvQssj.setText(plan_time_start);
        tvJzsj.setText(plan_time_end);
        tvRwnr.setText(task_content);

        if ("1".equals(red_sign)){
            presenter.remoRed(task_id,red_sign);
        }
    }

    private void initView() {
        ivBack = findViewById(R.id.ll_dzx_back);
        tvImplement = findViewById(R.id.tv_dzx_implement);
        tvAjh = findViewById(R.id.tv_dzx_ajh);
        tvAddres = findViewById(R.id.tv_dzx_addres);
        tvQssj = findViewById(R.id.tv_dzx_qssj);
        tvJzsj = findViewById(R.id.tv_dzx_zjsj);
        tvZpr = findViewById(R.id.tv_dzx_zpr);
        tvRwnr = findViewById(R.id.tv_dzx_rwnr);
        ivNavige = findViewById(R.id.iv_navige);
        ivBack.setOnClickListener(this);
        tvImplement.setOnClickListener(this);
        ivNavige.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_dzx_back:
                if ("1".equals(red_sign)) {
                    Intent intent = new Intent();
                    intent.putExtra("state", "20");
                    DzxDetailsActivity.this.setResult(RESULT_OK, intent);
                    finish();
                }
                finish();
                break;
            case R.id.tv_dzx_implement:
                presenter.submit(task_id,red_sign);
                break;
            case R.id.iv_navige:
                //调起高德导航
                if (isInstallByRead("com.autonavi.minimap")) {
                    goToNaviActivity(DzxDetailsActivity.this, "", task_address, task_lat, task_lng, "0", "2");
                }else {
                    UIUtils.showToast(DzxDetailsActivity.this,"未安装高德地图客户端");
                }
                break;
        }
    }

    public void success(SubmitModel submitModel) {
        Message msg = Message.obtain();
        msg.what = SUBMIT;
        msg.obj = submitModel;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {

        }
    }

    /**
     * 启动高德App进行导航
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:58
     * <h3>UpdateTime</h3> 2016/6/27,13:58
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname           非必填 POI 名称
     * @param lat               必填 纬度
     * @param lon               必填 经度
     * @param dev               必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style             必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */

    public static void goToNaviActivity(Context context, String sourceApplication, String poiname, String lat, String lon, String dev, String style) {
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=")
                .append(sourceApplication);
        if (!TextUtils.isEmpty(poiname)) {
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);

        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 根据包名检测某个APP是否安装
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:02
     * <h3>UpdateTime</h3> 2016/6/27,13:02
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     * @param packageName 包名
     * @return true 安装 false 没有安装
     */
    public static boolean isInstallByRead(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    @Override
    protected void onDestroy() {
        HttpUtils.cancleAllCall(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if ("1".equals(red_sign)) {
            Intent intent = new Intent();
            intent.putExtra("state", "20");
            DzxDetailsActivity.this.setResult(RESULT_OK, intent);
            finish();
        }else {
            super.onBackPressed();
        }
    }

    public void successRed(String str) {
        Message msg = Message.obtain();
        msg.what = SUCCRED;
        msg.obj = str;
        mHandler.sendMessage(msg);
    }
}

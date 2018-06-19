package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sx.cfsz.BuildConfig;
import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.util.JumpPermissionManagement;
import com.sx.cfsz.baseframework.util.UIUtils;
import com.sx.cfsz.cfsz.dagger.component.DaggerZxzDetailsComponent;
import com.sx.cfsz.cfsz.dagger.module.ZxzDetailsModule;
import com.sx.cfsz.cfsz.model.ZpFeedModel;
import com.sx.cfsz.cfsz.presenter.ZxzDetailsPresenter;
import com.sx.cfsz.cfsz.ui.MainActivity;
import com.sx.cfsz.cfsz.ui.adapter.GridViewAdapter;
import com.sx.cfsz.cfsz.ui.adapter.LvMp4Adapter;
import com.sx.cfsz.cfsz.ui.myView.CustomDialog;
import com.sx.cfsz.cfsz.ui.xrw.PictureSelectorConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import cn.jzvd.JZVideoPlayer;

/***       Author  shy              反馈
 *         Time   2018/4/12 0012    14:41      */

public class ZxzDetailsActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    @Inject
    ZxzDetailsPresenter presenter;

    private EditText etMsxx;
    private LinearLayout llBack;
    private TextView tvBack;
    private GridView gridView;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private ArrayList<String> mp4List = new ArrayList<>();  //上传视频的地址
    private HashMap<String, String> mapType = new HashMap<>();  //操作类型和值
    private ArrayList<String> listType = new ArrayList<>(); //所有的操作类型
    private GridViewAdapter mGridViewAddImgAdapter;
    private ListView lvMp4;
    private ImageView ivAddMp4;
    private LvMp4Adapter lvMp4Adapter;
    private LinearLayout llGongVisi;
    private TextView tvZpr;
    private TextView tvAjh;
    private TextView tvAddres;
    private TextView tvQssj;
    private TextView tvJzsj;
    private TextView tvRwnr;
    private ImageView ivDaohang;
    private String task_lat;
    private String task_lng;
    private String result;


    private TextView tvDcQssj;
    private TextView tvDcJzsj;
    private LinearLayout llDcQssj;
    private LinearLayout llDcJzsj;
    private String Task_sfbq;

    private static final int SUCCREDZXZ = 503;
    private static final int ZP = 500;
    private static final int SP = 501;
    private static final int CG = 502;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCREDZXZ:
                    String str = (String) msg.obj;

                    break;
                case ZP:
                    ZpFeedModel zpmodel = (ZpFeedModel) msg.obj;
                    BaseApplication.set("zp", zpmodel.getData());
                    presenter.feedSp(mp4List);
                    tvMsg.setText("(2/3)正在上传拍摄的视频");
                    break;

                case SP:
                    ZpFeedModel spmodel = (ZpFeedModel) msg.obj;
                    BaseApplication.set("sp", spmodel.getData());
                    tvMsg.setText("(3/3)正在上传整合信息");
                    presenter.feed(
                            BaseApplication.get("userId", ""),
                            task_id,
                            etMsxx.getText(),
                            BaseApplication.get("zp", ""),
                            BaseApplication.get("sp", ""),
                            strSelecte,
                            snQssj(tvDcQssj.getText() + ""),
                            snJzsj(tvDcJzsj.getText() + "")
                    );
                    break;

                case CG:
                    Intent intent = new Intent();
                    intent.putExtra("state", "30");
                    ZxzDetailsActivity.this.setResult(RESULT_OK, intent);
                    finish();
                    feedDialog.dismiss();
                    UIUtils.showToast(ZxzDetailsActivity.this, "反馈成功");
                    break;

                default:
                    break;
            }
        }
    };

    private String task_id;
    private String strSelecte = "";
    private LinearLayout llCzlx;
    private TextView tvCzlx;
    private TextView tvMsg;
    private Dialog feedDialog;
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxz_details);
        DaggerZxzDetailsComponent.builder().zxzDetailsModule(new ZxzDetailsModule(this)).build().in(this);
        initView();
        Intent intent = getIntent();
        String task_num = intent.getStringExtra("Task_num");
        String task_address = intent.getStringExtra("Task_address");
        String plan_time_start = intent.getStringExtra("Plan_time_start");
        String plan_time_end = intent.getStringExtra("Plan_time_end");
        String task_content = intent.getStringExtra("Task_content");
        String oldName = intent.getStringExtra("oldName");
        task_lat = intent.getStringExtra("Task_lat");
        task_lng = intent.getStringExtra("Task_lng");
        result = intent.getStringExtra("Result");
        Task_sfbq = intent.getStringExtra("Task_sfbq");
        task_id = intent.getStringExtra("Task_id");
        String red_sign = intent.getStringExtra("Red_sign");
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

        if ("1".equals(red_sign)) {
            presenter.remoRed(task_id, red_sign);
        }
        initSpi();
    }

    private void initView() {
        BaseApplication.addList(this);
        llBack = findViewById(R.id.llFeedbackBack);
        tvBack = findViewById(R.id.tvFeedback);
        etMsxx = findViewById(R.id.etMsxx);
        SpannableString ss = new SpannableString("请在这里输入反馈信息(最多200字)...");//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etMsxx.setHint(new SpannedString(ss));
        gridView = findViewById(R.id.gridView);
        lvMp4 = findViewById(R.id.lvMp4);
        ivAddMp4 = findViewById(R.id.ivAddMp4);
        llGongVisi = findViewById(R.id.ll_gong_visi);
        tvZpr = findViewById(R.id.tv_zxz_zpr);
        tvAjh = findViewById(R.id.tv_zxz_ajh);
        tvAddres = findViewById(R.id.tv_zxz_address);
        tvQssj = findViewById(R.id.tv_zxz_qssj);
        tvJzsj = findViewById(R.id.tv_zxz_jzsj);
        tvRwnr = findViewById(R.id.tv_zxz_rwnr);
        llCzlx = findViewById(R.id.ll_czlx);
        ivDaohang = findViewById(R.id.iv_zxz_daohang);
        scrollView = findViewById(R.id.scrollView);
//        czlxSpinner = findViewById(R.id.spi);
        tvDcQssj = findViewById(R.id.tv_dcQssj);
        tvDcJzsj = findViewById(R.id.tv_dcJzsj);
        llDcQssj = findViewById(R.id.ll_dcQssj);
        llDcJzsj = findViewById(R.id.ll_dcJzsj);
        tvCzlx = findViewById(R.id.tv_czlx);

        llCzlx.setOnClickListener(this);
        llBack.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        ivAddMp4.setOnClickListener(this);
        ivDaohang.setOnClickListener(this);
        llDcJzsj.setOnClickListener(this);
        llDcQssj.setOnClickListener(this);

        mGridViewAddImgAdapter = new GridViewAdapter(ZxzDetailsActivity.this, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(this);
        lvMp4Adapter = new LvMp4Adapter(ZxzDetailsActivity.this, mp4List);
        lvMp4.setAdapter(lvMp4Adapter);

        initPower();
    }

    private void initSpi() {
        //        "(账户冻结,2);(轮候冻结,3);(财产不实,100);(办理失败,101)"
        String[] str = result.split(";");

        for (int i = 0; i < str.length; i++) {
            String[] split = str[i].substring(1, str[i].length() - 1).split(",");
            mapType.put(split[0], split[1]);
        }
        for (String type : mapType.keySet()) {
            listType.add(type);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llFeedbackBack:
                finish();
                break;
            case R.id.tvFeedback:
                if ("".equals(strSelecte)) {
                    UIUtils.showToast(ZxzDetailsActivity.this, "请选择类型");
                } else {
                    //比较起止时间 截至时间>起始时间
                    String qs = new String(tvDcQssj.getText() + "");
                    String jz = new String(tvDcJzsj.getText() + "");
                    if (!"".equals(qs)){
                        //起始时间不为空
                        if (!"".equals(jz)){
                            //起始时间 和 截止时间 都为空时比较大小
                            Long sQs = Long.valueOf(qs.replaceAll("-", ""));
                            Long sJz = Long.valueOf(jz.replaceAll("-", ""));
                            if (sQs <= sJz) {
                                feedDialog();
                                presenter.feedZp(mPicList);
                            }else {
                                UIUtils.showToast(ZxzDetailsActivity.this,"结束时间应该大于开始时间,请重新选择");
                            }
                        }else {
                            // 起始时间不为空  截至时间为空 不比较 直接提交
                            feedDialog();
                            presenter.feedZp(mPicList);
                        }
                    }else {
                        //起始时间为空 不比较大小 直接提交
                        feedDialog();
                        presenter.feedZp(mPicList);
                    }
                }
                break;

            case R.id.ivAddMp4:
                //TODO 视频权限
                boolean camera = ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
                boolean write = ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                boolean record = ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
                if (camera && write && record) {
                    Intent intent = new Intent(ZxzDetailsActivity.this, VideoActivity.class);
                    startActivityForResult(intent, 10);
                } else {
                    UIUtils.showToast(ZxzDetailsActivity.this, "请添加访问权限");
                    JumpPermissionManagement.GoToSetting(ZxzDetailsActivity.this);
                }
                break;
            case R.id.iv_zxz_daohang:
                //调起高德导航
                if (isInstallByRead("com.autonavi.minimap")) {
                    goToNaviActivity(ZxzDetailsActivity.this, "", "", task_lat, task_lng, "0", "2");
                } else {
                    UIUtils.showToast(ZxzDetailsActivity.this, "未安装高德地图客户端");
                }
                break;

            case R.id.ll_dcQssj:
                showDcDialogqs();

                break;
            case R.id.ll_dcJzsj:
                showDcDialogjz();
                break;

            case R.id.ll_czlx:
                ShowSingleDialog(which);

                break;
        }
    }

    //由Adapter调用
    public void setVisibility() {
        ivAddMp4.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO 添加照片权限
        boolean camera = ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean write = ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean record = ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        if (camera && write && record) {
            if (position == parent.getChildCount() - 1) {
                //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过8张，才能点击
                if (mPicList.size() == AppConfig.MAX_SELECT_PIC_NUM) {
                    //最多添加8张图片
                    viewPluImg(position);
                } else {
                    //添加凭证图片
                    selectPic(AppConfig.MAX_SELECT_PIC_NUM - mPicList.size());
                }

            } else {
                viewPluImg(position);
            }

        } else {
            UIUtils.showToast(ZxzDetailsActivity.this, "请添加访问权限");
            JumpPermissionManagement.GoToSetting(ZxzDetailsActivity.this);

        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    break;
            }
        }
        if (requestCode == AppConfig.REQUEST_CODE_MAIN && resultCode == AppConfig.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(AppConfig.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
            int size = mPicList.size();

        }
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String mp4Address = bundle.getString("mp4Url");
            mp4List.add(mp4Address);
            lvMp4Adapter.notifyDataSetChanged();
            if (mp4List.size() >= 2) {
                ivAddMp4.setVisibility(View.GONE);
            }
        }
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
        int size = mPicList.size();

    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(ZxzDetailsActivity.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(AppConfig.IMG_LIST, mPicList);
        intent.putExtra(AppConfig.POSITION, position);
        startActivityForResult(intent, AppConfig.REQUEST_CODE_MAIN);
    }

    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    //启动高德App进行导航
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

    public static boolean isInstallByRead(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    private void initPower() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &
                    ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &
                    ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                    ) {
                ActivityCompat.requestPermissions(ZxzDetailsActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                //权限回调
                break;
            }
        }
    }

    //起始时间dialog
    private void showDcDialogqs() {
        Calendar cal = Calendar.getInstance();
        final DatePickerDialog qsDialog = new DatePickerDialog(this, null,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        final Date date = new Date();
        long time = date.getTime();
        qsDialog.getDatePicker().setMinDate(time -1000);
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
                String qsDay = "";
                if (day < 10) {
                    qsDay = "0" + day;
                } else {
                    qsDay = day + "";
                }

                tvDcQssj.setText(year + "-" + qsMonth + "-" + qsDay);
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
    private void showDcDialogjz() {
        Calendar cal = Calendar.getInstance();
        final DatePickerDialog jzDialog = new DatePickerDialog(this, null,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        Date date = new Date();
        long time = date.getTime();
        jzDialog.getDatePicker().setMinDate(time -1000);
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

                String jzDay = "";
                if (day < 10) {
                    jzDay = "0" + day;
                } else {
                    jzDay = day + "";
                }

                tvDcJzsj.setText(year + "-" + jzMonth + "-" + jzDay);
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

    private int which = -1;
    int yourChoice;

    private void ShowSingleDialog(int item) {
        final String[] items = new String[listType.size()];
        for (int i = 0; i < listType.size(); i++) {
            items[i] = listType.get(i);
        }

        yourChoice = -1;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(ZxzDetailsActivity.this);
        singleChoiceDialog.setTitle("请选择操作类型");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, item,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                        ZxzDetailsActivity.this.which = which;
                        strSelecte = mapType.get(listType.get(which));
                    }
                });

        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            tvCzlx.setText(items[yourChoice]);

                        }
                    }
                });
        singleChoiceDialog.show();
    }

    private void feedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ZxzDetailsActivity.this);
        LayoutInflater inflater = LayoutInflater.from(ZxzDetailsActivity.this);
        View v = inflater.inflate(R.layout.feed_dialog, null);
        tvMsg = v.findViewById(R.id.tv_dialog_msg);
        Button bCancle = v.findViewById(R.id.b_dialog_cancle);

//        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        feedDialog = builder.create();
        feedDialog.setCancelable(false);
        feedDialog.show();
        feedDialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        feedDialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        bCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtils.cancleAllCall(ZxzDetailsActivity.this);
                feedDialog.dismiss();
            }
        });

    }

    public void zpSubmitSuccess(ZpFeedModel zpFeedModel) {
        Message msg = Message.obtain();
        msg.what = ZP;
        msg.obj = zpFeedModel;
        mHandler.sendMessage(msg);
    }

    public void spSubmitSuccess(ZpFeedModel zpFeedModel) {
        Message msg = Message.obtain();
        msg.what = SP;
        msg.obj = zpFeedModel;
        mHandler.sendMessage(msg);
    }

    public void feedSuccess() {
        Message msg = Message.obtain();
        msg.what = CG;
        msg.obj = "";
        mHandler.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        HttpUtils.cancleAllCall(this);
        super.onDestroy();
    }

    public String snQssj(String sj) {
        if ("".equals(sj)) {
            return "";
        }
        return tvDcQssj.getText() + " 00:00:00";
    }

    public String snJzsj(String sj) {
        if ("".equals(sj)) {
            return "";
        }
        return tvDcJzsj.getText() + " 00:00:00";
    }

    public void successRed(String str) {
        Message msg = Message.obtain();
        msg.what = SUCCREDZXZ;
        msg.obj = str;
        mHandler.sendMessage(msg);
    }

}

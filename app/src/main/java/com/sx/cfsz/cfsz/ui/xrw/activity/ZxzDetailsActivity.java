package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.FileInfo;
import com.sx.cfsz.baseframework.http.FileType;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import cn.jzvd.JZVideoPlayer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.Manifest.permission.RECORD_AUDIO;

/***       Author  shy              反馈
 *         Time   2018/4/12 0012    14:41      */

public class ZxzDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    ZxzDetailsPresenter presenter;

    private EditText etMsxx;
    private ImageView ivBack;
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
    private String zxstate;
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

    private Spinner czlxSpinner;
    private TextView tvDcQssj;
    private TextView tvDcJzsj;
    private ImageView ivDcQssj;
    private ImageView ivDcJzsj;
    private String Task_sfbq;


    private static final int ZP = 500;
    private static final int SP = 501;
    private static final int CG = 502;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ZP:
                    ZpFeedModel zpmodel = (ZpFeedModel) msg.obj;
                    BaseApplication.set("zp", zpmodel.getData());
                    presenter.feedSp(mp4List);
                    break;

                case SP:
                    ZpFeedModel spmodel = (ZpFeedModel) msg.obj;
                    BaseApplication.set("sp", spmodel.getData());
                    presenter.feed(
                            AppConfig.userId,
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
                    customDialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra("state", "30");
                    ZxzDetailsActivity.this.setResult(RESULT_OK, intent);
                    finish();
                    UIUtils.showToast(ZxzDetailsActivity.this, "反馈成功");
                    break;

                default:
                    break;
            }
        }
    };

    private String task_id;
    private String strSelecte;
    private CustomDialog customDialog;

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

        initSpi();

    }


    private void initView() {
        BaseApplication.addList(this);
        ivBack = findViewById(R.id.ivFeedbackBack);
        tvBack = findViewById(R.id.tvFeedback);
        etMsxx = findViewById(R.id.etMsxx);
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
        ivDaohang = findViewById(R.id.iv_zxz_daohang);

        czlxSpinner = findViewById(R.id.spi);
        tvDcQssj = findViewById(R.id.tv_dcQssj);
        tvDcJzsj = findViewById(R.id.tv_dcJzsj);
        ivDcQssj = findViewById(R.id.iv_dcQssj);
        ivDcJzsj = findViewById(R.id.iv_dcJzsj);

        ivBack.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        ivAddMp4.setOnClickListener(this);
        ivDaohang.setOnClickListener(this);
        ivDcJzsj.setOnClickListener(this);
        ivDcQssj.setOnClickListener(this);

        mGridViewAddImgAdapter = new GridViewAdapter(ZxzDetailsActivity.this, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(this);

        lvMp4Adapter = new LvMp4Adapter(ZxzDetailsActivity.this, mp4List);
        lvMp4.setAdapter(lvMp4Adapter);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(ZxzDetailsActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ZxzDetailsActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                        1);

            }
        }
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
        ArrayAdapter<String> spiAdapter = new ArrayAdapter<>(ZxzDetailsActivity.this, android.R.layout.simple_spinner_item, listType);

        czlxSpinner.setAdapter(spiAdapter);
        //下拉框选中监听
        czlxSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSelecte = mapType.get(listType.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivFeedbackBack:
                finish();
                break;
            case R.id.tvFeedback:
                customDialog = new CustomDialog(ZxzDetailsActivity.this, R.style.CustomDialog, false);
                customDialog.show();
                presenter.feedZp(mPicList);
                break;

            case R.id.ivAddMp4:
                Intent intent = new Intent(ZxzDetailsActivity.this, VideoActivity.class);
                startActivityForResult(intent, 10);
                break;
            case R.id.iv_zxz_daohang:
                //调起高德导航
                if (isInstallByRead("com.autonavi.minimap")) {
                    goToNaviActivity(ZxzDetailsActivity.this, "", "", task_lat, task_lng, "0", "2");
                } else {
                    UIUtils.showToast(ZxzDetailsActivity.this, "未安装高德地图客户端");
                }
                break;

            case R.id.iv_dcQssj:
                showDcDialogqs();

                break;
            case R.id.iv_dcJzsj:
                showDcDialogjz();
                break;
        }
    }

    //由Adapter调用
    public void setVisibility() {
        ivAddMp4.setVisibility(View.VISIBLE);
    }

    //控制界面的显示与隐藏
    public void setGong(int zxstate) {
        if (zxstate == AppConfig.STETADZX) {
            llGongVisi.setVisibility(View.GONE);
        } else if (zxstate == AppConfig.STETAZXZ) {
            llGongVisi.setVisibility(View.VISIBLE);
        } else if (zxstate == AppConfig.STETAYWC) {
            llGongVisi.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
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
            Log.d("删除后////", "" + size);
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
        Log.d("添加后////", "" + size);
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


    //起始时间dialog
    private void showDcDialogqs() {
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

                tvDcQssj.setText(year + "-" + qsMonth + "-" + day);
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
                tvDcJzsj.setText(year + "-" + jzMonth + "-" + day);
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
        if ("查封开始时间".equals(sj)) {
            return "";
        }
        return tvDcQssj.getText() + " 00:00:00";
    }

    public String snJzsj(String sj) {
        if ("查封结束时间".equals(sj)) {
            return "";
        }
        return tvDcJzsj.getText() + " 00:00:00";
    }
}

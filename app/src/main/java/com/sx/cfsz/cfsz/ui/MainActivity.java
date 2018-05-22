package com.sx.cfsz.cfsz.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.cfsz.dagger.component.DaggerMainActivityComponent;
import com.sx.cfsz.cfsz.dagger.module.MainActivityModule;
import com.sx.cfsz.cfsz.presenter.MainActivityPresenter;
import com.sx.cfsz.cfsz.ui.fgbl.FgblFragment;
import com.sx.cfsz.cfsz.ui.msg.JstxFragment;
import com.sx.cfsz.cfsz.ui.myView.CustomDialog;
import com.sx.cfsz.cfsz.ui.tjfx.TjfxFragment;
import com.sx.cfsz.cfsz.ui.xrw.activity.SearchActivity;
import com.sx.cfsz.cfsz.ui.xrw.activity.UserActivity;
import com.sx.cfsz.cfsz.ui.xrw.fragment.XrwFragment;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import java.security.Guard;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    MainActivityPresenter presenter;

    private CircleImageView civHead;
    private TextView tvTitle;
    private ImageView ivSs;
    private FrameLayout mainF;
    private RadioGroup mainRg;
    private RadioButton rbXrw;
    private RadioButton rbJstx;
    private RadioButton rbTjfx;
    private RadioButton rbFgbl;
    private LinearLayout llDx;
    private TextView tvYse;
    private TextView tvNo;
    public XrwFragment xrwFragment;
    public TjfxFragment tjfxFragment;

    private int which; //搜索类型标识
    private int position = 0;   //导航fangment标识
    private JstxFragment jstxFragment;
    private FgblFragment fgblFragment;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerMainActivityComponent.builder().mainActivityModule(new MainActivityModule(this)).build().in(this);
        BaseApplication.addList(this);
        initView();
        initListener();
    }

    private void initView() {
        civHead = findViewById(R.id.civHead);
        tvTitle = findViewById(R.id.tvTitle);
        ivSs = findViewById(R.id.ivSs);
        mainF = findViewById(R.id.mainF);
        mainRg = findViewById(R.id.mainRg);
        rbXrw = findViewById(R.id.rbXrw);
        rbTjfx = findViewById(R.id.rbTjfx);
        rbJstx = findViewById(R.id.rbJstx);
        rbFgbl = findViewById(R.id.rbFgbl);
        llDx = findViewById(R.id.llDx);
        tvYse = findViewById(R.id.tvYse);
        tvNo = findViewById(R.id.tvNo);

        Intent intent = getIntent();
        int post = intent.getIntExtra("userPost", 99);
        if (post == 1) {
            rbXrw.setVisibility(View.GONE);
            setTabSelection(1);
        } else {
            setTabSelection(0);
        }
        initXg();
    }

    private void initXg() {
        //本次集成采用信鸽的自动集成方式，无法查看服务类，官网查看具体步骤
        XGPushConfig.enableDebug(this, true);
        XGPushManager.registerPush(this, BaseApplication.get("userId", ""),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        Log.d("TPush", "注册成功，设备token为：" + data);
                            BaseApplication.set("token",data.toString());
                        presenter.intTS(BaseApplication.get("userName", ""),BaseApplication.get("token",""));
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                    }
                });


    }

    private void initListener() {
        ivSs.setOnClickListener(this);
        civHead.setOnClickListener(this);
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
                    case R.id.rbJstx:
                        setTabSelection(2);
                        break;
                    case R.id.rbFgbl:
                        setTabSelection(3);
                        break;
                }
            }
        });
    }

    public void setTabSelection(int position) {
        this.position = position;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                settingsTitle(0);
                rbXrw.setChecked(true);
                if (xrwFragment == null) {
                    xrwFragment = new XrwFragment();
                    transaction.add(R.id.mainF, xrwFragment, "xrwFragment");
                } else {
                    transaction.show(xrwFragment);
//                    xrwFragment.refrsh();
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

            case 2:
                settingsTitle(2);
                rbJstx.setChecked(true);
                if (jstxFragment == null) {
                    jstxFragment = new JstxFragment();
                    transaction.add(R.id.mainF, jstxFragment, "jstxFragment");
                } else {
                    transaction.show(jstxFragment);
                }
                break;
            case 3:
                settingsTitle(3);
                rbFgbl.setChecked(true);
                if (fgblFragment == null) {
                    fgblFragment = new FgblFragment();
                    transaction.add(R.id.mainF, fgblFragment, "fgblFragment");
                } else {
                    transaction.show(fgblFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (xrwFragment != null)
            transaction.hide(xrwFragment);
        if (tjfxFragment != null)
            transaction.hide(tjfxFragment);
        if (jstxFragment != null)
            transaction.hide(jstxFragment);
        if (fgblFragment != null)
            transaction.hide(fgblFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        XrwFragment xrwFragment = (XrwFragment) getSupportFragmentManager().findFragmentByTag("xrwFragment");
        switch (v.getId()) {
            //搜索
            case R.id.ivSs:
//                showSingleChoiceDialog();
                showppp();
                break;
            //用户
            case R.id.civHead:
                startActivity(new Intent(MainActivity.this, UserActivity.class));
                llDx.setVisibility(View.GONE);
                mainRg.setVisibility(View.VISIBLE);
                break;
            //多选取消
            case R.id.tvNo:
                llDx.setVisibility(View.GONE);
                mainRg.setVisibility(View.VISIBLE);
                tvTitle.setText("新任务");
                xrwFragment.setAllNo();
                break;
            //多选完成
            case R.id.tvYse:
                xrwFragment.setAllYes();
                llDx.setVisibility(View.GONE);
                mainRg.setVisibility(View.VISIBLE);
                break;
        }
    }

    //切换底部导航 更改title标题
    public void settingsTitle(int i) {
        if (i == 0) {
            tvTitle.setText("新任务");
            ivSs.setVisibility(View.VISIBLE);
        } else if (i == 1) {
            tvTitle.setText("统计分析");
            ivSs.setVisibility(View.GONE);
        } else if (i == 2) {
            tvTitle.setText("即时通讯");
            ivSs.setVisibility(View.GONE);
        } else if (i == 3) {
            tvTitle.setText("法官笔录");
            ivSs.setVisibility(View.GONE);
        } else if (i == 99) {
            tvTitle.setText("选择任务");
            llDx.setVisibility(View.VISIBLE);
            mainRg.setVisibility(View.GONE);
        }
    }

    private void showSingleChoiceDialog() {
        final String[] items = {"按时间搜索", "案件号模糊搜索"};
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(MainActivity.this);
        singleChoiceDialog.setTitle("选择搜索类型");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //选择不同选项的回调
                        MainActivity.this.which = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //确定的回调
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra("state", MainActivity.this.which);
                        startActivity(intent);

                    }
                });
        singleChoiceDialog.show();
    }

    private void showppp() {
        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_ppp, null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        final PopupWindow window = new PopupWindow(contentView, 300, 180, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAsDropDown(ivSs, -200, 40);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        // window.showAtLocation(parent, gravity, x, y);
        LinearLayout llSjSs = contentView.findViewById(R.id.ll_sjss);
        LinearLayout llAhss = contentView.findViewById(R.id.ll_ahss);


        llSjSs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("state", 0);
                startActivity(intent);
                llDx.setVisibility(View.GONE);
                mainRg.setVisibility(View.VISIBLE);
                window.dismiss();
            }
        });
        llAhss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("state", 1);
                startActivity(intent);
                llDx.setVisibility(View.GONE);
                mainRg.setVisibility(View.VISIBLE);
                window.dismiss();
            }
        });
    }

    //对外提供页面切换
    public void setSwitch() {
        tvTitle.setText("新任务");
        llDx.setVisibility(View.GONE);
        mainRg.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        HttpUtils.cancleAllCall(this);
        super.onDestroy();
    }

    //返回键不退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showDialog(Context context) {
        if (customDialog == null) {
            customDialog = new CustomDialog(context, R.style.CustomDialog);
        }
        customDialog.show();
    }

    public void disDialog() {
        customDialog.dismiss();
    }

}

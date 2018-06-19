package com.sx.cfsz.cfsz.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.util.UIUtils;
import com.sx.cfsz.cfsz.dagger.component.DaggerLoginComponent;
import com.sx.cfsz.cfsz.dagger.module.LoginActivityModule;
import com.sx.cfsz.cfsz.model.LoginModel;
import com.sx.cfsz.cfsz.presenter.LoginPresenter;

import javax.inject.Inject;

/***       Author  shy
 *         Time   2018/4/9 0009    16:46
 *
 *         MVP的模式 Dagger解耦 OKhttp网络框架 glide图片加载框架
 *         集成信鸽推送 Ffmpeg4android压缩组建
 *
 *         主界面控制fragment隐藏与显示的方式实现切换
 *
 *         新任务界面三个Fragment大体相同 后期可实现基类
 *         待执行中多选执行功能 由于界面层级较多 各类传递的方法较多
 *         执行中反馈时 出发的网络请求顺势是 1.提交照片 保存返回的路径 2. 提交视频 保存返回的路径  3.整体提交
 *         视频录制采用JZVideoPlayer 可调码率 帧率 确保web可以播放
 *
 *         统计分析 图表主要依靠MPAndroidChart
 *         */

public class LoginActivity extends AppCompatActivity {

    @Inject
    LoginPresenter presenter;
    private Button bLogin;
    private EditText etName;
    private EditText etPwd;

    private static final int LOGIN = 66;
    private static final int LOGMSG = 67;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN:
                    LoginModel loginModel = (LoginModel) msg.obj;
                    BaseApplication.set("userId", loginModel.getData().getUserId());
                    BaseApplication.set("userName", loginModel.getData().getUserName());
                    BaseApplication.set("UserHeadpic", loginModel.getData().getUserHeadpic());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userPost", loginModel.getData().getUserPost());
                    startActivity(intent);
                    finish();
                    break;

                case LOGMSG:
                    String m = (String) msg.obj;
                    UIUtils.showToast(LoginActivity.this,m);

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        DaggerLoginComponent.builder().loginActivityModule(new LoginActivityModule(this)).build().in(this);
        initView();
    }

    private void initView() {
        bLogin = findViewById(R.id.bLogin);
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
//          用此方法设置hint字体大小后 没有dis效果
//        SpannableString name = new SpannableString("用户名");//定义hint的值
//        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
//        name.setSpan(ass, 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        etName.setHint(new SpannedString(name));
//        SpannableString pwd = new SpannableString("密码");//定义hint的值
//        pwd.setSpan(ass, 0, pwd.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        etPwd.setHint(new SpannedString(pwd));

        //禁止换行
        etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(etName.getText().toString()) | "".equals(etPwd.getText().toString())) {
                    UIUtils.showToast(LoginActivity.this, "请输入用户名和密码");
                } else {
                    presenter.login(etName.getText().toString(), etPwd.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        HttpUtils.cancleAllCall(this);
        super.onDestroy();
    }

    public void success(LoginModel loginModel) {
        Message msg = Message.obtain();
        msg.what = LOGIN;
        msg.obj = loginModel;
        mHandler.sendMessage(msg);

    }

    public void logMsg(String s) {
        Message msg = Message.obtain();
        msg.what = LOGMSG;
        msg.obj = s;
        mHandler.sendMessage(msg);
    }
}

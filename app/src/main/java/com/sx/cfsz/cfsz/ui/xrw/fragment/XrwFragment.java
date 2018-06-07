package com.sx.cfsz.cfsz.ui.xrw.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.cfsz.dagger.component.DaggerXrwFragmentComponent;
import com.sx.cfsz.cfsz.dagger.module.XrwFragmentModule;
import com.sx.cfsz.cfsz.model.RwNumberModel;
import com.sx.cfsz.cfsz.presenter.XrwFragmentPresenter;
import com.sx.cfsz.cfsz.ui.MainActivity;
import com.sx.cfsz.cfsz.ui.adapter.VpAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/***       Author  shy          新任务
 *         Time   2018/4/9 0009    15:51      */

public class XrwFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    @Inject
    XrwFragmentPresenter presenter;
    private static final int MSG = 100;
    private LinearLayout llZxz;
    private LinearLayout llDzx;
    private LinearLayout llYwc;
    private List<Fragment> list = new ArrayList<>();
    private ViewPager vp;
    private MainActivity activity;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG:
                    RwNumberModel model = (RwNumberModel) msg.obj;
                    tvDzx.setText(model.getData().getDzx() + "件");
                    tvZxz.setText(model.getData().getZxz() + "件");
                    tvYwc.setText(model.getData().getYwc() + "件");
                    break;

                default:
                    break;
            }
        }
    };

    private TextView tvDzx;
    private TextView tvZxz;
    private TextView tvYwc;
    public DzxFragment dzxFragment;
    public ZxzFragment zxzFragment;
    public YwcFragment ywcFragment;
    private TextView tvDzxTitle;
    private TextView tvZxztitle;
    private TextView tvYwctitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerXrwFragmentComponent.builder().xrwFragmentModule(new XrwFragmentModule(this)).build().in(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xrw, container, false);
        initView(view);
        initData();
        initVP();

        return view;
    }


    private void initView(View view) {
        llZxz = view.findViewById(R.id.llZxz);
        llDzx = view.findViewById(R.id.llDzx);
        llYwc = view.findViewById(R.id.llYwc);
        tvDzx = view.findViewById(R.id.tv_dzx);
        tvZxz = view.findViewById(R.id.tv_zxz);
        tvYwc = view.findViewById(R.id.tv_ywc);
        tvDzxTitle = view.findViewById(R.id.tv_dzxtitle);
        tvZxztitle = view.findViewById(R.id.tv_zxztitle);
        tvYwctitle = view.findViewById(R.id.tv_ywctitle);
        vp = view.findViewById(R.id.vp);
        llZxz.setOnClickListener(this);
        llDzx.setOnClickListener(this);
        llYwc.setOnClickListener(this);

        setChange(0);
    }

    private void initData() {
        activity = (MainActivity) getActivity();
        dzxFragment = new DzxFragment();
        zxzFragment = new ZxzFragment();
        ywcFragment = new YwcFragment();
        list.add(dzxFragment);
        list.add(zxzFragment);
        list.add(ywcFragment);
    }

    private void initVP() {
        VpAdapter vpAdapter = new VpAdapter(getFragmentManager(), getActivity(), list);
        vp.setAdapter(vpAdapter);
        vp.addOnPageChangeListener(this);
        vp.setOffscreenPageLimit(2);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

        } else {
            //底部导航切换时刷新
            presenter.getNumber();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getNumber();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llDzx:
                llZxz.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llDzx.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq_y));
                llYwc.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                vp.setCurrentItem(0);
                setChange(0);
                activity.setSwitch();
                break;
            case R.id.llZxz:
                llZxz.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq_y));
                llDzx.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llYwc.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                vp.setCurrentItem(1);
                setChange(1);
                activity.setSwitch();
                break;
            case R.id.llYwc:
                llZxz.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llDzx.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llYwc.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq_y));
                vp.setCurrentItem(2);
                setChange(2);
                activity.setSwitch();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        activity.setSwitch();
        switch (position) {

            case 0:
                llZxz.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llDzx.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq_y));
                llYwc.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                setChange(0);
                dzxFragment.dzxAllListRows.clear();
                break;
            case 1:
                llZxz.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq_y));
                llDzx.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llYwc.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                setChange(1);
                zxzFragment.zxzAllListRows.clear();
                break;
            case 2:
                llZxz.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llDzx.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llYwc.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq_y));
                setChange(2);
                ywcFragment.ywcAllListRows.clear();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void successNumber(RwNumberModel rwNumberModel) {
        Message msg = Message.obtain();
        msg.what = MSG;
        msg.obj = rwNumberModel;
        mHandler.sendMessage(msg);
    }

    /**
     * 文字变色
     *
     * @param changge
     */
    public void setChange(int changge) {
        if (changge == 0) {
            tvDzx.setTextColor(Color.WHITE);
            tvZxz.setTextColor(Color.BLACK);
            tvYwc.setTextColor(Color.BLACK);
            tvDzxTitle.setTextColor(Color.WHITE);
            tvZxztitle.setTextColor(Color.BLACK);
            tvYwctitle.setTextColor(Color.BLACK);
        } else if (changge == 1) {
            tvDzx.setTextColor(Color.BLACK);
            tvZxz.setTextColor(Color.WHITE);
            tvYwc.setTextColor(Color.BLACK);
            tvDzxTitle.setTextColor(Color.BLACK);
            tvZxztitle.setTextColor(Color.WHITE);
            tvYwctitle.setTextColor(Color.BLACK);
        } else if (changge == 2) {
            tvDzx.setTextColor(Color.BLACK);
            tvZxz.setTextColor(Color.BLACK);
            tvYwc.setTextColor(Color.WHITE);
            tvDzxTitle.setTextColor(Color.BLACK);
            tvZxztitle.setTextColor(Color.BLACK);
            tvYwctitle.setTextColor(Color.WHITE);
        }
    }

    public void xgRefresh(){
        presenter.getNumber();
        vp.setCurrentItem(0);
    }

    //多选取消
    public void setAllNo() {
        dzxFragment.setAllNo();
        dzxFragment.setRefreshLoad(true);
    }

    //多选确定
    public void setAllYes() {
        dzxFragment.setAllYse();
        dzxFragment.setRefreshLoad(true);
        dzxFragment.dzxAllListRows.clear();
        dzxFragment.presenter.getData(1,AppConfig.ROWSNUMBER,1);
    }
}
package com.sx.cfsz.cfsz.ui.xrw.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.cfsz.dagger.component.DaggerYwcFragmentComponent;
import com.sx.cfsz.cfsz.dagger.module.YwcFragmentModule;
import com.sx.cfsz.cfsz.model.RwModel;
import com.sx.cfsz.cfsz.presenter.YwcFragmentPersenter;
import com.sx.cfsz.cfsz.ui.adapter.YwcLvAdapter;
import com.sx.cfsz.cfsz.ui.xrw.activity.YwcDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/***       Author  shy
 *         Time   2018/4/19 0019    10:03      */

public class YwcFragment extends Fragment {

    @Inject
    YwcFragmentPersenter persenter;

    private SmartRefreshLayout ywcRefresh;
    private ListView lvYwc;
    private int totalCount;
    private static final int YWC = 103;
    private int page = 1;
    public List<RwModel.DataBean.RecordsBean> ywcListRows = new ArrayList<>();  //每次请求的新数据
    public List<RwModel.DataBean.RecordsBean> ywcAllListRows = new ArrayList<>();   //界面上的所有数据
    private YwcLvAdapter ywcLvAdapter;
    private ProgressDialog progressDialog;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case YWC:
                    RwModel model = (RwModel) msg.obj;
                    totalCount = model.getData().getTotalCount();
                    ywcListRows = model.getData().getRecords();
                    ywcAllListRows.addAll(ywcListRows);
                    ywcLvAdapter = new YwcLvAdapter(YwcFragment.this.getActivity(), ywcAllListRows);
                    lvYwc.setAdapter(ywcLvAdapter);
                    progressDialog.dismiss();
                    if (ywcAllListRows.size() < totalCount){
                        ywcRefresh.setEnableLoadMore(true);
                    }else {
                        ywcRefresh.setEnableLoadMore(false);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerYwcFragmentComponent.builder().ywcFragmentModule(new YwcFragmentModule(this)).build().in(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ywc_fragment, container, false);
        initView(view);
//        initData();
        initListener();

        return view;
    }

    private void initView(View view) {
        ywcRefresh = view.findViewById(R.id.ywc_srlRefresh);
        lvYwc = view.findViewById(R.id.lv_ywc);
        ywcRefresh.setRefreshFooter(new BallPulseFooter(getActivity()).setAnimatingColor(getResources().getColor(R.color.colorTitle)));
    }


    private void initData() {
        progressDialog = ProgressDialog.show(getActivity(), "请稍等...", "正在刷新数据中... 请稍后...", true);
        progressDialog.setCanceledOnTouchOutside(true);
        this.progressDialog.setCanceledOnTouchOutside(true);
        persenter.getData(1, AppConfig.ROWSNUMBER);
    }


    private void initListener() {
        ywcRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ywcListRows.clear();
                ywcAllListRows.clear();
                page = 1;
                ywcRefresh.finishRefresh(1000);//刷新动画时间
                persenter.getData(1, AppConfig.ROWSNUMBER);
            }
        });

        ywcRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ywcRefresh.finishLoadMore(1000);//上拉加载动画时间
                page = page + 1;
                persenter.getData(page, AppConfig.ROWSNUMBER);

            }
        });

        lvYwc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), YwcDetailsActivity.class);
                String oldName = ywcListRows.get(position).getOld_name();
                if (oldName != null) {
                    intent.putExtra("oldName", oldName);
                }
                intent.putExtra("Task_num", ywcListRows.get(position).getTask_num());
                intent.putExtra("Task_address", ywcListRows.get(position).getTask_address());
                intent.putExtra("Plan_time_start", ywcListRows.get(position).getPlan_time_start());
                intent.putExtra("Plan_time_end", ywcListRows.get(position).getPlan_time_end());
                intent.putExtra("Task_content", ywcListRows.get(position).getTask_content());
                intent.putExtra("Task_id", ywcListRows.get(position).getTask_id());
                intent.putExtra("Task_lat", ywcListRows.get(position).getTask_lat());
                intent.putExtra("Task_lng", ywcListRows.get(position).getTask_lng());
                intent.putExtra("Red_sign", ywcListRows.get(position).getRed_sign());
                intent.putExtra("Task_sfbq", ywcListRows.get(position).getTask_sfbq());
                intent.putExtra("Sealup_time_start",ywcAllListRows.get(position).getSealup_time_start());
                intent.putExtra("Sealup_time_end",ywcAllListRows.get(position).getSealup_time_end());
                intent.putExtra("Feedback_msg",ywcAllListRows.get(position).getFeedback_msg());
                intent.putExtra("Feedback_pic",ywcAllListRows.get(position).getFeedback_pic());
                intent.putExtra("Feedback_video",ywcAllListRows.get(position).getFeedback_video());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible()) {
            if (ywcAllListRows != null) {
                ywcAllListRows.clear();
            }
            progressDialog = ProgressDialog.show(getActivity(), "请稍等...", "正在刷新数据中... 请稍后...", true);
            progressDialog.setCanceledOnTouchOutside(true);
            persenter.getData(1, AppConfig.ROWSNUMBER);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void success(RwModel rwModel) {
        Message msg = Message.obtain();
        msg.what = YWC;
        msg.obj = rwModel;
        mHandler.sendMessage(msg);
    }
}
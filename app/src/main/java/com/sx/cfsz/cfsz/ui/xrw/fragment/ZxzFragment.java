package com.sx.cfsz.cfsz.ui.xrw.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.cfsz.dagger.component.DaggerZxzFragmentComponent;
import com.sx.cfsz.cfsz.dagger.module.ZxzFragmentModule;
import com.sx.cfsz.cfsz.model.RwModel;
import com.sx.cfsz.cfsz.presenter.ZxzFragmentPresenter;
import com.sx.cfsz.cfsz.ui.adapter.ZxzLvAdapter;
import com.sx.cfsz.cfsz.ui.xrw.activity.ZxzDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/***       Author  shy
 *         Time   2018/4/19 0019    10:03      */

public class ZxzFragment extends Fragment {

    @Inject
    ZxzFragmentPresenter presenter;

    private static final int ZXZ = 102;

    private SmartRefreshLayout zxzRefresh;
    private ListView lvZxz;
    private int page = 1;
    public List<RwModel.DataBean.RecordsBean> zxzListRows = new ArrayList<>();  //每次请求的新数据
    public List<RwModel.DataBean.RecordsBean> zxzAllListRows = new ArrayList<>();   //界面上的所有数据
    private ZxzLvAdapter rwLvAdapter;
    private int totalCount;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ZXZ:
                    RwModel model = (RwModel) msg.obj;
                    totalCount = model.getData().getTotalCount();
                    zxzListRows = model.getData().getRecords();
                    zxzAllListRows.addAll(zxzListRows);
                    rwLvAdapter = new ZxzLvAdapter(ZxzFragment.this.getActivity(), zxzAllListRows);
                    lvZxz.setAdapter(rwLvAdapter);
                    if (page != 1){
                        lvZxz.setSelection(page*20-22);
                    }
                    presenter.dialogDismis();
                    if (zxzAllListRows.size() < totalCount){
                        zxzRefresh.setEnableLoadMore(true);
                    }else {
                        zxzRefresh.setEnableLoadMore(false);
                    }

                    zxzRefresh.finishRefresh();//结束刷新
                    zxzRefresh.finishLoadMore();//结束加载
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerZxzFragmentComponent.builder().zxzFragmentModule(new ZxzFragmentModule(this)).build().in(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zxz_fragment, container, false);
        initView(view);
        initListener();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible()) {
            if (zxzAllListRows != null) {
                zxzAllListRows.clear();
            }
            presenter.getData(1, AppConfig.ROWSNUMBER,1);
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }


    private void initView(View view) {
        lvZxz = view.findViewById(R.id.lv_zxz);
        zxzRefresh = view.findViewById(R.id.zxz_srlRefresh);
        zxzRefresh.setHeaderHeight(60);
        zxzRefresh.setRefreshHeader(new ClassicsHeader(getActivity()));
        zxzRefresh.setRefreshFooter(new BallPulseFooter(getActivity()).setAnimatingColor(getResources().getColor(R.color.colorTitle)));
        zxzRefresh.setFooterTriggerRate(0);//触发加载距离 与 FooterHeight 的比率1.0.4
    }

    private void initListener() {
        zxzRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                zxzListRows.clear();
                zxzAllListRows.clear();
                page = 1;
                presenter.getData(1, AppConfig.ROWSNUMBER,0);
            }
        });
        zxzRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page = page + 1;
                presenter.getData(page, AppConfig.ROWSNUMBER,0);
            }
        });

        lvZxz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ZxzDetailsActivity.class);
                String oldName = zxzAllListRows.get(position).getOld_name();
                if (oldName != null) {
                    intent.putExtra("oldName", oldName);
                }
                intent.putExtra("Task_num", zxzAllListRows.get(position).getTask_num());
                intent.putExtra("Task_address", zxzAllListRows.get(position).getTask_address());
                intent.putExtra("Plan_time_start", zxzAllListRows.get(position).getPlan_time_start());
                intent.putExtra("Plan_time_end", zxzAllListRows.get(position).getPlan_time_end());
                intent.putExtra("Task_content", zxzAllListRows.get(position).getTask_content());
                intent.putExtra("Task_lat", zxzAllListRows.get(position).getTask_lat());
                intent.putExtra("Task_lng", zxzAllListRows.get(position).getTask_lng());
                intent.putExtra("Result", zxzAllListRows.get(position).getResult());
                intent.putExtra("Task_sfbq", zxzAllListRows.get(position).getTask_sfbq());
                intent.putExtra("Task_id",zxzAllListRows.get(position).getTask_id());
                intent.putExtra("Red_sign",zxzAllListRows.get(position).getRed_sign());
                startActivityForResult(intent,30);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //单个执行后返回到本界面并刷新
        if (requestCode == 30 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String state = bundle.getString("state");
            if ("30".equals(state)){
                if (zxzAllListRows != null) {
                    zxzAllListRows.clear();
                }
                presenter.getData(1, AppConfig.ROWSNUMBER,1);
            }
        }
    }

    public void success(RwModel rwModel) {
        Message msg = Message.obtain();
        msg.what = ZXZ;
        msg.obj = rwModel;
        mHandler.sendMessage(msg);
    }
}

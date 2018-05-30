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
import com.sx.cfsz.baseframework.util.UIUtils;
import com.sx.cfsz.cfsz.dagger.component.DaggerDzxFragmentComponent;
import com.sx.cfsz.cfsz.dagger.module.DzxFragmentModule;
import com.sx.cfsz.cfsz.model.RwModel;
import com.sx.cfsz.cfsz.model.SubmitModel;
import com.sx.cfsz.cfsz.presenter.DzxFragmentPresenter;
import com.sx.cfsz.cfsz.ui.MainActivity;
import com.sx.cfsz.cfsz.ui.adapter.DzxLvAdapter;
import com.sx.cfsz.cfsz.ui.xrw.activity.DzxDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/***       Author  shy
 *         Time   2018/4/19 0019    10:03      */

public class DzxFragment extends Fragment{

    @Inject
    DzxFragmentPresenter presenter;
    private static final int DZX = 101;
    private static final int ALLSUBMIT = 120;
    public List<RwModel.DataBean.RecordsBean> dzxListRows = new ArrayList<>();  //每次请求的新数据
    public List<RwModel.DataBean.RecordsBean> dzxAllListRows = new ArrayList<>();   //界面上的所有数据
    private ListView lvDzx;
    private SmartRefreshLayout dzxRefresh;
    private DzxLvAdapter dzxLvAdapter;
    private MainActivity mainActivity;
    public int page = 1;    //页码
    private int state =0;   //首次加载标识
    private int totalCount; //数据总数

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DZX:
                    RwModel rwModel = (RwModel) msg.obj;
                    totalCount = rwModel.getData().getTotalCount();
                    dzxListRows = rwModel.getData().getRecords();
                    dzxAllListRows.addAll(dzxListRows);
                    dzxLvAdapter = new DzxLvAdapter(DzxFragment.this.getActivity(), dzxAllListRows);
                    lvDzx.setAdapter(dzxLvAdapter);
                    if (page != 1){
                        lvDzx.setSelection(page*20-22);
                    }
                    presenter.dialogDismis();
                    if (dzxAllListRows.size() < totalCount){
                        dzxRefresh.setEnableLoadMore(true);
                    }else {
                        dzxRefresh.setEnableLoadMore(false);
                    }
                    dzxRefresh.finishRefresh();//结束刷新
                    dzxRefresh.finishLoadMore();//结束加载
                    break;
                case ALLSUBMIT:
                    SubmitModel submitModel = (SubmitModel) msg.obj;
                    String message = submitModel.getMessage();
                    UIUtils.showToast(getActivity(), message);
                    //多选提交成功后刷新本界面
                    mainActivity.xrwFragment.onResume();
                    if (dzxAllListRows != null) {
                        dzxAllListRows.clear();
                    }
                    presenter.getData(1, AppConfig.ROWSNUMBER,1);
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerDzxFragmentComponent.builder().dzxFragmentModule(new DzxFragmentModule(this)).build().in(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dzx_fragment, container, false);
        initView(view);
        /**
         * ViewPager 中Fragment的刷新
         * 通过setUserV..H..方法 切换显示ViewPager中Fragment时加载数据
         * 但首次不调用 因此使用本标识 state
         */
        if (state == 0){
            initData(1);
            state = 1;
        }
        initListener();
        return view;
    }

    private void initView(View view) {
        mainActivity = (MainActivity) getActivity();
        lvDzx = view.findViewById(R.id.lv_dzx);
        dzxRefresh = view.findViewById(R.id.dzx_srlRefresh);
        dzxRefresh.setHeaderHeight(60);
        dzxRefresh.setRefreshHeader(new ClassicsHeader(getActivity()));
        dzxRefresh.setRefreshFooter(new BallPulseFooter(getActivity()).setAnimatingColor(getResources().getColor(R.color.colorTitle)));
    }

    public void initData(int s) {
        presenter.getData(1, AppConfig.ROWSNUMBER,s);
    }

    private void initListener() {
        dzxRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                dzxListRows.clear();
                dzxAllListRows.clear();
                page = 1;
                presenter.getData(1, AppConfig.ROWSNUMBER,0);
            }
        });
        dzxRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page = page + 1;
                presenter.getData(page, AppConfig.ROWSNUMBER,0);
            }
        });
        //列表长按
        lvDzx.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dzxLvAdapter.setState(1);
                dzxLvAdapter.notifyDataSetChanged();
                setRefreshLoad(false);
                MainActivity activity = (MainActivity) getActivity();
                activity.settingsTitle(99);
                return false;
            }
        });

        lvDzx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DzxDetailsActivity.class);
                String oldName = dzxAllListRows.get(position).getOld_name();
                if (oldName != null) {
                    intent.putExtra("oldName", oldName);
                }
                intent.putExtra("Task_num", dzxAllListRows.get(position).getTask_num());
                intent.putExtra("Task_address", dzxAllListRows.get(position).getTask_address());
                intent.putExtra("Plan_time_start", dzxAllListRows.get(position).getPlan_time_start());
                intent.putExtra("Plan_time_end", dzxAllListRows.get(position).getPlan_time_end());
                intent.putExtra("Task_content", dzxAllListRows.get(position).getTask_content());
                intent.putExtra("Task_id", dzxAllListRows.get(position).getTask_id());
                intent.putExtra("Task_lat", dzxAllListRows.get(position).getTask_lat());
                intent.putExtra("Task_lng", dzxAllListRows.get(position).getTask_lng());
                intent.putExtra("Red_sign", dzxAllListRows.get(position).getRed_sign());
                startActivityForResult(intent,20);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible()) {
            if (dzxAllListRows != null) {
                dzxAllListRows.clear();
            }
            presenter.getData(1, AppConfig.ROWSNUMBER,1);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //单个执行后返回到本界面并刷新
        //去掉红点接口同理
        if (requestCode == 20 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String state = bundle.getString("state");
            if ("20".equals(state)){
                if (dzxAllListRows != null) {
                    dzxAllListRows.clear();
                }
                presenter.getData(1, AppConfig.ROWSNUMBER,1);
            }
        }
    }

    public void success(RwModel rwModel) {
        Message msg = Message.obtain();
        msg.what = DZX;
        msg.obj = rwModel;
        mHandler.sendMessage(msg);
    }

    //多选取消
    public void setAllNo() {
        dzxLvAdapter.setState(0);
        dzxLvAdapter.notifyDataSetChanged();
    }

    //多选确定
    public void setAllYse() {
        String allTastId = "";
        HashMap<Integer, String> map = dzxLvAdapter.map;
        for (String value : map.values()) {
            allTastId = value + "," + allTastId;
        }
        String allRed = "";
        Map<Integer, String> mapRed = dzxLvAdapter.mapRed;
        for (String value : mapRed.values()) {
            allRed = value + "," + allRed;
        }
        presenter.submitAll(allTastId.substring(0, allTastId.length() - 1), allRed.substring(0, allRed.length() - 1));
    }

    //刷新开关
    public void setRefreshLoad(boolean refreshLoad) {
        dzxRefresh.setEnableRefresh(refreshLoad);//是否启用下拉刷新功能
        dzxRefresh.setEnableLoadMore(refreshLoad);//是否启用上拉加载功能
    }

    public void allSubmitSuccess(SubmitModel submitModel) {
        Message msg = Message.obtain();
        msg.what = ALLSUBMIT;
        msg.obj = submitModel;
        mHandler.sendMessage(msg);
    }
}

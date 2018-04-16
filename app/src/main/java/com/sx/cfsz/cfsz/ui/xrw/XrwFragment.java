package com.sx.cfsz.cfsz.ui.xrw;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.sx.cfsz.R;
import com.sx.cfsz.cfsz.ui.adapter.MainLvAdapter;
import com.sx.cfsz.cfsz.ui.myView.MySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***       Author  shy          新任务
 *         Time   2018/4/9 0009    15:51      */

public class XrwFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private LinearLayout llZxz;
    private LinearLayout llDzx;
    private LinearLayout llYwc;
    private MySwipeRefreshLayout mysrlRefresh;
    private ListView listView;
    private List<String> list = new ArrayList<>();
    private MainLvAdapter mainLvAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xrw,container,false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            list.add("大声告诉我今天是几号？" + i + "号");
        }
    }

    private void initView(View view) {
        llZxz = view.findViewById(R.id.llZxz);
        llDzx = view.findViewById(R.id.llDzx);
        llYwc = view.findViewById(R.id.llYwc);
        mysrlRefresh = view.findViewById(R.id.mysrlRefresh);
        listView = view.findViewById(R.id.lv);

        llZxz.setOnClickListener(this);
        llDzx.setOnClickListener(this);
        llYwc.setOnClickListener(this);

        mainLvAdapter = new MainLvAdapter(getActivity(), list);
        listView.setAdapter(mainLvAdapter);

        listView.setOnItemClickListener(this);

        mysrlRefresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        mysrlRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mysrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                //swipeRefreshLayout.setRefreshing(true);
                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        list.add(0, "我是新加的~ 啦啦啦啦啦啦" + random.nextInt(100) + "号");
                        mainLvAdapter.notifyDataSetChanged();
//                        Toast.makeText(getActivity(), "刷新了一条数据", Toast.LENGTH_SHORT).show();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        mysrlRefresh.setRefreshing(false);
                    }
                }, 1200);
                // System.out.println(Thread.currentThread().getName());
                // 这个不能写在外边，不然会直接收起来
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
        mysrlRefresh.setOnLoadMoreListener(new MySwipeRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> xlist = new ArrayList<>();
                        for (int i = 0; i < 20; i++) {
                            xlist.add("新增加的数据 " + i + "号");
                        }
                        list.addAll(xlist);
                        Toast.makeText(getActivity(), "加载了" + 20 + "条数据", Toast.LENGTH_SHORT).show();
                        // 加载完数据设置为不加载状态，将加载进度收起来
                        mysrlRefresh.setLoading(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llZxz:
                llZxz.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq_y));
                llDzx.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llYwc.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                break;
            case R.id.llDzx:
                llZxz.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llDzx.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq_y));
                llYwc.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                break;
            case R.id.llYwc:
                llZxz.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llDzx.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq));
                llYwc.setBackground(getResources().getDrawable(R.drawable.shape_corner_rwxq_y));
                break;
        }
    }
    //由MainActivity中的点击事件调用此方法
    //多选
    public void seceleAll(int i){
        mainLvAdapter.setState(i);
        mainLvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getActivity(),DetailsActivity.class));
    }
}

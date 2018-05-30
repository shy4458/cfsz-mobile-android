package com.sx.cfsz.cfsz.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/***       Author  shy
 *         Time   2018/5/8 0008    10:09      */

public class YwcSPLVAdapter extends BaseAdapter {

    private Context context;
    private List<String> spList = new ArrayList<>();

    public YwcSPLVAdapter(Context context, List<String> spList) {
        this.context = context;
        this.spList = spList;
    }

    @Override
    public int getCount() {
        return spList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.lv_item_ywc_sp, parent, false);
        String url = AppConfig.IP + AppConfig.DQSP + spList.get(position);

        JZVideoPlayerStandard video = view.findViewById(R.id.videoplayer);
        video.setUp(url, JZVideoPlayer.SCREEN_WINDOW_LIST,"");

        return view;
    }
}

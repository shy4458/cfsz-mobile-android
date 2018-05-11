package com.sx.cfsz.cfsz.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;

import java.util.ArrayList;
import java.util.List;

/***       Author  shy
 *         Time   2018/5/8 0008    10:06      */

public class YwcGVAdapter extends BaseAdapter {

    private Context context;
    private List<String> zpList = new ArrayList<>();

    public YwcGVAdapter(Context context, List<String> zpList) {
        this.context = context;
        this.zpList = zpList;
    }

    @Override
    public int getCount() {
        return zpList.size();
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
        convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent,false);
        ImageView iv = convertView.findViewById(R.id.pic_iv);
        Glide.with(context).load(AppConfig.IP + AppConfig.DQZP + zpList.get(position)).into(iv);
        return convertView;
    }
}

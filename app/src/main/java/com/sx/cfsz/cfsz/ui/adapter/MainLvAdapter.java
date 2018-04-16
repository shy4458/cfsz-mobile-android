package com.sx.cfsz.cfsz.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.sx.cfsz.R;

import java.util.ArrayList;
import java.util.List;

/***       Author  shy
 *         Time   2018/4/12 0012    10:37      */

public class MainLvAdapter extends BaseAdapter {

    private Context context;
    private List<String> list = new ArrayList<>();
    private int state = 0;

    public MainLvAdapter(Context context,List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setState(int state){
        this.state = state;
    }
    @Override
    public int getCount() {
        return list.size();
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

        View view = LayoutInflater.from(context).inflate(R.layout.lv_item, null);

        CheckBox checkBox = view.findViewById(R.id.cb);
        if (state == 1){
            checkBox.setVisibility(View.VISIBLE);
        }
//        TextView tvMs = view.findViewById(R.id.tvMs);
//        tvMs.setText(list.get(position));

        return view;
    }
}

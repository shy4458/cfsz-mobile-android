package com.sx.cfsz.cfsz.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sx.cfsz.R;
import com.sx.cfsz.cfsz.model.RwModel;

import java.util.ArrayList;
import java.util.List;

/***       Author  shy
 *         Time   2018/4/12 0012    10:37      */

public class YwcLvAdapter extends BaseAdapter {

    private Context context;
    private List<RwModel.DataBean.RecordsBean> list = new ArrayList<>();


    public YwcLvAdapter(Context context, List<RwModel.DataBean.RecordsBean> list) {
        this.context = context;
        this.list = list;
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
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dzx_lv_item, null);
            viewHolder = new MyViewHolder();
            viewHolder.ivNewMsg = convertView.findViewById(R.id.iv_newMsg);
            viewHolder.checkBox = convertView.findViewById(R.id.cb);
            viewHolder.tvContent = convertView.findViewById(R.id.tv_content);
            viewHolder.ivTimeOut = convertView.findViewById(R.id.iv_timeout);
            viewHolder.ivDuban = convertView.findViewById(R.id.iv_duban);
            viewHolder.tvAjh = convertView.findViewById(R.id.tv_item_name);
            viewHolder.tvAddress = convertView.findViewById(R.id.tv_item_address);
            viewHolder.tvSj = convertView.findViewById(R.id.tv_item_sj);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        //红点
        if ("0".equals(list.get(position).getRed_sign())) {
            viewHolder.ivNewMsg.setVisibility(View.INVISIBLE);
            viewHolder.ivDuban.setVisibility(View.GONE);
        } else if ("1".equals(list.get(position).getRed_sign())) {
            viewHolder.ivNewMsg.setVisibility(View.VISIBLE);
            viewHolder.ivDuban.setVisibility(View.GONE);
        } else if ("2".equals(list.get(position).getRed_sign())) {
            //督办
            viewHolder.ivNewMsg.setVisibility(View.INVISIBLE);
            viewHolder.ivDuban.setVisibility(View.VISIBLE);
        }

        //标题
        viewHolder.tvAjh.setText(position + 1 + ".   " + list.get(position).getTask_num());
        viewHolder.tvAddress.setText(list.get(position).getTask_address());
        viewHolder.tvContent.setText(list.get(position).getTask_content());
        viewHolder.tvSj.setText(list.get(position).getPlan_time_start() + "至" + list.get(position).getPlan_time_end());

        //超时
        if (0 == list.get(position).getIs_timeout()) {
            viewHolder.ivTimeOut.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.ivTimeOut.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public static class MyViewHolder {
        ImageView ivNewMsg;
        CheckBox checkBox;
        TextView tvContent;
        ImageView ivTimeOut;
        ImageView ivDuban;
        TextView tvAjh;
        TextView tvAddress;
        TextView tvSj;
    }

}

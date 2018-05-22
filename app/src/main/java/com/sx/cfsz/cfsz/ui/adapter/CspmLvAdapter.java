package com.sx.cfsz.cfsz.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sx.cfsz.R;
import com.sx.cfsz.cfsz.model.CspmModel;

import java.util.List;

/***       Author  shy
 *         Time   2018/5/14 0014    10:16      */

public class CspmLvAdapter extends BaseAdapter {

    private Context context;
    private List<CspmModel.DataBean> cspmList;

    public CspmLvAdapter(Context context, List<CspmModel.DataBean> cspmList) {
        this.context = context;
        this.cspmList = cspmList;
    }

    @Override
    public int getCount() {
        if (cspmList.size() < 5) {
            return cspmList.size();
        } else {
            return 5;
        }
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
        convertView = LayoutInflater.from(context).inflate(R.layout.lv_cspm_item, null);
        TextView tvNumber = convertView.findViewById(R.id.tv_cs_number);
        TextView tvName = convertView.findViewById(R.id.tv_cs_name);
        TextView tvTimes = convertView.findViewById(R.id.tv_cs_times);
        tvNumber.setText(
                position + 1 + ""
        );
        tvName.setText(cspmList.get(position).getUser_name());
        tvTimes.setText(cspmList.get(position).getTime_out());

        return convertView;
    }
}

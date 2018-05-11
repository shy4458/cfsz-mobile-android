package com.sx.cfsz.cfsz.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sx.cfsz.R;
import com.sx.cfsz.cfsz.ui.xrw.activity.ZxzDetailsActivity;

import java.util.ArrayList;

import cn.jzvd.JZVideoPlayerStandard;

/***       Author  shy
 *         Time   2018/4/16 0016    16:48      */

public class LvMp4Adapter extends BaseAdapter {

    private ZxzDetailsActivity activity;
    private ArrayList<String> mp4List;

    public LvMp4Adapter(ZxzDetailsActivity activity, ArrayList<String> mp4List) {
        this.activity = activity;
        this.mp4List = mp4List;
    }

    @Override
    public int getCount() {
        return mp4List.size();
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
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(activity).inflate(R.layout.lv_mp_item, parent, false);
        JZVideoPlayerStandard jzVideoPlayerStandard = view.findViewById(R.id.videoplayer);
        ImageView ivDeleteMp4 = view.findViewById(R.id.ivDeleteMp4);
        jzVideoPlayerStandard.setUp(mp4List.get(position)
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");

        ivDeleteMp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp4List.remove(position);
                notifyDataSetChanged();
                if (mp4List.size()<2){
                    activity.setVisibility();
                }
            }
        });
        return view;
    }
}

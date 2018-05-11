package com.sx.cfsz.cfsz.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/***       Author  shy
 *         Time   2018/4/19 0019    10:01      */

public class VpAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> list = new ArrayList<>();
    public VpAdapter(FragmentManager fm,Context context,List<Fragment> list) {
        super(fm);
        this.context = context;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

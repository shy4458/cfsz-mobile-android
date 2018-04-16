package com.sx.cfsz.cfsz.ui.tjfx;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sx.cfsz.R;
import com.sx.cfsz.cfsz.ui.myView.CustomBarChart;

import java.util.ArrayList;
import java.util.List;

/***       Author  shy
 *         Time   2018/4/9 0009    15:57      */

public class TjfxFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tjfx, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        LinearLayout llgytj = view.findViewById(R.id.llGytj);
        LinearLayout llRybasjtj = view.findViewById(R.id.llRybasjtj);

        String[] xLabel = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        String[] yLabel = {"0", "100", "200", "300", "400", "500", "600", "700", "800", "900"};
        int[] data1 = {300, 500, 550, 500, 300, 700, 800, 750, 550, 600, 400, 300};
        int[] data2 = {400, 600, 500, 700, 300, 500, 550, 500, 300, 700, 800, 750};
        List<int[]> data = new ArrayList<>();
        data.add(data1);
        data.add(data2);
        List<Integer> color = new ArrayList<>();
        color.add(R.color.colorAccent);
        color.add(R.color.colorPrimary);
        color.add(R.color.colorPrimaryDark);
        llgytj.addView(new CustomBarChart(getActivity(), xLabel, yLabel, data, color));


        String[] nama = {"", "送人头", "送人头", "送人头", "送人头", "送人头", "送人头"};
        String[] number = {"0", "100", "200", "300", "400", "500", "600", "700", "800", "900"};
        int[] data3 = {300, 500, 550, 500, 300, 700, 800};
        List<int[]> data4 = new ArrayList<>();
        data4.add(data3);
        List<Integer> colorr = new ArrayList<>();
        colorr.add(R.color.colorPrimary);
        colorr.add(R.color.colorPrimary);
        colorr.add(R.color.colorPrimary);
        llRybasjtj.addView(new CustomBarChart(getActivity(), nama, number, data4, colorr));

    }
}

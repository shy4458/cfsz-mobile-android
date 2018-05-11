package com.sx.cfsz.cfsz.ui.tjfx;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.sx.cfsz.R;
import com.sx.cfsz.cfsz.dagger.component.DaggerTjfxFragmentComponent;
import com.sx.cfsz.cfsz.dagger.component.DaggerXrwFragmentComponent;
import com.sx.cfsz.cfsz.dagger.module.TjfxFragmentModule;
import com.sx.cfsz.cfsz.dagger.module.XrwFragmentModule;
import com.sx.cfsz.cfsz.model.AjxqgkModel;
import com.sx.cfsz.cfsz.model.GytjModel;
import com.sx.cfsz.cfsz.model.RybaTjModel;
import com.sx.cfsz.cfsz.presenter.TjfxFragmentPresenter;
import com.sx.cfsz.cfsz.ui.myView.ColumnLineView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/***       Author  shy
 *         Time   2018/4/9 0009    15:57      */

public class TjfxFragment extends Fragment implements OnChartValueSelectedListener {

    @Inject
    TjfxFragmentPresenter presenter;

    private TextView tvQb, tvYgwc, tvSjwc, tvCs, tvZp, tvXz;
    private PieChart mChart;
    private final int itemcount = 12;

    private CombinedChart cc, cc2;
    private List<GytjModel.DataBean> data;


    protected String[] mParties = new String[]{"房屋查封", "财产查封", "车辆查封", "其他案件"};
    protected String[] mMonths = new String[]{
            "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

    private static final int AJSL = 600;
    private static final int GYTJ = 601;
    private static final int RYBATJ = 602;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AJSL:
                    AjxqgkModel model = (AjxqgkModel) msg.obj;
                    tvQb.setText("" + model.getData().getAjzsl());
                    tvYgwc.setText("" + model.getData().getRwzsl());
                    tvSjwc.setText("" + model.getData().getScrwsl());
                    tvCs.setText("" + model.getData().getYqrwsl());
                    tvZp.setText("" + model.getData().getZpsl());
                    tvXz.setText("" + model.getData().getXzsl());
                    break;
                case GYTJ:
                    GytjModel gytjModel = (GytjModel) msg.obj;
                    data = gytjModel.getData();
                    initCc();
                    break;

                case RYBATJ:
                    RybaTjModel rybaTjModel = (RybaTjModel) msg.obj;
                    List<RybaTjModel.DataBean> data = rybaTjModel.getData();
                    setBarData(data);

                    break;
                default:
                    break;
            }
        }
    };
    private BarChart bc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerTjfxFragmentComponent.builder().tjfxFragmentModule(new TjfxFragmentModule(this)).build().in(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tjfx, container, false);
        initView(view);

        initData();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    private void initView(View view) {
        tvQb = view.findViewById(R.id.tv_tj_qb);
        tvYgwc = view.findViewById(R.id.tv_tj_ygwc);
        tvSjwc = view.findViewById(R.id.tv_tj_sjwc);
        tvCs = view.findViewById(R.id.tv_tj_cs);
        tvZp = view.findViewById(R.id.tv_tj_zp);
        tvXz = view.findViewById(R.id.tv_tj_xz);
        mChart = view.findViewById(R.id.piechart);
        cc = view.findViewById(R.id.cc);
        bc = view.findViewById(R.id.bc);

        initBc();
        initPieChart();
    }

    private void initData() {
        presenter.getData();
    }

    public void successAjNumber(AjxqgkModel ajxqgkModel) {
        Message msg = Message.obtain();
        msg.what = AJSL;
        msg.obj = ajxqgkModel;
        mHandler.sendMessage(msg);
    }
    public void successGrtj(GytjModel gytjModel) {
        Message msg = Message.obtain();
        msg.what = GYTJ;
        msg.obj = gytjModel;
        mHandler.sendMessage(msg);
    }
    public void successRybrtj(RybaTjModel rybaTjModel) {
        Message msg = Message.obtain();
        msg.what = RYBATJ;
        msg.obj = rybaTjModel;
        mHandler.sendMessage(msg);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
    }

    @Override
    public void onNothingSelected() {

    }
    //各月统计 混合图
    private void initCc() {
        //横坐标月份
        List<String> xData = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            xData.add(String.valueOf(i + "月"));
        }

        ArrayList<Integer> barList = new ArrayList<>();
        ArrayList<Integer> lineList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            barList.add(data.get(i).getYwc());
            lineList.add( data.get(i).getYcs());
        }

        //颜色集合
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        //竖状图管理类

        //管理类
        CombinedChartManager combineChartManager = new CombinedChartManager(cc);
        combineChartManager.showCombinedChart(xData, barList, lineList,
                "完成总数", "超时完成", colors.get(0), colors.get(1));
    }
    //人员办案 数据统计 柱状图初始化
    private void initBc() {
        bc.setOnChartValueSelectedListener(this);
        bc.setDrawBarShadow(false);
        bc.setDrawValueAboveBar(true);
        bc.getDescription().setEnabled(false);
        bc.setMaxVisibleValueCount(60);
        bc.setPinchZoom(true);
        bc.setTouchEnabled(true);
        bc.setDrawGridBackground(false);
        bc.getAxisRight().setEnabled(false); //右边轴线隐藏

        XAxis xAxis = bc.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0f);

        bc.getAxisLeft().setDrawGridLines(false);
        bc.animateY(2500);
        bc.getLegend().setEnabled(false);

        YAxis axisLeft = bc.getAxisLeft();
        axisLeft.setAxisMinimum(0f);

    }
    //人员办案 柱状图数据
    private void setBarData(final List<RybaTjModel.DataBean> list) {
        //获取最大值
        int count = list.size() -1 ;
        float range=0;
        for (int i = 0; i < list.size(); i++) {
            if (Integer.parseInt(list.get(i).getNum()) > range){
                range = Float.parseFloat(list.get(i).getNum());
            }
        }

        float start = 1f;
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        for (int i = (int) start; i < start + count + 1; i++) {
            float val = Float.parseFloat(list.get(i-1).getNum());
                yVals.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.gif_tag)));
        }

        bc.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value == 0.0){
                    return "";
                }else {
                    return  list.get((int) value-1).getName();
                }
            }
        });

        BarDataSet set1;
        if (bc.getData() != null && bc.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) bc.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            bc.getData().notifyDataChanged();
            bc.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals, "The year 2017");
            set1.setDrawIcons(false);
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            bc.setData(data);
        }
    }

    //饼状图
    private void initPieChart() {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setCenterText("类型\n统计");
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(40f);
        mChart.setTransparentCircleRadius(43f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setOnChartValueSelectedListener(this);
        setPieData(4, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);
    }
    //饼状图数据
    private void setPieData(int count, float range) {
        float mult = range;
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry(
                    //TODO 添加数据
                    (float) ((Math.random() * mult) + mult / 5)
                    , mParties[i], getResources().getDrawable(R.drawable.gif_tag)));
        }
        PieDataSet dataSet = new PieDataSet(entries, "案件类型统计");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);
        mChart.invalidate();

    }


}

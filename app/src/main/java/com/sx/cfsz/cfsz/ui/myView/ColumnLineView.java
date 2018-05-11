package com.sx.cfsz.cfsz.ui.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sx.cfsz.cfsz.model.ColumnLineModel;

import java.util.List;

/***       Author  shy
 *         Time   2018/5/8 0008    15:45      */

public class ColumnLineView extends View {


    public ColumnLineView(Context context) {
        this(context,null);
    }

    public ColumnLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColumnLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setData(List<ColumnLineModel.DataBean> list){
        for (int i = 0; i < list.size(); i++) {

        }
    }

    private void init() {
        //坐标系
        Paint coordinatePaint = new Paint();
        coordinatePaint.setColor(Color.BLACK);
        coordinatePaint.setAntiAlias(true);

        //柱状图
        Paint columnPaint = new Paint();
        columnPaint.setColor(Color.RED);
        columnPaint.setAntiAlias(true);

        //曲线图
        Paint linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}

package com.sx.cfsz.cfsz.ui.myView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.sx.cfsz.R;

/***       Author  shy
 *         Time   2018/5/17 0017    15:55      */

public class CustomDialog extends ProgressDialog {
    private Boolean b = true;
    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }
    public CustomDialog(Context context,int theme,Boolean b){
        super(context,theme);
        this.b = b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(b);
        setCanceledOnTouchOutside(b);
        setContentView(R.layout.loading_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }
}


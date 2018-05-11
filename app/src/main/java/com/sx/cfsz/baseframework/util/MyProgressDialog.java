package com.sx.cfsz.baseframework.util;

import android.app.ProgressDialog;
import android.content.Context;

/***       Author  shy
 *         Time   2018/5/7 0007    10:59      */

public class MyProgressDialog {

    public void show(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

}

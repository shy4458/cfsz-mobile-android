package com.sx.cfsz.cfsz.presenter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.sx.cfsz.BuildConfig;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.model.UserModel;
import com.sx.cfsz.cfsz.ui.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import okhttp3.Response;

/***       Author  shy
 *         Time   2018/5/20 0020    14:41      */

public class MainActivityPresenter {

    MainActivity activity;
    private File file;

    public MainActivityPresenter(MainActivity activity) {
        this.activity = activity;
    }

    public void updateQue() {
        String url = AppConfig.IP + AppConfig.BB;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null) {
                    if (response != null && response.isSuccessful()) {
                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            UserModel userModel = gson.fromJson(str, UserModel.class);
                            int data = userModel.getData();
                            if (data > BuildConfig.VERSION_CODE) {
                                activity.sucessUp();
                            }

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void dowAPK() {
        String urlAPK = AppConfig.IP + AppConfig.PATH;
        HttpUtils.getAsync(urlAPK, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null) {
                    InputStream is = null;//输入流
                    FileOutputStream fos = null;//输出流
                    try {
                        is = response.body().byteStream();//获取输入流
                        long total = response.body().contentLength();//获取文件大小
                        activity.dowAPKSezi(total);
                        if (is != null) {
                            // 设置路径
                            file = new File(Environment.getExternalStorageDirectory(), "cfsz.apk");
                            fos = new FileOutputStream(file);
                            byte[] buf = new byte[1024];
                            int ch = -1;
                            int process = 0;
                            while ((ch = is.read(buf)) != -1) {
                                fos.write(buf, 0, ch);
                                process += ch;
                                activity.downLoading(process);
                            }
                        }
                        fos.flush();
                        // 下载完成
                        if (fos != null) {
                            fos.close();
                        }
                        activity.downSuccess(file);
                    } catch (Exception e0) {
                        Log.e("//////", e0.getMessage());
                        activity.downFial("下载失败");
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e1) {
                        }
                        try {
                            if (fos != null)
                                fos.close();
                        } catch (IOException e2) {
                        }
                    }
                } else {
                    //链接异常
                    activity.downFial("网络连接异常");
                }
            }

        });
    }

    public void intTS(String userId, String token) {
        String url = AppConfig.IP + AppConfig.TOKEN + token + "&userId=" + userId;
        HttpUtils.getAsync(url, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null) {
                    if (response != null && response.isSuccessful()) {
                        try {
                            String string = response.body().string();

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}

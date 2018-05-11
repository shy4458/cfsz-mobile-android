package com.sx.cfsz.cfsz.presenter;

import android.text.Editable;
import android.util.Log;

import com.google.gson.Gson;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.baseframework.http.FileInfo;
import com.sx.cfsz.baseframework.http.FileType;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.model.ZpFeedModel;
import com.sx.cfsz.cfsz.ui.xrw.activity.ZxzDetailsActivity;
import com.sx.cfsz.cfsz.ui.xrw.util.VideoCompressListener;
import com.sx.cfsz.cfsz.ui.xrw.util.VideoCompressor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Response;

/***       Author  shy
 *         Time   2018/5/2 0002    13:31      */

public class ZxzDetailsPresenter {

    private ZxzDetailsActivity activity;

    public ZxzDetailsPresenter(ZxzDetailsActivity activity) {
        this.activity = activity;
    }

    public void feedZp(ArrayList<String> mPicList) {
        //没有添加照片
        if (mPicList.size() == 0) {
            ZpFeedModel zpFeedModel = new ZpFeedModel();
            zpFeedModel.setData("");
            activity.zpSubmitSuccess(zpFeedModel);
        } else {
            String url = AppConfig.IP + AppConfig.IMG;
            FileInfo[] fileInfos = new FileInfo[mPicList.size()];
            FileInfo fileInfo;
            File file;
            for (int i = 0; i < mPicList.size(); i++) {
                fileInfo = new FileInfo();
                file = new File(mPicList.get(i));
                fileInfo.setFormName("zp" + i);
                fileInfo.setFileType(FileType.IMAGE);
                fileInfo.setFile(file);
                fileInfos[i] = fileInfo;
            }
            HttpUtils.postFilesAsync(url, activity,fileInfos,new OnRequestResult() {
                @Override
                public void result(Exception e, Response response) {
                    if (e == null) {
                        if (response != null && response.isSuccessful()) {
                            try {
                                String string = response.body().string();
                                Gson gson = new Gson();
                                ZpFeedModel zpFeedModel = gson.fromJson(string, ZpFeedModel.class);
                                activity.zpSubmitSuccess(zpFeedModel);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }

    public void feedSp(ArrayList<String> mp4List) {
        //没有添加视频
        if (mp4List.size() == 0) {
            ZpFeedModel zpFeedModel = new ZpFeedModel();
            zpFeedModel.setData("");
            activity.spSubmitSuccess(zpFeedModel);
        } else {
//            for (int i = 0; i < mp4Lists.size(); i++) {
//                //压缩组件
//                VideoCompressor.compress(activity, mp4Lists.get(i), new VideoCompressListener() {
//                    @Override
//                    public void onSuccess(String outputFile, String filename, long duration) {
//
//                        Log.d("////", outputFile);
//                        Log.d("////", filename);
//
//                        mp4List.add(filename);
//                    }
//
//                    @Override
//                    public void onFail(String reason) {
//
//                    }
//
//                    @Override
//                    public void onProgress(int progress) {
//
//                    }
//                });
            String url = AppConfig.IP + AppConfig.VIDEO;
            FileInfo[] fileInfos = new FileInfo[mp4List.size()];
            HashMap<String, String> map = new HashMap<>();
            FileInfo fileInfo;
            for (int i = 0; i < mp4List.size(); i++) {
                fileInfo = new FileInfo();
                File file = new File(mp4List.get(i));
                map.put("" + i, mp4List.get(i));
                fileInfo.setFormName("sp" + i);
                fileInfo.setFileType(FileType.AUDIO);
                fileInfo.setFile(file);
                fileInfos[i] = fileInfo;
            }

            HttpUtils.postFilesAsync(url, activity/*, map*/, fileInfos, new OnRequestResult() {
                @Override
                public void result(Exception e, Response response) {
                    if (e == null) {
                        if (response != null && response.isSuccessful()) {
                            try {
                                String string = response.body().string();
                                Gson gson = new Gson();
                                ZpFeedModel zpFeedModel = gson.fromJson(string, ZpFeedModel.class);
                                activity.spSubmitSuccess(zpFeedModel);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }

    public void feed(String user_id, String task_id, Editable text, String zp, String sp, String s1) {
        String feedUrl = AppConfig.IP + AppConfig.FEED + AppConfig.ID + user_id + AppConfig.RWID + task_id + AppConfig.FEEDCONTENT + text
                + AppConfig.ZPURL + zp + AppConfig.SPURL + sp + AppConfig.RESULT + s1;
        HttpUtils.getAsync(feedUrl, activity, new OnRequestResult() {
            @Override
            public void result(Exception e, Response response) {
                if (e == null) {
                    if (response != null && response.isSuccessful()) {
                        try {
                            String string = response.body().string();
                            activity.feedSuccess();

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}

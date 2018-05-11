package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.http.FileInfo;
import com.sx.cfsz.baseframework.http.HttpUtils;
import com.sx.cfsz.baseframework.http.OnRequestResult;
import com.sx.cfsz.cfsz.ui.MainActivity;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/***       Author  shy              我的
 *         Time   2018/4/11 0011    15:06      */

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        BaseApplication.addList(this);

        url = "http://192.168.120.173:8085/dq/upload";

        HashMap<String, String> map = new HashMap<>();
        map.put("超哥", "6666");
        map.put("超哥", "6666");
        map.put("超哥", "6666");
        map.put("超哥", "6666");
        map.put("超哥", "6666");


//        File file = new File("/data/data/com.sx.cfsz/cfsz/ui/LoginActivity.java");
//        String [] strings = new String[]{"6"};
//        HttpUtils.postFilesAsync(url, UserActivity.this, hashMap, "", strings, new OnRequestResult() {
//            @Override
//            public void result(Exception e, Response response) {
//
//            }
//        });

//        File file = new File("/storage/emulated/0/Movies/961.mp4");
//        OkHttpClient client = new OkHttpClient();
//        // form 表单形式上传
//        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        if (file != null) {
//            // MediaType.parse() 里面是上传的文件类型。
//            RequestBody body = RequestBody.create(MediaType.parse("mp4"), file);
//            String filename = file.getName();
//            // 参数分别为， 请求key ，文件名称 ， RequestBody
//            requestBody.addFormDataPart("headImage", file.getName(), body);
//        }
//        if (map != null) {
//            // map 里面是请求中所需要的 key 和 value
//            for (Map.Entry entry : map.entrySet()) {
//                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
//            }
//        }
//        Request request = new Request.Builder().url(url).post(requestBody.build()).tag(UserActivity.this).build();
//        // readTimeout("请求超时时间" , 时间单位);
//        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                String message = e.getMessage();
//
//                Log.i("lfq", "onFailure" + message);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String str = response.body().string();
//                    Log.i("lfq", response.message() + " , body " + str);
//
//                } else {
//                    Log.i("lfq", response.message() + " error : body " + response.body().string());
//                }
//            }
//        });

        initView();
    }

//    public static final MediaType MEDIA_TYPE_MARKDOWN
//            = MediaType.parse("application/mp4; charset=utf-8");


    private void initView() {
        Button bTc = findViewById(R.id.bTc);
        bTc.setOnClickListener(this);

//        File file = new File("/storage/emulated/0/Movies/961.mp4");
//
//        Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file)).build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("//////",e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("//////",response.isSuccessful() + "");
//            }
//        });

//        FileInfo[] infos = new FileInfo[12];
//        Map<String, String> map = new HashMap<>();
//        map.put("fanguineirong", "dsdsdsdssds");
//        HttpUtils.postFilesAsync("url", UserActivity.this, map, infos, new OnRequestResult() {
//            @Override
//            public void result(Exception e, Response response) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        BaseApplication.clenList();
    }
}

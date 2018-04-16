package com.sx.cfsz.cfsz.ui.xrw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.AppConfig;
import com.sx.cfsz.cfsz.ui.PictureSelectorConfig;
import com.sx.cfsz.cfsz.ui.adapter.GridViewAdapter;

import java.util.ArrayList;
import java.util.List;

/***       Author  shy              反馈
 *         Time   2018/4/12 0012    14:41      */

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText etMsxx;
    private ImageView ivBack;
    private TextView tvBack;
    private GridView gridView;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAddImgAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
    }

    private void initView() {
        ivBack = findViewById(R.id.ivFeedbackBack);
        tvBack = findViewById(R.id.tvFeedback);
        etMsxx = findViewById(R.id.etMsxx);
        gridView = findViewById(R.id.gridView);

        ivBack.setOnClickListener(this);
        tvBack.setOnClickListener(this);


        mGridViewAddImgAdapter = new GridViewAdapter(DetailsActivity.this, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivFeedbackBack:
                finish();
                break;
            case R.id.tvFeedback:

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == parent.getChildCount() - 1) {
            //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过8张，才能点击
            if (mPicList.size() == AppConfig.MAX_SELECT_PIC_NUM) {
                //最多添加8张图片
                viewPluImg(position);
            } else {
                //添加凭证图片
//                selectPic(AppConfig.MAX_SELECT_PIC_NUM - mPicList.size());
            }
        } else {
//            viewPluImg(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == AppConfig.REQUEST_CODE_MAIN && resultCode == AppConfig.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(AppConfig.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
            int size = mPicList.size();
            Log.d("删除后////", "" + size);
        }
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
        int size = mPicList.size();
        Log.d("添加后////", "" + size);
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(DetailsActivity.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(AppConfig.IMG_LIST, mPicList);
        intent.putExtra(AppConfig.POSITION, position);
        startActivityForResult(intent, AppConfig.REQUEST_CODE_MAIN);
    }

    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }


}

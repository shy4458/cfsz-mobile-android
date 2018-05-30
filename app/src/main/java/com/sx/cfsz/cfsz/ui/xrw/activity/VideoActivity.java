package com.sx.cfsz.cfsz.ui.xrw.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.sx.cfsz.R;
import com.sx.cfsz.baseframework.base.BaseApplication;
import com.sx.cfsz.baseframework.util.UIUtils;
import com.sx.cfsz.cfsz.ui.myView.BothWayProgressBar;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/***       Author  shy
 *         Time   2018/4/17 0017    9:28      */

public class VideoActivity extends AppCompatActivity implements SurfaceHolder.Callback, BothWayProgressBar.OnProgressEndListener, View.OnTouchListener{
    private static final int LISTENER_START = 200;
    private static final int LING = 0;
    private static final int YI = 1;
    private static final int ER = 2;
    //预览SurfaceView
    private SurfaceView mSurfaceView;
    private Camera mCamera;
    //底部"按住拍"按钮
    private View mStartButton;
    //进度条
    private BothWayProgressBar mProgressBar;
    //进度条线程
    private Thread mProgressThread;
    //录制视频
    private MediaRecorder mMediaRecorder;
    private SurfaceHolder mSurfaceHolder;
    //屏幕分辨率
    private int videoWidth, videoHeight;
    //判断是否正在录制
    private boolean isRecording;
    //段视频保存的目录
    private File mTargetFile;
    //当前进度/时间
    private int mProgress;
    //录制最大时间
    public static final int MAX_TIME = 10;
    //是否上滑取消
    private boolean isCancel;
    //手势处理, 主要用于变焦 (双击放大缩小)
    private GestureDetector mDetector;
    //是否放大
    private boolean isZoomIn = false;
    private boolean prepared = false;// MediaRecord 是否准备完成
    private MyHandler mHandler;
    private TextView mTvTip;
    private boolean isRunning;
    private long l;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_video);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        BaseApplication.addList(this);
        videoWidth = 640;
        videoHeight = 480;
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);

        mDetector = new GestureDetector(this, new ZoomGestureListener());
        /**
         * 单独处理mSurfaceView的双击事件
         */
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });

        mSurfaceHolder = mSurfaceView.getHolder();
        //设置屏幕分辨率
        mSurfaceHolder.setFixedSize(videoWidth, videoHeight);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
        mStartButton = findViewById(R.id.main_press_control);
        mTvTip = (TextView) findViewById(R.id.main_tv_tip);

        mStartButton.setOnTouchListener(this);
        mProgressBar = (BothWayProgressBar) findViewById(R.
                id.main_progress_bar);
        mProgressBar.setOnProgressEndListener(this);
        mHandler = new MyHandler(this);
        mMediaRecorder = new MediaRecorder();
    }

    ///////////////////////////////////////////////////////////////////////////
    // SurfaceView回调
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        startPreView(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            //停止预览并释放摄像头资源
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    /**
     * 开启预览
     *
     * @param holder
     */
    private void startPreView(SurfaceHolder holder) {
        if (mCamera == null) {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        if (mCamera != null) {
            mCamera.setDisplayOrientation(90);
            try {
                mCamera.setPreviewDisplay(holder);
                Camera.Parameters parameters = mCamera.getParameters();
                //实现Camera自动对焦
                List<String> focusModes = parameters.getSupportedFocusModes();
                if (focusModes != null) {
                    for (String mode : focusModes) {
                        mode.contains("continuous-video");
                        parameters.setFocusMode("continuous-video");
                    }
                }
                mCamera.setParameters(parameters);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开始录制
     */
    private void startRecord() {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        //没有外置存储, 直接停止录制
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        try {
            mCamera.unlock();
            mMediaRecorder.setCamera(mCamera);
            //从相机采集视频
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            // 从麦克采集音频信息
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // TODO: 2016/10/20  设置视频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setVideoSize(videoWidth, videoHeight);
            //每秒的帧数
            mMediaRecorder.setVideoFrameRate(30);
            //编码格式
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            // 设置帧频率，然后就清晰了，文件大小也会变化
            mMediaRecorder.setVideoEncodingBitRate(1024 * 1024);
            File targetDir = Environment.
                    getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            l = SystemClock.currentThreadTimeMillis();
            mTargetFile = new File(targetDir,
                    l + ".mp4");
            if (!mTargetFile.exists()) {
                mTargetFile.createNewFile();
            }
            mMediaRecorder.setOutputFile(mTargetFile.getAbsolutePath());
            mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
            //解决录制视频, 播放器横向问题
            mMediaRecorder.setOrientationHint(90);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isRecording = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProgressEndListener() {
        //视频停止录制
        stopRecordSave();
    }

    /**
     * 停止录制 并且保存
     */
    private void stopRecordSave() {
        isRunning = false;
        if (isRecording) {
            stopRecord();
            UIUtils.showToast(this, "视频已经放至" + mTargetFile.getAbsolutePath());
            Log.d("onSuccess1", mTargetFile.getAbsolutePath());
            Intent intent = new Intent();
            intent.putExtra("mp4Url", mTargetFile.getAbsolutePath());
            VideoActivity.this.setResult(RESULT_OK, intent);
            super.finish();
        }
    }

    /**
     * 停止录制, 不保存
     */
    private void stopRecordUnSave() {
        isRunning = false;
        if (mMediaRecorder != null && isRecording) {
            stopRecord();
        }
        if (mTargetFile.exists()) {
            //不保存直接删掉
            mTargetFile.delete();
        }
    }

    private void stopRecord() {
        isRecording = false;

        try{
            mMediaRecorder.stop();
        } catch(RuntimeException stopException){
            //TODO 拍摄视频短按会抛出此异常
        }
//        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    /**
     * 相机变焦
     *
     * @param zoomValue
     */
    public void setZoom(int zoomValue) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            if (parameters.isZoomSupported()) {//判断是否支持
                int maxZoom = parameters.getMaxZoom();
                if (maxZoom == 0) {
                    return;
                }
                if (zoomValue > maxZoom) {
                    zoomValue = maxZoom;
                }
                parameters.setZoom(zoomValue);
                mCamera.setParameters(parameters);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Handler处理
    ///////////////////////////////////////////////////////////////////////////
    private class MyHandler extends Handler {
        private WeakReference<VideoActivity> mReference;
        private VideoActivity mActivity;

        public MyHandler(VideoActivity activity) {
            mReference = new WeakReference<VideoActivity>(activity);
            mActivity = mReference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LING:
                    mActivity.mProgressBar.setProgress(mActivity.mProgress);
                    break;
                case YI:
                    mActivity.mProgressBar.setCancel(true);
                    break;
//                case ER:
//                    VideoActivity.this.mMediaRecorder.start();
//                    VideoActivity.this.isRecording = true;
            }
        }
    }

    /**
     * 触摸事件的触发
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean ret = false;
        int action = event.getAction();
        float ey = event.getY();
        float ex = event.getX();
        //只监听中间的按钮处
        int vW = v.getWidth();
        int left = LISTENER_START;
        int right = vW - LISTENER_START;

        float downY = 0;

        switch (v.getId()) {
            case R.id.main_press_control: {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (ex > left && ex < right) {
                            mProgressBar.setCancel(false);
                            //显示上滑取消
                            mTvTip.setVisibility(View.VISIBLE);
                            mTvTip.setText("↑ 上滑取消");
                            //记录按下的Y坐标
                            downY = ey;
                            // TODO: 2016/10/20 开始录制视频, 进度条开始走
                            mProgressBar.setVisibility(View.VISIBLE);
                            mProgressBar.setProgress(0);
                            isCancel = false;
                            //开始录制
                            UIUtils.showToast(this, "开始录制");
                            startRecord();
                            mProgressThread = new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        mProgress = 0;
                                        isRunning = true;
                                        //mHandler.obtainMessage(ER).sendToTarget();
                                        while (isRunning) {
                                            mProgress++;
                                            mHandler.obtainMessage(LING).sendToTarget();
                                            Thread.sleep(80);
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            mProgressThread.start();
                            ret = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (ex > left && ex < right) {
                            mTvTip.setVisibility(View.INVISIBLE);
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mHandler.obtainMessage(YI).sendToTarget();
                            //判断是否为录制结束, 或者为成功录制(时间过短)
                            if (!isCancel) {
                                if (mProgress < 50) {
                                    //时间太短不保存
                                    stopRecordUnSave();
                                    UIUtils.showToast(this, "时间太短");
                                } else {
                                    //停止录制
                                    stopRecordSave();
                                }
                            } else {
                                //现在是取消状态,不保存
                                stopRecordUnSave();
                                isCancel = false;
                                UIUtils.showToast(this, "取消录制");
                                mProgressBar.setCancel(false);
                            }
                            ret = false;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (ex > left && ex < right) {
                            float currentY = event.getY();
                            if (downY - currentY > 10) {
                                isCancel = true;
                                mProgressBar.setCancel(true);
                                stopRecordUnSave();
                            }
                        }
                        break;
                }
                break;
            }
        }
        return ret;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 变焦手势处理类
    ///////////////////////////////////////////////////////////////////////////
    class ZoomGestureListener extends GestureDetector.SimpleOnGestureListener {
        //双击手势事件
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            super.onDoubleTap(e);
            if (mMediaRecorder != null) {
                if (!isZoomIn) {
                    setZoom(20);
                    isZoomIn = true;
                } else {
                    setZoom(0);
                    isZoomIn = false;
                }
            }
            return true;
        }
    }
}

package com.sx.cfsz.baseframework.http;

/***       Author  shy 
 *         Time   2018/6/1 0001    9:57
 *         上传进度条
 *
 *         */

public interface ProgressRequestListener {

    void onRequestProgress(long bytesWritten, long contentLength, boolean done);

}

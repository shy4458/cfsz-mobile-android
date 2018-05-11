package com.sx.cfsz.cfsz.ui.xrw.util;



public interface VideoCompressListener {
    public void onSuccess(String outputFile, String filename, long duration);
    public void onFail(String reason);
    public void onProgress(int progress);
}

package com.yrbase.luban;

import java.io.File;
import java.util.List;

public interface OnCompressListener {

    /**
     * Fired when the compression is started, override to handle in your own code
     */
    void onStart();

    /**
     * Fired when a compression returns successfully, override to handle in your own code
     */
    void onProgress(File file, int size);//单张显示进度

    /**
     * Fired when a compression fails to complete, override to handle in your own code
     */
    void onError(Throwable e);


    void onSuccessAll(List file);
}

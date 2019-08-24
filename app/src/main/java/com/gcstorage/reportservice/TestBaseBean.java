package com.gcstorage.reportservice;

import java.io.Serializable;

/**
 * Created by EA on 2018/4/18
 */

public class TestBaseBean<T> implements Serializable {
    //public int code;
    public String message;//

    public T data;
    public String errMsg;
    public boolean success;
    public int status;

    @Override
    public String toString() {
        return "BaseBean{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", errMsg='" + errMsg + '\'' +
                ", success=" + success +
                ", status=" + status +
                '}';
    }
}

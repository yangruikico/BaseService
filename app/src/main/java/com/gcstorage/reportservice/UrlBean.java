package com.gcstorage.reportservice;

public class UrlBean {


    /**
     * md5 : 07ae5c3419d4462aa0af3954de54bb5c
     * url : http://47.107.134.212:13922/group1/default/20191107/10/27/8/1573093631259.png
     */



    private String md5;
    private String url;

    public UrlBean(String md5, String url) {
        this.md5 = md5;
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "UrlBean{" +
                "md5='" + md5 + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

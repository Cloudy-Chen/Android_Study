package com.sdu.chy.chytest.UploadFile;

public interface CallBackPro {
    void onSuccess(Object o);
    void onFailed(Exception e);
    void onProgressBar(Long i);//用来显示下载进度

}

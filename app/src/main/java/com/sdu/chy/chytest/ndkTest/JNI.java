package com.sdu.chy.chytest.ndkTest;

/**
 * Java调用对应的C代码
 */

public class JNI {

    //加载JNI生成so库
    static {
        System.loadLibrary("hello_jni");
    }

    //定义Native方法，调用C代码对应方法
    public native String sayHello();
}

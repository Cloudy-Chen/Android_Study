#include<stdio.h>
#include<stdlib.h>
#include<jni.h>

//类名：Java类型+本地类型 对应关系
//C函数命名格式：Java_全类名_方法名
//JNIEnv*：代表了Java环境，通过这个JNIEnv* 指针，就可以对Java端的代码进行操作。
//jobject:代表native方法的实例（调用者），这里是JNI.ini
JNIEXPORT jstring JNICALL Java_com_sdu_chy_chytest_ndkTest_JNI_sayHello(JNIEnv* env,jobject jobj){
    char* text = "I am from C";
    return (*env)->NewStringUTF(env,text);
}
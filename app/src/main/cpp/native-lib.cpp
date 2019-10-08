#include <jni.h>
#include <string>
#include<android/log.h>
#include <fstream>
#include <iostream>

#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO, "========= Info =========   ", __VA_ARGS__)

#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, "========= Error =========   ", __VA_ARGS__)

#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, "========= Debug =========   ", __VA_ARGS__)

#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN, "========= Warn =========   ", __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_myndk_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
char password[] = "5178019";
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_myndk_Crypter_encrypt(JNIEnv *env, jobject thiz, jstring normal_path_jstr,
                                       jstring encrypt_path_jstr) {
    const char *normalPath = (env)->GetStringUTFChars(normal_path_jstr, JNI_FALSE);
    printf("路径：%s \n", normalPath);
    const char *encryptPath = (env)->GetStringUTFChars(encrypt_path_jstr, JNI_FALSE);
    FILE *normal_fp = fopen(normalPath, "rb");
    FILE *encrypt_fp = fopen(encryptPath, "wb");
    int ch;
    int i = 0; //循环使用密码中的字母进行异或运算
    int pwd_len = strlen(password); //密码的长度
    LOGD("你好");

    while ((ch = fgetc(normal_fp)) != EOF) { //End of File
        //写入（异或运算）
        fputc(ch ^ password[i % pwd_len], encrypt_fp);
        i++;
    }
//    关闭
    fclose(encrypt_fp);
    fclose(normal_fp);
    if (normal_fp == nullptr) {
        return env->NewStringUTF("normal_fp ");
    }
    if (encrypt_fp == nullptr) {
        return env->NewStringUTF("encrypt_fp ");
    }
    std::string normal_path = normalPath;
    return env->NewStringUTF(normal_path.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_myndk_Crypter_decrypt(JNIEnv *env, jobject thiz, jstring encrypt_path_jstr,
                                       jstring decrypt_path_jstr) {
    const char* crypt_path = (env)->GetStringUTFChars(encrypt_path_jstr,JNI_FALSE);
    const char* decrypt_path = (env)->GetStringUTFChars(decrypt_path_jstr,JNI_FALSE);
    //打开文件
    FILE *normal_fp = fopen(crypt_path, "rb");
    FILE *crypt_fp = fopen(decrypt_path, "wb");
    //一次读取一个字符
    int ch;
    int i = 0; //循环使用密码中的字母进行异或运算
    int pwd_len = strlen(password); //密码的长度
    while ((ch = fgetc(normal_fp)) != EOF) { //End of File
        //写入（异或运算）
        fputc(ch ^ password[i % pwd_len], crypt_fp);
        i++;
    }
    //关闭
    fclose(crypt_fp);
    fclose(normal_fp);
}
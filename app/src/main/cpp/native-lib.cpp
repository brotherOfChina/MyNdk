#include <jni.h>
#include <string>
#include<android/log.h>
#include <fstream>
#include <iostream>
#include <cstdio>
#include <dirent.h>

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
    const char *crypt_path = (env)->GetStringUTFChars(encrypt_path_jstr, JNI_FALSE);
    const char *decrypt_path = (env)->GetStringUTFChars(decrypt_path_jstr, JNI_FALSE);
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

extern "C"
JNIEXPORT void JNICALL
Java_com_example_myndk_FileUtils_merge(JNIEnv *env, jclass clazz, jstring dit_path) {

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_myndk_FileUtils_split(JNIEnv *env, jclass clazz, jstring j_path, jint file_size) {
    //获取文件位置
    const char *c_path = env->GetStringUTFChars(j_path, nullptr);
    //分割之后子文件的路径列表
    //读取文件，循环写入
    ///1.获取文件的大小
    FILE *fp = fopen(c_path, "rb");
    long size;
    if (fp == nullptr) {
        LOGI("打开文件错误");
        return;
    } else {
        fseek(fp, 0, SEEK_END);
        size = ftell(fp);
        fclose(fp);
        LOGI("文件大小：%ld", size);
    }

    ///2.将文件分割成 file_size 大小的若干份
    int num;
    num = size / file_size;
    LOGI("取余： %ld,取商：%ld", size % file_size, size / file_size);
    std::string path = c_path;
    unsigned int start_index = path.find_last_of('/');
    //获取源文件的名称，并生成同名文件夹
    unsigned int sub_size =
            path.length() - start_index - (path.length() - (path.find_last_of('.') - 1));
    std::string dir_name = path.substr(start_index + 1, sub_size);
    FILE *fp1 = fopen(c_path, "rb");
    for (int i = 0; i < num + 1; i++) {
        //生成文件名
        char s[100] = "";
        sprintf(s, "/%d.jpg", i);
        std::string path_name = path.substr(0, start_index) + "/" + dir_name + s;
        LOGD("%s",path_name.c_str());

        //    生成文件
        FILE *file = fopen(path_name.c_str(), "wb");
        if (file == nullptr) {
            LOGE("打开文件错误");
            opendir("/storage/emulated/0/DCIM/Camera/IMG_20191008_100344");
            return;
        } else {
            LOGD("打开文件成功");
        }
        //计算偏移量，
        int offset = i * file_size;
        fseek(fp1, offset, SEEK_SET);
        int count = 0;
        while (count < file_size && fgetc(fp1) != EOF) { //End of File
            fputc(fgetc(fp1), file);
            count++;
        }
        fclose(file);
    }
    fclose(fp1);
    ///3.生成文件名，名称为分割的第几份
}
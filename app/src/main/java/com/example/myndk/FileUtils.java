package com.example.myndk;

public class FileUtils {
    //分解
    public native static void split(String path,int fileSize);

    //合并
    public native static void merge(String ditPath);
}

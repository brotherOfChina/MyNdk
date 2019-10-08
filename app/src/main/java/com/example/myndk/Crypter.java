package com.example.myndk;

public class Crypter {
    public native String encrypt(String normalPath, String encryptPath);
    public native void decrypt(String encryptPath, String decryptPath);
}

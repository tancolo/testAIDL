// ITestServer.aidl
package com.example.myserviceapp;

// Declare any non-default types here with import statements

interface ITestServer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    //void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString);

    void say(String word);
    int tell(String word, int age);
}
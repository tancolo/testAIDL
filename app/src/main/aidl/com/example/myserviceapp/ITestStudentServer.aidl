// ITestStudentServer.aidl
package com.example.myserviceapp;

import com.example.myserviceapp.aidl.Student;

// Declare any non-default types here with import statements

interface ITestStudentServer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    void getStudentInfo(int age, in Student student);
    void getStudentInfo2(int age, out Student student);
    void getStudentInfo3(int age, inout Student student);

}
// IStudentInfo.aidl
package com.example.myserviceapp.aidlcallback;
import com.example.myserviceapp.aidlcallback.Student;
import com.example.myserviceapp.aidlcallback.RemoteCallback;

interface IStudentInfo {
        Student getStudentInfo();
        oneway void registor(in RemoteCallback callback);
}
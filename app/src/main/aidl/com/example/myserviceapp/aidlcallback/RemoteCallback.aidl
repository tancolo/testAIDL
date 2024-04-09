// RemoteCallback Student.aidl
package com.example.myserviceapp.aidlcallback;
import com.example.myserviceapp.aidlcallback.Student;

interface RemoteCallback {
    oneway void onCallback(in Student student);
}
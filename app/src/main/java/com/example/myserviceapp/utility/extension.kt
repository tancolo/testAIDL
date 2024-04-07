package com.example.myserviceapp.utility

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import java.lang.reflect.InvocationTargetException


fun getProcessName(): String? {
    return if (Build.VERSION.SDK_INT >= 28) Application.getProcessName() else try {
        @SuppressLint("PrivateApi") val activityThread = Class.forName("android.app.ActivityThread")

        // Before API 18, the method was incorrectly named "currentPackageName", but it still returned the process name
        // See https://github.com/aosp-mirror/platform_frameworks_base/commit/b57a50bd16ce25db441da5c1b63d48721bb90687
        val methodName =
            if (Build.VERSION.SDK_INT >= 18) "currentProcessName" else "currentPackageName"
        val getProcessName = activityThread.getDeclaredMethod(methodName)
        getProcessName.invoke(null) as String
    } catch (e: ClassNotFoundException) {
        throw RuntimeException(e)
    } catch (e: NoSuchMethodException) {
        throw RuntimeException(e)
    } catch (e: IllegalAccessException) {
        throw RuntimeException(e)
    } catch (e: InvocationTargetException) {
        throw RuntimeException(e)
    }

    // Using the same technique as Application.getProcessName() for older devices
    // Using reflection since ActivityThread is an internal API
}


// ERROR
fun Application.getProcessName01(): String? {
    val pid = android.os.Process.myPid()
    val manager = this.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
    return manager?.runningAppProcesses?.filterNotNull()?.firstOrNull { it.pid == pid }?.processName
}

fun String.getPidName(application: Application): String? {
    val pid = android.os.Process.myPid()
    val manager = application.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
    return manager?.runningAppProcesses?.filterNotNull()?.firstOrNull { it.pid == pid }?.processName
}
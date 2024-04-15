package com.example.myserviceapp

import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myserviceapp.aidl.TestAidlServer
import com.example.myserviceapp.aidl.TestAidlServiceConnection
import com.example.myserviceapp.aidl.TestStudentServer
import com.example.myserviceapp.aidl.TestStudentServiceConnection
import com.example.myserviceapp.aidlcallback.RemoteCallback
import com.example.myserviceapp.aidlcallback.Student
import com.example.myserviceapp.aidlcallback.StudentCallbackServiceConnection
import com.example.myserviceapp.aidlcallback.StudentService
import com.example.myserviceapp.messenger.TestMessengerServer
import com.example.myserviceapp.messenger.TestMessengerServiceConnection
import com.example.myserviceapp.service.MyBinder
import com.example.myserviceapp.service.MyService
import com.example.myserviceapp.testbinder.TestServer
import com.example.myserviceapp.testbinder.TestServiceConnection
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName
    private lateinit var mService: MyService
    private lateinit var mTextView: TextView
    private lateinit var mServiceConnection: ServiceConnection
    private lateinit var mTestServiceConnection: TestServiceConnection
    private lateinit var mAidlServiceConnection: TestAidlServiceConnection
    private lateinit var mStudentServiceConnection: TestStudentServiceConnection

    private lateinit var mMessengerServiceConnection: TestMessengerServiceConnection

    private lateinit var mStudentCallbackServiceConnection: StudentCallbackServiceConnection

    private lateinit var mWindowManager: WindowManager
    private lateinit var mWindowTextView: TextView

//    private lateinit var mRemoteCallback: RemoteCallback.Stub

    private lateinit var mTestHandlerTextView: TextView
    private val handler = Handler(Looper.getMainLooper()) { msg ->
        mTestHandlerTextView.text = msg.arg1.toString()
        false
    }

    // Object declaration, in fact, it is a static final class in java
    object Handler02 : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            //super.handleMessage(msg)
            println("Handler02->handleMessage, msg: ${msg.arg1}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        mTextView = findViewById(R.id.tx_random_number)
        val intent = Intent(this, MyService::class.java)

        val buttonStart: Button = findViewById(R.id.button_start_service)
        buttonStart.setOnClickListener {
            Log.d(TAG, "click start button")
            // 1.
            //startService(intent)

            // 2. bind service
//            bindService()

            // 3. bind TestServer
//            bindTestServer()

            // 4. aidl server
//            bindAidlServer()

            // 5. student adil
//            bindStudentServer()

            // 6. messenger
//            bindMessengerServer()

            // 7. student callback service
//            bindStudentCallbackService()

        }

        buttonStart.setOnLongClickListener {
            Log.d(TAG, "onLongClickListener")
//            false
            true
        }


        val buttonStop: Button = findViewById(R.id.button_stop_service)
        buttonStop.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d(TAG, "click stop button")
                // 1. stop server
                //stopService(intent)

                // 2. unbind service
//                unBindService()

                // 3. unbind testServer
//                unbindTestServer()

                // 4. unbind aidl server
//                unbindAidlServer()

                // 5. unbind student server
//                unbindStudentServer()

                // 7. unbind student callback server
//                unbindStudentCallbackService()
            }
        })

        // 8. show/dismiss window with textView
        val buttonShowWindow = findViewById<Button>(R.id.button_show_window)
        buttonShowWindow.setOnClickListener {
            grantPermissionAndShowWindow()
        }
        val buttonDismissWindow = findViewById<Button>(R.id.button_dismiss_window)
        buttonDismissWindow.setOnClickListener {
            dismissWindow()
        }

        // 9-1) show alert dialog
        findViewById<Button>(R.id.button_show_dialog).setOnClickListener {
            showAlertDialog()
        }

        // 9-2) custom dialog
        findViewById<Button>(R.id.button_show_custom_dialog).setOnClickListener {
            showCustomDialog()
        }

        // 10. popupWindow
        findViewById<Button>(R.id.button_show_popupwindow).setOnClickListener {
            showPopupWindow(it)
        }

        // 11. Toast
        findViewById<Button>(R.id.button_toast).setOnClickListener {
            showToast()
        }

        // 12. Handler-loop-message
        mTestHandlerTextView = findViewById(R.id.txt_show_message)
        findViewById<Button>(R.id.button_handler).setOnClickListener {
            testHandler()
        }

    }

    // 12. Handler-loop-message
    private fun testHandler() {
        thread {
            val message = Message.obtain()
            message.arg1 = 20
            //handler.sendMessage(message)
            handler.sendMessageDelayed(message, 1000)

            // new Message object with Message()
            val msg01 = Message()
            msg01.arg1 = 30
            handler.sendMessageDelayed(msg01, 2000)

            //send message with object declaration, subclass of Handler
            val message02 = Message.obtain()
            message02.arg1 = 2000
            Handler02.sendMessage(message02)

            // use post methods
            handler.postDelayed({
                println("postDelayed 3000mm")
            }, 3000)

        }

        mTestHandlerTextView.post {
            println("textView.width = ${mTestHandlerTextView.width}, height = ${mTestHandlerTextView.height}")
        }
    }

    // 11. show toast
    private fun showToast() {
        val toast = Toast.makeText(this, "this is a toast", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 100, 100)
        toast.show()
    }

    // 10. popup window
    private fun showPopupWindow(view: View) {
        val inflaterView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_layout, null)
        val linearLayout = inflaterView.findViewById<LinearLayout>(R.id.linear_layout)
        val okButton = inflaterView.findViewById<Button>(R.id.okButton)
        val popupWindow = PopupWindow(400, 400)
        popupWindow.contentView = linearLayout

        //设置focusable为true即可点击外部消失PopupWindow，反之则不消失
        popupWindow.isFocusable = true

//        popupWindow.showAsDropDown(view)
        //popupWindow.showAsDropDown(view, 100, 200, Gravity.CENTER_HORIZONTAL)
        popupWindow.showAtLocation(view, Gravity.END, 200, 100)

        okButton.setOnClickListener {
            popupWindow.dismiss()
        }
    }

    // 9-2) customize dialog
    private fun showCustomDialog() {
        //1. CustomDialog(this).show()

        //2. custom view
        // Inflate the layout XML file
        val inflater = LayoutInflater.from(this)
        val inflaterView = inflater.inflate(R.layout.custom_dialog_layout, null) as LinearLayout
        val button = inflaterView.findViewById<Button>(R.id.okButton)

        println(inflaterView)
        println(button)

        val dialog = Dialog(this)
        dialog.setContentView(inflaterView)
        // Dialog 外部变暗
        dialog.window?.attributes?.dimAmount = 0.433f
        dialog.show()
        button.setOnClickListener { dialog.dismiss() }
    }


    // 9-1). show alert dialog
    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert Dialog Title").setMessage("This is a simple message for the dialog")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        val alertDialog = builder.create()
        alertDialog.show()
        //Dialog点击外部和点击物理返回键消失需要同时满足两个条件
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
    }


    // 8. grant the permission
    private fun grantPermissionAndShowWindow() {
        // by ChatGPT
        // Inside your activity or fragment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            // If the permission has not been granted yet, request it
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION)
        } else {
            // Permission has already been granted, proceed with adding the view
            createWindowWithTextView(this)
        }
    }

    // code was created by ChatGPT
    private val SYSTEM_ALERT_WINDOW_PERMISSION =
        2038 // Define a constant for the permission request code

    // Handle the result of the permission request
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SYSTEM_ALERT_WINDOW_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                // Permission granted, proceed with adding the view
                createWindowWithTextView(this)
            } else {
                // Permission not granted, handle it accordingly (e.g., show a message to the user)
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun createWindowWithTextView(context: Context) {
        // Create a WindowManager instance
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // Create a LayoutParams object to define the window attributes
        val layoutParams = WindowManager.LayoutParams(
            400, // Width of the window
            400, // Height of the window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }, // Type of the window
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, // Flags
            PixelFormat.RGBA_8888 // Format
        )

        // Set window gravity
        layoutParams.gravity = Gravity.CENTER
        //Window外部区域变暗
        layoutParams.dimAmount = 0.7f
        layoutParams.flags = layoutParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND

        // Create a TextView
        mWindowTextView = TextView(context)
        mWindowTextView.text = "This is a sample TextView"
        mWindowTextView.background = ColorDrawable(Color.WHITE)
        // You can customize other properties of the TextView here

        // Add the TextView to the WindowManager
        mWindowManager.addView(mWindowTextView, layoutParams)
    }


    private fun dismissWindow() {
        try {
            mWindowManager.removeViewImmediate(mWindowTextView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 7. bind and unbind student service and register the callback
    private fun bindStudentCallbackService() {

        val remoteCallback = object : RemoteCallback.Stub() {
            override fun onCallback(student: Student?) {
                Log.d(TAG, "callback student: $student")
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity, "client receive change: $student", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        mStudentCallbackServiceConnection = StudentCallbackServiceConnection(remoteCallback)

        val intent = Intent(this, StudentService::class.java)
        Log.d(TAG, "call bindService(), intent: $intent")
        bindService(intent, mStudentCallbackServiceConnection, BIND_AUTO_CREATE)

//        Intent().let {
//
//            val remoteCallback = object : RemoteCallback.Stub() {
//                override fun onCallback(student: Student?) {
//                    Log.d(TAG, "callback student: $student")
//                    runOnUiThread {
//                        Toast.makeText(this@MainActivity, "client receive change: $student", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//            mStudentCallbackServiceConnection = StudentCallbackServiceConnection(remoteCallback)
//
//            it.setComponent(ComponentName("com.example.myserviceapp.aidlcallback",
//                "com.example.myserviceapp.aidlcallback.StudentService"))
//        }.also {
//            Log.d(TAG, "call bindService(), intent: $it")
//            bindService(it, mStudentCallbackServiceConnection, BIND_AUTO_CREATE)
//        }

    }

    private fun unbindStudentCallbackService() {
        unbindService(mStudentCallbackServiceConnection)
    }

    // end 7.

    private fun bindMessengerServer() {
        val intent = Intent(this, TestMessengerServer::class.java)
        mMessengerServiceConnection = TestMessengerServiceConnection()
        bindService(intent, mMessengerServiceConnection, BIND_AUTO_CREATE)
    }

    private fun unbindMessengerServer() {
        unbindService(mMessengerServiceConnection)
    }

    private fun unbindStudentServer() {
        unbindService(mStudentServiceConnection)
    }

    private fun bindStudentServer() {
        val intent = Intent(this, TestStudentServer::class.java)
        mStudentServiceConnection = TestStudentServiceConnection()
        bindService(intent, mStudentServiceConnection, BIND_AUTO_CREATE)
    }

    private fun unbindAidlServer() {
        unbindService(mAidlServiceConnection)
    }

    private fun bindAidlServer() {
        val intent = Intent(this, TestAidlServer::class.java)
        mAidlServiceConnection = TestAidlServiceConnection()
        bindService(intent, mAidlServiceConnection, BIND_AUTO_CREATE)
    }

    private fun unbindTestServer() {
        Log.d(TAG, "unBindTestServer()...")
        unbindService(mTestServiceConnection)
    }

    private fun bindTestServer() {
        Log.d(TAG, "bindTestServer()...")
        mTestServiceConnection = TestServiceConnection()
        val intent = Intent(this, TestServer::class.java)
        this.bindService(intent, mTestServiceConnection, BIND_AUTO_CREATE)
    }

    private fun bindService() {
        mServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                Log.d(TAG, "onServiceConnected, name: $name")
                //service即是从onBind(xx)方法返回的(绑定者和Service同一进程)
                val myBinder = service as MyBinder
                //获取Service的引用
                mService = myBinder.getService() as MyService

                //mTextView.setText(mService.getRandomNumber().toString())
                mTextView.text = mService.getCount().toString()
            }

            override fun onServiceDisconnected(name: ComponentName) {
                //Service被销毁时调用(内存不足等，正常解绑不会走这)
                Log.d(TAG, "onServiceDisconnected, name: $name")
            }

            override fun onBindingDied(name: ComponentName?) {
                super.onBindingDied(name)
                Log.d(TAG, "onBindingDied, name: ${name?.toString()}")
            }

            override fun onNullBinding(name: ComponentName?) {
                super.onNullBinding(name)
                Log.d(TAG, "onNullBinding, name: ${name?.toString()}")
            }
        }
        val intent = Intent(this, MyService::class.java)
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
    }

    private fun unBindService() {
        Log.d(TAG, "unBindService()...")
        unbindService(mServiceConnection)
    }

    override fun onDestroy() {
        Log.d(TAG, "MainActivity -> onDestroy()")
//        unbindService(mServiceConnection)

        super.onDestroy()
    }
}


class CustomDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog_layout)

        val messageTextView = findViewById<TextView>(R.id.messageTextView)
        val okButton = findViewById<Button>(R.id.okButton)

        okButton.setOnClickListener {
            dismiss()
        }
    }

}
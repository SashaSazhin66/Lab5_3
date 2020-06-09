package com.work.lab5_3

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    companion object {
        const val BATTERY_LOW = "android.intent.action.ACTION_BATTERY_LOW"
        const val CAMERA_BUTTON = "android.intent.action.CAMERA_BUTTON"
        const val AIRPLANE_MODE = "android.intent.action.AIRPLANE_MODE"
    }

    private var receiver: Receiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        receiver = Receiver()
        val filter = IntentFilter().apply {
            addAction(BATTERY_LOW)
            addAction(CAMERA_BUTTON)
            addAction(AIRPLANE_MODE)
        }
        registerReceiver(receiver, filter)

    }

    override fun onStop() {
        super.onStop()
        receiver?.let { unregisterReceiver(it) }
    }

    class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (context != null && intent != null)
                intent.action?.let {
                    val dialog = when (it) {
                        BATTERY_LOW -> AlertDialog.Builder(context)
                                .setMessage("Battery is low now!").setPositiveButton("Ok")
                                { dialog, _ -> dialog.dismiss() }
                        CAMERA_BUTTON -> AlertDialog.Builder(context)
                                .setMessage("Camera button clicked!").setPositiveButton("Ok")
                                { dialog, _ -> dialog.dismiss() }
                        AIRPLANE_MODE -> {
                            when (intent.getBooleanExtra("state", false)) {
                                true -> AlertDialog.Builder(context)
                                        .setMessage("Airplane onn!").setPositiveButton("Ok")
                                        { dialog, _ -> dialog.dismiss() }
                                false -> AlertDialog.Builder(context)
                                        .setMessage("Airplane off!").setPositiveButton("Ok")
                                        { dialog, _ -> dialog.dismiss() }
                            }
                        }
                        else -> {
                            return
                        }
                    }
                    dialog.show()
                }
        }
    }
}

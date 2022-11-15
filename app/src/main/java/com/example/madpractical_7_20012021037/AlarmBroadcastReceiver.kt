package com.example.madpractical_7_20012021037

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.madpractical_7_20012021037.AlarmService

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val str1 = intent.getStringExtra("Service1")
        if (str1 == "Start" || str1 == "Stop") {
            val intentService = Intent(context, AlarmService::class.java)
            intentService.putExtra("Service1", intent.getStringExtra("Service1"))
            if (str1 == "Start") {
                context.startService(intentService)
            } else if (str1 == "Stop") {
                context.stopService(intentService)
            }
        }
    }
}
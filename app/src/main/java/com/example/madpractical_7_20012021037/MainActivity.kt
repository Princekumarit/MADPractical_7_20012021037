package com.example.madpractical_7_20012021037


import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.util.*

class MainActivity : AppCompatActivity() {
    var mili: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val CancleAlarmCardView = findViewById<MaterialCardView>(R.id.remove_alram_card)
        val BtnCreateAlarm = findViewById<MaterialButton>(R.id.create_alarm)
        val SetAlarmTime = findViewById<TextView>(R.id.text_clock_)
        val BtnCancelAlarm = findViewById<MaterialButton>(R.id.cancel_alarm)
        val clockTC = findViewById<TextClock>(R.id.text_clock)

        clockTC.format12Hour = "hh:mm:ss a MMM,dd yyyy "

        CancleAlarmCardView.visibility = View.GONE

        BtnCreateAlarm.setOnClickListener {
            var cal: Calendar = Calendar.getInstance()
            var hour :Int = cal.get(Calendar.HOUR_OF_DAY)
            var min :Int = cal.get(Calendar.MINUTE)
            val timepickerdialog =
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = { view, h, m ->
                    mili = getMillis(h, m)
                    setAlarm(getMillis(h, m), "Start")
                    CancleAlarmCardView.visibility = View.VISIBLE
                    SetAlarmTime.text = h.toString() + ":" + m.toString()
                }), hour, min, false)
            timepickerdialog.show()
        }

        BtnCancelAlarm.setOnClickListener {
            setAlarm(mili, "Stop")
            CancleAlarmCardView.visibility = View.GONE
        }
    }

    private fun setAlarm(millisTime: Long, str: String) {
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        intent.putExtra("Service1", str)
        val pendingIntent =
            PendingIntent.getBroadcast(applicationContext, 234324243, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if (str == "Start") {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                millisTime,
                pendingIntent
            )
            Toast.makeText(this,"Set Alarm ",Toast.LENGTH_SHORT).show()
        } else if (str == "Stop") {
            alarmManager.cancel(pendingIntent)
            sendBroadcast(intent)
            Toast.makeText(this,"Cancel Alarm",Toast.LENGTH_SHORT).show()
        }
    }

    fun getMillis(hour: Int, min: Int): Long {
        val setcalendar = Calendar.getInstance()
        setcalendar[Calendar.HOUR_OF_DAY] = hour
        setcalendar[Calendar.MINUTE] = min
        setcalendar[Calendar.SECOND] = 0
        return setcalendar.timeInMillis
    }
}
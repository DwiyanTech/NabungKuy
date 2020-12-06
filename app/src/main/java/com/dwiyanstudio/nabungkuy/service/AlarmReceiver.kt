package com.dwiyanstudio.nabungkuy.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.dwiyanstudio.nabungkuy.Constanst
import com.dwiyanstudio.nabungkuy.R
import com.dwiyanstudio.nabungkuy.ui.MainActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ALARM_CHECK", "ALARM_CALLED")
        showAlarmNotifivcation(context)
        if (intent.action.equals("android.intent.action.BOOT_COMPLETED")) {
            showRepeatingAlarm(context)
            showAlarmNotifivcation(context)
        }
    }

    private fun showAlarmNotifivcation(context: Context) {
        val notifMgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat.Builder(context, Constanst.CHANNEL_NOTIFICATION)
                .setSmallIcon(R.drawable.ic_baseline_arrow_back_violet_24)
                .setContentTitle("NabungKuy !!")
                .setContentText("Apakah Anda Sudah Menabung Hari ini ?")
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constanst.CHANNEL_NOTIFICATION,
                Constanst.CHANNEL_NAME_NOTIFICATION,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            notificationBuilder.setChannelId(Constanst.CHANNEL_NOTIFICATION)
            notifMgr.createNotificationChannel(channel)
        }
        val buildNotif = notificationBuilder.build()
        notifMgr.notify(Constanst.NOTIFICATION_REMINDER_ID, buildNotif)


    }

    fun showRepeatingAlarm(context: Context) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, Constanst.ALARM_REQUESTCODE, alarmIntent, 0)
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        alarmMgr.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show()

    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, Constanst.ALARM_REQUESTCODE, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show()
    }
}
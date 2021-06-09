package com.bangkit.electrateam.qualityumapp.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bangkit.electrateam.qualityumapp.MainActivity
import com.bangkit.electrateam.qualityumapp.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(EXTRA_MESSAGE)
        val notifId = ID_REMINDER
        val title = "GitHub Reminder"

        message?.let {
            showReminderNotification(context, title, message, notifId)
        }
    }

    private fun showReminderNotification(
        context: Context?,
        title: String,
        message: String?,
        notifId: Int
    ) {

        val channelId = "Channel_1"
        val channelName = "GitHubApp Reminder"

        val notificationManagerCompat =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notifIntent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, channelId)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(alarmSound)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        // check for android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
                    .apply {
                        enableLights(true)
                        lightColor = Color.RED
                        enableVibration(true)
                        vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
                    }

            notificationManagerCompat.createNotificationChannel(notifChannel)

            builder.setChannelId(channelId)
        }
        notificationManagerCompat.notify(notifId, builder.build())
    }

    fun setReminder(context: Context?, date: Long, message: String?) {

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_REMINDER,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Reminder one day before
        val dateRemind = date - 86400000

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            dateRemind,
            pendingIntent
        )

        Toast.makeText(context, context.getString(R.string.setup_reminder), Toast.LENGTH_SHORT)
            .show()
    }

    fun cancelReminder(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_REMINDER,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, context.getString(R.string.cancel_reminder), Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        const val EXTRA_MESSAGE = "message"
        const val ID_REMINDER = 100
        private const val Date_FORMAT = "dd MMM yyyy"
    }
}
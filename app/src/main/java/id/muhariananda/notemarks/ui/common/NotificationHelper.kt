package id.muhariananda.notemarks.ui.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import id.muhariananda.notemarks.R
import id.muhariananda.notemarks.common.Constant.CHANNEL_ID
import id.muhariananda.notemarks.common.Constant.NOTIFICATION_ID
import id.muhariananda.notemarks.ui.MainActivity

class NotificationHelper(private val context: Context) {

    fun createNotification(task: String) {
        createNotificationChannel()
        
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.todoListFragment)
            .createPendingIntent()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_done)
            .setContentTitle("Your task reminder!")
            .setContentText(task)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_ID,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Todo channel"
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

}
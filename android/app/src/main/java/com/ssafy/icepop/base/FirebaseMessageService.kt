package com.ssafy.icepop.base

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.icepop.R
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.icepop.ui.auth.AuthActivity

private const val TAG = "FirebaseMessageService_ssafy"
class FirebaseMessageService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        MainActivity.uploadToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "onMessageReceived: ${remoteMessage.notification}")

        var messageTitle = ""
        var messageContent = ""

        if (remoteMessage.notification != null) {
            Log.d(TAG, "onMessageReceived: not null")
            messageTitle = remoteMessage.notification!!.title.toString()
            messageContent = remoteMessage.notification!!.body.toString()
        }
        else {
            messageTitle = remoteMessage.data.get("myTitle").toString()
            messageContent = remoteMessage.data.get("myBody").toString()

            Log.d(TAG, "onMessageReceived: $messageTitle $messageContent")
        }

        val mainIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        //mainIntent를 인자로 보내줘
        //클릭이 될지 안될지 모르는 인텐트 pendingintent
        val mainPendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE)

        val builder1 = NotificationCompat.Builder(this, MainActivity.channel_id)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle(messageTitle)
            .setContentText(messageContent)
            .setAutoCancel(true)
            .setContentIntent(mainPendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)      // head up message

        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(101, builder1.build())
    }
}
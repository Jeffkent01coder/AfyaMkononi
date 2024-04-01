package com.example.afyamkononi.shared.fire.messagingService

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.afyamkononi.R
import com.example.afyamkononi.shared.fire.notification.retrofit.ApiService
import com.example.afyamkononi.shared.fire.screens.MyChats
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import kotlin.random.Random
import com.google.firebase.messaging.FirebaseMessaging


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Check if the message contains data payload.
        remoteMessage.data.isNotEmpty().let {
            // Here, you can handle the data payload.
            val title = remoteMessage.data["title"] ?: "New Message"
            val body = remoteMessage.data["body"] ?: "Tap to see message"
            sendNotification(title, body)
        }

        // Check if the message contains notification payload.
        remoteMessage.notification?.let {
            val title = it.title ?: "New Message"
            val body = it.body ?: "Tap to see message"
            sendNotification(title, body)
        }
    }

    private fun sendNotification(title: String, messageBody: String) {
        val intent = Intent(this, MyChats::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(Random.nextInt(), notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // Send the token to your backend server
        registerDeviceToBackend(token)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Timber.tag("FCM Token").d(token)
        })
    }

    private fun registerDeviceToBackend(token: String) {
        // Create a Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("http:127.0.0.1:300o/sendNotification") // Replace with your backend URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of your ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Call the registerDevice API
        val call = apiService.registerDevice(token)

        // Enqueue the call
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Device registration successful
                    Toast.makeText(this@MyFirebaseMessagingService, "Successful", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle error
                    Toast.makeText(this@MyFirebaseMessagingService, "Failed", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle failure
            }
        })
    }
}

package com.jose.shopmart

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShopKartApplication: Application() {

    //Creating channel for API level 33 (Android 13)
    //Checking android version for minimum O is not required as this app supports minimum android 10
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val channelDeliveryStatus = NotificationChannel("Channel_Id","Delivery Status",NotificationManager.IMPORTANCE_HIGH)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channelDeliveryStatus)
    }
}
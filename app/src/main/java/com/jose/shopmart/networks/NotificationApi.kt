package com.jose.shopmart.networks

import com.jose.shopmart.ShopKartUtils
import com.jose.shopmart.models.PushNotificationData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Singleton

interface NotificationApi {

    //    @Singleton
    @Headers("Authorization: key = ${ShopKartUtils.SERVER_KEY}", "Content-Type: ${ShopKartUtils.CONTENT_TYPE}")
    @POST("fcm/send")
    suspend fun postNotification( @Body notification: PushNotificationData): Response<ResponseBody>
}
package com.ssafy.icepop.data.remote

import com.ssafy.icepop.data.model.dto.NotificationItem
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationService {
    @GET("/notification/{email}")
    suspend fun getNotificationItems(@Path("email")email: String) : List<NotificationItem>


}
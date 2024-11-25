package com.ssafy.icepop.data.remote

import com.ssafy.smartstore_jetpack.base.ApplicationClass

class RetrofitUtil {
    companion object {
        val userService: UserService = ApplicationClass.retrofit.create(UserService::class.java)
        val iceCreamService: IceCreamService = ApplicationClass.retrofit.create(IceCreamService::class.java)
        val orderService: OrderService = ApplicationClass.retrofit.create(OrderService::class.java)
        val notificationService: NotificationService = ApplicationClass.retrofit.create(NotificationService::class.java)
        val reviewService: ReviewService = ApplicationClass.retrofit.create(ReviewService::class.java)
        val kakaoService: KakaoService = ApplicationClass.retrofit.create(KakaoService::class.java)
    }
}
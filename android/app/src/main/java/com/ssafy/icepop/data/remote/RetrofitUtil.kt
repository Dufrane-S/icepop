package com.ssafy.icepop.data.remote

import com.ssafy.smartstore_jetpack.base.ApplicationClass

class RetrofitUtil {
    companion object {
        val userService: UserService = ApplicationClass.retrofit.create(UserService::class.java)
        val iceCreamService: IceCreamService = ApplicationClass.retrofit.create(IceCreamService::class.java)
    }
}
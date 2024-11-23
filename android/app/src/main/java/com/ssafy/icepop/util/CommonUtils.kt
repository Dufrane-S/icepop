package com.ssafy.smartstore_jetpack.util

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.icepop.R
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CommonUtils {

    //천단위 콤마
    fun makeComma(num: Int): String {
        val comma = DecimalFormat("#,###")
        return "${comma.format(num)}원"
    }

    fun makeKcal(kcal: Int) : String {
        return "${kcal}kcal"
    }

    //날짜 포맷 출력
    fun dateformatYMDHM(time:Date):String{
        val format = SimpleDateFormat("yyyy.MM.dd. HH:mm", Locale.KOREA)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return format.format(time)
    }

    fun dateformatYMD(time: Date):String{
        val format = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return format.format(time)
    }

    private fun checkTime(time:Long):Boolean{
        val curTime = (Date().time+60*60*9*1000)

        return (curTime - time) > ApplicationClass.ORDER_COMPLETED_TIME
    }

    fun setVerticalDivider(context: Context, recyclerView: RecyclerView) {
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }


    fun getMaleNumber(gender: String) : Int {
        return when (gender) {
            "남자" -> 1
            else -> 2
        }
    }

    fun getAge(context: Context, age: String) : Int {
        return when(age) {
            context.getString(R.string.ten) -> 10
            context.getString(R.string.twenty) -> 20
            context.getString(R.string.thirty) -> 30
            context.getString(R.string.forty_over) -> 40
            else -> -1
        }
    }
}
package com.ssafy.smartstore_jetpack.util

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.icepop.R
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CommonUtils {
    const val MALE = 1
    const val FEMALE = 2

    //새싹, 꽃, 열매, 나무
    const val SEED_LEVEL = "씨앗"
    const val SPROUT_LEVEL = "새싹"
    const val FLOWER_LEVEL = "꽃"
    const val FRUIT_LEVEL = "열매"
    const val TREE_LEVEL = "나무"
    const val NOTHING_NEXT_LEVEL = "다음 단계 없음"
    const val NOTHING_EXIST_LEVEL = "단계가 존재하지 않음"

    fun getNextUserLevel(level: String) : String {
        return when(level) {
            SEED_LEVEL -> SPROUT_LEVEL
            SPROUT_LEVEL -> FLOWER_LEVEL
            FLOWER_LEVEL -> FRUIT_LEVEL
            FRUIT_LEVEL -> TREE_LEVEL
            TREE_LEVEL -> NOTHING_NEXT_LEVEL
            else -> NOTHING_EXIST_LEVEL
        }
    }

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

    fun getGenderByNumber(gender: Int) : String {
        return when (gender) {
            MALE -> "남"
            else -> "여"
        }
    }

    fun getMaleNumber(gender: String) : Int {
        return when (gender) {
            "남자" -> MALE
            else -> FEMALE
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

    fun formatLongToDateTime(timestamp: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
            .withZone(ZoneId.systemDefault()) // 시스템 기본 시간대
        return formatter.format(Instant.ofEpochMilli(timestamp))
    }
}
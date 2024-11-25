package com.ssafy.icepop.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ssafy.icepop.data.model.dto.Member

class SharedPreferencesUtil (context: Context) {
    val SHARED_PREFERENCES_NAME = "smartstore_preference"
    val COOKIES_KEY_NAME = "cookies"

    val LAST_RUN_TIME = "lastRunTime"
    val ONE_DAY_IN_MILLIS = 10000L // 24시간 = 86,400,000ms
//    val ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000L // 24시간 = 86,400,000ms

    var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun addUser(member: Member) {
        val editor = preferences.edit()
        editor.putString("email", member.email)
        editor.putInt("age", member.age)
        editor.putInt("gender", member.gender)

        editor.apply()
    }

    fun getUser() : Member {
        val email = preferences.getString("email", "")

        if (email != "") {
            val age = preferences.getInt("age", -1)
            val gender = preferences.getInt("gender", -1)

            return Member(email = email!!, age = age, gender = gender)
        }

        return Member()
    }


//    //사용자 정보 저장
//    fun addUser(user: User){
//        val editor = preferences.edit()
//        editor.putString("id", user.id)
//        editor.putString("name", user.name)
//        editor.apply()
//    }
//
//    fun getUser(): User {
//        val id = preferences.getString("id", "")
//        if (id != ""){
//            val name = preferences.getString("name", "")
//            return User(id!!, name!!, "",0)
//        }else{
//            return User()
//        }
//    }

    fun deleteUser(){
        //preference 지우기
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    fun addUserCookie(cookies: HashSet<String>) {
        val editor = preferences.edit()
        editor.putStringSet(COOKIES_KEY_NAME, cookies)
        editor.apply()
    }

    fun getUserCookie(): MutableSet<String>? {
        return preferences.getStringSet(COOKIES_KEY_NAME, HashSet())
    }

    fun deleteUserCookie() {
        preferences.edit().remove(COOKIES_KEY_NAME).apply()
    }

    fun addNotice(info: String) {
        val list = getNotice()

        list.add(info)
        val json = Gson().toJson(list)

        preferences.edit().let {
            it.putString("notice", json)
            it.apply()
        }
    }

    fun setNotice(list: MutableList<String>) {
        preferences.edit().let {
            it.putString("notice", Gson().toJson(list))
            it.apply()
        }
    }

    fun getNotice() : MutableList<String> {
        val str = preferences.getString("notice", "")!!
        val list = if(str.isEmpty()) mutableListOf<String>() else Gson().fromJson(str, MutableList::class.java) as MutableList<String>

        return list
    }

    // 마지막 실행 시각을 저장하는 메서드
    fun saveCurrentTime(currentTime: Long) {
        preferences.edit()
            .putLong(LAST_RUN_TIME, currentTime)
            .apply()
    }

    // 마지막 실행 시각을 반환하는 메서드
    fun getLastRunTime(): Long {
        return preferences.getLong(LAST_RUN_TIME, 0L)
    }
}
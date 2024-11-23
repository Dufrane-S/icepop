package com.ssafy.icepop.ui.auth

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.ActivityAuthBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.BaseActivity
import com.ssafy.smartstore_jetpack.util.PermissionChecker


private const val TAG = "AuthActivity_ssafy"
class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {

    private val checker = PermissionChecker(this)
    private val runtimePermissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_fragment_layout, LoginFragment())
            .commit()

        checkNotificationPermission()
    }

    private fun checkNotificationPermission() {
        if (!checker.checkPermission(this, runtimePermissions)) {
            //권한 획득시
            checker.setOnGrantedListener {
                init()
            }

            checker.requestPermissionLauncher.launch(runtimePermissions)  // 권한 없으면 창 띄우기
        }
        //이미 전체 권한이 있는 경우,
        else {
            init()
        }
    }

    private fun init(){
        initFCM()
        createNotificationChannel(channel_id, "ssafy")
    }

    private fun initFCM() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM 토큰 얻기에 실패하였습니다.", task.exception)
                return@OnCompleteListener
            }

            // token log 남기기
            Log.d(TAG, "token: ${task.result?:"task.result is null"}")

            //토큰을 서버에 업로드
            if(task.result != null){
                uploadToken(task.result!!)
            }
        })
    }

    // Notification 수신을 위한 체널 추가
    private fun createNotificationChannel(id: String, name: String){
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationChannel(id, name, importance))
        }
    }

//    fun openFragment(int: Int){
//        val transaction = supportFragmentManager.beginTransaction()
//        when(int){
//            1 -> {
//                val intent = Intent(this, MainActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent)
//            }
//            2 -> transaction.replace(R.id.frame_layout_login, JoinFragment())
//                .addToBackStack(null)
//
//            3 -> {
//                // 회원가입한 뒤 돌아오면, 2번에서 addToBackStack해 놓은게 남아 있어서,
//                // stack을 날려 줘야 한다. stack날리기.
//                supportFragmentManager.popBackStack()
//                transaction.replace(R.id.frame_layout_login, LoginFragment())
//            }
//        }
//        transaction.commit()
//    }

    fun changeFragment(viewInt: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (viewInt) {
            MAIN_ACTIVITY -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }
            SIGN_UP_FRAGMENT -> {
                transaction.replace(R.id.auth_fragment_layout, SignupFragment())
                    .addToBackStack(null)
            }
            LOGIN_FRAGMENT -> {
                supportFragmentManager.popBackStack()
                transaction.replace(R.id.auth_fragment_layout, LoginFragment())
            }
        }
        transaction.commit()
    }

    companion object {
        const val MAIN_ACTIVITY = 1

        const val LOGIN_FRAGMENT = 2
        const val SIGN_UP_FRAGMENT = 3

        const val MALE = 1
        const val FEMALE = 2
        const val NOTHING = -1

        const val channel_id = "ice_creamapp_channel"

        //토큰 전송
        fun uploadToken(token: String) {}

        //fcm 알림을 보내는 코드 작성
        fun fcmBroadcast(title: String, body: String) {}
    }
}
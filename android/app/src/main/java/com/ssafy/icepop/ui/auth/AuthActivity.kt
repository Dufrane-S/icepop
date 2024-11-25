package com.ssafy.icepop.ui.auth

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.request.TokenRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.ActivityAuthBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.smartstore_jetpack.base.BaseActivity
import com.ssafy.smartstore_jetpack.util.PermissionChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "AuthActivity_ssafy"
class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_fragment_layout, LoginFragment())
            .commit()
    }

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
    }
}
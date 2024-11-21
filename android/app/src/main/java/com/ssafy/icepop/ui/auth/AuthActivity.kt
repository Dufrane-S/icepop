package com.ssafy.icepop.ui.auth

import android.content.Intent
import android.os.Bundle
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.ActivityAuthBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.BaseActivity

class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, LoginFragment())
            .commit()
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
                transaction.replace(R.id.fragment_layout, SignupFragment())
                    .addToBackStack(null)
            }
            LOGIN_FRAGMENT -> {
                supportFragmentManager.popBackStack()
                transaction.replace(R.id.fragment_layout, LoginFragment())
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
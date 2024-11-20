package com.ssafy.icepop.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.ssafy.icepop.data.model.dto.User
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.ActivityMainBinding
import com.ssafy.smartstore_jetpack.base.BaseActivity
import kotlinx.coroutines.launch

private const val TAG = "MainActivity_ssafy"
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            kotlin.runCatching {
                RetrofitUtil.userService.login(User("test", "test"))
            }.onSuccess {
                Log.d(TAG, "onCreate: ${it}")
            }.onFailure {
                Log.d(TAG, "실패:")
            }
        }
    }
}
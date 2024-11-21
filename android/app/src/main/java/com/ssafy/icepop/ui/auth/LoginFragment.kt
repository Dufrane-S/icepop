package com.ssafy.icepop.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.ssafy.icepop.R
import com.ssafy.icepop.data.local.SharedPreferencesUtil
import com.ssafy.icepop.data.model.dto.User
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentLoginBinding
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.smartstore_jetpack.base.BaseFragment
import kotlinx.coroutines.launch


private const val TAG = "LoginFragment_ssafy"
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind,
    R.layout.fragment_login
) {
    private lateinit var authActivity: AuthActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        authActivity = context as AuthActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        binding.loginBtn.setOnClickListener {
            val id = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()

            Log.d(TAG, "initEvent: $id $password")

            login(id, password)
        }
    }

    private fun login(id: String, password: String) {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.userService.login(User(id, password))
            }.onSuccess {
                ApplicationClass.sharedPreferencesUtil.addEmail(it)

                authActivity.changeFragment(AuthActivity.MAIN_ACTIVITY)
            }.onFailure {
                Log.d(TAG, "실패")
            }
        }
    }
}
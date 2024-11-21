package com.ssafy.icepop.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.User
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentSignupBinding
import com.ssafy.smartstore_jetpack.base.BaseFragment
import kotlinx.coroutines.launch

private const val TAG = "SignupFragment_ssafy"
class SignupFragment : BaseFragment<FragmentSignupBinding> (
    FragmentSignupBinding::bind,
    R.layout.fragment_signup
) {
    private lateinit var authActivity: AuthActivity

    private var selectGender = 1

    override fun onAttach(context: Context) {
        super.onAttach(context)

        authActivity = context as AuthActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        binding.genderToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                selectGender = when(checkedId) {
                    R.id.male_button -> AuthActivity.MALE
                    R.id.female_button -> AuthActivity.FEMALE
                    else -> AuthActivity.NOTHING
                }

                Log.d(TAG, "initEvent: $selectGender")
            }
        }

        //회원가입 버튼 클릭시 처리
        binding.signupBtn.setOnClickListener {
            val email = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()
            val age = binding.ageTextView.text.toString().toInt()
            val gender = selectGender

            Log.d(TAG, "$email $password $name $age $gender")

            signUp(User(email, password, name, age, gender))
        }
    }

    private fun signUp(user : User) {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.userService.signup(user)
            }.onSuccess {
                authActivity.changeFragment(AuthActivity.LOGIN_FRAGMENT)
            }.onFailure {
                showToast("회원가입에 실패했습니다.")
            }
        }
    }
}
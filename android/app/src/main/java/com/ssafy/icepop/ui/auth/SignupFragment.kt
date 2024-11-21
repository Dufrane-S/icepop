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

    private var isEmailChecked = false
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

            if (isEmailChecked) {
                signUp(User(email, password, name, age, gender))
            }
            //이메일 중복 확인을 누르지 않았을 경우,
            else {
                showToast(getString(R.string.msg_email_check))
            }
        }

        binding.idDuplicateBtn.setOnClickListener {
            val email = binding.etId.text.toString()

            if (email.isEmpty()) {
                showToast(getString(R.string.msg_email_edittext_empty))
            }
            else {
                requestEmailDuplicationCheck(email)
            }
        }
    }

    private fun signUp(user: User) {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.userService.signup(user)
            }.onSuccess {
                authActivity.changeFragment(AuthActivity.LOGIN_FRAGMENT)
            }.onFailure {
                showToast(getString(R.string.msg_signup_fail))
            }
        }
    }

    //false => 사용 중이다, true면 사용 가능하다.
    private fun requestEmailDuplicationCheck(email: String) {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.userService.isUsedEmail(email)
            }.onSuccess {
                handleDuplicateEmail(it)
            }.onFailure {
                showToast(getString(R.string.msg_email_duplication_check_fail))
            }
        }
    }

    private fun handleDuplicateEmail(isUsing: Boolean) {
        if (isUsing) {
            isEmailChecked = true
            showToast(getString(R.string.msg_email_available))
        }
        else {
            showToast(getString(R.string.msg_email_duplicate))
        }
    }
}
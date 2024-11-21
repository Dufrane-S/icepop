package com.ssafy.icepop.ui.auth

import android.os.Bundle
import android.view.View
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.FragmentLoginBinding
import com.ssafy.smartstore_jetpack.base.BaseFragment


class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind,
    R.layout.fragment_login
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
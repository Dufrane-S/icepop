package com.ssafy.icepop.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.FragmentSignupBinding
import com.ssafy.smartstore_jetpack.base.BaseFragment
import okhttp3.internal.notify

private const val TAG = "SignupFragment_ssafy"
class SignupFragment : BaseFragment<FragmentSignupBinding> (
    FragmentSignupBinding::bind,
    R.layout.fragment_signup
) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
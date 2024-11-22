package com.ssafy.icepop.ui.my


import android.content.Context
import android.os.Bundle
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.FragmentMyPageBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.BaseFragment

class MyPageFragment : BaseFragment<FragmentMyPageBinding> (
    FragmentMyPageBinding::bind,
    R.layout.fragment_my_page
){
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity.hideBottomNav(false)
    }
}
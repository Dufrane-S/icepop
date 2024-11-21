package com.ssafy.icepop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.FragmentHomeBinding
import com.ssafy.smartstore_jetpack.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding> (
    FragmentHomeBinding::bind,
    R.layout.fragment_home
) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}
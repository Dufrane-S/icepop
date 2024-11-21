package com.ssafy.icepop.ui.list

import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import com.google.android.material.tabs.TabLayout
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.FragmentIceCreamListBinding
import com.ssafy.smartstore_jetpack.base.BaseFragment

class IceCreamListFragment : BaseFragment<FragmentIceCreamListBinding> (
    FragmentIceCreamListBinding::bind,
    R.layout.fragment_ice_cream_list
){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        binding.tabLayout.addOnTabSelectedListener (object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // 탭이 선택될 때 실행할 코드
                when (tab.position) {
                    TYPE -> {
                        binding.optionArea.visibility = View.VISIBLE
                    }
                    POPULARITY -> {
                        binding.optionArea.visibility = View.VISIBLE
                    }
                    AI_RECOMMEND -> {
                        binding.optionArea.visibility = View.GONE
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    companion object {
        const val TYPE = 0
        const val POPULARITY = 1
        const val AI_RECOMMEND = 2
    }
}
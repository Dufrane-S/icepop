package com.ssafy.icepop.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.IceCream
import com.ssafy.icepop.data.model.dto.request.IceCreamRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentIceCreamListBinding
import com.ssafy.smartstore_jetpack.base.BaseFragment
import kotlinx.coroutines.launch

private const val TAG = "IceCreamListFragment_ssafy"
class IceCreamListFragment : BaseFragment<FragmentIceCreamListBinding> (
    FragmentIceCreamListBinding::bind,
    R.layout.fragment_ice_cream_list
){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()

        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.iceCreamService.getAllIceCream(IceCreamRequest())
            }.onSuccess {
                setIceCreamAdapter(it)
            }.onFailure {
                Log.d(TAG, "실패")
            }
        }
    }

    private fun initListener() {
        binding.tabLayout.addOnTabSelectedListener (object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // 탭이 선택될 때 실행할 코드 (view 처리와, 서버 처리 로직을 분리)
                //handleOptionAreaByTabPosition
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

    private fun setIceCreamAdapter(iceCreamList : List<IceCream>) {
        val iceCreamAdapter = IceCreamAdapter(iceCreamList)

        binding.iceCreamRv.apply {
            adapter = iceCreamAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        const val TYPE = 0
        const val POPULARITY = 1
        const val AI_RECOMMEND = 2
    }
}
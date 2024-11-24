package com.ssafy.icepop.ui.my


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.IceCreamOrder
import com.ssafy.icepop.data.model.dto.Member
import com.ssafy.icepop.data.model.dto.request.OrderRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentMyPageBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.smartstore_jetpack.base.ApplicationClass.Companion.ICE_CREAM_IMAGE_BASE_URL
import com.ssafy.smartstore_jetpack.base.ApplicationClass.Companion.USER_IMAGE_BASE_URL
import com.ssafy.smartstore_jetpack.base.BaseFragment
import com.ssafy.smartstore_jetpack.util.CommonUtils
import kotlinx.coroutines.launch

private const val TAG = "MyPageFragment_ssafy"
class MyPageFragment : BaseFragment<FragmentMyPageBinding> (
    FragmentMyPageBinding::bind,
    R.layout.fragment_my_page
){
    private lateinit var mainActivity: MainActivity

    private lateinit var recentOrderAdapter: RecentOrderAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initEvent()
        getMyInfo()
        getRecentOrder()
    }

    override fun onResume() {
        super.onResume()

        mainActivity.hideBottomNav(false)
    }

    private fun initAdapter() {
        recentOrderAdapter = RecentOrderAdapter(mutableListOf()) {
            mainActivity.openFragment(MainActivity.ORDER_DETAIL_FRAGMENT, it.id)
        }

        binding.recentOrderListRv.apply {
            adapter = recentOrderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    private fun initEvent() {
        binding.orderListArea.setOnClickListener {
            mainActivity.openFragment(MainActivity.ORDER_LIST_FRAGMENT)
        }
    }

    private fun getMyInfo() {
        Log.d(TAG, "getMyInfo: ")

        val email = ApplicationClass.sharedPreferencesUtil.getUser().email

        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.userService.getMyInfo(email)
            }.onSuccess {
                initMemberView(it)
            }.onFailure {
                Log.d(TAG, "실패")
            }
        }
    }

    private fun getRecentOrder() {
        val email = ApplicationClass.sharedPreferencesUtil.getUser().email

        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.orderService.getOrderList(OrderRequest(email = email, recent = true))
            }.onSuccess {
                setNotifyRecentOrderView(it)
            }.onFailure {
                Log.d(TAG, "getRecentOrder: 실패")
            }
        }
    }

    private fun setNotifyRecentOrderView(iceCreamOrder: List<IceCreamOrder>) {
        recentOrderAdapter.iceCreamOrderList = iceCreamOrder
        recentOrderAdapter.notifyDataSetChanged()
    }


    private fun initMemberView(member: Member) {
        binding.myPageName.text = member.name
        binding.myPageAgeGenderTv.text = "${member.age}세(${CommonUtils.getGenderByNumber(member.gender)})"
        binding.myPageEmail.text = member.email

        binding.levelTv.text = "${member.level} 단계"
        binding.nextLevelTv.text =
            "${CommonUtils.getNextUserLevel(member.level)}: + ${CommonUtils.makeComma(member.nextLvRemain)}"

        val imageUrl = USER_IMAGE_BASE_URL + member.img

        Glide.with(binding.root).load(imageUrl).into(binding.levelImage)

        binding.gradeProgressBar.progress = (50000 - member.nextLvRemain)
    }
}
package com.ssafy.icepop.ui.my


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.IceCreamOrder
import com.ssafy.icepop.data.model.dto.Member
import com.ssafy.icepop.data.model.dto.request.OrderRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentMyPageBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.ApplicationClass
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity.hideBottomNav(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        getMyInfo()
        getRecentOrder()
    }

    private fun initAdapter() {
        recentOrderAdapter = RecentOrderAdapter(mutableListOf()) {

        }

        binding.recentOrderListRv.apply {
            adapter = recentOrderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
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
    }
}
package com.ssafy.icepop.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.request.ReviewListRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentHomeBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.smartstore_jetpack.base.BaseFragment
import com.ssafy.smartstore_jetpack.util.CommonUtils
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment_ssafy"
class HomeFragment : BaseFragment<FragmentHomeBinding> (
    FragmentHomeBinding::bind,
    R.layout.fragment_home
) {
    private lateinit var mainActivity: MainActivity

    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var orderReviewAdapter: OrderReviewAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNotificationAdapter()
        initOrderReviewAdapter()

        getNotificationData()
        getReviews()
    }

    override fun onResume() {
        super.onResume()

        mainActivity.hideBottomNav(false)
    }

    private fun initNotificationAdapter() {
        notificationAdapter = NotificationAdapter(mutableListOf())

        binding.notificationRv.apply {
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun initOrderReviewAdapter() {
        orderReviewAdapter = OrderReviewAdapter(mutableListOf())

        binding.reviewRv.apply {
            adapter = orderReviewAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    private fun getNotificationData() {
        val email = ApplicationClass.sharedPreferencesUtil.getUser().email

        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.notificationService.getNotificationItems(email)
            }.onSuccess {
                notificationAdapter.notificationItems = it
                notificationAdapter.notifyDataSetChanged()
            }.onFailure {
                Log.d(TAG, "getNotificationData: 알림 데이터 가져오기 실패")
            }
        }
    }

    private fun getReviews() {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.reviewService.getReviewList(ReviewListRequest(""))
            }.onSuccess {
                orderReviewAdapter.reviews = it
                orderReviewAdapter.notifyDataSetChanged()
            }.onFailure {
                Log.d(TAG, "getNotificationData: 알림 데이터 가져오기 실패")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}
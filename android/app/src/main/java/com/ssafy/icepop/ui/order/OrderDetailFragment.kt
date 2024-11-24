package com.ssafy.icepop.ui.order

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.IceCreamOrder
import com.ssafy.icepop.data.model.dto.IceCreamOrderDetail
import com.ssafy.icepop.data.model.dto.Member
import com.ssafy.icepop.data.model.dto.request.OrderRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentOrderDetailBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.smartstore_jetpack.base.BaseFragment
import com.ssafy.smartstore_jetpack.util.CommonUtils
import kotlinx.coroutines.launch

private const val TAG = "OrderDetailFragment_ssafy"
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding>(
    FragmentOrderDetailBinding::bind,
    R.layout.fragment_order_detail
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var orderDetailListAdapter: OrderDetailListAdapter

    var orderId : Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity.hideBottomNav(true)

        orderId = arguments?.getInt(ARG_ID) ?: -1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()


        if (orderId != -1) {
            initEvent()
            getMyInfo()
            getOrderDetailById(orderId)
        }
    }

    private fun initAdapter() {
        orderDetailListAdapter = OrderDetailListAdapter(mutableListOf())

        binding.orderIceCreamListRv.apply {
            adapter = orderDetailListAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        CommonUtils.setVerticalDivider(binding.orderIceCreamListRv.context, binding.orderIceCreamListRv)
    }

    private fun initEvent() {
        binding.reviewBtn.setOnClickListener {
            mainActivity.openFragment(MainActivity.ORDER_REVIEW_FRAGMENT, orderId)
        }
    }

    private fun getOrderDetailById(id: Int) {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.orderService.getOrderList(OrderRequest(orderId = id))
            }.onSuccess {
                setNotifyOrderIceCreamDetail(it[0].details)
                setViewByIceCreamOrder(it[0])
                Log.d(TAG, "getOrderDetailById: ${it.get(0)}")
            }.onFailure {
                Log.d(TAG, "getRecentOrder: 실패")
            }
        }
    }

    private fun setNotifyOrderIceCreamDetail(details : List<IceCreamOrderDetail>) {
        orderDetailListAdapter.iceCreamOrderDetails = details
        orderDetailListAdapter.notifyDataSetChanged()
    }

    private fun setViewByIceCreamOrder(iceCreamOrder: IceCreamOrder) {
        if (iceCreamOrder.isforhere == CommonUtils.MARKET) {
            binding.orderDetailTypeTv.text = "매장"
            binding.spoonCount.text = "${iceCreamOrder.spoon}개"
            binding.iceArea.visibility = View.GONE
        }
        else {
            binding.orderDetailTypeTv.text = "포장"
            binding.spoonCount.text = "${iceCreamOrder.spoon}개"
            binding.iceMinute.text = "${iceCreamOrder.dryice}분"
        }

        binding.orderPriceTv.text = "주문 금액 : ${CommonUtils.makeComma(iceCreamOrder.priceSum)}"
        binding.discountPriceTv.text = "할인 금액 : ${CommonUtils.makeComma(iceCreamOrder.discountSum)}"
        binding.totalPriceTv.text = "최종 금액 : ${CommonUtils.makeComma(iceCreamOrder.resultSum)}"
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

    private fun initMemberView(member: Member) {
        val imageUrl = ApplicationClass.USER_IMAGE_BASE_URL + member.img
        Glide.with(binding.root).load(imageUrl).into(binding.userImage)

        binding.gradeTv.text = "${member.level} (${100 - (member.discountRate * 100).toInt()}%)할인"
    }

    companion object {
        const val ARG_ID = "id"

        @JvmStatic
        fun newInstance(id: Int): OrderDetailFragment {
            return OrderDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                }
            }
        }
    }
}
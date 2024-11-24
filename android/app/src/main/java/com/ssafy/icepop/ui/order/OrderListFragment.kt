package com.ssafy.icepop.ui.order

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.IceCreamOrder
import com.ssafy.icepop.data.model.dto.request.OrderRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentOrderListBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.smartstore_jetpack.base.BaseFragment
import com.ssafy.smartstore_jetpack.util.CommonUtils
import kotlinx.coroutines.launch

private const val TAG = "OrderListFragment_ssafy"
class OrderListFragment : BaseFragment<FragmentOrderListBinding>(
    FragmentOrderListBinding::bind,
    R.layout.fragment_order_list
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var orderListAdapter: OrderListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity.hideBottomNav(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        getAllOrder()
    }

    private fun initAdapter() {
        orderListAdapter = OrderListAdapter(mutableListOf()) {
            mainActivity.openFragment(MainActivity.ORDER_DETAIL_FRAGMENT, it.id)
        }

        binding.orderListRv.apply {
            adapter = orderListAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        CommonUtils.setVerticalDivider(binding.orderListRv.context, binding.orderListRv)
    }

    private fun getAllOrder() {
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

    private fun setNotifyRecentOrderView(iceCreamOrderList: List<IceCreamOrder>) {
        orderListAdapter.iceCreamOrderList = iceCreamOrderList
        orderListAdapter.notifyDataSetChanged()
    }
}
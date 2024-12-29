package com.ssafy.icepop.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.IceCreamCartItem
import com.ssafy.icepop.data.model.dto.Member
import com.ssafy.icepop.data.model.dto.request.IceCreamOrderRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentIceCreamOrderBinding
import com.ssafy.icepop.ui.ActivityViewModel
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.smartstore_jetpack.base.BaseFragment
import com.ssafy.smartstore_jetpack.util.CommonUtils
import kotlinx.coroutines.launch
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.Payload


private const val TAG = "IceCreamOrderFragment_ssafy"
class IceCreamOrderFragment : BaseFragment<FragmentIceCreamOrderBinding>(
    FragmentIceCreamOrderBinding::bind,
    R.layout.fragment_ice_cream_order
) {
    private lateinit var iceCreamCartAdapter: IceCreamCartAdapter
    private lateinit var mainActivity: MainActivity

    private val activityViewModel: ActivityViewModel by activityViewModels()

    private var spoonCount = 0
    private var iceCount = 0

    private val spoonUnit = 1
    private val iceUnit = 15

    private var selectOrderType = MARKET

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

        initView()
        initAdapter()
        initEvent()
        registerObserver()

        //내 정보 가져오기
        getMyInfo()
    }

    private fun initView() {
        binding.spoonCount.text = spoonCount.toString()
        binding.iceCount.text = iceCount.toString()
    }

    private fun initAdapter() {
        iceCreamCartAdapter = IceCreamCartAdapter(mutableListOf())

        iceCreamCartAdapter.iceCreamClickListener = object : IceCreamCartAdapter.IceCreamClickListener {
            override fun plusClick(iceCreamCartItem: IceCreamCartItem) {
                iceCreamItemPlusClick(iceCreamCartItem)
            }

            override fun minusClick(iceCreamCartItem: IceCreamCartItem) {
                iceCreamItemMinusClick(iceCreamCartItem)
            }
        }
        binding.iceCreamCartRv.apply {
            adapter = iceCreamCartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        CommonUtils.setVerticalDivider(binding.iceCreamCartRv.context, binding.iceCreamCartRv)
    }

    private fun initEvent() {
        binding.spoonPlusBtn.setOnClickListener {
            spoonCount += spoonUnit

            binding.spoonCount.text = spoonCount.toString()
        }

        binding.spoonMinusBtn.setOnClickListener {
            if (spoonCount != 0) spoonCount -= spoonUnit

            binding.spoonCount.text = spoonCount.toString()
        }

        binding.icePlusBtn.setOnClickListener {
            iceCount += iceUnit

            binding.iceCount.text = iceCount.toString()
        }

        binding.iceMinusBtn.setOnClickListener {
            if (iceCount != 0) iceCount -= iceUnit

            binding.iceCount.text = iceCount.toString()
        }

        binding.orderTypeToggleGroup.addOnButtonCheckedListener {_, checkedId, isChecked ->
            if (isChecked) {
                selectOrderType = when(checkedId) {
                    R.id.market_button -> MARKET
                    R.id.take_out_button -> TAKE_OUT
                    else -> TYPE_NOTHING
                }

                when (selectOrderType) {
                    MARKET -> binding.iceInfoArea.visibility = View.GONE
                    TAKE_OUT -> binding.iceInfoArea.visibility = View.VISIBLE
                }
            }
        }

        binding.orderBtn.setOnClickListener {
            val email = ApplicationClass.sharedPreferencesUtil.getUser().email
            val details = activityViewModel.cartItems.value?.values?.toList()!!

            Log.d(TAG, "initEvent: $details")
            Log.d(TAG, "initEvent: ${details.size}")

            val iceCreamOrderRequest = IceCreamOrderRequest(
                details = details,
                dryice = iceCount,
                email = email,
                spoon = spoonCount,
                isForHere = selectOrderType,
                priceSum = activityViewModel.totalPrice.value!!,
                discountSum = activityViewModel.discountAmount.value!!,
                resultSum = activityViewModel.finalPrice.value!!
            )

            val orderName = if (details.size == 1) {
                details[0].name
            }
            else {
                "${details[0].name} 외 ${details.size - 1}종"
            }

            val payload = Payload()
            payload.setApplicationId(ApplicationClass.APPLICATION_ID)
                .setOrderName(orderName)
                .setOrderId("1234")
                .setPrice(activityViewModel.finalPrice.value!!.toDouble())

            val map: MutableMap<String, Any> = HashMap()
            map["1"] = "abcdef"
            map["2"] = "abcdef55"
            map["3"] = 1234
            payload.setMetadata(map)

            /*Bootpay.init(parentFragmentManager)
                .setPayload(payload)
                .setEventListener(object : BootpayEventListener {
                    override fun onCancel(data: String) {}
                    override fun onError(data: String) {}
                    override fun onClose() { Bootpay.dismiss() }
                    override fun onIssued(data: String) {}
                    override fun onConfirm(data: String): Boolean { return true }
                    override fun onDone(data: String) { makeOrder(iceCreamOrderRequest) }
                }).requestPayment()*/

            //주문하기
            makeOrder(iceCreamOrderRequest)
        }
    }

    private fun makeOrder(iceCreamOrderRequest: IceCreamOrderRequest) {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.orderService.makeOrder(iceCreamOrderRequest)
            }.onSuccess {
                if (it.isSuccessful) {
                    mainActivity.openFragment(MainActivity.ICE_CREAM_LIST_FRAGMENT)

                    activityViewModel.resetCart()

                    Log.d(TAG, "makeOrder: 성공")
                }
            }.onFailure {
                Log.e(TAG, "makeOrder: 실패, 예외 메시지: ${it.message}", it)
            }
        }
    }

    private fun registerObserver() {
        Log.d(TAG, "registerObserver: ")
        
        activityViewModel.cartItems.observe(viewLifecycleOwner) {
            if (it.values.toList().isEmpty()) {
                mainActivity.openFragment(MainActivity.ICE_CREAM_LIST_FRAGMENT)
            }
            else {
                iceCreamCartAdapter.cartItems = it.values.toList()
                iceCreamCartAdapter.notifyDataSetChanged()
            }
        }

        activityViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.orderPriceTv.text = "주문 금액: ${CommonUtils.makeComma(it)}"
        }

        activityViewModel.discountAmount.observe(viewLifecycleOwner) { discountAmount ->
            binding.discountPriceTv.text = "할인 금액: ${CommonUtils.makeComma(discountAmount)}"
        }

        activityViewModel.finalPrice.observe(viewLifecycleOwner) { finalPrice ->
            binding.totalPriceTv.text = "최종 금액: ${CommonUtils.makeComma(finalPrice)}"
        }
    }

    private fun iceCreamItemPlusClick(iceCreamCartItem: IceCreamCartItem) {
        val cartItem = iceCreamCartItem.copy(quantity = 1)

        activityViewModel.addToCart(cartItem)
    }

    private fun iceCreamItemMinusClick(iceCreamCartItem: IceCreamCartItem) {
        activityViewModel.removeFromCart(iceCreamCartItem.productId)
    }

    private fun getMyInfo() {
        Log.d(TAG, "getMyInfo: ")

        val email = ApplicationClass.sharedPreferencesUtil.getUser().email

        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.userService.getMyInfo(email)
            }.onSuccess {
                activityViewModel.setDiscountRate(it.discountRate)

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
        private const val MARKET = 1
        private const val TAKE_OUT = 2
        private const val TYPE_NOTHING = -1
    }
}
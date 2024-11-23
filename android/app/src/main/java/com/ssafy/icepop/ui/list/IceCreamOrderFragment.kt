package com.ssafy.icepop.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.icepop.R
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
        initEvent()
        registerObserver()

        //내 정보 가져오기
        getMyInfo()
    }

    private fun initView() {
        binding.spoonCount.text = spoonCount.toString()
        binding.iceCount.text = iceCount.toString()
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

            val iceCreamOrderRequest = IceCreamOrderRequest(
                details = details,
                dryice = iceCount,
                email = email,
                spoon = spoonCount,
                isForHere = selectOrderType,
            )

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
            iceCreamCartAdapter = IceCreamCartAdapter(it.values.toList())
            binding.iceCreamCartRv.adapter = iceCreamCartAdapter

            binding.iceCreamCartRv.layoutManager = LinearLayoutManager(requireContext())

            CommonUtils.setVerticalDivider(binding.iceCreamCartRv.context, binding.iceCreamCartRv)
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

        100 - (member.discountRate * 100).toInt()

        binding.gradeTv.text = "${member.level} (${100 - (member.discountRate * 100).toInt()}%)할인"
    }

    companion object {
        private const val MARKET = 1
        private const val TAKE_OUT = 2
        private const val TYPE_NOTHING = -1

//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            IceCreamOrderFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}
package com.ssafy.icepop.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.IceCream
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentIceCreamDetailBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.ApplicationClass.Companion.ICE_CREAM_IMAGE_BASE_URL
import com.ssafy.smartstore_jetpack.base.BaseFragment
import com.ssafy.smartstore_jetpack.util.CommonUtils
import kotlinx.coroutines.launch

private const val TAG = "IceCreamDetailFragment_ssafy"
class IceCreamDetailFragment : BaseFragment<FragmentIceCreamDetailBinding> (
    FragmentIceCreamDetailBinding::bind,
    R.layout.fragment_ice_cream_detail
){
    private lateinit var mainActivity: MainActivity

    var iceCreamId : Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity.hideBottomNav(true)

        iceCreamId = arguments?.getInt(ARG_ID) ?: -1

        Log.d(TAG, "onCreate: $id")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //식별자가 잘 들어왔다면, 아이스크림 정보 조회
        if (iceCreamId != -1) {
            getDetailIceCreamData(iceCreamId)
        }
    }

    private fun getDetailIceCreamData(id: Int) {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.iceCreamService.getIceCreamById(id)
            }.onSuccess {
                setViewByIceCreamData(it)
                Log.d(TAG, "getDetailIceCreamData: $it")
            }.onFailure {
                Log.d(TAG, "getDetailIceCreamData: 실패")
            }
        }
    }

    private fun setViewByIceCreamData(iceCream: IceCream) {
        val imageUrl = ICE_CREAM_IMAGE_BASE_URL + iceCream.img
        Glide.with(binding.root).load(imageUrl).into(binding.iceCreamImage)

        binding.iceCreamName.text = iceCream.name
        binding.iceCreamContent.text = iceCream.content
        binding.iceCreamPrice.text = CommonUtils.makeComma(iceCream.price)
        binding.iceCreamKcal.text = CommonUtils.makeKcal(iceCream.kcal)
        binding.iceCreamType.text = iceCream.type
        binding.iceCreamOrderCount.text = "1"

        binding.iceCreamOrderPrice.text = "주문금액 ${CommonUtils.makeComma(iceCream.price)}"
    }

    companion object {
        const val ARG_ID = "id"

        @JvmStatic
        fun newInstance(id: Int): IceCreamDetailFragment {
            return IceCreamDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                }
            }
        }
    }
}
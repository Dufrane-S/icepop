package com.ssafy.icepop.ui.list

import android.os.Bundle
import android.util.Log
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.FragmentIceCreamDetailBinding
import com.ssafy.smartstore_jetpack.base.BaseFragment

private const val TAG = "IceCreamDetailFragment_ssafy"
class IceCreamDetailFragment : BaseFragment<FragmentIceCreamDetailBinding> (
    FragmentIceCreamDetailBinding::bind,
    R.layout.fragment_ice_cream_detail
){
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val id = arguments?.getInt(ARG_ID)
        // 데이터를 활용해 필요한 로직 수행

        Log.d(TAG, "onCreate: $id")
    }
}
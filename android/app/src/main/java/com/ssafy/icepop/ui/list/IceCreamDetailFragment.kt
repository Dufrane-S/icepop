package com.ssafy.icepop.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.FragmentIceCreamDetailBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.BaseFragment

private const val TAG = "IceCreamDetailFragment_ssafy"
class IceCreamDetailFragment : BaseFragment<FragmentIceCreamDetailBinding> (
    FragmentIceCreamDetailBinding::bind,
    R.layout.fragment_ice_cream_detail
){
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity.hideBottomNav(true)


        val id = arguments?.getInt(ARG_ID)
        // 데이터를 활용해 필요한 로직 수행

        Log.d(TAG, "onCreate: $id")
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
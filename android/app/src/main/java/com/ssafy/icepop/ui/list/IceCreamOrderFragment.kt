package com.ssafy.icepop.ui.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.FragmentIceCreamOrderBinding
import com.ssafy.icepop.ui.ActivityViewModel
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.BaseFragment
import com.ssafy.smartstore_jetpack.util.CommonUtils


private const val TAG = "IceCreamOrderFragment_ssafy"
class IceCreamOrderFragment : BaseFragment<FragmentIceCreamOrderBinding>(
    FragmentIceCreamOrderBinding::bind,
    R.layout.fragment_ice_cream_order
) {
    private lateinit var iceCreamCartAdapter: IceCreamCartAdapter
    private lateinit var mainActivity: MainActivity

    private val activityViewModel: ActivityViewModel by activityViewModels()

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


        //여기에서 이제, 할인 정보를 가져와야함(sharedPreferecne의 email에서 조회)

        activityViewModel.cartItems.observe(viewLifecycleOwner) {
            iceCreamCartAdapter = IceCreamCartAdapter(it.values.toList())
            binding.iceCreamCartRv.adapter = iceCreamCartAdapter

            binding.iceCreamCartRv.layoutManager = LinearLayoutManager(requireContext())

            CommonUtils.setVerticalDivider(binding.iceCreamCartRv.context, binding.iceCreamCartRv)
        }
    }

    companion object {
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
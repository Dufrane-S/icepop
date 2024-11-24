package com.ssafy.icepop.ui.order

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.request.ReviewPostRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentOrderReviewBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.smartstore_jetpack.base.BaseFragment
import kotlinx.coroutines.launch

private const val TAG = "OrderReviewFragment_ssafy"
class OrderReviewFragment : BaseFragment<FragmentOrderReviewBinding>(
    FragmentOrderReviewBinding::bind,
    R.layout.fragment_order_review
) {
    private lateinit var mainActivity: MainActivity

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

        initView()
        initEvent()

        Log.d(TAG, "onViewCreated: $orderId")
    }

    private fun initView() {
        binding.reviewEt.setHorizontallyScrolling(false)
        binding.reviewEt.isVerticalScrollBarEnabled = true
        binding.reviewEt.movementMethod = ScrollingMovementMethod()
    }

    private fun initEvent() {
        binding.reviewBtn.setOnClickListener {
            postReview()
        }
    }

    private fun postReview() {
        val email = ApplicationClass.sharedPreferencesUtil.getUser().email
        val rating = binding.ratingbar.rating.toDouble()
        val content = binding.reviewEt.text.toString()

        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.reviewService.postReview(
                    ReviewPostRequest(
                        content = content,
                        email = email,
                        rate = rating,
                        orderId = orderId
                    )
                )
            }.onSuccess {
                showToast("작성 되었습니다.")
                mainActivity.openFragment(MainActivity.HOME_FRAGMENT)
            }.onFailure {
                Log.d(TAG, "postReview: 실패")
            }
        }
    }

    companion object {
        const val ARG_ID = "id"

        @JvmStatic
        fun newInstance(id: Int): OrderReviewFragment {
            return OrderReviewFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                }
            }
        }
    }
}
package com.ssafy.icepop.ui.my


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.IceCreamOrder
import com.ssafy.icepop.data.model.dto.Member
import com.ssafy.icepop.data.model.dto.request.OrderRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentMyPageBinding
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.smartstore_jetpack.base.ApplicationClass.Companion.ICE_CREAM_IMAGE_BASE_URL
import com.ssafy.smartstore_jetpack.base.ApplicationClass.Companion.USER_IMAGE_BASE_URL
import com.ssafy.smartstore_jetpack.base.BaseFragment
import com.ssafy.smartstore_jetpack.util.CommonUtils
import kotlinx.coroutines.launch

private const val TAG = "MyPageFragment_ssafy"
class MyPageFragment : BaseFragment<FragmentMyPageBinding> (
    FragmentMyPageBinding::bind,
    R.layout.fragment_my_page
){
    private lateinit var mainActivity: MainActivity

    private lateinit var recentOrderAdapter: RecentOrderAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initEvent()
        getMyInfo()
        getRecentOrder()
    }

    override fun onResume() {
        super.onResume()

        mainActivity.hideBottomNav(false)
    }

    private fun initAdapter() {
        recentOrderAdapter = RecentOrderAdapter(mutableListOf()) {
            mainActivity.openFragment(MainActivity.ORDER_DETAIL_FRAGMENT, it.id)
        }

        binding.recentOrderListRv.apply {
            adapter = recentOrderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    private fun initEvent() {
        binding.orderListArea.setOnClickListener {
            mainActivity.openFragment(MainActivity.ORDER_LIST_FRAGMENT)
        }
        binding.logoutBtn.setOnClickListener {
            ApplicationClass.sharedPreferencesUtil.deleteUser()
            showToast("로그아웃 되었습니다.")
            mainActivity.openFragment(MainActivity.AUTH_ACTIVITY)
        }

        binding.levelTableTv.setOnClickListener {
            showLevelDialog()
        }
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

    private fun getRecentOrder() {
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

    private fun showLevelDialog() {


        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_level, null)

        val builder = AlertDialog.Builder(requireContext(), R.style.AppAlertDialogTheme)
            .setView(view)
            .setTitle("사용자 등급관리표")
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
            }

        // AlertDialog 생성 및 표시
        val dialog = builder.create()

        // 다이얼로그를 표시하기 전에 크기 조정
        dialog.setOnShowListener {
            val window = dialog.window
            window?.setLayout(800, 850)
        }

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        dialog.show()

        val title = dialog.findViewById<TextView>(android.R.id.title)
        val textView = dialog.findViewById<TextView>(android.R.id.message)
        val face = ResourcesCompat.getFont(requireContext(), R.font.meet_me)
        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

        val customFont = ResourcesCompat.getFont(requireContext(), R.font.meet_me)  // meet_me는 custom font 파일

        positiveButton.setTextColor(getColor(requireContext().resources, R.color.primary_color, null))
        positiveButton.typeface = customFont

        textView?.typeface = face

        title?.textSize = 24f
        textView?.textSize = 18f
    }

    private fun setNotifyRecentOrderView(iceCreamOrder: List<IceCreamOrder>) {
        recentOrderAdapter.iceCreamOrderList = iceCreamOrder
        recentOrderAdapter.notifyDataSetChanged()
    }


    private fun initMemberView(member: Member) {
        binding.myPageName.text = member.name
        binding.myPageAgeGenderTv.text = "${member.age}세(${CommonUtils.getGenderByNumber(member.gender)})"
        binding.myPageEmail.text = member.email

        binding.levelTv.text = "${member.level} 단계"
        binding.nextLevelTv.text =
            "${CommonUtils.getNextUserLevel(member.level)}: + ${CommonUtils.makeComma(member.nextLvRemain)}"

        val imageUrl = USER_IMAGE_BASE_URL + member.img

        Glide.with(binding.root).load(imageUrl).into(binding.levelImage)

        binding.gradeProgressBar.progress = (50000 - member.nextLvRemain)
    }
}
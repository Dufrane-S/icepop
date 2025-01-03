package com.ssafy.icepop.ui.list

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.IceCream
import com.ssafy.icepop.data.model.dto.request.IceCreamRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.FragmentIceCreamListBinding
import com.ssafy.icepop.ui.ActivityViewModel
import com.ssafy.icepop.ui.MainActivity
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.smartstore_jetpack.base.BaseFragment
import com.ssafy.smartstore_jetpack.util.CommonUtils
import kotlinx.coroutines.launch

private const val TAG = "IceCreamListFragment_ssafy"
class IceCreamListFragment : BaseFragment<FragmentIceCreamListBinding> (
    FragmentIceCreamListBinding::bind,
    R.layout.fragment_ice_cream_list
){
    private lateinit var mainActivity: MainActivity
    private lateinit var iceCreamAdapter : IceCreamAdapter

    private val activityViewModel: ActivityViewModel by activityViewModels()

    private val iceCreamAllList = mutableListOf<IceCream>()
    private val iceCreamListByPopularity = mutableListOf<IceCream>()
    private val iceCreamListByAI = mutableListOf<IceCream>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onResume() {
        super.onResume()

        mainActivity.hideBottomNav(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()

        initAdapter()
        initListener()
        getAllIceCream()
    }

    private fun registerObserver() {
        activityViewModel.isCartEmpty.observe(viewLifecycleOwner) { isCartEmpty ->
            binding.iceCreamShoppingCartFab.isEnabled = !isCartEmpty
            binding.iceCreamShoppingCartFab.alpha = if (isCartEmpty) 0.5f else 1f
        }
    }

    private fun showIndicator() {
        binding.progressIndicator.visibility = View.VISIBLE
        binding.progressIndicator.isIndeterminate = true
    }

    private fun hideIndicator() {
        binding.progressIndicator.visibility = View.GONE
    }

    private fun getAllIceCream() {
        showIndicator()
        if (iceCreamAllList.isEmpty()) {
            lifecycleScope.launch {
                runCatching {
                    RetrofitUtil.iceCreamService.getAllIceCream(IceCreamRequest())
                }.onSuccess {
                    iceCreamAllList.addAll(it)
                    setNotifyRecyclerView(it)
                }.onFailure {
                    Log.d(TAG, "실패")
                }
            }
        }
        else {
            setNotifyRecyclerView(iceCreamAllList)
        }
        hideIndicator()
    }

    private fun getIceCreamByPopularity() {
        val user = ApplicationClass.sharedPreferencesUtil.getUser()

        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.iceCreamService.getAllIceCream(IceCreamRequest(age = user.age, gender = user.gender))
            }.onSuccess {
                setNotifyRecyclerView(it)
                iceCreamListByPopularity.addAll(it)
            }.onFailure {
                Log.d(TAG, "실패")
            }
        }
    }

    private fun getIceCreamByAI() {
        showIndicator()

        val user = ApplicationClass.sharedPreferencesUtil.getUser()

        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.iceCreamService.getIceCreamByAI(user.email)
            }.onSuccess {
                setNotifyRecyclerView(iceCreamListByAI)

                iceCreamListByAI.addAll(it)

                hideIndicator()
            }.onFailure {
                Log.d(TAG, "실패")
            }
        }
    }

    private fun initAdapter() {
        iceCreamAdapter = IceCreamAdapter(mutableListOf()) {
            mainActivity.openFragment(MainActivity.ICE_CREAM_DETAIL_FRAGMENT, it.id)
        }

        binding.iceCreamRv.apply {
            adapter = iceCreamAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        CommonUtils.setVerticalDivider(binding.iceCreamRv.context, binding.iceCreamRv)
    }

    private fun initListener() {
        binding.filterButton.setOnClickListener {
            showTypePickerDialog()
        }

        binding.iceCreamShoppingCartFab.setOnClickListener {
            mainActivity.openFragment(MainActivity.ICE_CREAM_ORDER_FRAGMENT)
        }

        binding.tabLayout.addOnTabSelectedListener (object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // 탭이 선택될 때 실행할 코드 (view 처리와, 서버 처리 로직을 분리)
                //handleOptionAreaByTabPosition
                when (tab.position) {
                    TYPE -> {
                        binding.optionArea.visibility = View.VISIBLE
                        getAllIceCream()

                        binding.filterButton.setOnClickListener {
                            showTypePickerDialog()
                        }
                    }
                    POPULARITY -> {
                        binding.optionArea.visibility = View.VISIBLE
                        getIceCreamByPopularity()

                        binding.filterButton.setOnClickListener {
                            showPersonInfoPickerDialog()
                        }
                    }
                    AI_RECOMMEND -> {
                        binding.optionArea.visibility = View.GONE
                        getIceCreamByAI()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun showTypePickerDialog() {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_type_picker, null)

        val types = resources.getStringArray(R.array.type_array)

        val numberPicker = view.findViewById<NumberPicker>(R.id.numberPicker).apply {
            minValue = 0
            maxValue = types.size - 1
            value = 0
            displayedValues = types
        }

        // API 29 이상에서만 setTextColor 사용
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            numberPicker.textColor = Color.BLACK
            numberPicker.textSize = 50F
        } else {
            val pickerChildCount = numberPicker.childCount

            for (i in 0 until pickerChildCount) {
                val child = numberPicker.getChildAt(i)

                if (child is TextView) {
                    child.setTextColor(Color.BLACK)
                    child.textSize = 50F
                }
            }
        }

        // AlertDialog 생성
        val dialog = AlertDialog.Builder(requireContext(), R.style.AppAlertDialogTheme)
            .setView(view)
            .setTitle("타입을 선택하세요")
            .setPositiveButton("OK") { _, _ ->
                // OK 버튼 클릭 시 동작
                val selectedItem = numberPicker.value
                var type = numberPicker.displayedValues[selectedItem]

                if (type == getString(R.string.init)) type = ""

                getIceCreamByType(type)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        // 다이얼로그 표시
        dialog.show()
    }

    private fun showPersonInfoPickerDialog() {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_person_info_picker, null)

        val genders = resources.getStringArray(R.array.gender_items)
        val ages = resources.getStringArray(R.array.age_items)

        val agePicker = view.findViewById<NumberPicker>(R.id.agePicker).apply {
            minValue = 0
            maxValue = ages.size - 1
            value = 0
            displayedValues = ages
        }

        val genderPicker = view.findViewById<NumberPicker>(R.id.genderPicker).apply {
            minValue = 0
            maxValue = genders.size - 1
            value = 0
            displayedValues = genders
        }

        // API 29 이상에서만 setTextColor 사용
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            agePicker.textColor = Color.BLACK
            agePicker.textSize = 50F

            genderPicker.textColor = Color.BLACK
            genderPicker.textSize = 50F
        } else {
            val agePickerChildCount = agePicker.childCount

            for (i in 0 until agePickerChildCount) {
                val child = agePicker.getChildAt(i)

                if (child is TextView) {
                    child.setTextColor(Color.BLACK)
                    child.textSize = 50F
                }
            }

            val genderPickerChildCount = genderPicker.childCount

            for (i in 0 until genderPickerChildCount) {
                val child = genderPicker.getChildAt(i)

                if (child is TextView) {
                    child.setTextColor(Color.BLACK)
                    child.textSize = 50F
                }
            }
        }

        // AlertDialog 생성
        val dialog = AlertDialog.Builder(requireContext(), R.style.AppAlertDialogTheme)
            .setView(view)
            .setTitle("성별과 나이대를 선택하세요.")
            .setPositiveButton("OK") { _, _ ->
                // OK 버튼 클릭 시 동작
                val genderSelectedItem = genderPicker.value
                val genderStr = genderPicker.displayedValues[genderSelectedItem]
                
                val ageSelectedItem = agePicker.value
                val ageStr = agePicker.displayedValues[ageSelectedItem]

                val age = CommonUtils.getAge(requireContext(), ageStr)
                val gender = CommonUtils.getMaleNumber(genderStr)

                getIceCreamByPersonInfo(gender = gender, age = age)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        // 다이얼로그 표시
        dialog.show()
    }

    private fun getIceCreamByType(type: String) {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.iceCreamService.getAllIceCream(IceCreamRequest(type = type))
            }.onSuccess {
                setNotifyRecyclerView(it)
            }.onFailure {
                Log.d(TAG, "실패")
            }
        }
    }

    private fun getIceCreamByPersonInfo(gender: Int, age: Int) {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.iceCreamService.getAllIceCream(IceCreamRequest(gender = gender, age = age))
            }.onSuccess {
                Log.d(TAG, "getIceCreamByPersonInfo: $it")
                setNotifyRecyclerView(it)
            }.onFailure {
                Log.d(TAG, "실패")
            }
        }
    }

    private fun setNotifyRecyclerView(iceCreamList: List<IceCream>) {
        iceCreamAdapter.iceCreamList = iceCreamList
        iceCreamAdapter.notifyDataSetChanged()
    }

    companion object {
        const val TYPE = 0
        const val POPULARITY = 1
        const val AI_RECOMMEND = 2
    }
}
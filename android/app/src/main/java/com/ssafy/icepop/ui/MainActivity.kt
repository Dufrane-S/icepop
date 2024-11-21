package com.ssafy.icepop.ui

import android.os.Bundle
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.ActivityMainBinding
import com.ssafy.icepop.ui.home.HomeFragment
import com.ssafy.icepop.ui.list.IceCreamListFragment
import com.ssafy.icepop.ui.my.MyPageFragment
import com.ssafy.smartstore_jetpack.base.BaseActivity

private const val TAG = "MainActivity_ssafy"
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_layout, HomeFragment())
            .commit()

        initListener()
    }

    private fun initListener() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_layout, HomeFragment())
                        .commit()
                    true
                }

                R.id.list_page -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_layout, IceCreamListFragment())
                        .commit()
                    true
                }

                R.id.my_page -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_layout, MyPageFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener { item ->
            // 재 선택시 다시 랜더링 방지
            if (binding.bottomNavigation.selectedItemId != item.itemId) {
                binding.bottomNavigation.selectedItemId = item.itemId
            }
        }
    }
}
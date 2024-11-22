package com.ssafy.icepop.ui

import android.os.Bundle
import com.ssafy.icepop.R
import com.ssafy.icepop.databinding.ActivityMainBinding
import com.ssafy.icepop.ui.home.HomeFragment
import com.ssafy.icepop.ui.list.IceCreamDetailFragment
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

    fun openFragment(index: Int, key: String, value: Int) {
        moveFragment(index, key, value)
    }

    fun openFragment(index: Int, value: Int) {
        moveFragment(index, "", value)
    }

    fun openFragment(index: Int) {
        moveFragment(index, "", 0)
    }

    private fun moveFragment(index: Int, key: String, value: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (index) {
            ICE_CREAM_DETAIL_FRAGMENT -> {
                val fragment = IceCreamDetailFragment.newInstance(value)
                transaction.replace(R.id.main_fragment_layout, fragment)
                    .addToBackStack(null) // 뒤로가기 지원
            }
//            //장바구니
//            1 -> {
//                // 재주문
//                if (key.isNotBlank() && value != 0) {
//                    transaction.replace(
//                        R.id.frame_layout_main,
//                        ShoppingListFragment.newInstance(value)
//                    )
//                        .addToBackStack(null)
//                } else {
//                    transaction.replace(
//                        R.id.frame_layout_main,
//                        ShoppingListFragment()
//                    )
//                        .addToBackStack(null)
//                }
//            }
//            //주문 상세 보기
//            2 -> transaction.replace(R.id.frame_layout_main, OrderDetailFragment())
//                .addToBackStack(null)
//            //메뉴 상세 보기
//            3 -> transaction.replace(R.id.frame_layout_main, MenuDetailFragment())
//                .addToBackStack(null)
//            //map으로 가기
//            4 -> transaction.replace(R.id.frame_layout_main, MapFragment())
//                .addToBackStack(null)
//            //logout
//            5 -> {
//                logout()
//            }
        }
        transaction.commit()
    }

    companion object {
        const val ICE_CREAM_DETAIL_FRAGMENT = 1
    }
}
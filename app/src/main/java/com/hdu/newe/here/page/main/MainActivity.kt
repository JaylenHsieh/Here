package com.hdu.newe.here.page.main

import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.hdu.newe.here.R
import com.hdu.newe.here.page.main.leavenoto.LeaveNotoFragment
import com.hdu.newe.here.page.main.profile.ProfileFragment
import com.hdu.newe.here.page.main.profile.ProfilePresenter
import com.jonnyhsia.uilib.widget.BottomNavigation
import java.util.Arrays
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navItems = Arrays.asList(
                BottomNavigation.BottomNavItem("attendance", R.drawable.ic_attendance),
                BottomNavigation.BottomNavItem("data", R.drawable.ic_data),
                BottomNavigation.BottomNavItem("notification", R.drawable.ic_notification),
                BottomNavigation.BottomNavItem("profile", R.drawable.ic_profile))
        bottomBar.setNavItems(navItems)
                .addItemSelectListener { oldPos, pos, _ ->
                    showFragmentByPosition(oldPos, pos)
                }
                .addPrimarySelectListener {

                }
                .addItemReselectListener { pos, _ ->
                    Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
                }
                .performClickItem(0)
    }

    //通过传入的 oldPos 和 pos 来判断加载哪个 Fragment
    private fun showFragmentByPosition(oldPos: Int, pos: Int) {
        val transaction = supportFragmentManager.beginTransaction()

        val newFragment = findOrGenerateFragment(pos)
        if (oldPos != BottomNavigation.defaultIndex) {
            val oldFragment = findOrGenerateFragment(oldPos)
            transaction.hide(oldFragment)
        }

        if (newFragment.isAdded) {
            transaction.show(newFragment)
        } else {
            transaction.add(R.id.container, newFragment, "fragment$pos")
        }

        transaction.commit()
    }

    private fun findOrGenerateFragment(pos: Int): Fragment {
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag("fragment$pos")

        if (fragment != null) {
            return fragment
        }

        /***************************
         * 第一个 Fragment 是签到
         * 第二个 Fragment 是数据
         * 第三个 Fragment 是通知
         * 第四个 Fragment 是个人中心
         * 中间那个按钮是创建请假条的 Activity
         */
        return when (pos) {
            0 -> ProfileFragment()
            1 -> LeaveNotoFragment()
            3 -> Fragment()
            4 -> ProfileFragment().apply { bindPresenter(ProfilePresenter(this)) }
            else -> throw Exception("Position 不可以是 0,1,3,4 以外的数")
        }
    }
}

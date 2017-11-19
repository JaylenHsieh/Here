package com.hdu.newe.here.page.main

import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.TextView
import android.widget.Toast
import com.hdu.newe.here.R
import com.hdu.newe.here.page.base.BaseFragment
import com.hdu.newe.here.page.main.profile.ProfileFragment
import com.hdu.newe.here.page.main.profile.ProfilePresenter
import com.jonnyhsia.uilib.widget.BottomNavigation
import java.util.Arrays
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navItems = Arrays.asList(
                BottomNavigation.BottomNavItem("", R.mipmap.ic_eip),
                BottomNavigation.BottomNavItem("", R.mipmap.ic_eip),
                BottomNavigation.BottomNavItem("", R.mipmap.ic_eip),
                BottomNavigation.BottomNavItem("", R.mipmap.ic_eip))
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

        return when (pos) {
            0 -> ProfileFragment()
            1 -> Fragment()
            3 -> Fragment()
            4 -> ProfileFragment().apply { bindPresenter(ProfilePresenter(this)) }
            else -> throw Exception("Position 不可以是 0,1,3,4 以外的数")
        }
    }
}

package com.hdu.newe.here.page.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.hdu.newe.here.R
import com.hdu.newe.here.biz.ModelFactory
import com.hdu.newe.here.page.main.inbox.InboxFragment
import com.hdu.newe.here.page.main.leaverequest.LeaveRequestActivity
import com.hdu.newe.here.page.main.leaverequest.teacher.LeaveRequestActivityTeacher
import com.hdu.newe.here.page.main.login.LoginActivity
import com.hdu.newe.here.page.main.profile.PersonalInfoActivity
import com.hdu.newe.here.page.main.profile.ProfileFragment
import com.hdu.newe.here.page.main.signin.LBSFragment
import com.hdu.newe.here.page.main.variousdata.student.VariousDataFragment
import com.hdu.newe.here.page.main.variousdata.student.VariousDataPresenter
import com.hdu.newe.here.utils.UIUtils
import com.jonnyhsia.uilib.widget.BottomNavigation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_lbs.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var currentPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        UIUtils.transparentStatusBar(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (ModelFactory.getUserInterface().isUserLogin.not()) {
            val bundle = Bundle().apply {
                putString("test", "lallala")
                putInt("int", 1213123)
            }

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

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
                    val leaveRequestObjId = getSharedPreferences("user", Context.MODE_PRIVATE)?.getString(PersonalInfoActivity.LEAVE_REQUEST_OBJ_ID, "")
                    val isTeacher = getSharedPreferences("user",Context.MODE_PRIVATE).getBoolean("isTeacher",false)
                    if ("".equals(leaveRequestObjId)) {
                        Toast.makeText(this@MainActivity,"请前往个人信息页面编辑个人信息后才可以请假",Toast.LENGTH_LONG).show()
                    } else if (isTeacher){
                        val intent = Intent(this@MainActivity,LeaveRequestActivityTeacher::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@MainActivity, LeaveRequestActivity::class.java)
                        startActivity(intent)
                    }

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
            currentPos = pos
        } else {
            transaction.add(R.id.container, newFragment, "fragment$pos")
        }

        transaction.commit()
        currentPos = pos
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (currentPos == 0) {
            val fragment = findOrGenerateFragment(0) as LBSFragment
            if (fragment.group2_subject_message.visibility == View.VISIBLE) {
                fragment.hideAndShowGroup(2,1)
                return false
            }
        }
        return super.onKeyUp(keyCode, event)
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
            0 -> LBSFragment()
//            1 -> VariousDataFragmentT().also { VariousDataPresenterT(it) }
            1 -> VariousDataFragment().also { VariousDataPresenter(it) }
            3 -> InboxFragment()
//            4 -> ProfileFragment().also { ProfilePresenter(it) }
            4 -> ProfileFragment()
            else -> throw Exception("Position 不可以是 0,1,3,4 以外的数")
        }
    }
}

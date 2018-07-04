package com.hdu.newe.here.page.main.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.hdu.newe.here.R
import com.hdu.newe.here.biz.profile.bean.LessonBean
import kotlinx.android.synthetic.main.activity_personal_info.*

class PersonalInfoActivity : AppCompatActivity() {

    private val lessonList = ArrayList<LessonBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mToolbar.setNavigationOnClickListener(View.OnClickListener { finish() })
        initLessonList()
        lessonRecList.layoutManager = LinearLayoutManager(this)
        //lessonRecList.adapter = LessonAdapter(lessonList, this)
    }

    private fun initLessonList() {
        lessonList.add(LessonBean("通信原理", "已签到"))
        lessonList.add(LessonBean("数字信号处理", "已签到"))
        lessonList.add(LessonBean("电磁场与电磁波", "已签到"))
        lessonList.add(LessonBean("通信原理","未签到"))
    }
}

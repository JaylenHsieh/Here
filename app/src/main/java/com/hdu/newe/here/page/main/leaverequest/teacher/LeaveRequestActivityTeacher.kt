package com.hdu.newe.here.page.main.leaverequest.teacher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hdu.newe.here.R

/**
 * @author Jaylen Hsieh
 * @date 2018/07/29
 */
class LeaveRequestActivityTeacher : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_request_teacher)

        supportFragmentManager
                .beginTransaction()
                .add(R.id.container,LeaveRequestFragmentTeacher())
                .commit()
    }
}
package com.hdu.newe.here.page.main.leaverequest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hdu.newe.here.R

class LeaveRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_request)
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container,LeaveRequestFragment())
                .commit()
    }

}

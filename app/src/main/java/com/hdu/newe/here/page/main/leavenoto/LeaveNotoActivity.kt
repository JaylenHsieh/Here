package com.hdu.newe.here.page.main.leavenoto

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.hdu.newe.here.R

class LeaveNotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_noto)
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container,LeaveNotoFragment())
                .commit()
    }

}

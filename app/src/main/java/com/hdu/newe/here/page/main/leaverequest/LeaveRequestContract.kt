package com.hdu.newe.here.page.main.leaverequest

import com.hdu.newe.here.page.base.BasePresenter
import com.hdu.newe.here.page.base.BaseView

/**
 * Created by Jaylen Hsieh on 2018/03/16.
 */

interface LeaveRequestContract{
    interface Presenter : BasePresenter {

        fun inputReason(reason : String)

        fun clickSubmit()
    }

    interface View : BaseView<Presenter>{
        fun render()

        /**
         * 显示正在发送数据的指示器
         */
        fun showSendingIndicator()

        fun hideSendingIndicator()
    }
}

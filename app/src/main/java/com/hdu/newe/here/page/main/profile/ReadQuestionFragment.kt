package com.hdu.newe.here.page.main.profile


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobQuery

import com.hdu.newe.here.R
import com.hdu.newe.here.biz.profile.bean.QandA
import com.hdu.newe.here.page.main.profile.adapter.QandAViewBinder
import com.hdu.newe.here.utils.notifyItemChanged
import com.hdu.newe.here.utils.query
import kotlinx.android.synthetic.main.fragment_read_question.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import java.util.*


/**
 * @author Jaylen Hsieh
 * @date 2018/5/1,劳动最光荣~~~
 */
class ReadQuestionFragment : Fragment() {

    /** QA 列表数据*/
    private var qaData: List<QandA> = Collections.emptyList()

    /** QA 列表适配器 */
    private val qaAdapter = MultiTypeAdapter().apply {
        register(QandAViewBinder{
            Toast.makeText(context, qaData[it].question, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_read_question, container, false)
        val qaList = view.findViewById<RecyclerView>(R.id.recycleQA)
        qaList.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = qaAdapter
        }
        fetchQAList()
        layout_refresh?.setOnRefreshListener { fetchQAList() }
        return view
    }

    private fun fetchQAList() {
        BmobQuery<QandA>().query{
            qaData = it
            if (it.isEmpty()){
                return@query
            }
            qaAdapter.notifyItemChanged(qaData)
        }
    }

    companion object {

        fun newInstance(): ReadQuestionFragment {
            val args = Bundle()
            val fragment = ReadQuestionFragment()
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor

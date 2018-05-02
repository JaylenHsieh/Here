package com.hdu.newe.here.page.main.profile.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hdu.newe.here.R
import com.hdu.newe.here.biz.profile.bean.QandA
import me.drakeet.multitype.ItemViewBinder

/**
 * @author Jaylen Hsieh
 * @date 2018/05/02
 */
class QandAViewBinder(
        private val tapQAItem: (pos: Int) -> Unit
) : ItemViewBinder<QandA, QandAViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder{
        val root = inflater.inflate(R.layout.item_q_and_a, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, qandA: QandA) {
        with(qandA) {
            holder.tvQuestion.text = question
            holder.tvAnswer.text = answer
        }

        // item的点击事件
        holder.itemView.setOnClickListener {
            tapQAItem(holder.adapterPosition)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQuestion: TextView = itemView.findViewById(R.id.tvQuestion)
        val tvAnswer: TextView = itemView.findViewById(R.id.tvAnswer)
        val imgTop: ImageView = itemView.findViewById(R.id.imgTop)
    }
}
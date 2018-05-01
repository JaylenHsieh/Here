package com.hdu.newe.here.page.main.profile.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.profile.bean.QuestionBean;

import java.util.List;

/**
 * Created by Jaylen Hsieh on 2018/04/26.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private List<QuestionBean> mQuestionBeanList;

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvQuestion;
        TextView tvAnswer;

        public MyViewHolder(View view){
            super(view);
            tvQuestion = view.findViewById(R.id.tvQuestion);
            tvAnswer = view.findViewById(R.id.tvAnswer);
        }
    }

    public QuestionAdapter(List<QuestionBean> questionBeanList){
        mQuestionBeanList = questionBeanList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        //TODO 点击展开
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        QuestionBean questionBean = mQuestionBeanList.get(position);
        holder.tvQuestion.setText(questionBean.getQuestion());
        holder.tvAnswer.setText(questionBean.getAnswer());
    }

    @Override
    public int getItemCount() {
        return mQuestionBeanList.size();
    }
}

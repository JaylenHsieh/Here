package com.hdu.newe.here.page.main.profile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.profile.Bean.LessonBean;

import java.util.List;

/**
 * Created by Jaylen Hsieh on 2018/04/12.
 */
public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {
    private List<LessonBean> mLessonList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View lessonView;
        TextView lessonName;
        TextView lessonState;

        public ViewHolder(View view){
            super(view);
            lessonView = view;
            lessonName = view.findViewById(R.id.tv_lesson_name);
            lessonState = view.findViewById(R.id.tv_lesson_state);
        }
    }

    public LessonAdapter(List<LessonBean> lessonList,Context context){
        mLessonList = lessonList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lesson, parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.lessonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                LessonBean lesson = mLessonList.get(position);
                Toast.makeText(mContext, holder.lessonState.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LessonBean lesson = mLessonList.get(position);
        holder.lessonName.setText(lesson.getLesson());
        holder.lessonState.setText(lesson.getState());
    }

    @Override
    public int getItemCount() {
        return mLessonList.size();
    }
}

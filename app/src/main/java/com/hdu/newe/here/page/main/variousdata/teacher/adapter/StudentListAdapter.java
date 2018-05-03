package com.hdu.newe.here.page.main.variousdata.teacher.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdu.newe.here.R;

import java.util.List;

/**
 * 学生列表适配器
 *
 * @author pope
 * @date 2018/5/5
 */

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private List<String> studentNameList;
    private List<String> studentNumList;

    public StudentListAdapter(List<String> studentNameList, List<String> studentNumList) {
        this.studentNameList = studentNameList;
        this.studentNumList = studentNumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_one_to_one_sample,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.studentName.setText(studentNameList.get(position));
        holder.studentNum.setText(studentNumList.get(position));
    }

    @Override
    public int getItemCount() {
        return studentNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView studentName;
        TextView studentNum;

        public ViewHolder(View itemView) {
            super(itemView);
            studentNum = itemView.findViewById(R.id.tv_one_to_one_right);
            studentName = itemView.findViewById(R.id.tv_one_to_one_left);
        }
    }
}

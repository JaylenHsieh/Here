package com.hdu.newe.here.page.main.variousdata.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdu.newe.here.R;

import java.util.List;

/**
 * @author pope
 * @date 2018/4/8
 */

public class AttendanceRateAdapter extends RecyclerView.Adapter<AttendanceRateAdapter.ViewHolder> {

    private List<String> subjectList;
    private List<Number> attendanceRateList;

    public AttendanceRateAdapter(List<String> subjectList, List<Number> attendanceRateList) {
        this.subjectList = subjectList;
        this.attendanceRateList = attendanceRateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance_recyclerview,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String subjectName = subjectList.get(position);
        Number attendanceRate = attendanceRateList.get(position);
        holder.subjectName.setText(subjectName);
        holder.attendanceRate.setText(attendanceRate.toString());
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subjectName;
        TextView attendanceRate;

        public ViewHolder(View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.tv_subject_name);
            attendanceRate = itemView.findViewById(R.id.tv_attendance_rate);
        }
    }
}

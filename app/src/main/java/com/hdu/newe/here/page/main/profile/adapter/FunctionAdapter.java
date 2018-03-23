package com.hdu.newe.here.page.main.profile.adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.profile.Bean.FunctionBean;

import java.util.List;

/**
 * Created by Jaylen Hsieh on 2018/03/08.
 */

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.ViewHolder> {
    private List<FunctionBean> mFunctionBeanList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View functionView;
        ImageView functionIcon;
        TextView functionName;

        public ViewHolder(View view) {
            super(view);
            functionView = view;
            functionIcon = view.findViewById(R.id.ic_function);
            functionName = view.findViewById(R.id.name_function);
        }
    }

    public FunctionAdapter(List<FunctionBean> functionBeanList) {
        mFunctionBeanList = functionBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.functionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                FunctionBean function = mFunctionBeanList.get(position);
                switch (position) {
                    case 0:
                        Toast.makeText(view.getContext(), "个人信息", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(view.getContext(), "更换手机", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(view.getContext(), "这已经是一个船新的版本了", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        Snackbar.make(view,"若要退出请前往设置清除数据",Snackbar.LENGTH_LONG).show();
                    default:
                }

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FunctionBean profile = mFunctionBeanList.get(position);
        holder.functionIcon.setImageResource(profile.getImageId());
        holder.functionName.setText(profile.getName());
    }

    @Override
    public int getItemCount() {
        return mFunctionBeanList.size();
    }
}

package com.hdu.newe.here.page.main.variousdata;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;
import com.hdu.newe.here.page.main.variousdata.adapter.ExpandRecyclerAdapter;
import com.hdu.newe.here.page.main.variousdata.bean.ExpandDataBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author pope
 */
public class HistoryDataFragment extends Fragment {

    @BindView(R.id.recyclerview_history_expandlist)
    RecyclerView recyclerviewHistoryExpandlist;
    Unbinder unbinder;

    public static HistoryDataFragment newInstance() {
        HistoryDataFragment fragment = new HistoryDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_data, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void loadHistoryData(List<ExpandDataBean> expandDataBeans) {
        recyclerviewHistoryExpandlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        ExpandRecyclerAdapter mAdapter = new ExpandRecyclerAdapter(getActivity(),expandDataBeans);
        recyclerviewHistoryExpandlist.setAdapter(mAdapter);
        //滚动监听
        mAdapter.setOnScrollListener(new ExpandRecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                recyclerviewHistoryExpandlist.scrollToPosition(pos);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

//package com.hdu.newe.here.page.main.login;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.Toast;
//
//import com.hdu.newe.here.R;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.Unbinder;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class LoginNoticeFragment extends Fragment {
//
//
//    @BindView(R.id.checkIsRead1)
//    CheckBox checkIsRead1;
//    @BindView(R.id.checkIsRead2)
//    CheckBox checkIsRead2;
//    @BindView(R.id.checkIsRead3)
//    CheckBox checkIsRead3;
//    @BindView(R.id.checkIsRead4)
//    CheckBox checkIsRead4;
//    Unbinder unbinder;
//
//
//    public LoginNoticeFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_login_notice, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        return view;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//
//        checkIsRead1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (checkIsRead1.isChecked() && checkIsRead2.isChecked() && checkIsRead3.isChecked() && checkIsRead4.isChecked()) {
//                    Toast.makeText(getContext(), "全部确认", Toast.LENGTH_SHORT).show();
//                    transaction.remove(getParentFragment()).commit();
//                }
//            }
//        });
//        checkIsRead2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (checkIsRead1.isChecked() && checkIsRead2.isChecked() && checkIsRead3.isChecked() && checkIsRead4.isChecked()) {
//                    Toast.makeText(getContext(), "全部确认", Toast.LENGTH_SHORT).show();
//                    transaction.remove(getParentFragment()).commit();
//                }
//            }
//        });
//        checkIsRead3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (checkIsRead1.isChecked() && checkIsRead2.isChecked() && checkIsRead3.isChecked() && checkIsRead4.isChecked()) {
//                    Toast.makeText(getContext(), "全部确认", Toast.LENGTH_SHORT).show();
//                    transaction.remove(getParentFragment()).commit();
//
//                }
//            }
//        });
//        checkIsRead4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (checkIsRead1.isChecked() && checkIsRead2.isChecked() && checkIsRead3.isChecked() && checkIsRead4.isChecked()) {
//                    Toast.makeText(getContext(), "全部确认", Toast.LENGTH_SHORT).show();
//                    transaction.remove(getParentFragment()).commit();
//                }
//
//            }
//        });
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//}

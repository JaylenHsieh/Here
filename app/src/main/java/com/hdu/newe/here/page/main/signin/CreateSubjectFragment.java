package com.hdu.newe.here.page.main.signin;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.profile.bean.ClassDataBean;
import com.hdu.newe.here.biz.user.entity.UserBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author pope
 */
public class CreateSubjectFragment extends Fragment {

    @BindView(R.id.tv_create_teacher_name)
    TextView tvCreateTeacherName;
    @BindView(R.id.btn_create)
    Button btnCreate;
    Unbinder unbinder;
    @BindView(R.id.numberPicker_create_day_of_week)
    NumberPicker numberPickerCreateDayOfWeek;
    @BindView(R.id.numberPicker_create_last_class)
    NumberPicker numberPickerCreateLastClass;
    @BindView(R.id.numberPicker_create_first_class)
    NumberPicker numberPickerCreateFirstClass;
    @BindView(R.id.numberPicker_create_day_of_week2)
    NumberPicker numberPickerCreateDayOfWeek2;
    @BindView(R.id.numberPicker_create_last_class2)
    NumberPicker numberPickerCreateLastClass2;
    @BindView(R.id.numberPicker_create_first_class2)
    NumberPicker numberPickerCreateFirstClass2;
    @BindView(R.id.editText_create_subject_name)
    EditText editTextCreateSubjectName;
    @BindView(R.id.numberPicker_building)
    NumberPicker numberPickerBuilding;
    @BindView(R.id.numberPicker_classroom)
    NumberPicker numberPickerClassroom;
    @BindView(R.id.numberPicker_building_derection)
    NumberPicker numberPickerBuildingDerection;
    @BindView(R.id.textView_building_derection)
    TextView textViewBuildingDerection;
    @BindView(R.id.numberPicker_building2)
    NumberPicker numberPickerBuilding2;
    @BindView(R.id.numberPicker_classroom2)
    NumberPicker numberPickerClassroom2;
    @BindView(R.id.numberPicker_building_derection2)
    NumberPicker numberPickerBuildingDerection2;
    @BindView(R.id.textView_building_derection2)
    TextView textViewBuildingDerection2;

    private SharedPreferences sharedPreferences;
    private String[] dayOfWeek = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private String[] classSelect = {"第一节", "第二节", "第三节", "第四节", "第五节", "第六节", "第七节",
            "第八节", "第九节", "第十节", "第十一节", "第十二节"};
    private String[] dayOfWeek2 = {"无", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private String[] classSelect2 = {"无", "第一节", "第二节", "第三节", "第四节", "第五节", "第六节", "第七节",
            "第八节", "第九节", "第十节", "第十一节", "第十二节"};
    private String[] teachingBuilding = {"1", "2", "3", "6", "7", "8", "9", "10", "11", "12"};
    private String[] teachingBuilding2 = {"无", "1", "2", "3", "6", "7", "8", "9", "10", "11", "12"};
    private String[] buildingDerection = {"北", "中", "南"};
    private String[] buildingDerection2 = {"无", "北", "中", "南"};
    private String[] classroomList1;
    private String[] classroomList2;
    private String[] classroomList3;
    private String[] classroomList6;
    private String[] classroomList8;
    private String[] classroomList7N = {"106", "108", "110", "116", "118", "120", "122", "206",
            "208", "210", "216", "218", "220", "222", "302", "306", "308", "310", "316", "318",
            "320", "322", "404", "406", "408", "414", "416", "418", "420", "504", "506", "508"};
    private String[] classroomList7M = {"1011", "1021", "2011", "2012", "2021", "2022", "301", "302"};
    private String[] classroomList7S = {"105", "107", "109", "111", "115", "117", "119", "121",
            "127", "129(即127)", "205", "206", "207", "208", "209", "210", "211", "215", "216",
            "217", "218", "219", "220", "221", "301", "305", "307", "309", "311", "315", "317",
            "319", "321", "403", "405", "407", "409", "412", "415", "417", "419", "503", "505",
            "507", "509", "513", "515", "517", "519"};
    private String[] classroomList7N2 = {"无", "106", "108", "110", "116", "118", "120", "122", "206",
            "208", "210", "216", "218", "220", "222", "302", "306", "308", "310", "316", "318",
            "320", "322", "404", "406", "408", "414", "416", "418", "420", "504", "506", "508"};
    private String[] classroomList7M2 = {"无", "1011", "1021", "2011", "2012", "2021", "2022", "301", "302"};
    private String[] classroomList7S2 = {"无", "105", "107", "109", "111", "115", "117", "119", "121",
            "127", "129(即127)", "205", "206", "207", "208", "209", "210", "211", "215", "216",
            "217", "218", "219", "220", "221", "301", "305", "307", "309", "311", "315", "317",
            "319", "321", "403", "405", "407", "409", "412", "415", "417", "419", "503", "505",
            "507", "509", "513", "515", "517", "519"};
    private String[] classroomList9;
    private String[] classroomList10;
    private String[] classroomList11;
    private String[] classroomList12;

    private List<Integer> timeCode;
    private List<Integer> placeCode;

    private ProgressDialog progressDialog;

    /**
     * 创建CreateSubjectFragment的唯一对象
     *
     * @return CreateSubjectFragment的唯一对象
     */
    public static CreateSubjectFragment newInstance() {
        CreateSubjectFragment fragment = new CreateSubjectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_subject, container, false);
        unbinder = ButterKnife.bind(this, view);

        //初始化控件
        initWidget();

        return view;
    }

    /**
     * 初始化控件方法
     */
    private void initWidget() {

        //初始化timeCode和placeCode
        timeCode = new ArrayList<>();
        placeCode = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            timeCode.add(1);
        }
        for (int i = 0; i < 3; i++) {
            timeCode.add(0);
        }
        for (int i = 0; i < 6; i++) {
            placeCode.add(0);
        }

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        tvCreateTeacherName.setText(sharedPreferences.getString("userName", "ERROR"));

        //数字选择器 选择上课地点
        numberPickerBuilding.setDisplayedValues(teachingBuilding);
        numberPickerBuilding2.setDisplayedValues(teachingBuilding2);
        numberPickerBuildingDerection.setDisplayedValues(buildingDerection);
        numberPickerBuildingDerection2.setDisplayedValues(buildingDerection2);
        numberPickerClassroom.setDisplayedValues(classroomList7N);
        numberPickerClassroom2.setDisplayedValues(classroomList7N2);
        numberPickerBuilding.setMaxValue(teachingBuilding.length - 1);
        numberPickerBuilding2.setMaxValue(teachingBuilding2.length - 1);
        numberPickerBuildingDerection.setMaxValue(buildingDerection.length - 1);
        numberPickerBuildingDerection2.setMaxValue(buildingDerection2.length - 1);
        numberPickerClassroom.setMaxValue(classroomList7N.length - 1);
        numberPickerClassroom2.setMaxValue(classroomList7N2.length - 1);
        numberPickerBuilding.setMinValue(0);
        numberPickerBuilding2.setMinValue(0);
        numberPickerBuildingDerection.setMinValue(0);
        numberPickerBuildingDerection2.setMinValue(0);
        numberPickerClassroom.setMinValue(0);
        numberPickerClassroom2.setMinValue(0);

        //数字选择器 选择上课时间
        numberPickerCreateDayOfWeek.setDisplayedValues(dayOfWeek);
        numberPickerCreateFirstClass.setDisplayedValues(classSelect);
        numberPickerCreateLastClass.setDisplayedValues(classSelect);
        numberPickerCreateDayOfWeek.setMinValue(0);
        numberPickerCreateFirstClass.setMinValue(0);
        numberPickerCreateLastClass.setMinValue(0);
        numberPickerCreateDayOfWeek.setMaxValue(dayOfWeek.length - 1);
        numberPickerCreateFirstClass.setMaxValue(classSelect.length - 1);
        numberPickerCreateLastClass.setMaxValue(classSelect.length - 1);
        numberPickerCreateDayOfWeek2.setDisplayedValues(dayOfWeek2);
        numberPickerCreateFirstClass2.setDisplayedValues(classSelect2);
        numberPickerCreateLastClass2.setDisplayedValues(classSelect2);
        numberPickerCreateDayOfWeek2.setMinValue(0);
        numberPickerCreateFirstClass2.setMinValue(0);
        numberPickerCreateLastClass2.setMinValue(0);
        numberPickerCreateDayOfWeek2.setMaxValue(dayOfWeek2.length - 1);
        numberPickerCreateFirstClass2.setMaxValue(classSelect2.length - 1);
        numberPickerCreateLastClass2.setMaxValue(classSelect2.length - 1);

        //对12个数字选择器进行监听
        setNumberPickerListener();
    }

    /**
     * 对12个数字选择器设置监听
     */
    private void setNumberPickerListener() {

        //以下为上课地点的选择监听
        numberPickerBuilding.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                placeCode.set(0, i1);
            }
        });

        numberPickerBuildingDerection.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                placeCode.set(1, i1);
                switch (i1) {
                    case 0:
                        //先设置MaxValue为较小值 否则会发生数组越界的报错
                        numberPickerClassroom.setMaxValue(1);
                        numberPickerClassroom.setDisplayedValues(classroomList7N);
                        numberPickerClassroom.setMaxValue(classroomList7N.length - 1);
                        break;
                    case 1:
                        numberPickerClassroom.setMaxValue(1);
                        numberPickerClassroom.setDisplayedValues(classroomList7M);
                        numberPickerClassroom.setMaxValue(classroomList7M.length - 1);
                        break;
                    case 2:
                        numberPickerClassroom.setMaxValue(1);
                        numberPickerClassroom.setDisplayedValues(classroomList7S);
                        numberPickerClassroom.setMaxValue(classroomList7S.length - 1);
                        break;
                    default:
                        break;
                }
            }
        });

        numberPickerClassroom.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                placeCode.set(2, i1);
            }
        });

        numberPickerBuilding2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                placeCode.set(3, i1);
            }
        });

        numberPickerBuildingDerection2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                placeCode.set(4, i1);
                switch (i1) {
                    case 0:
                        numberPickerClassroom2.setMaxValue(1);
                        numberPickerClassroom2.setDisplayedValues(classroomList7N2);
                        numberPickerClassroom2.setMaxValue(classroomList7N2.length - 1);
                        break;
                    case 1:
                        numberPickerClassroom2.setMaxValue(1);
                        numberPickerClassroom2.setDisplayedValues(classroomList7M2);
                        numberPickerClassroom2.setMaxValue(classroomList7M2.length - 1);
                        break;
                    case 2:
                        numberPickerClassroom2.setMaxValue(1);
                        numberPickerClassroom2.setDisplayedValues(classroomList7S2);
                        numberPickerClassroom2.setMaxValue(classroomList7S2.length - 1);
                        break;
                    default:
                        break;
                }
            }
        });

        numberPickerClassroom2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                placeCode.set(5, i1);
            }
        });

        //以下为上课时间的选择监听
        numberPickerCreateDayOfWeek.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                timeCode.set(0, i1 + 1);
            }
        });

        numberPickerCreateFirstClass.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                timeCode.set(1, i1 + 1);
            }
        });

        numberPickerCreateLastClass.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                timeCode.set(2, i1 + 1);
            }
        });

        numberPickerCreateDayOfWeek2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                timeCode.set(3, i1);
            }
        });

        numberPickerCreateFirstClass2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                timeCode.set(4, i1);
            }
        });

        numberPickerCreateLastClass2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                timeCode.set(5, i1);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 点击监听
     */
    @OnClick(R.id.btn_create)
    public void onViewClicked() {

        //显示进度条，以免用户以为APP失去响应
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("请稍等！");
        progressDialog.setMessage("正在创建中...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //检查用户填写的信息是否合法
        if (isLegal()) {

            //产生100~999中的随机数
            final int randomCode = createRandomCode();

            //检查该随机数是否被使用
            BmobQuery<ClassDataBean> q = new BmobQuery<>();
            q.addWhereNotEqualTo("subjectCode", "");
            q.findObjects(new FindListener<ClassDataBean>() {
                @Override
                public void done(List<ClassDataBean> list, BmobException e) {
                    if (e == null) {
                        if (list == null) {
                            createSubject(generateSubjectCode(null, randomCode),generatePlaceCode());
                            return;
                        }
                        List<String> allSubjectCode = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            allSubjectCode.add(list.get(i).getSubjectCode());
                        }
                        //生成课程码
                        String subjectCode = generateSubjectCode(allSubjectCode, randomCode);
                        //生成地点码
                        String placeCode = generatePlaceCode();
                        //创建教学班
                        createSubject(subjectCode,placeCode);
                    } else {
                        Toast.makeText(getActivity(), "班级数据查询失败\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * 生成上课地点码
     * @return 地点码
     */
    private String generatePlaceCode() {

        String code = "";
        String[] building = numberPickerBuilding.getDisplayedValues();
        String[] classroom = numberPickerClassroom.getDisplayedValues();
        if (building[placeCode.get(0)].length() < 2) {
            code = "0";
        }
        code += building[placeCode.get(0)] + classroom[placeCode.get(2)];
        if (placeCode.get(3) != 0 && placeCode.get(4) != 0 && placeCode.get(5) != 0){
            String[] building2 = numberPickerBuilding2.getDisplayedValues();
            String[] classroom2 = numberPickerClassroom2.getDisplayedValues();
            if (building2[placeCode.get(3)].length() < 2){
                code += "0";
            }
            code += building2[placeCode.get(3)] + classroom2[placeCode.get(5)];
        }
        return code;
    }

    /**
     * 创建教学班
     *
     * @param subjectCode 课程码
     */
    private void createSubject(final String subjectCode,String placeCode) {

        if (subjectCode == null) {
            return;
        }

        //创建新ClassDataBean进行数据保存
        ClassDataBean classDataBean = new ClassDataBean();
        classDataBean.setTeacherId(sharedPreferences.getString("objId", null));
        classDataBean.setClassMember(new ArrayList<String>());
        classDataBean.setSubjectCode(subjectCode);
        classDataBean.setPlaceCode(placeCode);
        classDataBean.setSubjectName(editTextCreateSubjectName.getText().toString());
        classDataBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //获取当前用户已有的教学班的课程码
                    BmobQuery<UserBean> query = new BmobQuery<>();
                    query.getObject(sharedPreferences.getString("objId", null), new QueryListener<UserBean>() {
                        @Override
                        public void done(UserBean userBean, BmobException e) {
                            if (e == null) {
                                List<String> subjectList = userBean.getSubjectList();
                                if (subjectList == null) {
                                    subjectList = new ArrayList<>();
                                }
                                //添加当前新建的教学班的课程码并更新Bmob数据
                                subjectList.add(subjectCode);
                                userBean.setSubjectList(subjectList);
                                userBean.update(userBean.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(getActivity(), "创建成功！", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            //创建并更新数据成功后跳转ResultFragment
                                            NewSubjectActivity newSubjectActivity = (NewSubjectActivity) getActivity();
                                            newSubjectActivity.setSubjectCode(subjectCode);
                                            newSubjectActivity.setSubjectName(editTextCreateSubjectName.getText().toString());
                                            newSubjectActivity.lunchResultFragment();
                                        } else {
                                            Toast.makeText(getActivity(), "创建失败3\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "创建失败2\n" + e.getMessage()
                                        , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "创建失败1\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * 检查时间段是否选择错误
     *
     * @return 检查结果
     */
    private Boolean isLegal() {
        if (editTextCreateSubjectName.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "请填写完整课程名", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return false;
        }
        //当第一次上课时间段的结束时间早于开始时间时，不合法即选择错误
        if (timeCode.get(2) < timeCode.get(1)) {
            Toast.makeText(getActivity(), "请检查您的第一次上课时间段是否选择错误", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return false;
        }
        //当第二次上课时间段的结束时间早于开始时间时，不合法即选择错误
        if (timeCode.get(5) < timeCode.get(4)) {
            Toast.makeText(getActivity(), "请检查您的第二次上课时间段是否选择错误", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return false;
        }
        int x = timeCode.get(3);
        int y = timeCode.get(4);
        int z = timeCode.get(5);
        //若无第二次上课时间段，则timeCode第3、4、5位置保存的必须全为0
        if (x == 0 && (y != 0 || z != 0)) {
            Toast.makeText(getActivity(), "请检查您的第二次上课时间段是否选择错误", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return false;
        }
        if (y == 0 && (x != 0 || z != 0)) {
            Toast.makeText(getActivity(), "请检查您的第二次上课时间段是否选择错误", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return false;
        }
        if (z == 0 && (x != 0 || y != 0)) {
            Toast.makeText(getActivity(), "请检查您的第二次上课时间段是否选择错误", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return false;
        }
        int a = placeCode.get(3);
        int b = placeCode.get(4);
        int c = placeCode.get(5);
        //若无第二次上课时间段，则placeCode第3、4、5位置保存的必须全为0
        if (a == 0 && (b != 0 || c != 0)) {
            Toast.makeText(getActivity(), "请检查您的第二次上课地点是否选择错误", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return false;
        }
        if (b == 0 && (a != 0 || c != 0)) {
            Toast.makeText(getActivity(), "请检查您的第二次上课地点是否选择错误", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return false;
        }
        if (c == 0 && (a != 0 || b != 0)) {
            Toast.makeText(getActivity(), "请检查您的第二次上课地点是否选择错误", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return false;
        }
        return true;
    }

    /**
     * 生成课程码
     *
     * @param allSubjectCode 所有课程码
     * @param randomCode     随机码（三位数）
     */
    private String generateSubjectCode(List<String> allSubjectCode, int randomCode) {

        //遍历已有的subjectCode，检查前三位随机数是否有重复，若重复则重新生成并检查直到无重复
        if (allSubjectCode != null && allSubjectCode.size() != 0) {
            for (int i = 0; i < allSubjectCode.size(); i++) {
                int code = Integer.valueOf(allSubjectCode.get(i).substring(0, 3));
                if (code == randomCode) {
                    //发现重复则重新生成唯一随机码并重新遍历检查
                    generateSubjectCode(allSubjectCode, createRandomCode());
                    return null;
                }
            }
        }

        //将时间段转换成课程码
        String subjectCode = String.valueOf(randomCode)
                + String.valueOf(timeCode.get(0));
        //若该课在当天的节数小于10，则在前面先补个0
        if (timeCode.get(1) < 10) {
            subjectCode += "0";
        }
        subjectCode += String.valueOf(timeCode.get(1));
        subjectCode += String.valueOf(timeCode.get(2) - timeCode.get(1) + 1);

        //当该课有两个时间段时，课程码从7位数增加至11位数
        if (timeCode.get(3) != 0 && timeCode.get(4) != 0 && timeCode.get(5) != 0) {
            subjectCode += String.valueOf(timeCode.get(3));
            //若该课在当天的节数小于10，则在前面先补个0
            if (timeCode.get(4) < 10) {
                subjectCode += "0";
            }
            subjectCode += String.valueOf(timeCode.get(4));
            subjectCode += String.valueOf(timeCode.get(5) - timeCode.get(4) + 1);
        }
        return subjectCode;
    }

    /**
     * 产生100~999中的随机数
     *
     * @return 100~999中的随机数
     */
    private int createRandomCode() {
        Random random = new Random();
        return random.nextInt(999) % 900 + 100;
    }
}
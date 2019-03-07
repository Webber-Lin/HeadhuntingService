package com.linzhaowei.headhuntingservice.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.linzhaowei.headhuntingservice.data.Applicant;
import com.linzhaowei.headhuntingservice.ApplicantMain;
import com.linzhaowei.headhuntingservice.JobObjective;
import com.linzhaowei.headhuntingservice.R;
import com.linzhaowei.headhuntingservice.Resume;


/**
 * 求职者个人模块的Fragment
 */
public class PersonalFragment extends Fragment implements View.OnClickListener{
    View view;

    private TextView realname;        //真实姓名
    private Button resume;            //简历按钮
    private Button jobobjective;      //求职意向按钮
    ApplicantMain activity;
    Applicant applicant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view=inflater.inflate(R.layout.activity_personal_fragment,container,false);

        activity=(ApplicantMain)getActivity();

        initViews();

        setEvents();

        applicant=activity.getApplicant();

        realname.setText(applicant.getRealname());

        return view;
    }


    private void initViews(){
        realname=view.findViewById(R.id.P_realname);
        resume=view.findViewById(R.id.resume);
        jobobjective=view.findViewById(R.id.jobobjective);
    }

    private void setEvents(){
        resume.setOnClickListener(this);
        jobobjective.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.resume:
                Intent intent = new Intent(getActivity().getApplicationContext(),Resume.class);
                intent.putExtra("username",applicant.getUsername());
                startActivity(intent);
                break;
            case R.id.jobobjective:
                Intent intent2 = new Intent(getActivity().getApplicationContext(),JobObjective.class);
                intent2.putExtra("username",applicant.getUsername());
                startActivity(intent2);

                break;
            default:
                break;
        }
    }



}

package com.linzhaowei.headhuntingservice.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.linzhaowei.headhuntingservice.data.Interviewer;
import com.linzhaowei.headhuntingservice.InterviewerMain;
import com.linzhaowei.headhuntingservice.InterviewerPersonalInformation;
import com.linzhaowei.headhuntingservice.JobInformationMain;
import com.linzhaowei.headhuntingservice.R;

/**
 * 招聘者个人界面
 */
public class InterviewerPersonalFragment extends Fragment implements View.OnClickListener{

    private View view;
    private TextView inteusername;
    private InterviewerMain interviewerMain;
    private Button inteinfo;
    private Button interelease;

    Interviewer interviewer;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_interviewer_personal, container, false);
        interviewerMain=(InterviewerMain) getActivity();
        interviewer=interviewerMain.getInterviewer();
        initViews();
        setEvents();
        inteusername.setText(interviewer.getRealname());


        return view;
    }

    void initViews(){
        inteusername=view.findViewById(R.id.inte_username);
        inteinfo=view.findViewById(R.id.inte_info);
        interelease=view.findViewById(R.id.inte_release);
    }

    private void setEvents(){
        inteinfo.setOnClickListener(this);
        interelease.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.inte_info:
                Intent intent = new Intent(getActivity(),InterviewerPersonalInformation.class);
                intent.putExtra("interviewer",interviewer);
                startActivity(intent);
                break;
            case R.id.inte_release:
                Intent intent2 = new Intent(getActivity(),JobInformationMain.class);
                intent2.putExtra("interviewer",interviewer);
                startActivity(intent2);
                break;


            default:
                break;
        }
    }

}

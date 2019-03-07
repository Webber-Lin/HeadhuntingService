package com.linzhaowei.headhuntingservice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hyphenate.chat.EMClient;
import com.linzhaowei.headhuntingservice.data.Interviewer;
import com.linzhaowei.headhuntingservice.fragment.InterviewerPersonalFragment;
import com.linzhaowei.headhuntingservice.fragment.MessageInterviewerFragment;
import com.linzhaowei.headhuntingservice.fragment.PositionInterviewerFragment;

/**
 * 招聘者主界面
 */
public class InterviewerMain extends AppCompatActivity implements View.OnClickListener {


    private Interviewer interviewer;
    private Button position;
    private Button message;
    private Button personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviewermain);

        interviewer=new Interviewer(getIntent().getStringExtra("username"));

        initViews();
        setEvents();


        replaceFragment(new PositionInterviewerFragment());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().logout(true);  //退出环信登录
    }

    private void initViews(){
        position=findViewById(R.id.i_position);
        message=findViewById(R.id.i_message);
        personal=findViewById(R.id.i_personal);

    }

    private void setEvents(){
        position.setOnClickListener(this);
        message.setOnClickListener(this);
        personal.setOnClickListener(this);

    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.i_position:
                replaceFragment(new PositionInterviewerFragment());
                break;
            case R.id.i_message:
                replaceFragment(new MessageInterviewerFragment());
                break;
            case R.id.i_personal:
                replaceFragment(new InterviewerPersonalFragment());
                break;
            default:
                break;
        }

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.interviewer,fragment);
        transaction.commit();
    }


    /**
     * 获得Interview实例
     * @return
     */
    public Interviewer getInterviewer() {
        return interviewer;
    }
}

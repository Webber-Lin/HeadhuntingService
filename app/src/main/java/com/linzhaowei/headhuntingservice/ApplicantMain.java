package com.linzhaowei.headhuntingservice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hyphenate.chat.EMClient;
import com.linzhaowei.headhuntingservice.data.Applicant;
import com.linzhaowei.headhuntingservice.fragment.MessageFragment;
import com.linzhaowei.headhuntingservice.fragment.PersonalFragment;
import com.linzhaowei.headhuntingservice.fragment.PositionFragment;

/**
 * 求职者主界面
 */
public class ApplicantMain extends AppCompatActivity implements View.OnClickListener{

    private Button position;      //职位按钮
    private Button message;       //消息按钮
    private Button personal;      //个人按钮
    private Applicant applicant;  //求职者实例



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicantmain);

        applicant=new Applicant(getIntent().getStringExtra("username"));

        replaceFragment(new PositionFragment());
        initViews();
        setEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().logout(true);  //退出环信登录
    }

    /**
     * 绑定组件
     */
    private void initViews(){
        position=findViewById(R.id.f_position);
        message=findViewById(R.id.f_message);
        personal=findViewById(R.id.f_personal);

    }

    /**
     * 绑定事件
     */
    private void setEvents(){
        position.setOnClickListener(this);
        message.setOnClickListener(this);
        personal.setOnClickListener(this);

    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.f_position:
                replaceFragment(new PositionFragment());
                break;
            case R.id.f_message:
                replaceFragment(new MessageFragment());
                break;
            case R.id.f_personal:
                replaceFragment(new PersonalFragment());
                break;
            default:
                    break;
        }

    }


    /**
     * 刷新Fragment界面
     * @param fragment
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.up_layout,fragment);
        transaction.commit();
    }


    /**
     * 获得Applicant实例
     * @return
     */
    public Applicant getApplicant() {
        return applicant;
    }
}

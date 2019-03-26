package com.linzhaowei.headhuntingservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.linzhaowei.headhuntingservice.bean.User;
import com.linzhaowei.headhuntingservice.utils.HttpUtils;
import com.linzhaowei.headhuntingservice.utils.Ip;

import java.io.IOException;

/**
 * 选择身份
 */
public class ChoseStatus extends AppCompatActivity implements View.OnClickListener{

    private Button applicant;
    private Button interviewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_status);
        initViews();
        setEvents();
    }

    private void initViews(){
        applicant=findViewById(R.id.chose_applicant);
        interviewer=findViewById(R.id.chose_interviewer);
    }

    private void setEvents(){
        applicant.setOnClickListener(this);
        interviewer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chose_applicant:
                choseStatus(1);
                break;
            case R.id.chose_interviewer:
                choseStatus(2);
                break;

                default:
                    break;
        }
    }

    private void choseStatus(int i){
        User user=new User();
        SharedPreferences sharedPreferences= getSharedPreferences("user", Context.MODE_PRIVATE);
        user.setId(sharedPreferences.getInt("id",-1));
        user.setStatus(i);
        String str=JSON.toJSONString(user);

        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils=new HttpUtils();

                try{
                    final String result=httpUtils.post(Ip.ip+"/user/chose_status",str);
                    runOnUiThread(() -> {
                        if("true".equals(result)&&1==i){
                            Toast.makeText(ChoseStatus.this,"您选择了求职者",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ChoseStatus.this,"您选择了招聘者",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

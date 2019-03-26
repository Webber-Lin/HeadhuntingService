package com.linzhaowei.headhuntingservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hyphenate.chat.EMClient;

/**
 * 开始界面，程序启动界面
 */
public class Start extends AppCompatActivity implements View.OnClickListener{

    private Button login;        //登录按钮
    private Button register;    //注册按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initViews();
        setEvents();


    }


    /**
     * 绑定组件
     */
    private void initViews(){
        login=findViewById(R.id.s_login);
        register=findViewById(R.id.s_register);

    }


    /**
     * 设置监听事件
     */
    private void setEvents(){
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.s_login:
                //跳转至登录界面
                Intent intent=new Intent(Start.this,Login.class);
                startActivity(intent);
                finish();
                break;

            case R.id.s_register:
                //跳转至注册界面
                Intent intent2=new Intent(Start.this,Register.class);
                startActivity(intent2);
                finish();
                break;

                default:
                    break;
        }

    }
}

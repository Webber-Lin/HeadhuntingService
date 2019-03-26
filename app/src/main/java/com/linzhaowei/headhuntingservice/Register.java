package com.linzhaowei.headhuntingservice;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.linzhaowei.headhuntingservice.bean.User;
import com.linzhaowei.headhuntingservice.utils.HttpUtils;
import com.linzhaowei.headhuntingservice.utils.Ip;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * 求职者注册界面
 */
public class Register extends AppCompatActivity implements View.OnClickListener{

    private EditText username;         //用户名
    private EditText userpsd;          //密码
    private EditText userpsd2;         //再次输入密码
    private Button r_register;           //注册按钮
    private TextView existing_account;  //已有账号跳转登录



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        setEvents();
    }

    /**
     * 绑定组件
     */
    private void initViews(){
        username=findViewById(R.id.r_username);
        userpsd=findViewById(R.id.r_userpsd);
        userpsd2=findViewById(R.id.r_userpsd2);
        r_register=findViewById(R.id.r_register);
        existing_account=findViewById(R.id.r_existing_account);
    }


    /**
     * 绑定事件
     */
    private void setEvents(){
        r_register.setOnClickListener(this);
        existing_account.setOnClickListener(this);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.r_register:
                registerCheck();

                break;

            case R.id.r_existing_account:
                Intent intent2=new Intent(this,Login.class);
                startActivity(intent2);
                finish();
                break;

                default:
                    break;
        }

    }



    /**
     * 注册检查
     */
    private void registerCheck(){
        if(username.getText().toString().trim().equals("")){
            Toast.makeText(Register.this,"请输入用户名",Toast.LENGTH_SHORT).show();
        }else if(userpsd.getText().toString().trim().equals("")){
            Toast.makeText(Register.this,"请输入密码",Toast.LENGTH_SHORT).show();
        }else if(userpsd2.getText().toString().trim().equals("")){
            Toast.makeText(Register.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
        }else if(!userpsd.getText().toString().trim().equals(userpsd2.getText().toString())){
            Toast.makeText(Register.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
        }else {
            register();
        }
    }

    private void register(){
        User user=new User();
        user.setUsername(username.getText().toString().trim());
        user.setUserpsd(userpsd.getText().toString().trim());
        String str= JSON.toJSONString(user);

        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils=new HttpUtils();

                try{
                    final String result=httpUtils.post(Ip.ip+"/user/register",str);
                    runOnUiThread(() -> {
                        if("true".equals(result)){
                            Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Register.this,Login.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(Register.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}

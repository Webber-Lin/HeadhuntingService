package com.linzhaowei.headhuntingservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.linzhaowei.headhuntingservice.bean.User;
import com.linzhaowei.headhuntingservice.utils.HttpUtils;
import com.linzhaowei.headhuntingservice.utils.Ip;




import java.io.IOException;



/**
 * 登录界面
 */
public class Login extends AppCompatActivity implements View.OnClickListener{


    private EditText etUserName;   //用户名
    private EditText etUserPsd;    //密码
    private Button mLogin;          //登录按钮
    private TextView to_re;         //跳转注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setEvents();

    }


    /**
     * 绑定组件
     */
    private void initViews(){
        etUserName=findViewById(R.id.l_username);
        etUserPsd=findViewById(R.id.l_userpsd);
        mLogin=findViewById(R.id.l_login);
        to_re=findViewById(R.id.l_to_re);
    }


    /**
     * 绑定事件
     */
    private void setEvents(){
        mLogin.setOnClickListener(this);
        to_re.setOnClickListener(this);
    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.l_login:
                login();

                    break;


                case R.id.l_to_re:
                        Intent intent = new Intent(Login.this, Register.class);
                        startActivity(intent);
                        finish();
                        break;

        }
    }

    private void login(){
        final User user=new User();
        user.setUsername(etUserName.getText().toString().trim());
        user.setUserpsd(etUserPsd.getText().toString().trim());
        String str= JSON.toJSONString(user);

        new Thread(){
            @Override
            public void run() {
                HttpUtils httpUtils=new HttpUtils();

                try{
                    final String result=httpUtils.post(Ip.ip+"/user/login",str);

                    JSONArray jsonArray=JSON.parseArray(result);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    runOnUiThread(() -> {
                        if(jsonObject.getInteger("id")!=null){
                            SharedPreferences sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("id",jsonObject.getInteger("id"));
                            editor.apply();
                            Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Login.this,ChoseStatus.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(Login.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



}

package com.linzhaowei.headhuntingservice;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.linzhaowei.headhuntingservice.utils.Ipv4;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * 招聘者注册界面
 */
public class Register2 extends AppCompatActivity implements View.OnClickListener{

    private TextView to_re;           //跳转求职者
    private EditText username;         //用户名
    private EditText userpsd;          //密码
    private EditText userpsd2;         //再次输入密码
    private EditText realname;         //真实姓名
    private EditText company;            //公司
    private EditText industry;           //所属行业
    private EditText companyaddress;     //公司地址
    private EditText email;              //E_mail
    private EditText tel;              //电话
    private Button r2_register;       //注册按钮
    private TextView existing_account;  //已有账号跳转登录

    public static final int REGISTER_OK=1;      //注册成功
    public static final int REGISTER_FAIL=2;   //注册失败


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        initViews();
        setEvents();
    }

    /**
     * 绑定组件
     */
    private void initViews(){
        to_re=findViewById(R.id.r2_re);
        username=findViewById(R.id.r2_username);
        userpsd=findViewById(R.id.r2_userpsd);
        userpsd2=findViewById(R.id.r2_userpsd2);
        realname=findViewById(R.id.r2_realname);
        company=findViewById(R.id.r2_company);
        industry=findViewById(R.id.r2_industry);
        companyaddress=findViewById(R.id.r2_companyaddress);
        email=findViewById(R.id.r2_email);
        tel=findViewById(R.id.r2_tel);
        r2_register=findViewById(R.id.r2_register);
        existing_account=findViewById(R.id.r2_existing_account);
    }


    /**
     * 绑定事件
     */
    private void setEvents(){
        to_re.setOnClickListener(this);
        r2_register.setOnClickListener(this);
        existing_account.setOnClickListener(this);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.r2_register:
                Register_check();

                break;

            case R.id.r2_re:
                Intent intent=new Intent(this,Register.class);
                startActivity(intent);
                finish();
                break;

            case R.id.r2_existing_account:
                Intent intent2=new Intent(this,Login.class);
                startActivity(intent2);
                finish();
                break;

            default:
                break;

        }

    }

    /**
     * 异步消息处理机制
     */

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REGISTER_OK:
                    Toast.makeText(Register2.this,"注册成功！",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Register2.this,Login.class);
                    startActivity(intent);
                    finish();
                    break;
                case REGISTER_FAIL:
                    Toast.makeText(Register2.this,"请检查输入是否正确，或用户名已存在",Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };

    /**
     * 注册检查
     */
    private void Register_check(){
        if(username.getText().toString().trim().equals("")){
            Toast.makeText(Register2.this,"请输入用户名",Toast.LENGTH_SHORT).show();
        }else if(userpsd.getText().toString().trim().equals("")){
            Toast.makeText(Register2.this,"请输入密码",Toast.LENGTH_SHORT).show();
        }else if(userpsd2.getText().toString().trim().equals("")){
            Toast.makeText(Register2.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
        }else if(!userpsd.getText().toString().trim().equals(userpsd2.getText().toString())){
            Toast.makeText(Register2.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
        }else if(realname.getText().toString().trim().equals("")){
            Toast.makeText(Register2.this,"请输入真实姓名",Toast.LENGTH_SHORT).show();
        } else if(company.getText().toString().trim().equals("")){
            Toast.makeText(Register2.this,"请输入公司名称",Toast.LENGTH_SHORT).show();
        }else if(industry.getText().toString().trim().equals("")){
            Toast.makeText(Register2.this,"请输入所属行业",Toast.LENGTH_SHORT).show();
        } else if(companyaddress.getText().toString().trim().equals("")){
            Toast.makeText(Register2.this,"请输入公司地址",Toast.LENGTH_SHORT).show();
        }  else if(email.getText().toString().trim().equals("")){
            Toast.makeText(Register2.this,"请输入E_mail",Toast.LENGTH_SHORT).show();
        }else if(tel.getText().toString().trim().equals("")){
            Toast.makeText(Register2.this,"请输入电话",Toast.LENGTH_SHORT).show();
        }else {
            ECRegister();
        }

    }



    /**
     * 注册环信账号
     */
    private void ECRegister(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    EMClient.getInstance().createAccount(username.getText().toString().trim(), userpsd.getText().toString().trim());
                    checkRegister();
                }catch (final HyphenateException e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Register2.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }


    /**
     * 注册并验证是否成功
     * @return
     */
    private void checkRegister(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    String str = Ipv4.ipv4+"/headhuntingservice/checkRegister2.php?"
                            + "username=" + username.getText().toString().trim()
                            + "&userpassword=" + userpsd.getText().toString().trim()
                            + "&realname=" + realname.getText().toString().trim()
                            + "&company=" + company.getText().toString()
                            + "&industry=" + industry.getText().toString().trim()
                            + "&company_address=" + companyaddress.getText().toString().trim()
                            + "&e_mail=" + email.getText().toString().trim()
                            + "&telephone=" + tel.getText().toString().trim();
                    Request request=new Request.Builder()
                            .url(str)
                            .build();

                    okhttp3.Response response=client.newCall(request).execute();
                    String responseData=response.body().string();

                    Message message=new Message();
                    if(responseData.equals("0")) {
                        message.what=REGISTER_OK;
                        handler.sendMessage(message);
                    }else {
                        message.what=REGISTER_FAIL;
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();


    }



}

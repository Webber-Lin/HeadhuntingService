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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.linzhaowei.headhuntingservice.utils.Ipv4;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * 登录界面
 */
public class Login extends AppCompatActivity implements View.OnClickListener{


    private EditText etUserName;   //用户名
    private EditText etUserPsd;    //密码
    private Button mLogin;          //登录按钮
    private RadioGroup role;        //身份选择
    private TextView to_re;         //跳转注册

    public static final int LOGIN_OK=0;      //求职者登录成功
    public static final int LOGIN_FAIL=1;   //登录失败
    public static final int LOGIN_OK2=2;      //招聘者登录成功

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
        role=findViewById(R.id.l_role);
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

                if (role.getCheckedRadioButtonId() == R.id.role_1) {


                    EMClient.getInstance().login(etUserName.getText().toString().trim(),etUserPsd.getText().toString().trim(),new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
                            LoginCheck();
                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }

                        @Override
                        public void onError(int code, String message) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this,"登录失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.d("code="+code, "message"+message);
                        }
                    });



                }

                if (role.getCheckedRadioButtonId() == R.id.role_2) {
                    EMClient.getInstance().login(etUserName.getText().toString().trim(),etUserPsd.getText().toString().trim(),new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
                            Log.d("main", "登录聊天服务器成功！");

                            LoginCheck2();
                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }

                        @Override
                        public void onError(int code, String message) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this,"登录失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.d("main", "登录聊天服务器失败！");
                            Log.d("login", "code:"+code+" message:"+message);
                        }
                    });
                }
                    break;


                case R.id.l_to_re:
                        Intent intent = new Intent(Login.this, Register.class);
                        startActivity(intent);
                        finish();
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
                case LOGIN_OK:
                    Toast.makeText(Login.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Login.this,ApplicantMain.class);
                    intent.putExtra("username",etUserName.getText().toString().trim());
                    startActivity(intent);
                    break;
                case LOGIN_FAIL:
                    Toast.makeText(Login.this,"登录失败，请检查账号密码是否正确",Toast.LENGTH_SHORT).show();
                    break;

                case LOGIN_OK2:
                    Toast.makeText(Login.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    Intent intent2=new Intent(Login.this,InterviewerMain.class);
                    intent2.putExtra("username",etUserName.getText().toString().trim());
                    startActivity(intent2);
                    break;

                    default:
                        break;
            }
        }
    };


    /**
     * 求职者登录检查
     */

    private void LoginCheck(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    String str = Ipv4.ipv4+"/headhuntingservice/checklogin.php?username=" +
                            etUserName.getText().toString().trim() + "&userpassword=" + etUserPsd.getText().toString().trim();
                    Request request=new Request.Builder()
                            .url(str)
                            .build();

                    okhttp3.Response response=client.newCall(request).execute();
                    String responseData=response.body().string();

                    Message message=new Message();
                    if(responseData.equals("0")) {
                        message.what=LOGIN_OK;
                        handler.sendMessage(message);
                    }else {
                        message.what=LOGIN_FAIL;
                        handler.sendMessage(message);
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


    }



    /**
     * 招聘者登录检查
     */

    private void LoginCheck2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    String str = Ipv4.ipv4+"/headhuntingservice/checklogin2.php?username=" +
                            etUserName.getText().toString().trim() + "&userpassword=" + etUserPsd.getText().toString().trim();
                    Request request=new Request.Builder()
                            .url(str)
                            .build();

                    okhttp3.Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Message message=new Message();
                    if(responseData.equals("0")) {
                        message.what=LOGIN_OK2;
                        handler.sendMessage(message);
                    }else {
                        message.what=LOGIN_FAIL;
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


    }

}

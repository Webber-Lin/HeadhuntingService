package com.linzhaowei.headhuntingservice;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.linzhaowei.headhuntingservice.data.Applicant;
import com.linzhaowei.headhuntingservice.utils.Ipv4;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 个人简历活动
 */
public class Resume extends AppCompatActivity implements View.OnClickListener {

    private final int CHECKRESUME_OK=1;
    private final int GETRESUME_OK=2;
    private final int UPDATERESUME_OK=3;

    private Applicant applicant;
    private TextView realname;
    private EditText resume;
    private Button btn_resume;
    private Button cancel;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        applicant=new Applicant(getIntent().getStringExtra("username"));

        initViews();
        setEvents();

        realname.setText(applicant.getRealname());
        checkResume();


    }


    private void initViews(){
        realname=findViewById(R.id.resume_realname);
        resume=findViewById(R.id.resume_text);
        btn_resume=findViewById(R.id.resume_btn);
        cancel=findViewById(R.id.resume_cancel);

    }

    private void setEvents(){
        btn_resume.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.resume_btn:
                updateResume();
                break;
            case R.id.resume_cancel:
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
                case CHECKRESUME_OK:
                    getResume();
                    break;


                case GETRESUME_OK:
                    resume.setText(text);
                    break;

                case UPDATERESUME_OK:
                    Toast.makeText(Resume.this,"修改简历成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 检查简历是否为空，为空创建简历
     */
    private void checkResume(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    String str = Ipv4.ipv4+"/headhuntingservice/checkresume.php?username=" +applicant.getUsername();
                    Request request=new Request.Builder()
                            .url(str)
                            .build();
                    client.newCall(request).execute();

                    Message message=new Message();
                    message.what=CHECKRESUME_OK;
                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 获取简历
     */
    private void getResume(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    String str = Ipv4.ipv4+"/headhuntingservice/getresume.php?username=" +applicant.getUsername();
                    Request request=new Request.Builder()
                            .url(str)
                            .build();
                    okhttp3.Response response=client.newCall(request).execute();
                    String responseData=response.body().string();

                    text=responseData;

                    Message message=new Message();
                    message.what=GETRESUME_OK;
                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 更新简历
     */
    private void updateResume(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    String str = Ipv4.ipv4+"/headhuntingservice/updateresume.php?" +
                            "username=" +applicant.getUsername()+
                            "&text="+resume.getText().toString();
                    Request request=new Request.Builder()
                            .url(str)
                            .build();
                    okhttp3.Response response=client.newCall(request).execute();
                    String responseData=response.body().string();

                    text=responseData;

                    Message message=new Message();
                    message.what=UPDATERESUME_OK;
                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }


}

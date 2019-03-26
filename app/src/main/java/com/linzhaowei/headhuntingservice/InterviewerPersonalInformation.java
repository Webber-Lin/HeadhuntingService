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

import com.linzhaowei.headhuntingservice.data.Interviewer;
import com.linzhaowei.headhuntingservice.utils.Ip;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 招聘者个人信息界面
 */
public class InterviewerPersonalInformation extends AppCompatActivity implements View.OnClickListener {

    private Interviewer interviewer;
    private TextView realname;
    private EditText inteinf;
    private Button btn_resume;
    private Button cancel;

    private String information;

    private final int CHECKINTERVIEWERINFORMATION_OK=1;
    private final int GETINTERVIEWERINFORMATION_OK=2;
    private final int UPDATEINTERVIEWERINFORMATION_OK=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviewer_personal_information);

        interviewer = (Interviewer) getIntent().getSerializableExtra("interviewer");

        initViews();
        setEvents();

        realname.setText(interviewer.getRealname());

        checkInterviewerInformation();




    }


    private void initViews() {
        realname = findViewById(R.id.inte_inf_realname);
        inteinf = findViewById(R.id.inte_inf_text);
        btn_resume = findViewById(R.id.inte_inf_btn);
        cancel = findViewById(R.id.inte_inf_cancel);

    }

    private void setEvents() {
        btn_resume.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inte_inf_btn:
                updateInterviewerInformation();
                break;
            case R.id.inte_inf_cancel:
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
                case CHECKINTERVIEWERINFORMATION_OK:
                    getInterviewerInformation();
                    break;


                case GETINTERVIEWERINFORMATION_OK:
                    inteinf.setText(information);
                    break;

                case UPDATEINTERVIEWERINFORMATION_OK:
                    Toast.makeText(InterviewerPersonalInformation.this,"修改个人信息成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 检查个人信息是否为空
     */
    private void checkInterviewerInformation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    String str = Ip.ip+"/headhuntingservice/checkInterviewerInformation.php?username=" +interviewer.getUsername();
                    Request request=new Request.Builder()
                            .url(str)
                            .build();
                    client.newCall(request).execute();

                    Message message=new Message();
                    message.what=CHECKINTERVIEWERINFORMATION_OK;
                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 获取个人信息
     */
    private void getInterviewerInformation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    String str = Ip.ip+"/headhuntingservice/getInterviewerInformation.php?username=" +interviewer.getUsername();
                    Request request=new Request.Builder()
                            .url(str)
                            .build();
                    okhttp3.Response response=client.newCall(request).execute();
                    String responseData=response.body().string();

                    information=responseData;

                    Message message=new Message();
                    message.what=GETINTERVIEWERINFORMATION_OK;
                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 更新个人信息
     */
    private void updateInterviewerInformation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    String str = Ip.ip+"/headhuntingservice/updateInterviewerInformation.php?" +
                            "username=" +interviewer.getUsername()+
                            "&information="+inteinf.getText().toString();
                    Request request=new Request.Builder()
                            .url(str)
                            .build();
                    okhttp3.Response response=client.newCall(request).execute();
                    String responseData=response.body().string();

                    information=responseData;

                    Message message=new Message();
                    message.what=UPDATEINTERVIEWERINFORMATION_OK;
                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

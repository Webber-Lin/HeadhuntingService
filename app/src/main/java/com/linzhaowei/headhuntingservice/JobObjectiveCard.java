package com.linzhaowei.headhuntingservice;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.hyphenate.easeui.EaseConstant;
import com.linzhaowei.headhuntingservice.data.Applicant;
import com.linzhaowei.headhuntingservice.data.JobObjects;
import com.linzhaowei.headhuntingservice.utils.Ipv4;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * 求职意向展示界面
 */
public class JobObjectiveCard extends AppCompatActivity implements View.OnClickListener {
    private TextView realname;
    private TextView sex;
    private TextView education;
    private TextView tel;
    private TextView job;
    private TextView address;
    private TextView resume;
    private Button chat;
    private String text;
    private Applicant applicant;
    private JobObjects jobObjects;
    private final int GETRESUME_OK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_objective_card);
        applicant = new Applicant(getIntent().getStringExtra("username"));
        jobObjects = (JobObjects) getIntent().getSerializableExtra("jobObjects");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initViews();
        setEvents();
        setText();
    }


    private void initViews() {
        realname = findViewById(R.id.jobobjectivecard_realname);
        sex = findViewById(R.id.jobobjectivecard_sex);
        education = findViewById(R.id.jobobjectivecard_education);
        tel = findViewById(R.id.jobobjectivecard_tel);
        job = findViewById(R.id.jobobjectivecard_job);
        address = findViewById(R.id.jobobjectivecard_address);
        resume = findViewById(R.id.jobobjectivecard_resume);
        chat = findViewById(R.id.jobobjectivecard_chat);
    }

    private void setEvents() {
        chat.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jobobjectivecard_chat:
                Intent intent = new Intent(this, ECChat.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, jobObjects.getUsername());
                intent.putExtra("realname",jobObjects.getRealname());
                startActivity(intent);
                break;

            default:
                break;
        }

    }

    private void setText() {
        realname.setText(applicant.getRealname());
        sex.setText(applicant.getSex());
        education.setText(applicant.getEducation());
        tel.setText(applicant.getTel());
        job.setText(jobObjects.getJob());
        address.setText(jobObjects.getAddress());
        getResume();

    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GETRESUME_OK:
                    resume.setText(text);
                    break;

                default:
                    break;
            }
        }
    };

    private void getResume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String str = Ipv4.ipv4 + "/headhuntingservice/getresume.php?username=" + applicant.getUsername();
                    Request request = new Request.Builder()
                            .url(str)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    text = responseData;

                    Message message = new Message();
                    message.what = GETRESUME_OK;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

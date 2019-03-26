package com.linzhaowei.headhuntingservice;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.linzhaowei.headhuntingservice.data.Applicant;
import com.linzhaowei.headhuntingservice.utils.Ip;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 添加求职意向界面
 */
public class AddJobObjective extends AppCompatActivity implements View.OnClickListener {

    private Applicant applicant;
    private EditText job;
    private EditText address;
    private Button add;
    private final int ADDJOBOBEJCTIVE_OK = 1;
    private final int ADDJOBOBEJCTIVE_FAIL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_objective);
        applicant = new Applicant(getIntent().getStringExtra("username"));
        initViews();
        setEvents();
    }

    private void initViews() {
        job = findViewById(R.id.addjobobjecive_job);
        address = findViewById(R.id.addjobobjecive_address);
        add = findViewById(R.id.addjobobjective_add);
    }

    private void setEvents() {
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addjobobjective_add:
                checkText();
                break;

            default:
                break;
        }


    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADDJOBOBEJCTIVE_OK:
                    Toast.makeText(AddJobObjective.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;

                case ADDJOBOBEJCTIVE_FAIL:
                    Toast.makeText(AddJobObjective.this, "添加失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }
        }
    };


    /**
     * 检查输入框是否为空
     */
    private void checkText() {
        if (job.getText().toString().trim().equals("")) {
            Toast.makeText(AddJobObjective.this, "工作意向", Toast.LENGTH_SHORT).show();
        } else if (address.getText().toString().trim().equals("")) {
            Toast.makeText(AddJobObjective.this, "工作地点", Toast.LENGTH_SHORT).show();
        } else {
            addJobObjective();
        }
    }

    /**
     * 添加求职意向
     */
    private void addJobObjective() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    String str = Ip.ip + "/headhuntingservice/addJobObjective.php?username=" +
                            applicant.getUsername() +
                            "&job=" + job.getText().toString().trim() +
                            "&address=" + address.getText().toString().trim();

                    Request request = new Request.Builder()
                            .url(str)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    if (responseData.equals("1")) {
                        Message message = new Message();
                        message.what = ADDJOBOBEJCTIVE_OK;
                        handler.sendMessage(message);

                    }else{
                        Message message = new Message();
                        message.what = ADDJOBOBEJCTIVE_FAIL;
                        handler.sendMessage(message);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

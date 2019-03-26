package com.linzhaowei.headhuntingservice;



import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


import com.linzhaowei.headhuntingservice.adapter.JobObjectiveAdapter;
import com.linzhaowei.headhuntingservice.data.Applicant;
import com.linzhaowei.headhuntingservice.data.JobObjects;
import com.linzhaowei.headhuntingservice.utils.Ip;

import org.json.JSONArray;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import okhttp3.OkHttpClient;
import okhttp3.Request;



/**
 * 求职意向界面
 */
public class JobObjective extends AppCompatActivity implements View.OnClickListener {

    private Applicant applicant;
    private String id[];
    private String job[];
    private String address[];
    private RecyclerView listdata;
    private Button jobobjective_add;
    private List<JobObjects> jobObjects=new ArrayList<>();
    private final int GETJOBOBJECTIVE_OK=1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_jobobjective);


        applicant=new Applicant(getIntent().getStringExtra("username"));

        initViews();
        setEvents();


    }

    @Override
    protected void onStart() {
        super.onStart();
        getJobObjective();
    }

    private void initViews(){
        listdata=findViewById(R.id.jobobjective_view);
        jobobjective_add=findViewById(R.id.jobobjective_add);
    }

    private void setEvents(){
        jobobjective_add.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jobobjective_add:
                Intent intent=new Intent(this,AddJobObjective.class);
                intent.putExtra("username",applicant.getUsername());
                startActivity(intent);
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
                case GETJOBOBJECTIVE_OK:
                    show();
                    break;

                    default:
                        break;

            }
        }
    };



    private void getJobObjective(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();

                    String str = Ip.ip+"/headhuntingservice/getjobobjective.php?username="+
                            applicant.getUsername();

                    Request request=new Request.Builder()
                            .url(str)
                            .build();

                    okhttp3.Response response=client.newCall(request).execute();
                    String responseData=response.body().string();

                    if(!responseData.equals("")){
                        JSONArray jsonArray = new JSONArray(responseData);

                        id=new String[jsonArray.length()];
                        job=new String[jsonArray.length()];
                        address=new String[jsonArray.length()];


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonData = jsonArray.getJSONObject(i);
                            id[i]=jsonData.get("id").toString();
                            job[i] = jsonData.get("job").toString();
                            address[i] =  jsonData.get("address").toString();
                        }

                        for(int i=0;i<job.length;i++) {
                            JobObjects addjob = new JobObjects(id[i],job[i], address[i]);
                            jobObjects.add(addjob);
                        }

                        Message message=new Message();
                        message.what=GETJOBOBJECTIVE_OK;
                        handler.sendMessage(message);

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 展示求职意向
     */
    private void show(){
        JobObjective.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {


                LinearLayoutManager layoutManager=new LinearLayoutManager(JobObjective.this);
                listdata.setLayoutManager(layoutManager);

                JobObjectiveAdapter adapter=new JobObjectiveAdapter(jobObjects);
                listdata.setAdapter(adapter);


            }
        });
    }



}

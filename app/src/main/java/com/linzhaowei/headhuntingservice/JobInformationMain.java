package com.linzhaowei.headhuntingservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.linzhaowei.headhuntingservice.adapter.JobInformationAdapter;
import com.linzhaowei.headhuntingservice.data.Interviewer;
import com.linzhaowei.headhuntingservice.data.JobInformation;
import com.linzhaowei.headhuntingservice.utils.Ipv4;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 职位信息界面
 */
public class JobInformationMain extends AppCompatActivity {


    private Interviewer interviewer;

    private String id[];
    private RecyclerView listdata;

    private List<JobInformation> jobInformation=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_job_information);

        interviewer=(Interviewer)getIntent().getSerializableExtra("interviewer");


        sendRequestWithOkHttp();
        show();



    }

    private void initViews(){
        listdata=findViewById(R.id.jobinformation_view);

    }


    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();

                    String str = Ipv4.ipv4+"/headhuntingservice/getalljobobjective.php";

                    Request request=new Request.Builder()
                            .url(str)
                            .build();

                    okhttp3.Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);

                    id=new String[jsonArray.length()];


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonData = jsonArray.getJSONObject(i);
                        id[i] = jsonData.get("id").toString();
                    }

                    for(int i=0;i<id.length;i++) {
                        JobInformation addjob = new JobInformation(id[i]);
                        jobInformation.add(addjob);
                    }



                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void show(){
        JobInformationMain.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                initViews();


                LinearLayoutManager layoutManager=new LinearLayoutManager(JobInformationMain.this);
                listdata.setLayoutManager(layoutManager);

                JobInformationAdapter adapter=new JobInformationAdapter(jobInformation);
                listdata.setAdapter(adapter);


            }
        });
    }



}

package com.linzhaowei.headhuntingservice.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.linzhaowei.headhuntingservice.data.Interviewer;
import com.linzhaowei.headhuntingservice.InterviewerMain;
import com.linzhaowei.headhuntingservice.utils.Ipv4;
import com.linzhaowei.headhuntingservice.data.JobObjects;
import com.linzhaowei.headhuntingservice.R;
import com.linzhaowei.headhuntingservice.adapter.PositionInterviewerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * 招聘者查看求职意向界面
 */
public class PositionInterviewerFragment extends Fragment {
    View view;


    private String id[];

    InterviewerMain activity;
    Interviewer interviewer;

    RecyclerView recyclerView;

    private List<JobObjects> jobObjects=new ArrayList<>();

    private final int GETAllJOBOBJECTIVE=1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_position_interviewer, container, false);

        activity=(InterviewerMain)getActivity();
        interviewer=activity.getInterviewer();

        getAllJobObjective();

        recyclerView=view.findViewById(R.id.position_interviewer);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        set_eSearch_TextChanged();

        return view;
    }



    /**
     * 异步消息处理机制
     */

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GETAllJOBOBJECTIVE:
                    show();
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 获取所有求职意向
     */
    private void getAllJobObjective(){
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

                    if(!responseData.equals("")){
                        JSONArray jsonArray = new JSONArray(responseData);

                        id=new String[jsonArray.length()];


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonData = jsonArray.getJSONObject(i);
                            id[i]=jsonData.get("id").toString();
                        }

                        for(int i=0;i<id.length;i++) {
                            JobObjects addjob = new JobObjects(id[i]);
                            jobObjects.add(addjob);
                        }


                        Message message=new Message();
                        message.what=GETAllJOBOBJECTIVE;
                        handler.sendMessage(message);


                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 展示
     */
    private void show(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.activity);
        recyclerView.setLayoutManager(layoutManager);
        PositionInterviewerAdapter adapter=new PositionInterviewerAdapter(jobObjects);
        recyclerView.setAdapter(adapter);

    }


    /**
     * 搜索框文本实时监听
     */
    private void set_eSearch_TextChanged() {
        final EditText eSearch = view.findViewById(R.id.query);

        eSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                String tempName = eSearch.getText().toString();
                queryData(tempName);
            }
        });
    }


    /**
     * 模糊查询数据 并显示在ListView列表上
     * @param tempName
     */
    private void queryData(String tempName) {

        PositionInterviewerAdapter adapter2;
        List<JobObjects> jobObjects2=new ArrayList<>();

        for (int i = 0; i < jobObjects.size(); i++) {

            if (jobObjects.get(i).getAddress().contains(tempName) ||
                    jobObjects.get(i).getJob().contains(tempName) ||
                    jobObjects.get(i).getRealname().contains(tempName)) {
                jobObjects2.add(jobObjects.get(i));
            }

        }


        adapter2 = new PositionInterviewerAdapter(jobObjects2);
        recyclerView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }




}

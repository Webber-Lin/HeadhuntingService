package com.linzhaowei.headhuntingservice.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.linzhaowei.headhuntingservice.data.Applicant;
import com.linzhaowei.headhuntingservice.ApplicantMain;
import com.linzhaowei.headhuntingservice.utils.Ipv4;
import com.linzhaowei.headhuntingservice.data.JobInformation;
import com.linzhaowei.headhuntingservice.R;
import com.linzhaowei.headhuntingservice.adapter.PositionAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * 求职者查看职位信息界面
 */
public class PositionFragment extends Fragment {
    View view;


    private String id[];

    ApplicantMain applicantMain;
    Applicant applicant;

    RecyclerView recyclerView;

    private List<JobInformation> jobInformations = new ArrayList<>();

    private final int GETAllJOBINFORMATIONS = 1;

    PositionAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_position_interviewer, container, false);
        recyclerView = view.findViewById(R.id.position_interviewer);
        applicantMain = (ApplicantMain) getActivity();
        applicant = applicantMain.getApplicant();


        getAllJobInformation();


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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GETAllJOBINFORMATIONS:
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
    private void getAllJobInformation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    String str = Ipv4.ipv4 + "/headhuntingservice/getAllJobInformation.php";

                    Request request = new Request.Builder()
                            .url(str)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    if (!responseData.equals("")) {
                        JSONArray jsonArray = new JSONArray(responseData);

                        id = new String[jsonArray.length()];


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonData = jsonArray.getJSONObject(i);
                            id[i] = jsonData.get("id").toString();
                        }

                        for (int i = 0; i < id.length; i++) {
                            JobInformation addjob = new JobInformation(id[i]);
                            jobInformations.add(addjob);
                        }

                        Message message = new Message();
                        message.what = GETAllJOBINFORMATIONS;
                        handler.sendMessage(message);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 展示
     */
    private void show() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.applicantMain);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PositionAdapter(jobInformations);
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
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
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

        PositionAdapter adapter2;
        List<JobInformation> jobInformations2 = new ArrayList<>();

        for (int i = 0; i < jobInformations.size(); i++) {

            if (jobInformations.get(i).getAddress().contains(tempName) ||
                    jobInformations.get(i).getJob().contains(tempName) ||
                    jobInformations.get(i).getRealname().contains(tempName)) {
                jobInformations2.add(jobInformations.get(i));
            }

        }



        adapter2 = new PositionAdapter(jobInformations2);
        recyclerView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }



}
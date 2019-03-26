package com.linzhaowei.headhuntingservice.data;


import com.linzhaowei.headhuntingservice.utils.Ip;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 求职者类
 */

public class Interviewer implements Serializable {
    private String id;               //id
    private String username;         //用户名
    private String realname;         //真实姓名
    private String company;            //公司
    private String industry;           //所属行业
    private String company_address;     //公司地址
    private String email;              //E_mail
    private String tel;              //电话



    public Interviewer(String username){
        this.username=username;
        getAllInf();
    }

    /**
     * 获取所有信息
     */
    private void getAllInf(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String str= Ip.ip+"/headhuntingservice/getallinf.php?username="+ username;
                    Request request = new Request.Builder()
                            .url(str)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    String responseData=response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonData = jsonArray.getJSONObject(i);
                        id = jsonData.get("id").toString();
                        realname =  jsonData.get("realname").toString();
                        company = jsonData.get("company").toString();
                        industry =  jsonData.get("industry").toString();
                        company_address = jsonData.get("company_address").toString();
                        email =  jsonData.get("e_mail").toString();
                        tel =  jsonData.get("telephone").toString();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRealname() {
        return realname;
    }

    public String getCompany() {
        return company;
    }

    public String getIndustry() {
        return industry;
    }

    public String getCompany_address() {
        return company_address;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }
}

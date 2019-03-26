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

public class Applicant implements Serializable {
    private String id;                   //id
    private String username;          //用户名
    private String realname;          //真实姓名
    private String sex;               //性别
    private String email;             //E_mail
    private String education;         //学历
    private String tel;                //电话
    private String native_place;      //籍贯


    public Applicant(String username){
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
                        sex = jsonData.get("sex").toString();
                        email =  jsonData.get("e_mail").toString();
                        education = jsonData.get("education").toString();
                        tel =  jsonData.get("telephone").toString();
                        native_place =  jsonData.get("native_place").toString();
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

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getEducation() {
        return education;
    }

    public String getTel() {
        return tel;
    }

    public String getNative_place() {
        return native_place;
    }
}

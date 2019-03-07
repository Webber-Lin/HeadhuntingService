package com.linzhaowei.headhuntingservice.data;

import com.linzhaowei.headhuntingservice.utils.Ipv4;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 职位信息类
 */
public class JobInformation {
    private String id;
    private String username;
    private String realname;
    private String job;
    private String address;
    private String salary;
    private String text;


    public JobInformation(String id) {
        this.id = id;
        setJobInformation();

    }


    /**
     * 设置所有职位信息成员变量
     */
    private void setJobInformation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    String str = Ipv4.ipv4 + "/headhuntingservice/setJobInformation.php?id=" + id;

                    Request request = new Request.Builder()
                            .url(str)
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();


                    if (!responseData.equals("")) {
                        JSONArray jsonArray = new JSONArray(responseData);

                        JSONObject jsonData = jsonArray.getJSONObject(0);
                        username = jsonData.get("username").toString();
                        job = jsonData.get("job").toString();
                        address = jsonData.get("address").toString();
                        salary = jsonData.get("salary").toString();
                        text = jsonData.get("text").toString();


                        setRealname();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void setRealname() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    String str = Ipv4.ipv4 + "/headhuntingservice/setRealname2.php?username=" + username;

                    Request request = new Request.Builder()
                            .url(str)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    if (!responseData.equals("")) {

                        realname = responseData;

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

    public String getJob() {
        return job;
    }

    public String getAddress() {
        return address;
    }

    public String getSalary() {
        return salary;
    }

    public String getText() {
        return text;
    }


}

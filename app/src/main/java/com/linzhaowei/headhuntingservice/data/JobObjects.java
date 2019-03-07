package com.linzhaowei.headhuntingservice.data;


import com.linzhaowei.headhuntingservice.utils.Ipv4;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 求职意向类
 */
public class JobObjects implements Serializable {
    private String id;
    private String username;
    private String realname;
    private String job;
    private String address;

    public JobObjects(String id,String job,String address){
        this.id=id;
        this.job=job;
        this.address=address;
    }

    public JobObjects(String id){
        this.id=id;
        setJobObjects();
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


    /**
     * 设置所有求职者成员变量
     */
    private void setJobObjects(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();

                    String str = Ipv4.ipv4+"/headhuntingservice/setJobObjects.php?id="+id;

                    Request request=new Request.Builder()
                            .url(str)
                            .build();

                    okhttp3.Response response=client.newCall(request).execute();
                    String responseData=response.body().string();


                    if(!responseData.equals("")){
                        JSONArray jsonArray = new JSONArray(responseData);

                        JSONObject jsonData = jsonArray.getJSONObject(0);
                        username=jsonData.get("username").toString();
                        job = jsonData.get("job").toString();
                        address =  jsonData.get("address").toString();


                        setRealname();

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void setRealname() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();

                    String str = Ipv4.ipv4+"/headhuntingservice/setRealname.php?username="+username;

                    Request request=new Request.Builder()
                            .url(str)
                            .build();

                    okhttp3.Response response=client.newCall(request).execute();
                    String responseData=response.body().string();

                    if(!responseData.equals("")){

                        realname=responseData;

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

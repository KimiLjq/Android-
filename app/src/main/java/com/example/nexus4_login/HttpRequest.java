package com.example.nexus4_login;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequest {

    public String loginRequest(String userName,String password){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        try{
            json.put("username",userName);
            json.put("password",password);
        }catch (Exception e){
            return null;
        }
       ;

       /* RequestBody formBody = new FormBody.Builder()
                .add("username",userName)
                .add("password",password)
                .build();*/

        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
        //发起请求
        final Request request = new Request.Builder()
                .url("http://172.16.83.38:8080/fastStep/login.action")
                .post(requestBody)
                .build();

                OkHttpClient client = new OkHttpClient();
                try {
                    Response response = client.newCall(request).execute();
                    return response.body().string();

                }
                catch (IOException e) {
                    e.printStackTrace();
                }

        return null;
    }
}

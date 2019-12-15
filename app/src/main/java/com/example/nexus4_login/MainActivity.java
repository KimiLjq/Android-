package com.example.nexus4_login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText account = null;
    private EditText password = null;
    private CheckBox isRemPwd = null;
    private CheckBox isAutoLogin = null;
    private Button login = null;
    private SharedPreferences sp = null;
    private HttpRequest httpRequest = null;

    private String loginReslt = null;

   /* Handler handler = new Handler() {
          @Override
          public void handleMessage(Message msg) {
              if (msg.what == 1) {
                  Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                  //保存登录的状态和用户名
                  saveLoginStatus(true, userName);
                  //登录成功的状态保存到MainActivity
                  Intent intent = new Intent();
                  intent.putExtra("isLogin", true);
                  setResult(RESULT_OK, intent);
                  LoginActivity.this.finish();
                  }else {
                  Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                  }
          }
     };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setListener();
        System.out.println(loginReslt);
    }

    private void init(){
        account = findViewById(R.id.loginAccount);
        password = findViewById(R.id.loginPassword);
        isRemPwd = findViewById(R.id.remPassword);
        isAutoLogin = findViewById(R.id.autoLogin);
        login = findViewById(R.id.login);
        sp = this.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);

        httpRequest = new HttpRequest();
    }

    private void setListener(){

        if(!sp.getBoolean("isRemPwd",false)){
            sp.edit().putBoolean("isAutoLogin",false).commit();
        }
        if(sp.getBoolean("isRemPwd",false)){
            isRemPwd.setChecked(true);
            account.setText(sp.getString("account",""));
            password.setText(sp.getString("password",""));
            if(sp.getBoolean("isAutoLogin",false)){
                isAutoLogin.setChecked(true);
                Toast.makeText(this,"登录成功",Toast.LENGTH_LONG);
            }
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"登录",Toast.LENGTH_LONG);
               if(isRemPwd.isChecked()){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("account",account.getText().toString());
                    editor.putString("password",password.getText().toString());
                    editor.commit();
                }

                final String userName = account.getText().toString();
                final String psw = password.getText().toString();
                new Thread() {
                         @Override
                         public void run() {
                             //传递参数获取响应内容
                             String result = httpRequest.loginRequest(userName, psw);
                             //判断数据库查询出来值是否与输入的值相等
                             if (result != null && !result.isEmpty()){
                                 loginReslt = result;
                             }
                             else {
                                 Log.e("LoginResult","登录错误");
                             }
                         }
               }.start();

            }
        });

        isRemPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isRemPwd.isChecked()){
                    sp.edit().putBoolean("isRemPwd",true).commit();
                }
                else{
                    sp.edit().putBoolean("isRemPwd",false).commit();
                }
            }
        });

        isAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isAutoLogin.isChecked()){
                    Log.i("*********information","自动登录已选中");
                    sp.edit().putBoolean("isAutoLogin",true).commit();
                }
                else{
                    Log.i("#########information","自动登录未选中");
                    sp.edit().putBoolean("isAutoLogin",false).commit();
                }
            }
        });


    }


    public void forgetPassword(View view){
        startActivity(new Intent(MainActivity.this,ForgetPassword.class));
    }

    public void register(View view){
        startActivity(new Intent(MainActivity.this, register.class));
    }
}

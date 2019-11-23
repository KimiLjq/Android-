package com.example.nexus4_login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText account = null;
    private EditText password = null;
    private CheckBox isRemPwd = null;
    private CheckBox isAutoLogin = null;
    private Button login = null;
    private SharedPreferences sp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setListener();
    }

    private void init(){
        account = findViewById(R.id.loginAccount);
        password = findViewById(R.id.loginPassword);
        isRemPwd = findViewById(R.id.remPassword);
        isAutoLogin = findViewById(R.id.autoLogin);
        login = findViewById(R.id.login);
        sp = this.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
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
            }
        });

        isRemPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isRemPwd.isChecked()){
                    Log.i("********information","记住密码已选中");
                    sp.edit().putBoolean("isRemPwd",true).commit();
                }
                else{
                    Log.i("#########information","记住密码未选中");
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

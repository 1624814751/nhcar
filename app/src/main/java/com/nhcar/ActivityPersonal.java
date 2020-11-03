package com.nhcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ActivityPersonal extends AppCompatActivity {
    // 声明控件、对象
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();
    private Gson gson = new Gson();  //JSON转换工具

    private Button personal_reg_button,personal_login_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_personal);

        initView();
        initData();
        initListener();

    }// onCreate End
    private void initView() {
        personal_reg_button=this.findViewById(R.id.personal_reg_button);//个人中心注册按键
        personal_login_button=this.findViewById(R.id.personal_login_button);//个人中心登录按键
    }
    private void initData() {

    }
    private void initListener() {
        personal_reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ActivityMyResgister.class);
                startActivity(intent);
            }
        });
        personal_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ActivityMyLogin.class);
                startActivity(intent);
            }
        });

    }
}

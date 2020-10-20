package com.nhcar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nhcar.ActivityPoductList0;
import com.nhcar.R;
import com.nhcar.adapter.CarNewsAdapter;
import com.nhcar.entity.ECarNewsResult;
import com.nhcar.entity.ECategory;
import com.nhcar.entity.EUser;
import com.nhcar.utils.Const;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
                Intent intent=new Intent(getApplicationContext(),ActivityMyResgister.class);
                startActivity(intent);
            }
        });
        personal_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ActivityMyLogin.class);
                startActivity(intent);
            }
        });

    }
}

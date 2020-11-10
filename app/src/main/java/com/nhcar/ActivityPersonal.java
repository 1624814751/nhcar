package com.nhcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

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
    private SharedPreferences sp;   //声明读取登录者信息的共享对象

    private Button personal_reg_button,personal_login_button,personal_change_button;
    private TextView personal_welcome;

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
        personal_welcome=this.findViewById(R.id.personal_welcome);//个人中心欢迎语
        personal_change_button=this.findViewById(R.id.personal_change_button);//个人中心修改信息按键
    }
    private void initData() {
        sp=this.getSharedPreferences("nhcarsp",MODE_PRIVATE);

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
                if (personal_login_button.getText().toString().equals("登录")){
                    Intent intent=new Intent(getApplicationContext(), ActivityMyLogin.class);
                    startActivity(intent);
                }else{
                    //注销
                    SharedPreferences.Editor editor=sp.edit();
                    editor.remove("isLogin");
                    editor.remove("loginUserName");
                    editor.remove("loginUserID");
                    editor.commit();
                    checkLogin();
                }
            }
        });
        personal_change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ActivityChangeMessage.class);
                startActivity(intent);
            }
        });
    }
    private void checkLogin(){
        Boolean isLogin=sp.getBoolean("isLogin",false);//读取是否登录
        String uname=sp.getString("loginUserName","");//读取是否登录账号
        if (isLogin){
            personal_login_button.setText("注销");
            personal_welcome.setText(uname+",欢迎来到南华汽车商城!");
            personal_change_button.setVisibility(View.VISIBLE);
        }else {
            personal_login_button.setText("登录");
            personal_welcome.setText("欢迎来到南华汽车商城!");
            personal_change_button.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }
}

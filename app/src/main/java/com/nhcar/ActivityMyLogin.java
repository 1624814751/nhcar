package com.nhcar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nhcar.entity.EUser;
import com.nhcar.entity.EUserResult;
import com.nhcar.utils.Const;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityMyLogin extends AppCompatActivity {
    // 声明控件、对象
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();
    private Gson gson = new Gson();  //JSON转换工具

    private EditText loginaccount,loginpassword;
    private Button personal_back_button,login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initView();
        initData();
        initListener();

    }// onCreate End
    private void initView() {
        loginaccount=this.findViewById(R.id.loginaccount);//个人中心登录页账号名
        loginpassword=this.findViewById(R.id.loginpassword);//个人中心登录页密码
        personal_back_button=this.findViewById(R.id.personal_back_button);//个人中心登录页返回按键
        login=this.findViewById(R.id.login);//个人中心登录页登录按键
        register=this.findViewById(R.id.register);//个人中心登录页免费注册按键

    }
    private void initData() {
        registers();
    }
    private void initListener() {
        personal_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registers();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ActivityMyResgister.class);
                startActivity(intent);
            }
        });
    }
    private void registers(){
        //先检查填写内容是否正确
        String uname=loginaccount.getText().toString().trim();
        if (uname.equals("")){
            Toast.makeText(this,"账号不能空",Toast.LENGTH_SHORT).show();
            return;
        }
        String psw=loginpassword.getText().toString().trim();
        if (uname.equals("")){
            Toast.makeText(this,"密码不能空",Toast.LENGTH_SHORT).show();
            return;
        }
        EUser user=new EUser();
        user.setUname(uname);
        user.setUpwd(psw);

        String url= Const.SERVLET_URL+Const.SERVLET_URL+"addUser";
        Log.d("<<<<<url>>>",url);

        FormBody.Builder fromBody=new FormBody.Builder();//表单参数对象
        String userJson=gson.toJson(user);
        String userJsonEncode="";
        try {
            userJsonEncode= URLEncoder.encode(userJson,"utf-8");
            Log.d("<<<<<userJson>>>",userJsonEncode);
        }catch (Exception e){
            Toast.makeText(this,"数据编码错误",Toast.LENGTH_SHORT).show();
            return;
        }
        fromBody.add("userstr",userJsonEncode);//参数1，还可以添加更多参数

        Request request = new Request.Builder()
                .url(url)
                .post(fromBody.build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("<<register返回:>>", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();//接收接口返回的内容
                Log.d("<<register返回:>>", result);
                final EUserResult eUserResult=gson.fromJson(result,EUserResult.class);

                //在UI线程中更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此处更新UI:数据绑定到GridView
                        Toast.makeText(ActivityMyLogin.this,eUserResult.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

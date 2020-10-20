package com.nhcar;

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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityMyResgister extends AppCompatActivity {
    // 声明控件、对象
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();
    private Gson gson = new Gson();  //JSON转换工具

    private EditText account0,email0,psw0,repsw0;
    private Button login,productback;

    private List<EUserResult> listuser=new ArrayList<EUserResult>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_normal0);

        initView();
        initData();
        initListener();

    }// onCreate End
    private void initView() {
        productback=this.findViewById(R.id.productback);//注册页返回个人中心
        account0=this.findViewById(R.id.account0);//手机号
        email0=this.findViewById(R.id.email0);//邮箱账号
        psw0=this.findViewById(R.id.psw0);//输入密码
        repsw0=this.findViewById(R.id.repsw0);//输入确认密码
        login=this.findViewById(R.id.login);//注册按键
    }
    private void initData() {
        registers();
    }
    private void initListener() {
        productback.setOnClickListener(new View.OnClickListener() {
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
    }
    private void registers(){
        //先检查填写内容是否正确
        String uname=account0.getText().toString().trim();
        if (uname.equals("")){
            Toast.makeText(this,"账号不能空",Toast.LENGTH_SHORT).show();
            return;
        }
        String email=email0.getText().toString().trim();
        if (uname.equals("")){
            Toast.makeText(this,"Email不能空",Toast.LENGTH_SHORT).show();
            return;
        }
        String psw=psw0.getText().toString().trim();
        if (uname.equals("")){
            Toast.makeText(this,"密码不能空",Toast.LENGTH_SHORT).show();
            return;
        }
        String repsw=repsw0.getText().toString().trim();
        if (!repsw.equals(psw)){
            Toast.makeText(this,"再次密码不同",Toast.LENGTH_SHORT).show();
            return;
        }
        EUser user=new EUser();
        user.setUname(uname);
        user.setUemail(email);
        user.setUpwd(psw);
        user.setUrepwd(repsw);
        user.setUid(0);
        user.setUlogo("");

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
                        Toast.makeText(ActivityMyResgister.this,eUserResult.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

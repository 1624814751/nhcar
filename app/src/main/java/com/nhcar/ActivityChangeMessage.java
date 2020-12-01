package com.nhcar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
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

public class ActivityChangeMessage extends Activity {
    // 声明控件、对象
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();
    private Gson gson = new Gson();  //JSON转换工具
    private SharedPreferences sp;   //声明读取登录者信息的共享对象
    private EUser user;

    private Button change_message_productback, change_message;
    private TextView change_account, change_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_changemessage);

        initView();
        initData();
        initListener();

    }// onCreate End

    private void initView() {
        change_message_productback = this.findViewById(R.id.change_message_productback);//注册页返回个人中心
        change_account = this.findViewById(R.id.change_account);//账号
        change_email = this.findViewById(R.id.change_email);//邮箱账号
//        change_old_psw = this.findViewById(R.id.change_old_psw);//输入旧密码
//        change_new_psw = this.findViewById(R.id.change_new_psw);//输入新密码
//        change_repsw = this.findViewById(R.id.change_repsw);//确认输入的新密码
        change_message = this.findViewById(R.id.change_message);//确认修改按键


    }

    private void initData() {
        // Nmessage();
        sp = this.getSharedPreferences("nhcarsp", MODE_PRIVATE);
        getAndShowMyInfo();
    }

    private void initListener() {
        change_message_productback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        change_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nmessage();
            }
        });
    }

    private void Nmessage() {
        //先检查填写内容是否正确
        String email = change_email.getText().toString().trim();
        if (!email.matches("^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+.[a-zA-Z]{2,3}$")) {
            Toast.makeText(this, "邮箱格式不对", Toast.LENGTH_SHORT).show();
            return;
        }


        user.setUemail(email);//修改邮箱

      //调用接口进行注册
        String url = Const.SERVER_URL + Const.SERVLET_URL + "updateUsers";
        Log.d("<<<<<url>>>", url);

        FormBody.Builder fromBody = new FormBody.Builder();//表单参数对象
        String userJson = gson.toJson(user);
        String userJsonEncode = "";
        try {
            userJsonEncode = URLEncoder.encode(userJson, "utf-8");
            Log.d("<<<<<userJson>>>", userJsonEncode);
        } catch (Exception e) {
            Toast.makeText(this, "数据编码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        fromBody.add("userstr", userJsonEncode);//参数1，还可以添加更多参数

        Request request = new Request.Builder()
                .url(url)
                .post(fromBody.build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("<<change返回值:>>", e.getMessage());
            }

            //
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();//接收接口返回的内容
                Log.d("<<change返回值:>>", result);


                final EUserResult eUserResult = gson.fromJson(result, EUserResult.class);

                //在UI线程中更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此处更新UI:数据绑定到GridView
                        if (eUserResult.getResult() == 1) {
                            Intent intent = new Intent(getApplicationContext(), ActivityPersonal.class);
                            startActivity(intent);
                            Toast.makeText(ActivityChangeMessage.this, "修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ActivityChangeMessage.this, eUserResult.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                });
            }
        });
    }


    private void getAndShowMyInfo() {
        Boolean isLogin = sp.getBoolean("isLogin", false);
        int uid = sp.getInt("loginUserID", 0);
        if (!isLogin || uid <= 0) {
            Toast.makeText(ActivityChangeMessage.this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = Const.SERVER_URL + Const.SERVLET_URL + "getUserInfoByUid";
        Log.d("<<<<<url>>>", url);

        FormBody.Builder fromBody = new FormBody.Builder();//表单参数对象
        fromBody.add("uid", String.valueOf(uid));//参数1，还可以添加更多参数


        String userJson = gson.toJson(uid);
        String userJsonEncode = "";
        try {
            userJsonEncode = URLEncoder.encode(userJson, "utf-8");
            Log.d("<<<<<userJson>>>", userJsonEncode);
        } catch (Exception e) {
            Toast.makeText(ActivityChangeMessage.this, "数据编码错误", Toast.LENGTH_SHORT).show();
            return;
        }


        Request request = new Request.Builder()
                .url(url)
                .post(fromBody.build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("<<change返回值:>>", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();//接收接口返回的内容
                Log.d("<<change返回值:>>", result);

                //"result":1,",msg":注册成功
                user = gson.fromJson(result, EUser.class);

                //在UI线程中更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此处更新UI:数据绑定到GridView
                        if (user != null) {
                            change_account.setText(user.getUname());
                            change_account.setKeyListener(null);//设置输入文本框不可更改
                            change_email.setText(user.getUemail());
//                          change_old_psw.setText(user.getUpwd());
                        } else {
                            Toast.makeText(ActivityChangeMessage.this, "当前用户信息无法获取", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
        });
    }

}








package com.nhcar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class ActivityMyResgister2 extends AppCompatActivity {
    // 声明控件、对象
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();
    private Gson gson = new Gson();  //JSON转换工具

    private EditText account0, email0, psw0, repsw0;
    private Button login, productback;
    // private String userName, psw, pswAgain;//用户名，密码，再次输入的密码的控件的获取值

    private List<EUserResult> listuser = new ArrayList<EUserResult>();

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
        productback = this.findViewById(R.id.productback);//注册页返回个人中心
        account0 = this.findViewById(R.id.account0);//手机号
        email0 = this.findViewById(R.id.email0);//邮箱账号
        psw0 = this.findViewById(R.id.psw0);//输入密码
        repsw0 = this.findViewById(R.id.repsw0);//输入确认密码
        login = this.findViewById(R.id.login);//注册按键
    }

    private void initData() {
        register();

    }

//    private void registers() throws IOException {
//        registers();
//        EUser eUser = getUser();
//        addUser(eUser);
//    }


    private void initListener() {
        productback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                registers();
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
//                try {
//                    registers();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    private void register() {
        String uname = account0.getText().toString().trim();
        if (TextUtils.isEmpty(uname)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = email0.getText().toString().trim();
        if (!email.matches("^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+.[a-zA-Z]{2,3}$")) {
            Toast.makeText(this, "邮箱格式不对", Toast.LENGTH_SHORT).show();
            return;
        }
        String psw = psw0.getText().toString().trim();
        if (TextUtils.isEmpty(psw)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        String repsw = repsw0.getText().toString().trim();
        if (!repsw.equals(psw)) {
            Toast.makeText(this, "再次密码不同", Toast.LENGTH_SHORT).show();
            return;
        }

        EUser user = new EUser();
        user.setUname(uname);
        user.setUemail(email);
        user.setUpwd(psw);
        user.setUrepwd(repsw);
        user.setUid(0);
        user.setUlogo("");

        String url = Const.SERVER_URL + Const.SERVLET_URL + "addUser";
        String userJson=gson.toJson(user);
        String userJsonEncode = "";
        try {
            userJsonEncode = URLEncoder.encode(userJson, "utf-8");
            Log.d("<<<<<userJson>>>", userJsonEncode);
        } catch (Exception e) {
            Toast.makeText(this, "数据编码错误", Toast.LENGTH_SHORT).show();
            return;
        }

        FormBody.Builder fromBody = new FormBody.Builder();//表单参数对象

        Request request = new Request.Builder()
                .url(url)
                .post(fromBody.build())
//                        .post(RequestBody.create(MediaType.parse("application/json"), gson.toJson(eUser)))
                .build();


        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            private ActivityMyResgister2 activityMyResgister2;

            public ActivityMyResgister2 getActivityMyResgister2() {
                return activityMyResgister2;
            }

            public Callback setActivityMyResgister(ActivityMyResgister2 activityMyResgister2) {
                this.activityMyResgister2 = activityMyResgister2;
                return this;
            }

            @Override
            public void onFailure(Call call1, IOException e) {

            }

            @Override
            public void onResponse(Call call1, Response response) throws IOException {
                String result = response.body().string();//接收接口返回的内容
                final EUserResult eUserResult = gson.fromJson(result, EUserResult.class);
                if (eUserResult.getResult() == 1) {
                    Toast.makeText(activityMyResgister2, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ActivityMyLogin2.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(activityMyResgister2, "注册失败", Toast.LENGTH_SHORT).show();
                }

            }
        }.setActivityMyResgister(this));
    }

}

//    private EUser getUser() {
//        String uname = account0.getText().toString().trim();
//        if (TextUtils.isEmpty(uname)) {
//            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
//            return null;
//        }
//        String email = email0.getText().toString().trim();
//        if (!email.matches("^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+.[a-zA-Z]{2,3}$")) {
//            Toast.makeText(this, "邮箱格式不对", Toast.LENGTH_SHORT).show();
//            return null;
//        }
//        String psw = psw0.getText().toString().trim();
//        if (TextUtils.isEmpty(psw)) {
//            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
//            return null;
//        }
//        String repsw = repsw0.getText().toString().trim();
//        if (!repsw.equals(psw)) {
//            Toast.makeText(this, "再次密码不同", Toast.LENGTH_SHORT).show();
//            return null;
//        }
//
//        EUser user = new EUser();
//        user.setUname(uname);
//        user.setUemail(email);
//        user.setUpwd(psw);
//        user.setUrepwd(repsw);
//        user.setUid(0);
//        user.setUlogo("");
//
//        return user;
//    }

//   ===============================================方法1====================================
//    private void registers(){
//    //先检查填写内容是否正确
//        String uname=account0.getText().toString().trim();
//        if (uname.equals("")){
//            Toast.makeText(this,"账号不能空",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        String email=email0.getText().toString().trim();
//        if (uname.equals("")){
//            Toast.makeText(this,"Email不能空",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        String psw=psw0.getText().toString().trim();
//        if (uname.equals("")){
//            Toast.makeText(this,"密码不能空",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        String repsw=repsw0.getText().toString().trim();
//        if (!repsw.equals(psw)){
//            Toast.makeText(this,"再次密码不同",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        EUser user=new EUser();
//        user.setUname(uname);
//        user.setUemail(email);
//        user.setUpwd(psw);
//        user.setUrepwd(repsw);
//        user.setUid(0);
//        user.setUlogo("");
//
//        String url= Const.SERVLET_URL+Const.SERVLET_URL+"addUser";
//        Log.d("<<<<<url>>>",url);
//
//        FormBody.Builder fromBody=new FormBody.Builder();//表单参数对象
//        String userJson=gson.toJson(user);
//        String userJsonEncode="";
//        try {
//            userJsonEncode= URLEncoder.encode(userJson,"utf-8");
//            Log.d("<<<<<userJson>>>",userJsonEncode);
//        }catch (Exception e){
//            Toast.makeText(this,"数据编码错误",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        fromBody.add("userstr",userJsonEncode);//参数1，还可以添加更多参数
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(fromBody.build())
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("<<register返回:>>", e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();//接收接口返回的内容
//                Log.d("<<register返回:>>", result);
//                final EUserResult eUserResult=gson.fromJson(result,EUserResult.class);
//
//                //在UI线程中更新
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //此处更新UI:数据绑定到GridView
//                        Toast.makeText(ActivityMyResgister.this,eUserResult.getMsg(),Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }
//==============================方法2==================================
//    private void registers() {
//      获取输入在相应控件中的字符串
//        getEditString();
//        //判断输入框内容
//        if (TextUtils.isEmpty(userName)) {
//            Toast.makeText(ActivityMyResgister.this, "请输入用户名", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(psw)) {
//            Toast.makeText(ActivityMyResgister.this, "请输入密码", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        //创建验证邮箱格式的正则表达式
//        String email = email0.getText().toString().trim();
//        if (!email.matches("^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+.[a-zA-Z]{2,3}$")) {
//            Toast.makeText(this, "邮箱格式不对", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(pswAgain)) {
//            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (!psw.equals(pswAgain)) {
//            Toast.makeText(this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
//            return;
//            /**
//             *从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
//             */
//        } else if (isExistUserName(userName)) {
//            Toast.makeText(this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
//            return;
//        } else {
//            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
//            //把账号、密码和账号标识保存到sp里面
//            /**
//             * 保存账号和密码到SharedPreferences中
//             */
//            saveRegisterInfo(userName, psw);
//            //注册成功后把账号传递到LoginActivity.java中
//            // 返回值到loginActivity显示
//            Intent data = new Intent();
//            data.putExtra("userName", userName);
//            setResult(RESULT_OK, data);
//            //RESULT_OK为Activity系统常量，状态码为-1，
//            // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
//            ActivityMyResgister.this.finish();
//        }
//    }
//
//
//    /**
//     * 获取控件中的字符串
//     */
//    private void getEditString() {
//        userName = account0.getText().toString().trim();
//        psw = psw0.getText().toString().trim();
//        pswAgain = repsw0.getText().toString().trim();
//    }
//
//    /**
//     * 从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
//     */
//    private boolean isExistUserName(String userName) {
//        boolean has_userName = false;
//        //mode_private SharedPreferences sp = getSharedPreferences( );
//        // "loginInfo", MODE_PRIVATE
//        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        //获取密码
//        String spPsw = sp.getString(userName, "");//传入用户名获取密码
//        //如果密码不为空则确实保存过这个用户名
//        if (!TextUtils.isEmpty(spPsw)) {
//            has_userName = true;
//        }
//        return has_userName;
//    }
//
//    /**
//     * 保存账号和密码到SharedPreferences中SharedPreferences
//     */
//    private void saveRegisterInfo(String userName, String psw) {
//        String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
//        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
//        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
//        SharedPreferences.Editor editor = sp.edit();
//        //以用户名为key，密码为value保存在SharedPreferences中
//        //key,value,如键值对，editor.putString(用户名，密码）;
//        editor.putString(userName, md5Psw);
//        //提交修改 editor.commit();
//        editor.commit();
//    }
//
//}
//    ================================我是分界线（方法3）============================================
//       先检查填写内容是否正确
//    private void addUser(EUser eUser) throws IOException {
//        String url = Const.SERVLET_URL + Const.SERVLET_URL + "addUser";
//        FormBody.Builder fromBody = new FormBody.Builder();//表单参数对象
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(fromBody.build())
////                        .post(RequestBody.create(MediaType.parse("application/json"), gson.toJson(eUser)))
//                .build();
//
//
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            private ActivityMyResgister activityMyResgister;
//
//            public ActivityMyResgister getActivityMyResgister() {
//                return activityMyResgister;
//            }
//
//            public Callback setActivityMyResgister(ActivityMyResgister activityMyResgister) {
//                this.activityMyResgister = activityMyResgister;
//                return this;
//            }
//
//            @Override
//            public void onFailure(Call call1, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call1, Response response) throws IOException {
//                String result = response.body().string();//接收接口返回的内容
//                final EUserResult eUserResult = gson.fromJson(result, EUserResult.class);
//                if (eUserResult.getResult() == 1) {
//                    Toast.makeText(activityMyResgister, "注册成功", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), ActivityMyLogin.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(activityMyResgister, "注册失败", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }.setActivityMyResgister(this));
//    }







package com.nhcar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nhcar.utils.Const;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityMyLogin2 extends AppCompatActivity {
    // 声明控件、对象
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();
    private Gson gson = new Gson();  //JSON转换工具

    private EditText loginaccount, loginpassword;
    private Button personal_back_button, login, register;
    private ToggleButton isShowPassword;
    // private String userName, psw, spPsw;//获取的用户名，密码，加密密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initView();
        initListener();

    }// onCreate End

    private void initView() {
        loginaccount = this.findViewById(R.id.loginaccount);//个人中心登录页账号名
        loginpassword = this.findViewById(R.id.loginpassword);//个人中心登录页密码
        personal_back_button = this.findViewById(R.id.personal_back_button);//个人中心登录页返回按键
        login = this.findViewById(R.id.login);//个人中心登录页登录按键
        register = this.findViewById(R.id.register);//个人中心登录页免费注册按键

    }


    private void initListener() {
        personal_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public ActivityMyLogin2 getActivityMyLogin2() {
                return activityMyLogin2;
            }

            public View.OnClickListener setActivityMyLogin(ActivityMyLogin2 activityMyLogin2) {
                this.activityMyLogin2 = activityMyLogin2;
                return this;
            }

            public ActivityMyLogin2 activityMyLogin2;

            @Override
            public void onClick(View v) {
                String uname = loginaccount.getText().toString().trim();
                if (TextUtils.isEmpty(uname)) {
                    Toast.makeText(activityMyLogin2, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                String psw = loginpassword.getText().toString().trim();
                if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(activityMyLogin2, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = Const.SERVLET_URL + Const.SERVLET_URL + "addUser";
                OkHttpClient client = new OkHttpClient();
                final Call call = client.newCall(new Request.Builder().url(url/*"http://10.0.0.2:8080/login?user=" +uname+"&pwd="+psw*/).get().build());
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String body = response.body().string();
                        if ("true".equals(body)) {
                            Toast.makeText(activityMyLogin2,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ActivityPersonal.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(activityMyLogin2,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }.setActivityMyLogin(this));
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityMyResgister2.class);
                startActivity(intent);
            }
        });
//        isShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    loginpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                }else{
//                    loginpassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                }
//            }
//        });
    }

    //=========================================方法2====================================
//    private void registers() {
////开始登录，获取用户名和密码 getText().toString().trim();
//        userName = loginaccount.getText().toString().trim();
//        psw = loginpassword.getText().toString().trim();
//        //对当前用户输入的密码进行MD5加密再进行比对判断, MD5Utils.md5( ); psw 进行加密判断是否一致
//        String md5Psw = MD5Utils.md5(psw);
//        // md5Psw ; spPsw 为 根据从SharedPreferences中用户名读取密码
//        // 定义方法 readPsw为了读取用户名，得到密码
//        spPsw = readPsw(userName);
//        // TextUtils.isEmpty
//        if (TextUtils.isEmpty(userName)) {
//            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (TextUtils.isEmpty(psw)) {
//            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
//            return;
//            // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
//        } else if (md5Psw.equals(spPsw)) {
//            //一致登录成功
//            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
//            //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
//            saveLoginStatus(true, userName);
//            //登录成功后关闭此页面进入主页
//            Intent data = new Intent();
//            //datad.putExtra( ); name , value ;
//            data.putExtra("isLogin", true);
//            //RESULT_OK为Activity系统常量，状态码为-1
//            // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
//            setResult(RESULT_OK, data);
//            //销毁登录界面
//            this.finish();
//            //跳转到主界面，登录成功的状态传递到 MainActivity 中
//            startActivity(new Intent(this, ActivityIndex.class));
//            return;
//        } else if ((spPsw != null && !TextUtils.isEmpty(spPsw) && !md5Psw.equals(spPsw))) {
//            Toast.makeText(this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
//            return;
//        } else {
//            Toast.makeText(this, "此用户名不存在", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    /**
//     * 从SharedPreferences中根据用户名读取密码
//     */
//    private String readPsw(String userName) {
//        //getSharedPreferences("loginInfo",MODE_PRIVATE);
//        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
//        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        //sp.getString() userName, "";
//        return sp.getString(userName, "");
//    }
//
//    /**
//     * 保存登录状态和登录用户名到SharedPreferences中
//     */
//    private void saveLoginStatus(boolean status, String userName) {
//        //saveLoginStatus(true, userName);
//        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
//        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        //获取编辑器
//        SharedPreferences.Editor editor = sp.edit();
//        //存入boolean类型的登录状态
//        editor.putBoolean("isLogin", status);
//        //存入登录状态时的用户名
//        editor.putString("loginUserName", userName);
//        //提交修改
//        editor.commit();
//    }
//
//    /**
//     * 注册成功的数据返回至此
//     *
//     * @param requestCode 请求码
//     * @param resultCode  结果码
//     * @param data        数据
//     */
//    @Override
////显示数据， onActivityResult
////startActivityForResult(intent, 1); 从注册界面中获取数据
////int requestCode , int resultCode , Intent data
//// LoginActivity -> startActivityForResult -> onActivityResult();
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //super.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null) {
//            //是获取注册界面回传过来的用户名
//            // getExtra().getString("***");
//            String userName = data.getStringExtra("userName");
//            if (!TextUtils.isEmpty(userName)) {
//                //设置用户名到 loginaccount 控件
//                loginaccount.setText(userName);
//                //et_user_name控件的setSelection()方法来设置光标位置
//                loginaccount.setSelection(userName.length());
//            }
//        }
//    }
//}

//    ==================================     我是分界线（方法1）       ========================================

//    private void registers() {
//        //先检查填写内容是否正确
//        String uname = loginaccount.getText().toString().trim();
//        if (TextUtils.isEmpty(uname)) {
//            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        String psw = loginpassword.getText().toString().trim();
//        if (TextUtils.isEmpty(psw)) {
//            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        EUser eUser = new EUser();
//        eUser.setUname(uname);
//        eUser.setUpwd(psw);
//
//        String url = Const.SERVLET_URL + Const.SERVLET_URL + "addUser";
//        Log.d("<<<<<url>>>", url);
//
//        FormBody.Builder fromBody = new FormBody.Builder();//表单参数对象
//        String userJson = gson.toJson(eUser);
//        String userJsonEncode = "";
//        try {
//            userJsonEncode = URLEncoder.encode(userJson, "utf-8");
//            Log.d("<<<<<userJson>>>", userJsonEncode);
//        } catch (Exception e) {
//            Toast.makeText(this, "数据编码错误", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        fromBody.add("userstr", userJsonEncode);//参数1，还可以添加更多参数
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
//                final EUserResult eUserResult = gson.fromJson(result, EUserResult.class);
//
//                //在UI线程中更新
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //此处更新UI:数据绑定到GridView
//                        Toast.makeText(ActivityMyLogin.this, eUserResult.getMsg(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

}

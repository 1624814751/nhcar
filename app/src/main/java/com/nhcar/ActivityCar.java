package com.nhcar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhcar.adapter.CartAdapter;
import com.nhcar.entity.ECartltem;
import com.nhcar.entity.EUserResult;
import com.nhcar.utils.Const;

import java.io.IOException;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import loopj.android.image.SmartImage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityCar extends AppCompatActivity {
    // 声明控件、对象
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();
    private Gson gson = new Gson();  //JSON转换工具

    private SharedPreferences sp;
    private LinearLayout cart_linear_notlogin, cart_linear_nonetwork, cart_linear_empty, cart_linear_notempty;
    private Button cart_login, btnorder, cart_market;
    private TextView tvcart, tvtotal;
    private ListView lvcart;

    private List<ECartltem> mListCart = new ArrayList<ECartltem>();
    private CartAdapter cartAdapter;
    private String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cart);


        initView();
        initData();
        initListener();

    }// onCreate End

    protected void onResume() {
        super.onResume();
        checkLogin();
    }

    private void initView() {
        cart_linear_notlogin = this.findViewById(R.id.cart_linear_notlogin);
        cart_linear_nonetwork = this.findViewById(R.id.cart_linear_nonetwork);
        cart_linear_empty = this.findViewById(R.id.cart_linear_empty);
        cart_linear_notempty = this.findViewById(R.id.cart_linear_notempty);
        cart_login = this.findViewById(R.id.cart_login);
        tvcart = this.findViewById(R.id.tvcart);
        btnorder = this.findViewById(R.id.btnorder);
        cart_market = this.findViewById(R.id.cart_market);
        tvtotal = this.findViewById(R.id.tvtotal);
        lvcart = this.findViewById(R.id.lvcart);


    }

    private void initData() {
        sp = this.getSharedPreferences("nhcarsp", MODE_PRIVATE);
        checkLogin();
    }

    private void initListener() {
        cart_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityMyLogin.class);
                startActivity(intent);
            }
        });
        cart_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityIndex.class);
                startActivity(intent);
                finish();
            }
        });
        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityCar.this, "正在调试中，目前暂不支持在线支付，只能货到付款", Toast.LENGTH_SHORT).show();
            }
        });
    }//intListener end

    private void checkLogin() {

        Boolean isLogin = sp.getBoolean("isLogin", false);
        uname = sp.getString("loginUserName", "");
       // int uid = sp.getInt("loginUserId", 0);
        //如果已经登录，则访问购物车接口取购物车数据，如果有数据则显示购物信息，否则显示空的购物车
        if (isLogin) {
            //fillCat();
            tvcart.setText(uname + "的购物车");
            fillCartDetails();
        } else {
            cart_linear_notlogin.setVisibility(View.VISIBLE);
            cart_linear_nonetwork.setVisibility(View.GONE);
            cart_linear_empty.setVisibility(View.GONE);
            cart_linear_notempty.setVisibility(View.GONE);
            tvcart.setText("购物车");
        }
    }//checklOgin end

    public void fillCartDetails(){
        String url= Const.SERVER_URL+Const.SERVLET_URL+"getCarListByUname";

        FormBody.Builder formBody=new FormBody.Builder();
        formBody.add("uname",uname);
        Request request=new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("loginUser返回值:",e.getMessage());
                cart_linear_notlogin.setVisibility(View.GONE);
                cart_linear_nonetwork.setVisibility(View.VISIBLE);
                cart_linear_empty.setVisibility(View.GONE);
                cart_linear_notempty.setVisibility(View.GONE);
                tvcart.setText(uname+"的购物车");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();//接口返回的内容
                Log.d("getCarListByUname返回值",result);

                mListCart=gson.fromJson(result,new TypeToken<List<ECartltem>>() {
                }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mListCart.size()>0){
                            //有购物时
                            cart_linear_notlogin.setVisibility(View.GONE);
                            cart_linear_nonetwork.setVisibility(View.GONE);
                            cart_linear_empty.setVisibility(View.GONE);
                            cart_linear_notempty.setVisibility(View.VISIBLE);
                            tvcart.setText(uname+"的购物车");
                            cartAdapter=new CartAdapter(mListCart,ActivityCar.this);
                            lvcart.setAdapter(cartAdapter);
                            float sum=0;
                            for (int i=0;i<mListCart.size();i++){
                                sum+=mListCart.get(i).getPprice()*mListCart.get(i).getCnum();
                            }
                            tvtotal.setText(String.valueOf(sum)+"万元");
                        }else{
                            //无购物时
                            cart_linear_notlogin.setVisibility(View.GONE);
                            cart_linear_nonetwork.setVisibility(View.GONE);
                            cart_linear_empty.setVisibility(View.VISIBLE);
                            cart_linear_notempty.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}





package com.nhcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nhcar.adapter.CommentAdapter;
import com.nhcar.adapter.ProductListAdapter;
import com.nhcar.adapter.SearchAdapter;
import com.nhcar.entity.EComment;
import com.nhcar.entity.ECommentResult;
import com.nhcar.entity.EProductListResult;
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

public  class ActivityComment extends AppCompatActivity {
    // 声明控件、对象
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)//访问的时间
            .readTimeout(5, TimeUnit.SECONDS)//浏览的时间
            .build();
    private Gson gson = new Gson();//Json工具对象，转换工具

    //声明一个
    private SharedPreferences sp;

    private ListView productlist_listview;
    private Button Conmmenttback, CommentMore;
    private TextView conmment_date,conmment_content;
    private int pageNo = 0;//当前页码
    private List<EComment> listProduct = new ArrayList<>();//存放信息，当前页累加
    private CommentAdapter commentAdapter;//数据适配器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conmment);

        initView();
        initData();
        initListener();

    }// onCreate End

    private void initView() {
        productlist_listview = this.findViewById(R.id.productlist_listview);
        Conmmenttback = this.findViewById(R.id.Conmmenttback);
        CommentMore = this.findViewById(R.id.CommentMore);
        conmment_date=this.findViewById(R.id.conmment_date);
        conmment_content=this.findViewById(R.id.conmment_content);

    }

    private void initData() {
        Intent intent=getIntent();
        int pid=intent.getIntExtra("pid",0);
        loadProductList(pid);
    }

    private void initListener() {
        Conmmenttback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityIndex.class);
                startActivity(intent);
                finish();
            }
        });
        CommentMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                int pid=intent.getIntExtra("pid",0);
                loadMoreProductList(pid);
            }
        });
    }
    //显示第一页
    private void loadProductList(int pid) {

        String url = Const.SERVER_URL + Const.SERVLET_URL + "getCommentListByPid";
        pageNo = 1;//显示第一页
        Log.d("<<<<>>>", url);

        FormBody.Builder formBody = new FormBody.Builder();//表单参数对象

        formBody.add("pid", String.valueOf(pid));//参数1，还可以添加更多参数
        formBody.add("pageno", String.valueOf(pageNo));
        formBody.add("pagesize", "6");

        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        Call call = okHttpClient.newCall(request);
        //失败后返回的方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("getNewsByPage返回", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();//接收接口返回的内容
                Log.d("getNewsListByPage返回", result);
                //将接口返回的JSON数据还原为List<E>
                ECommentResult eCommentResult = gson.fromJson(result, ECommentResult.class);
                listProduct = eCommentResult.getDataResult();
                Log.d("<<<<json 还原为List后的行数>>>", listProduct.size() + "");
                Log.d("<<listCarNews>>>", gson.toJson(listProduct));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //更新UI
                        commentAdapter = new CommentAdapter(listProduct,getApplicationContext());
                        productlist_listview.setAdapter(commentAdapter);


                    }
                });
            }
        });
    }

    //加载更多
    private void loadMoreProductList(int pid){
        String url=Const.SERVER_URL+Const.SERVLET_URL+"getCommentListByPid";
        pageNo++;
        Log.d("<<<<<>>>",url);

        FormBody.Builder formBody = new FormBody.Builder();//表单参数对象
        formBody.add("pid", String.valueOf(pid));
        formBody.add("pageno", String.valueOf(pageNo));
        formBody.add("pagesize", "6");

        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("getNewsByPage返回", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();//接收接口返回的内容
                Log.d("getNewsListByPage返回", result);
                //将接口返回的JSON数据还原为List<E>
                ECommentResult eCommentResult = gson.fromJson(result, ECommentResult.class);
                listProduct.addAll(eCommentResult.getDataResult());
                Log.d("<<<<listCarNews行数>>>", listProduct.size() + "");
                Log.d("<<listCarNews>>>", gson.toJson(listProduct));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //更新UI

                        commentAdapter.notifyDataSetChanged();

                    }
                });
            }
        });
    }

}
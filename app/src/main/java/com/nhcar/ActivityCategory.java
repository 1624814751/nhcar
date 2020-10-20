package com.nhcar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhcar.adapter.CatAdapter;
import com.nhcar.adapter.CatAdapter1;
import com.nhcar.adapter.ProductListAdapter;
import com.nhcar.entity.ECategory;
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

public class ActivityCategory extends AppCompatActivity {
    //声明控件对象
    private  OkHttpClient okHttpClient=new OkHttpClient.Builder()
            .connectTimeout(5,TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();
    private Gson gson=new Gson();  //JSON转换工具

    private ListView categorylist_listview;

    private List<ECategory> listCat=new ArrayList<ECategory>();//存放品牌数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_category);

        initView();
        initData();
        initListener();

    }// onCreate End
    private void initView(){
        categorylist_listview=this.findViewById(R.id.categorylist_listview);

    }
    private void initData(){
        loadCategory();

    }
    private void initListener(){
        categorylist_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int cid=listCat.get(position).getCid();
                Intent intent=new Intent(getApplicationContext(),ActivityPoductList0.class);
                intent.putExtra("cid",listCat.get(position).getCid());
                startActivity(intent);
            }
        });
    }
    //自定义方法
    private void loadCategory(){
        String url= Const.SERVER_URL+Const.SERVLET_URL+"getAllCategory";

        Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call=okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("网络访问返回结果",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string(); //接口返回结果
                Log.d("网络访问返回结果",result);

                listCat=gson.fromJson(result,new TypeToken<List<ECategory>>(){}.getType());
                Log.d("<<<<JSON还原后集合的成员数量>>>",listCat.size()+"");
                // Log.d("<<JSON还原后集合的第一个成员品牌名称为>",listCat.get(3).getCname());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CatAdapter1 catAdapter1=new CatAdapter1(listCat,getApplicationContext());
                        categorylist_listview.setAdapter(catAdapter1);

                    }
                });
            }
        });
    }

}

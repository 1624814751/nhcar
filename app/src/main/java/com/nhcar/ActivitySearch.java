package com.nhcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nhcar.adapter.ProductListAdapter;
import com.nhcar.adapter.SearchAdapter;
import com.nhcar.entity.EProduct;
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

public class ActivitySearch extends AppCompatActivity {
    // 声明控件、对象
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)//访问的时间
            .readTimeout(5, TimeUnit.SECONDS)//浏览的时间
            .build();
    private Gson gson = new Gson();//Json工具对象，转换工具

    //声明一个
    private SharedPreferences sp;

    private ListView productlist_listview;
    private Button search_back_button, search_button, btnmore;
    private EditText search_context;
    private int pageNo = 0;//当前页码
    private List<EProduct> listProduct = new ArrayList<EProduct>();//存放汽车列表信息，当前页累加
    private SearchAdapter searchAdapter;//数据适配器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchcar);

        initView();
        initData();
        initListener();

    }// onCreate End

    private void initView() {
        productlist_listview = this.findViewById(R.id.productlist_listview);
        search_back_button = this.findViewById(R.id.search_back_button);
        search_button = this.findViewById(R.id.search_button);
        btnmore = this.findViewById(R.id.btnmore);
        search_context = this.findViewById(R.id.search_context);
    }

    private void initData() {
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProductList();
            }
        });
    }

    private void initListener() {
        search_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityIndex.class);
                startActivity(intent);
                finish();
            }
        });
        btnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMoreProductList();
            }
        });
    }
    //显示第一页
    private void loadProductList() {
        String searchcarText=search_context.getText().toString().trim();
        String url = Const.SERVER_URL + Const.SERVLET_URL + "searchProductList";
        pageNo = 1;//显示第一页
        Log.d("<<<<>>>", url);

        FormBody.Builder formBody = new FormBody.Builder();//表单参数对象
        String userJsonEncode="";  //定义一个空的存放数据的变量
        try {
            userJsonEncode = URLEncoder.encode(searchcarText, "utf-8");//对userJson进行编码转换
            Log.d("<<<<<userJson>>>", userJsonEncode);
        } catch (Exception e) {
            Toast.makeText(this, "数据编码错误", Toast.LENGTH_SHORT).show();
            return;
        }

        formBody.add("key", searchcarText);//参数1，还可以添加更多参数
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
                EProductListResult eProductListResult = gson.fromJson(result, EProductListResult.class);
                listProduct = eProductListResult.getDataResult();
                Log.d("<<<<json 还原为List后的行数>>>", listProduct.size() + "");
                Log.d("<<listCarNews>>>", gson.toJson(listProduct));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //更新UI
                        searchAdapter=new SearchAdapter(ActivitySearch.this,listProduct);
                        productlist_listview.setAdapter(searchAdapter);

                    }
                });
            }
        });
    }

    //加载更多
    private void loadMoreProductList(){
        String searchcarText=search_context.getText().toString().trim();
        String url=Const.SERVER_URL+Const.SERVLET_URL+"searchProductList";
        pageNo++; //显示为下一页
        Log.d("<<<<<>>>",url);

        FormBody.Builder formBody = new FormBody.Builder();//表单参数对象
        formBody.add("key", searchcarText);
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
                EProductListResult ECarNewsResult = gson.fromJson(result, EProductListResult.class);
                listProduct.addAll(ECarNewsResult.getDataResult());
                Log.d("<<<<listCarNews行数>>>", listProduct.size() + "");
                Log.d("<<listCarNews>>>", gson.toJson(listProduct));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //更新UI

                        searchAdapter.notifyDataSetChanged();

                    }
                });
            }
        });
}

}

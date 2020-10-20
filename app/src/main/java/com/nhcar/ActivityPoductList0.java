package com.nhcar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nhcar.adapter.ProductListAdapter;
import com.nhcar.entity.ECarNewsResult;
import com.nhcar.entity.EProduct;
import com.nhcar.entity.EProductListResult;
import com.nhcar.utils.Const;
import com.nhcar.view.MyListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Result;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityPoductList0 extends AppCompatActivity {
	private OkHttpClient okHttpClient=new OkHttpClient.Builder()
			.connectTimeout(5, TimeUnit.SECONDS)
			.readTimeout(5, TimeUnit.SECONDS)
			.build();
	private Gson gson=new Gson();  //JSON转换工具
	private int pageNo=0;//当前页码

	private MyListView productlist_listview;
	private Button productback,btnmore;


	private List<EProduct> listProduct=new ArrayList<EProduct>();//存放汽车列表信息，当前页累加
	private ProductListAdapter productListAdapter;//数据适配器对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_productlist0);

		initView();
		initData();
		initListener();

	}// onCreate End

	private void initView() {
		productlist_listview=this.findViewById(R.id.productlist_listview);
		productback=this.findViewById(R.id.productback);
		btnmore=this.findViewById(R.id.btnmore);

	}

	private void initData() {
		Intent intent = getIntent();
		int cid = intent.getIntExtra("cid", 0);
		Toast.makeText(this, "品牌ID" + cid, Toast.LENGTH_SHORT).show();
		loadProductList(cid);

	}

	private void initListener() {
		productback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnmore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=getIntent();
				int cid=intent.getIntExtra("cid",0);
				loadMoreProductList(cid);
			}
		});

	}
	private void loadProductList(int cid) {
		String url = Const.SERVER_URL + Const.SERVLET_URL + "getproductListByCid";
		pageNo = 1;
		Log.d("<<<<>>>", url);

		FormBody.Builder formBody = new FormBody.Builder();//表单参数对象
		formBody.add("cid", String.valueOf(cid));
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
				EProductListResult eProductListResult = gson.fromJson(result, EProductListResult.class);
				listProduct = eProductListResult.getDataResult();
				Log.d("<<<<listCarNews行数>>>", listProduct.size() + "");
				Log.d("<<listCarNews>>>", gson.toJson(listProduct));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //更新UI
						productListAdapter=new ProductListAdapter(getApplicationContext(),listProduct);
						productlist_listview.setAdapter(productListAdapter);

                    }
                });
			}
		});
	}
	private void loadMoreProductList(int cid){
		String url=Const.SERVER_URL+Const.SERVLET_URL+"getproductListByCid";
		pageNo++;
		Log.d("<<<<<>>>",url);

		FormBody.Builder formBody = new FormBody.Builder();//表单参数对象
		formBody.add("cid", String.valueOf(cid));
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
						productListAdapter.notifyDataSetChanged();

					}
				});
			}
		});

	}
}

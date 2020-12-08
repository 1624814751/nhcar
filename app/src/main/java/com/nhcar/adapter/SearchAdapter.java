package com.nhcar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nhcar.ActivityMyLogin;
import com.nhcar.R;
import com.nhcar.entity.ECart;
import com.nhcar.entity.EProduct;
import com.nhcar.entity.EUserResult;
import com.nhcar.utils.Const;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import loopj.android.image.SmartImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchAdapter extends BaseAdapter {
    private List<EProduct> list=new ArrayList<>();//数据源
    private Activity context;//上下文
    private LayoutInflater layoutInflater;//获取布局项
    private SharedPreferences sp;


    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)//访问的时间
            .readTimeout(5, TimeUnit.SECONDS)//浏览的时间
            .build();
    private Gson gson = new Gson();//Json工具对象，转换工具

    public SearchAdapter(Activity context, List<EProduct> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        layoutInflater = LayoutInflater.from(context);
        //如果视图不存在
        if (convertView == null) {
            //获取项布局
            convertView = layoutInflater.inflate(R.layout.activity_searchcar_item, null);
            //获取项布局中的控件
            holder.img = convertView.findViewById(R.id.search_item_image);
            holder.title = convertView.findViewById(R.id.search_item_title);
            holder.price = convertView.findViewById(R.id.search_item_price);
            holder.addToCart =  convertView.findViewById(R.id.addToCart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //视图中控件的数据绑定
        String imgUrl = Const.SERVER_URL + Const.PIC_URL + list.get(position).getPpic();
        holder.img.setImageUrl(imgUrl);
        holder.title.setText(list.get(position).getPname() + "");
        holder.price.setText(list.get(position).getPprice() + "万元");
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(this,"你按了放入购物车按钮，购买。"+listgetPname());
                sp=context.getSharedPreferences("nhcarsp", Context.MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("isLogin", false);
                //String unmae = sp.getString("loginUserName", "");
                int uid = sp.getInt("loginUserID", 0);
                if (isLogin) {
                    ECart eCart = new ECart();
                    eCart.setCid(0);
                    eCart.setCpid(list.get(position).getPid());
                    eCart.setCuid(uid);
                    eCart.setCnum(1);

                    String cartJson = gson.toJson(eCart);
                    //调用接口进行注册
                    String url = Const.SERVER_URL + Const.SERVLET_URL + "addCart";
                    Log.d("<<<<<url>>>", url);

                    FormBody.Builder fromBody = new FormBody.Builder();//表单参数对象
                    fromBody.add("cartstr", cartJson);

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
                            Log.d("addCart返回值", result);
                            final EUserResult eUserResult = gson.fromJson(result, EUserResult.class);
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //更新UI
                                    Toast.makeText(context, eUserResult.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.d("<<addCart结果>>", eUserResult.getMsg());
                        }
                    });
                } else {//否则跳转到登录页
                    Intent intent = new Intent(context, ActivityMyLogin.class);
                    context.startActivity(intent);
                }
            }


        });//setOnClickListener end

        return convertView;
    }///getView end

    //vieHolder的成员要与项布局的控件一一对应：类型相同，名称可以不同
    class ViewHolder {
        SmartImageView img;
        TextView title;
        TextView price;
        Button addToCart;
    }
}

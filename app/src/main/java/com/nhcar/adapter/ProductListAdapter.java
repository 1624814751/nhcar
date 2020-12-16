package com.nhcar.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nhcar.ActivityComment;
import com.nhcar.ActivityMyLogin;
import com.nhcar.ActivityPoductList0;
import com.nhcar.R;
import com.nhcar.entity.ECart;
import com.nhcar.entity.EComment;
import com.nhcar.entity.EProduct;
import com.nhcar.entity.EUserResult;
import com.nhcar.utils.Const;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import loopj.android.image.SmartImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductListAdapter extends BaseAdapter {
    private List<EProduct> list = new ArrayList<EProduct>();//数据源
    private Activity context;//上下文
    private LayoutInflater layoutInflater;//获取布局项
    private SharedPreferences sp;


    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();
    private Gson gson = new Gson();

    public ProductListAdapter(Activity context, List<EProduct> list) {
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
            convertView = layoutInflater.inflate(R.layout.activity_productlist, null);
            //获取项布局中的控件
            holder.img = convertView.findViewById(R.id.productlist_image);
            holder.title = convertView.findViewById(R.id.productlist_title);
            holder.price = convertView.findViewById(R.id.productlist_price);
            holder.addToCart = convertView.findViewById(R.id.addToCart);
            holder.addToConmment = convertView.findViewById(R.id.addToConmment);
            holder.ViewConmment = convertView.findViewById(R.id.ViewConmment);
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
                sp = context.getSharedPreferences("nhcarsp", Context.MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("isLogin", false);
                String unmae = sp.getString("loginUserName", "");
                int uid = sp.getInt("loginUserID", 0);
                if (isLogin) {
                    ECart eCart = new ECart();
                    eCart.setCid(0);
                    eCart.setCpid(list.get(position).getPid());
                    eCart.setCuid(uid);
                    eCart.setCnum(1);

                    String cartJson = gson.toJson(eCart);

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

                } else {
                    //否则跳转到登录页
                    Intent intent = new Intent(context, ActivityMyLogin.class);
                    context.startActivity(intent);
                }
            }
        });//addToCart setOnClickListener end
        holder.addToConmment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pid = list.get(position).getPid();
                String pname = list.get(position).getPname();

                sp = context.getSharedPreferences("nhcarsp", Context.MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("isLogin", false);
                final String cnmae = sp.getString("loginUserName", "");
                int cid = sp.getInt("loginUserID", 0);
                if (isLogin) {
                    final EditText editText = new EditText(context);
                    editText.setSingleLine(false);
                    editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    editText.setGravity(Gravity.TOP);
                    editText.setMinLines(3);
                    editText.setMaxLines(5);
                    editText.setHorizontallyScrolling(false);
                    AlertDialog.Builder editDialog = new AlertDialog.Builder(context);
                    editDialog.setIcon(R.mipmap.ic_launcher_round);
                    pname = pname.length() > 12 ? pname.substring(0, 12) + "..." : pname;
                    editDialog.setTitle("评论:" + pname).setView(editText);
                    editDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String content = editText.getText().toString().trim();
                            if (content.isEmpty()) {
                                Toast.makeText(context, "输入的评论内容为空，评论失败。" + editText.getText(), Toast.LENGTH_SHORT).show();
                            } else {
                                EComment eComment = new EComment();
                                eComment.setCname(cnmae);
                                eComment.setCpid(pid);
                                eComment.setCcontent(content);


                                String cartJson = gson.toJson(eComment);

                                String url = Const.SERVER_URL + Const.SERVLET_URL + "addComment";
                                Log.d("<<<<<url>>>", url);

                                FormBody.Builder fromBody = new FormBody.Builder();//表单参数对象
                                fromBody.add("commentstr", cartJson);

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
                            }
                        }
                    });
                    editDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "取消评论", Toast.LENGTH_SHORT).show();
                        }
                    });
                    editDialog.show();
                } else {//否则跳转到登录页
                    Intent intent = new Intent(context, ActivityMyLogin.class);
                    context.startActivity(intent);
                }
            }
        });//addToConmment setOnClickListener end
        holder.ViewConmment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ActivityComment.class);
                intent.putExtra("pid",list.get(position).getPid());
                context.startActivity(intent);

            }
        });//ViewComment setOnClickListener end

        return convertView;
    }///getView end

    //vieHolder的成员要与项布局的控件一一对应：类型相同，名称可以不同
    class ViewHolder {
        SmartImageView img;
        TextView title;
        TextView price;
        Button addToCart;
        Button addToConmment;
        Button ViewConmment;
    }
}

package com.nhcar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nhcar.R;
import com.nhcar.entity.ECategory;
import com.nhcar.utils.Const;

import java.util.ArrayList;
import java.util.List;

import loopj.android.image.SmartImage;
import loopj.android.image.SmartImageView;

public class CatAdapter extends BaseAdapter {
    private List<ECategory>listCat=new ArrayList<>();//数据源
    private Context context;//上下文
    private LayoutInflater layoutInflater;//获取布局项
    //构造方法：需要上下文、数据源集合进行初始化
    public CatAdapter(List<ECategory> listCat, Context context) {
        this.listCat = listCat;
        this.context = context;

    }

    @Override
    public int getCount() {
        return listCat.size();
    }

    @Override
    public Object getItem(int position) {
        return listCat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        layoutInflater=layoutInflater.from(context);
        //如果视图不存在
        if (convertView==null){
            //获取布局项
            convertView=layoutInflater.inflate(R.layout.activity_index_category_item,null);
            //获取项布局中的控件
            holder.smv=convertView.findViewById(R.id.imageView8);
            holder.tv=convertView.findViewById(R.id.textView14);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        //视图中控件的数据绑定
        String imgUrl= Const.SERVER_URL+Const.PIC_URL+listCat.get(position).getCpic();
       // Log.d("<<<<<Cat imgURL>>>>",imgUrl);
        holder.smv.setImageUrl(imgUrl);
        holder.tv.setText(listCat.get(position).getCname());
        return convertView;
    }

    //ViewHolder的成员要项布局对应
    class ViewHolder{
        SmartImageView smv;
        TextView tv;
    }
}

package com.nhcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nhcar.R;
import com.nhcar.entity.ECategory;
import com.nhcar.entity.EComment;
import com.nhcar.utils.Const;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loopj.android.image.SmartImageView;

public class CommentAdapter extends BaseAdapter {
    private List<EComment> listCat=new ArrayList<>();//数据源
    private Context context;//上下文
    private LayoutInflater layoutInflater;//获取布局项
    //构造方法：需要上下文、数据源集合进行初始化
    public CommentAdapter(List<EComment> listCat, Context context) {
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
        CommentAdapter.ViewHolder holder=new CommentAdapter.ViewHolder();
        layoutInflater=layoutInflater.from(context);
        //如果视图不存在
        if (convertView==null){
            //获取布局项
            convertView=layoutInflater.inflate(R.layout.activity_conmment_item,null);
            //获取项布局中的控件
            holder.cn=convertView.findViewById(R.id.conmment_content);
            holder.nm=convertView.findViewById(R.id.conmment_name);
            holder.tv=convertView.findViewById(R.id.conmment_date);
            convertView.setTag(holder);
        }else{
            holder=(CommentAdapter.ViewHolder)convertView.getTag();
        }
        //视图中控件的数据绑定
        holder.cn.setText(listCat.get(position).getCcontent());
        holder.nm.setText(listCat.get(position).getCname());
        Date date=new Date();
        holder.tv.setText(date.toLocaleString());

        return convertView;
    }

    //ViewHolder的成员要项布局对应
    class ViewHolder{
        TextView cn;
        TextView tv;
        TextView nm;
    }
}


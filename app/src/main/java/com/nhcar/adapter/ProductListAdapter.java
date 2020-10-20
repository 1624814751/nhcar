package com.nhcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nhcar.R;
import com.nhcar.entity.ECarNews;
import com.nhcar.entity.ECarNewsResult;
import com.nhcar.entity.EProduct;
import com.nhcar.entity.EProductListResult;
import com.nhcar.utils.Const;

import java.util.ArrayList;
import java.util.List;

import loopj.android.image.SmartImageView;

public class ProductListAdapter extends BaseAdapter {
    private List<EProduct> list=new ArrayList<EProduct>();//数据源
    private Context context;//上下文
    private LayoutInflater layoutInflater;//获取布局项

    public ProductListAdapter(Context context,List<EProduct> list) {
        this.context = context;
        this.list=list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductListAdapter.ViewHolder holder=new ProductListAdapter.ViewHolder();
        layoutInflater=LayoutInflater.from(context);
        //如果视图不存在
        if(convertView==null){
            //获取项布局
            convertView=layoutInflater.inflate(R.layout.activity_productlist_item,null);
            //获取项布局中的控件
            holder.img=convertView.findViewById(R.id.productlist_image);
            holder.title=convertView.findViewById(R.id.productlist_title);
            holder.price=convertView.findViewById(R.id.productlist_price);
            convertView.setTag(holder);
        }else{
            holder=(ProductListAdapter.ViewHolder)convertView.getTag();
        }
        //视图中控件的数据绑定
        String imgUrl= Const.SERVER_URL+Const.PIC_URL+list.get(position).getPpic();
        holder.img.setImageUrl(imgUrl);
        holder.title.setText(list.get(position).getPname()+"");
        holder.price.setText(list.get(position).getPprice()+"");
        return convertView;
    }///getView end

    //vieHolder的成员要与项布局的控件一一对应：类型相同，名称可以不同
    class ViewHolder{
        SmartImageView img;
        TextView title;
        TextView price;
    }
}

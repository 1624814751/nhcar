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
import com.nhcar.entity.ECategory;
import com.nhcar.utils.Const;

import java.util.ArrayList;
import java.util.List;

import loopj.android.image.SmartImageView;

public class CarNewsAdapter extends BaseAdapter {
    private List<ECarNews> list=new ArrayList<ECarNews>();//数据源
    private Context context;//上下文
    private LayoutInflater layoutInflater;//获取布局项

    public CarNewsAdapter(Context context,List<ECarNews> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        layoutInflater=LayoutInflater.from(context);
        //如果视图不存在
        if(convertView==null){
            //获取项布局
            convertView=layoutInflater.inflate(R.layout.activity_index_carnews_item,null);
            //获取项布局中的控件
            holder.smv=convertView.findViewById(R.id.news_image);
            holder.title=convertView.findViewById(R.id.news_title);
            holder.date=convertView.findViewById(R.id.news_date);
            holder.author=convertView.findViewById(R.id.news_author);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        //视图中控件的数据绑定
        String imgUrl= Const.SERVER_URL+Const.PIC_URL+"news/"+list.get(position).getNpic();
        holder.smv.setImageUrl(imgUrl);
        holder.title.setText(list.get(position).getNtitle()+"");
        holder.date.setText(list.get(position).getNdate()+"");
        holder.author.setText(list.get(position).getUname()+"");
        return convertView;
    }///getView end

    //vieHolder的成员要与项布局的控件一一对应：类型相同，名称可以不同
    class ViewHolder{
        SmartImageView smv;
        TextView title;
        TextView date;
        TextView author;
    }
}

package com.example.core.shenmatrip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.core.shenmatrip.R;
import com.example.core.shenmatrip.entity.BitMapCache;
import com.example.core.shenmatrip.entity.Spot;

import java.util.ArrayList;

/**
 * Created by Core on 2016/2/18.
 */

public class MainAdapter extends BaseAdapter {

    ArrayList<Spot> list;
    private LayoutInflater mInflater;
    Context context;
    public final class ViewHolder {
        public ImageView listImg;
        public TextView info;
    }

    public void BindData(ArrayList<Spot> datas) {

        this.list = datas;
    }

    public MainAdapter(Context contex) {
        this.context=contex;
        this.mInflater = LayoutInflater.from(contex);

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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        RequestQueue mQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitMapCache());

        if (convertView == null) {
            holder = new ViewHolder();

            //可以理解为从vlist获取view  之后把view返回给ListView

            convertView = mInflater.inflate(R.layout.fragment_main_list, null);
            holder.listImg = (ImageView) convertView.findViewById(R.id.list_img);
            holder.info = (TextView) convertView.findViewById(R.id.list_info);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //网络图片
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.listImg,
                R.drawable.defaultcovers, R.drawable.defaultcovers);
        holder.listImg.setImageResource(R.drawable.defaultcovers);
        imageLoader.get(list.get(position).getMain_pic(), listener);

        //固定drawable图片
//        holder.listImg.setImageResource(R.drawable.header_bridge);
        holder.info.setText(list.get(position).getName());
        return convertView;
    }

}




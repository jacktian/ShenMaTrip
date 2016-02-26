package com.example.core.shenmatrip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.core.shenmatrip.R;

import java.util.List;

/**
 * Created by zhouyf on 16/2/26.
 */
public class LocationAdapter extends BaseAdapter{

    List<String> list;
    private LayoutInflater mInflater;
    Context context;
    public final class ViewHolder {
        public TextView info;
    }

    public void BindData(List<String> datas) {
        this.list = datas;
    }

    public LocationAdapter(Context contex) {
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

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.choose_location_item, null);
            holder.info = (TextView) convertView.findViewById(R.id.choose_location_list_textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.info.setText(list.get(position));
        return convertView;
    }
}

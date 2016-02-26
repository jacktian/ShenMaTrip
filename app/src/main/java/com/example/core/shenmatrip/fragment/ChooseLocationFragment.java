package com.example.core.shenmatrip.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.shenmatrip.R;
import com.example.core.shenmatrip.adapter.LocationAdapter;
import com.example.core.shenmatrip.entity.Location;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhouyf on 16/2/26.
 */
public class ChooseLocationFragment extends Fragment{
    private ListView listView;
    private TextView location;
    private List<String> provinces;
    private LocationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_location,container,false);

        listView = (ListView)view.findViewById(R.id.choose_location_list);
        location = (TextView)view.findViewById(R.id.choose_location_textView);
        setContent();
        return view;
    }

    private void setContent(){
        setData();
        location.setText("当前位置为:"+Location.getLocation());

        //listView初始化
        adapter = new LocationAdapter(getContext());
        adapter.BindData(provinces);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //listener设置
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //数据存储
                Location.setLocation(provinces.get(position));
                Toast.makeText(getContext(),"更改位置为:"+provinces.get(position),Toast.LENGTH_SHORT).show();

                //跳转
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                MainFragment mainFragment =new MainFragment();
                ft.replace(R.id.frame_layout,mainFragment);
                ft.commit();
            }
        });
    }

    private void setData(){
        provinces = Arrays.asList("北京","上海","天津","重庆",
                "河北","山西","内蒙古",
                "辽宁", "吉林","黑龙江",
                "江苏","浙江","安徽","福建","山东",
                "河南","湖北","湖南","江西",
                "广东","海南","广西",
                "四川","贵州","云南","西藏",
                "陕西","宁夏","青海","甘肃","新疆",
                "香港","澳门","台湾");
    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    //FragmentManager初始化
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    Fragment fragment = new SettingFragment();

                    ft.replace(R.id.frame_layout, fragment);
                    ft.commit();
                    return true;
                }

                return false;
            }
        });
    }
}

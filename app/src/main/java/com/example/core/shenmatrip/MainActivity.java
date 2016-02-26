package com.example.core.shenmatrip;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.shenmatrip.fragment.MainFragment;
import com.example.core.shenmatrip.fragment.SettingFragment;
import com.example.core.shenmatrip.fragment.TravelFragment;
import com.example.core.shenmatrip.service.Common;
import com.example.core.shenmatrip.service.LocService;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import org.json.JSONException;
import org.json.JSONObject;




public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    Toolbar toolbar;
    SearchView searchView;
    TextView headText;
    ImageView headImage;
    private ProgressDialog dialog;
    MainFragment Fragment0;
    SettingFragment Fragment3;
    TravelFragment Fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.tl_custom);
        toolbar.setTitle("神马旅游");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //init navigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(navItemSelect);

        //init Fragment
        Fragment0 = new MainFragment();
        Fragment3 = new SettingFragment();
        Fragment1 = new TravelFragment();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        fragmentTransaction.add(R.id.frame_layout, Fragment0);
        fragmentTransaction.commit();

        // 注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Common.LOCATION_ACTION);
        this.registerReceiver(new LocationBroadcastReceiver(), filter);

        // 启动服务
        Intent intent = new Intent();
        intent.setClass(this, LocService.class);
        startService(intent);
        headImage = (ImageView) findViewById(R.id.head_image);
        headText = (TextView) findViewById(R.id.head_text);
        setWeather("武汉");

        // 等待提示
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在定位...");
        dialog.setCancelable(true);
        dialog.show();

        // 测试 SDK 是否正常工作的代码
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("ww","你好");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    Log.d("saved","success!");
//                }
//            }
//        });
    }

    private class LocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(Common.LOCATION_ACTION)) return;
            String locationInfo = intent.getStringExtra(Common.LOCATION);
            //text.setText(locationInfo);
            Log.i("Loc",locationInfo);
            dialog.dismiss();
            MainActivity.this.unregisterReceiver(this);// 不需要时注销
        }
    }


    public void setWeather(String cityname) {

        Parameters para = new Parameters();
        para.add("cityname", "武汉");
        JuheData.executeWithAPI(getApplicationContext(),73, "http://op.juhe.cn/onebox/weather/query", JuheData.GET, para,
                new DataCallBack() {
                    @Override
                    public void onSuccess(int i, String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            JSONObject result = json.getJSONObject("result");
                            JSONObject data = result.getJSONObject("data");
                            JSONObject weather = data.getJSONObject("realtime").getJSONObject("weather");
                            String image = weather.getString("img");
                            String info = weather.getString("info");
                            String temp = weather.getString("temperature");
                            headImage.setImageResource(getResources().getIdentifier("d"+image,"drawable","com.example.core.shenmatrip"));
                            //headImage.setAlpha(80);
                            headText.setText(info +"     当前气温：" + temp + "℃");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i("apiweather", "onSuccess " + s);
                    }

                    @Override
                    public void onFinish() {
                        Log.i("apiweather", "onFinish ");
                    }

                    @Override
                    public void onFailure(int i, String s, Throwable throwable) {
                        Log.i("apiweather", "onFailure " + s);
                    }
                });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        //搜索视窗
        searchView = (SearchView) menu.findItem(R.id.menu_search)
                .getActionView();
        // 为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(searchListener);
        // 设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        searchView.setQueryHint("景区搜索");

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "点击搜素按钮了", Toast.LENGTH_SHORT).show();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //侧边栏监听
    NavigationView.OnNavigationItemSelectedListener navItemSelect = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            // Handle navigation view item clicks here.
            int id = menuItem.getItemId();

            if (id == R.id.nav_setting) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, Fragment3);
                fragmentTransaction.commit();
                mDrawerLayout.closeDrawers();
            }
            if (id == R.id.nav_spots) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, Fragment0);
                fragmentTransaction.commit();
                mDrawerLayout.closeDrawers();
            }
            if (id == R.id.nav_map) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, Fragment1);
                fragmentTransaction.commit();
                mDrawerLayout.closeDrawers();
            }

            return true;
        }
    };

    //搜索监听
    SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        // 用户输入字符时激发该方法
        public boolean onQueryTextChange(String newText) {
           // Toast.makeText(MainActivity.this, "textChange--->" + newText, Toast.LENGTH_LONG).show();
            if (TextUtils.isEmpty(newText)) {
                // 清除ListView的过滤
                //ListView.clearTextFilter();
            } else {
                // 使用用户输入的内容对ListView的列表项进行过滤
                //searchView.setFilterText(newText);
            }
            return true;
        }

        // 单击搜索按钮时激发该方法
        public boolean onQueryTextSubmit(String query) {
            // 实际应用中应该在该方法内执行实际查询
            // 此处仅使用Toast显示用户输入的查询内容
            Toast.makeText(MainActivity.this, "您的选择是:" + query, Toast.LENGTH_SHORT).show();
            return false;
        }
    };

}

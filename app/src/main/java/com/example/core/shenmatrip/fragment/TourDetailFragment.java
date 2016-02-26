package com.example.core.shenmatrip.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.core.shenmatrip.R;
import com.example.core.shenmatrip.entity.BitMapCache;

import krelve.view.Kanner;

/**
 * Created by zhouyf on 16/2/22.
 */
public class TourDetailFragment extends Fragment {

    private TextView intro;
    private ImageView img;
    private TextView foodIntro;
    private TextView culture;
    private Kanner kanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tourdetail,container,false);

        //对显示内容取值
        String intro = getArguments().getString("introduction");
        String picture = getArguments().getString("picture");
        String picture2 = getArguments().getString("picture2");
        String foodIntro = getArguments().getString("foodIntro");
        String foodPic = getArguments().getString("foodPic");
        String culture =getArguments().getString("culture");

        img= (ImageView) view.findViewById(R.id.img_food);
        this.intro = (TextView) view.findViewById(R.id.textView);
        this.foodIntro = (TextView) view.findViewById(R.id.text_food);
        this.culture = (TextView) view.findViewById(R.id.text_culture);
        RequestQueue mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        //加载foodPic
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitMapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(img,
                R.drawable.defaultcovers, R.drawable.defaultcovers);
        imageLoader.get(foodPic, listener);

        //加载轮播
        kanner = (Kanner) view.findViewById(R.id.loop_img);
        kanner.setImagesUrl(new String[] {
                picture,picture2});


        this.intro.setText(intro);
        this.culture.setText(culture);
        this.foodIntro.setText(foodIntro);
        return view;
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

                    Fragment fragment = new MainFragment();

                    ft.replace(R.id.frame_layout, fragment);
                    ft.commit();
                    return true;
                }

                return false;
            }
        });
    }


}

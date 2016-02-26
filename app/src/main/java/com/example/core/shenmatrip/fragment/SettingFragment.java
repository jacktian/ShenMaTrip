package com.example.core.shenmatrip.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.core.shenmatrip.R;

/**
 * Created by zhouyf on 16/2/24.
 */
public class SettingFragment extends Fragment {
    private Button quit;
    private Button clear;
    private Button location;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);

        quit = (Button) view.findViewById(R.id.setting_quit);
        clear = (Button) view.findViewById(R.id.setting_clear);
        location = (Button) view.findViewById(R.id.setting_location);

        setClick();
        return view;
    }

    private void setClick(){

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"缓存已清除",Toast.LENGTH_SHORT).show();
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ChooseLocationFragment chooseLocationFragment =new ChooseLocationFragment();
                ft.replace(R.id.frame_layout,chooseLocationFragment);
                ft.commit();
            }
        });
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

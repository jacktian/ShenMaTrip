package com.example.core.shenmatrip.fragment;



import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.core.shenmatrip.R;
import com.example.core.shenmatrip.adapter.MainAdapter;
import com.example.core.shenmatrip.entity.GetData;
import com.example.core.shenmatrip.entity.Spot;

import java.util.ArrayList;




public class MainFragment extends Fragment {

    private ListView list;
    private ArrayList<Spot> spots;
    MainAdapter adapter;


    private Handler listHandler = new Handler() {

        public void handleMessage(Message msg){

            if (msg.what == 0){



            }
            if (msg.what ==1)
            {
                spots = (ArrayList<Spot>) msg.obj;
                adapter=new MainAdapter(getActivity());
                adapter.BindData(spots);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_main, container, false);
        list = (ListView) view.findViewById(R.id.list);


        init();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("introduction", spots.get(position).getIntroduction());
                bundle.putString("picture", spots.get(position).getSpot_pic());
                bundle.putString("picture2",spots.get(position).getSpot_pic_2());
                bundle.putString("foodPic",spots.get(position).getFoodPic());
                bundle.putString("foodIntro",spots.get(position).getFoodIntro());
                bundle.putString("culture",spots.get(position).getCulture());


                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                TourDetailFragment tourDetailFragment =new TourDetailFragment();
                tourDetailFragment.setArguments(bundle);
                ft.replace(R.id.frame_layout,tourDetailFragment);
                ft.commit();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }
    private void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                GetData getData= new GetData(getContext(),listHandler);
                getData.GetJingDianData();

                Looper.loop();
            }
        }).start();
    }
}

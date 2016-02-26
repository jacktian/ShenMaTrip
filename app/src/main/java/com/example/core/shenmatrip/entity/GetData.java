package com.example.core.shenmatrip.entity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouyf on 16/2/25.
 */
public class GetData {
    Context context;
    private Handler handle;


    public GetData(Context context, Handler handle) {
        this.context = context;
        this.handle = handle;
    }


    public void GetJingDianData() {
        final ArrayList<Spot> temp = new ArrayList<Spot>();

        AVQuery<AVObject> query = new AVQuery<AVObject>("Data");
        query.whereEqualTo("bestSeason", "夏");
        query.setLimit(5);
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    Log.d("Season成功", "查询到" + avObjects.size() + " 条符合条件的数据");
                    for (AVObject obj:avObjects) {
                        Spot tmp_spot = new Spot();
                        String spots = (String) obj.get("spots");
                        tmp_spot.setBestSeason((String) obj.get("bestSeason"));
                        tmp_spot.setCulture((String) obj.get("culture"));
                        tmp_spot.setFoodIntro((String) obj.get("foodIntro"));
                        tmp_spot.setIntroduction((String) obj.get("introduction"));
                        tmp_spot.setFoodPic("http://7xqmuo.com1.z0.glb.clouddn.com/"
                                +"f"+ (String) obj.get("food")+".jpg");
                        tmp_spot.setName((String) obj.get("mingcheng"));
//                        tmp_spot.setProvince((String) obj.get("province"));
                        tmp_spot.setMain_pic("http://7xqmuo.com1.z0.glb.clouddn.com/"+spots+"01.jpg");
                        tmp_spot.setSpot_pic("http://7xqmuo.com1.z0.glb.clouddn.com/"+spots+"02.jpg");
                        tmp_spot.setSpot_pic_2("http://7xqmuo.com1.z0.glb.clouddn.com/"+spots+"03.jpg");
                        temp.add(tmp_spot);
                    }
                    Message message = Message.obtain(handle);
                    message.obj = temp;
                    message.what = 1;
                    message.sendToTarget();
                } else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });
    }

}

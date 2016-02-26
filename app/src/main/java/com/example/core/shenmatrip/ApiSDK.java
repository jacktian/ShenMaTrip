package com.example.core.shenmatrip;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.thinkland.sdk.android.JuheSDKInitializer;

/**
 * Created by Core on 2016/2/12.
 */
public class ApiSDK extends Application {

    @Override
    public void onCreate() {
        //ApiStoreSDK.init(this,"268a0acee33966e8bf14aabded4ff4a4");
        JuheSDKInitializer.initialize(getApplicationContext());
        AVOSCloud.initialize(this,"oeytp7xcekjpbk9mg68u20li6u3lcud68scme21bgun8zuwk","31hn73y885dnokpolk9iqbml78n5vffv1fer4mm4dpwvv7c1");
        super.onCreate();
    }
}

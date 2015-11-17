package com.beiair.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.beiair.net.business.entity.CurrentUser;
import com.beiair.ui.base.BaseActivity;
import com.beiair.utils.LocationUtils;
import com.beiair.R;

public class SplashActivity extends BaseActivity {
    ImageView iv_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv_ad = (ImageView) findViewById(R.id.iv_ad);

        new LocationUtils().startLocation(mActivity, null);

        loadView();
    }

    private void loadView() {
        iv_ad.setImageResource(R.drawable.loading_bg);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CurrentUser.instance().isLogin()) {
                    if (!TextUtils.isEmpty(CurrentUser.instance().getPhone())) {
                        startActivity(new Intent(mActivity, DeviceActivity.class));
                    } else {
                        startActivity(new Intent(mActivity, BindUserActivity.class));
                    }
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class).putExtra(LoginActivity.ACTION, "nomal"));
                }
                finish();
            }
        }, 2000);
    }
}

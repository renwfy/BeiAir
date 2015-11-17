package com.beiair;

import android.app.Application;

import com.beiair.net.business.entity.CurrentDevice;
import com.beiair.net.business.entity.CurrentUser;
import com.beiair.ui.ActivityManager;
import com.beiair.utils.FileUtils;
import com.beiair.utils.LogUtil;
import com.beiair.utils.Settings;

public class BeiAirApplaction extends Application {
	public static BeiAirApplaction applaction;

	@Override
	public void onCreate() {
		super.onCreate();
		applaction = this;
		
		init();
	}
	
	public static BeiAirApplaction getInstance() {
		return applaction;
	}
	public void init() {

		//初始化屏幕大小
		Settings.P_HEIGHT = getResources().getDisplayMetrics().heightPixels;
		Settings.P_WIDTH = getResources().getDisplayMetrics().widthPixels;

		//初始化文件夹
		FileUtils.makedirs();
	}

	// 退出应用程序
	public void exit() {
		ActivityManager.getScreenManager().popAllActivity();
		onTerminate();
	}
	
	@Override
	public void onTerminate() {
		LogUtil.i("onTerminate");
		CurrentUser.instance().clean();
		CurrentDevice.instance().clean();
		super.onTerminate();
	}
	
}
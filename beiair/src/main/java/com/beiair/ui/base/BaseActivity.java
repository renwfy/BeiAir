package com.beiair.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.beiair.BeiAirApplaction;
import com.beiair.ui.ActivityManager;
import com.beiair.widget.CustomProgressDialog;


public class BaseActivity extends FragmentActivity {
	private CustomProgressDialog dialog;
	public BeiAirApplaction mApplication;
	public ActivityManager activityManager;
	public Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mActivity = this;
		mApplication = BeiAirApplaction.getInstance();
		activityManager = ActivityManager.getScreenManager();

		activityManager.pushActivity(this);
		
		 initDialog();
	}
	
	
	 public void initDialog() {
			// TODO Auto-generated method stub
			dialog = CustomProgressDialog.createDialog(mActivity);
			dialog.setCanceledOnTouchOutside(false);
		}
		
		public void showDialog(String msg) {
			if (!"".equals(msg))
				dialog.setMessage(msg);
			
			if (!dialog.isShowing())
				dialog.show();

		}
		
		public void hideDialog() {
			if (dialog.isShowing())
				dialog.dismiss();

		}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (activityManager != null) {
			activityManager.popActivity(this);
		}
		super.onDestroy();
	}
}

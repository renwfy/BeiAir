package com.beiair.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.beiair.ui.base.BaseMultiPartActivity;
import com.beiair.R;

public class HelpActivity extends BaseMultiPartActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_layout);
		
		setMenuEnable(false);

		initView();
	}

	
	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initView() {
		String verson = null;
		try {
			verson = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((TextView) findViewById(R.id.cur_verson)).setText("当前版本：" + verson);

		findViewById(R.id.about).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(mActivity, AboutActivity.class));
			}
		});
		findViewById(R.id.check_update).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
	}
}

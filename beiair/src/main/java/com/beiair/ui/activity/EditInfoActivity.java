package com.beiair.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.beiair.net.business.BsOperationHub;
import com.beiair.net.business.entity.CurrentDevice;
import com.beiair.net.business.entity.CurrentUser;
import com.beiair.net.business.entity.DevEntity;
import com.beiair.net.business.homer.QueryDevDataPair;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.beiair.net.httpcloud.aync.abs.ReqCbk;
import com.beiair.ui.base.BaseMultiPartActivity;
import com.beiair.ui.model.AirInfo;
import com.beiair.ui.model.FirmwareEntity;
import com.beiair.widget.Toast;
import com.beiair.R;
import com.google.gson.Gson;

/***
 * Description ：修改昵称
 * 
 * @author Lsd
 * 
 */

public class EditInfoActivity extends BaseMultiPartActivity implements OnClickListener {
	EditText et_nickname;
	TextView tv_devid, tv_softver,tv_sinal, tips, tv_confirm;

	DevEntity mDevice;
	String nick;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editinfo);

		setSwipeBackEnable(true);
		setMenuEnable(false);
		
		mDevice = CurrentDevice.instance().curDevice;

		initView();
		devFirmware();
		queryMXData();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initView() {
		tv_devid = (TextView) findViewById(R.id.tv_devid);
		tv_softver = (TextView) findViewById(R.id.tv_softver);
		tv_sinal = (TextView) findViewById(R.id.tv_sinal);
		et_nickname = (EditText) findViewById(R.id.et_nickname);
		tips = (TextView) findViewById(R.id.tips);

		tv_confirm = (TextView) findViewById(R.id.tv_confirm);
		tv_confirm.setOnClickListener(this);

		
		tv_devid.setText(mDevice.devId);
		
		nick = mDevice.nickName;
		et_nickname.setText(mDevice.nickName);
	}

	/***
	 * 软件版本
	 */
	private void devFirmware() {
		// TODO Auto-generated method stub
		BsOperationHub.instance().queryDevFirmware(mDevice, "beiang_firmware", new ReqCbk<RspMsgBase>() {
			@Override
			public void onSuccess(RspMsgBase rspData) {
				// TODO Auto-generated method stub
				if (rspData.isSuccess()) {
					QueryDevDataPair.RspQueryDevData rsp = (QueryDevDataPair.RspQueryDevData) rspData;
					if (rsp.data != null) {
						byte[] reply = rsp.reply;
						String firmwareStr = new String(reply);
						if (!TextUtils.isEmpty(firmwareStr)) {
							FirmwareEntity fEntity = new Gson().fromJson(firmwareStr, FirmwareEntity.class);
							tv_softver.setText(fEntity.getVersioncode());
						}
					}
				}
			}

			@Override
			public void onFailure(ErrorObject err) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	/***
	 * 
	 */
	private void queryMXData() {
		BsOperationHub.instance().queryDevStatus(mDevice.devId, "beiang_status", new ReqCbk<RspMsgBase>() {
			@Override
			public void onSuccess(RspMsgBase rspData) {
				// TODO Auto-generated method stub
				if (rspData.isSuccess()) {
					AirInfo airInfo = CurrentDevice.instance().queryDevice.airInfo;
					if (airInfo != null) {
						tv_sinal.setText(airInfo.getSignal()+"");
					}
				}
			}

			@Override
			public void onFailure(ErrorObject err) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	private void upDevInfo() {
		final String nickname = et_nickname.getText().toString();
		DevEntity devEntity = new DevEntity();
		devEntity.nickName = nickname;
		devEntity.role = mDevice.role;
		devEntity.devInfo = mDevice.devInfo;
		devEntity.devId = mDevice.devId;
		devEntity.deviceSn = mDevice.deviceSn;
		BsOperationHub.instance().upDateAuthrorize(devEntity, CurrentUser.instance().getUserId(), new ReqCbk<RspMsgBase>() {
			@Override
			public void onSuccess(RspMsgBase rspData) {
				// TODO Auto-generated method stub
				if (rspData.isSuccess()) {
					Toast.show(mActivity, "修改成功");
					mDevice.nickName = nickname;
					setResult(RESULT_OK);
					finish();
				} else {
					String eroMsg = rspData.getErrorString();
					if (!"".equals(eroMsg)) {
						Toast.show(mActivity, eroMsg);
					}
				}
			}

			@Override
			public void onFailure(ErrorObject err) {
				// TODO Auto-generated method stub
				String eroMsg = err.getErrorString();
				if (!"".equals(eroMsg)) {
					Toast.show(mActivity, eroMsg);
				}
			}
		});
	}

	// 隐藏输入法
	private void dismissKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et_nickname.getWindowToken(), 0);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_confirm:
			dismissKeyboard();
			String et_txt = et_nickname.getText().toString();
			if (et_txt.equals(nick)) {
				finish();
			} else {
				upDevInfo();
			}
			break;

		default:
			break;
		}
	}

}
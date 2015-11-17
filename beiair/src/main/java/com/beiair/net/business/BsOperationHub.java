package com.beiair.net.business;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;

import com.beiair.BeiAirApplaction;
import com.beiair.constant.Config;
import com.beiair.db.DB_Device;
import com.beiair.net.business.account.EditUserPair;
import com.beiair.net.business.account.GetUserPair;
import com.beiair.net.business.account.LoginPair;
import com.beiair.net.business.account.RegisterPair;
import com.beiair.net.business.account.TrdLoginPair;
import com.beiair.net.business.entity.CurrentDevice;
import com.beiair.net.business.entity.CurrentUser;
import com.beiair.net.business.entity.DevEntity;
import com.beiair.net.business.entity.UserEntity;
import com.beiair.net.business.homer.QueryBindedListPair;
import com.beiair.net.business.homer.QueryBindedResultPair;
import com.beiair.net.business.homer.QueryDevStatusPair;
import com.beiair.net.business.impl.AccoutOperator;
import com.beiair.net.business.impl.BsOperator;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.beiair.net.httpcloud.aync.abs.ReqCbk;
import com.beiair.net.httpcloud.aync.ServerDefinition;
import com.beiair.net.httpcloud.aync.abs.BaseMsg;
import com.beiair.utils.EParse;
import com.beiair.utils.TimerUtil;

public class BsOperationHub {
	private static BsOperationHub sOperator;

	private BaseAcountOp mAccoutOp;
	private BaseBsOp mBsOp;

	synchronized public static BsOperationHub instance() {
		if (sOperator == null) {
			sOperator = new BsOperationHub();
		}
		return sOperator;
	}

	private BsOperationHub() {
		mAccoutOp = new AccoutOperator();
		mBsOp = new BsOperator();
	}

	public void phoneRegister(final String phone, final String passwd, String code, String info, final ReqCbk<RspMsgBase> cloudCbk) {
		mAccoutOp.register(phone, passwd, code, info, cloudCbk);
	}

	public void register(final String userName, final String passwd, String info, final ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		ReqCbk<BaseMsg.RspMsgBase> cbk = new ReqCbk<BaseMsg.RspMsgBase>() {
			@Override
			public void onSuccess(BaseMsg.RspMsgBase rspData) {
				if (rspData.isSuccess()) {
					RegisterPair.RspRegister rsp = (RegisterPair.RspRegister) rspData;
					if (rsp.data != null) {
						CurrentUser.instance().setUserName(userName);
						CurrentUser.instance().setPasswd(passwd);
						CurrentUser.instance().setVerified(true);
						CurrentUser.instance().setUserId(rsp.data.userId);
					}
				}
				cloudCbk.onSuccess(rspData);
			}

			@Override
			public void onFailure(ErrorObject err) {
				cloudCbk.onFailure(err);
			}
		};
		mAccoutOp.register(userName, passwd, info, cbk);
	}

	public void login(final String userName, final String passwd, final ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		ReqCbk<BaseMsg.RspMsgBase> cbk = new ReqCbk<BaseMsg.RspMsgBase>() {
			@Override
			public void onSuccess(BaseMsg.RspMsgBase rspData) {
				if (rspData.isSuccess()) {
					LoginPair.RspLogin rsp = (LoginPair.RspLogin) rspData;
					if (rsp.data != null) {
						CurrentUser.instance().setLogin(true);
						CurrentUser.instance().setVerified(true);
						CurrentUser.instance().setUserId(rsp.data.userId);
						CurrentUser.instance().setToken(rsp.data.token);
						CurrentUser.instance().setCookie(rsp.data.cookie);
						CurrentUser.instance().setUserName(userName);
						CurrentUser.instance().setPasswd(passwd);
					}
				}
				cloudCbk.onSuccess(rspData);
			}

			@Override
			public void onFailure(ErrorObject err) {
				cloudCbk.onFailure(err);
			}
		};
		if (CurrentUser.instance().isLogin()) {
			ReqCbk.ErrorObject err = new ReqCbk.ErrorObject();
			err.cause = ReqCbk.ErrorCause.LOGINED;
			cloudCbk.onFailure(err);
			return;
		}
		mAccoutOp.login(userName, passwd, cbk);
	}

	/** 第三方登陆 */
	public void thdLogin(String channel, String account, String token, String info, final ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		ReqCbk<BaseMsg.RspMsgBase> cbk = new ReqCbk<BaseMsg.RspMsgBase>() {
			@Override
			public void onSuccess(BaseMsg.RspMsgBase rspData) {
				if (rspData.isSuccess()) {
					TrdLoginPair.RspTrdLogin rsp = (TrdLoginPair.RspTrdLogin) rspData;
					if (rsp.data != null) {
						CurrentUser.instance().setLogin(true);
						CurrentUser.instance().setVerified(true);
						CurrentUser.instance().setUserId(rsp.data.userId);
						CurrentUser.instance().setToken(rsp.data.token);
						CurrentUser.instance().setCookie(rsp.data.cookie);
					}
				}
				cloudCbk.onSuccess(rspData);
			}

			@Override
			public void onFailure(ErrorObject err) {
				cloudCbk.onFailure(err);
			}
		};
		mAccoutOp.login(channel, account, token, info, cbk);
	}

	public void logout(final ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		//if (CurrentUser.instance().isLogin()) {
			ReqCbk<BaseMsg.RspMsgBase> cbk = new ReqCbk<BaseMsg.RspMsgBase>() {
				@Override
				public void onSuccess(BaseMsg.RspMsgBase rspData) {
					if (rspData.isSuccess()) {
						CurrentUser.instance().setLogin(false);
						CurrentUser.instance().setToken("");
						CurrentUser.instance().setUserId("");
						// CurrentUser.instance().setUserName("");
						CurrentUser.instance().setPasswd("");
						
						CurrentDevice.instance().devList = null;
					}
					cloudCbk.onSuccess(rspData);
				}

				@Override
				public void onFailure(ErrorObject err) {
					cloudCbk.onFailure(err);
				}
			};
			mAccoutOp.logout(cbk);
		/*} else {
			ErrorObject err = new ErrorObject();
			err.cause = ErrorCause.UN_LOGIN;
			cloudCbk.onFailure(err);
		}*/
	}

	public void getUser(String userId, String userName, final ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		ReqCbk<BaseMsg.RspMsgBase> cbk = new ReqCbk<BaseMsg.RspMsgBase>() {
			@Override
			public void onSuccess(BaseMsg.RspMsgBase rspData) {
				if (rspData.isSuccess()) {
					GetUserPair.RspGetUser rsp = (GetUserPair.RspGetUser) rspData;
					if (rsp.data != null) {
						CurrentUser.instance().setNickName(rsp.data.nickName);
						CurrentUser.instance().setEmail(rsp.data.email);
						CurrentUser.instance().setPhone(rsp.data.phone);
					}
				}
				if (cloudCbk != null)
					cloudCbk.onSuccess(rspData);
			}

			@Override
			public void onFailure(ErrorObject err) {
				if (cloudCbk != null)
					cloudCbk.onFailure(err);
			}
		};
		mAccoutOp.getUser(userId, userName, cbk);
	}

	public void editUser(UserEntity user, final ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		if (user.getUserId() == CurrentUser.instance().getUserId()) {
			ReqCbk<BaseMsg.RspMsgBase> cbk = new ReqCbk<BaseMsg.RspMsgBase>() {
				@Override
				public void onSuccess(BaseMsg.RspMsgBase rspData) {
					if (rspData.isSuccess()) {
						EditUserPair.RspEditUser rsp = (EditUserPair.RspEditUser) rspData;
						if (rsp.data != null) {
							CurrentUser.instance().setUserName(rsp.data.userName);
							CurrentUser.instance().setEmail(rsp.data.email);
						}
					}
					cloudCbk.onSuccess(rspData);
				}

				@Override
				public void onFailure(ErrorObject err) {
					cloudCbk.onFailure(err);
				}
			};
			mAccoutOp.editUser(user, cbk);
		} else {
			mAccoutOp.editUser(user, cloudCbk);
		}
	}

	/** 绑定用户 */
	public void bindUser(String code, String phone, String userName, String password, ReqCbk<BaseMsg.RspMsgBase> cbk) {
		mAccoutOp.bindUser(code, phone, userName, password, cbk);
	}

	/*** 获取短信授权码 */
	public void authCode(String phone, ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		mAccoutOp.authCode(phone, cloudCbk);
	}

	/** 通过短信验证设置新密码 */
	public void updatePassByAuth(String phone, String userName, String newPass, String code, ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		mAccoutOp.updatePass(phone, userName, newPass, code, cloudCbk);
	}

	/*** 获取设备信息，通过设备id */
	public void getDeviceById(String ownerId, ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		mBsOp.getDevice(ownerId, cloudCbk);
	}

	/*** 绑定设备，通过设备信息 */
	public void bindDevice(DevEntity dev, ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		mBsOp.bindDevice(dev, cloudCbk);
	}

	/*** 解除绑定设备 */
	public void unbindDevice(String devId, ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		mBsOp.unbindDevice(devId, cloudCbk);
	}

	/** 复位设备 */
	public void resetDevice(String devId, ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		mBsOp.resetDevice(devId, cloudCbk);
	}

	/** 查询绑定设备绑定结果 */
	public void queryBindDev(final String userId, final String bindCode, final ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		queryBindDevice(userId, bindCode, cloudCbk);
	}

	/*** 查询绑定列表 */
	public void queryBindList(String userId, final ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		ReqCbk<BaseMsg.RspMsgBase> cbk = new ReqCbk<BaseMsg.RspMsgBase>() {
			@Override
			public void onSuccess(BaseMsg.RspMsgBase rspData) {
				List<DevEntity> list = null;
				if (rspData.isSuccess()) {
					QueryBindedListPair.RspQueryBindedList rsp = (QueryBindedListPair.RspQueryBindedList) rspData;
					if (rsp.data != null && rsp.data.size() > 0) {
						list = matchList(rsp.data);
					}
				}

				CurrentDevice.instance().devList = list;
				if(cloudCbk != null){
					cloudCbk.onSuccess(rspData);
				}
			}

			@Override
			public void onFailure(ErrorObject err) {
				if(cloudCbk != null){
					cloudCbk.onFailure(err);
				}
			}
		};
		mBsOp.queryBindList(userId, cbk);
	}

	/** 获取ip */
	public void queryDevIp(String devIp, ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		mBsOp.queryDevIp(devIp, cloudCbk);
	}

	/** 查询天气 */
	public void queryWeather(String position, ReqCbk<BaseMsg.RspMsgBase> cbk) {
		mBsOp.queryWeather(position, cbk);
	}

	/** 编辑设备信息 */
	public void editDevInfo(String devId, String nickname, ReqCbk<BaseMsg.RspMsgBase> cbk) {
		mBsOp.editDevInfo(devId, nickname, cbk);
	}

	/** 查询硬件数据 (beiang_firmware) */
	public void queryDevFirmware(DevEntity entity, String key, ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		if (!"beiang_firmware".equals(key)) {
			key = "beiang_firmware";
		}
		mBsOp.queryDevData(entity, key, cloudCbk);
	}

	/** 查询设备数据 (beiang_status) */
	/*public void queryDevData(DevEntity entity, String key, final ReqCbk<RspMsgBase> cloudCbk) {
		ReqCbk<RspMsgBase> cbk = new ReqCbk<RspMsgBase>() {
			@Override
			public void onSuccess(RspMsgBase rspData) {
				if (rspData.isSuccess()) {
					QueryDevDataPair.RspQueryDevData rsp = (QueryDevDataPair.RspQueryDevData) rspData;
					if (rsp.data != null) {
						DevEntity dt = new DevEntity();
						byte[] devValues = null;
						try {
							devValues = Base64.decode(rsp.reply, Base64.DEFAULT);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (devValues != null && devValues.length == 25) {
							dt.airInfo = EParse.parseEairByte(devValues);
						}

						if (dt.airInfo != null) {
							dt.devType = dt.airInfo.getDeviceType();
						}

						CurrentDevice.instance().curDevice = dt;
						cloudCbk.onSuccess(rspData);
						return;
					}
				}
				cloudCbk.onFailure(getErrorObj(ErrorCause.BUSINESS_RESPONSE_CODE_ERROR, 0, "data error!"));
			}

			@Override
			public void onFailure(ErrorObject err) {
				cloudCbk.onFailure(err);
			}
		};

		if (!"beiang_status".equals(key)) {
			key = "beiang_status";
		}
		mBsOp.queryDevData(entity, key, cbk);
	}*/

	/** 查询设备状态 */
	public void queryDevStatus(String devId, String ndevSn, final ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		ReqCbk<BaseMsg.RspMsgBase> cbk = new ReqCbk<BaseMsg.RspMsgBase>() {
			@Override
			public void onSuccess(BaseMsg.RspMsgBase rspData) {
				if (rspData.isSuccess()) {
					QueryDevStatusPair.RspQueryDevStatus rsp = (QueryDevStatusPair.RspQueryDevStatus) rspData;
					if (rsp.data != null) {
						QueryDevStatusPair.RspQueryDevStatus.Data rspDev = rsp.data;
						DevEntity dt = new DevEntity();
						dt.devId = rspDev.devId;
						dt.product = rspDev.product;
						dt.devInfo = rspDev.devInfo;
						dt.deviceSn = rspDev.deviceSn;
						dt.module = rspDev.mod;
						dt.status = rspDev.status;
						dt.role = rspDev.role;
						dt.deviceSn = rspDev.deviceSn;
						byte[] devValues = null;
						try {
							devValues = Base64.decode(rspDev.value, Base64.DEFAULT);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (devValues != null && devValues.length == 25) {
							dt.airInfo = EParse.parseEairByte(devValues);
						}

						if (dt.airInfo != null) {
							dt.devType = dt.airInfo.getDeviceType();
						}

						CurrentDevice.instance().queryDevice = dt;
						cloudCbk.onSuccess(rspData);
						return;
					}
				}

				cloudCbk.onFailure(getErrorObj(ErrorCause.BUSINESS_RESPONSE_CODE_ERROR, 0, "data error!"));
			}

			@Override
			public void onFailure(ErrorObject err) {
				cloudCbk.onFailure(err);
			}
		};
		mBsOp.queryDevStatus(devId, ndevSn, cbk);
	}

	/*** 发送控制命令 */
	public void sendCtrlCmd(String devId, byte[] cmd, ReqCbk<BaseMsg.RspMsgBase> cbk) {
		mBsOp.sendCtrlCmd(devId, cmd, cbk);
	}

	/** 授权 */
	public void Authrorize(DevEntity entity, ReqCbk<BaseMsg.RspMsgBase> cbk) {
		mBsOp.Authrorize(entity, cbk);
	}

	/** 更新授权、昵称等信息 */
	public void upDateAuthrorize(DevEntity entity, String userId, ReqCbk<BaseMsg.RspMsgBase> cbk) {
		mBsOp.upDateAuthrorize(entity, userId, cbk);
	}
	
	/** 查找绑定结果操作 */
	boolean query = false;
	TimerUtil tUtil;

	public void stopQuery() {
		query = false;
		if (tUtil != null) {
			tUtil.stopTimer();
			tUtil = null;
		}
	}

	private void queryBindDevice(final String userId, final String bindCode, final ReqCbk<BaseMsg.RspMsgBase> cloudCbk) {
		query = true;
		tUtil = new TimerUtil(new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				stopQuery();
				cloudCbk.onFailure(new ReqCbk.ErrorObject());
			}
		});
		tUtil.startTimer(Config.SEARCH_TIMEOUT);
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (query) {
					ReqCbk<BaseMsg.RspMsgBase> cbk = new ReqCbk<BaseMsg.RspMsgBase>() {
						@Override
						public void onSuccess(BaseMsg.RspMsgBase rspData) {
							if (rspData.isSuccess()) {
								QueryBindedResultPair.RspQueryBindedResult rsp = (QueryBindedResultPair.RspQueryBindedResult) rspData;
								if (rsp.data != null && !TextUtils.isEmpty(rsp.data.devId)) {
									stopQuery();
									cloudCbk.onSuccess(rspData);
									return;
								}
							}
							if (rspData.errorCode == ServerDefinition.BusinessErr.DEVICE_BINDED || rspData.errorCode == ServerDefinition.BusinessErr.DEVICE_BINDED_OTHER) {
								stopQuery();
								cloudCbk.onSuccess(rspData);
								return;
							}
						}

						@Override
						public void onFailure(ErrorObject err) {
						}
					};
					mBsOp.queryBindDev(userId, bindCode, cbk);
					try {
						sleep(4500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	/** 匹配设备列表 */
	private List<DevEntity> matchList(List<QueryBindedListPair.RspQueryBindedList.Data> rspList) {
		List<DevEntity> list = new ArrayList<DevEntity>();
		DB_Device dbDevice = new DB_Device(BeiAirApplaction.getInstance());
		for (QueryBindedListPair.RspQueryBindedList.Data rspDev : rspList) {
			DevEntity dt = new DevEntity();
			dt.devId = rspDev.devId;
			dt.devInfo = rspDev.devInfo;
			dt.nickName = rspDev.nickName;
			if ("".equals(dt.nickName)) {
				dt.nickName = "贝艾尔";
			}
			dt.status = rspDev.status;
			dt.role = rspDev.role;
			dt.deviceSn = rspDev.deviceSn;
			byte[] devValues = null;
			try {
				devValues = Base64.decode(rspDev.value, Base64.DEFAULT);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (devValues != null && devValues.length == 25) {
				dt.airInfo = EParse.parseEairByte(devValues);
			}

			if (dt.airInfo != null) {
				dt.devType = dt.airInfo.getDeviceType();
				dbDevice.setDevType(dt.devId, dt.devType);
			} else {
				dt.devType = dbDevice.getDevType(dt.devId);
			}

			if ("online".equals(dt.status)) {
				list.add(0, dt);
			} else {
				list.add(dt);
			}
		}
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	

	private static ReqCbk.ErrorObject getErrorObj(ReqCbk.ErrorCause cause, int code, String msg) {
		ReqCbk.ErrorObject err = new ReqCbk.ErrorObject();
		err.cause = cause;
		err.errorCode = code;
		err.errorMsg = msg;
		return err;
	}

}

package com.beiair.net.business.homer;

import com.android.volley.Request.Method;
import com.beiair.net.business.entity.DevEntity;
import com.beiair.net.business.homer.AuthrorizePair.ReqAuthrorize;
import com.beiair.net.business.homer.AuthrorizePair.RspAuthrorize;
import com.beiair.net.httpcloud.aync.abs.AbsReqMsg;
import com.beiair.net.httpcloud.aync.abs.AbsRspMsg;
import com.beiair.net.httpcloud.aync.abs.AbsSmartNetReqRspPair;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.beiair.net.httpcloud.aync.abs.ReqCbk;
import com.beiair.net.httpcloud.aync.ServerDefinition;
import com.beiair.net.httpcloud.aync.abs.BaseMsg;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

/***
 * 授权
 * 
 * @author LSD
 * 
 */
public class AuthrorizePair extends AbsSmartNetReqRspPair<ReqAuthrorize, RspAuthrorize> {
	
	public void sendRequest(DevEntity entity, ReqCbk<RspMsgBase> cbk) {
		ReqAuthrorize req = new ReqAuthrorize(entity);
		sendMsg(req, cbk);
	}

	public static class ReqAuthrorize extends AbsReqMsg {
		@SerializedName("params")
		public AuthrorizePama pama = new AuthrorizePama();
		
		public ReqAuthrorize(DevEntity entity) {
			pama.role = "user";
			pama.devInfo = entity.devInfo;
			pama.devId = entity.devId;
			pama.deviceSn = entity.deviceSn;
			pama.nick_name = "贝艾尔";
			pama.code = "0";
		}
		
		public static class AuthrorizePama{
			@SerializedName("role")
			public String role;

			@SerializedName("device_info")
			public String devInfo;
			
			@SerializedName("ndevice_id")
			public String devId;
			
			@SerializedName("ndevice_sn")
			public String deviceSn;
			
			@SerializedName("nick_name")
			public String nick_name;
			
			@SerializedName("code")
			public String code;
		}

		@Override
		public String getReqMethod() {
			// TODO Auto-generated method stub
			return ServerDefinition.APIServerMethod.HOMER_Authorize.getMethod();
		}
	}

	public static class RspAuthrorize extends AbsRspMsg {
		//
	}

	@Override
	public int getHttpMethod() {
		return Method.POST;
	}

	@Override
	public String getUri() {
		return ServerDefinition.APIServerAdrs.HOMER;
	}

	@Override
	public Class<RspAuthrorize> getResponseType() {
		return RspAuthrorize.class;
	}

	@Override
	public JsonSerializer<ReqAuthrorize> getExcludeJsonSerializer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendMsg(BaseMsg.ReqMsgBase req, ReqCbk<BaseMsg.RspMsgBase> cbk) {
		// TODO Auto-generated method stub
		return sendMsg(req, cbk, req.getTag());
	}

}

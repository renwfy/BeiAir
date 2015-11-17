package com.beiair.net.business.homer;

import com.android.volley.Request.Method;
import com.beiair.net.business.entity.DevEntity;
import com.beiair.net.business.homer.UpdateAuthrorizePair.ReqUpdateAuthrorize;
import com.beiair.net.business.homer.UpdateAuthrorizePair.RspUpdateAuthrorize;
import com.beiair.net.httpcloud.aync.abs.AbsReqMsg;
import com.beiair.net.httpcloud.aync.abs.AbsRspMsg;
import com.beiair.net.httpcloud.aync.abs.AbsSmartNetReqRspPair;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.ReqMsgBase;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.beiair.net.httpcloud.aync.abs.ReqCbk;
import com.beiair.net.httpcloud.aync.ServerDefinition;
import com.beiair.utils.LogUtil;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

/***
 * 修改授权，昵称等
 * 
 * @author LSD
 * 
 */
public class UpdateAuthrorizePair extends AbsSmartNetReqRspPair<ReqUpdateAuthrorize, RspUpdateAuthrorize> {
	
	public void sendRequest(DevEntity entity, String userId, ReqCbk<RspMsgBase> cbk) {
		ReqUpdateAuthrorize req = new ReqUpdateAuthrorize(entity,userId);
		sendMsg(req, cbk);
	}

	public static class ReqUpdateAuthrorize extends AbsReqMsg {
		@SerializedName("params")
		public UpdateAuthrorizePama pama = new UpdateAuthrorizePama();
		
		public ReqUpdateAuthrorize(DevEntity entity,String userId) {
			pama.role = entity.role;
			LogUtil.i("role = "+entity.role);
			pama.devInfo = entity.devInfo;
			pama.devId = entity.devId;
			pama.deviceSn = entity.deviceSn;
			pama.nick_name = entity.nickName;
			LogUtil.i("role = "+entity.nickName);
			pama.userId = userId;
		}
		
		public static class UpdateAuthrorizePama{
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
			
			@SerializedName("user_id")
			public String userId;
		}

		@Override
		public String getReqMethod() {
			// TODO Auto-generated method stub
			return ServerDefinition.APIServerMethod.HOMER_UpdateAuthorize.getMethod();
		}
	}

	public static class RspUpdateAuthrorize extends AbsRspMsg {
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
	public Class<RspUpdateAuthrorize> getResponseType() {
		return RspUpdateAuthrorize.class;
	}

	@Override
	public JsonSerializer<ReqUpdateAuthrorize> getExcludeJsonSerializer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendMsg(ReqMsgBase req, ReqCbk<RspMsgBase> cbk) {
		// TODO Auto-generated method stub
		return sendMsg(req, cbk, req.getTag());
	}

}

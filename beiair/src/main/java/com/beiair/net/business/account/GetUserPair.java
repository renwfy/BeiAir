package com.beiair.net.business.account;

import com.android.volley.Request.Method;
import com.beiair.net.business.account.GetUserPair.ReqGetUser;
import com.beiair.net.business.account.GetUserPair.RspGetUser;
import com.beiair.net.httpcloud.aync.abs.AbsReqMsg;
import com.beiair.net.httpcloud.aync.abs.AbsRspMsg;
import com.beiair.net.httpcloud.aync.abs.AbsSmartNetReqRspPair;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.beiair.net.httpcloud.aync.abs.ReqCbk;
import com.beiair.net.httpcloud.aync.ServerDefinition;
import com.beiair.net.httpcloud.aync.abs.BaseMsg;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

public class GetUserPair extends AbsSmartNetReqRspPair<ReqGetUser, RspGetUser> {

	public void sendRequest(String userId,String userName,ReqCbk<RspMsgBase> cbk) {
		ReqGetUser req = new ReqGetUser(userId,userName);
		sendMsg(req, cbk);
	}

	public static class ReqGetUser extends AbsReqMsg {
		@SerializedName("params")
		public GetUserPama pama = new GetUserPama();
		
		public ReqGetUser(String userId,String userName) {
			// TODO Auto-generated constructor stub
			pama.userId = userId;
			pama.userName = userName;
		}
		
		public static class GetUserPama{
			@SerializedName("user_id")
			public String userId;
			
			@SerializedName("user_name")
			public String userName;
		}
		
		@Override
		public String getReqMethod() {
			// TODO Auto-generated method stub
			return ServerDefinition.APIServerMethod.ACCOUNT_GetUserInfo.getMethod();
		}
	}

	public static class RspGetUser extends AbsRspMsg {

		@SerializedName("data")
		public Data data;

		public static class Data {
			@SerializedName("nick_name")
			public String nickName;
			
			@SerializedName("sex")
			public String sex;
			
			@SerializedName("phone")
			public String phone;
			
			@SerializedName("email")
			public String email;
		}
	}

	@Override
	public String getUri() {
		return ServerDefinition.APIServerAdrs.ACCOUNT;
	}

	@Override
	public Class<RspGetUser> getResponseType() {
		return RspGetUser.class;
	}

	@Override
	public JsonSerializer<ReqGetUser> getExcludeJsonSerializer() {
		return null;
	}

	@Override
	public int getHttpMethod() {
		// TODO Auto-generated method stub
		return Method.POST;
	}

	@Override
	public boolean sendMsg(BaseMsg.ReqMsgBase req, ReqCbk<BaseMsg.RspMsgBase> cbk) {
		// TODO Auto-generated method stub
		return sendMsg(req, cbk, req.getTag());
	}
}

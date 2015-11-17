package com.beiair.net.business.account;

import com.android.volley.Request.Method;
import com.beiair.net.business.account.UpdatePassByAuthPair.ReqUpdatePassByAuth;
import com.beiair.net.business.account.UpdatePassByAuthPair.RspUpdatePassByAuth;
import com.beiair.net.httpcloud.aync.abs.AbsReqMsg;
import com.beiair.net.httpcloud.aync.abs.AbsRspMsg;
import com.beiair.net.httpcloud.aync.abs.AbsSmartNetReqRspPair;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.beiair.net.httpcloud.aync.abs.ReqCbk;
import com.beiair.net.httpcloud.aync.ServerDefinition;
import com.beiair.net.httpcloud.aync.abs.BaseMsg;
import com.beiair.utils.MD5;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

public class UpdatePassByAuthPair extends AbsSmartNetReqRspPair<ReqUpdatePassByAuth, RspUpdatePassByAuth> {

	public void sendRequest(String phone,String userName,String newPass,String code, ReqCbk<RspMsgBase> cbk) {
		ReqUpdatePassByAuth req = new ReqUpdatePassByAuth(phone,userName,newPass,code);
		sendMsg(req, cbk);
	}

	public static class ReqUpdatePassByAuth extends AbsReqMsg {
		@SerializedName("params")
		public UpdatePassByAuthPama pama = new UpdatePassByAuthPama();

		public ReqUpdatePassByAuth(String phone,String userName,String newPass,String code) {
			pama.phone = phone;
			//pama.userName = userName;
			pama.newPass = MD5.encryptMD5(newPass);
			pama.code = code;
		}

		public static class UpdatePassByAuthPama {
			@SerializedName("phone")
			public String phone;
			
			//@SerializedName("user_name")
			//public String userName;
			
			@SerializedName("newpasswd")
			public String newPass;
			
			@SerializedName("code")
			public String code;
		}

		@Override
		public String getReqMethod() {
			// TODO Auto-generated method stub
			return ServerDefinition.APIServerMethod.ACCOUNT_UpdatePassByAuth.getMethod();
		}
	}

	public static class RspUpdatePassByAuth extends AbsRspMsg {

		@SerializedName("data")
		public Data data;

		public static class Data {
		}
	}

	@Override
	public int getHttpMethod() {
		return Method.POST;
	}

	@Override
	public String getUri() {
		return ServerDefinition.APIServerAdrs.ACCOUNT;
	}

	@Override
	public Class<RspUpdatePassByAuth> getResponseType() {
		return RspUpdatePassByAuth.class;
	}

	@Override
	public boolean sendMsg(BaseMsg.ReqMsgBase req, ReqCbk<BaseMsg.RspMsgBase> cbk) {
		// TODO Auto-generated method stub
		return sendMsg(req, cbk, req.getTag());
	}

	@Override
	public JsonSerializer<ReqUpdatePassByAuth> getExcludeJsonSerializer() {
		// TODO Auto-generated method stub
		return null;
	}
}

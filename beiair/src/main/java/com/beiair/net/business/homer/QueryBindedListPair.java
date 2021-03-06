package com.beiair.net.business.homer;

import java.util.List;

import com.android.volley.Request.Method;
import com.beiair.net.business.homer.QueryBindedListPair.ReqQueryBindedResult;
import com.beiair.net.business.homer.QueryBindedListPair.RspQueryBindedList;
import com.beiair.net.httpcloud.aync.abs.AbsReqMsg;
import com.beiair.net.httpcloud.aync.abs.AbsRspMsg;
import com.beiair.net.httpcloud.aync.abs.AbsSmartNetReqRspPair;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.beiair.net.httpcloud.aync.abs.ReqCbk;
import com.beiair.net.httpcloud.aync.ServerDefinition;
import com.beiair.net.httpcloud.aync.abs.BaseMsg;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

public class QueryBindedListPair extends AbsSmartNetReqRspPair<ReqQueryBindedResult, RspQueryBindedList> {

	public void sendRequest(String userId,ReqCbk<RspMsgBase> cbk) {
		ReqQueryBindedResult req = new ReqQueryBindedResult(userId);
		sendMsg(req, cbk);
	}

	public static class ReqQueryBindedResult extends AbsReqMsg {
		@SerializedName("params")
		public QueryBindedListPama pama = new QueryBindedListPama();
		
		public ReqQueryBindedResult(String userId) {
			// TODO Auto-generated constructor stub
			pama.userId = userId;
		}
		
		public static class QueryBindedListPama{
			@SerializedName("user_id")
			public String userId;
		}
		
		@Override
		public String getReqMethod() {
			// TODO Auto-generated method stub
			return ServerDefinition.APIServerMethod.HOMER_GetBind.getMethod();
		}
	}

	public static class RspQueryBindedList extends AbsRspMsg {
		@SerializedName("data")
		public List<Data> data;
		
		public transient byte[] reply;

		public static class Data {
			@SerializedName("ndevice_id")
			public String devId;

			@SerializedName("device_info") 
			public String devInfo;
			
			@SerializedName("nick_name") 
			public String nickName;

			@SerializedName("devicestatus")
			public String status; // online, offline
			
			@SerializedName("role")
			public String role; // owner, user , guest
			
			@SerializedName("ndevice_sn")
			public String deviceSn; // 
			
			@SerializedName("time")
			public String time;
			
			@SerializedName("value")//base64(data)
			public String value;
		}
	}

	@Override
	public String getUri() {
		return ServerDefinition.APIServerAdrs.HOMER;
	}

	@Override
	public Class<RspQueryBindedList> getResponseType() {
		return RspQueryBindedList.class;
	}

	@Override
	public int getHttpMethod() {
		// TODO Auto-generated method stub
		return Method.POST;
	}

	@Override
	public JsonSerializer<ReqQueryBindedResult> getExcludeJsonSerializer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendMsg(BaseMsg.ReqMsgBase req, ReqCbk<BaseMsg.RspMsgBase> cbk) {
		// TODO Auto-generated method stub
		return sendMsg(req, cbk, req.getTag());
	}

}

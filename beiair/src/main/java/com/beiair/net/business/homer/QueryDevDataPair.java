package com.beiair.net.business.homer;

import android.util.Base64;

import com.android.volley.Request.Method;
import com.beiair.net.business.entity.DevEntity;
import com.beiair.net.business.homer.QueryDevDataPair.ReqQueryDevData;
import com.beiair.net.business.homer.QueryDevDataPair.RspQueryDevData;
import com.beiair.net.httpcloud.aync.abs.AbsReqMsg;
import com.beiair.net.httpcloud.aync.abs.AbsRspMsg;
import com.beiair.net.httpcloud.aync.abs.AbsSmartNetReqRspPair;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.beiair.net.httpcloud.aync.abs.ReqCbk;
import com.beiair.net.httpcloud.aync.ServerDefinition;
import com.beiair.net.httpcloud.aync.abs.BaseMsg;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

public class QueryDevDataPair extends AbsSmartNetReqRspPair<ReqQueryDevData, RspQueryDevData> {

	public void sendRequest(DevEntity entity,String key,ReqCbk<RspMsgBase> cbk) {
		ReqQueryDevData req = new ReqQueryDevData(entity,key);
		sendMsg(req, cbk);
	}

	public static class ReqQueryDevData extends AbsReqMsg {
		@SerializedName("params")
		public QueryDevDataPama pama = new QueryDevDataPama();
		
		public ReqQueryDevData(DevEntity dev,String key) {
			// TODO Auto-generated constructor stub
			pama.devId = dev.devId;
			pama.ndeviceSn = dev.deviceSn;
			pama.key = key;
		}
		
		public static class QueryDevDataPama{
			@SerializedName("ndevice_id")
			public String devId;
			
			@SerializedName("ndevice_sn")
			public String ndeviceSn;
			
			@SerializedName("key")
			public String key;
		}
		
		@Override
		public String getReqMethod() {
			// TODO Auto-generated method stub
			return ServerDefinition.APIServerMethod.HOMER_GetDeviceData.getMethod();
		}
	}

	public static class RspQueryDevData extends AbsRspMsg {
		@SerializedName("data")
		public Data data;
		
		public transient byte[] reply;

		public static class Data {
			@SerializedName("value")
			public String value;

			@SerializedName("key")
			public String key;
		}
		
		@Override
		public void onPostRsp() {
			// TODO Auto-generated method stub
			if(data != null){
				reply = Base64.decode(data.value, Base64.DEFAULT);
			}
		}
	}

	@Override
	public String getUri() {
		return ServerDefinition.APIServerAdrs.HOMER;
	}

	@Override
	public Class<RspQueryDevData> getResponseType() {
		return RspQueryDevData.class;
	}

	@Override
	public int getHttpMethod() {
		// TODO Auto-generated method stub
		return Method.POST;
	}

	@Override
	public JsonSerializer<ReqQueryDevData> getExcludeJsonSerializer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendMsg(BaseMsg.ReqMsgBase req, ReqCbk<BaseMsg.RspMsgBase> cbk) {
		// TODO Auto-generated method stub
		return sendMsg(req, cbk, req.getTag());
	}

}

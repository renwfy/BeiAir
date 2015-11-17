package com.beiair.net.business.homer;

import com.android.volley.Request.Method;
import com.beiair.net.business.homer.ResetDevPair.ReqResetDev;
import com.beiair.net.business.homer.ResetDevPair.RspResetDev;
import com.beiair.net.httpcloud.aync.abs.AbsReqMsg;
import com.beiair.net.httpcloud.aync.abs.AbsRspMsg;
import com.beiair.net.httpcloud.aync.abs.AbsSmartNetReqRspPair;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.ReqMsgBase;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.beiair.net.httpcloud.aync.abs.ReqCbk;
import com.beiair.net.httpcloud.aync.ServerDefinition;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

public class ResetDevPair extends AbsSmartNetReqRspPair<ReqResetDev, RspResetDev> {

	public void sendRequest(String devId, ReqCbk<RspMsgBase> cbk) {
		ReqResetDev req = new ReqResetDev(devId);
		sendMsg(req, cbk);
	}

	public static class ReqResetDev extends AbsReqMsg {
		@SerializedName("params")
		public ResetDevPama pama = new ResetDevPama();

		public ReqResetDev(String devId) {
			// TODO Auto-generated constructor stub
			pama.devId = devId;
		}

		public static class ResetDevPama {
			@SerializedName("ndevice_id")
			String devId;
		}

		@Override
		public String getReqMethod() {
			// TODO Auto-generated method stub
			return ServerDefinition.APIServerMethod.HOMER_ResetDevice.getMethod();
		}
	}

	public static class RspResetDev extends AbsRspMsg {
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
	public Class<RspResetDev> getResponseType() {
		return RspResetDev.class;
	}

	@Override
	public JsonSerializer<ReqResetDev> getExcludeJsonSerializer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendMsg(ReqMsgBase req, ReqCbk<RspMsgBase> cbk) {
		// TODO Auto-generated method stub
		return sendMsg(req, cbk, req.getTag());
	}

}

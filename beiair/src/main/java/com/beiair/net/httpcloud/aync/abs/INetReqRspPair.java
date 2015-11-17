package com.beiair.net.httpcloud.aync.abs;

import com.beiair.net.httpcloud.aync.abs.BaseMsg.ReqMsgBase;
import com.beiair.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.google.gson.JsonSerializer;

public interface INetReqRspPair<REQ,RSP> {
	int getHttpMethod();

	String getBaseUrl();

	String getUri();

	Class<RSP> getResponseType();
	
	JsonSerializer<REQ> getExcludeJsonSerializer();
	
	boolean sendMsg(ReqMsgBase req,ReqCbk<RspMsgBase> cbk);
}

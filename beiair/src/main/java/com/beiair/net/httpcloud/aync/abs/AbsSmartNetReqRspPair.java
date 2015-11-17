package com.beiair.net.httpcloud.aync.abs;

import java.io.File;

import com.beiair.net.httpcloud.aync.CloudHttp;
import com.beiair.net.httpcloud.aync.ServerDefinition;

public abstract class AbsSmartNetReqRspPair<REQ, RSP> implements INetReqRspPair<REQ, RSP> {
	public static char SLASH = File.separatorChar;

	@Override
	public String getBaseUrl() {
		return ServerDefinition.getSmarthomeHostUrl();
	}

	private String getFullUrl() {
		StringBuilder url = new StringBuilder(getBaseUrl());
		int lastIndex = url.length() - 1;
		if (url.charAt(lastIndex) == SLASH) {
			url.deleteCharAt(lastIndex);
		}
		String uri = getUri();
		if (uri.charAt(0) != SLASH) {
			url.append(SLASH);
		}
		url.append(uri);
		return url.toString();
	}

	public boolean sendMsg(BaseMsg.ReqMsgBase req, ReqCbk<BaseMsg.RspMsgBase> cbk, Object tag) {
		// TODO Auto-generated method stub
		return CloudHttp.sendRequest(getHttpMethod(), getFullUrl(), req, cbk, getResponseType(), tag);
	}
}

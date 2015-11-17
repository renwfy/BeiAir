package com.beiair.net.business;

import java.util.List;

import com.beiair.net.httpcloud.aync.abs.BaseMsg.RspMsgBase;
import com.beiair.net.httpcloud.aync.abs.ReqCbk;
import com.beiair.net.httpcloud.aync.abs.BaseMsg;

public interface BaseCloudIHomer {
	public void addHome(String name, ReqCbk<RspMsgBase> cbk);

	public void getHome(ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void sendHomeCmd(long home_id, long device_id, String device_sn, byte[] cmd, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void addTime(long home_id, long device_id, String device_sn, int repeat, long secs, List<Integer> week, byte[] cmd,
			ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void getTime(long home_id, long timer_id, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void updateTime(long home_id, long timer_id,long device_id, String device_sn, int repeat, long secs, List<Integer> week, byte[] cmd,ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void delTime(long home_id, long timer_id, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void addScript(long home_id, long device_id, String device_sn, int type, int repeat, long secs, List<Integer> week,
			String user_time, String factor, String msg, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void getScript(long home_id, long script_id, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void updateScript(long home_id, long device_id,long script_id, String device_sn, int type, int repeat, long secs, List<Integer> week,
			String user_time, String factor, String msg, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void delScript(long home_id, long script_id, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void addDevice(long home_id, long device_id, String device_sn, String name, String room, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void updateDevice(long home_id, long device_id, String device_sn, String name, String room, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void getDevice(long home_id, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void delDevice(long home_id, long device_id, String device_sn, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void addUser(long home_id, long user_id, long tar_id, String name, String role, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void getUser(long home_id, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void delUser(long home_id, long user_id, long tar_id, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void addRoom(long home_id, String name, String brief, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void updateRoom(long home_id, long room_id,String name, String brief, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void getRoom(long home_id, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void delRoom(long home_id, long room_id, ReqCbk<BaseMsg.RspMsgBase> cbk);

	public void setMonitor(long home_id, long device_id, String device_sn, ReqCbk<BaseMsg.RspMsgBase> cbk);

}

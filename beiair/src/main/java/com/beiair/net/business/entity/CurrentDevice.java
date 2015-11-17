package com.beiair.net.business.entity;

import java.util.ArrayList;
import java.util.List;

public class CurrentDevice {
	private static CurrentDevice cDevice;

	private CurrentDevice() {
	}

	synchronized public static CurrentDevice instance() {
		if (cDevice == null) {
			synchronized (CurrentDevice.class) {
				if (cDevice == null) {
					cDevice = new CurrentDevice();
				}
			}
		}
		return cDevice;
	}

	public List<DevEntity> devList;

	public DevEntity curDevice;
	
	public DevEntity queryDevice;
	
	public void addList(List<DevEntity> list){
		if(devList == null){
			devList = new ArrayList<DevEntity>();
		}
		devList.addAll(0, list);
		
	}
	
	public void clean(){
		if(cDevice != null){
			cDevice = null;
		}
	}
}

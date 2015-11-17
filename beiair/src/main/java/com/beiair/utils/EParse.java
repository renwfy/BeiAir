package com.beiair.utils;

import com.beiair.ui.model.AirInfo;

public class EParse {

	/***
	 * 
	 * 解析25个的字节
	 * 
	 * byte[] --> AirInfo
	 * 
	 * @param data
	 * @return
	 */
	public static AirInfo parseEairByte(byte[] data) {
		if (data == null) {
			return null;
		}
		AirInfo info = null;
		if (data.length == 25) {
			info = new AirInfo();
			// 设备类型
			byte typeH = data[2];
			byte typeL = data[3];
			info.setDeviceType((Helper.hexadeToDecimal(typeH) * 256 + Helper.hexadeToDecimal(typeL)));

			// 开关机
			byte powerOn = data[4];
			info.setOnoff(Helper.hexadeToDecimal(powerOn));

			// 手自动
			byte isAuto = data[5];
			info.setIsAuto(Helper.hexadeToDecimal(isAuto));

			// 档位
			byte speed = data[6];
			info.setPosition(Helper.hexadeToDecimal(speed));

			// 睡眠状态
			byte sleep = data[7];
			info.setSleep(Helper.hexadeToDecimal(sleep));

			// 儿童锁
			byte childLock = data[8];
			info.setChildLock(Helper.hexadeToDecimal(childLock));

			// 电极运行时间
			byte runTimeH = data[9];// 小时
			byte runTimeM = data[10];// 分钟
			info.setElectRunTimeHour(Helper.hexadeToDecimal(runTimeH));
			info.setElectRunTimeMin(Helper.hexadeToDecimal(runTimeM));

			// 空气质量
			byte airQuality = data[11];
			info.setAirLevel(Helper.hexadeToDecimal(airQuality));

			// 空气质量原始数值
			byte airValueH = data[12];
			byte airValueL = data[13];
			info.setAirValue((Helper.hexadeToDecimal(airValueH) * 256 + Helper.hexadeToDecimal(airValueL)));

			// 光照状态
			byte lightStatu = data[14];
			info.setLight(Helper.hexadeToDecimal(lightStatu));

			// 维护状态
			byte service = data[15];
			info.setErr(Helper.hexadeToDecimal(service));

			// 温度
			byte temp = data[16];
			info.setTem(Helper.hexadeToDecimal(temp));

			// 湿度
			byte humidity = data[17];
			info.setHum(Helper.hexadeToDecimal(humidity));

			// TVOC
			byte tvoc = data[18];
			info.setAirTvoc(Helper.hexadeToDecimal(tvoc));

			// signal信号强度
			byte signal = data[19];
			info.setSignal(Helper.hexadeToDecimal(signal));

			//新风CO2指数
			byte co2levelH = data[20];
			byte co2levell = data[21];
			info.setCo2Levle((Helper.hexadeToDecimal(co2levelH) * 256 + Helper.hexadeToDecimal(co2levell)));

			//新风的内循环切换
			byte freshloop = data[22];
			info.setFreshloop(Helper.hexadeToDecimal(freshloop));
			
			// 预留
			//receive[23];清洁复位

			// receive[24];结束位
		}
		return info;
	}

	/***
	 * 
	 * 解析模型数据 25个字节
	 * 
	 * AirInfo --> byte[]
	 * 
	 * @param info
	 * @return
	 */
	public static byte[] parseEairInfo(AirInfo info) {
		if (info == null) {
			return null;
		}
		byte[] sendB = new byte[25];
		// 针头
		sendB[0] = (byte) 0xfe;
		// 控制设备
		sendB[1] = 0x41;

		// 设备类型
		sendB[2] = 0x00;
		sendB[3] = 0x00;
		if (info != null && info.getDeviceType() != 0) {
			byte[] type = Helper.decimalToHex2Bytes(info.getDeviceType());
			sendB[2] = type[0];
			sendB[3] = type[1];
		}

		// 开关机
		sendB[4] = Helper.ConvertIntTo1byteHexaFormat(info.getOnoff());

		// 手自动
		sendB[5] = Helper.ConvertIntTo1byteHexaFormat(info.getIsAuto());

		// 档位
		sendB[6] = Helper.ConvertIntTo1byteHexaFormat(info.getPosition());

		// 睡眠状态
		sendB[7] = Helper.ConvertIntTo1byteHexaFormat(info.getSleep());

		// 儿童锁
		sendB[8] = Helper.ConvertIntTo1byteHexaFormat(info.getChildLock());

		// 电极运行时间
		sendB[9] = 0x00;// 小时
		sendB[10] = 0x00;// 分钟

		// 电极运行时间
		sendB[9] = Helper.ConvertIntTo1byteHexaFormat(info.getElectRunTimeHour());// 小时
		sendB[10] = Helper.ConvertIntTo1byteHexaFormat(info.getElectRunTimeMin());// 分钟

		// 空气质量
		sendB[11] = 0x00;

		// 空气质量原始数值
		sendB[12] = 0x00;
		sendB[13] = 0x00;

		// 光照状态
		sendB[14] = 0x00;

		// 维护状态
		sendB[15] = 0x00;

		// 温度
		sendB[16] = 0x00;

		// 湿度
		sendB[17] = 0x00;

		// TVOC
		sendB[18] = 0x00;

		// signal信号强度
		sendB[19] = 0x00;

		//CO2指数
		sendB[20] = 0x00;
		sendB[21] = 0x00;

		// 新分内循环切换
		sendB[22] = Helper.ConvertIntTo1byteHexaFormat(info.getFreshloop());

		// 清洁复位
		sendB[23] = Helper.ConvertIntTo1byteHexaFormat(info.getClean());
		// 尾针
		sendB[24] = (byte) 0xaa;

		return sendB;
	}

}

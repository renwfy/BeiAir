package com.beiair.ui.model;

import com.broadlink.beiangtecheair.parse.lib.EairInfo;

/***
 * 原始的模型不能添加属性
 *
 * @author LSD
 */
public class AirInfo extends EairInfo {
    private int airTvoc;// TVOC
    private int signal;// 信号强度
    private int clean;// 清洁复位
    private int co2Levle;//新风CO2指数
    private int freshloop;//新风切换

    public int getAirTvoc() {
        return airTvoc;
    }

    public void setAirTvoc(int airTvoc) {
        this.airTvoc = airTvoc;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public int getClean() {
        return clean;
    }

    public void setClean(int clean) {
        this.clean = clean;
    }

    public int getCo2Levle() {
        return co2Levle;
    }

    public void setCo2Levle(int co2Levle) {
        this.co2Levle = co2Levle;
    }

    public int getFreshloop() {
        return freshloop;
    }

    public void setFreshloop(int freshloop) {
        this.freshloop = freshloop;
    }
}

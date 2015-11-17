package com.beiair.ui.model;

public class FirmwareEntity {
	private String versioncode;
	private String releasenote;
	private String updateaction;
	private String hardwaremodel;
	private String userdefined;

	public String getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(String versioncode) {
		this.versioncode = versioncode;
	}

	public String getReleasenote() {
		return releasenote;
	}

	public void setReleasenote(String releasenote) {
		this.releasenote = releasenote;
	}

	public String getUpdateaction() {
		return updateaction;
	}

	public void setUpdateaction(String updateaction) {
		this.updateaction = updateaction;
	}

	public String getHardwaremodel() {
		return hardwaremodel;
	}

	public void setHardwaremodel(String hardwaremodel) {
		this.hardwaremodel = hardwaremodel;
	}

	public String getUserdefined() {
		return userdefined;
	}

	public void setUserdefined(String userdefined) {
		this.userdefined = userdefined;
	}

}

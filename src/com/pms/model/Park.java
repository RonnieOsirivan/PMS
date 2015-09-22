package com.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Park {
	@Column(name="parkingNumber")
	private int parkNum;
	@Column(name="status")
	private String parkStatus;
	
	public int getParkNum() {
		return parkNum;
	}
	public void setParkNum(int parkNum) {
		this.parkNum = parkNum;
	}
	public String getParkStatus() {
		return parkStatus;
	}
	public void setParkStatus(String parkStatus) {
		this.parkStatus = parkStatus;
	}
}

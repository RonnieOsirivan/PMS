package com.pms.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Bill {
	
	@Column(name="billNumber")
	private int billNum;
	@Column(name="parkingNumber")
	private int parkingNum;
	@Column(name="customerID")
	private int cusID;
	@Column(name="employeeID")
	private int empID;
	@Column(name="timeIn")
	private Timestamp timeIn;
	@Column(name="timeOut")
	private Timestamp timeOut;
	@Column(name="hoursService")
	private int hoursService;
	@Column(name="status")
	private String status;
	
	public int getBillNum() {
		return billNum;
	}
	public void setBillNum(int billNum) {
		this.billNum = billNum;
	}
	public int getParkingNum() {
		return parkingNum;
	}
	public void setParkingNum(int parkingNum) {
		this.parkingNum = parkingNum;
	}
	public int getCusID() {
		return cusID;
	}
	public void setCusID(int cusID) {
		this.cusID = cusID;
	}
	public int getEmpID() {
		return empID;
	}
	public void setEmpID(int empID) {
		this.empID = empID;
	}
	public Timestamp getTimeIn() {
		return timeIn;
	}
	public void setTimeIn(Timestamp timeIn) {
		this.timeIn = timeIn;
	}
	public Timestamp getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(Timestamp timeOut) {
		this.timeOut = timeOut;
	}
	public int getHoursService() {
		return hoursService;
	}
	public void setHoursService(int hoursService) {
		this.hoursService = hoursService;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
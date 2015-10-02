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
	@Column(name="costPerHour")
	private Double costPerHour;
	@Column(name="serviceCost")
	private Double serviceCost;
	@Column(name="customerName")
	private String cusName;
	@Column(name="employeeName")
	private String empName;
	@Column(name="carRegistration")
	private String carRegis;
	
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
	public Double getCostPerHour() {
		return costPerHour;
	}
	public void setCostPerHour(Double costPerHour) {
		this.costPerHour = costPerHour;
	}
	public Double getServiceCost() {
		return serviceCost;
	}
	public void setServiceCost(Double serviceCost) {
		this.serviceCost = serviceCost;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getCarRegis() {
		return carRegis;
	}
	public void setCarRegis(String carRegis) {
		this.carRegis = carRegis;
	}
}
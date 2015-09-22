package com.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Employee {
	@Column(name="employeeID")
	private int employeeID;
	@Column(name="positionID")
	private int positionID;
	@Column(name="employeeFirstName")
	private String employeeFirstName;
	@Column(name="employeeLastName")
	private String employeeLastName;
	@Column(name="phoneNumber")
	private String phoneNumber;
	@Column(name="addressID")
	private int addressID;
	@Column(name="grander")
	private String grander;
	
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public int getPositionID() {
		return positionID;
	}
	public void setPositionID(int positionID) {
		this.positionID = positionID;
	}
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}
	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}
	public String getEmployeeLastName() {
		return employeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getAddressID() {
		return addressID;
	}
	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}
	public String getGrander() {
		return grander;
	}
	public void setGrander(String grander) {
		this.grander = grander;
	}
}

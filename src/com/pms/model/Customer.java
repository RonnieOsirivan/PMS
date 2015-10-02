package com.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Customer {
	@Column(name="customerID")
	private int cusID;
	@Column(name="customerName")
	private String cusName;
	@Column(name="customerFirstName")
	private String cusFirstname;
	@Column(name="customerLastName")
	private String cusLastname;
	@Column(name="phoneNumber")
	private String phone;
	@Column(name="gender")
	private String gender;
	@Column(name="address")
	private String address;
	@Column(name="addressID")
	private int addressID;
	@Column(name="country")
	private String country;
	@Column(name="city")
	private String city;
	@Column(name="zipcode")
	private int zipcode;

	public int getCusID() {
		return cusID;
	}
	public void setCusID(int cusID) {
		this.cusID = cusID;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCusFirstname() {
		return cusFirstname;
	}
	public void setCusFirstname(String cusFirstname) {
		this.cusFirstname = cusFirstname;
	}
	public String getCusLastname() {
		return cusLastname;
	}
	public void setCusLastname(String cusLastname) {
		this.cusLastname = cusLastname;
	}
	public int getAddressID() {
		return addressID;
	}
	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
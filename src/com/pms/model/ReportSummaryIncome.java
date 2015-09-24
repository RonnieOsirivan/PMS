package com.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ReportSummaryIncome {
	@Column(name="customerTypeName")
	private String customerType;
	@Column(name="month")
	private String month;
	@Column(name="sumIncom")
	private Double summaryIncome;
	
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Double getSummaryIncome() {
		return summaryIncome;
	}
	public void setSummaryIncome(Double summaryIncome) {
		this.summaryIncome = summaryIncome;
	}
}

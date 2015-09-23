package com.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ReportMemberVsRelate {
	@Column(name="customerTypeName")
	private String cusTypeName;
	@Column(name="month")
	private String mount;
	@Column(name="sumCost")
	private Double sumCost;
	public String getCusTypeName() {
		return cusTypeName;
	}
	public void setCusTypeName(String cusTypeName) {
		this.cusTypeName = cusTypeName;
	}
	public String getMount() {
		return mount;
	}
	public void setMount(String mount) {
		this.mount = mount;
	}
	public Double getSumCost() {
		return sumCost;
	}
	public void setSumCost(Double sumCost) {
		this.sumCost = sumCost;
	}
}

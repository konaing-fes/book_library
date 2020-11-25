package com.fes.app.entity;

import java.util.List;

public class SaleDTO {
	
	private Sale sale;
	private List<SaleDetial> detial;
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	public List<SaleDetial> getDetial() {
		return detial;
	}
	public void setDetial(List<SaleDetial> detial) {
		this.detial = detial;
	} 

}

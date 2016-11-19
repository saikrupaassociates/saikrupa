package com.saikrupa.app.dto;

import java.util.Date;

public class PriceRowData {
	
	private Double price;
	private Date validFrom;
	private Date validTill;

	public PriceRowData() {
		// TODO Auto-generated constructor stub
		
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTill() {
		return validTill;
	}

	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}

}

package com.saikrupa.app.dto;

import java.util.Date;

public class InventoryData {
	
	private ProductData product;
	private Date lastUpdatedDate;
	
	private Double totalAvailableQuantity;
	private Double totalReservedQuantity;
	private Double totalDamagedQuantity;
	
	private Double addedQuantity;
	private Double addedReservedQuantity;
	private Double rejectedQuantity;
	
	
	private Integer code;
	

	public InventoryData() {
		// TODO Auto-generated constructor stub
	}


	public ProductData getProduct() {
		return product;
	}


	public void setProduct(ProductData product) {
		this.product = product;
	}


	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}


	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}


	


	public Double getAddedQuantity() {
		return addedQuantity;
	}


	public void setAddedQuantity(Double addedQuantity) {
		this.addedQuantity = addedQuantity;
	}


	/**
	 * @return the addedReservedQuantity
	 */
	public Double getAddedReservedQuantity() {
		return addedReservedQuantity;
	}


	/**
	 * @param addedReservedQuantity the addedReservedQuantity to set
	 */
	public void setAddedReservedQuantity(Double addedReservedQuantity) {
		this.addedReservedQuantity = addedReservedQuantity;
	}


	public Integer getCode() {
		return code;
	}


	public void setCode(Integer code) {
		this.code = code;
	}


	public Double getRejectedQuantity() {
		return rejectedQuantity;
	}


	public void setRejectedQuantity(Double rejectedQuantity) {
		this.rejectedQuantity = rejectedQuantity;
	}


	public Double getTotalAvailableQuantity() {
		return totalAvailableQuantity;
	}


	public void setTotalAvailableQuantity(Double totalAvailableQuantity) {
		this.totalAvailableQuantity = totalAvailableQuantity;
	}


	public Double getTotalReservedQuantity() {
		return totalReservedQuantity;
	}


	public void setTotalReservedQuantity(Double totalReservedQuantity) {
		this.totalReservedQuantity = totalReservedQuantity;
	}


	public Double getTotalDamagedQuantity() {
		return totalDamagedQuantity;
	}


	public void setTotalDamagedQuantity(Double totalDamagedQuantity) {
		this.totalDamagedQuantity = totalDamagedQuantity;
	}

}

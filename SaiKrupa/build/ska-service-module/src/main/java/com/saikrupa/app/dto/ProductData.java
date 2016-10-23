package com.saikrupa.app.dto;

public class ProductData {
	
	private String code;
	private String name;
	private String description;
	private boolean saleable;
	private InventoryData inventory;
	
	private PriceRowData priceRow;

	public ProductData() {
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the saleable
	 */
	public boolean isSaleable() {
		return saleable;
	}

	/**
	 * @param saleable the saleable to set
	 */
	public void setSaleable(boolean saleable) {
		this.saleable = saleable;
	}

	public InventoryData getInventory() {
		return inventory;
	}

	public void setInventory(InventoryData inventory) {
		this.inventory = inventory;
	}

	/**
	 * @return the priceRow
	 */
	public PriceRowData getPriceRow() {
		return priceRow;
	}

	/**
	 * @param priceRow the priceRow to set
	 */
	public void setPriceRow(PriceRowData priceRow) {
		this.priceRow = priceRow;
	}

	

}

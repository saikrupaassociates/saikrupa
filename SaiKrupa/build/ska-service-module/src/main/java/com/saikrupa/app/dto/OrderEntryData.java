package com.saikrupa.app.dto;

import java.util.List;

public class OrderEntryData {
	
	private int entryNumber;
	private int orderedQuantity;
	private ProductData product;
	private Double price;
	private Double transportationCost;
	private Double discount;
	private String entryNote;
	private OrderData order;
	
	private List<PaymentEntryData> paymentEntries;
	
	private int code;
	private DeliveryData deliveryData;
	private AddressData deliveryAddress;
	

	public OrderEntryData() {
		
	}

	public int getEntryNumber() {
		return entryNumber;
	}


	public void setEntryNumber(int entryNumber) {
		this.entryNumber = entryNumber;
	}


	public int getOrderedQuantity() {
		return orderedQuantity;
	}


	public void setOrderedQuantity(int orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}


	public ProductData getProduct() {
		return product;
	}


	public void setProduct(ProductData product) {
		this.product = product;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Double getTransportationCost() {
		return transportationCost;
	}


	public void setTransportationCost(Double transportationCost) {
		this.transportationCost = transportationCost;
	}


	public Double getDiscount() {
		return discount;
	}


	public void setDiscount(Double discount) {
		this.discount = discount;
	}


	public String getEntryNote() {
		return entryNote;
	}


	public void setEntryNote(String entryNote) {
		this.entryNote = entryNote;
	}


	public OrderData getOrder() {
		return order;
	}


	public void setOrder(OrderData order) {
		this.order = order;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public DeliveryData getDeliveryData() {
		return deliveryData;
	}


	public void setDeliveryData(DeliveryData deliveryData) {
		this.deliveryData = deliveryData;
	}
	
	public String toString() {
		return code+"|"+entryNumber+"|"+order;
	}

	public AddressData getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(AddressData deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public List<PaymentEntryData> getPaymentEntries() {
		return paymentEntries;
	}

	public void setPaymentEntries(List<PaymentEntryData> paymentEntries) {
		this.paymentEntries = paymentEntries;
	}

}

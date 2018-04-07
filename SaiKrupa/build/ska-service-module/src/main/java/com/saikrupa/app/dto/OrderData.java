package com.saikrupa.app.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderData {

	@Override
	public String toString() {
		return getCode() + " || " + getTotalPrice() + " || "
				+ (getPaymentStatus() == null ? "N/A" : getPaymentStatus().toString()) + " || "
				+ (getDeliveryStatus() == null ? "N/A" : getDeliveryStatus().toString()) + " || "
				+ (getOrderStatus() == null ? "N/A" : getOrderStatus().toString());
	}

	private List<OrderEntryData> orderEntries;
	private Double totalPrice;
	private CustomerData customer;
	private AddressData deliveryAddress;
	private OrderStatus orderStatus;
	private PaymentStatus paymentStatus;
	private PaymentTypeData paymentMode;
	private DeliveryStatus deliveryStatus;

	private String code;
	private Date createdDate;
	private Date lastModifiedDate;	

	public OrderData() {
		orderEntries = new ArrayList<OrderEntryData>();
		setOrderStatus(OrderStatus.CREATED);
		setPaymentStatus(PaymentStatus.PENDING);
	}

	public List<OrderEntryData> getOrderEntries() {
		return orderEntries;
	}

	public void setOrderEntries(List<OrderEntryData> orderEntries) {
		this.orderEntries = orderEntries;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public CustomerData getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerData customer) {
		this.customer = customer;
	}

	public AddressData getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(AddressData deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public PaymentTypeData getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentTypeData paymentMode) {
		this.paymentMode = paymentMode;
	}

	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

}

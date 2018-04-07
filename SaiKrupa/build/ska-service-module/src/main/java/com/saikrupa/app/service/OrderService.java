package com.saikrupa.app.service;

import java.util.List;

import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.PaymentEntryData;

public interface OrderService {
	public OrderData createOrder(OrderData order);
	public void updateOrderStatus(OrderData order);	
	public void updateOrderPayment(OrderData order, List<PaymentEntryData> newEntries, List<PaymentEntryData> customerExistingPayments);
	
}

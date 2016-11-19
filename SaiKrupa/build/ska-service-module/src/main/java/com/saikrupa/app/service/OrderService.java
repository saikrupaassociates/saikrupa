package com.saikrupa.app.service;

import com.saikrupa.app.dto.OrderData;

public interface OrderService {
	public OrderData createOrder(OrderData order);
	public void updateOrderStatus(OrderData order);
	
}

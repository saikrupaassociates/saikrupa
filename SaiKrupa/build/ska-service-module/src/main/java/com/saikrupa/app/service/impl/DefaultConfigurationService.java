package com.saikrupa.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.dto.DeliveryStatus;
import com.saikrupa.app.dto.OrderStatus;
import com.saikrupa.app.dto.PaymentStatus;
import com.saikrupa.app.service.ConfigurationService;

public class DefaultConfigurationService implements ConfigurationService {

	public List<PaymentStatus> getConfiguredPaymentStatuses() {
		List<PaymentStatus> statuses = new ArrayList<PaymentStatus>();
		statuses.add(PaymentStatus.PAID);
		statuses.add(PaymentStatus.PENDING);
		return statuses;
	}

	public List<OrderStatus> getConfiguredOrderStatuses() {
		List<OrderStatus> statuses = new ArrayList<OrderStatus>();
		statuses.add(OrderStatus.CREATED);
		statuses.add(OrderStatus.CONFIRMED);
		statuses.add(OrderStatus.COMPLETED);
		statuses.add(OrderStatus.DELIVERED);
		statuses.add(OrderStatus.PAID);
		statuses.add(OrderStatus.SHIPPED);
		statuses.add(OrderStatus.SHIPPING);
		return statuses;
	}

	public List<DeliveryStatus> getConfiguredDeliveryStatuses() {
		List<DeliveryStatus> statuses = new ArrayList<DeliveryStatus>();
		statuses.add(DeliveryStatus.SHIPPED);
		statuses.add(DeliveryStatus.SHIPPING);
		return statuses;
	}

	

}

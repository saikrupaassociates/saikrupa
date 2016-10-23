package com.saikrupa.app.service;

import java.util.List;

import com.saikrupa.app.dto.DeliveryStatus;
import com.saikrupa.app.dto.OrderStatus;
import com.saikrupa.app.dto.PaymentStatus;

public interface ConfigurationService {
	public List<PaymentStatus> getConfiguredPaymentStatuses();
	public List<OrderStatus> getConfiguredOrderStatuses();
	public List<DeliveryStatus> getConfiguredDeliveryStatuses();
}

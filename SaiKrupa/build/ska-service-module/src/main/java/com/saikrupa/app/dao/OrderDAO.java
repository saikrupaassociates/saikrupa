package com.saikrupa.app.dao;

import java.util.List;

import com.saikrupa.app.dto.DeliveryData;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.OrderEntryData;

public interface OrderDAO {
	public List<OrderData> findAllOrders();
	public List<OrderData> searchOrderWithFilter(final String whereCondition, Object[] params);
	public DeliveryData findDeliveryDetailForEntry(OrderEntryData entry);
}

package com.saikrupa.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.dao.OrderDAO;
import com.saikrupa.app.dao.ProductDAO;
import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.DeliveryData;
import com.saikrupa.app.dto.DeliveryStatus;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.OrderEntryData;
import com.saikrupa.app.dto.PaymentStatus;
import com.saikrupa.app.util.OrderUtil;

public class DefaultOrderDAO implements OrderDAO {

	public List<OrderData> findAllOrders() {
		List<OrderData> orderList = new ArrayList<OrderData>();
		final String sql = "SELECT CODE, ORDER_STATUS, PAYMENT_STATUS, DELIVERY_STATUS, CUSTOMER_CODE, CREATED_DATE FROM COM_ORDER ORDER BY CODE DESC";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		CustomerDAO customerDAO = new DefaultCustomerDAO();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderData data = new OrderData();
				data.setCode(rs.getString(1));
				data.setOrderStatus(OrderUtil.getOrderStatusByCode(rs.getInt(2)));
				data.setPaymentStatus(OrderUtil.getPaymentStatusByCode(rs.getInt(3)));
				data.setDeliveryStatus(OrderUtil.getDeliveryStatusByCode(rs.getInt(4)));
				data.setCustomer(customerDAO.findCustomerByCode(rs.getString(5)));
				data.setCreatedDate((java.util.Date) rs.getDate(6));
				data.setOrderEntries(getOrderEntries(data));
				updateOrderTotalPrice(data);
				data.setDeliveryAddress(data.getCustomer().getAddress());
				orderList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderList;
	}

	private void updateOrderTotalPrice(OrderData data) {
		double totalPrice = 0.0;
		for (OrderEntryData entry : data.getOrderEntries()) {
			totalPrice = totalPrice + (entry.getPrice() * entry.getOrderedQuantity()) + entry.getTransportationCost()
					- entry.getDiscount();
		}
		data.setTotalPrice(totalPrice);
	}

	private List<OrderEntryData> getOrderEntries(OrderData orderData) {
		List<OrderEntryData> orderEntries = new ArrayList<OrderEntryData>();
		final String sql = "SELECT CODE, ENTRY_NO, QUANTITY, PRICE, DELIVERY_COST, DISCOUNT,PRODUCT_CODE FROM COM_ORDER_ENTRY WHERE ORDER_CODE = ? ORDER BY ENTRY_NO ASC";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		ProductDAO productDAO = new DefaultProductDAO();

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, orderData.getCode());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderEntryData data = new OrderEntryData();
				data.setCode(rs.getInt(1));
				data.setEntryNumber(rs.getInt(2));
				data.setOrderedQuantity(rs.getInt(3));
				data.setPrice(rs.getDouble(4));
				data.setTransportationCost(rs.getDouble(5));
				data.setDiscount(rs.getDouble(6));
				data.setProduct(productDAO.findProductByCode(rs.getString(7)));
				data.setOrder(orderData);
				DeliveryData deliveryData = findDeliveryDetailForEntry(data);
				data.setDeliveryData(deliveryData);
				orderEntries.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderEntries;
	}
	
	public DeliveryData findDeliveryDetailForEntry(OrderEntryData entry) {
		final String sql = "SELECT CODE,DELIVERED_QUANTITY, DELIVERY_RECEIPT_NO, VEHICLE_CODE, DELIVERY_DATE "
				+ "FROM COM_ORDER_DELIVERY "
				+ "WHERE ORDER_CODE = ? AND ENTRY_NO=?";
		
		DeliveryData deliveryData = null;
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, entry.getOrder().getCode());
			ps.setInt(2, entry.getEntryNumber());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				deliveryData = new DeliveryData();
				deliveryData.setCode(rs.getInt(1));
				deliveryData.setActualDeliveryQuantity(rs.getDouble(2));
				deliveryData.setDeliveryReceiptNo(rs.getString(3));
				deliveryData.setDeliveryVehicleNo(rs.getString(4));
				deliveryData.setDeliveryDate(new java.sql.Date(rs.getDate(5).getTime()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(deliveryData != null) {
			deliveryData.setOrderEntryData(entry);
		}
		return deliveryData;
	}

	

	public List<OrderData> searchOrderWithFilter(final String condition, Object[] params) {
		List<OrderData> orderList = new ArrayList<OrderData>();
		String searchQuery = "";
		String whereCluse = "";
		if (condition.equals("PENDING") && PaymentStatus.valueOf(condition) == PaymentStatus.PENDING) {
			whereCluse = " PAYMENT_STATUS = 1";
		} else if(condition.equals("SHIPPING") && DeliveryStatus.valueOf(condition) == DeliveryStatus.SHIPPING) {
			whereCluse = " DELIVERY_STATUS = 1";					
		} else if(condition.equals("DELIVERED_PENDING_PAYMENT")) {
			whereCluse = " PAYMENT_STATUS = 1 AND DELIVERY_STATUS = 0";
		} else if(condition.equals("DELIVERY_QUANTITY_MISMATCH")) {
			searchQuery = "SELECT O.CODE, O.ORDER_STATUS, O.PAYMENT_STATUS, O.DELIVERY_STATUS, O.CUSTOMER_CODE, O.CREATED_DATE"
					+ " FROM COM_ORDER O, COM_ORDER_ENTRY E, COM_ORDER_DELIVERY D"
					+ " WHERE O.CODE = E.ORDER_CODE"
					+ " AND E.ENTRY_NO = D.ENTRY_NO"
					+ " AND D.ORDER_CODE = O.CODE"
					+ " AND E.QUANTITY <> D.DELIVERED_QUANTITY";			
		} else if(condition.equals("REPORT_ORDER_BY_CUSTOMER")) {
			searchQuery = "SELECT O.CODE, O.ORDER_STATUS, O.PAYMENT_STATUS, O.DELIVERY_STATUS, O.CUSTOMER_CODE, O.CREATED_DATE, O.CREATED_BY"
					+ " FROM COM_ORDER O "
					+ " WHERE O.CUSTOMER_CODE = ?";

		} else if(condition.equals("REPORT_ORDER_CONSOLIDATED")) {
			searchQuery = "SELECT O.CODE, O.ORDER_STATUS, O.PAYMENT_STATUS, O.DELIVERY_STATUS, O.CUSTOMER_CODE, O.CREATED_DATE, O.CREATED_BY"
					+ " FROM COM_ORDER O ";
		}
		if(searchQuery == null || searchQuery.trim().length() < 1) {
			searchQuery = "SELECT CODE, ORDER_STATUS, PAYMENT_STATUS, DELIVERY_STATUS, CUSTOMER_CODE, CREATED_DATE "
			 		+ "FROM COM_ORDER "
			 		+ "WHERE "+whereCluse
			 		+ " ORDER BY CODE DESC";
		}

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		CustomerDAO customerDAO = new DefaultCustomerDAO();
		try {
			PreparedStatement ps = connection.prepareStatement(searchQuery);
			if(condition.equals("REPORT_ORDER_BY_CUSTOMER")) {
				if(params != null) {
					Integer param = (Integer) params[0];
					ps.setInt(1, param);
				}
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderData data = new OrderData();
				data.setCode(rs.getString(1));
				data.setOrderStatus(OrderUtil.getOrderStatusByCode(rs.getInt(2)));
				data.setPaymentStatus(OrderUtil.getPaymentStatusByCode(rs.getInt(3)));
				data.setDeliveryStatus(OrderUtil.getDeliveryStatusByCode(rs.getInt(4)));
				data.setCustomer(customerDAO.findCustomerByCode(rs.getString(5)));
				data.setCreatedDate((java.util.Date) rs.getDate(6));
				data.setOrderEntries(getOrderEntries(data));
				data.setDeliveryAddress(data.getCustomer().getAddress());
				updateOrderTotalPrice(data);
				orderList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderList;
	}

}

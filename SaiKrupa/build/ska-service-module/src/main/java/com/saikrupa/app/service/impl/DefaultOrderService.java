package com.saikrupa.app.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.AddressData;
import com.saikrupa.app.dto.ApplicationUserData;
import com.saikrupa.app.dto.CustomerData;
import com.saikrupa.app.dto.DeliveryData;
import com.saikrupa.app.dto.DeliveryStatus;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.OrderEntryData;
import com.saikrupa.app.service.CustomerService;
import com.saikrupa.app.service.OrderService;
import com.saikrupa.app.session.ApplicationSession;
import com.saikrupa.app.util.DateUtil;
import com.saikrupa.app.util.OrderUtil;

public class DefaultOrderService implements OrderService {

	public OrderData createOrder(OrderData order) {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		final String sql = "INSERT INTO COM_ORDER (ORDER_STATUS, PAYMENT_STATUS, "
				+ "DELIVERY_STATUS, CUSTOMER_CODE, "
				+ "CREATED_DATE, CREATED_BY, "
				+ "MODIFIED_DATE, LAST_MODIFIED_BY) VALUES(?,?,?,?,?,?,?,?)";
		boolean commit = false;
		try {
			connection.setAutoCommit(false);
			CustomerData customer = createCustomer(order.getCustomer(), connection);

			PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setInt(1, OrderUtil.getOrderStatusCode(order));
			statement.setInt(2, OrderUtil.getPaymentStatusCode(order));
			statement.setInt(3, 1); // Delivery Status
			statement.setInt(4, Integer.valueOf(customer.getCode()));
			statement.setTimestamp(5, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));			
			ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
			statement.setString(6, currentUser.getUserId());
			statement.setTimestamp(7, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
			statement.setString(8, currentUser.getUserId());
			
			int count = statement.executeUpdate();
			if (count > 0) {
				ResultSet keys = statement.getGeneratedKeys();
				keys.next();
				String code = keys.getString(1);
				order.setCode(code);
				order.setCustomer(customer);
				createOrderEntries(order, connection);
				commit = true;
			}
		} catch (SQLException e) {
			commit = false;
			e.printStackTrace();
		} catch (Exception e) {
			commit = false;
			e.printStackTrace();
		} finally {
			if (!commit) {
				try {
					System.out.println("Roll back...");
					connection.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				try {
					connection.commit();
				} catch (SQLException e) {
					System.out.println("Exception while commiting Order ");
					e.printStackTrace();
				}
			}
		}
		return order;
	}

	public void updateOrderStatus(final OrderData order) {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();

		final String sql = "UPDATE COM_ORDER "
				+ "SET ORDER_STATUS = ?, PAYMENT_STATUS = ?, "
				+ "DELIVERY_STATUS = ? , "
				+ "MODIFIED_DATE = ? , LAST_MODIFIED_BY = ? WHERE CODE=?";
		PreparedStatement statement = null;
		try {
			connection.setAutoCommit(false);
			updateOrderEntryDetail(order, connection);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, OrderUtil.getOrderStatusCode(order));
			statement.setInt(2, OrderUtil.getPaymentStatusCode(order));
			statement.setInt(3, OrderUtil.getDeliveryStatusCode(order));
			statement.setTimestamp(4, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
			ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
			statement.setString(5, currentUser.getUserId());
			statement.setString(6, order.getCode());
			int row = statement.executeUpdate();
			if (row < 1) {
				throw new Exception("updateOrderStatus failed to Update Order");
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean deliveryEntryExists(OrderEntryData entry, Connection connection) {
		final String SQL = "SELECT CODE, DELIVERY_DATE FROM COM_ORDER_DELIVERY WHERE ORDER_CODE=? AND ENTRY_NO=?";
		boolean value = false;
		try {
			PreparedStatement ps = connection.prepareStatement(SQL);
			ps.setString(1, entry.getOrder().getCode());
			ps.setInt(2, entry.getEntryNumber());
			ResultSet result = ps.executeQuery();
			if (result != null && result.next()) {
				value = true;
				System.out.println("deliveryEntryExists :: Delivery Entry Date :"+result.getDate(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			value = false;
		}
		return value;
	}

	private void updateOrderEntryDetail(final OrderData order, Connection connection) {
		for (OrderEntryData entry : order.getOrderEntries()) {
			DeliveryData entryDeliveryData = entry.getDeliveryData();
			if (order.getDeliveryStatus() == DeliveryStatus.SHIPPED) {
				if (!deliveryEntryExists(entry, connection)) {					
					createDeliveryEntry(entryDeliveryData, entry, connection);
				}
			}
		}
	}

	private void createDeliveryEntry(DeliveryData entryDeliveryData, OrderEntryData entry, Connection connection) {
		final String SQL_CREATE_DELIVERY_ENTRY = "INSERT INTO COM_ORDER_DELIVERY(ORDER_CODE, ENTRY_NO, DELIVERED_QUANTITY, DELIVERY_RECEIPT_NO, DELIVERY_DATE, VEHICLE_CODE, LAST_MODIFIED_BY) VALUES(?,?,?,?,?,?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(SQL_CREATE_DELIVERY_ENTRY,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, Integer.valueOf(entry.getOrder().getCode()));
			ps.setInt(2, Integer.valueOf(entry.getEntryNumber()));
			ps.setDouble(3, entryDeliveryData.getActualDeliveryQuantity());
			ps.setInt(4, Integer.valueOf(entryDeliveryData.getDeliveryReceiptNo()));
			ps.setDate(5, new java.sql.Date(entryDeliveryData.getDeliveryDate().getTime()));
			ps.setString(6, entryDeliveryData.getDeliveryVehicleNo());
			
			ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
			ps.setString(7, currentUser.getUserId());
			
			int row = ps.executeUpdate();
			if (row < 1) {
				throw new SQLException("createDeliveryEntry failed to Create Order Delivery Entry for Order "
						+ entry.getOrder().getCode());
			}
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			int code = keys.getInt(1);
			entryDeliveryData.setCode(code);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void createOrderEntries(OrderData order, Connection connection) throws Exception {
		final String SQL_CREATE_ORDER_ENTRIES = "insert into COM_ORDER_ENTRY(ENTRY_NO, QUANTITY, PRICE, DELIVERY_COST, DISCOUNT, NOTES, ORDER_CODE, PRODUCT_CODE, LAST_MODIFIED_BY) values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(SQL_CREATE_ORDER_ENTRIES,
				PreparedStatement.RETURN_GENERATED_KEYS);
		for (OrderEntryData entry : order.getOrderEntries()) {
			ps.setInt(1, entry.getEntryNumber());
			ps.setDouble(2, entry.getOrderedQuantity());
			ps.setDouble(3, entry.getPrice());
			ps.setDouble(4, entry.getTransportationCost());
			ps.setDouble(5, entry.getDiscount());
			ps.setString(6, entry.getEntryNote());
			ps.setString(7, order.getCode());
			ps.setString(8, entry.getProduct().getCode());
			
			ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
			ps.setString(9, currentUser.getUserId());

			int count = ps.executeUpdate();
			if (count > 0) {
				ResultSet keys = ps.getGeneratedKeys();
				keys.next();
				int code = keys.getInt(1);
				entry.setCode(code);
				updateProductInventory(entry, connection);
				createInventoryEntryForOrder(entry, connection);
			}
		}
	}

	private void updateProductInventory(OrderEntryData entry, Connection connection) throws Exception {
		final String SQL_UPDATE_INVENTORY = "UPDATE INVENTORY SET TOTAL_AVAILABLE_QUANTITY = ?, LAST_MODIFIED_BY = ? WHERE PRODUCT_CODE=?";
		double newQuantity = entry.getProduct().getInventory().getTotalAvailableQuantity() - entry.getOrderedQuantity();
		PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_INVENTORY);
		statement.setDouble(1, newQuantity);
		statement.setString(2, "SYSTEM");
		statement.setString(3, entry.getProduct().getCode());
		int resultCount = statement.executeUpdate();
		if (resultCount > 0) {
			System.out.println("Inventory reduced to " + newQuantity + " for product " + entry.getProduct().getName());
		}
	}
	
	private void createInventoryEntryForOrder(OrderEntryData orderEntry, Connection connection) {
		PreparedStatement ps = null;
		String sql = "INSERT INTO INVENTORY_ENTRY (INVENTORY_CODE, CREATED_DATE, QUANTITY_ADDED, QUANTITY_REDUCED, QUANTITY_RESERVED, QUANTITY_DAMAGED, LAST_MODIFIED_BY) VALUES(?,?,?,?,?,?,?)";
		try {
			ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, orderEntry.getProduct().getInventory().getCode());
			ps.setTimestamp(2, DateUtil.createCurrentTimeStamp());
			ps.setDouble(3, Double.valueOf("0.0"));
			ps.setDouble(4, orderEntry.getOrderedQuantity()); 
			ps.setDouble(5, Double.valueOf("0.0"));
			ps.setDouble(6, Double.valueOf("0.0"));
			
			ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
			ps.setString(7, currentUser.getUserId());
			
			ps.executeUpdate();
			ps.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private AddressData createCustomerAddress(CustomerData customer, Connection connection) throws Exception {

		final String SQL_CREATE_CUSTOMER_ADDRESS = "INSERT INTO CUSTOMER_ADDRESS(ADDRESS_LINE1, ADDRESS_LINE2,ADDRESS_LANDMARK, ADDRESS_ZIP ,CUSTOMER_CODE, ADDRESS_TYPE) VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement addressStatement = connection.prepareStatement(SQL_CREATE_CUSTOMER_ADDRESS,
					PreparedStatement.RETURN_GENERATED_KEYS);
			addressStatement.setString(1, customer.getAddress().getLine1());
			addressStatement.setString(2, customer.getAddress().getLine2());
			addressStatement.setString(3, customer.getAddress().getLandmark());
			addressStatement.setString(4, customer.getAddress().getZipCode());
			addressStatement.setString(5, customer.getCode());
			addressStatement.setInt(6, 0); // delivery Address

			int addrCount = addressStatement.executeUpdate();

			if (addrCount > 0) {
				ResultSet addressKey = addressStatement.getGeneratedKeys();
				addressKey.next();
				String addressCode = addressKey.getString(1);
				customer.getAddress().setCode(addressCode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer.getAddress();
	}

	private CustomerData createCustomer(CustomerData customer, Connection connection) throws Exception {

		if (customer.getCode() == null) {
			final String SQL_CREATE_CUSTOMER = "INSERT INTO CUSTOMER (NAME, CONTACT_PRIMARY, CONTACT_SECONDARY, LAST_MODIFIED_BY) VALUES(?,?,?,?)";
			try {
				PreparedStatement statement = connection.prepareStatement(SQL_CREATE_CUSTOMER,
						PreparedStatement.RETURN_GENERATED_KEYS);
				statement.setString(1, customer.getName());
				statement.setString(2, customer.getPrimaryContact());
				statement.setString(3, customer.getSecondaryContact());
				ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
				statement.setString(4, currentUser.getUserId());
				
				int count = statement.executeUpdate();
				if (count > 0) {
					ResultSet keys = statement.getGeneratedKeys();
					keys.next();
					String code = keys.getString(1);
					customer.setCode(code);
					customer.setAddress(createCustomerAddress(customer, connection));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			CustomerService customerService = new DefaultCustomerService();
			customer = customerService.getCustomerByCode(customer.getCode());
		}

		return customer;
	}

}

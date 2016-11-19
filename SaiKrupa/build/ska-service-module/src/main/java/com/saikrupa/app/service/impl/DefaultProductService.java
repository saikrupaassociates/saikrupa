package com.saikrupa.app.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.ApplicationUserData;
import com.saikrupa.app.dto.InventoryData;
import com.saikrupa.app.dto.InventoryEntryData;
import com.saikrupa.app.dto.ProductData;
import com.saikrupa.app.service.ProductService;
import com.saikrupa.app.session.ApplicationSession;
import com.saikrupa.app.util.DateUtil;

public class DefaultProductService implements ProductService {

	public DefaultProductService() {
		// TODO Auto-generated constructor stub
	}

	public void updateProduct(ProductData product) {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		PreparedStatement ps = null;
		try {						
			final String sql = "UPDATE PRODUCT SET NAME=?, DESCRIPTION=?, SALEABLE=? WHERE CODE=?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, product.getName());
			ps.setString(2, product.getDescription());
			ps.setInt(3, product.isSaleable() ? 0 : 1);
			ps.setString(4, product.getCode());
			int rowCount = ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void createInventoryEntryForProduct(InventoryEntryData entry, Connection connection) {
		PreparedStatement ps = null;
		String sql = "INSERT INTO INVENTORY_ENTRY (INVENTORY_CODE, CREATED_DATE, QUANTITY_ADDED, QUANTITY_REDUCED, QUANTITY_RESERVED, QUANTITY_DAMAGED, LAST_MODIFIED_BY) VALUES(?,?,?,?,?,?,?)";
		try {
			ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, entry.getInventory().getCode());
			ps.setTimestamp(2, DateUtil.createCurrentTimeStamp());
			ps.setDouble(3, entry.getAddedQuantity());
			ps.setDouble(4, Double.valueOf("0.0")); //Product Update - reduced Quantity is Zero
			ps.setDouble(5, entry.getReservedQuantity());
			ps.setDouble(6, entry.getDamagedQuantity());
			ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
			ps.setString(7, currentUser.getUserId());
			
			int rowCount = ps.executeUpdate();
			if(rowCount > 0) {
				ResultSet keys = ps.getGeneratedKeys();
				keys.next();
				String code = keys.getString(1);
				entry.setCode(Integer.valueOf(code));
			}
			ps.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void updateInventory(InventoryEntryData inventoryEntry) {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		
		PreparedStatement ps = null;
		String sql = null;
		try {
			connection.setAutoCommit(false);
			createInventoryEntryForProduct(inventoryEntry, connection);
			
			InventoryData inventoryHeader = inventoryEntry.getInventory();
			inventoryHeader.setTotalAvailableQuantity(inventoryHeader.getTotalAvailableQuantity() + inventoryEntry.getAddedQuantity());
			inventoryHeader.setTotalReservedQuantity(inventoryHeader.getTotalReservedQuantity() + inventoryEntry.getReservedQuantity());
			inventoryHeader.setTotalDamagedQuantity(inventoryHeader.getTotalDamagedQuantity() + inventoryEntry.getDamagedQuantity());
			
			sql = "UPDATE INVENTORY SET TOTAL_AVAILABLE_QUANTITY=?, TOTAL_RESERVED_QUANTITY=?, TOTAL_DAMAGED_QUANTITY=?, LAST_UPDATED_DATE=?, LAST_MODIFIED_BY = ? WHERE PRODUCT_CODE=?";			
			ps = connection.prepareStatement(sql);
			ps.setDouble(1, inventoryHeader.getTotalAvailableQuantity());
			ps.setDouble(2, inventoryHeader.getTotalReservedQuantity());
			ps.setDouble(3, inventoryHeader.getTotalDamagedQuantity());
			ps.setTimestamp(4, DateUtil.createCurrentTimeStamp());
			
			ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
			ps.setString(5, currentUser.getUserId());
			
			ps.setString(6, inventoryHeader.getProduct().getCode());
			int rowCount = ps.executeUpdate();
			if(rowCount > 0) {
				connection.commit();
			}
			ps.close();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

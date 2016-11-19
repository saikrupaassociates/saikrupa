package com.saikrupa.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.saikrupa.app.dao.EmployeeDAO;
import com.saikrupa.app.dao.ProductDAO;
import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.InventoryData;
import com.saikrupa.app.dto.PriceRowData;
import com.saikrupa.app.dto.ProductData;
import com.saikrupa.app.service.ProductPriceService;
import com.saikrupa.app.service.impl.DefaultProductPriceService;
import com.saikrupa.app.util.DateUtil;

public class DefaultProductDAO implements ProductDAO {

	public DefaultProductDAO() {
		// TODO Auto-generated constructor stub
	}

	public List<ProductData> findAllProducts() {
		List<ProductData> productList = new ArrayList<ProductData>();
		final String sql = "SELECT CODE, NAME, DESCRIPTION, SALEABLE FROM PRODUCT";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		ProductPriceService priceService = new DefaultProductPriceService();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductData data = new ProductData();
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
				data.setDescription(rs.getString(3));
				data.setSaleable(rs.getInt(4) == 0 ? true : false);

				InventoryData inventory = findInventoryLevelByProduct(data);
				
				if (inventory == null) {
					inventory = new InventoryData();
					inventory.setTotalAvailableQuantity(Double.valueOf(0));
					inventory.setTotalReservedQuantity(Double.valueOf(0));
					inventory.setTotalDamagedQuantity(Double.valueOf(0));
					Calendar cal = Calendar.getInstance();					
					inventory.setLastUpdatedDate(cal.getTime());
				}				
				data.setInventory(inventory);
				PriceRowData priceRow = priceService.getBestMatchingUnitPrice(data.getCode());
				if (priceRow != null) {
					data.setPriceRow(priceRow);
				}
				productList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productList;
	}

	public ProductData findProductByCode(String productCode) {
		final String sql = "SELECT CODE, NAME, DESCRIPTION, SALEABLE FROM PRODUCT WHERE CODE=?";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		ProductPriceService priceService = new DefaultProductPriceService();
		ProductData data = null;
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, productCode);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				data = new ProductData();
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
				data.setDescription(rs.getString(3));
				data.setSaleable(rs.getInt(4) == 0 ? true : false);

				InventoryData inventory = findInventoryLevelByProduct(data);
				if (inventory == null) {
					inventory = new InventoryData();
					inventory.setTotalAvailableQuantity(Double.valueOf(0));
					inventory.setTotalReservedQuantity(Double.valueOf(0));
					inventory.setTotalDamagedQuantity(Double.valueOf(0));
					inventory.setLastUpdatedDate(new java.util.Date());
				}
				data.setInventory(inventory);

				PriceRowData priceRow = priceService.getBestMatchingUnitPrice(data.getCode());
				if (priceRow != null) {
					data.setPriceRow(priceRow);
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public InventoryData findInventoryLevelByProduct(ProductData productData) {
		final String sql = "SELECT I.CODE, I.TOTAL_AVAILABLE_QUANTITY, I.TOTAL_RESERVED_QUANTITY, I.TOTAL_DAMAGED_QUANTITY, I.LAST_UPDATED_DATE, I.LAST_MODIFIED_BY FROM INVENTORY I, PRODUCT P WHERE P.CODE=I.PRODUCT_CODE AND P.CODE=? ORDER BY I.LAST_UPDATED_DATE DESC";
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		List<InventoryData> invList = new ArrayList<InventoryData>();
		EmployeeDAO empDAO = new DefaultEmployeeDAO();	
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, productData.getCode());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				InventoryData data = new InventoryData();
				data.setCode(rs.getInt(1));
				data.setTotalAvailableQuantity(rs.getDouble(2));
				data.setTotalReservedQuantity(rs.getDouble(3));				
				data.setTotalDamagedQuantity(rs.getDouble(4));
				data.setLastUpdatedDate(DateUtil.convertDate(rs.getTimestamp(5)));				 
				data.setLastUpdatedBy(empDAO.findEmployeeByCode(rs.getString(6)));
				data.setProduct(productData);
				invList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (invList.isEmpty() ? null : invList.get(0));
	}
}

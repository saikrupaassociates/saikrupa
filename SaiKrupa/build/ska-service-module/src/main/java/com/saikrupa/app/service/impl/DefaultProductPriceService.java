package com.saikrupa.app.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.PriceRowData;
import com.saikrupa.app.service.ProductPriceService;

public class DefaultProductPriceService implements ProductPriceService {

	public DefaultProductPriceService() {
		// TODO Auto-generated constructor stub
	}

	public PriceRowData getBestMatchingUnitPrice(String productCode) {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		PreparedStatement ps = null;
		PriceRowData data = null;
		try {
			final String sql = "SELECT PR.BASEPRICE, PR.VALID_FROM, PR.VALID_TILL FROM PRODUCT_PRICE_ROW PR, PRODUCT P WHERE P.CODE = ? AND P.CODE=PR.PRODUCT_CODE";
			ps = connection.prepareStatement(sql);
			ps.setString(1, productCode);			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				data = new PriceRowData();
				data.setPrice(rs.getDouble(1));
				data.setValidFrom(rs.getDate(2));
				data.setValidTill(rs.getDate(3));
			}
			
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
		return data;
	}

}

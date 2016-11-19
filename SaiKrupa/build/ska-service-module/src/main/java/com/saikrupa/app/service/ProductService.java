package com.saikrupa.app.service;

import com.saikrupa.app.dto.InventoryEntryData;
import com.saikrupa.app.dto.ProductData;

public interface ProductService {
	public void updateProduct(ProductData product);
	public void updateInventory(InventoryEntryData product);
}

package com.saikrupa.app.service;

import com.saikrupa.app.dto.PriceRowData;

public interface ProductPriceService {
	public PriceRowData getBestMatchingUnitPrice(String productCode);
	 
}

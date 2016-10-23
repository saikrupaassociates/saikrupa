package com.saikrupa.app.service;

import java.util.List;

import com.saikrupa.app.dto.VendorData;

public interface VendorService {
	public VendorData createVendor(VendorData vendorData) throws Exception;
	public VendorData updateVendor(VendorData vendorData) throws Exception;
	public List<VendorData> getAllVendors();
	public VendorData findVendorByCode(String code);
}

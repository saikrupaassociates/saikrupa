package com.saikrupa.app.dao;

import java.util.List;

import com.saikrupa.app.dto.InvestorData;

public interface InvestorDAO {
	public InvestorData findInvestorByCode(final String code);
	public List<InvestorData> findInvestors();
	
	
}

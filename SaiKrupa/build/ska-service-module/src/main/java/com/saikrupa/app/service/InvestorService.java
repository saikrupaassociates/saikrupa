package com.saikrupa.app.service;

import java.util.List;

import com.saikrupa.app.dto.InvestmentData;
import com.saikrupa.app.dto.InvestorData;

public interface InvestorService {
	public InvestorData createInvestor(InvestorData investor) throws Exception;
	public InvestorData updateInvestor(InvestorData investor) throws Exception;
	
	public List<InvestmentData> addInvestments(List<InvestmentData> investments) throws Exception;
	public InvestmentData addInvestment(InvestmentData investment) throws Exception;
}

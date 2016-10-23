package com.saikrupa.app.dto;

import java.util.List;

public class InvestorData extends ContactPerson {	
	private List<InvestmentData> investments;

	public List<InvestmentData> getInvestments() {
		return investments;
	}

	public void setInvestments(List<InvestmentData> investments) {
		this.investments = investments;
	}
}

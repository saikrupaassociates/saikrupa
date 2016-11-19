package com.saikrupa.app.dto;

import java.util.Date;

public class InvestmentData {
	
	private String code;
	private String name;
	private double amount;
	private Date investmentDate;
	private InvestorData investor;

	public InvestmentData() {
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getInvestmentDate() {
		return investmentDate;
	}

	public void setInvestmentDate(Date investmentDate) {
		this.investmentDate = investmentDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public InvestorData getInvestor() {
		return investor;
	}

	public void setInvestor(InvestorData investor) {
		this.investor = investor;
	}

}

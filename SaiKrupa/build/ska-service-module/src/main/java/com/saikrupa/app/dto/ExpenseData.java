package com.saikrupa.app.dto;

import java.util.Date;

public class ExpenseData {
	
	@Override
	public String toString() {		
		return expenseType.getName() +"-" +expenseType.getCode()
		+"||"+amount+"||"+expenseDate.toString();
	}

	private ExpenseTypeData expenseType;
	private Double amount;
	private Date expenseDate;
	
	private String code;
	private VendorData vendor;
	private PaymentTypeData paymentData;	
	private InvestorData paidBy;	
	private String comments;
	private EmployeeData updatedBy;
	private EmployeeData createdBy;
	
	private Date createdDate;
	private Date lastModifedDate;
	
	public ExpenseData() {
		
		
	}

	public ExpenseTypeData getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseTypeData expenseType) {
		this.expenseType = expenseType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public VendorData getVendor() {
		return vendor;
	}

	public void setVendor(VendorData vendor) {
		this.vendor = vendor;
	}

	public PaymentTypeData getPaymentData() {
		return paymentData;
	}

	public void setPaymentData(PaymentTypeData paymentData) {
		this.paymentData = paymentData;
	}

	public InvestorData getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(InvestorData paidBy) {
		this.paidBy = paidBy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public EmployeeData getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(EmployeeData updatedBy) {
		this.updatedBy = updatedBy;
	}

	public EmployeeData getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeData createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifedDate() {
		return lastModifedDate;
	}

	public void setLastModifedDate(Date lastModifedDate) {
		this.lastModifedDate = lastModifedDate;
	}

}

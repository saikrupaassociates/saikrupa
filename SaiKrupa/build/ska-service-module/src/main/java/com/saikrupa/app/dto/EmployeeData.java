package com.saikrupa.app.dto;

import java.util.Date;
import java.util.List;

public class EmployeeData extends VendorData {
	
	private Date joiningDate;
	private double effectiveSalary;
	private List<EmployeeSalaryData> revisions;
	private boolean active;	
	private ApplicationRole role;
	

	public EmployeeData() {
		setLoginDisabled(false);
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public double getEffectiveSalary() {
		return effectiveSalary;
	}

	public void setEffectiveSalary(double effectiveSalary) {
		this.effectiveSalary = effectiveSalary;
	}

	public List<EmployeeSalaryData> getRevisions() {
		return revisions;
	}

	public void setRevisions(List<EmployeeSalaryData> revisions) {
		this.revisions = revisions;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ApplicationRole getRole() {
		return role;
	}

	public void setRole(ApplicationRole role) {
		this.role = role;
	}
}

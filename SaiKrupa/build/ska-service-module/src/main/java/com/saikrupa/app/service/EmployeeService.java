package com.saikrupa.app.service;

import com.saikrupa.app.dto.EmployeeData;

public interface EmployeeService {
	public EmployeeData createEmployee(EmployeeData employee) throws Exception ;	
	public void updateEmployee(EmployeeData employee) throws Exception ;
}

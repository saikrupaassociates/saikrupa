package com.saikrupa.app.service;

import com.saikrupa.app.dto.EmployeeData;
import com.saikrupa.app.dto.EmployeeSalaryData;

public interface EmployeeService {
	public EmployeeData createEmployee(EmployeeData employee) throws Exception ;	
	public void updateEmployee(EmployeeData employee) throws Exception ;
}

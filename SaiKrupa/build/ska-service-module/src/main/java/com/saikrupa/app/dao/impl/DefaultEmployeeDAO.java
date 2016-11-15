package com.saikrupa.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.dao.EmployeeDAO;
import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.EmployeeData;
import com.saikrupa.app.dto.EmployeeSalaryData;

public class DefaultEmployeeDAO implements EmployeeDAO {

	public DefaultEmployeeDAO() {
		
	}

	public List<EmployeeData> getAllEmployees() {
		final String query = "SELECT CODE, NAME, CONTACT_PRIMARY, CONTACT_SECONDARY, ACTIVE, JOINING_DATE FROM EMPLOYEE";
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		List<EmployeeData> employees = new ArrayList<EmployeeData>();
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				EmployeeData data = new EmployeeData();
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
				data.setPrimaryContactNo(rs.getString(3));
				data.setSecondaryContactNo(rs.getString(4));
				data.setActive((rs.getInt(5) == 1) ? true : false);
				data.setJoiningDate((java.util.Date)rs.getTimestamp(6));
				data.setRevisions(findRevisionsByEmployee(data));
				employees.add(data);
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return employees;
	}

	public EmployeeData findEmployeeByCode(String code) {
		final String query = "SELECT CODE, NAME, CONTACT_PRIMARY, CONTACT_SECONDARY, ACTIVE, JOINING_DATE FROM EMPLOYEE WHERE CODE = ?";
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		EmployeeData data = null;
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				data = new EmployeeData();
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
				data.setPrimaryContactNo(rs.getString(3));
				data.setSecondaryContactNo(rs.getString(4));
				data.setActive((rs.getInt(5) == 1) ? true : false);
				data.setJoiningDate(rs.getTimestamp(6));				
				data.setRevisions(findRevisionsByEmployee(data));				
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}

	public List<EmployeeSalaryData> findRevisionsByEmployee(EmployeeData employee) {
		final String query = "SELECT CODE, EFFECTIVE_FROM, EFFECTIVE_TILL, SALARY, ACTIVE_REVISION FROM EMPLOYEE_SALARY WHERE EMPLOYEE_CODE = ?";
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		List<EmployeeSalaryData> revisions = new ArrayList<EmployeeSalaryData>();
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, employee.getCode());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				EmployeeSalaryData data = new EmployeeSalaryData();
				data.setCode(rs.getString(1));					
				data.setEffectiveFrom(rs.getTimestamp(2));
				data.setEffectiveTill(rs.getTimestamp(3));
				data.setSalary(rs.getDouble(4));
				data.setCurrentRevision((rs.getInt(5) == 1) ? true : false);
				data.setEmployee(employee);
				revisions.add(data);
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return revisions;
	}

}

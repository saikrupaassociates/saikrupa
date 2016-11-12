package com.saikrupa.app.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.EmployeeData;
import com.saikrupa.app.dto.EmployeeSalaryData;
import com.saikrupa.app.service.EmployeeService;

public class DefaultEmployeeService implements EmployeeService {

	public DefaultEmployeeService() {
		// TODO Auto-generated constructor stub
	}

	public EmployeeData createEmployee(EmployeeData employee) throws Exception {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		connection.setAutoCommit(false);

		final String SQL_CREATE_EMPLOYEE = "INSERT INTO EMPLOYEE (NAME, CONTACT_PRIMARY, CONTACT_SECONDARY, ACTIVE, JOINING_DATE) VALUES(?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(SQL_CREATE_EMPLOYEE,
				PreparedStatement.RETURN_GENERATED_KEYS);

		statement.setString(1, employee.getName());
		statement.setString(2, employee.getPrimaryContactNo());
		statement.setString(3, employee.getPrimaryContactNo());
		statement.setInt(4, employee.isActive() ? 1 : -1); // Active :: -1 is
															// inactive
		statement.setDate(5, new java.sql.Date(employee.getJoiningDate().getTime()));
		int rowCount = statement.executeUpdate();
		if (rowCount > 0) {
			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			String code = keys.getString(1);
			employee.setCode(code);
			connection.commit();
		} else {
			connection.rollback();
			statement.close();
			throw new Exception("Employee Data could not be persisted");
		}
		statement.close();
		return employee;
	}

	private void reviseSalary(EmployeeSalaryData salaryData, Connection connection) throws Exception {
		final String SQL_CREATE_EMPLOYEE = "INSERT INTO EMPLOYEE_SALARY (EMPLOYEE_CODE, EFFECTIVE_FROM, EFFECTIVE_TILL, SALARY, ACTIVE_REVISION) VALUES(?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(SQL_CREATE_EMPLOYEE,
				PreparedStatement.RETURN_GENERATED_KEYS);

		statement.setString(1, salaryData.getEmployee().getCode());
		statement.setDate(2, new java.sql.Date(salaryData.getEffectiveFrom().getTime()));
		if (salaryData.getEffectiveTill() == null) {
			statement.setDate(3, null);
		} else {
			statement.setDate(3, new java.sql.Date(salaryData.getEffectiveTill().getTime()));
		}
		statement.setDouble(4, salaryData.getSalary());
		statement.setInt(5, (salaryData.isCurrentRevision()) ? 1 : -1);
		int rowCount = statement.executeUpdate();
		if (rowCount > 0) {
			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			String code = keys.getString(1);
			salaryData.setCode(code);
		} else {
			connection.rollback();
			statement.close();
			throw new Exception("Employee Salary Data could not be persisted");
		}
	}

	public void updateEmployee(EmployeeData employee) throws Exception {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		connection.setAutoCommit(false);

		final String SQL_UPDATE_EMPLOYEE = "UPDATE EMPLOYEE set NAME = ?, "
				+ "CONTACT_PRIMARY = ?, CONTACT_SECONDARY = ?, ACTIVE = ?, JOINING_DATE= ? WHERE CODE=?";
		PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EMPLOYEE);

		statement.setString(1, employee.getName());
		statement.setString(2, employee.getPrimaryContactNo());
		statement.setString(3, employee.getPrimaryContactNo());
		statement.setInt(4, employee.isActive() ? 1 : -1); // Active :: -1 is
															// inactive
		statement.setDate(5, new java.sql.Date(employee.getJoiningDate().getTime()));
		statement.setString(6, employee.getCode());
		int rowCount = statement.executeUpdate();
		if (rowCount > 0) {
			if (!employee.getRevisions().isEmpty()) {
				for (EmployeeSalaryData revision : employee.getRevisions()) {
					if (revision.getCode() == null) {
						try {
							reviseSalary(revision, connection);
							connection.commit();
						} catch (Exception e) {
							connection.rollback();
							throw e;
						}
					}
				}
			}
		} else {
			connection.rollback();
			System.out.println("updateEmployee :: Update Failed");
		}
	}

}
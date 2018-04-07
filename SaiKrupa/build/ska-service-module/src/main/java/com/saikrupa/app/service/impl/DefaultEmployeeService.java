package com.saikrupa.app.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.ApplicationRole;
import com.saikrupa.app.dto.ApplicationUserData;
import com.saikrupa.app.dto.EmployeeData;
import com.saikrupa.app.dto.EmployeeSalaryData;
import com.saikrupa.app.security.SecurityUtil;
import com.saikrupa.app.service.EmployeeService;
import com.saikrupa.app.session.ApplicationSession;

public class DefaultEmployeeService implements EmployeeService {

	public DefaultEmployeeService() {
		// TODO Auto-generated constructor stub
	}

	public EmployeeData createEmployee(EmployeeData employee) throws Exception {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		connection.setAutoCommit(false);

		final String SQL_CREATE_EMPLOYEE = "INSERT INTO EMPLOYEE (NAME, CONTACT_PRIMARY, CONTACT_SECONDARY, ACTIVE, JOINING_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) VALUES(?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(SQL_CREATE_EMPLOYEE,
				PreparedStatement.RETURN_GENERATED_KEYS);

		statement.setString(1, employee.getName());
		statement.setString(2, employee.getPrimaryContactNo());
		statement.setString(3, employee.getPrimaryContactNo());
		statement.setInt(4, employee.isActive() ? 1 : -1); // Active :: -1 is
															// inactive
		statement.setTimestamp(5, new java.sql.Timestamp(employee.getJoiningDate().getTime()));
		ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
		statement.setString(6, currentUser.getUserId());
		statement.setTimestamp(7, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
		
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
		final String SQL_CREATE_EMPLOYEE = "INSERT INTO EMPLOYEE_SALARY (EMPLOYEE_CODE, EFFECTIVE_FROM, EFFECTIVE_TILL, SALARY, ACTIVE_REVISION, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) VALUES(?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(SQL_CREATE_EMPLOYEE,
				PreparedStatement.RETURN_GENERATED_KEYS);

		statement.setString(1, salaryData.getEmployee().getCode());
		statement.setTimestamp(2, new java.sql.Timestamp(salaryData.getEffectiveFrom().getTime()));
		if (salaryData.getEffectiveTill() == null) {
			statement.setTimestamp(3, null);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(salaryData.getEffectiveTill());
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);			
			salaryData.setEffectiveTill(cal.getTime());			
			statement.setTimestamp(3, new java.sql.Timestamp(salaryData.getEffectiveTill().getTime()));
		}
		statement.setDouble(4, salaryData.getSalary());
		statement.setInt(5, (salaryData.isCurrentRevision()) ? 1 : -1);
		ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
		statement.setString(6, currentUser.getUserId());
		statement.setTimestamp(7, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
		
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
				+ "CONTACT_PRIMARY = ?, CONTACT_SECONDARY = ?, ACTIVE = ?, JOINING_DATE= ?, LAST_MODIFIED_BY = ?, LAST_MODIFIED_DATE=? WHERE CODE=?";
		PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EMPLOYEE);

		statement.setString(1, employee.getName());
		statement.setString(2, employee.getPrimaryContactNo());
		statement.setString(3, employee.getPrimaryContactNo());
		statement.setInt(4, employee.isActive() ? 1 : -1); // Active :: -1 is
															// inactive
		statement.setTimestamp(5, new java.sql.Timestamp(employee.getJoiningDate().getTime()));
		ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
		statement.setString(6, currentUser.getUserId());
		statement.setTimestamp(7, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
		statement.setString(8, employee.getCode());
		
		int rowCount = statement.executeUpdate();
		if (rowCount > 0) {
			if (!employee.getRevisions().isEmpty()) {
				for (EmployeeSalaryData revision : employee.getRevisions()) {
					if (revision.getCode() == null) {
						try {
							reviseSalary(revision, connection);
							createApplicationUserEntry(employee, connection);
							connection.commit();
						} catch (Exception e) {
							connection.rollback();
							throw e;
						}
					} else {						
						updateApplicationUserRole(employee, connection);
						connection.commit();
					}
				}
			}
		} else {
			connection.rollback();			
		}
	}

	private void createApplicationUserEntry(EmployeeData employee, Connection connection) throws Exception {
		final String SQL_CREATE_APP_USER = "INSERT INTO APPLICATION_USER(CODE, PASSCODE, NAME, CONTACT, CREATED_DATE, LAST_MODIFIED_BY, USER_ROLE)  VALUES(?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(SQL_CREATE_APP_USER);

		statement.setString(1, employee.getCode());		
		statement.setString(2, SecurityUtil.encrypt("password"));
		statement.setString(3, employee.getName());
		statement.setString(4, employee.getPrimaryContactNo());															
		statement.setTimestamp(5, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));		
		ApplicationUserData currentUser = (ApplicationUserData)ApplicationSession.getSession().getCurrentUser();
		statement.setString(6, currentUser.getUserId());
		if(employee.getRole() == ApplicationRole.ADMIN) {
			statement.setString(7, "1000");
		} else {
			statement.setString(7, "1001");
		}		
		
		int rowCount = statement.executeUpdate();
		if (rowCount > 0) {
			System.out.println("New User Login created : for User "+(employee.getName()+" with User ID :: "+employee.getCode()));
		}
	}
	
	private void updateApplicationUserRole(EmployeeData employee, Connection connection) throws Exception {
		final String SQL_UPDATE_ROLE_APP_USER = "UPDATE APPLICATION_USER SET USER_ROLE = ?, LAST_MODIFIED_BY = ? WHERE CODE = ?";
		PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ROLE_APP_USER);

		if(employee.getRole() == ApplicationRole.ADMIN) {
			statement.setInt(1, 1000);
		} else {
			statement.setInt(1, 1001);
		}
		ApplicationUserData currentUser = ApplicationSession.getSession().getCurrentUser();
		statement.setString(2, currentUser.getUserId());		
		statement.setString(3, employee.getCode());		
		int rowCount = statement.executeUpdate();
		if (rowCount > 0) {
			System.out.println("Employee Role updated");
		}
	}
}

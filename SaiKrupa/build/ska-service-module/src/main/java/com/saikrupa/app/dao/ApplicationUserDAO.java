package com.saikrupa.app.dao;

import java.util.List;

import com.saikrupa.app.dto.ApplicationUserData;

public interface ApplicationUserDAO {
	public List<ApplicationUserData> findAllUsers();
	public ApplicationUserData findUserByCode(String code);
	public void changePassword(ApplicationUserData user) throws Exception;
}

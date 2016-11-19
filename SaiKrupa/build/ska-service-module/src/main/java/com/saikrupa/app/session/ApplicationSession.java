package com.saikrupa.app.session;

import java.util.HashMap;
import java.util.Map;

import com.saikrupa.app.dto.ApplicationUserData;

public class ApplicationSession {
	
	private static ApplicationSession session;
	
	private Map<String, Object> sessionMap;

	private ApplicationSession() {
		this.sessionMap = new HashMap<String, Object>();
	}
	
	public static ApplicationSession getSession() {
		if(session == null) {
			session = new ApplicationSession();
		}
		return session;
	}
	
	public void setProperty(String key, Object value) {
		sessionMap.put(key, value);
	}
	
	public Object getProperty(String key) {
		return sessionMap.get(key);
	}
	
	public ApplicationUserData getCurrentUser() {
		return (ApplicationUserData) getProperty("CURRENT_USER");
	}
	
	public void setCurrentUser(ApplicationUserData user) {
		setProperty("CURRENT_USER", user);
	}

}

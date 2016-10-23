package com.saikrupa.app.session;

import java.util.HashMap;
import java.util.Map;

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

}

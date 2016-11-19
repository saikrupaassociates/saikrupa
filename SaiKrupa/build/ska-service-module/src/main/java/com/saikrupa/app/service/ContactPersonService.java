package com.saikrupa.app.service;

import com.saikrupa.app.dto.ContactPerson;

public interface ContactPersonService {
	public ContactPerson createContactPerson(ContactPerson contactPerson) throws Exception;	
	public ContactPerson updateContactPerson(ContactPerson contactPerson) throws Exception;
}

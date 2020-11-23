package com.heraizen.cj.cms;

import java.util.Comparator;

public interface ContactBookService {
	Contacts[] getContacts();

	Contacts addContact(Contacts contact);
	
	boolean deleteContact(int id);

	Contacts updateContact(int id, Contacts contact);

	public void removeAll();
	
	public Contacts[] sort(Comparator<Contacts> comparator);

	public Contacts[] searchData(String str);
	public Contacts getContactById(int id);
}

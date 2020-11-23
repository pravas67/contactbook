package com.heraizen.cj.cms;

import java.util.Arrays;
import java.util.Comparator;

import com.heraizen.cj.cms.Contacts.ContactBuilder;

public class ContactsServiceImpls implements ContactBookService {

	private static ContactsServiceImpls contactImpl;
	private Contacts[] contacts;
	int count = 0;

	private ContactsServiceImpls() {
		contacts = getExitingData();
		count = contacts.length;
	}

	private Contacts[] getExitingData() {
		Contacts c1 = new Contacts.ContactBuilder().withName("Pravas").withEmail("pravas@gmail.com")
				.withMobile("7328858578").build();
		Contacts c2 = new Contacts.ContactBuilder().withName("Subash").withEmail("subash@gmail.com")
				.withMobile("7978264743").build();
		Contacts c3 = new Contacts.ContactBuilder().withName("Manu").withEmail("manu@gmail.com")
				.withMobile("9078548585").build();
		Contacts c4 = new Contacts.ContactBuilder().withName("Muna").withEmail("muna@gmail.com")
				.withMobile("8018559966").build();
		Contacts c5 = new Contacts.ContactBuilder().withName("Anshuman").withEmail("anshuman@gmail.com")
				.withMobile("6370452256").build();
		return new Contacts[] { c1, c2, c3, c4, c5 };
	}

	public static ContactsServiceImpls getInstance() {
		if (contactImpl == null) {
			contactImpl = new ContactsServiceImpls();
		}
		return contactImpl;
	}

	@Override
	public Contacts addContact(Contacts contact) {
		if (contact != null) {
			if (contacts.length == count) {
				Contacts[] temp = new Contacts[contacts.length * 2];
				System.arraycopy(contacts, 0, temp, 0, contacts.length);
				contacts = temp;
			}

			contacts[count++] = contact;
		}
		return contacts[count - 1];
	}

	@Override
	public Contacts[] getContacts() {
		Contacts[] temp = new Contacts[count];
		System.arraycopy(contacts, 0, temp, 0, temp.length);
		return temp;
	}

	@Override
	public Contacts[] sort(Comparator<Contacts> comparator) {
		Arrays.sort(contacts, comparator);
		return contacts;
	}

	@Override
	public boolean deleteContact(int id) {
		int cp = 0;
		if (contacts == null) {
			return false;
		}
		Contacts[] temp = new Contacts[contacts.length - 1];
		for (int i = 0; i < contacts.length; i++) {
			if (contacts[i].getId() == id) {
				cp = i;
			}
		}
		System.arraycopy(contacts, 0, temp, 0, cp);
		System.arraycopy(contacts, cp + 1, temp, cp, contacts.length - cp - 1);
		contacts = temp;
		count--;
		return true;

	}

	@Override
	public Contacts updateContact(int id, Contacts contact) {
		System.out.println("contact/////////////"+contact);
		int j = -1;
		for (int i = 0; i < contacts.length; i++) {
			if (contacts[i].getId() == id) {
				contacts[i] = contact;
				j = i;
				break;
			}
		}
		return j != -1 ? contacts[j] : null;
	}

	@Override
	public void removeAll() {
		contacts = new Contacts[0];
		count = 0;
	}

	@Override
	public Contacts[] searchData(String str) {
		Contacts[] temp = new Contacts[contacts.length];
		int count = 0;
		for (Contacts c : contacts) {
			if (c.getName().contains(str) || c.getEmail().contains(str) || c.getMobile().contains(str)) {
				temp[count++] = c;
			}
		}
		return temp;
	}

	@Override
	public Contacts getContactById(int id) {
		Contacts con = null;
		contacts = getContacts();
		for (Contacts c : contacts) {
			if (c.getId() == id) {
				con = c;
			}
		}
		return con;
	}

}

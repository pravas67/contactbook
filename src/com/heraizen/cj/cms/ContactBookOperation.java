package com.heraizen.cj.cms;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactBookOperation {
	ContactBookService contactService = ContactsServiceImpls.getInstance();
	Scanner sc = null;

	public void start() {
		this.sc = new Scanner(System.in);
		do {
			System.out.println("\n--------------------------------------Contact Book----------------------------------------\n");
			System.out.println("1.ADD\t2.DISPLAY ALL\t3.EDIT\t4.DELETE\t5.SEARCH\t6.SORT\t7.EXIT ");
			System.out.println(
					"\n------------------------------------------------------------------------------------------\n");
			int choice = this.getUserChoice();
			switch (choice) {
			case 1:
				this.addContact();
				break;

			case 2:
				this.viewContacts();
				break;
			case 3:
				this.editContact();
				break;
			case 4:
				this.deleteContact();
				break;
			case 5:
				this.searchContact();
				break;
			case 6:
				this.sortContact();
				break;

			case 7: {
				System.out.println("Thank you............");
				this.sc.close();
				System.exit(0);
			}
			}
		} while (true);
	}

	private void sortContact() {

		System.out.println("Sort by : 1. Name 2. Email");
		boolean isNumber = true;
		int choice = 0;
		try {
			choice = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			isNumber = false;
		}
		while (!(choice > 0 && choice < 4 || isNumber)) {
			System.out.println("Choice must be 1 or 2 only");
			try {
				choice = Integer.parseInt(this.sc.nextLine());
			} catch (NumberFormatException e) {
				isNumber = false;
			}
		}
		Contacts[] contacts = contactService.getContacts();
		if (choice == 1) {
			contacts = contactService.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
		} else if (choice == 2) {
			contacts = contactService.sort((c1, c2) -> c1.getEmail().compareTo(c2.getEmail()));
		}
		System.out.println("Sorting is done successfully ");
		for (Contacts contact : contacts) {
			printContact(contact);
		}

	}

	private void searchContact() {
		System.out.println("Enter the search string (name, email, mobile )");
		String str = sc.nextLine();
		Contacts[] contacts = contactService.searchData(str);
		if (contacts != null && contacts.length > 0) {
			System.out.println("Loading.....");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("Search contact result :");
			for (Contacts contact : contacts) {
				printContact(contact);
			}
		} else {
			System.out.println("Contact not found with entered data.");
		}
	}

	private void deleteContact() {
		int id = getUserInputContactId();
		Contacts con = contactService.getContactById(id);
		while (con == null) {
			System.out.println("Contact not found for entered contact id");
			id = getUserInputContactId();
			con = contactService.getContactById(id);
		}

		System.out.println("Are sure do want to delete Contact (y/n) " + con.getName());
		String choice = sc.nextLine();
		if (choice.equalsIgnoreCase("y")) {
			if(contactService.deleteContact(id)) {
				System.out.println(con.getName() + " deleted successfuly ");
			}else {
				System.out.println("Failed!! to delete.");
			}
		}

	}

	private void editContact() {
		int id = getUserInputContactId();
		Contacts con = contactService.getContactById(id);
		while (con == null) {
			id = getUserInputContactId();
			con = contactService.getContactById(id);
		}
		System.out.println("Contact details to edit:");
		printContact(con);
		System.out.println("\nUpdate 1.Name 2. Mobile 3. Email 4. Nothing");
		boolean isNumber = true;
		int choice = 0;
		try {
			choice = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			isNumber = false;
		}
		while (!(choice > 0 && choice < 5 || isNumber)) {
			System.out.println("Choice must be 1 to 4 only");
			try {
				choice = Integer.parseInt(this.sc.nextLine());
			} catch (NumberFormatException e) {
				isNumber = false;
			}
		}
		Contacts contact = con;
		String mobile = null, name = null, email = null;
		if (choice == 1) {
			System.out.println("Enter the name :");
			name = this.sc.nextLine();
		} else if (choice == 2) {
			mobile = getValidMobileInput();
		} else if (choice == 3) {
			email = this.checkUserEmail();
		} else if (choice == 4) {
			return;
		}
		contact.setName(name == null ? con.getName() : name);
		contact.setMobile(mobile == null ? con.getMobile() : mobile);
		contact.setEmail(email == null ? con.getEmail() : email);
		contactService.updateContact(con.getId(), contact);
		System.out.println("Contact updated successfully.");
	}

	private int getUserInputContactId() {
		boolean isNumber = false;
		int id = 0;
		System.out.println("Enter the id :");
		try {
			id = Integer.parseInt(this.sc.nextLine());
		} catch (NumberFormatException e) {
			isNumber = true;
		}
		while (id <= 0 && isNumber) {
			System.out.println("Enter valid id : must be >= 1000");
			try {
				id = Integer.parseInt(this.sc.nextLine());
			} catch (NumberFormatException e) {
				isNumber = true;
			}
		}
		return id;
	}

	private void viewContacts() {
		Contacts[] contacts = contactService.getContacts();
		System.out.format("%-10s%-20s%-20s%-20s%n", "ID", "NAME", "MOBILE","EMAIL");
		System.out.println();
		for (Contacts contact : contacts) {
			printContact(contact);
		}
	}

	private void printContact(Contacts contact) {
		if (contact != null) {
			System.out.format("%-10d%-20s%-20s%-20s%n", contact.getId(), contact.getName(), contact.getMobile(),
					contact.getEmail());
		} else {
			return;
		}
	}

	private void addContact() {
		System.out.println("Enter the name :");
		String name = this.sc.nextLine();
		String email = this.checkUserEmail();
		String mobile = this.getValidMobileInput();
		Contacts contact = new Contacts.ContactBuilder().withName(name).withMobile(mobile).withEmail(email).build();
		System.out.println("Loading.....");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		contact = this.contactService.addContact(contact);
		if (contact != null) {
			System.out.println("Contact added successfully");
		} else {
			System.out.println("Failed!!!!");
		}
	}

	private String getValidMobileInput() {
		System.out.println("Enter the mobile number :");
		String mobile = this.sc.nextLine();
		while (!this.checkContainDigits(mobile)) {
			System.out.println("Enter the valid mobile number :");
			mobile = this.sc.nextLine();
		}
		return mobile;
	}

	private boolean checkContainDigits(String mobile) {
		Pattern pattern = Pattern.compile("\\d{10}");
		Matcher matcher = pattern.matcher(mobile);
		boolean isvalid = matcher.matches();
		return isvalid;
	}

	private String checkUserEmail() {
		boolean isValid = false;
		System.out.println("Enter the email : Ex (abc@gmail.com) :");
		String email = this.sc.nextLine();
		while (!isValid) {
			isValid = this.isValidEmail(email);
			if (isValid)
				break;
			System.out.println("Enter the email : Ex (abc@gmail.com) :");
			email = this.sc.nextLine();
		}
		return email;
	}

	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private int getUserChoice() {
		boolean isNumber = true;
		int choice = 0;
		try {
			choice = Integer.parseInt(this.sc.nextLine());
		} catch (NumberFormatException e) {
			isNumber = false;
		}
		while (!(choice > 0 && choice < 8 || isNumber)) {
			System.out.println("Choice must be 1 to 7 only");
			try {
				choice = Integer.parseInt(this.sc.nextLine());
			} catch (NumberFormatException e) {
				isNumber = false;
			}
		}
		return choice;
	}

}

package com.heraizen.cj.cms;

public class Contacts {

	private int id;
	private String name;
	private String email;
	private String mobile;
	private Contacts(ContactBuilder builder)
	  {
	    this.id = GenerateId.generateId();
	    this.name = builder.name;
	    this.email = builder.email;
	    this.mobile = builder.mobile;
	  }
	@Override
	public String toString() {
		return "Contacts [id=" + id + ", name=" + name + ", email=" + email + ", mobile=" + mobile + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public static class ContactBuilder
	  {
	    private String name;
	    private String email;
	    private String mobile;
	    
	    public ContactBuilder withName(String name)
	    {
	      this.name = name;
	      return this;
	    }
	    
	    public ContactBuilder withEmail(String email)
	    {
	      this.email = email;
	      return this;
	    }
	    
	    public ContactBuilder withMobile(String mobile)
	    {
	      this.mobile = mobile;
	      return this;
	    }
	    
	    public Contacts build()
	    {
	      return new Contacts(this);
	    }
	  }
}

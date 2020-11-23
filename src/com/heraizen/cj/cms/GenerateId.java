package com.heraizen.cj.cms;

public enum GenerateId {
	obj;
	static int id=1000;
	public static int  generateId()
	{
		return id++;
	}
}

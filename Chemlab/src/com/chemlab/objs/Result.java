package com.chemlab.objs;

import java.io.Serializable;

public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 123L;
	private String name;
	private String prop1;
	private String prop2;

	public Result() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public String getProp2() {
		return prop2;
	}

	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

}

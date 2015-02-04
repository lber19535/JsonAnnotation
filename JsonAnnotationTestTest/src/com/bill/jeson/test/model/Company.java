package com.bill.jeson.test.model;

import java.util.List;

import com.bill.jeson.annotation.FieldType;
import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

@JsonObject
public class Company {

	@JsonField(defaultValue = "HT")
	private String name;
	@JsonField(type = FieldType.JsonObject)
	private PeopleAll ceo;
	@JsonField()
	private List<PeopleAll> employee;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PeopleAll getCeo() {
		return ceo;
	}

	public void setCeo(PeopleAll ceo) {
		this.ceo = ceo;
	}

	public List<PeopleAll> getEmployee() {
		return employee;
	}

	public void setEmployee(List<PeopleAll> employee) {
		this.employee = employee;
	}

}

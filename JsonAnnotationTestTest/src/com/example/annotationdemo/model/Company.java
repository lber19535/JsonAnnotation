package com.example.annotationdemo.model;

import java.util.List;

import com.example.annotationdemo.annotation.FieldType;
import com.example.annotationdemo.annotation.JsonField;
import com.example.annotationdemo.annotation.JsonObject;

@JsonObject
public class Company {

	@JsonField(defaultValue = "HT")
	private String name;
	@JsonField(type = FieldType.JsonObject)
	private PeopleAll ceo;
	@JsonField(type = FieldType.JsonArray)
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

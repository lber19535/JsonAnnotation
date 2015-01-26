package com.example.annotationdemo.model;

import com.example.annotationdemo.annotation.FieldType;
import com.example.annotationdemo.annotation.JsonField;
import com.example.annotationdemo.annotation.JsonObject;

@JsonObject
public class Company {

	@JsonField(defaultValue = "HT")
	private String name;
	@JsonField(type=FieldType.JsonObject)
	private PeopleAll ceo;

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

}

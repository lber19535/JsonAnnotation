package com.example.annotationdemo.model;

import com.example.annotationdemo.annotation.FieldType;
import com.example.annotationdemo.annotation.JsonField;
import com.example.annotationdemo.annotation.JsonObject;

@JsonObject
public class People {

	@JsonField(type = FieldType.String, defaultValue = "null")
	private String firstName;
	@JsonField(type = FieldType.String, defaultValue = "null")
	private String lastName;
	@JsonField(type = FieldType.String, defaultValue = "null")
	private String sex;
	@JsonField(type = FieldType.Int, defaultValue = "1")
	private int age;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}

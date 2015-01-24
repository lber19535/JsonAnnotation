package com.example.annotationdemo.model;

import com.example.annotationdemo.annotation.FieldType;
import com.example.annotationdemo.annotation.JsonField;
import com.example.annotationdemo.annotation.JsonObject;

@JsonObject
public class PeopleAll {

	@JsonField(type = FieldType.String, defaultValue = "Bill")
	private String firstName;
	@JsonField(type = FieldType.String, defaultValue = "Lv")
	private String lastName;
	@JsonField(type = FieldType.String, defaultValue = "man")
	private String sex;
	@JsonField(type = FieldType.Int, defaultValue = "1")
	private int age;
	@JsonField(type = FieldType.Float, defaultValue = "1")
	private float height;
	@JsonField(type = FieldType.Long, defaultValue = "1")
	private long phoneNumber;
	@JsonField(type = FieldType.Double, defaultValue = "1")
	private double weight;

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

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}

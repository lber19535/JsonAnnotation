package com.bill.jeson.test.model;

import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

@JsonObject
public class PeopleWithoutType {

	@JsonField(defaultValue = "Bill")
	private String firstName;
	@JsonField(defaultValue = "Lv")
	private String lastName;
	@JsonField(defaultValue = "man")
	private String sex;
	@JsonField(defaultValue = "1")
	private int age;
	@JsonField(defaultValue = "1")
	private float height;
	@JsonField(defaultValue = "1")
	private long phoneNumber;
	@JsonField(defaultValue = "1")
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

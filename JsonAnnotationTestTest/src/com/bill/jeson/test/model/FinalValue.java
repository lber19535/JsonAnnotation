package com.bill.jeson.test.model;

import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

@JsonObject
public class FinalValue {

	@JsonField
	private final String finalStr = "next";

	public String getFinalStr() {
		return finalStr;
	}

}

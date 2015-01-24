package com.example.annotationdemo.test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.annotationdemo.JsonFactory;
import com.example.annotationdemo.model.PeopleAll;
import com.example.annotationdemo.model.PeopleWithoutType;

public class JsonObjectTest extends TestCase {

	private String jsonObjStr;
	private String emptyJsonStr;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		jsonObjStr = "{\"firstName\": \"John\",\"lastName\": \"Smith\",\"sex\": \"third\",\"age\": 25,\"height\": 175,\"phoneNumber\": 13800000000,\"weight\": 65.1 }";
		emptyJsonStr = "{}";
	}

	public void testJsonSource() {
		try {
			JSONObject jsonObject = new JSONObject(jsonObjStr);
			assertEquals("John", jsonObject.getString("firstName"));
			assertEquals("Smith", jsonObject.getString("lastName"));
			assertEquals("third", jsonObject.getString("sex"));
			assertEquals(25, jsonObject.getInt("age"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void testJsonBaseTypeAnnotation() {
		try {
			PeopleAll people = JsonFactory.createBean(PeopleAll.class,
					jsonObjStr);
			assertEquals("John", people.getFirstName());
			assertEquals("Smith", people.getLastName());
			assertEquals("third", people.getSex());
			assertEquals(25, people.getAge());
			assertEquals(175, people.getHeight(), 0);
			assertEquals(13800000000L, people.getPhoneNumber());
			assertEquals(65.1, people.getWeight());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testFieldDetect() {
		try {
			PeopleWithoutType people = JsonFactory.createBean(
					PeopleWithoutType.class, jsonObjStr);
			assertEquals("John", people.getFirstName());
			assertEquals("Smith", people.getLastName());
			assertEquals("third", people.getSex());
			assertEquals(25, people.getAge());
			assertEquals(175, people.getHeight(), 0);
			assertEquals(13800000000L, people.getPhoneNumber());
			assertEquals(65.1, people.getWeight());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testEmptyJsonDetect() {
		try {
			PeopleWithoutType people = JsonFactory.createBean(
					PeopleWithoutType.class, emptyJsonStr);
			assertEquals("Bill", people.getFirstName());
			assertEquals("Lv", people.getLastName());
			assertEquals("man", people.getSex());
			assertEquals(1, people.getAge());
			assertEquals(1, people.getHeight(), 0);
			assertEquals(1, people.getPhoneNumber());
			assertEquals(1, people.getWeight(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

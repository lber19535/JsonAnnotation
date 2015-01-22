package com.example.annotationdemo.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.annotationdemo.JsonFactory;
import com.example.annotationdemo.annotation.FieldType;
import com.example.annotationdemo.annotation.JsonField;
import com.example.annotationdemo.model.People;

import junit.framework.TestCase;

public class JsonObjectTest extends TestCase {

	private String jsonObjStr;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		jsonObjStr = "{\"firstName\": \"John\",\"lastName\": \"Smith\",\"sex\": \"third\",\"age\": 25 }";
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

	public void testJsonAnnotation() {
		try {
			People people = JsonFactory.createBean(People.class, jsonObjStr);
			assertEquals("John", people.getFirstName());
			assertEquals("Smith", people.getLastName());
			assertEquals("third", people.getSex());
			assertEquals(25, people.getAge());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

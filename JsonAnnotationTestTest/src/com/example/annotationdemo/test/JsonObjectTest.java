package com.example.annotationdemo.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testJsonAnnotation() {
		try {
			JSONObject jsonObject = new JSONObject(jsonObjStr);
			Object obj = Class.forName(People.class.getName()).newInstance();
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(JsonField.class)) {
					JsonField jsonField = field.getAnnotation(JsonField.class);
					if (jsonField.name().equals(JsonField.DEFAULT_STRING)) {
						String fieldName = field.getName();
						Object fieldValue = getField(jsonField.type(),
								jsonObject, fieldName);
						field.setAccessible(true);
						field.set(obj, fieldValue);
					} else {

					}
				}
			}
			People people = (People) obj;
			assertEquals("John", people.getFirstName());
			assertEquals("Smith", people.getLastName());
			assertEquals("third", people.getSex());
			assertEquals(25, people.getAge());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Object getField(FieldType type, JSONObject jsonObject, String name)
			throws JSONException {
		switch (type) {
		case Int:
			return jsonObject.getInt(name);
		case Double:
			return jsonObject.getDouble(name);
		case Float:
			return (float) jsonObject.getDouble(name);
		case JsonArray:
			break;
		case JsonObject:
			break;
		case Long:
			return (float) jsonObject.getLong(name);
		case String:
			return jsonObject.getString(name);
		case Unknow:
			break;
		default:
			break;
		}
		return null;
	}

}

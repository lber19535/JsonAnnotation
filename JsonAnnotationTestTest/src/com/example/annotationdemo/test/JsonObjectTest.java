package com.example.annotationdemo.test;

import java.util.List;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.annotationdemo.JsonFactory;
import com.example.annotationdemo.model.Company;
import com.example.annotationdemo.model.PeopleAll;
import com.example.annotationdemo.model.PeopleWithoutType;

import dalvik.annotation.TestTarget;

public class JsonObjectTest extends TestCase {

	private String jsonObjStr;
	private String emptyJsonStr;
	private String jsonObjsStr;
	private String jsonObjListStr;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		jsonObjStr = "{\"firstName\": \"John\"," + "\"lastName\": \"Smith\","
				+ "\"sex\": \"third\"," + "\"age\": 25," + "\"height\": 175,"
				+ "\"phoneNumber\": 13800000000," + "\"weight\": 65.1 }";
		emptyJsonStr = "{}";
		jsonObjsStr = "{\"name\":\"Baidu\", " + "\"ceo\":"
				+ "{\"firstName\": \"Robin\"," + "\"lastName\": \"Li\","
				+ "\"sex\": \"third\"," + "\"age\": 46," + "\"height\": 175,"
				+ "\"phoneNumber\": 13800000000," + "\"weight\": 65.1 }}";

		initJsonObjListString();

	}

	private void initJsonObjListString() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "Baidu");
		JSONObject ceoObject = new JSONObject();
		ceoObject.put("firstName", "Robin");
		ceoObject.put("lastName", "Li");
		ceoObject.put("sex", "third");
		ceoObject.put("age", 46);
		ceoObject.put("height", 175);
		ceoObject.put("phoneNumber", 13800000000L);
		ceoObject.put("weight", 65.1);
		jsonObject.put("ceo", ceoObject);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < 3; i++) {
			JSONObject employeeObject = new JSONObject();
			employeeObject.put("firstName", "Bill");
			employeeObject.put("lastName", "Lv");
			employeeObject.put("sex", "man");
			employeeObject.put("age", 24);
			employeeObject.put("height", 175);
			employeeObject.put("phoneNumber", 13800000000L);
			employeeObject.put("weight", 55.1);
			jsonArray.put(employeeObject);
		}
		jsonObject.put("employee", jsonArray);
		jsonObjListStr = jsonObject.toString();
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

	public void testJsonEmptyObjectsDetect() {
		try {
			Company company = JsonFactory.createBean(Company.class,
					emptyJsonStr);
			assertEquals("HT", company.getName());
			PeopleAll ceo = company.getCeo();
			assertEquals("Bill", ceo.getFirstName());
			assertEquals("Lv", ceo.getLastName());
			assertEquals("man", ceo.getSex());
			assertEquals(1, ceo.getAge());
			assertEquals(1, ceo.getHeight(), 0);
			assertEquals(1, ceo.getPhoneNumber());
			assertEquals(1, ceo.getWeight(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testJsonObjectsDetect() {
		try {
			Company company = JsonFactory
					.createBean(Company.class, jsonObjsStr);
			assertEquals("Baidu", company.getName());
			PeopleAll ceo = company.getCeo();
			assertEquals("Robin", ceo.getFirstName());
			assertEquals("Li", ceo.getLastName());
			assertEquals("third", ceo.getSex());
			assertEquals(46, ceo.getAge());
			assertEquals(175, ceo.getHeight(), 0);
			assertEquals(13800000000L, ceo.getPhoneNumber());
			assertEquals(65.1, ceo.getWeight(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testJsonObjectsListDetect() {
		try {
			Company company = JsonFactory.createBean(Company.class,
					jsonObjListStr);
			assertEquals("Baidu", company.getName());
			PeopleAll ceo = company.getCeo();
			assertEquals("Robin", ceo.getFirstName());
			assertEquals("Li", ceo.getLastName());
			assertEquals("third", ceo.getSex());
			assertEquals(46, ceo.getAge());
			assertEquals(175, ceo.getHeight(), 0);
			assertEquals(13800000000L, ceo.getPhoneNumber());
			assertEquals(65.1, ceo.getWeight(), 0);
			List<PeopleAll> employees = company.getEmployee();
			assertNotNull(employees);
			for (PeopleAll employee : employees) {
				assertEquals("Bill", employee.getFirstName());
				assertEquals("Lv", employee.getLastName());
				assertEquals("man", employee.getSex());
				assertEquals(24, employee.getAge());
				assertEquals(175, employee.getHeight(), 0);
				assertEquals(13800000000L, employee.getPhoneNumber());
				assertEquals(55.1, employee.getWeight(), 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

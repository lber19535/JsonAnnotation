package com.example.annotationdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.annotationdemo.annotation.FieldType;
import com.example.annotationdemo.annotation.JsonField;
import com.example.annotationdemo.annotation.JsonObject;

public class JsonFactory {

	private static final String EMPTY_JSON = "{}";

	/**
	 * Create java bean by json string
	 * 
	 * @param clazz
	 *            java bean class
	 * @param json
	 *            json String
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T createBean(Class<T> javaBean, String json)
			throws Exception {
		// if javabean is not a jsonobject
		if (!javaBean.isAnnotationPresent(JsonObject.class)) {
			throw new Exception(javaBean.getName()
					+ " is not a json object java bean");
		}
		// if json string is null
		if (json == null) {
			// throw new Exception("json string is Null");
			json = EMPTY_JSON;
		}
		JSONObject jsonObject = new JSONObject(json);
		Object targetObj = getBeanObj(javaBean);
		Field[] fields = getBeanFields(targetObj);
		for (Field field : fields) {
			if (field.isAnnotationPresent(JsonField.class)) {
				/*
				 * order: 
				 * 1. detect field type
				 * 2. detect field name (if don't set field name, use field actual name) 
				 * 3. if the json object don't have that field, then throw a exception
				 * 4. set json field values
				 */
				FieldType fieldType = getFieldType(field);
				String fieldName = getFieldName(field);
				Object fieldValue = null;
				switch (fieldType) {
				case JsonArray:
					
					break;
				case Unknow:
					throw new Exception("Json Field Is Unkonw Type");
				default:
					fieldValue = getFieldValue(field, fieldType,
							jsonObject, fieldName);
					break;
				}
				field.setAccessible(true);
				field.set(targetObj, fieldValue);
			}
		}
		return (T) targetObj;
	}

	private static Object getFieldValue(Field field, FieldType type,
			JSONObject jsonObject, String name) throws Exception {
		/*
		 * 1. if json object is null, set java bean with the default values
		 * (1.have set default value 2.json object is null) 
		 * 2. if java bean don't set default value
		 */
		JsonField jsonField = field.getAnnotation(JsonField.class);

		if (!jsonObject.has(name)) {
			switch (type) {
			case Int:
				return Integer.valueOf(jsonField.defaultValue());
			case Double:
				return Double.valueOf(jsonField.defaultValue());
			case Float:
				return Float.valueOf(jsonField.defaultValue());
			case JsonArray:
				break;
			case JsonObject:
				return createBean(field.getType(), null);
			case Long:
				return Long.valueOf(jsonField.defaultValue());
			case String:
				return jsonField.defaultValue();
			case Unknow:
				break;
			default:
				break;
			}
		} else {
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
				return createBean(field.getType(),
						jsonObject.getJSONObject(name).toString());
			case Long:
				return jsonObject.getLong(name);
			case String:
				return jsonObject.getString(name);
			case Unknow:
				break;
			default:
				break;
			}
		}
		return null;
	}

	private static String getFieldName(Field field) {
		JsonField jsonField = field.getAnnotation(JsonField.class);
		if (jsonField.name().equals(JsonField.DEFAULT_STRING)) {
			return field.getName();
		} else {
			return jsonField.name();
		}

	}

	private static FieldType getFieldType(Field field) {
		JsonField jsonField = field.getAnnotation(JsonField.class);
		if (jsonField.type() == FieldType.Unknow) {
			Type fieldType = field.getGenericType();
			if (fieldType == String.class) {
				return FieldType.String;
			} else if (fieldType == int.class) {
				return FieldType.Int;
			} else if (fieldType == float.class) {
				return FieldType.Float;
			} else if (fieldType == double.class) {
				return FieldType.Double;
			} else if (fieldType == long.class) {
				return FieldType.Long;
			} else {
				return FieldType.Unknow;
			}
			// TODO add json object and json array
		} else {
			return jsonField.type();
		}
	}

	@SuppressWarnings("rawtypes")
	private static Object getBeanObj(Class javaBean) throws Exception {
		return Class.forName(javaBean.getName()).newInstance();
	}

	private static Field[] getBeanFields(Object obj) throws Exception {
		return obj.getClass().getDeclaredFields();
	}
}

package com.example.annotationdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.json.JSONObject;

import com.example.annotationdemo.annotation.FieldType;
import com.example.annotationdemo.annotation.JsonField;
import com.example.annotationdemo.utils.JsonUtils;

public class JsonFactory {

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
		JSONObject jsonObject = new JSONObject(json);
		Object obj = getBeanObj(javaBean);
		Field[] fields = getBeanFields(obj);
		for (Field field : fields) {
			if (field.isAnnotationPresent(JsonField.class)) {
				JsonField jsonField = field.getAnnotation(JsonField.class);
				/*
				 * order: 
				 * 1. detect field type √
				 * 2. detect field name(if don't set field name, use field actual name) 
				 * 3. if the json object don't have that field, then throw a exception 
				 * 4.
				 */
				if (jsonField.name().equals(JsonField.DEFAULT_STRING)) {
					String fieldName = field.getName();
					Object fieldValue;
					// TODO if not set type, we should detect the object type
					FieldType fieldType = getFieldType(field);
					if (fieldType == FieldType.Unknow) {
						// TODO if json object don't have define field, we
						// should throw a Exception
						// jsonObject.has(fieldName);
						fieldValue = JsonUtils.getField(fieldType, jsonObject,
								fieldName);
					} else {
						fieldValue = JsonUtils.getField(fieldType, jsonObject,
								fieldName);
					}
					field.setAccessible(true);
					field.set(obj, fieldValue);
				} else {
					// TODO if user set the json field name

				}
			}
		}
		return (T) obj;
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

		} else {
			return jsonField.type();
		}
	}

	private static Object getBeanObj(Class javaBean) throws Exception {
		return Class.forName(javaBean.getName()).newInstance();
	}

	private static Field[] getBeanFields(Object obj) throws Exception {
		return obj.getClass().getDeclaredFields();
	}
}

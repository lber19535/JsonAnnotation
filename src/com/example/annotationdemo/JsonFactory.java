package com.example.annotationdemo;

import java.lang.reflect.Field;

import org.json.JSONException;
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
				if (jsonField.name().equals(JsonField.DEFAULT_STRING)) {
					String fieldName = field.getName();
					Object fieldValue;
					// TODO if not set type, we should detect the object type
					System.out.println(field.getType());
					if (jsonField.type() == FieldType.Unknow) {
						// TODO if json object don't have define field, we
						// should throw a Exception
						// jsonObject.has(fieldName);
						fieldValue = JsonUtils.getField(jsonField.type(),
								jsonObject, fieldName);
					} else {
						fieldValue = JsonUtils.getField(jsonField.type(),
								jsonObject, fieldName);
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

	private static Object getBeanObj(Class javaBean) throws Exception {
		return Class.forName(javaBean.getName()).newInstance();
	}

	private static Field[] getBeanFields(Object obj) throws Exception {
		return obj.getClass().getDeclaredFields();
	}
}

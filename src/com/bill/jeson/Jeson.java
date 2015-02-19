package com.bill.jeson;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.bill.jeson.annotation.FieldType;
import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

/**
 * This util use reflect and {@link org.json} to analysis the json object, like
 * other orm framework, depend on the annotation with java bean and auto
 * analysis json string to java bean.
 * 
 * @author Bill Lv
 * @version 1.1
 */
public class Jeson {

	private static final String EMPTY_JSON = "{}";
	private static final String TAG = "Jeson";

	/**
	 * Create java bean by {@link JSONObject}
	 * 
	 * @param clazz
	 *            java bean class
	 * @param json
	 *            json String
	 * @return
	 * @throws Exception
	 */
	public static <T extends Object> T createBean(Class<T> javaBean,
			JSONObject jsonObject) throws Exception {

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
					fieldValue = getJsonFieldValue(field, fieldType,
							jsonObject, fieldName);
					break;
				case Unknow:
					throw new Exception("Json Field Is Unkonw Type");
				default:
					fieldValue = getJsonFieldValue(field, fieldType,
							jsonObject, fieldName);
					break;
				}
				field.setAccessible(true);
				field.set(targetObj, fieldValue);
			}
		}
		return (T) targetObj;
	}

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
			Log.e(TAG, "json string is null");
			json = EMPTY_JSON;
		}
		JSONObject jsonObject = new JSONObject(json);
		return createBean(javaBean, jsonObject);
	}

	/**
	 * Convert java bean to json string
	 * 
	 * @param javaBean
	 * @return
	 * @throws Exception
	 */
	public static <T> String bean2String(T javaBean) throws Exception {
		return bean2JsonObj(javaBean).toString();
	}

	/**
	 * Convert java bean to json object
	 * 
	 * @param javaBean
	 * @return
	 * @throws Exception
	 */
	public static <T> JSONObject bean2JsonObj(T javaBean) throws Exception {
		JsonObject beanType = javaBean.getClass().getAnnotation(
				JsonObject.class);
		JSONObject jsonObject = new JSONObject();

		if (beanType != null) {
			Field[] fields = javaBean.getClass().getDeclaredFields();
			for (Field field : fields) {
				JsonField fieldType = field.getAnnotation(JsonField.class);
				if (fieldType != null) {
					String fieldName = getFieldName(field);
					FieldType type = getFieldType(field);
					Object fieldValue = getBeanFieldValue(field, type, javaBean);
					jsonObject.put(fieldName, fieldValue);
				}
			}
		} else {
			Log.e(TAG, javaBean.getClass() + " is not a json object bean");
		}
		return jsonObject;
	}

	private static <T> Object getBeanFieldValue(Field field, FieldType type,
			T javaBean) throws Exception {
		field.setAccessible(true);
		switch (type) {
		case String:
		case Double:
		case Float:
		case Int:
		case Long:
			return field.get(javaBean);
		case JsonArray:
			List list = (List) field.get(javaBean);
			JSONArray array = new JSONArray();
			Class genericType = getListGenericType(field);
			if (list == null) {
				return array;
			}
			for (Object object : list) {
				String item = bean2String(object);
				array.put(new JSONObject(item));
			}
			return array;
		case JsonObject:
			return new JSONObject(bean2String(field.get(javaBean)));
		case Unknow:
			break;
		default:
			break;
		}
		return EMPTY_JSON;
	}

	private static Object getJsonFieldValue(Field field, FieldType type,
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
				return createBean(field.getType(), EMPTY_JSON);
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
				JSONArray array = jsonObject.getJSONArray(name);
				return getListObject(array, field);
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
			Type fieldType = field.getType();
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
			} else if (isList(field)) {
				return FieldType.JsonArray;
			} else {
				return FieldType.JsonObject;
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

	/*
	 * get list generic type
	 */
	private static Class<?> getListGenericType(Field field) {
		ParameterizedType parameterizedType = (ParameterizedType) field
				.getGenericType();
		Class<?> genericType = (Class<?>) parameterizedType
				.getActualTypeArguments()[0];
		return genericType;
	}

	@SuppressWarnings("unchecked")
	private static Object getListObject(JSONArray array, Field field)
			throws JSONException, Exception {
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();
		Class<?> genericType = getListGenericType(field);
		if (array == null) {
			Object obj = createBean(genericType, EMPTY_JSON);
			list.add(obj);
		} else {
			for (int i = 0; i < array.length(); i++) {
				Object obj = createBean(genericType, array.get(i).toString());
				list.add(obj);
			}
		}
		return list;
	}

	private static boolean isList(Field field) {
		if (field.getType() == List.class) {
			return true;
		}
		try {
			Type[] types = Class.forName(field.getType().getName())
					.newInstance().getClass().getGenericInterfaces();
			return Arrays.toString(types).contains(List.class.getName());
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return false;
	}
}

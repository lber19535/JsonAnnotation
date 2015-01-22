package com.example.annotationdemo.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.annotationdemo.annotation.FieldType;

public class JsonUtils {

	public static Object getField(FieldType type, JSONObject jsonObject,
			String name) throws JSONException {
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

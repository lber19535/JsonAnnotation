package com.bill.jeson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use to define json field
 * 
 * @author Bill Lv
 *
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonField {
	public static final String DEFAULT_STRING = "default_string";

	FieldType type() default FieldType.Unknow;

	String defaultValue() default DEFAULT_STRING;

	String name() default DEFAULT_STRING;
}

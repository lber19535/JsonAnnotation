package com.bill.jeson.test.model;

import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

@JsonObject
public class HttpHeader {

	@JsonField(name = "Accept-Language")
	private String language;
	@JsonField(name = "Host")
	private String host;
	@JsonField(name = "Accept-Charset")
	private String charset;
	@JsonField(name = "Accept")
	private String accept;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

}

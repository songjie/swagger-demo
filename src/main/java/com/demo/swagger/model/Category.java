package com.demo.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
	private long id;
	private String name;

	public Category(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@JsonCreator
	public static Category create(@JsonProperty("id") long id, @JsonProperty("name") String name) {
		return new Category(id, name);
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

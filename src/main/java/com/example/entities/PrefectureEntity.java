package com.example.entities;

public class PrefectureEntity {
	private int id;
	private String name;

	public PrefectureEntity(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public PrefectureEntity() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
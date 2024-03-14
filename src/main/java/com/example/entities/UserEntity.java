package com.example.entities;

import java.util.Date;

public class UserEntity {
	private int id;
	private String name;
	private String email;
	private int prefectureId;
	private Date createdAt;
	private Date updatedAt;

	public UserEntity(int id, String name, String email, int prefectureId, Date createdAt, Date updatedAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.prefectureId = prefectureId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UserEntity() {

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public int getPrefectureId() {
		return prefectureId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPrefectureId(int prefectureId) {
		this.prefectureId = prefectureId;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
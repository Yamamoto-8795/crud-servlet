package com.example.viewmodels;

public class UserDisplayModel {
	private int id;
	private String name;
	private String email;
	private String prefectureName;
	private String formattedCreatedAt;
	private String formattedUpdatedAt;
	 private int prefectureId;

	public UserDisplayModel(int id, String name, String email, String prefectureName, String formattedCreatedAt,String formattedUpdatedAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.prefectureName = prefectureName;
		this.formattedCreatedAt = formattedCreatedAt;
		this.formattedUpdatedAt = formattedUpdatedAt;
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

	public String getPrefectureName() {
		return prefectureName;
	}

	public String getFormattedCreatedAt() {
		return formattedCreatedAt;
	}

	public String getFormattedUpdatedAt() {
		return formattedUpdatedAt;
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

	public void setPrefectureName(String prefectureName) {
		this.prefectureName = prefectureName;
	}

	public void setFormattedCreatedAt(String formattedCreatedAt) {
		this.formattedCreatedAt = formattedCreatedAt;
	}

	public void setFormattedUpdatedAt(String formattedUpdatedAt) {
		this.formattedUpdatedAt = formattedUpdatedAt;
	}

	public int getPrefectureId() {
		return prefectureId;
	}

	public void setPrefectureId(int prefectureId) {
		this.prefectureId = prefectureId;
	}
}

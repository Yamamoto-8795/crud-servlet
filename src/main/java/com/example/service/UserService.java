package com.example.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.entities.PrefectureEntity;
import com.example.entities.UserEntity;
import com.example.repositries.PrefectureRepository;
import com.example.repositries.UserRepository;
import com.example.viewmodels.UserDisplayModel;

public class UserService {
	private UserRepository userRepository;
	private PrefectureRepository prefectureRepository;

	public UserService(UserRepository userRepository, PrefectureRepository prefectureRepository) {
		this.userRepository = userRepository;
		this.prefectureRepository = prefectureRepository;
	}

	public List<UserEntity> getAllUsers() throws SQLException {
		return userRepository.findAll();
	}

	public List<PrefectureEntity> getAllPrefectures() throws SQLException {
		return prefectureRepository.findAll();
	}

	public String getPrefectureNameById(int id, List<PrefectureEntity> prefectures) {
		PrefectureEntity prefecture = prefectureRepository.findById(id, prefectures);
		return prefecture != null ? prefecture.getName() : "不明";
	}

	public int createUser(UserEntity user) {
		return userRepository.createUsers(user);
	}

	public boolean emailExist(String email) throws SQLException {
		return userRepository.findByEmail(email);
	}

	public UserDisplayModel getUserDetails(int userId) throws SQLException {
		UserEntity user = userRepository.findById(userId);
		if (user != null) {
			PrefectureEntity prefecture = prefectureRepository.findById(user.getPrefectureId());
			String prefectureName = (prefecture != null) ? prefecture.getName() : "不明";

			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formattedCreatedAt = dateFormat.format(user.getCreatedAt());
			String formattedUpdatedAt = dateFormat.format(user.getUpdatedAt());

			return new UserDisplayModel(user.getId(), user.getName(), user.getEmail(), prefectureName,
					formattedCreatedAt, formattedUpdatedAt);
		}
		return null;
	}

	public boolean updateUser(UserEntity user) throws SQLException {
		return userRepository.update(user);
	}
}
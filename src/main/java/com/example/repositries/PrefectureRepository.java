package com.example.repositries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.entities.PrefectureEntity;
import com.example.utilities.DatabaseConnector;

public class PrefectureRepository {

	public List<PrefectureEntity> findAll() throws SQLException {
		DatabaseConnector connector = new DatabaseConnector();
		List<PrefectureEntity> prefectures = new ArrayList<>();

		try (Connection connection = connector.getConnection();
			Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery("SELECT * FROM prefectures");

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				PrefectureEntity prefecture = new PrefectureEntity();
				prefecture.setId(id);
				prefecture.setName(name);
				prefectures.add(prefecture);
			}

		} catch (SQLException e) {
			throw e;
		}
		return prefectures;
	}

	public PrefectureEntity findById(int id, List<PrefectureEntity> prefectures) {
		for (PrefectureEntity prefecture : prefectures) {
			if (prefecture.getId() == id) {
				return prefecture;
			}
		}
		return null;
	}

	public PrefectureEntity findById(int id) throws SQLException {
		DatabaseConnector connector = new DatabaseConnector();
		PrefectureEntity prefecture = null;

		Connection connection = connector.getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM prefectures WHERE id = ?");

		statement.setInt(1, id);
		ResultSet resultset = statement.executeQuery();

		if (resultset.next()) {
			prefecture = new PrefectureEntity();
			prefecture.setId(resultset.getInt("id"));
			prefecture.setName(resultset.getString("name"));
		}
		return prefecture;
	}

}
package com.example.repositries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.entities.UserEntity;
import com.example.utilities.DatabaseConnector;

public class UserRepository {

	public List<UserEntity> findAll() throws SQLException {
		DatabaseConnector connector = new DatabaseConnector();
		List<UserEntity> users = new ArrayList<>();

		try (Connection connection = connector.getConnection();
			Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				Timestamp created_at = resultSet.getTimestamp("created_at");
				Timestamp updated_at = resultSet.getTimestamp("updated_at");
				int prefecture_id = resultSet.getInt("prefecture_id");

				UserEntity user = new UserEntity();
				user.setId(id);
				user.setName(name);
				user.setEmail(email);
				user.setCreatedAt(created_at);
				user.setUpdatedAt(updated_at);
				user.setPrefectureId(prefecture_id);

				users.add(user);

			}
		} catch (SQLException e) {
			throw e;
		}
		return users;
	}

	public int createUsers(UserEntity user) {
		DatabaseConnector connector = new DatabaseConnector();
		String sql = "INSERT INTO users (name ,email, prefecture_id) VALUES (?, ?, ?)";

		try (Connection connection = connector.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setInt(3, user.getPrefectureId());
			int result = ps.executeUpdate();
			return result = 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public UserEntity findById(int id) throws SQLException {
		DatabaseConnector connector = new DatabaseConnector();
		String sql = "SELECT * FROM users WHERE id = ?";
		Connection connection = connector.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet resultSet = ps.executeQuery();

		if (resultSet.next()) {
			UserEntity user = new UserEntity();
			user.setId(resultSet.getInt("id"));
			user.setName(resultSet.getString("name"));
			user.setEmail(resultSet.getString("email"));
			user.setCreatedAt(resultSet.getTimestamp("created_at"));
			user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
			user.setPrefectureId(resultSet.getInt("prefecture_id"));
			return user;
		}
		return null;
	}

	public boolean findByEmail(String email) throws SQLException {
		String sql = "SELECT COUNT(*) AS count FROM users WHERE email = ?";
		DatabaseConnector connector = new DatabaseConnector();
		Connection connection = connector.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, email);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("count") > 0;
		}
		return false;
	}
	
	public boolean update(UserEntity user) throws SQLException {
		String sql = "UPDATE users SET name = ?, email = ?, prefecture_Id = ? WHERE id = ?";
		DatabaseConnector connector = new DatabaseConnector();
		Connection connection = connector.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, user.getName());
		ps.setString(2, user.getEmail());
		ps.setInt(3, user.getPrefectureId());
		ps.setInt(4, user.getId());
		int influence = ps.executeUpdate();
		return influence > 0; 
	}	
	
	public boolean deleteById(int id) throws SQLException {
		String sql = "DELETE users WHERE id = ?";
		DatabaseConnector connector = new DatabaseConnector();
		Connection connection = connector.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		int rowsAffected = ps.executeUpdate();
	    return rowsAffected > 0;
	}
}




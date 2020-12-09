package com.fes.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fes.app.entity.Users;
import com.fes.app.util.ConnectionManager;

public class UserService {
	private String get = "Select * from user where 1=1 and name like ? and password like ?";
	private String getall = "Select name from user where id=1";
	private String set = "Insert into user (name,password) values (? , ?)";
	private static UserService INSTANCE;

	public UserService() {
	}

	public static UserService getInstance() {
		if (null == INSTANCE) {
			INSTANCE = new UserService();
		}

		return INSTANCE;
	}

	public Boolean getUser() {
		Boolean ret = false;
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getall)) {

			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				ret=true;
			}
			
				
			
		} 
	catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	public Users getUser(String userName, String password) {

		try (Connection conn = ConnectionManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(get)) {

			
			
			stmt.setString(1, userName);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return getData(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void setUser(String userName, String password) {

		try (Connection conn = ConnectionManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(set)) {

			stmt.setString(1, userName);
			stmt.setString(2, password);
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public Users getData(ResultSet rs) throws SQLException {

		Users user = new Users();
		user.setUserName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		return user;
	}
	
}

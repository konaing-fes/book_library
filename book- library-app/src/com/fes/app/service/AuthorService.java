package com.fes.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.fes.app.entity.Author;
import com.fes.app.util.ConnectionManager;


public class AuthorService {
 
	public void add(Author a) {
		
		try (Connection conn = ConnectionManager.getConnection()) {
			PreparedStatement stmt =conn.prepareStatement("");
		} catch (Exception e) {
		
		}
	}
}

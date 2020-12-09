package com.fes.app.view;

import java.io.IOException;

import com.fes.app.entity.Users;
import com.fes.app.service.UserService;
import com.fes.app.util.ApplicationException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
	

public class Login {
	@FXML
	private Label info;
	@FXML
	private TextField user;
	@FXML
	private PasswordField password;
	@FXML
	private Button login;
	
	private UserService userService;
	
	
	public void login() throws IOException {		
	
	try {
			
			if(user.getText().isEmpty()) {
				throw new ApplicationException("Please Enter UserName");
			}
			
			if(password.getText().isEmpty()) {
				throw new ApplicationException("Please Enter Password");
			}
			
			if(false==userService.getUser()) {
				
				userService.setUser(user.getText(), password.getText());
				user.clear();
				password.clear();				
				info.setText("Please Login Your UserName and Password");
				login.setText("Login");
				return;
			}
				
		Users userdata = userService.getUser(user.getText(), password.getText());
			
		if(null==userdata) {
			
			throw new ApplicationException("User Not Found !");
			
		}
			
			if(!userdata.getUserName().equals(user.getText())) {
				throw new ApplicationException("Please enter correct user name!");}
				
			
			
			
			if(!userdata.getPassword().equals(password.getText())) {
				throw new ApplicationException("Please enter correct password!");}
			
			MainFrame.show();
			cancel();			
			
		} catch (Exception e) {			
			MessagesBox.show(e.getMessage());
		}
		
		
		
		
	}
	
	public void cancel() {
		
		info.getScene().getWindow().hide();
		
	}
	
	@FXML
	private void initialize() {
		userService = UserService.getInstance();
		
		
			if(false==userService.getUser()) {
			login.setText("Insert");
			info.setText("Hello New User \n Welcome to My Book Library ! \n  Please Set  Your \n  UserName and Password");
			}else {
				
				info.setText("Welcome to My Book Library !");
				
			}
	
		
	}

}

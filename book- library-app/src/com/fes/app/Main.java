package com.fes.app;

import java.io.FileInputStream;

import com.fes.app.view.Login;
import com.fes.app.view.MainFrame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{
	@Override
	public void start(Stage stage) throws Exception {
	Parent root = FXMLLoader.load(Login.class.getResource("Logoin.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Login");
		stage.getIcons().add(new Image(new FileInputStream("login.png")));
		stage.setScene(scene);
		stage.show();
	} 
	public static void main(String[] args) {
		launch(args);
	}

}

package com.fes.app.view;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class MainFrame implements Initializable {
	@FXML
	private Label title;
	@FXML
	private Label footer;
	@FXML
	private StackPane viewHoder;
	@FXML
	private VBox sideBar;
	
	
	public static void show() {
		try {
			FXMLLoader loader = new FXMLLoader(Login.class.getResource("MainFrame.fxml"));
			Stage stage = new Stage();
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setTitle("Book Library");
			stage.getIcons().add(new Image(new FileInputStream("search.png")));
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showHome(MouseEvent event) {
			changeActive(event);
			loadView("My Book Library", "Home");
	}
	public void showCategory(MouseEvent event) {
		changeActive(event);
		loadView("Book Category", "BookCategory");
	}
	
	public void showBook(MouseEvent event) {
		changeActive(event);
		loadView("Book List", "BookList");
	}
	
	public void showAuthor(MouseEvent event) {
		changeActive(event);
		loadView("Author List", "AuthorList");
	}
	
	public void showSaleHistory(MouseEvent event) {
		
		changeActive(event);
		loadView("Sale History", "SaleHistory");
	
	}
	
	
	private void changeActive(MouseEvent event) {
		Node node = (Node)event.getSource();
		sideBar.getChildren().stream()							
							 .filter(n -> n.getStyleClass().contains("active"))
							 .findAny()
							 .ifPresent(n -> n.getStyleClass().remove("active"));
		node.getStyleClass().add("active");
	}
	
	public void loadView(String viewName, String viewFile) {
		
		title.setText(viewName);
		try {
			Parent view =FXMLLoader.load(getClass().getResource(viewFile.concat(".fxml"))); 
			viewHoder.getChildren().clear();
			viewHoder.getChildren().add(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		footer.setText("\u00A9 By FES \u00A9");
		loadView("My Book Library", "Home");
	}
	

	
}
	
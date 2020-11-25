package com.fes.app.view;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;

import com.fes.app.entity.Book;
import com.fes.app.service.BookService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Detail {

	@FXML
	private ImageView image;
	@FXML
	private Label category;
	@FXML
	private Label authorName;
	@FXML
	private Label bookName;
	@FXML
	private Label price;
	@FXML
	private Label releasedDate;
	@FXML
	private Label remark;
	
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	public static void show(Book book) {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(Detail.class.getResource("Detial.fxml"));
			
			Stage stage = new Stage();			
			Parent view = loader.load();
			Detail controler = loader.getController();
			controler.setData(book);
			stage.setTitle("Book Detail");
			stage.setScene(new Scene(view));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
	}
	
	private void setData(Book book) throws FileNotFoundException {
		
		if(null != book) {
			category.setText(book.getCategory().getName());
			authorName.setText(book.getAuthor().getName());
			bookName.setText(book.getName());
			price.setText(String.valueOf(book.getPrice()));
			if(BookService.getInstance().getImage(book)==null) {
			image.setImage(new Image(new FileInputStream("notFound.jpg")));
			}else {image.setImage(new Image(new FileInputStream(BookService.getInstance().getImage(book))));}
			releasedDate.setText(String.valueOf(DF.format(book.getReleaseDate())));
			remark.setText(book.getRemark().isEmpty() ? "Unknown" : book.getRemark());
		}
		
		
	}
	
	public void buy() {
		
	}
	public void close() {
		
		category.getScene().getWindow().hide();
		
	}
}

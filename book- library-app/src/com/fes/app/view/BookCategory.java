package com.fes.app.view;

import java.io.File;
import java.io.IOException;
import java.util.List;


import com.fes.app.entity.Category;
import com.fes.app.service.CategoryService;
import com.fes.app.util.ApplicationException;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;






public class BookCategory {
	@FXML
	private TextField name;
	@FXML
	private FlowPane catBoxHolder;
	private CategoryService catService;

	public void add() throws IOException {
		
	try {
		if(name.getText().isEmpty()) {
		
			throw new ApplicationException("Please Enter Category Name");
			
		}else {
			
		Category c = new Category();
		c.setName(name.getText());
		
		catService.add(c);
		search();
		}
		
	} catch (Exception e) {
		MessagesBox.show(e.getMessage());
		
	}
		
	}
	
	public void upload() {
		
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose Category");
		fc.setSelectedExtensionFilter(new ExtensionFilter("Category", "*.txt","*.csv", "*.tsv"));
		File file = fc.showOpenDialog(name.getScene().getWindow());
		if(file!=null) {
		catService.upload(file);
		search();}
	}
	
	public void search() {
		catBoxHolder.getChildren().clear();
		List<Category> catList = catService.findByName(name.getText());

		catList.stream().map(CategoryBox::new)
						.forEach(catBoxHolder.getChildren()::addAll);
	}
	

	
	public void clear() {
		name.clear();
		catBoxHolder.getChildren().clear();
	}
	
	@FXML
	private void initialize() {
		catService = CategoryService.getInstance();
		search();
		
		name.textProperty().addListener((a, b, c) -> search());
		
name.setOnKeyPressed(e ->{
			
			if(e.getCode().equals(KeyCode.ENTER)) {
				
				try {
					add();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 
			}
		});
		
	}
	
	
	
	private class CategoryBox extends HBox {
		 CategoryBox(Category c) {
		
			getStyleClass().add("cat-box");
			Label catName = new Label(c.getName());
			getChildren().add(catName);
		}
	
	}


	
}	

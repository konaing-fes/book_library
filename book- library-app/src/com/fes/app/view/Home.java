package com.fes.app.view;

import com.fes.app.entity.SaleDetial;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Home {
	@FXML
	private ComboBox<String> category;
	@FXML
	private ComboBox<String> authorName;
	@FXML
	private TextField bookName;
	@FXML
	private DatePicker releasedDate;
	@FXML  
	private TableView<SaleDetial> tblist;
	
	public void addToCart(MouseEvent event) {
		
		if(event.getClickCount()==2) {
			
		}
		
	}
	
	public void search() {
		
	}
	
	public void clear() {
		
	}
	

}

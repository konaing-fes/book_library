package com.fes.app.view;

import com.fes.app.entity.CommonDTO;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
	private TableView<CommonDTO> tblist;
	
	public void search() {
		
	}
	
	public void clear() {
		
	}
	

}

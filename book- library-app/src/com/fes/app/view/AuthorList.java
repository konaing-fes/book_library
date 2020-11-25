package com.fes.app.view;

import java.util.List;

import com.fes.app.entity.Author;
import com.fes.app.service.AuthorService;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class AuthorList {
	
	@FXML
	private TextField name;
	@FXML
	private TextField age;
	@FXML
	private TextField country;
	@FXML
	private TableView<Author> tblist;
	@FXML
	private TableColumn<Author, String> nameCol;
	@FXML
	private TableColumn<Author, String> ageCol;
	@FXML
	private TableColumn<Author, String> countryCol;
	
	private AuthorService authorSb;
	
	public void add() {
		
		Author author = new Author();
		if(!name.getText().isEmpty())
		author.setName(name.getText());
		
		if(null!=age.getText()&& !age.getText().isEmpty()) {
		author.setAge(Integer.parseInt(age.getText()));}
		
		if(!country.getText().isEmpty())
		author.setCountry(country.getText());
		authorSb.add(author);		
		clear();
		search();
	}
	
	public void search() {
		tblist.getItems().clear();
		int age=this.age.getText().isEmpty() ? 0:Integer.parseInt(this.age.getText());
		List<Author> authorList = authorSb.finByParams(name.getText(), age, country.getText());
		tblist.getItems().addAll(authorList);
		
	}
	
	public void clear() {
		
		name.clear();
		age.clear();
		country.clear();
		
	}
	
	@FXML
	private void initialize() {
		authorSb = AuthorService.getInstance();
		search();
		
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setOnEditCommit(e -> {
			Author data = e.getRowValue();
			data.setName(e.getNewValue());
			authorSb.update(data);
			search();
		});
		
		countryCol.setCellFactory(TextFieldTableCell.forTableColumn());
		countryCol.setOnEditCommit(e -> {
			Author data = e.getRowValue();
			data.setCountry(e.getNewValue());
			authorSb.update(data);
			search();
		});
		
		MenuItem delete = new MenuItem("Delete");
		tblist.setContextMenu(new ContextMenu(delete));
		delete.setOnAction(e -> {
			
			Author author = tblist.getSelectionModel().getSelectedItem();
			authorSb.delete(author);
			search();
			
		});
		
		name.textProperty().addListener((a, b, c) -> search());
		age.textProperty().addListener((a, b, c) -> search());
		country.textProperty().addListener((a, b, c) -> search());
		
	}

}

package com.fes.app.view;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

import com.fes.app.entity.Author;
import com.fes.app.entity.Book;
import com.fes.app.entity.Category;
import com.fes.app.service.AuthorService;
import com.fes.app.service.BookService;
import com.fes.app.service.CategoryService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class BookList {
	
	   @FXML
	   private ComboBox<Category> category;
	   @FXML
	   private ComboBox<Author> authorName;
	   @FXML
	   private DatePicker releasedDate;
	   @FXML
	   private TableView<Book> tbList;
	   
	   private CategoryService catService;
	   private AuthorService athService;
	   private BookService bookService;
	   private Consumer<Book>listener;
	   
	   public void add() {
		   
		   BookEdit.show(null, this::listener);
	   }
	   
	   public void search() {
		   tbList.getItems().clear();		   
		   List<Book> bookList = bookService.findByParams(category.getValue(), authorName.getValue(), null, releasedDate.getValue());
		   tbList.getItems().addAll(bookList);
	}
	   public void clear() {
		   
		   category.setValue(null);
		   authorName.setValue(null);
		   releasedDate.setValue(LocalDate.now());
	}
	  
	   private void loadCategory() {
		   
		   List<Category> catList = catService.findAll();
		   category.getItems().addAll(catList);
		   //catList.stream().map(a -> a.getName()).forEach(category.getItems()::addAll);
		   //catList.stream().map(a -> a.getName()).forEach(a -> category.getItems().addAll(a));
	}
	   private void loadAuthor() {
		   
		   
		   List<Author> attList = athService.finByAll();
		   //attList.stream().map(a -> a.getName()).forEach(authorName.getItems()::addAll);
		   authorName.getItems().addAll(attList);
		   //catList.stream().map(a -> a.getName()).forEach(a -> category.getItems().addAll(a));

	}
	   
	   private void listener(Book book) {
		   
		   listener.accept(book);
		   search();
		   
	   }
	   
	   private void edit() {
		   Book book = tbList.getSelectionModel().getSelectedItem(); 
		   BookEdit.show(book,this::listener);
		   bookService.updae(book);
		   search();
	   }
	   
	   private void delete() {
		   Book book = tbList.getSelectionModel().getSelectedItem(); 
		   bookService.delete(book);
		   search();
	   }
	   
	   private void imageUpload() {
		   
		   FileChooser fc = new FileChooser();
		   fc.setTitle("Select Book Cover");
		   fc.setSelectedExtensionFilter(new ExtensionFilter("Book Cover", "*.png", "*.jpeg"));
		   fc.setInitialDirectory(new File(System.getProperty("user.home"), "desktop"));
		   File file = fc.showOpenDialog(category.getScene().getWindow());
		   
		   Book book = tbList.getSelectionModel().getSelectedItem();
		   book.setImage(file.getAbsolutePath());
		   bookService.imgUpload(book);
		   
	   }
	   
	   private void showDetial() {
		   Book book = tbList.getSelectionModel().getSelectedItem();
		   Detail.show(book);
		   
	   }
	   
	   private void createMenu() {
		   
		   MenuItem edit = new MenuItem("Edit");
		   MenuItem delete = new MenuItem("Delete");
		   MenuItem imageUpload = new MenuItem("Image Upload");
		   MenuItem detial = new MenuItem("Show Detial");
		   
		   ContextMenu menu = new ContextMenu(edit, delete, imageUpload, detial);
		   tbList.setContextMenu(menu);
		   
		   edit.setOnAction(e-> edit());
		   delete.setOnAction(e-> delete());
		   imageUpload.setOnAction(e-> imageUpload());
		   detial.setOnAction(e-> showDetial());
	   }
	   @FXML
	   private void initialize() {
		   catService = CategoryService.getInstance();
		   athService =AuthorService.getInstance();
		   bookService = BookService.getInstance();
		   loadCategory();
		   loadAuthor();
		   search();
		   createMenu();
		   
		   category.valueProperty().addListener((a, b, c) -> search());
		   authorName.valueProperty().addListener((a, b, c) -> search());
		   releasedDate.valueProperty().addListener((a, b, c) -> search());
	   }
	   

}

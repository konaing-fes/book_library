package com.fes.app.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

import com.fes.app.entity.Author;
import com.fes.app.entity.Book;
import com.fes.app.entity.Category;
import com.fes.app.service.AuthorService;
import com.fes.app.service.BookService;
import com.fes.app.service.CategoryService;
import com.fes.app.util.ApplicationException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class BookEdit {

	@FXML
	private Label info;
	@FXML
	private Label title;
	@FXML
	private ComboBox<Category> category;
	@FXML
	private ComboBox<Author> authorName;
	@FXML
	private TextField bookName;
	@FXML
	private TextField price;
	@FXML
	private DatePicker releasedDate;
	@FXML
	private TextArea remark;
	private BookService bookService;
	private Book book;
	private static Stage stage;
	private Consumer<Book> saveHandler;

	public static void show(Book book, Consumer<Book> saveHandler) {

		try {

			FXMLLoader loader = new FXMLLoader(BookEdit.class.getResource("BookEdit.fxml"));
			
			stage = new Stage();
			Parent view = loader.load();
			BookEdit controller = loader.getController();
			controller.saveHandler = saveHandler;
			controller.setData(book);
			stage.setScene(new Scene(view));
			// stage.initStyle(StageStyle.UTILITY);
			// stage.setTitle("Add Author");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void save() {

		try {
				Boolean bookIsNull = null == book;
			if (bookIsNull) {
				book = new Book();
			}
			
			book.setCategory(category.getValue());
			if(null==category.getValue())
				throw new ApplicationException("Please Select Category");
			
			book.setAuthor(authorName.getValue());
			if(null==authorName.getValue()) 
				throw new ApplicationException("Please Select Author Name");
			
			book.setName(bookName.getText());
			if(null==bookName.getText()&&bookName.getText().isEmpty())
				throw new ApplicationException("Please Enter Book Name");
			
			book.setPrice(Integer.parseInt(price.getText()));
			if(null==price.getText() && price.getText().isEmpty())
				throw new ApplicationException("Please Enter Book Price");
			
			book.setReleaseDate(releasedDate.getValue());
			if(null==releasedDate.getValue())
				throw new ApplicationException("Please Select Book Released Date");
			
			book.setRemark(remark.getText());
			
			if(bookIsNull) {
				bookService.add(book);
			}else {
				bookService.updae(book);
			}
			
			close();
			saveHandler.accept(book);

		} catch (Exception e) {

			info.setText(e.getMessage());

		}

	}

	private void setData(Book book) throws FileNotFoundException {

		this.book = book;
		if (null != book) {

			category.setValue(book.getCategory());
			authorName.setValue(book.getAuthor());
			bookName.setText(book.getName());
			price.setText(String.valueOf(book.getPrice()));
			releasedDate.setValue(book.getReleaseDate());
			remark.setText(book.getRemark());

			title.setText("EDIT BOOK");
			stage.setTitle("EDIT BOOK");
			stage.getIcons().add(new Image(new FileInputStream("edit.png")));

		} else {
			title.setText("ADD BOOK");
			stage.setTitle("ADD BOOK");
			stage.getIcons().add(new Image(new FileInputStream("add.png")));

		}

	}

	public void close() {

		info.getScene().getWindow().hide();

	}

	@FXML
	private void initialize() {
		
		category.getItems().addAll(CategoryService.getInstance().findAll());
		authorName.getItems().addAll(AuthorService.getInstance().finByAll());
		bookService = BookService.getInstance();
		
	}
}

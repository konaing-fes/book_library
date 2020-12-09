package com.fes.app.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fes.app.entity.Book;
import com.fes.app.entity.Category;
import com.fes.app.entity.Sale;
import com.fes.app.entity.SaleDTO;
import com.fes.app.entity.SaleDetial;
import com.fes.app.service.BookService;
import com.fes.app.service.CategoryService;
import com.fes.app.service.SaleService;
import com.fes.app.util.ApplicationException;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Home {
	@FXML
	private ComboBox<Category> category;
	@FXML	
	private TextField bookName;
	@FXML
	private DatePicker releasedDate;
	@FXML  
	private TableView<Book> bookTblist;
	@FXML  
	private Label cartTotal;
	@FXML  
	private Label subTotal;
	@FXML  
	private Label tax;
	@FXML 
	private Label total;


	@FXML
	private TableView<SaleDetial> sdtTblist;
	private CategoryService catService;
	private BookService bookService;
	private SaleDetial sd;
	private List<SaleDetial> detialList;
	private SaleService saleService;
	private SaleDTO saleDTO;
	
	public void addToCart(MouseEvent event) {
		
		if(event.getClickCount()==2) {
			Book  book = bookTblist.getSelectionModel().getSelectedItem();			
			
			for(int i=0; sdtTblist.getItems().size()>i;i++) {
			if(book.getId()==sdtTblist.getItems().get(i).getBook().getId()) {				
				sd= sdtTblist.getItems().get(i);
			}else {sd=null;}
			
			}
			if(null==sd) {
			sd = new SaleDetial();
			sd.setQuantity(1);
			sd.setBook(book);
			sd.setUnitPrice(book.getPrice());			
			detialList.add(sd);
			}
			else {
				sd.setQuantity(sd.getQuantity() + 1);
				detialList = new ArrayList<>(sdtTblist.getItems());
				
			}
			cal();
		}
		
	}
	
	private void cal() {
		int stotal=0;
		int tx=0;
		sdtTblist.getItems().clear();
		sdtTblist.getItems().addAll(detialList);
		
		for(int i=0; detialList.size()>i; i++) {
		stotal +=detialList.get(i).getUnitPrice()*detialList.get(i).getQuantity();
		}
		tx=(int)(stotal*0.05);
		subTotal.setText(String.valueOf(stotal));
		cartTotal.setText(String.valueOf(stotal+tx));
		tax.setText(String.valueOf(tx));
		total.setText(String.valueOf(stotal+tx));
		sd = null;
	}
	
	private void loadeCategory() {
		
		List<Category> list = catService.findAll();
		category.getItems().addAll(list);
		
		
	}
	
		
	public void search() {
		
		bookTblist.getItems().clear();
		
		List<Book> list = bookService.findByParams(category.getValue(),null, bookName.getText(), null);
		bookTblist.getItems().addAll(list);
		
	}
	
	public void clear() {
		
		detialList.clear();		
		sdtTblist.getItems().clear();
		cal();
		
	}
	
	public void paid() throws IOException {
		
		try {
			
			if(sdtTblist.getItems().isEmpty()) { 				
				
				throw new ApplicationException("Please add book to cart!");				
			}
			
			if(null==saleDTO) {
				saleDTO = new SaleDTO();
				Sale s = saleDTO.getSale();
				s.setSaleDate(LocalDate.now());
				s.setSaleTime(LocalTime.now());
				s.setTax(Integer.parseInt(tax.getText()));

		}
			saleDTO.getDetial().clear();
			saleDTO.getDetial().addAll(sdtTblist.getItems());
			saleService.Inset(saleDTO);
			clear();
			
		} catch (Exception e) {
			 
			if(e.getMessage()=="Please add book to cart!" ) {
				
				MessagesBox.show(e.getMessage());
			}
			else {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
 public void initialize () {
		catService = CategoryService.getInstance();
		bookService = BookService.getInstance();
		saleService = SaleService.getInstance();
		detialList = new ArrayList<>();
	 loadeCategory();
	 search();
	 
	 category.valueProperty().addListener((a, b, c) -> search());
	 bookName.textProperty().addListener((a,b,c)-> search());
 }
}

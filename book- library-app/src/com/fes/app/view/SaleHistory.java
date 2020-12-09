package com.fes.app.view;

import java.util.List;

import com.fes.app.entity.Category;
import com.fes.app.entity.SaleDetial;
import com.fes.app.service.CategoryService;
import com.fes.app.service.SaleService;


import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SaleHistory {
	@FXML
	private ComboBox<Category> category;
	@FXML
	private TextField bookName;
	@FXML
	private DatePicker dateFrom;
	@FXML
	private DatePicker dateTo;
	@FXML
	private TableView<SaleDetial> tblist;
	private SaleService saleService;
	private CategoryService categoryService;
	
	public void search() {
		tblist.getItems().clear();
		List<SaleDetial> dtlist=saleService.findSaleDetial(category.getValue(), bookName.getText(), dateFrom.getValue(), dateTo.getValue());
		
	tblist.getItems().addAll(dtlist);
	}
	
	public void clear() {
		tblist.getItems().clear();
		category.setValue(null);
		bookName.clear();
		dateTo.setValue(null);
		dateFrom.setValue(null);		
	}
	
	@FXML
	private void initialize() {
		categoryService =CategoryService.getInstance();
		saleService=SaleService.getInstance();
		category.getItems().addAll(categoryService.findAll());
		search();
	}

}

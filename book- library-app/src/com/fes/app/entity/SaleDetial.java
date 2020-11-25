package com.fes.app.entity;

public class SaleDetial {
	
	
	
	private int id;		
	private Book book;
	private Author author;
	private Category category;
	private Sale sale;
	private int quantity;
	private int unitPrice;
	private boolean delete;
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public String getCategoryName() {
		
		return category.getName();
		
	}
	
	public String getAuthorName() {
		
		return author.getName();
	}
	
	public String getBookName() {
		
		return book.getName();
	}
	
	public int getSaleTax() {
		
		return sale.getTax();
	}
	
	
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public int getSubTotal() {
		
		return getQuantity()*getUnitPrice();
	}
	
	public int getTotal() {
		
		return getSubTotal()+ getSaleTax();
	}
	
}

package com.fes.app.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fes.app.entity.Author;
import com.fes.app.entity.Book;
import com.fes.app.entity.Category;
import com.fes.app.entity.Sale;
import com.fes.app.entity.SaleDTO;
import com.fes.app.entity.SaleDetial;
import com.fes.app.util.ConnectionManager;

public class SaleService {
	
	private static SaleService INSTANCE;
	
	private SaleService() {};
	
	public static SaleService getInstance() {
		if(null == INSTANCE) {
			
			INSTANCE = new SaleService();
		}
	return INSTANCE;		
	}
	
	public void Inset(SaleDTO saleDTO) {
		
		String saleInsert = "Insert into sale (sale_date, sale_time, tax) values (?,?,?)" ;
		String detailInsert = "Insert into sale_detail (quantity, unit_price, book_id, sale_id, book_category_id, book_author_id) values(?,?,?,?,?,?)";
		
		try (Connection conn= ConnectionManager.getConnection();
				PreparedStatement insertSale = conn.prepareStatement(saleInsert,Statement.RETURN_GENERATED_KEYS);
				PreparedStatement insertDetial = conn.prepareStatement(detailInsert)){
			
			Sale sale = saleDTO.getSale();
			
			insertSale.setDate(1, Date.valueOf(sale.getSaleDate()));
			insertSale.setTime(2, Time.valueOf(sale.getSaleTime()));
			insertSale.setInt(3, sale.getTax());
			insertSale.executeUpdate();
			
			ResultSet rs = insertSale.getGeneratedKeys();
			while(rs.next()) {
				
				int saleId=(rs.getInt(1));
				
				List<SaleDetial> detial = saleDTO.getDetial();
				
				for (SaleDetial sd : detial) {
					
					insertDetial.setInt(1, sd.getQuantity());
					insertDetial.setInt(2, sd.getUnitPrice());
					insertDetial.setInt(3, sd.getBook().getId());
					insertDetial.setInt(4, saleId);
					insertDetial.setInt(5, sd.getBook().getCategory().getId());
					insertDetial.setInt(6, sd.getBook().getAuthor().getId());
					insertDetial.addBatch();
					
				}
				
				insertDetial.executeBatch();
			}
			
		} catch (Exception e) {

		e.printStackTrace();
			
		}
	}
	
	public void update(SaleDTO saleDTO) {
		
		String insertSD = "Insert into sale_detial (quantity, unit_price, book_id, sale_id,book_category_id, book_author_id) values(?,?,?,?,?,?)";
		String updateSD = "Update sale_detial quantity = ?, unit_price = ? where id = ?";
		String deleteSD = "Delete for sale_detial where id = ?";
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stInset = conn.prepareStatement(insertSD);
				PreparedStatement stUpdate = conn.prepareStatement(updateSD);
				PreparedStatement stDelete = conn.prepareStatement(deleteSD);){
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		
		}
		
	}
	
	
	public List<Sale> findSale(LocalDate dateFrom, LocalDate dateTo){
		
		String sql = "select * for sale  where 1=1";
		
		List<Sale> result = new ArrayList<>();
		StringBuilder sb = new StringBuilder(sql);
		List<Object> params = new LinkedList<>();
		
		if(null!=dateFrom&& dateFrom.isBefore(dateTo)) {
			
			sb.append(" and sale_date >=?");
			params.add(Date.valueOf(dateFrom));
		}
		
		if(null!=dateTo&& dateTo.isAfter(dateFrom)) {
			sb.append(" and sale_date <=?");
			params.add(Date.valueOf(dateTo));
		}
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i+1, params.get(i));				
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Sale s = new Sale();
				s.setId(rs.getInt("id"));
				s.setSaleDate(rs.getDate("sale_date").toLocalDate());
				s.setSaleTime(rs.getTime("sale_time").toLocalTime());
				result.add(s);
			}
			
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
		return result;
	}
	
	public List<SaleDetial> findSaleDetial(Category category, String bookName, LocalDate dateFrom, LocalDate dateTo){
		
		String sql = "select sd.id sd_id, sd.sale_id sale_id, "
				+ "c.name  category_name, b.name  book_name, a.name  author_name, s.tax  tax, "
				+ "sd.book_id book_id, sd.book_category_id book_category_id, "
				+ "sd.book_author_id book_author_id, sd.unit_price  price, "
				+ "sd.quantity  quantity "
				+ "from sale_detail sd join book b on sd.book_id = b.id join sale s "
				+ "on sd.sale_id = s.id join category c on sd.book_category_id = c.id "
				+ "join author a on sd.book_author_id = a.id where 1 = 1";
		List<SaleDetial> result= new ArrayList<>();
		StringBuilder sb = new StringBuilder(sql); 
		List<Object> params = new LinkedList<>();
		
		if(null!=category) {
			
			sb.append(" and c.name like ?");
			params.add(category.getName()); 
			
		}
		
		if(null!=bookName && !bookName.isEmpty()) {
			
			sb.append(" and b.name like ?");
			params.add("%".concat(bookName).concat("%") );
		}
		
		if(null!=dateFrom && dateFrom.isBefore(dateTo)) {
			
			sb.append("and sale_date >= ?");
			params.add(Date.valueOf(dateFrom));
		}
		
		
		if(null!= dateTo&& dateTo.isAfter(dateFrom)) {
			
			sb.append(" and sale_date <= ?");
			params.add(Date.valueOf(dateTo));
		}
		
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())){
			
			for (int i = 0; i < params.size(); i++) {
				
				stmt.setObject(i+1, params.get(i));
				
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				
				SaleDetial sd = new SaleDetial();
				
				sd.setId(rs.getInt("sd_id"));
				sd.setQuantity(rs.getInt("quantity"));
				sd.setUnitPrice(rs.getInt("price"));
				sd.setDelete(true);
				
				Sale s = new Sale();
				s.setId(rs.getInt("sale_id"));
				s.setTax(rs.getInt("tax"));
				
				Book b = new Book();
				b.setId(rs.getInt("book_id"));
				b.setName(rs.getString("book_name"));
				
				Category c = new Category();
				c.setId(rs.getInt("book_category_id"));
				c.setName(rs.getString("category_name"));
				
				Author a = new Author();
				a.setId(rs.getInt("book_author_id"));
				a.setName(rs.getString("author_name"));
				
				sd.setAuthor(a);
				sd.setBook(b);
				sd.setCategory(c);
				sd.setSale(s);
				
				result.add(sd);
				
			}
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
		return result;
	
		
	}
	

}

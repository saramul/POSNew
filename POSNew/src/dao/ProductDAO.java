package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.Product;
import util.MySQLHelper;

public class ProductDAO {
	
	public String generateProductId() {
		String id = "PRO-00000001";
		String sql = "select id from product order by id desc limit 1";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getString(1);
				id = id.substring(4);
				int num = Integer.parseInt(id);
				num++;
				
				if(num<10)
					id = "PRO-0000000" + num;
				else if(num<100)
					id = "PRO-000000" + num;
				else if(num<1000)
					id = "PRO-00000" + num;
				else if(num<10000)
					id = "PRO-0000" + num;
				else if(num<100000)
					id = "PRO-000" + num;
				else if(num<1000000)
					id = "PRO-00" + num;
				else if(num<10000000)
					id = "PRO-0" + num;
				else
					id = "PRO-" + num;
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public ObservableList<Product> select() {
		ObservableList<Product> products = FXCollections.observableArrayList();
		String sql = "select * from product";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				int amount = rs.getInt(3);
				int price = rs.getInt(4);
				String catId = rs.getString(5);	
				CategoryDAO dao = new CategoryDAO();
				Category category = dao.selectById(catId);
				
				
				Product product = new Product(id, name, amount, price, category);
				products.add(product);
				
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}
	
	public ObservableList<Product> selectByName(String name) {
		ObservableList<Product> products = FXCollections.observableArrayList();
		String sql = "select * from product where name like ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString(1);
				String sname = rs.getString(2);
				int amount = rs.getInt(3);
				int price = rs.getInt(4);
				String catId = rs.getString(5);
				CategoryDAO dao = new CategoryDAO();
				Category category = dao.selectById(catId);
				
				Product product = new Product(id, sname, amount, price, category);
				products.add(product);
				
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}
	
	public Product selectById(String id) {
		Product product = new Product();
		String sql = "select * from product where id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {								
				String sid = rs.getString(1);
				String sname = rs.getString(2);
				int amount = rs.getInt(3);
				int price = rs.getInt(4);
				String catId = rs.getString(5);
				CategoryDAO dao = new CategoryDAO();
				Category category = dao.selectById(catId);
				
				product = new Product(sid, sname, amount, price, category);								
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}
	
	public boolean insert(Product product) {
		boolean result = false;
		String sql = "insert into product(id, name, amount, price, cat_id) values(?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, product.getId());
			ps.setString(2, product.getName());
			ps.setInt(3, product.getAmount());
			ps.setInt(4, product.getPrice());
			ps.setString(5, product.getCategory().getId());
			
			int row = ps.executeUpdate();
			if(row>0) {
				result = true;
			}
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public boolean update(Product product) {
		boolean result = false;
		String sql = "update product set name = ?, amount = ?, price = ?, cat_id = ? where id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(5, product.getId());
			ps.setString(1, product.getName());
			ps.setInt(2, product.getAmount());
			ps.setInt(3, product.getPrice());
			ps.setString(4, product.getCategory().getId());
			
			int row = ps.executeUpdate();
			if(row>0) {
				result = true;
			}
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public boolean delete(String id) {
		boolean result = false;
		String sql = "delete from product where id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);			
			ps.setString(1, id);
			
			int row = ps.executeUpdate();
			if(row>0) {
				result = true;
			}
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}

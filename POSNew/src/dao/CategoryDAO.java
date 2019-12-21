package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import util.MySQLHelper;

public class CategoryDAO {
	
	public String generateCatId() {
		String id = "CAT-0001";
		String sql = "select id from category order by id desc limit 1";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getString(1);
				id = id.substring(4);
				int num = Integer.parseInt(id);
				num++;
				
				if(num<10)
					id = "CAT-000" + num;
				else if(num<100)
					id = "CAT-00" + num;
				else if(num<1000)
					id = "CAT-0" + num;
				else
					id = "CAT-" + num;
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
	
	public ObservableList<Category> select() {
		ObservableList<Category> categories = FXCollections.observableArrayList();
		String sql = "select * from category";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				
				Category category = new Category(id, name);
				categories.add(category);
				
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categories;
	}
	
	public ObservableList<Category> selectByName(String name) {
		ObservableList<Category> categories = FXCollections.observableArrayList();
		String sql = "select * from category where name like ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString(1);
				String sname = rs.getString(2);
				
				Category category = new Category(id, sname);
				categories.add(category);
				
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categories;
	}
	
	public Category selectById(String id) {
		Category category = new Category();
		String sql = "select * from category where id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {								
				category = new Category(rs.getString(1), rs.getString(2));								
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return category;
	}
	
	public boolean insert(Category category) {
		boolean result = false;
		String sql = "insert into category(id, name) values(?, ?)";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, category.getId());
			ps.setString(2, category.getName());
			
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
	public boolean update(Category category) {
		boolean result = false;
		String sql = "update category set name = ? where id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(2, category.getId());
			ps.setString(1, category.getName());
			
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
		String sql = "delete from category where id = ?";
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

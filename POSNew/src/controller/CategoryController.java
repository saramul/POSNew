package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.CategoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Category;

public class CategoryController implements Initializable{

	private ObservableList<Category> categories;
	private int index;
    @FXML
    private AnchorPane category_frame;

    @FXML
    private TableView<Category> tb_category;

    @FXML
    private TableColumn<Category, String> col_cat_id;

    @FXML
    private TableColumn<Category, String> col_cat_name;

    @FXML
    private JFXTextField cat_id;

    @FXML
    private JFXTextField cat_name;

    @FXML
    private JFXButton btn_insert;

    @FXML
    private JFXButton btn_update;

    @FXML
    private JFXButton btn_clear;

    @FXML
    void clearCategory(ActionEvent event) {
    	cat_name.setText("");
    	loadCategoryId();
    	
    	btn_update.setDisable(true);
    	btn_insert.setDisable(false);
    	
    	tb_category.getSelectionModel().clearSelection();
    }

    @FXML
    void insertCategory(ActionEvent event) {
    	String id = cat_id.getText();
    	String name = cat_name.getText();
    	
    	if(name.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Warning Message");
    		alert.setContentText("Full fill data!");
    		alert.showAndWait();
    	}else {
    		CategoryDAO dao = new CategoryDAO();
    		Category category = new Category(id, name);
    		boolean result = dao.insert(category);
    		if(result) {
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setHeaderText("Information Message");
        		alert.setContentText("Insert category successfull!");
        		alert.showAndWait();
        		
        		tb_category.getItems().add(category);
    		} else {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setHeaderText("Error Message");
        		alert.setContentText("Unbable to insert category!");
        		alert.showAndWait();
    		}
    		clearText();
    	}
    }

    private void clearText() {
		// TODO Auto-generated method stub
		cat_name.setText("");
		loadCategoryId();
	}

	@FXML
    void updateCategory(ActionEvent event) {
		String id = cat_id.getText();
    	String name = cat_name.getText();
    	
    	if(name.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Warning Message");
    		alert.setContentText("Full fill data!");
    		alert.showAndWait();
    	}else {
    		CategoryDAO dao = new CategoryDAO();
    		Category category = new Category(id, name);
    		boolean result = dao.update(category);
    		if(result) {
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setHeaderText("Information Message");
        		alert.setContentText("Update category successfull!");
        		alert.showAndWait();
        		
        		tb_category.getItems().set(index, category);
    		} else {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setHeaderText("Error Message");
        		alert.setContentText("Unbable to update category!");
        		alert.showAndWait();
    		}
    		clearText();
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		categories = FXCollections.observableArrayList();
		
		initCategoryTable();
		loadCategoryToTable();		
		loadCategoryId();
	}

	private void loadCategoryId() {
		// TODO Auto-generated method stub
		CategoryDAO dao = new CategoryDAO();
		String catId = dao.generateCatId();
		cat_id.setText(catId);
	}

	private void loadCategoryToTable() {
		// TODO Auto-generated method stub
		CategoryDAO dao = new CategoryDAO();
		categories = dao.select();
		
		tb_category.setItems(categories);	
	}

	private void initCategoryTable() {
		// TODO Auto-generated method stub
		col_cat_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_cat_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		tb_category.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				Category category = tb_category.getSelectionModel().getSelectedItem();
				cat_id.setText(category.getId());
				cat_name.setText(category.getName());
				
				index = tb_category.getSelectionModel().getSelectedIndex();
				
				btn_insert.setDisable(true);
				btn_update.setDisable(false);
			}
		});
	}

}

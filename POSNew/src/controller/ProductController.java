package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import dao.CategoryDAO;
import dao.ProductDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Category;
import model.Product;

public class ProductController implements Initializable{

	private ObservableList<Product> products;
	private ObservableList<Category> categories;
	private int tableIndex;
	private int cmbIndex;
	
    @FXML
    private AnchorPane product_frame;

    @FXML
    private TableView<Product> tb_product;

    @FXML
    private TableColumn<Product, String> col_product_id;

    @FXML
    private TableColumn<Product, String> col_product_name;

    @FXML
    private TableColumn<Product, Integer> col_product_amount;

    @FXML
    private TableColumn<Product, Integer> col_product_price;

    @FXML
    private TableColumn<Product, Category> col_product_category;

    @FXML
    private JFXTextField pro_id;

    @FXML
    private JFXTextField pro_name;

    @FXML
    private JFXTextField pro_amount;

    @FXML
    private JFXTextField pro_price;

    @FXML
    private JFXComboBox<Category> cmb_category;

    @FXML
    private JFXTextField pro_search;

    @FXML
    private JFXButton btn_search;

    @FXML
    private JFXButton btn_insert;

    @FXML
    private JFXButton btn_update;

    @FXML
    private JFXButton btn_clear;

    @FXML
    void clearProduct(ActionEvent event) {
    	tb_product.getSelectionModel().clearSelection();
    	clearText();
    	
    	btn_update.setDisable(true);
		btn_insert.setDisable(false);
		
		loadProductToTable();
    }

    @FXML
    void insertProduct(ActionEvent event) {

    	String id = pro_id.getText();
    	String name = pro_name.getText();
    	
    	if(name.isEmpty() ||
    			pro_amount.getText().isEmpty() ||
    			pro_price.getText().isEmpty() ||
    			cmb_category.getItems().isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Warning Message");
    		alert.setContentText("Full fill data!");
    		alert.showAndWait();
    	} else {
        	String catId = cmb_category.getSelectionModel().getSelectedItem().getId();
        	String catName = cmb_category.getSelectionModel().getSelectedItem().getName();
        	Category category = new Category(catId, catName);
        	
        	int amount = Integer.parseInt(pro_amount.getText());
        	int price = Integer.parseInt(pro_price.getText());
        	
        	Product product = new Product(id, name, amount, price, category);
        	ProductDAO dao = new ProductDAO();
        	
        	boolean result = dao.insert(product);
        	
        	if(result) {
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setHeaderText("Information Message");
        		alert.setContentText("Insert product successfull!");
        		alert.showAndWait();
        		
        		tb_product.getItems().add(product);
    		} else {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setHeaderText("Error Message");
        		alert.setContentText("Unbable to insert product!");
        		alert.showAndWait();
    		}
    		clearText();
    	}
    	
    }

    private void clearText() {
		// TODO Auto-generated method stub
		pro_name.setText("");
		pro_amount.setText("");
		pro_price.setText("");
		cmb_category.getSelectionModel().clearSelection();
		
		
		loadProductId();
	}

	@FXML
    void updateProduct(ActionEvent event) {
		String id = pro_id.getText();
    	String name = pro_name.getText();
    	
    	if(name.isEmpty() ||
    			pro_amount.getText().isEmpty() ||
    			pro_price.getText().isEmpty() ||
    			cmb_category.getItems().isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Warning Message");
    		alert.setContentText("Full fill data!");
    		alert.showAndWait();
    	} else {
        	String catId = cmb_category.getSelectionModel().getSelectedItem().getId();
        	String catName = cmb_category.getSelectionModel().getSelectedItem().getName();
        	Category category = new Category(catId, catName);
        	int amount = Integer.parseInt(pro_amount.getText());
        	int price = Integer.parseInt(pro_price.getText());
        	
        	Product product = new Product(id, name, amount, price, category);
        	ProductDAO dao = new ProductDAO();
        	
        	boolean result = dao.update(product);
        	
        	if(result) {
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setHeaderText("Information Message");
        		alert.setContentText("Update product successfull!");
        		alert.showAndWait();
        		
//        		tb_product.getItems().set(tableIndex, product);
        		loadProductToTable();
        		
    		} else {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setHeaderText("Error Message");
        		alert.setContentText("Unbable to update product!");
        		alert.showAndWait();
    		}
    		clearText();
    		
    		btn_insert.setDisable(false);
    		btn_update.setDisable(true);
    		
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		categories = FXCollections.observableArrayList();
		products = FXCollections.observableArrayList();
		
		initProductTable();
		loadProductToTable();		
		loadProductId();
		loadCategoryToComboBox();
	}

	private void loadCategoryToComboBox() {
		// TODO Auto-generated method stub
		CategoryDAO dao = new CategoryDAO();
		categories = dao.select();
		
		cmb_category.setItems(categories);
	}

	private void loadProductId() {
		// TODO Auto-generated method stub
		ProductDAO dao = new ProductDAO();
		String id = dao.generateProductId();
		
		pro_id.setText(id);
	}

	private void loadProductToTable() {
		// TODO Auto-generated method stub
		ProductDAO dao = new ProductDAO();
		products = dao.select();
		
		tb_product.setItems(products);
	}

	private void initProductTable() {
		// TODO Auto-generated method stub
		col_product_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		col_product_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		col_product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		col_product_category.setCellValueFactory(new PropertyValueFactory<>("category"));
		
		tb_product.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				tableIndex = tb_product.getSelectionModel().getSelectedIndex();
				Product product = tb_product.getSelectionModel().getSelectedItem();
				
				pro_id.setText(product.getId());
				pro_name.setText(product.getName());
				pro_amount.setText(product.getAmount() + "");
				pro_price.setText(product.getPrice() + "");
				
				int size = categories.size();
				for(int i=0; i<size; i++) {
					if(product.getCategory().getId().equals(categories.get(i).getId())) {
						cmb_category.getSelectionModel().select(i);
					}
				}
				
				btn_update.setDisable(false);
				btn_insert.setDisable(true);
				
			}
		});
		
	}
	@FXML
    void searchProduct(ActionEvent event) {
		String name = pro_search.getText();
		System.out.println(name);
		ProductDAO dao = new ProductDAO();		
		products = dao.selectByName(name);
		
		tb_product.setItems(products);
		
		pro_search.setText("");
    }

}

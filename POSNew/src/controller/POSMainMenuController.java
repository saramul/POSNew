package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class POSMainMenuController implements Initializable{
	@FXML
    private MenuItem menu_category;

    @FXML
    private MenuItem menu_product;
    @FXML
    private JFXButton toolbar_category;

    @FXML
    private JFXButton toolbar_product;

    @FXML
    private JFXButton toolbar_exit;

    @FXML
    private AnchorPane content_pane;

    @FXML
    void exitProgram(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    void loadCategoryFrame(ActionEvent event) {
    	setContentPane(fadeAnimation("/view/CategoryFrame.fxml"));
    }

    @FXML
    void loadProductFrame(ActionEvent event) {
    	setContentPane(fadeAnimation("/view/ProductFrame.fxml"));
    }

    private void setContentPane(Node node) {
		// TODO Auto-generated method stub
		content_pane.getChildren().setAll(node);
	}

	private AnchorPane fadeAnimation(String path) {
		// TODO Auto-generated method stub
		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource(path));
			FadeTransition ft = new FadeTransition(Duration.millis(1500));
			ft.setNode(pane);
			ft.setFromValue(0.1);
			ft.setToValue(1);
			ft.setCycleCount(1);
			ft.setAutoReverse(false);
			ft.play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pane;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}

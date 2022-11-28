package ekrut.gui;

import java.io.IOException;

import ekrut.server.ServerUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;

public class ServerPortSelectionController {

    @FXML
    private Button portSelectBtn;

    @FXML
    private TextField portTxt;
    
    @FXML
    private Label redLabel;

    @FXML
    void selectPort(ActionEvent event) throws IOException {
    	String portText = portTxt.getText().trim();
    	int port;
    	
    	if (portText.isEmpty()) {
    		redLabel.setVisible(true);
    		return;
    	} else {
    		try {
    			port = Integer.parseInt(portText);
    		} catch (NumberFormatException e) {
    			redLabel.setVisible(true);
    			return;
    		}
    		
    		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("ekrut/gui/ServerMainScene.fxml"));
    		Parent root = loader.load();
    		stage.getScene().setRoot(root);
    		ServerUI.runServer(port, loader.getController());
    	}
    }

}

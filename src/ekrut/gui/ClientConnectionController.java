package ekrut.gui;

import java.io.IOException;

import ekrut.client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientConnectionController {

    @FXML
    private Button connectBtn;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField hostTxt;

    @FXML
    private TextField portTxt;
    
    private final static String INVALID_VALUES = "The host or port number are invalid!";
    private final static String CONNECTION_FAILED = "Connection Failed! Please try again";
    
    private void setInvalidValues() {
    	errorLabel.setText(INVALID_VALUES);
    	errorLabel.setVisible(true);
    }
    
    private void setConnectionFailed() {
    	errorLabel.setText(CONNECTION_FAILED);
    	errorLabel.setVisible(true);
    }

    @FXML
    void connectToServer(ActionEvent event) throws IOException {
    	String host = hostTxt.getText().trim();
    	String portString = portTxt.getText().trim();
    	
    	if (host.isEmpty() || portString.isEmpty()) {
    		setInvalidValues();
    		return;
    	}
    	
    	int port;
    	
    	try {
    		port = Integer.parseInt(portString);
    	} catch (NumberFormatException e) {
    		setInvalidValues();
    		return;
    	}
    	
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ekrut/gui/ClientMain.fxml"));
		Parent root = loader.load();
		stage.getScene().setRoot(root);
		stage.sizeToScene();
		if (!ClientUI.runClient(host, port, loader.getController())) {
			setConnectionFailed();
		}
    }

}

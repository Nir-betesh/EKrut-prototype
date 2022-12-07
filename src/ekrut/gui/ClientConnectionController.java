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

	// ERROR messages
	private final static String INVALID_VALUES = "The host or port number are invalid!";
	private final static String CONNECTION_FAILED = "Connection Failed! Please try again";
	private final static String PORT_RANGE_VALUE = "Port not in range 1 - 65535";

	// ERROR label - Host or port invalid
	private void setInvalidValues() {
		errorLabel.setText(INVALID_VALUES);
		errorLabel.setVisible(true);
	}

	// ERROR label - Connection failed
	private void setConnectionFailed() {
		errorLabel.setText(CONNECTION_FAILED);
		errorLabel.setVisible(true);
	}
	
	// ERROR label - Invalid port
	private void setInvalidPortValue() {
		errorLabel.setText(PORT_RANGE_VALUE);
		errorLabel.setVisible(true);
	}
	
	private FXMLLoader loader;

	// Connect button
	@FXML
	void connectToServer(ActionEvent event) throws IOException {
		
		String host = hostTxt.getText().trim();
		String portString = portTxt.getText().trim();

		// Check if host text box is empty
		if (host.isEmpty() || portString.isEmpty()) { 
			setInvalidValues();
			return;
		}

		int port;
		
		// Check if host text is number
		try {
			port = Integer.parseInt(portString);
		} catch (NumberFormatException e) {
			setInvalidValues();
			return;
		}
		
		// Check if host box in range
		if (0 >= port || port >= 65536) {
			setInvalidPortValue();
			return;
		}
		
		// Load client main stage
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		if (loader == null) {
			loader = new FXMLLoader(getClass().getResource("/ekrut/gui/ClientMain.fxml"));
			loader.load();
		}
				
		// Run client stage.
		Parent root = loader.getRoot();
		if (!ClientUI.runClient(host, port, loader.getController())) {
			setConnectionFailed();
			return;
		}
		
		stage.getScene().setRoot(root);
		stage.sizeToScene();
	}

}


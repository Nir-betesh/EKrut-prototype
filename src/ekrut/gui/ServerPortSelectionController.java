package ekrut.gui;

import java.io.IOException;
import java.sql.SQLException;
import ekrut.server.ServerUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Node;
import javafx.scene.Parent;

public class ServerPortSelectionController {

	@FXML
	private Button portSelectBtn;

	@FXML
	private TextField portTxt;

	@FXML
	private TextField dbNameTxt;

	@FXML
	private PasswordField dbPasswordTxt;

	@FXML
	private TextField dbUsernameTxt;

	@FXML
	private Label redLabel;

	// ERROR messages.
	private final static String INVALID_PORT = "Port must be a valid integer in the range 1 - 65535";
	private final static String PORT_UNAVAILABLE = "Failed to listen to port. Try another one";
	private final static String DBERROR = "Failed to connect to Database. Try changing your settings";

	
	private void setInvalidPort() {
		redLabel.setText(INVALID_PORT);
		redLabel.setVisible(true); // Show ERROR message.
	}

	private void setUnavailablePort() {
		redLabel.setText(PORT_UNAVAILABLE);
		redLabel.setVisible(true);
	}
	
	private void setDBError() {
		redLabel.setText(DBERROR);
		redLabel.setVisible(true);
	}
	
	public void setFocus(WindowEvent event) {
		dbPasswordTxt.requestFocus();
	}

	private FXMLLoader loader;
	
	
	@FXML
	void selectSettings(ActionEvent event) throws IOException {
		redLabel.setVisible(false); // If an ERROR label was previously shown, hide it.  
		String portText = portTxt.getText().trim();
		int port;

		if (portText.isEmpty()) {
			setInvalidPort();
			return;
		} else {
			try {
				port = Integer.parseInt(portText);
			} catch (NumberFormatException e) {
				setInvalidPort();
				return;
			}

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			if (loader == null) {
				loader = new FXMLLoader(getClass().getResource("/ekrut/gui/ServerMainScene.fxml"));
				loader.load();
			}
			Parent root = loader.getRoot();
			try {
				// Try to launch server
				if (!ServerUI.runServer(port, dbNameTxt.getText(), dbUsernameTxt.getText(), dbPasswordTxt.getText(),
										loader.getController())) {
					setUnavailablePort();
					return;
				}
			} catch (SQLException e) {
				setDBError();
				return;
			}
			stage.getScene().setRoot(root); // If server was launch, change scene.
		}
	}

}

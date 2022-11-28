package ekrut.gui;

import java.net.InetAddress;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ocsf.server.ConnectionToClient;

public class ServerMainSceneController {

    @FXML
    private Label connectionHost;

    @FXML
    private Label connectionIP;

    @FXML
    private Label connectionStatus;
    
    public void clientConnected(ConnectionToClient client) {
    	Platform.runLater(() -> {
    		connectionStatus.setText("Connection established");
	    	InetAddress address = client.getInetAddress();
	    	connectionHost.setText(address.getCanonicalHostName());
	    	connectionIP.setText(address.getHostAddress());
	    });
    }
    
    public void clientDisconnected(ConnectionToClient client) {
    	Platform.runLater(() -> {
    		connectionStatus.setText("Not connected");
        	connectionHost.setText("");
        	connectionIP.setText("");
    	});
    }

}

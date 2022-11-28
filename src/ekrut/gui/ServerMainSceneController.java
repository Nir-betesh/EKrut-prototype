package ekrut.gui;

import java.net.InetAddress;

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
    	connectionStatus.setText("Connection established");
    	InetAddress address = client.getInetAddress();
    	connectionHost.setText(address.getHostName());
    	connectionIP.setText(address.getHostAddress());
    }
    
    public void clientDisconnected(ConnectionToClient client) {
    	connectionStatus.setText("Not connected");
    	connectionHost.setText("");
    	connectionIP.setText("");
    }

}

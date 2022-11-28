package ekrut.client;

import java.io.IOException;

import ekrut.gui.ClientMainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientUI extends Application {
	
	private static Client client;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/ekrut/gui/ClientConnection.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Client");
		primaryStage.show();
	}
	
	@Override
	public void stop() throws IOException {
		client.closeConnection();
	}
	
	public static boolean runClient(String host, int port, ClientMainController controller) {
		try {
			client = new Client(host, port, controller);
			
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}

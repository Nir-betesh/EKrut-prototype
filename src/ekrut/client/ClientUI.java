package ekrut.client;

import java.io.IOException;
import ekrut.gui.ClientMainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

 /*
  * Activate Application.
  */
public class ClientUI extends Application {

	private static Client client;

	// Launch application.
	public static void main(String[] args) {
		launch(args);
	}
	
	// Run Main client stage
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/ekrut/gui/ClientConnection.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Client");
		primaryStage.show();
	}

	// Close screen
	@Override
	public void stop() throws IOException {
		if (client != null)
			client.closeConnection();
	}

	// Attempt to launch client.
	public static boolean runClient(String host, int port, ClientMainController controller) {
		try {
			client = new Client(host, port, controller);
			return true;
			
		} catch (IOException e) {
			return false;
		}
	}

}

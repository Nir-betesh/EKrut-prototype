package ekrut.server;

import java.io.IOException;
import java.sql.SQLException;

import ekrut.gui.ServerMainSceneController;
import ekrut.gui.ServerPortSelectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Run server Application. 
 */
public class ServerUI extends Application {

	private static Server server;

	// Launch to server.
	public static void main(String[] args) {
		launch(args);
	}

	// Present the first Screen of server.
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load first screen
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ekrut/gui/ServerPortSelection.fxml"));
		Parent root = loader.load();
		ServerPortSelectionController controller = loader.getController();
		Scene scene = new Scene(root);

		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.setOnShown(controller::setFocus);
		primaryStage.show();
	}

	// Close server
	@Override
	public void stop() throws IOException {
		if (server != null)
			server.close();
	}

	// Attempt to launch server.
	public static boolean runServer(int port, String dbName, String dbUsername, String dbPassword,
									ServerMainSceneController controller) throws SQLException {
		server = new Server(port, dbName, dbUsername, dbPassword, controller);
		
		try {
			server.listen();
			return true;
		} catch (IOException | IllegalArgumentException e) {
			return false;
		}
	}

}

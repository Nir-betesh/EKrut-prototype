package ekrut.server;

import java.io.IOException;

import ekrut.gui.ServerMainSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/ekrut/gui/ServerPortSelection.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void runServer(int port, ServerMainSceneController controller) {
		Server server = new Server(port, controller);
		
		try {
			server.listen();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}

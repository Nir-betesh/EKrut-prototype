package ekrut.server;

import java.io.IOException;
import java.sql.SQLException;
import ekrut.common.Subscriber;
import ekrut.gui.ServerMainSceneController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {

	private DBController dbCon;
	private ServerMainSceneController controller;

	// Start listening for incoming messages.
	public Server(int port, String dbName, String dbUsername,
			String dbPassword, ServerMainSceneController controller) throws SQLException {
		super(port);
		dbCon = new DBController(dbName, dbUsername, dbPassword);  // Establish DB connection.
		this.controller = controller;
	}

	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			if (msg instanceof Subscriber) { // Subscriber update request.
				if (dbCon.updateSubscriber((Subscriber) msg))
					client.sendToClient("Success!");
				else
					client.sendToClient("Failed!");
			} else if (msg instanceof String) { // Subscriber fetch request.
				Subscriber sub = dbCon.getSubscriber((String) msg);
				if (sub == null)
					client.sendToClient("Couldn't locate subscriber id");
				else
					client.sendToClient(sub);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	// A client had connected to server.
	@Override
	protected void clientConnected(ConnectionToClient client) {
		controller.clientConnected(client);
	}

	// A client had disconnected from server (Exist "Nicely" & client crash).
	@Override
	protected synchronized void clientException(ConnectionToClient client, Throwable exception) {
		controller.clientDisconnected(client);
	}

}

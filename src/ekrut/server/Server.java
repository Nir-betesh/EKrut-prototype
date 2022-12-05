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

	public Server(int port, String dbName, String dbUsername, String dbPassword, ServerMainSceneController controller) 
			throws SQLException {
		super(port);
		dbCon = new DBController(dbName, dbUsername, dbPassword);
		this.controller = controller;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			if (msg instanceof Subscriber) {
				if (dbCon.updateSubscriber((Subscriber) msg))
					client.sendToClient("Success!");
				else
					client.sendToClient("Failed!");
			} else if (msg instanceof String) {
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

	@Override
	protected void clientConnected(ConnectionToClient client) {
		controller.clientConnected(client);
	}

	@Override
	protected synchronized void clientException(ConnectionToClient client, Throwable exception) {
		controller.clientDisconnected(client);
	}

}

package ekrut.server;

import java.io.IOException;

import ekrut.common.Subscriber;
import ekrut.gui.ServerMainSceneController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {

	private DBController dbCon;
	private ServerMainSceneController controller;

	public Server(int port, ServerMainSceneController controller) {
		super(port);
		dbCon = new DBController();
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
			} else if (msg instanceof String){
				Subscriber sub = dbCon.getSubscriber((String)msg);
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
	protected void clientDisconnected(ConnectionToClient client) {
		controller.clientDisconnected(client);
	}

}

package ekrut.server;

import java.io.IOException;

import ekrut.common.Subscriber;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {

	private DBController dbCon;

	public Server(int port) {
		super(port);
		dbCon = new DBController();
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

}

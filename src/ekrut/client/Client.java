package ekrut.client;

import java.io.IOException;
import ekrut.common.Subscriber;
import ekrut.gui.ClientMainController;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient {

	// Create instance of ClientMainController for presenting screens.
	private ClientMainController controller;

	// Open connection to server
	public Client(String host, int port, ClientMainController controller) throws IOException {
		super(host, port);
		this.controller = controller;
		controller.setClient(this);

		openConnection();
	}

	/*
	 * This abstract method is handler messages from server. 
	 * 1) In case method get Subscriber instance it will send the subscriber to displaySubscriber method.
	 * 2) In case method get "Success!" String its mean that update was success.
	 * 3) In case method get "Failed!" String its mean that update was fail.
	 * 4) Else it fail in fetching file.
	 */
	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof Subscriber) {
			Subscriber sub = (Subscriber) msg;
			controller.displaySubscriber(sub);
		} else if (msg instanceof String) {
			if (msg.equals("Success!")) {
				controller.displayUpdateSuccess();
			} else if (msg.equals("Failed!")) {
				controller.displayUpdateFailed();
			} else {
				controller.displayFetchFailed();
			}
		}
	}

	/*
	 * Send id to server.
	 */
	public boolean getSubscriber(String id) {
		try {
			sendToServer(id);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	/*
	 * Send updated subscriber to server.
	 */
	public boolean updateSubscriber(Subscriber sub) {
		try {
			sendToServer(sub);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}

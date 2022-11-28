package ekrut.client;

import java.io.IOException;
import ekrut.common.Subscriber;
import ekrut.gui.ClientMainController;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient {

	private ClientMainController controller;

	public Client(String host, int port, ClientMainController controller) throws IOException {
		super(host, port);
		this.controller = controller;
		controller.setClient(this);

		openConnection();
	}

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

	public boolean getSubscriber(String id) {
		try {
			sendToServer(id);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

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

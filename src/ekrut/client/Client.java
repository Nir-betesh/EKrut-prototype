package ekrut.client;

import java.io.IOException;
import ekrut.common.Subscriber;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient{

	public Client(String host, int port) {
		super(host, port);
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof Subscriber) {
			Subscriber sub = (Subscriber)msg;
			
			
		} else if (msg instanceof String) {
			if (msg.equals("Success!")) {
				
				
			} else if(msg.equals("Failed!")) {
				
				
			} else {
				
				
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

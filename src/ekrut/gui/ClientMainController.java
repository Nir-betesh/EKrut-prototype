package ekrut.gui;

import ekrut.client.Client;
import ekrut.common.Subscriber;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientMainController {

	@FXML
	private TextField creditCardTxt;

	@FXML
	private TextField emailAddressTxt;

	@FXML
	private Label errorLabel;

	@FXML
	private Button fetchBtn;

	@FXML
	private TextField firstNameTxt;

	@FXML
	private TextField idTxt;

	@FXML
	private TextField lastNameTxt;

	@FXML
	private TextField phoneNumberTxt;

	@FXML
	private Button resetBtn;

	@FXML
	private TextField subscriberNumberTxt;

	@FXML
	private Button updateBtn;

	private Client client;
	private Subscriber sub;

	private final static String INVALID_ID = "Entered invalid ID";
	private final static String FETCH_FAILED = "Failed to fetch subscriber from server";
	private final static String INVALID_DETAILS = "Invalid subscriber details";
	private final static String UPDATE_FAILED = "Failed to update subscriber details in server";
	private final static String INVALID_CREDIT_CARD = "Credit card can only contain digits";

	private void setInvalidId() {
		errorLabel.setText(INVALID_ID);
		errorLabel.setVisible(true);
	}

	private void setFetchFailed() {
		errorLabel.setText(FETCH_FAILED);
		errorLabel.setVisible(true);
	}

	private void setInvalidDetails() {
		errorLabel.setText(INVALID_DETAILS);
		errorLabel.setVisible(true);
	}

	private void setUpdateFailed() {
		errorLabel.setText(UPDATE_FAILED);
		errorLabel.setVisible(true);
	}
	
	private void setInvalidCreditCard() {
		errorLabel.setText(INVALID_CREDIT_CARD);
		errorLabel.setVisible(true);
	}

	@FXML
	void getSubscriber(ActionEvent event) {
		errorLabel.setVisible(false);
		String id = idTxt.getText().trim();

		if (id.isEmpty()) {
			setInvalidId();
			return;
		}

		if (!client.getSubscriber(id)) {
			setFetchFailed();
			return;
		}

		idTxt.setEditable(false);
	}

	@FXML
	void resetSubscriber(ActionEvent event) {
		reset();
	}

	// Helper method to validate credit card 
	private boolean isStrOnlyDigits(String parmeter) {
		for(int i = 0; i < parmeter.length(); i++) {
			if (!Character.isDigit(parmeter.charAt(i)))
			return false;
		}
		return true;
	}
	
	@FXML
	void updateSubscriber(ActionEvent event) {
		errorLabel.setVisible(false);
		String creditCard = creditCardTxt.getText().trim();
		String subscriberNumber = subscriberNumberTxt.getText().trim();

		if (creditCard.isEmpty()) {
			setInvalidDetails();
			return;
		}
		if (!isStrOnlyDigits(creditCard)) {
			setInvalidCreditCard();
			return;
		}
		
		
		sub.setCreditCardNumber(creditCard);
		if (subscriberNumber.isEmpty() || subscriberNumber.equals("N/A")) {
			sub.setSubscriberNumber(null);
		} else {
			try {
				int subNum = Integer.parseInt(subscriberNumber);
				sub.setSubscriberNumber(subNum);
			} catch (NumberFormatException e) {
				setInvalidDetails();
				return;
			}
		}

		if (!client.updateSubscriber(sub)) {
			setUpdateFailed();
			return;
		}
	}



	public void setClient(Client client) {
		this.client = client;
	}

	public void displaySubscriber(Subscriber sub) {
		Platform.runLater(() -> {
			this.sub = sub;

			firstNameTxt.setText(sub.getFirstName());
			lastNameTxt.setText(sub.getLastName());
			phoneNumberTxt.setText(sub.getPhoneNumber());
			emailAddressTxt.setText(sub.getEmailAddress());
			creditCardTxt.setText(sub.getCreditCardNumber());
			Integer subNum = sub.getSubscriberNumber();

			if (subNum == null)
				subscriberNumberTxt.setText("N/A");
			else
				subscriberNumberTxt.setText(subNum.toString());

			creditCardTxt.setEditable(true);
			subscriberNumberTxt.setEditable(true);
		});
	}

	public void displayUpdateSuccess() {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.INFORMATION, "Subscriber update successful!", ButtonType.OK);
			alert.show();
		});
	}

	public void displayUpdateFailed() {
		Platform.runLater(() -> {
			setUpdateFailed();
		});
	}

	private void reset() {
		firstNameTxt.setText("");
		lastNameTxt.setText("");
		phoneNumberTxt.setText("");
		emailAddressTxt.setText("");
		creditCardTxt.setText("");
		subscriberNumberTxt.setText("");
		errorLabel.setVisible(false);
		creditCardTxt.setEditable(false);
		subscriberNumberTxt.setEditable(false);
		idTxt.setEditable(true);
		this.sub = null;
	}

	public void displayFetchFailed() {
		Platform.runLater(() -> {
			reset();
			setFetchFailed();
		});
	}

}

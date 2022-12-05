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

	// ERROR messages.
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

	
	/*
	 * Get subscriber from server throw client.
	 */
	@FXML
	void getSubscriber(ActionEvent event) {
		errorLabel.setVisible(false);
		String id = idTxt.getText().trim();

		if (id.isEmpty()) {
			setInvalidId();
			return;
		}
		
		// Get subscriber from server throw client.
		if (!client.getSubscriber(id)) {
			setFetchFailed();
			return;
		}
		
		// Enable to search another subscriber.
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
	
	/*
	 * Update Subscriber credit card or subscriber number. 
	 */
	@FXML
	void updateSubscriber(ActionEvent event) {
		
		errorLabel.setVisible(false); // Hide error label
		
		String creditCard = creditCardTxt.getText().trim();
		String subscriberNumber = subscriberNumberTxt.getText().trim();
		
		// Check Validations
		if (creditCard.isEmpty()) {
			setInvalidDetails();
			return;
		}
		if (!isStrOnlyDigits(creditCard)) {
			setInvalidCreditCard();
			return;
		}
		
		// Set credit card number.
		sub.setCreditCardNumber(creditCard);
		if (subscriberNumber.isEmpty() || subscriberNumber.equals("N/A")) {
			sub.setSubscriberNumber(null);
		} else {
			try {
				// Set subscriber number.
				int subNum = Integer.parseInt(subscriberNumber);
				sub.setSubscriberNumber(subNum);
			} catch (NumberFormatException e) {
				setInvalidDetails();
				return;
			}
		}
		// Send update subscriber to client
		if (!client.updateSubscriber(sub)) {
			setUpdateFailed();
			return;
		}
	}



	public void setClient(Client client) {
		this.client = client;
	}

	/*
	 * displaySubscriber Method get subscriber and present to user all required information.
	 */
	public void displaySubscriber(Subscriber sub) {
		// Run all in GUI thread.
		Platform.runLater(() -> {
			this.sub = sub;

			firstNameTxt.setText(sub.getFirstName());
			lastNameTxt.setText(sub.getLastName());
			phoneNumberTxt.setText(sub.getPhoneNumber());
			emailAddressTxt.setText(sub.getEmailAddress());
			creditCardTxt.setText(sub.getCreditCardNumber());
			Integer subNum = sub.getSubscriberNumber();
			
			// Check if subscriber has subscriber number.
			if (subNum == null)
				subscriberNumberTxt.setText("N/A");
			else
				subscriberNumberTxt.setText(subNum.toString());
			
			// Enable editing credit card and subscriber number.
			creditCardTxt.setEditable(true);
			subscriberNumberTxt.setEditable(true);
		});
	}

	// Success to updated subscriber - pop-up alert.
	public void displayUpdateSuccess() {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.INFORMATION, "Subscriber update successful!", ButtonType.OK);
			alert.show();
		});
	}
	// Fail to updated subscriber - pop-up alert.
	public void displayUpdateFailed() {
		Platform.runLater(() -> {
			setUpdateFailed();
		});
	}

	// Clear all subscriber information except ID.
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

	// Can't fetch subscriber info message.
	public void displayFetchFailed() {
		Platform.runLater(() -> {
			reset(); 
			setFetchFailed();
		});
	}

}

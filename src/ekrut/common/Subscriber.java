package ekrut.common;

import java.io.Serializable;

public class Subscriber implements Serializable{

	private static final long serialVersionUID = -3660991795279770252L;
	private String firstName, lastName, id, phoneNumber, emailAddress, creditCardNumber;
	private Integer subscriberNumber;

	public Subscriber(String firstName, String lastName, String id, String phoneNumber, String emailAddress, String creditCardNumber, Integer subscriber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.creditCardNumber = creditCardNumber;
		this.subscriberNumber = subscriber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public Integer getSubscriberNumber() {
		return subscriberNumber;
	}

	public void setSubscriberNumber(Integer subscriber) {
		this.subscriberNumber = subscriber;
	}

}

package ekrut.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ekrut.common.Subscriber;

public class DBController {

	private Connection conn;

	public DBController() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ekrut?serverTimezone=IST", "root", "Retool7 Sturdy Tug Unwashed");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public Subscriber getSubscriber(String id) {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM subscriber WHERE id = ?;");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Subscriber(rs.getString(1), rs.getString(2),
									  rs.getString(3), rs.getString(4),
						              rs.getString(5), rs.getString(6), rs.getInt(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateSubscriber(Subscriber sub) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE subscriber SET CreditCardNumber = ?, SubscriberNumber = ? WHERE id = ?");
			ps.setString(1, sub.getCreditCardNumber());
			ps.setInt(2, sub.getSubscriberNumber());
			ps.setString(3, sub.getId());
			return 1 == ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

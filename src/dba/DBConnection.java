package dba;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.Connection;

public class DBConnection {
	public static Connection myConnection() {
		Connection connection = null; 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/quanlynhansu?useSSL=false";
			String user = "root";
			String pass = "anhthanh1997";
			connection = (Connection) DriverManager.getConnection(url, user, pass);
			
		} catch (ClassNotFoundException e) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (SQLException e) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
			
		}
		return connection;
	}
}

package opensource.tamilkeyboard.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	static Connection conn=null;
	public static Connection getConnection(){
	try {
		Class.forName("org.sqlite.JDBC");
	} catch (ClassNotFoundException e) {
		
		e.printStackTrace();
	}
	String dbURL = "jdbc:sqlite:tamil.db";
	
	try {
		conn = DriverManager.getConnection(dbURL);
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	return conn;
	}
	public static void closeConnection(){
		try {
			if(!conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}

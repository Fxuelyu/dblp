package org.me.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	// Change the parameters accordingly.
	private static String dbUrl = "jdbc:mysql://127.0.0.1:3306/dblp";
	private static String Driver = "com.mysql.jdbc.Driver";
	private static String user = "root";
	private static String password = "Since1992!";

	public static Connection getConn() {
		try {
		   //Class.forName("org.gjt.mm.mysql.Driver");
			Class.forName(Driver).newInstance();
		   //org.gjt.mm.mysql.Driver
		 return DriverManager.getConnection(dbUrl, user, password);
		} catch (Exception e) {
			System.out.println("Error while opening a conneciton to database server: "
								+ e.getMessage());
			return null;
		}
	}
}
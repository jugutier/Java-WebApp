package ar.edu.itba.grupo2.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionUtilities {
	private static ConnectionUtilities conUt;
	private static String database;
	private static String dbuser;
	private static String dbpassword;

	// @source: http://www.mkyong.com/java/java-properties-file-examples/
	private ConnectionUtilities() {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = getClass().getClassLoader().getResourceAsStream("config.properties");

			prop.load(input);

			database = prop.getProperty("database");
			dbuser = prop.getProperty("dbuser");
			dbpassword = prop.getProperty("dbpassword");

			System.out.println(database);
			System.out.println(dbuser);
			System.out.println(dbpassword);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new ConfigPropertiesException();
				}
			}
		}
	}

	public static ConnectionUtilities getInstance() {
		if (conUt == null) {
			conUt = new ConnectionUtilities();
		}
		return conUt;
	}

	public Connection getConnection() {

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {
			return null;

		}

		Connection connection = null;

		try {

			connection = DriverManager.getConnection("jdbc:postgresql://"
					+ database, dbuser, dbpassword);

		} catch (SQLException e) {
			return null;

		}

		return connection;
	}

	@Deprecated
	public void testQuery(Connection c) {
		Statement s = null;
		if (c != null) {
			try {
				s = c.createStatement();
				ResultSet rs = s.executeQuery("SELECT * FROM FILM");
				while (rs.next()) {
					System.out.println(rs.getString("NAME"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (s != null) {
					try {
						s.close();
						c.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}

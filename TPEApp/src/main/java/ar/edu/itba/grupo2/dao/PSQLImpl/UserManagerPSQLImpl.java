package ar.edu.itba.grupo2.dao.PSQLImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.grupo2.dao.UserManagerDAO;
import ar.edu.itba.grupo2.dao.exceptions.ConnectionException;
import ar.edu.itba.grupo2.dao.exceptions.UserAlreadyExistsException;
import ar.edu.itba.grupo2.dao.exceptions.UserNotFoundException;
import ar.edu.itba.grupo2.utils.ConnectionUtilities;
import ar.edu.itba.it.paw.model.User;

public final class UserManagerPSQLImpl implements UserManagerDAO {

	private static UserManagerPSQLImpl user_manager = null;
	public static final String TABLENAME = "GAJAMDBUSER";

	private UserManagerPSQLImpl() {
	}

	public static UserManagerPSQLImpl getInstance() {
		if (user_manager == null) {
			user_manager = new UserManagerPSQLImpl();
		}

		return user_manager;
	}

	@Override
	public User getUserById(int id) throws ConnectionException  {
		User newUser = null;
		try {
			Connection c = ConnectionUtilities.getInstance().getConnection();
			PreparedStatement s;
			s = c.prepareStatement("SELECT * FROM " + TABLENAME
					+ " WHERE ID= ?");
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				newUser = new User.Builder().email(rs.getString("email"))
						.name(rs.getString("name")).lastname("lastname")
						.password(rs.getString("password")).id(rs.getInt("id"))
						.birthdate(rs.getDate("date"))
						.vip(rs.getBoolean("vip")).build();
			}
		} catch (SQLException e) {
			throw new ConnectionException();
		}
		return newUser;
	}


	@Deprecated
	@Override
	public List<User> getAllUsers() {
		Connection c = ConnectionUtilities.getInstance().getConnection();
		Statement s = null;
		User newUser = null;
		List<User> users = null;
		if (c != null) {
			try {
				s = c.createStatement();
				ResultSet rs = s.executeQuery("SELECT * FROM " + TABLENAME);
				users = new ArrayList<User>(rs.getFetchSize());
				while (rs.next()) {
					newUser = new User.Builder().email(rs.getString("email"))
							.name(rs.getString("name")).lastname("lastname")
							.password(rs.getString("password"))
							.id(rs.getInt("id")).birthdate(rs.getDate("date"))
							.vip(rs.getBoolean("vip")).build();
					users.add(newUser);

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
		return users;
	}

	@Override
	public User getUserByEmail(String email) throws ConnectionException {
		User newUser = null;
		try {
			Connection c = ConnectionUtilities.getInstance().getConnection();
			PreparedStatement s;
			s = c.prepareStatement("SELECT * FROM " + TABLENAME
					+ " WHERE EMAIL= ?");
			s.setString(1, email);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				newUser = new User.Builder().email(rs.getString("email"))
						.name(rs.getString("name")).lastname("lastname")
						.password(rs.getString("password")).id(rs.getInt("id"))
						.birthdate(rs.getDate("birthdate"))
						.vip(rs.getBoolean("vip")).build();
			}
		} catch (SQLException e) {
			throw new ConnectionException();
		}
		return newUser;
	}

	@Override
	public User saveUser(User user) throws ConnectionException {
		try {
			Connection c = ConnectionUtilities.getInstance().getConnection();
			PreparedStatement s;
			if (!user.isNew()) {
				s= c.prepareStatement("UPDATE "+ TABLENAME + "SET password = ? WHERE email= ?");
				s.setString(1, user.getPassword());
				s.setString(2, user.getEmail());
				s.executeUpdate();
			}
			else{
				s= c.prepareStatement("INSERT INTO "+ TABLENAME +"(email,password,name,lastname,birthdate,secretquestion,secretanswer,vip) VALUES(?,?,?,?,?,?,?,?) returning id");
				s.setString(1, user.getEmail());
				s.setString(2, user.getPassword());
				s.setString(3, user.getName());
				s.setString(4, user.getLastname());
				s.setDate(5, (new Date(user.getBirthdate().getTime())));
				s.setString(6, user.getSecretQuestion());
				s.setString(7, user.getSecretAnswer());
				s.setBoolean(8, false);
				s.executeUpdate();
				ResultSet rs = s.getResultSet();
				if (rs.next()) {
					user.setId(rs.getInt(1));
				}

			}
			c.close();
		} catch (SQLException e) {
			throw new ConnectionException();
		}
		return user;
	}
}

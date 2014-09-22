package ar.edu.itba.grupo2.dao.PSQLImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.grupo2.dao.UserManagerDAO;
import ar.edu.itba.grupo2.dao.exceptions.ConnectionException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.utils.ConnectionUtilities;

public final class UserManagerPSQLImpl implements UserManagerDAO {

	private static UserManagerPSQLImpl user_manager = null;
	private static final String USER_TABLENAME = "GAJAMDBUSER";
	private static final String COMMENT_TABLENAME = "COMMENT";

	private UserManagerPSQLImpl() {
	}

	public synchronized static UserManagerPSQLImpl getInstance() {
		if (user_manager == null) {
			user_manager = new UserManagerPSQLImpl();
		}

		return user_manager;
	}

	@Override
	public User getUserById(int id){
		User newUser = null;
		try {
			Connection c = ConnectionUtilities.getInstance().getConnection();
			PreparedStatement s;
			s = c.prepareStatement("SELECT * FROM " + USER_TABLENAME
					+ " WHERE ID = ?");
			s.setInt(1, id);
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
	public User getUserByEmail(String email){
		User newUser = null;
		try {
			Connection c = ConnectionUtilities.getInstance().getConnection();
			PreparedStatement s;
			s = c.prepareStatement("SELECT * FROM " + USER_TABLENAME
					+ " WHERE EMAIL= ?");
			s.setString(1, email);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				newUser = new User.Builder().email(rs.getString("email"))
						.name(rs.getString("name")).lastname("lastname")
						.password(rs.getString("password")).id(rs.getInt("id"))
						.birthdate(rs.getDate("birthdate"))
						.vip(rs.getBoolean("vip"))
						.secretQuestion(rs.getString("secretquestion"))
						.secretAnswer(rs.getString("secretanswer"))
						.build();
			}
		} catch (SQLException e) {
			throw new ConnectionException();
		}
		return newUser;
	}

	@Override
	public User saveUser(User user){
		try {
			Connection c = ConnectionUtilities.getInstance().getConnection();
			PreparedStatement s;
			if (!user.isNew()) {
				s = c.prepareStatement("UPDATE " + USER_TABLENAME
						+ " SET password = ? WHERE email= ?");
				s.setString(1, user.getPassword());
				s.setString(2, user.getEmail());
				s.execute();
			} else {
				s = c.prepareStatement("INSERT INTO "
						+ USER_TABLENAME
						+ "(email,password,name,lastname,birthdate,secretquestion,secretanswer,vip) VALUES(?,?,?,?,?,?,?,?) returning id");
				s.setString(1, user.getEmail());
				s.setString(2, user.getPassword());
				s.setString(3, user.getName());
				s.setString(4, user.getLastname());
				s.setDate(5, (new Date(user.getBirthdate().getTime())));
				s.setString(6, user.getSecretQuestion());
				s.setString(7, user.getSecretAnswer());
				s.setBoolean(8, false);
				s.execute();
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

	@Override
	public List<Comment> getCommentsByUser(User user){
		Connection c = ConnectionUtilities.getInstance().getConnection();
		PreparedStatement s = null;
		List<Comment> ret = null;
		if (c != null) {
			try {
				s = c.prepareStatement("SELECT * FROM " + COMMENT_TABLENAME
						+ " WHERE USER_ID = ?");
				s.setInt(1, user.getId());
				ResultSet rs = s.executeQuery();
				ret = new ArrayList<Comment>();
				while (rs.next()) {
					Comment comment = new Comment.Builder()
							.id(rs.getInt("ID"))
							.film(FilmManagerPSQLImpl.getInstance()
									.getFilmById(rs.getInt("FILM_ID")))
							.user(getUserById(rs.getInt("USER_ID")))
							.creationDate(
									new Date(rs.getDate("CREATIONDATE")
											.getTime()))
							.text(rs.getString("TEXT")).rate(rs.getInt("RATE"))
							.build();

					ret.add(comment);
				}
			} catch (SQLException e) {
				throw new ConnectionException();
			} finally {
				if (s != null) {
					try {
						s.close();
						c.close();
					} catch (SQLException e) {
						throw new ConnectionException();
					}
				}
			}
		}
		return ret;
	}
}

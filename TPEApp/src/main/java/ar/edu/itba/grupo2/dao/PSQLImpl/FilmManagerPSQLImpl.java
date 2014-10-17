package ar.edu.itba.grupo2.dao.PSQLImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.dao.exceptions.ConnectionException;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmManagerRepo;
import ar.edu.itba.grupo2.utils.ConnectionUtilities;

public class FilmManagerPSQLImpl implements FilmManagerRepo {
	private static final String FILM_TABLENAME = "FILM";
	private static final String COMMENT_TABLENAME = "COMMENT";

	private static FilmManagerPSQLImpl instance = null;

	private FilmManagerPSQLImpl() {

	}

	public synchronized static FilmManagerPSQLImpl getInstance() {
		if (instance == null) {
			instance = new FilmManagerPSQLImpl();
		}

		return instance;
	}

	@Override
	public Film get(int id) {
		Connection c = ConnectionUtilities.getInstance().getConnection();
		PreparedStatement s = null;
		Film ret = null;
		if (c != null) {
			try {
				s = c.prepareStatement("SELECT * FROM " + FILM_TABLENAME
						+ " WHERE ID = ?");
				s.setInt(1, id);
				ResultSet rs = s.executeQuery();
				if (rs.next()) {
					ret = new Film.Builder().id(id).name(rs.getString("NAME"))
							.director(rs.getString("DIRECTOR"))
							.creationDate(rs.getDate("CREATIONDATE"))
							.releaseDate(rs.getDate("RELEASEDATE"))
							.genre(rs.getString("GENRE"))
							.description(rs.getString("DESCRIPTION"))
							.length(rs.getInt("LENGTH"))
							.totalComments(rs.getInt("TOTALCOMMENTS"))
							.sumComments(rs.getInt("SUMCOMMENTS"))

							.build();

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

	@Override
	public List<Film> getAllFilms() {
		Connection c = ConnectionUtilities.getInstance().getConnection();
		Statement s = null;
		List<Film> ret = null;
		if (c != null) {
			try {
				s = c.createStatement();
				ResultSet rs = s
						.executeQuery("SELECT * FROM " + FILM_TABLENAME);
				ret = new ArrayList<Film>(rs.getFetchSize());
				while (rs.next()) {
					Film f = new Film.Builder()
							.id(rs.getInt("ID"))
							.name(rs.getString("NAME"))
							.director(rs.getString("DIRECTOR"))
							.creationDate(
									new Date(rs.getDate("CREATIONDATE")
											.getTime()))
							.releaseDate(
									new Date(rs.getDate("RELEASEDATE")
											.getTime()))
							.genre(rs.getString("GENRE"))
							.description(rs.getString("DESCRIPTION"))
							.length(rs.getInt("LENGTH"))
							.totalComments(rs.getInt("TOTALCOMMENTS"))
							.sumComments(rs.getInt("SUMCOMMENTS"))

							.build();

					ret.add(f);
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

	@Override
	public Film save(Film film) {
		if (film == null) {
			throw new IllegalArgumentException();
		}
		Connection c = ConnectionUtilities.getInstance().getConnection();
		PreparedStatement s = null;
		if (c != null) {
			try {
				if (!film.isNew()) {
					s = c.prepareStatement("UPDATE "
							+ FILM_TABLENAME
							+ " SET (name,director,creationdate,releasedate,genre,description,length,sumcomments,totalcomments) = ( ? ,?, ?, ?, ?, ?, ?, ?, ?) WHERE ID = ?");
					s.setString(1, film.getName());
					s.setString(2, film.getDirector());
					s.setDate(3, new java.sql.Date(film.getCreationDate()
							.getTime()));
					s.setDate(4, new java.sql.Date(film.getReleaseDate()
							.getTime()));
					s.setString(5, film.getGenre().toString());
					s.setString(6, film.getDescription());
					s.setInt(7, film.getLength());
					s.setInt(8, film.getSumComments());
					s.setInt(9, film.getTotalComments());

					s.setInt(10, film.getId());
					s.executeUpdate();
				} else {
					// INSERT
					s = c.prepareStatement("INSERT INTO "
							+ FILM_TABLENAME
							+ " (name,director,creationdate,releasedate,genre,description,length,sumcomments,totalcomments) VALUES ( ? ,?, ?, ?, ?, ?, ?, ?, ?) returning id");
					s.setString(1, film.getName());
					s.setString(2, film.getDirector());
					s.setDate(3, new java.sql.Date(film.getCreationDate()
							.getTime()));
					s.setDate(4, new java.sql.Date(film.getReleaseDate()
							.getTime()));
					s.setString(5, film.getGenre().toString());
					s.setString(6, film.getDescription());
					s.setInt(7, film.getLength());
					s.setInt(8, film.getSumComments());
					s.setInt(9, film.getTotalComments());

					s.execute();
					ResultSet rs = s.getResultSet();
					if (rs.next()) {
						int newId = rs.getInt(1);
						film.setId(newId);
					}
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
		return film;

	}

	@Override
	public List<Comment> getCommentsForFilm(Film film) {
		if (film == null) {
			throw new IllegalArgumentException();
		}
		Connection c = ConnectionUtilities.getInstance().getConnection();
		PreparedStatement s = null;
		List<Comment> ret = null;
		if (c != null) {
			try {
				s = c.prepareStatement("SELECT * FROM " + COMMENT_TABLENAME
						+ " WHERE FILM_ID = ?");
				s.setInt(1, film.getId());
				ResultSet rs = s.executeQuery();
				ret = new ArrayList<Comment>();
				while (rs.next()) {
					Comment comment = new Comment.Builder()
							.id(rs.getInt("ID"))
							.film(get(rs.getInt("FILM_ID")))
							.user(UserManagerPSQLImpl.getInstance()
									.get(rs.getInt("USER_ID")))
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

	/**
	 * creationDate is set to 'now' by the database
	 */
	@Override
	public Comment saveComment(Comment comment) {
		if (comment == null) {
			throw new IllegalArgumentException();
		}
		Connection c = ConnectionUtilities.getInstance().getConnection();
		PreparedStatement s = null;
		if (c != null) {
			try {
				s = c.prepareStatement("INSERT INTO "
						+ COMMENT_TABLENAME
						+ " (film_id,user_id,text,rate) VALUES (?, ?  ,? ,?) returning id");
				s.setInt(1, comment.getFilm().getId());
				s.setInt(2, comment.getUser().getId());
				s.setString(3, comment.getText());
				s.setInt(4, comment.getRate());
				s.execute();
				ResultSet rs = s.getResultSet();
				if (rs.next()) {
					comment.setId(rs.getInt(1));
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
		return comment;
	}
}

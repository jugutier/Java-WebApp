package ar.edu.itba.grupo2.dao.PSQLImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.itba.grupo2.dao.FilmManagerDAO;
import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.utils.ConnectionUtilities;

public class FilmManagerPSQLImpl implements FilmManagerDAO {
	private static final String TABLENAME = "FILM";

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
	public Film getFilmById(int id) throws FilmNotFoundException {
		Connection c = ConnectionUtilities.getInstance().getConnection();
		PreparedStatement s = null;
		Film ret = null;
		if (c != null) {
			try {
				s = c.prepareStatement("SELECT * FROM " + TABLENAME
						+ " WHERE ID = ?");
				s.setInt(1, id);
				ResultSet rs = s.executeQuery();
				if (rs.next()) {
					ret = new Film.Builder().id(id).name(rs.getString("NAME"))
							.director(rs.getString("DIRECTOR"))
							.releaseDate(rs.getDate("RELEASEDATE"))
							.genre(rs.getString("GENRE"))
							.description(rs.getString("DESCRIPTION"))
							.length(rs.getInt("LENGTH"))
							.totalComments(rs.getInt("TOTALCOMMENTS"))
							.sumComments(rs.getInt("SUMCOMMENTS"))

							.build();

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
				ResultSet rs = s.executeQuery("SELECT * FROM " + TABLENAME);
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
		return ret;
	}

	@Override
	public Film saveFilm(Film film) {
		Connection c = ConnectionUtilities.getInstance().getConnection();
		PreparedStatement s = null;
		if (c != null) {
			try {
				if (!film.isNew()) {
					s = c.prepareStatement("UPDATE "
							+ TABLENAME
							+ "SET (name,director,creationdate,releasedate,genre,description,length,sumcomments,totalcomments) = ( ? ,?, ?, ?, ?, ?, ?, ?, ?) WHERE ID = ?");
					s.setString(1, film.getName());
					s.setString(2, film.getDirector());
					s.setDate(3, new java.sql.Date(film.getCreationDate()
							.getTime()));
					s.setDate(4, new java.sql.Date(film.getReleaseDate()
							.getTime()));
					s.setString(5, film.getGenre());
					s.setString(6, film.getDescription());
					s.setInt(7, film.getLength());
					s.setInt(8, film.getSumComments());
					s.setInt(9, film.getTotalComments());

					s.setInt(10, film.getId());
					s.executeUpdate();
				} else {
					// INSERT
					s = c.prepareStatement("INSERT INTO "
							+ TABLENAME
							+ " (name,director,creationdate,releasedate,genre,description,length,sumcomments,totalcomments) VALUES ( ? ,?, ?, ?, ?, ?, ?, ?, ?) returning id");
					s.setString(1, film.getName());
					s.setString(2, film.getDirector());
					s.setDate(3, new java.sql.Date(film.getCreationDate()
							.getTime()));
					s.setDate(4, new java.sql.Date(film.getReleaseDate()
							.getTime()));
					s.setString(5, film.getGenre());
					s.setString(6, film.getDescription());
					s.setInt(7, film.getLength());
					s.setInt(8, film.getSumComments());
					s.setInt(9, film.getTotalComments());

					s.execute();
					ResultSet rs = s.getResultSet();
					if (rs.next()) {
						int newId = rs.getInt(1);
						System.out.println("ID:" + newId);
						film.setId(newId);
					}
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
		return film;

	}

	@Deprecated
	@Override
	public void deleteFilm(Film film) {
		Connection c = ConnectionUtilities.getInstance().getConnection();
		PreparedStatement s = null;
		if (c != null) {
			try {
				s = c.prepareStatement("DELETE FROM " + TABLENAME
						+ " WHERE ID = ?");
				s.setInt(1, film.getId());
				s.executeQuery();

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

	@Override
	public List<Comment> getCommentsForFilm(Film film) {
		Connection c = ConnectionUtilities.getInstance().getConnection();
		Statement s = null;
		List<Comment> ret = null;
		if (c != null) {
			try {
				s = c.createStatement();
				ResultSet rs = s.executeQuery("SELECT * COMMENT " + TABLENAME);
				ret = new ArrayList<Comment>(rs.getFetchSize());
				while (rs.next()) {
					Comment comment = new Comment.Builder()
							.id(rs.getInt("ID"))
							.film(getFilmById(rs.getInt("FILM_ID")))
							.user(UserManagerPSQLImpl.getInstance()
									.getUserById(rs.getInt("USER_ID")))
							// TODO: verify
							.creationDate(
									new Date(rs.getDate("CREATIONDATE")
											.getTime()))
							.text(rs.getString("TEXT")).rate(rs.getInt("RATE"))

							.build();

					ret.add(comment);
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
		return ret;
	}

	@Override
	public void addCommentToFilm(Film film, Comment comment) {
		// TODO Auto-generated method stub

	}

}

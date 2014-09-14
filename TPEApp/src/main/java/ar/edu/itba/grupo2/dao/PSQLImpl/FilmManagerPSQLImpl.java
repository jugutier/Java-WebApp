package ar.edu.itba.grupo2.dao.PSQLImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.grupo2.dao.FilmManagerDAO;
import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.utils.ConnectionUtilities;

public class FilmManagerPSQLImpl implements FilmManagerDAO {
	private static final String TABLENAME = "FILM";

	@Override
	public Film getFilmById(int id) throws FilmNotFoundException {

		return null;
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
					Film f = new Film.Builder().id(rs.getInt("ID"))
							.name(rs.getString("NAME"))
							.director(rs.getString("DIRECTOR"))
							.releaseDate(rs.getDate("RELEASEDATE"))
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
	public void saveFilm(Film film) {
		/*Connection c = ConnectionUtilities.getInstance().getConnection();
		PreparedStatement s = null;
		if (c != null) {
			try {
				s = c.createStatement();
				ResultSet rs = s.executeQuery("SELECT * FROM " + TABLENAME + "WHERE ID = ?");
				
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
		stmt.execute("insert into foo (some_value) values ('foo') returning id");
		rs = stmt.getResultSet();
		if (rs.next())
		{
		   int newId = rs.getInt(1);
		}
*/
	}

	@Override
	public void deleteFilm(Film film) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Comment> getCommentsForFilm(Film film) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCommentToFilm(Film film, Comment comment) {
		// TODO Auto-generated method stub

	}

}

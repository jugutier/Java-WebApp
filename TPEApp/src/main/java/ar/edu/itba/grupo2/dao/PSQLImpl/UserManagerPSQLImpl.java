package ar.edu.itba.grupo2.dao.PSQLImpl;

import java.util.List;

import ar.edu.itba.grupo2.dao.UserManagerDAO;
import ar.edu.itba.it.paw.model.User;

public final class UserManagerPSQLImpl implements UserManagerDAO {
	
	private static UserManagerPSQLImpl user_manager = null;
	
	private UserManagerPSQLImpl(){
	}
	
	public static UserManagerPSQLImpl getInstance(){
		if (user_manager == null){
			user_manager = new UserManagerPSQLImpl();
		}
		
		return user_manager;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByEmail(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean authenticateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}

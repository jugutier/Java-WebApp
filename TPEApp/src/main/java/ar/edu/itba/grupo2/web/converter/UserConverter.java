package ar.edu.itba.grupo2.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;

@Component
public class UserConverter implements Converter<String,User> {
	
	private final UserRepo userRepo;
	
	@Autowired
	public UserConverter(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public User convert(String id) {
		return userRepo.get(Integer.valueOf(id));
	}

}

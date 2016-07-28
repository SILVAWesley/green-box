package org.ufcg.si.models.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ufcg.si.models.User;

/**
 * This is a façade that is used in out Controller. It also contains the User Repository
 */
@Service
public class UserServiceImpl implements UserService{
	
	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository repository){
		this.userRepository = repository;
	}
	
	@Override
	public Iterable<User> listAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void save(User pessoa) {
		userRepository.save(pessoa);
		
	}

}

package org.ufcg.si.models.repositories;

import org.ufcg.si.models.User;

// Façade
public interface UserService {
	public Iterable<User> listAllUsers();
	public void save(User pessoa);
}

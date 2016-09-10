package org.ufcg.si.repositories;

import org.ufcg.si.models.User;

// Façade
public interface UserService {
	public Iterable<User> findAll();
	public User findByEmail(String email);
	public User findById(Long id);
	public User findByUsername(String name);
	public User save(User pessoa);
	public User delete(Long id);
	public User update(User user);
}

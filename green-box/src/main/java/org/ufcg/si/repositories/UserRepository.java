package org.ufcg.si.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.ufcg.si.models.User;


public interface UserRepository extends CrudRepository<User, Long>{
	
	@Query(value = "Select user from User user where user.username=:username")
	public User findByUsername(@Param("username") String username);
	
}

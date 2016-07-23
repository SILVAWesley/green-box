package org.ufcg.si.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.ufcg.si.models.User;


public interface UserRepository extends CrudRepository<User, Long>{

}

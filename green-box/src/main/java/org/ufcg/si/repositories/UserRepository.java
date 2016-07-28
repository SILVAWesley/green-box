package org.ufcg.si.repositories;

import org.springframework.data.repository.CrudRepository;
import org.ufcg.si.models.User;

public interface UserRepository extends CrudRepository<User, Long>{

}

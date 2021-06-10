package com.testplus.testplus.repo;

import com.testplus.testplus.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByLogin(String login);
}

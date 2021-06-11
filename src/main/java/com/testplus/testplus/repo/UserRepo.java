package com.testplus.testplus.repo;

import com.testplus.testplus.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}

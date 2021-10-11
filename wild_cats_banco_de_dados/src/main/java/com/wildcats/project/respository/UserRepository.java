package com.wildcats.project.respository;

import com.wildcats.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByLoginAndPassword(String login, String password);

}

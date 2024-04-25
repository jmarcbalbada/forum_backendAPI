package com.example.forum_backendapi.repository;
import com.example.forum_backendapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // login
    User findByFirstNameAndLastName(String firstName, String lastName);

    // find by first name
    Optional<User> findByFirstName(String firstName);

    // find by last name
    Optional<User> findByLastName(String lastName);
}

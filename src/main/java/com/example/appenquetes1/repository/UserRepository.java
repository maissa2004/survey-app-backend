// com.example.appenquetes1.repository.UserRepository.java
package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);

    @Query("SELECT u FROM User u WHERE u.username = :login OR u.email = :login OR u.phone = :login")
    Optional<User> findByUsernameEmailOrPhone(@Param("login") String login);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
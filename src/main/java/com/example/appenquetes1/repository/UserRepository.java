package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
    // ✅ MÉTHODE se trouve dans sessionservice
    List<User> findByRole(User.Role role);

    @Query("SELECT u FROM User u WHERE u.role = :role AND u.id NOT IN :excludedIds")
    List<User> findByRoleAndIdNotIn(@Param("role") User.Role role, @Param("excludedIds") List<Integer> excludedIds);

    @Query("SELECT u FROM User u WHERE u.username = :login OR u.email = :login OR u.phone = :login")
    Optional<User> findByUsernameEmailOrPhone(@Param("login") String login);
}
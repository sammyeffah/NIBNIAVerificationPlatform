package com.nib.gh.nia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nib.gh.nia.model.Users;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndEnabled(String username, boolean enabled);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
           "FROM Users u JOIN u.roles r " +
           "WHERE u.username = :username AND r.name = 'ADMIN'")
    boolean hasAdminRole(@Param("username") String username);

    long count();

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.enabled = :enabled WHERE u.id = :userId")
    void updateDataById(@Param("userId") Long userId, @Param("enabled") boolean enabled);
}

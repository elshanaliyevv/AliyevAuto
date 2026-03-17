package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByNumber(String number);

    Optional<User> findByUsernameOrEmailOrNumber(String username, String email, String number);

    boolean existsByUsername(String username);

    @Transactional
    void deleteUserById(Long id);

    boolean existsByEmail(String email);

    boolean existsByNumber(String number);

    @Transactional
    boolean updateUsernameById(Long id, String Username);
}

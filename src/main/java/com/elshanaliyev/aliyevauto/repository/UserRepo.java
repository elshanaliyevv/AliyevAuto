package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Long, User> {
    Optional<User> findAllByid(Long id);
}

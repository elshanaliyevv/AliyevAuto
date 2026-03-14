package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.model.entity.LastServiceTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LastServiceTimeRepo extends JpaRepository<LastServiceTime, Long> {
    Optional<LastServiceTime> findAllById(Long id);
}

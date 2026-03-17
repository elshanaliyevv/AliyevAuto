package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.model.entity.LastServiceTimes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LastServiceTimesRepo extends JpaRepository<LastServiceTimes, Long> {
    Optional<LastServiceTimes> findAllById(Long id);
}


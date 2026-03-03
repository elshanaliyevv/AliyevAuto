package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.entity.ServiceParts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicePartsRepo extends JpaRepository<Long, ServiceParts> {
    Optional<ServiceParts> findAllById(Long id);
}

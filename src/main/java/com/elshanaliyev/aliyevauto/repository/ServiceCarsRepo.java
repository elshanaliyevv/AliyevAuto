package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.entity.ServiceCars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceCarsRepo extends JpaRepository<Long, ServiceCars> {
    Optional<ServiceCars> findAllById(Long id);
}

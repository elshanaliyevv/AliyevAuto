package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.model.entity.ServiceCars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceCarsRepo extends JpaRepository<ServiceCars, Long> {

    List<ServiceCars> findByUser_Id(Long userId);
}

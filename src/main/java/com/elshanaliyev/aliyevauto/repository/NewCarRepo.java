package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.model.entity.NewCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewCarRepo extends JpaRepository<NewCar, Long> {

    List<NewCar> findByBrand_Name(String brandName);

    Optional<NewCar> findFirstByBrand_NameAndCountGreaterThanOrderByIdAsc(String brandName, Integer count);
}

package com.elshanaliyev.aliyevauto.repository;
import com.elshanaliyev.aliyevauto.model.entity.NewCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewCarRepo extends JpaRepository<NewCar, Long> {
    Optional<NewCar> findAllById (Long id);
}

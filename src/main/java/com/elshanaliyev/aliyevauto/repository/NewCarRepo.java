package com.elshanaliyev.aliyevauto.repository;
import com.elshanaliyev.aliyevauto.entity.NewCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewCarRepo extends JpaRepository<Long, NewCar> {
    Optional<NewCar> findAllById (Long id);
}

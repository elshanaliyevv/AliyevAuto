package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.model.entity.Brand;
import com.elshanaliyev.aliyevauto.model.entity.Color;
import com.elshanaliyev.aliyevauto.model.entity.Engine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ColorRepo extends JpaRepository<Color, Long> {
    Optional<Color> findByColorCode(String colorCode);

    @Transactional
    void deleteColorById(Long id);

    @Transactional
    void deleteByColorCode(String colorCode);

    List<Color> findAll();
}

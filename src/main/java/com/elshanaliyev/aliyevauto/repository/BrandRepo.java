package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.model.entity.Brand;
import com.elshanaliyev.aliyevauto.model.entity.Color;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepo extends JpaRepository<Brand,Long> {
    Optional<Brand> findByName(String name);
    @Transactional
    Optional<Brand> deleteBrandById(Long id);

    List<Brand> findAll();

}

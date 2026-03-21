package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.model.entity.Engine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EngineRepo extends JpaRepository<Engine,Long> {
    Optional<Engine> findByEngineCode(String engineCode);

    @Transactional
    void deleteEngineById(Long id);

    @Transactional
    void deleteByEngineCode(String engineCode);

    List<Engine> findAll();
}

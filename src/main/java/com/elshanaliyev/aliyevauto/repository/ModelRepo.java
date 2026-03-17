package com.elshanaliyev.aliyevauto.repository;

import com.elshanaliyev.aliyevauto.model.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepo extends JpaRepository<Model,Long> {
}

package com.elshanaliyev.aliyevauto.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    @Column(name = "engine_code")
    String engineCode;
    @OneToMany(mappedBy = "engine")
    @Column(name = "new_cars")
    List<NewCar> cars;
    @OneToMany(mappedBy = "engine")
    @Column(name = "service_cars")
    List<ServiceCars> serviceCars;
}

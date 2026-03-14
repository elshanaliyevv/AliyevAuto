package com.elshanaliyev.aliyevauto.model.entity;

import com.elshanaliyev.aliyevauto.Enums.CarEnums.Models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    @Enumerated(EnumType.STRING)
    Models name;
    @OneToMany(mappedBy = "model")
    @Column(name = "new_cars")
    List<NewCar> cars;
    @OneToMany(mappedBy = "model")
    @Column(name = "service_cars")
    List<ServiceCars> serviceCars;

}

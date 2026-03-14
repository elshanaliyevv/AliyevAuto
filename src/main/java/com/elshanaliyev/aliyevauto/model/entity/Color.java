package com.elshanaliyev.aliyevauto.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    @Column(name = "color_code")
    String colorCode;
    @OneToMany(mappedBy = "color")
    @Column(name = "new_cars")
    List<NewCar> cars;
    @OneToMany(mappedBy = "color")
    @Column(name = "service_cars")
    List<ServiceCars> serviceCars;

}

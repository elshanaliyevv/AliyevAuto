package com.elshanaliyev.aliyevauto.entity;

import com.elshanaliyev.aliyevauto.Enums.CarEnums.BmwEngines;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "service_cars")
public class ServiceCars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    String name;
    @NotBlank
    BmwEngines engines;
    @NotNull
    Integer kilometers;
    @Column(name = "product_year")
    LocalDateTime productYear;
    @NotNull
    @Column(name = "purchased_year")
    LocalDateTime purchasedYear;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne(mappedBy = "serviceCars")
    ServiceParts serviceParts;
}

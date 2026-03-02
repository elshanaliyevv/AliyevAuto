package com.elshanaliyev.aliyevauto.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "service_parts")
public class ServiceParts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer lastOilChangeKilometr;
    Integer lastAirFilterChangeKilometr;
    Integer lastOilFilterChangeKilometr;
    @Column(name = "service_fee")
    BigDecimal serviceFee;
    @Column(name = "total_amount")// Xidmət haqqı
    BigDecimal totalAmount;
    @OneToOne
    @JoinColumn(name = "car_id")
    ServiceCars serviceCars;
}

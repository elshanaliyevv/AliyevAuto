package com.elshanaliyev.aliyevauto.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "service_parts")
public class LastServiceTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "last_filter_oil_change_filter")
    Integer lastOilChangeKilometr;
    @Column(name = "last_air_filter_change_kilometr")
    Integer lastAirFilterChangeKilometr;
    @Column(name = "last_oil_filter_change_kilometr")
    Integer lastOilFilterChangeKilometr;
    @Column(name = "service_fee")
    BigDecimal serviceFee;
    @Column(name = "total_amount")// Xidmət haqqı
    BigDecimal totalAmount;
    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updated_at;
    @OneToOne
    @JoinColumn(name = "car_id")
    ServiceCars serviceCars;
}

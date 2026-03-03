package com.elshanaliyev.aliyevauto.entity;

import com.elshanaliyev.aliyevauto.Enums.CarEnums.BmwEngines;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Enumerated(EnumType.STRING)
    @NotBlank
    BmwEngines engines;
    @NotNull
    Integer kilometers;
    @Column(name = "product_year")
    LocalDateTime productYear;
    @NotNull
    @Column(name = "purchased_year")
    LocalDateTime purchasedYear;
    @CreationTimestamp
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updated_at;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne(mappedBy = "serviceCars")
    LastServiceTime lastServiceTime;
}

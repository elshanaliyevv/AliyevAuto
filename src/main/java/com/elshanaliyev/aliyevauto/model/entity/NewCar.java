package com.elshanaliyev.aliyevauto.model.entity;

import com.elshanaliyev.aliyevauto.Enums.CarEnums.Trim;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "new_cars")
public class NewCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    String Factory;
    @Enumerated(EnumType.STRING)
    @NotNull
    Trim trim;
    @NotNull
    @Column(name = "product_year")
    LocalDateTime productYear;
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    Color color;
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_id")
    Engine engine;
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    Brand brand;
    @NotNull
    BigDecimal price;
}

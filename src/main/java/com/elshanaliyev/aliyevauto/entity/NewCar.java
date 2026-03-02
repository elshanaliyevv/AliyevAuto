package com.elshanaliyev.aliyevauto.entity;

import com.elshanaliyev.aliyevauto.Enums.CarEnums.BmwEngines;
import com.elshanaliyev.aliyevauto.Enums.CarEnums.Color;
import com.elshanaliyev.aliyevauto.Enums.CarEnums.Model;
import com.elshanaliyev.aliyevauto.Enums.CarEnums.Trim;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

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
    @NotBlank
    Model model;
    @NotBlank
    BmwEngines engine;
    @NotNull
    Trim trim;
    @NotNull
    Color color;
    @NotNull
    @Column(name = "product_year")
    LocalDateTime productYear;
    @NotNull
    Integer price;
}

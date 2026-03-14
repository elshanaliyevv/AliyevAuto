package com.elshanaliyev.aliyevauto.model.response;


import com.elshanaliyev.aliyevauto.Enums.CarEnums.Trim;
import com.elshanaliyev.aliyevauto.model.entity.Model;
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
public class NewCarResponse {
    @NotBlank
    String Factory;
    @NotBlank
    String model;
    @NotBlank
    String engine;
    @NotNull
    Trim trim;
    @NotNull
    String color;
    @NotNull
    @Column(name = "product_year")
    LocalDateTime productYear;
    @NotNull
    BigDecimal price;
}

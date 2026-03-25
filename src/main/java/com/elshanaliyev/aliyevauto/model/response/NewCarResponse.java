package com.elshanaliyev.aliyevauto.model.response;


import com.elshanaliyev.aliyevauto.Enums.CarEnums.Trim;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NewCarResponse {
    Long id;
    @NotBlank
    String brand;
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
    Integer count;
}

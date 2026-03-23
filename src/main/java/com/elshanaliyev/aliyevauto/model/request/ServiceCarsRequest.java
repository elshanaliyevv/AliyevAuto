package com.elshanaliyev.aliyevauto.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ServiceCarsRequest {
    @NotBlank
    String brand;
    @NotNull
    String engine;
    @NotBlank
    String color;
    @NotNull
    Integer kilometers;
    @NotNull
    LocalDateTime productYear;
    /** İstəyə bağlı; hazırda entity-də ayrıca sahə yoxdur. */
    LocalDateTime purchasedYear;
}

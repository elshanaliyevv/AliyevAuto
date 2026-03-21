package com.elshanaliyev.aliyevauto.model.request;

import com.elshanaliyev.aliyevauto.Enums.CarEnums.Trim;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class NewCarsRequest {
    @NotBlank(message = "Zavod (Factory) qeyd olunmalıdır")
    String factory;

    @NotNull(message = "Brand seçilməlidir")
    String brand;

    @NotNull(message = "Mühərrik tipi seçilməlidir")
    String engine;

    @NotNull(message = "Trim (Komplektasiya) seçilməlidir")
    Trim trim;

    @NotNull(message = "Rəng seçilməlidir")
    String color;

    @NotNull(message = "İstehsal ili mütləqdir")
    LocalDateTime productYear;

    @NotNull(message = "Qiymət qeyd olunmalıdır")
    BigDecimal price;
}

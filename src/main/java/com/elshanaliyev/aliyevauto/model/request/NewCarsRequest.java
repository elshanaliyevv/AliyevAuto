package com.elshanaliyev.aliyevauto.model.request;

import com.elshanaliyev.aliyevauto.Enums.CarEnums.Trim;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCarsRequest {

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

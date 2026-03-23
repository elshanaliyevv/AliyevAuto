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
public class NewCarUpdateRequest {

    @NotNull
    String brand;

    @NotNull
    String engine;

    @NotNull
    Trim trim;

    @NotNull
    String color;

    @NotNull
    LocalDateTime productYear;

    @NotNull
    BigDecimal price;

    @NotNull
    Integer count;
}

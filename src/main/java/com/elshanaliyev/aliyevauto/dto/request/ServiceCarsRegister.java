package com.elshanaliyev.aliyevauto.dto.request;

import com.elshanaliyev.aliyevauto.Enums.CarEnums.BmwEngines;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ServiceCarsRegister {
    @NotBlank
    String name;
    @NotNull
    BmwEngines engines;
    @NotNull
    Integer kilometers;
    @NotNull
    LocalDateTime productYear;
    @NotNull
    LocalDateTime purchasedYear;
    @NotNull
    Long userId;
}

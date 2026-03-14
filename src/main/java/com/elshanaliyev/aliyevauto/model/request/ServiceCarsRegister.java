package com.elshanaliyev.aliyevauto.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ServiceCarsRegister {
    @NotBlank
    String name;
    @NotNull
    String engine;
    @NotNull
    Integer kilometers;
    @NotNull
    LocalDateTime productYear;
    @NotNull
    LocalDateTime purchasedYear;
    @NotNull
    Long userId;
}

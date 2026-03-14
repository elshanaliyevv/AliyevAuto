package com.elshanaliyev.aliyevauto.model.response;

import com.elshanaliyev.aliyevauto.model.entity.Engine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ServiceCarsResponse {
    @NotBlank
    String name;
    @NotBlank
    String engine;
    @NotNull
    Integer kilometers;
    LocalDateTime productYear;
    @NotNull
    LocalDateTime purchasedYear;
    @NotNull
    Long user_id;

}

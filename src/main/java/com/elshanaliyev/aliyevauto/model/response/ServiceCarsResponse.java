package com.elshanaliyev.aliyevauto.model.response;

import com.elshanaliyev.aliyevauto.model.entity.Engine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCarsResponse {
    @NotBlank
    String model;
    @NotBlank
    String engine;
    @NotNull
    Long kilometers;
    LocalDateTime productYear;
    @NotNull
    Long user_id;

}

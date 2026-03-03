package com.elshanaliyev.aliyevauto.dto.response;

import com.elshanaliyev.aliyevauto.Enums.CarEnums.BmwEngines;
import com.elshanaliyev.aliyevauto.entity.ServiceParts;
import com.elshanaliyev.aliyevauto.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

public class ServiceCarsResponse {
    @NotBlank
    String name;
    @NotBlank
    BmwEngines engines;
    @NotNull
    Integer kilometers;
    LocalDateTime productYear;
    @NotNull
    LocalDateTime purchasedYear;
    @NotNull
    Long user_id;

}

package com.elshanaliyev.aliyevauto.dto.request;

import com.elshanaliyev.aliyevauto.Enums.CarEnums.BmwEngines;
import com.elshanaliyev.aliyevauto.Enums.CarEnums.Color;
import com.elshanaliyev.aliyevauto.Enums.CarEnums.Model;
import com.elshanaliyev.aliyevauto.Enums.CarEnums.Trim;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class NewCarsRegister {
    @NotBlank(message = "Zavod (Factory) qeyd olunmalıdır")
    String factory;

    @NotNull(message = "Model seçilməlidir")
    Model model;

    @NotNull(message = "Mühərrik tipi seçilməlidir")
    BmwEngines engin;

    @NotNull(message = "Trim (Komplektasiya) seçilməlidir")
    Trim trim;

    @NotNull(message = "Rəng seçilməlidir")
    Color color;

    @NotNull(message = "İstehsal ili mütləqdir")
    LocalDateTime productYear;

    @NotNull(message = "Qiymət qeyd olunmalıdır")
    BigDecimal price;
}

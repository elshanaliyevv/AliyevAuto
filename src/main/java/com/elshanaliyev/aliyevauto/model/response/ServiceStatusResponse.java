package com.elshanaliyev.aliyevauto.model.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceStatusResponse {
    Long carId;
    Long currentKilometers;
    boolean oilChangeDue;
    boolean airFilterDue;
    boolean oilFilterDue;
    String summary;
    BigDecimal totalAmount;
}

package com.elshanaliyev.aliyevauto.model.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceTimesResponse {
    Long id;
    Integer lastOilChangeKilometr;
    Integer lastAirFilterChangeKilometr;
    Integer lastOilFilterChangeKilometr;
    BigDecimal serviceFee;
    BigDecimal totalAmount;
    Long carId;
}

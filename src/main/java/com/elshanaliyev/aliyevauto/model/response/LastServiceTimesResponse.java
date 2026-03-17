package com.elshanaliyev.aliyevauto.model.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LastServiceTimesResponse {
    Integer lastOilChangeKilometr;
    Integer lastAirFilterChangeKilometr;
    Integer lastOilFilterChangeKilometr;
    BigDecimal totalAmount;
    Long carId;
}

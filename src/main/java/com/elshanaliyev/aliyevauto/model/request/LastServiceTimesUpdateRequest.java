package com.elshanaliyev.aliyevauto.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LastServiceTimesUpdateRequest {

    Integer lastOilChangeKilometr;
    Integer lastAirFilterChangeKilometr;
    Integer lastOilFilterChangeKilometr;
    BigDecimal totalAmount;
}

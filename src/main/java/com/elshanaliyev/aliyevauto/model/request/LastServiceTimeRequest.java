package com.elshanaliyev.aliyevauto.model.request;

import java.math.BigDecimal;

public class LastServiceTimeRequest {
    Long carId;
    Integer lastOilChangeKilometr;
    Integer lastAirFilterChangeKilometr;
    BigDecimal serviceFee;
    BigDecimal totalAmount;
}

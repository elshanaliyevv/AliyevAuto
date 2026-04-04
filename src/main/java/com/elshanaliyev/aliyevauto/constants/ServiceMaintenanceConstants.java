package com.elshanaliyev.aliyevauto.constants;

import java.math.BigDecimal;

public final class ServiceMaintenanceConstants {

    public static final int INTERVAL_KM = 6000;

    public static final BigDecimal OIL_CHANGE_PRICE = new BigDecimal("60");
    public static final BigDecimal AIR_FILTER_PRICE = new BigDecimal("15");
    public static final BigDecimal OIL_FILTER_PRICE = new BigDecimal("10");

    private ServiceMaintenanceConstants() {
    }
}

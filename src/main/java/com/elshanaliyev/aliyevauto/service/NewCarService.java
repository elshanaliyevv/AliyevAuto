package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.model.request.NewCarsRequest;
import com.elshanaliyev.aliyevauto.model.response.NewCarResponse;

public interface NewCarService {
    public void addCar(NewCarsRequest newCarsRequest);
    public void buyCar(NewCarResponse newCarResponse);

}

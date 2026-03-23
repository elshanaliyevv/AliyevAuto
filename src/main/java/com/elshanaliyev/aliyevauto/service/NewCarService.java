package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.model.request.NewCarUpdateRequest;
import com.elshanaliyev.aliyevauto.model.request.NewCarsRequest;
import com.elshanaliyev.aliyevauto.model.response.NewCarResponse;

import java.math.BigDecimal;
import java.util.List;

public interface NewCarService {
    List<NewCarResponse> getAll();

    NewCarResponse getCarWithId(Long id);

    List<NewCarResponse> getCarWithBrand(String brand);

    List<NewCarResponse> getCars();

    List<NewCarResponse> getCarsById(Long id);

    void addCar(NewCarsRequest newCarsRequest);

    NewCarResponse updateCar(Long id, NewCarUpdateRequest request);

    NewCarResponse buyCarById(Long id, Long buyerUserId);

    NewCarResponse buyCarByBrand(String brand, Long buyerUserId);

    void deleteCar(Long id);

    void updatePrice(Long id, BigDecimal price);
}

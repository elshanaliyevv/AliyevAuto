package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.Exceptions.CarNotFoundException;
import com.elshanaliyev.aliyevauto.Exceptions.NewCarsNotFoundException;
import com.elshanaliyev.aliyevauto.mapper.EntityMapper;
import com.elshanaliyev.aliyevauto.model.entity.NewCar;
import com.elshanaliyev.aliyevauto.model.request.NewCarUpdateRequest;
import com.elshanaliyev.aliyevauto.model.request.NewCarsRequest;
import com.elshanaliyev.aliyevauto.model.response.NewCarResponse;
import com.elshanaliyev.aliyevauto.repository.NewCarRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NewCarServiceImpl implements NewCarService {

    private final NewCarRepo newCarRepo;
    private final EntityMapper entityMapper;
    private final ServiceCarsService serviceCarsService;

    @Override
    public List<NewCarResponse> getAll() {
        List<NewCar> newCarList = newCarRepo.findAll();
        if (newCarList.isEmpty()) {
            throw new NewCarsNotFoundException("New cars not found");
        }
        return newCarList.stream()
                .map(entityMapper::newCarToResponseForAdmin)
                .toList();
    }

    @Override
    public NewCarResponse getCarWithId(Long id) {
        NewCar newCar = findNewCarByIdOrThrow(id);
        return entityMapper.newCarToResponseForAdmin(newCar);
    }

    @Override
    public List<NewCarResponse> getCarWithBrand(String brand) {
        List<NewCarResponse> responses = newCarRepo.findByBrand_Name(brand).stream()
                .filter(this::inStock)
                .map(entityMapper::newCarToResponse)
                .toList();
        if (responses.isEmpty()) {
            throw new NewCarsNotFoundException("New cars not found for brand: " + brand);
        }
        return responses;
    }

    @Override
    public List<NewCarResponse> getCars() {
        return newCarRepo.findAll().stream()
                .filter(this::inStock)
                .map(entityMapper::newCarToResponse)
                .toList();
    }

    @Override
    public List<NewCarResponse> getCarsById(Long id) {
        return List.of(getCarWithId(id));
    }

    @Override
    @Transactional
    public void addCar(NewCarsRequest newCarsRequest) {
        newCarRepo.save(entityMapper.newCarRequestToNewCar(newCarsRequest));
    }

    @Override
    @Transactional
    public NewCarResponse updateCar(Long id, NewCarUpdateRequest request) {
        NewCar car = findNewCarByIdOrThrow(id);
        entityMapper.updateNewCarFromRequest(car, request);
        newCarRepo.save(car);
        return entityMapper.newCarToResponseForAdmin(car);
    }

    @Override
    @Transactional
    public NewCarResponse buyCarById(Long id, Long buyerUserId) {
        NewCar car = findNewCarByIdOrThrow(id);
        int stock = stockCount(car);
        if (stock <= 0) {
            throw new CarNotFoundException("Car out of stock");
        }
        car.setCount(stock - 1);
        newCarRepo.save(car);
        serviceCarsService.registerFromNewCarPurchase(car, buyerUserId);
        return entityMapper.newCarToResponse(car);
    }

    @Override
    @Transactional
    public NewCarResponse buyCarByBrand(String brand, Long buyerUserId) {
        NewCar car = newCarRepo.findFirstByBrand_NameAndCountGreaterThanOrderByIdAsc(brand, 0)
                .orElseThrow(() -> new CarNotFoundException("No car in stock for brand: " + brand));
        int stock = stockCount(car);
        car.setCount(stock - 1);
        newCarRepo.save(car);
        serviceCarsService.registerFromNewCarPurchase(car, buyerUserId);
        return entityMapper.newCarToResponse(car);
    }

    @Override
    @Transactional
    public void deleteCar(Long id) {
        if (!newCarRepo.existsById(id)) {
            throw new CarNotFoundException("Car not found");
        }
        newCarRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void updatePrice(Long id, BigDecimal price) {
        NewCar car = findNewCarByIdOrThrow(id);
        car.setPrice(price);
        newCarRepo.save(car);
    }

    private NewCar findNewCarByIdOrThrow(Long id) {
        return newCarRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car not found"));
    }

    private boolean inStock(NewCar car) {
        return stockCount(car) > 0;
    }

    private static int stockCount(NewCar car) {
        Integer c = car.getCount();
        return c == null ? 0 : c;
    }
}

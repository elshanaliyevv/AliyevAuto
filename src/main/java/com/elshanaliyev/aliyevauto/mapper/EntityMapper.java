package com.elshanaliyev.aliyevauto.mapper;

import com.elshanaliyev.aliyevauto.Exceptions.BrandNotFoundException;
import com.elshanaliyev.aliyevauto.Exceptions.ColorNotFoundException;
import com.elshanaliyev.aliyevauto.Exceptions.EngineNotFoundException;
import com.elshanaliyev.aliyevauto.model.entity.LastServiceTimes;
import com.elshanaliyev.aliyevauto.model.entity.NewCar;
import com.elshanaliyev.aliyevauto.model.entity.ServiceCars;
import com.elshanaliyev.aliyevauto.model.entity.User;
import com.elshanaliyev.aliyevauto.model.request.NewCarUpdateRequest;
import com.elshanaliyev.aliyevauto.model.request.NewCarsRequest;
import com.elshanaliyev.aliyevauto.model.request.ServiceCarsRequest;
import com.elshanaliyev.aliyevauto.model.response.LastServiceTimesResponse;
import com.elshanaliyev.aliyevauto.model.response.NewCarResponse;
import com.elshanaliyev.aliyevauto.model.response.ServiceCarsResponse;
import com.elshanaliyev.aliyevauto.model.response.UserResponse;
import com.elshanaliyev.aliyevauto.repository.BrandRepo;
import com.elshanaliyev.aliyevauto.repository.ColorRepo;
import com.elshanaliyev.aliyevauto.repository.EngineRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityMapper {

    private final BrandRepo brandRepo;
    private final EngineRepo engineRepo;
    private final ColorRepo colorRepo;

    public UserResponse userToResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getNumber(),
                user.getRole().toString());
    }

    public LastServiceTimesResponse lastServiceTimesToResponse(LastServiceTimes lastServiceTimes) {
        if (lastServiceTimes == null) {
            return null;
        }
        return LastServiceTimesResponse.builder()
                .lastOilChangeKilometr(lastServiceTimes.getLastOilChangeKilometr())
                .lastAirFilterChangeKilometr(lastServiceTimes.getLastAirFilterChangeKilometr())
                .lastOilFilterChangeKilometr(lastServiceTimes.getLastOilFilterChangeKilometr())
                .totalAmount(lastServiceTimes.getTotalAmount())
                .carId(lastServiceTimes.getServiceCars().getId())
                .build();
    }

    public NewCarResponse newCarToResponseForAdmin(NewCar newCar) {
        if (newCar == null) {
            return null;
        }
        return NewCarResponse.builder()
                .brand(newCar.getBrand().getName().toString())
                .engine(newCar.getEngine().getEngineCode())
                .trim(newCar.getTrim())
                .color(newCar.getColor().getColorCode())
                .productYear(newCar.getProductYear())
                .price(newCar.getPrice())
                .count(newCar.getCount())
                .build();
    }

    public NewCarResponse newCarToResponse(NewCar newCar) {
        if (newCar == null) {
            return null;
        }
        return NewCarResponse.builder()
                .brand(newCar.getBrand().getName().toString())
                .engine(newCar.getEngine().getEngineCode())
                .trim(newCar.getTrim())
                .color(newCar.getColor().getColorCode())
                .productYear(newCar.getProductYear())
                .price(newCar.getPrice())
                .build();
    }

    public NewCar newCarRequestToNewCar(NewCarsRequest request) {
        if (request == null) {
            return null;
        }
        NewCar newCar = new NewCar();
        newCar.setBrand(brandRepo.findByName(request.getBrand())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found: " + request.getBrand())));
        newCar.setEngine(engineRepo.findByEngineCode(request.getEngine())
                .orElseThrow(() -> new EngineNotFoundException("Engine not found: " + request.getEngine())));
        newCar.setColor(colorRepo.findByColorCode(request.getColor())
                .orElseThrow(() -> new ColorNotFoundException("Color not found: " + request.getColor())));
        newCar.setTrim(request.getTrim());
        newCar.setProductYear(request.getProductYear());
        newCar.setPrice(request.getPrice());
        newCar.setCount(1);
        return newCar;
    }

    public void updateNewCarFromRequest(NewCar car, NewCarUpdateRequest request) {
        if (car == null || request == null) {
            return;
        }
        car.setBrand(brandRepo.findByName(request.getBrand())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found: " + request.getBrand())));
        car.setEngine(engineRepo.findByEngineCode(request.getEngine())
                .orElseThrow(() -> new EngineNotFoundException("Engine not found: " + request.getEngine())));
        car.setColor(colorRepo.findByColorCode(request.getColor())
                .orElseThrow(() -> new ColorNotFoundException("Color not found: " + request.getColor())));
        car.setTrim(request.getTrim());
        car.setProductYear(request.getProductYear());
        car.setPrice(request.getPrice());
        car.setCount(request.getCount());
    }

    public void updateServiceCarFromRequest(ServiceCars car, ServiceCarsRequest request) {
        if (car == null || request == null) {
            return;
        }
        car.setBrand(brandRepo.findByName(request.getBrand())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found: " + request.getBrand())));
        car.setEngine(engineRepo.findByEngineCode(request.getEngine())
                .orElseThrow(() -> new EngineNotFoundException("Engine not found: " + request.getEngine())));
        car.setColor(colorRepo.findByColorCode(request.getColor())
                .orElseThrow(() -> new ColorNotFoundException("Color not found: " + request.getColor())));
        car.setKilometers(request.getKilometers().longValue());
        car.setProductYear(request.getProductYear());
    }

    public ServiceCarsResponse serviceCarsToResponse(ServiceCars serviceCars) {
        if (serviceCars == null) {
            return null;
        }
        return ServiceCarsResponse.builder()
                .brand(serviceCars.getBrand().getName().toString())
                .engine(serviceCars.getEngine().getEngineCode())
                .kilometers(serviceCars.getKilometers())
                .productYear(serviceCars.getProductYear())
                .user_id(serviceCars.getUser().getId())
                .build();
    }

    public ServiceCars serviceCarsRequestToEntity(ServiceCarsRequest request, User user) {
        if (request == null || user == null) {
            return null;
        }
        ServiceCars sc = new ServiceCars();
        sc.setBrand(brandRepo.findByName(request.getBrand())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found: " + request.getBrand())));
        sc.setEngine(engineRepo.findByEngineCode(request.getEngine())
                .orElseThrow(() -> new EngineNotFoundException("Engine not found: " + request.getEngine())));
        sc.setColor(colorRepo.findByColorCode(request.getColor())
                .orElseThrow(() -> new ColorNotFoundException("Color not found: " + request.getColor())));
        sc.setKilometers(request.getKilometers().longValue());
        sc.setProductYear(request.getProductYear());
        sc.setUser(user);
        return sc;
    }
}

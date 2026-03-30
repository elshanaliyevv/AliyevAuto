package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.Exceptions.CarNotFoundException;
import com.elshanaliyev.aliyevauto.Exceptions.ServiceTImeNotFoundException;
import com.elshanaliyev.aliyevauto.Exceptions.UserNotFoundException;
import com.elshanaliyev.aliyevauto.constants.ServiceMaintenanceConstants;
import com.elshanaliyev.aliyevauto.mapper.EntityMapper;
import com.elshanaliyev.aliyevauto.model.entity.LastServiceTimes;
import com.elshanaliyev.aliyevauto.model.entity.NewCar;
import com.elshanaliyev.aliyevauto.model.entity.ServiceCars;
import com.elshanaliyev.aliyevauto.model.entity.User;
import com.elshanaliyev.aliyevauto.model.request.ServiceCarsRequest;
import com.elshanaliyev.aliyevauto.model.response.ServiceCarsResponse;
import com.elshanaliyev.aliyevauto.model.response.ServiceStatusResponse;
import com.elshanaliyev.aliyevauto.repository.LastServiceTimesRepo;
import com.elshanaliyev.aliyevauto.repository.ServiceCarsRepo;
import com.elshanaliyev.aliyevauto.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceCarsService {

    private final ServiceCarsRepo serviceCarsRepo;
    private final LastServiceTimesRepo lastServiceTimesRepo;
    private final UserRepo userRepo;
    private final EntityMapper entityMapper;

    public List<ServiceCarsResponse> listMyCars(Long userId) {
        return serviceCarsRepo.findByUser_Id(userId).stream()
                .map(entityMapper::serviceCarsToResponse)
                .toList();
    }

    @Transactional
    public ServiceCarsResponse addCar(ServiceCarsRequest request, Long ownerUserId) {
        User user = userRepo.findById(ownerUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        ServiceCars car = entityMapper.serviceCarsRequestToEntity(request, user);
        serviceCarsRepo.save(car);
        int baselineKm = car.getKilometers().intValue();
        initLastServiceTimes(car, baselineKm);
        return entityMapper.serviceCarsToResponse(car);
    }

    @Transactional
    public void registerFromNewCarPurchase(NewCar newCar, Long buyerUserId) {
        User user = userRepo.findById(buyerUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        ServiceCars car = new ServiceCars();
        car.setBrand(newCar.getBrand());
        car.setEngine(newCar.getEngine());
        car.setColor(newCar.getColor());
        car.setKilometers(0L);
        car.setProductYear(newCar.getProductYear());
        car.setUser(user);
        serviceCarsRepo.save(car);
        initLastServiceTimes(car, 0);
    }

    @Transactional
    public ServiceCarsResponse updateMyCar(Long carId, Long userId, ServiceCarsRequest request) {
        ServiceCars car = findOwnedCarOrThrow(carId, userId);
        long newKm = request.getKilometers().longValue();
        if (newKm < car.getKilometers()) {
            throw new IllegalArgumentException("Kilometr göstəricisi azala bilməz");
        }
        entityMapper.updateServiceCarFromRequest(car, request);
        serviceCarsRepo.save(car);
        return entityMapper.serviceCarsToResponse(car);
    }

    @Transactional
    public void deleteMyCar(Long carId, Long userId) {
        ServiceCars car = findOwnedCarOrThrow(carId, userId);
        lastServiceTimesRepo.findByServiceCars_Id(carId).ifPresent(lastServiceTimesRepo::delete);
        serviceCarsRepo.delete(car);
    }

    @Transactional
    public void resetLastServiceTimes(Long carId, Long userId) {
        ServiceCars car = findOwnedCarOrThrow(carId, userId);
        lastServiceTimesRepo.findByServiceCars_Id(carId).ifPresent(lastServiceTimesRepo::delete);
        int baseline = car.getKilometers().intValue();
        initLastServiceTimes(car, baseline);
    }

    @Transactional
    public ServiceCarsResponse updateOdometer(Long carId, Long userId, Long newKilometers) {
        ServiceCars car = findOwnedCarOrThrow(carId, userId);
        if (newKilometers < car.getKilometers()) {
            throw new IllegalArgumentException("Kilometr göstəricisi azala bilməz");
        }
        car.setKilometers(newKilometers);
        serviceCarsRepo.save(car);
        return entityMapper.serviceCarsToResponse(car);
    }

    public ServiceStatusResponse getDueStatus(Long carId, Long userId) {
        ServiceCars car = findOwnedCarOrThrow(carId, userId);
        LastServiceTimes times = lastServiceTimesRepo.findByServiceCars_Id(carId)
                .orElseThrow(() -> new ServiceTImeNotFoundException("Servis qeydiyyatı tapılmadı"));

        long current = car.getKilometers();
        boolean oilDue = due(current, lastOrZero(times.getLastOilChangeKilometr()));
        boolean airDue = due(current, lastOrZero(times.getLastAirFilterChangeKilometr()));
        boolean oilFilterDue = due(current, lastOrZero(times.getLastOilFilterChangeKilometr()));

        BigDecimal estimated = BigDecimal.ZERO;
        if (oilDue) {
            estimated = estimated.add(ServiceMaintenanceConstants.OIL_CHANGE_PRICE);
        }
        if (airDue) {
            estimated = estimated.add(ServiceMaintenanceConstants.AIR_FILTER_PRICE);
        }
        if (oilFilterDue) {
            estimated = estimated.add(ServiceMaintenanceConstants.OIL_FILTER_PRICE);
        }

        boolean anyDue = oilDue || airDue || oilFilterDue;
        String summary = anyDue
                ? "Xidmət lazımdır (6000 km intervalı keçilib və ya çatılıb)"
                : "Hal-hazırda bu interval üzrə təcili xidmət tələb olunmur";

        return ServiceStatusResponse.builder()
                .carId(carId)
                .currentKilometers(current)
                .oilChangeDue(oilDue)
                .airFilterDue(airDue)
                .oilFilterDue(oilFilterDue)
                .summary(summary)
                .totalAmount(estimated)
                .accumulatedDebt(times.getTotalAmount() != null ? times.getTotalAmount() : BigDecimal.ZERO)
                .build();
    }

    public static boolean isOilChangeDue(long currentKm, Integer lastOilKm) {
        return due(currentKm, lastOrZero(lastOilKm));
    }

    public static boolean isAirFilterDue(long currentKm, Integer lastAirKm) {
        return due(currentKm, lastOrZero(lastAirKm));
    }

    public static boolean isOilFilterDue(long currentKm, Integer lastOilFilterKm) {
        return due(currentKm, lastOrZero(lastOilFilterKm));
    }

    ServiceCars findOwnedCarOrThrow(Long carId, Long userId) {
        ServiceCars car = serviceCarsRepo.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car not found"));
        if (!car.getUser().getId().equals(userId)) {
            throw new CarNotFoundException("Car not found");
        }
        return car;
    }

    private void initLastServiceTimes(ServiceCars car, int lastServiceBaselineKm) {
        LastServiceTimes lst = new LastServiceTimes();
        lst.setServiceCars(car);
        lst.setLastOilChangeKilometr(lastServiceBaselineKm);
        lst.setLastAirFilterChangeKilometr(lastServiceBaselineKm);
        lst.setLastOilFilterChangeKilometr(lastServiceBaselineKm);
        lst.setTotalAmount(BigDecimal.ZERO);
        lastServiceTimesRepo.save(lst);
    }

    private static int lastOrZero(Integer v) {
        return v == null ? 0 : v;
    }

    private static boolean due(long currentKm, int lastKm) {
        return currentKm - lastKm >= ServiceMaintenanceConstants.INTERVAL_KM;
    }
}

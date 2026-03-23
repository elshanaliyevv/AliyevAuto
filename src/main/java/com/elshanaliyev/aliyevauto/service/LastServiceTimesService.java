package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.Exceptions.ServiceTImeNotFoundException;
import com.elshanaliyev.aliyevauto.constants.ServiceMaintenanceConstants;
import com.elshanaliyev.aliyevauto.mapper.EntityMapper;
import com.elshanaliyev.aliyevauto.model.entity.LastServiceTimes;
import com.elshanaliyev.aliyevauto.model.entity.ServiceCars;
import com.elshanaliyev.aliyevauto.model.request.LastServiceTimeRequest;
import com.elshanaliyev.aliyevauto.model.request.LastServiceTimesUpdateRequest;
import com.elshanaliyev.aliyevauto.model.response.LastServiceTimesResponse;
import com.elshanaliyev.aliyevauto.repository.LastServiceTimesRepo;
import com.elshanaliyev.aliyevauto.repository.ServiceCarsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LastServiceTimesService {

    private final LastServiceTimesRepo lastServiceTimesRepo;
    private final ServiceCarsRepo serviceCarsRepo;
    private final ServiceCarsService serviceCarsService;
    private final EntityMapper entityMapper;

    public LastServiceTimesResponse getForCar(Long carId, Long userId) {
        serviceCarsService.findOwnedCarOrThrow(carId, userId);
        LastServiceTimes times = lastServiceTimesRepo.findByServiceCars_Id(carId)
                .orElseThrow(() -> new ServiceTImeNotFoundException("Service time not found"));
        return entityMapper.lastServiceTimesToResponse(times);
    }

    @Transactional
    public LastServiceTimesResponse confirmService(Long carId, Long userId, LastServiceTimeRequest request) {
        ServiceCars car = serviceCarsService.findOwnedCarOrThrow(carId, userId);
        LastServiceTimes times = lastServiceTimesRepo.findByServiceCars_Id(carId)
                .orElseThrow(() -> new ServiceTImeNotFoundException("Service time not found"));

        if (!request.isOilChange() && !request.isAirFilterChange() && !request.isOilFilterChange()) {
            throw new IllegalArgumentException("Ən azı bir xidmət növü seçilməlidir");
        }

        int odo = request.getOdometerAtService();
        if (odo < car.getKilometers()) {
            throw new IllegalArgumentException("Yeni km göstəricisi cari göstəricidən kiçik ola bilməz");
        }

        long currentKmBefore = car.getKilometers();
        boolean oilDue = ServiceCarsService.isOilChangeDue(currentKmBefore, times.getLastOilChangeKilometr());
        boolean airDue = ServiceCarsService.isAirFilterDue(currentKmBefore, times.getLastAirFilterChangeKilometr());
        boolean filterDue = ServiceCarsService.isOilFilterDue(currentKmBefore, times.getLastOilFilterChangeKilometr());

        BigDecimal addition = BigDecimal.ZERO;

        if (request.isOilChange()) {
            if (!oilDue) {
                throw new IllegalArgumentException("Yağ dəyişimi hazırda tələb olunmur (6000 km intervalı)");
            }
            times.setLastOilChangeKilometr(odo);
            addition = addition.add(ServiceMaintenanceConstants.OIL_CHANGE_PRICE);
        }
        if (request.isAirFilterChange()) {
            if (!airDue) {
                throw new IllegalArgumentException("Hava filteri hazırda tələb olunmur (6000 km intervalı)");
            }
            times.setLastAirFilterChangeKilometr(odo);
            addition = addition.add(ServiceMaintenanceConstants.AIR_FILTER_PRICE);
        }
        if (request.isOilFilterChange()) {
            if (!filterDue) {
                throw new IllegalArgumentException("Yağ filteri hazırda tələb olunmur (6000 km intervalı)");
            }
            times.setLastOilFilterChangeKilometr(odo);
            addition = addition.add(ServiceMaintenanceConstants.OIL_FILTER_PRICE);
        }

        car.setKilometers((long) odo);
        BigDecimal prev = times.getTotalAmount() == null ? BigDecimal.ZERO : times.getTotalAmount();
        times.setTotalAmount(prev.add(addition));

        serviceCarsRepo.save(car);
        lastServiceTimesRepo.save(times);

        return entityMapper.lastServiceTimesToResponse(times);
    }

    @Transactional
    public LastServiceTimesResponse updateForCar(Long carId, Long userId, LastServiceTimesUpdateRequest request) {
        serviceCarsService.findOwnedCarOrThrow(carId, userId);
        LastServiceTimes times = lastServiceTimesRepo.findByServiceCars_Id(carId)
                .orElseThrow(() -> new ServiceTImeNotFoundException("Service time not found"));

        boolean any = false;
        if (request.getLastOilChangeKilometr() != null) {
            times.setLastOilChangeKilometr(request.getLastOilChangeKilometr());
            any = true;
        }
        if (request.getLastAirFilterChangeKilometr() != null) {
            times.setLastAirFilterChangeKilometr(request.getLastAirFilterChangeKilometr());
            any = true;
        }
        if (request.getLastOilFilterChangeKilometr() != null) {
            times.setLastOilFilterChangeKilometr(request.getLastOilFilterChangeKilometr());
            any = true;
        }
        if (request.getTotalAmount() != null) {
            times.setTotalAmount(request.getTotalAmount());
            any = true;
        }
        if (!any) {
            throw new IllegalArgumentException("Ən azı bir sahə göndərilməlidir");
        }

        lastServiceTimesRepo.save(times);
        return entityMapper.lastServiceTimesToResponse(times);
    }

    @Transactional
    public LastServiceTimesResponse resetForCar(Long carId, Long userId) {
        serviceCarsService.resetLastServiceTimes(carId, userId);
        return getForCar(carId, userId);
    }
}

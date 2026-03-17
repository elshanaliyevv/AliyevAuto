package com.elshanaliyev.aliyevauto.mapper;

import com.elshanaliyev.aliyevauto.model.entity.LastServiceTimes;
import com.elshanaliyev.aliyevauto.model.entity.NewCar;
import com.elshanaliyev.aliyevauto.model.entity.ServiceCars;
import com.elshanaliyev.aliyevauto.model.entity.User;
import com.elshanaliyev.aliyevauto.model.request.ServiceCarsRequest;
import com.elshanaliyev.aliyevauto.model.response.LastServiceTimesResponse;
import com.elshanaliyev.aliyevauto.model.response.NewCarResponse;
import com.elshanaliyev.aliyevauto.model.response.ServiceCarsResponse;
import com.elshanaliyev.aliyevauto.model.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
    public UserResponse userToResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getNumber());
    }

    public LastServiceTimesResponse lastServiceTimesToResponse(LastServiceTimes lastServiceTimes) {
        if (lastServiceTimes == null) {
            return null;
        }
        return LastServiceTimesResponse.builder()
                .lastOilChangeKilometr(lastServiceTimes.getLastOilChangeKilometr())
                .lastAirFilterChangeKilometr(lastServiceTimes.getLastAirFilterChangeKilometr())
                .totalAmount(lastServiceTimes.getTotalAmount())
                .carId(lastServiceTimes.getServiceCars().getId())
                .build();
    }

    public NewCarResponse newCarToResponse(NewCar newCar) {
        if (newCar == null) {
            return null;
        }
        return NewCarResponse.builder()
                .Factory(newCar.getFactory())
                .model(newCar.getModel().getName().toString())
                .engine(newCar.getEngine().getEngineCode())
                .trim(newCar.getTrim())
                .color(newCar.getColor().getColorCode())
                .productYear(newCar.getProductYear())
                .price(newCar.getPrice())
                .build();
    }

    public ServiceCarsResponse serviceCarsToResponse(ServiceCars serviceCars) {
        if (serviceCars == null) {
            return null;
        }
        return ServiceCarsResponse.builder()
                .model(serviceCars.getModel().getName().toString())
                .engine(serviceCars.getEngine().getEngineCode())
                .kilometers(serviceCars.getKilometers())
                .productYear(serviceCars.getProductYear())
                .user_id(serviceCars.getUser().getId())
                .build();
    }
}

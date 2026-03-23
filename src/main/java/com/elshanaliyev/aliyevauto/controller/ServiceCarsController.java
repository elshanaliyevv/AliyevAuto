package com.elshanaliyev.aliyevauto.controller;

import com.elshanaliyev.aliyevauto.model.request.OdometerUpdateRequest;
import com.elshanaliyev.aliyevauto.model.request.ServiceCarsRequest;
import com.elshanaliyev.aliyevauto.model.response.ServiceCarsResponse;
import com.elshanaliyev.aliyevauto.model.response.ServiceStatusResponse;
import com.elshanaliyev.aliyevauto.security.CustomUserDetails;
import com.elshanaliyev.aliyevauto.service.ServiceCarsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service-cars")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ServiceCarsController {

    private final ServiceCarsService serviceCarsService;

    @GetMapping("/my")
    public ResponseEntity<List<ServiceCarsResponse>> myCars(Authentication authentication) {
        return ResponseEntity.ok(serviceCarsService.listMyCars(currentUserId(authentication)));
    }

    @PostMapping
    public ResponseEntity<ServiceCarsResponse> addCar(
            @Valid @RequestBody ServiceCarsRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(serviceCarsService.addCar(request, currentUserId(authentication)));
    }

    @PutMapping("/{carId}")
    public ResponseEntity<ServiceCarsResponse> updateCar(
            @PathVariable Long carId,
            @Valid @RequestBody ServiceCarsRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(serviceCarsService.updateMyCar(
                carId, currentUserId(authentication), request));
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(
            @PathVariable Long carId,
            Authentication authentication) {
        serviceCarsService.deleteMyCar(carId, currentUserId(authentication));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{carId}/odometer")
    public ResponseEntity<ServiceCarsResponse> updateOdometer(
            @PathVariable Long carId,
            @Valid @RequestBody OdometerUpdateRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(serviceCarsService.updateOdometer(
                carId, currentUserId(authentication), request.getKilometers()));
    }


    @GetMapping("/{carId}/due-status")
    public ResponseEntity<ServiceStatusResponse> dueStatus(
            @PathVariable Long carId,
            Authentication authentication) {
        return ResponseEntity.ok(serviceCarsService.getDueStatus(carId, currentUserId(authentication)));
    }

    private static Long currentUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getId();
    }
}

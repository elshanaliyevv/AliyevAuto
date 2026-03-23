package com.elshanaliyev.aliyevauto.controller;

import com.elshanaliyev.aliyevauto.model.request.NewCarUpdateRequest;
import com.elshanaliyev.aliyevauto.model.request.NewCarsRequest;
import com.elshanaliyev.aliyevauto.model.response.NewCarResponse;
import com.elshanaliyev.aliyevauto.security.CustomUserDetails;
import com.elshanaliyev.aliyevauto.service.NewCarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/new-cars")
@RequiredArgsConstructor
public class NewCarController {

    private final NewCarService newCarService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Void> addCar(@Valid @RequestBody NewCarsRequest request) {
        newCarService.addCar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<NewCarResponse> updateCar(
            @PathVariable Long id,
            @Valid @RequestBody NewCarUpdateRequest request) {
        return ResponseEntity.ok(newCarService.updateCar(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        newCarService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/buy/{id}")
    public ResponseEntity<NewCarResponse> buyById(
            @PathVariable Long id,
            Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(newCarService.buyCarById(id, userId));
    }

    @PostMapping("/buy/brand/{brand}")
    public ResponseEntity<NewCarResponse> buyByBrand(
            @PathVariable String brand,
            Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(newCarService.buyCarByBrand(brand, userId));
    }
}

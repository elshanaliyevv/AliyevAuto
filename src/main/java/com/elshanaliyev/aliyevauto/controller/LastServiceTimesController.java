package com.elshanaliyev.aliyevauto.controller;

import com.elshanaliyev.aliyevauto.model.request.LastServiceTimeRequest;
import com.elshanaliyev.aliyevauto.model.request.LastServiceTimesUpdateRequest;
import com.elshanaliyev.aliyevauto.model.response.LastServiceTimesResponse;
import com.elshanaliyev.aliyevauto.security.CustomUserDetails;
import com.elshanaliyev.aliyevauto.service.LastServiceTimesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-cars/{carId}/last-service")
@RequiredArgsConstructor
public class LastServiceTimesController {

    private final LastServiceTimesService lastServiceTimesService;

    @GetMapping
    public ResponseEntity<LastServiceTimesResponse> get(
            @PathVariable Long carId,
            Authentication authentication) {
        return ResponseEntity.ok(lastServiceTimesService.getForCar(carId, currentUserId(authentication)));
    }

    @PostMapping("/confirm")
    public ResponseEntity<LastServiceTimesResponse> confirm(
            @PathVariable Long carId,
            @Valid @RequestBody LastServiceTimeRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(lastServiceTimesService.confirmService(
                carId, currentUserId(authentication), request));
    }

    @PutMapping
    public ResponseEntity<LastServiceTimesResponse> update(
            @PathVariable Long carId,
            @RequestBody LastServiceTimesUpdateRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(lastServiceTimesService.updateForCar(
                carId, currentUserId(authentication), request));
    }

    @DeleteMapping
    public ResponseEntity<LastServiceTimesResponse> reset(
            @PathVariable Long carId,
            Authentication authentication) {
        return ResponseEntity.ok(lastServiceTimesService.resetForCar(
                carId, currentUserId(authentication)));
    }

    private static Long currentUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getId();
    }
}

package com.elshanaliyev.aliyevauto.controller;

import com.elshanaliyev.aliyevauto.dto.request.RefreshTokenRequest;
import com.elshanaliyev.aliyevauto.dto.request.UserLogin;
import com.elshanaliyev.aliyevauto.dto.request.UserRegister;
import com.elshanaliyev.aliyevauto.dto.response.AuthResponse;
import com.elshanaliyev.aliyevauto.dto.response.TokensResponse;
import com.elshanaliyev.aliyevauto.service.AuthService;
import com.elshanaliyev.aliyevauto.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRegister request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLogin request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokensResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(jwtService.refreshAccessToken(request.getRefreshToken()));
    }
}
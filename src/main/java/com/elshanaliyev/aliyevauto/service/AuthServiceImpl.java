package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.config.SecurityConfig;
import com.elshanaliyev.aliyevauto.dto.request.UserLogin;
import com.elshanaliyev.aliyevauto.dto.request.UserRegister;
import com.elshanaliyev.aliyevauto.dto.response.AuthResponse;
import com.elshanaliyev.aliyevauto.entity.User;
import com.elshanaliyev.aliyevauto.repository.UserRepo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    UserRepo userRepo;
    PasswordEncoder passwordEncoder;
    @Override
    public AuthResponse register(UserRegister userRegister) {
        if (userRepo.findAllByUsername(userRegister.getUsername()).isPresent()){
            throw new RuntimeException("bu istifadeci var");
        }
        User user= User.builder()
                .email(userRegister.getEmail())
                .number(userRegister.getNumber())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .username(userRegister.getUsername())
                .build();
        User saved = userRepo.save(user);
        AuthResponse authResponse = new AuthResponse();
        return null;
    }

    @Override
    public AuthResponse login(UserLogin userLogin) {
        return null;
    }
}

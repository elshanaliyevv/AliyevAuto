package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.Enums.UserEnums.Roles;
import com.elshanaliyev.aliyevauto.model.request.UserLogin;
import com.elshanaliyev.aliyevauto.model.request.UserRegister;
import com.elshanaliyev.aliyevauto.model.response.AuthResponse;
import com.elshanaliyev.aliyevauto.model.response.TokensResponse;
import com.elshanaliyev.aliyevauto.model.response.UserResponse;
import com.elshanaliyev.aliyevauto.model.entity.User;
import com.elshanaliyev.aliyevauto.repository.UserRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(UserRegister userRegister) {
        if (userRepo.existsByUsername(userRegister.getUsername())) {
            throw new RuntimeException("Bu username artiq movcuddur");
        }

        if (StringUtils.hasText(userRegister.getEmail()) && userRepo.existsByEmail(userRegister.getEmail())) {
            throw new RuntimeException("Bu email artiq movcuddur");
        }

        if (StringUtils.hasText(userRegister.getNumber()) && userRepo.existsByNumber(userRegister.getNumber())) {
            throw new RuntimeException("Bu nomre artiq movcuddur");
        }

        User user = User.builder()
                .username(userRegister.getUsername())
                .email(userRegister.getEmail())
                .number(userRegister.getNumber())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .role(Roles.USER)
                .build();

        User savedUser = userRepo.save(user);
        TokensResponse tokens = jwtService.generateTokens(savedUser.getUsername());

        return buildAuthResponse(savedUser, tokens);
    }

    @Override
    public AuthResponse login(UserLogin userLogin) {
        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        userLogin.getIdentifier(),
                        userLogin.getPassword()
                )
        );

        User user = userRepo.findByUsernameOrEmailOrNumber(
                userLogin.getIdentifier(),
                userLogin.getIdentifier(),
                userLogin.getIdentifier()
        ).orElseThrow(() -> new RuntimeException("Istifadeci tapilmadi"));

        TokensResponse tokens = jwtService.generateTokens(user.getUsername());

        return buildAuthResponse(user, tokens);
    }

    private AuthResponse buildAuthResponse(User user, TokensResponse tokens) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setNumber(user.getNumber());
        userResponse.setRole(user.getRole().toString());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setTokensResponse(tokens);
        authResponse.setUserResponse(userResponse);

        return authResponse;
    }
}
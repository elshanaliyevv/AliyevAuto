package com.elshanaliyev.aliyevauto.security;

import com.elshanaliyev.aliyevauto.model.entity.User;
import com.elshanaliyev.aliyevauto.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

        User user = userRepo
                .findByUsernameOrEmailOrNumber(identifier, identifier, identifier)
                .orElseThrow(() -> new UsernameNotFoundException("User tapılmadı"));

        String role = user.getRole() == null ? "USER" : user.getRole().name();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(role)
                .build();
    }
}
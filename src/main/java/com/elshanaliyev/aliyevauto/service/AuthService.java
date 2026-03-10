package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.dto.request.UserLogin;
import com.elshanaliyev.aliyevauto.dto.request.UserRegister;
import com.elshanaliyev.aliyevauto.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register (UserRegister userRegister);
    AuthResponse login(UserLogin userLogin);
}

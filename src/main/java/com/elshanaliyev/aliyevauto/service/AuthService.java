package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.model.request.UserLogin;
import com.elshanaliyev.aliyevauto.model.request.UserRegister;
import com.elshanaliyev.aliyevauto.model.response.AuthResponse;

public interface AuthService {
    AuthResponse register (UserRegister userRegister);
    AuthResponse login(UserLogin userLogin);
}

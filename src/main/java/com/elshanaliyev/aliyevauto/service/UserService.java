package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.model.response.ServiceCarsResponse;
import com.elshanaliyev.aliyevauto.model.response.UserResponse;

import java.util.List;

public interface UserService {
    public UserResponse getUserById(Long id);
    public UserResponse getUserByEmail(String email);
    public void deleteUserById(Long id);
    public UserResponse getUserByUsername(String name);
    public boolean updateUsername(Long id);
    public boolean updateMail(Long id);
    public boolean updateNumber(String number);
}

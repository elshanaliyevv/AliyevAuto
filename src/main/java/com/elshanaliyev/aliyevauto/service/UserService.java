package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.model.response.UserResponse;

public interface UserService {
    public UserResponse getUserById(Long id);
    public UserResponse getUserByEmail(String email);
    public void deleteUserById(Long id);
    public UserResponse getUserByUsername(String name);
    public void updateMyUsername(String username);
    public void updateMyMail(String mail);
    public void updateMyNumber(String number);
}

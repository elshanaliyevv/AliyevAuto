package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.Exceptions.UserCantDeleted;
import com.elshanaliyev.aliyevauto.Exceptions.UserNotFoundException;
import com.elshanaliyev.aliyevauto.Exceptions.WrongNumberForm;
import com.elshanaliyev.aliyevauto.mapper.EntityMapper;
import com.elshanaliyev.aliyevauto.model.entity.User;
import com.elshanaliyev.aliyevauto.model.response.UserResponse;
import com.elshanaliyev.aliyevauto.repository.UserRepo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserServiceImpl implements UserService {

    UserRepo userRepo;
    EntityMapper entityMapper;

    @Override
    public UserResponse getUserById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        User user=userOptional.get();
        return entityMapper.userToResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        User user=userOptional.get();
        return entityMapper.userToResponse(user);
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        userRepo.deleteUserById(id);
        Optional<User> userOptional1 = userRepo.findById(id);
        if (userOptional1.isPresent()){
            throw new UserCantDeleted("User cant be deleted");
        }

    }

    @Override
    public UserResponse getUserByUsername(String name) {
        Optional<User> userOptional = userRepo.findByUsername(name);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("Username does not exist");
        }
        return entityMapper.userToResponse(userOptional.get());

    }

    @Override
    public void updateMyUsername(String username) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepo.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setUsername(username);
        userRepo.save(user);
    }

    @Override
    public void updateMyNumber(String number) {
        String regex = "^(?:\\+994|0)(?:10|50|51|55|60|70|77|99)\\d{7}$";
        if (!number.matches(regex)){
            throw new WrongNumberForm("Please enter avaible number");
        }
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepo.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setNumber(number);
        userRepo.save(user);
    }

    @Override
    public void updateMyMail(String email) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepo.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(email);
        userRepo.save(user);
    }


    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}

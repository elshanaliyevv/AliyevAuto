package com.elshanaliyev.aliyevauto.controller;


import com.elshanaliyev.aliyevauto.Exceptions.UserCantDeleted;
import com.elshanaliyev.aliyevauto.Exceptions.UserNotFoundException;
import com.elshanaliyev.aliyevauto.Exceptions.WrongNumberForm;
import com.elshanaliyev.aliyevauto.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getuserbyid(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Istiadeci tapilmadi");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getuserbyEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok().body(userService.getUserByEmail(email));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Istifadeci tapilmadi");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok().body("Success");

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Istifadeci tapilmadi");
        }catch (UserCantDeleted e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User cant be deleted");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getuserbyName(@PathVariable String name) {
        try {
            return ResponseEntity.ok().body(userService.getUserByUsername(name));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USer not found");
        }
    }
    @PostMapping("/updateusername")
    public ResponseEntity<?> updateUsername(@RequestParam String username) {
        try {
            userService.updateMyUsername(username);
            return ResponseEntity.ok("Username changed");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USer not found");
        }
    }

    @PostMapping("/updatemail")
    public ResponseEntity<?> updateMail(@RequestParam String mail) {
        try {
            userService.updateMyMail(mail);
            return ResponseEntity.ok("Mail changed");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USer not found");
        }
    }

    @PostMapping("/updatenumber")
    public ResponseEntity<?> updateNumber(@RequestParam String number) {
        try {
            userService.updateMyNumber(number);
            return ResponseEntity.ok("Number changed");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USer not found");
        }catch (WrongNumberForm e){
            return ResponseEntity.status(400).body("Please enter right number ");
        }
    }


}

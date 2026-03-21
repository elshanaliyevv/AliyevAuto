package com.elshanaliyev.aliyevauto.controller;

import com.elshanaliyev.aliyevauto.Exceptions.ColorAlreadyExistException;
import com.elshanaliyev.aliyevauto.Exceptions.ColorNotFoundException;
import com.elshanaliyev.aliyevauto.model.request.UpdateColorRequest;
import com.elshanaliyev.aliyevauto.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/color")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(colorService.findById(id));
        }catch (ColorNotFoundException e){
            return ResponseEntity.status(404).body("Not Found");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.ok(colorService.getAll());
        }catch (ColorNotFoundException e){
            return ResponseEntity.status(404).body("Color not found");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addColor(@RequestHeader String colorCode) {
        try {
            colorService.addColor(colorCode);
            return ResponseEntity.ok("Success");
        } catch (ColorAlreadyExistException e) {
            return ResponseEntity.status(400).body("Color is already exist");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/colorcode/{colorCode}")
    public ResponseEntity<?> deleteColorByColorCode(@PathVariable String colorCode){
        try {
            colorService.deleteColorByColorCode(colorCode);
            return ResponseEntity.ok("Successfull");
        } catch (ColorNotFoundException e) {
            return ResponseEntity.status(400).body("Color is not found");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<?> deleteColorById(@PathVariable Long id){
        try {
            colorService.deleteColorById(id);
            return ResponseEntity.ok("Successfull");
        } catch (ColorNotFoundException e) {
            return ResponseEntity.status(400).body("Color is not found");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update")
    public ResponseEntity<?> updateColor(@RequestBody UpdateColorRequest updateColorRequest){
        try{
            return ResponseEntity.ok(colorService.updateColor(updateColorRequest));
        } catch (ColorNotFoundException e) {
            return ResponseEntity.status(404).body("not found");
        }catch (ColorAlreadyExistException r){
            return ResponseEntity.status(400).body("Already exist");
        }
    }
}

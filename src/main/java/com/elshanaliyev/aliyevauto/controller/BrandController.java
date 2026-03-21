package com.elshanaliyev.aliyevauto.controller;


import com.elshanaliyev.aliyevauto.Exceptions.BrandAlreadyExist;
import com.elshanaliyev.aliyevauto.Exceptions.BrandNotFoundException;
import com.elshanaliyev.aliyevauto.model.request.UpdateBrandRequest;
import com.elshanaliyev.aliyevauto.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addBrand(@RequestHeader String brand){
        try{
            brandService.addModel(brand);
            return ResponseEntity.ok("Brand added");
        }catch (BrandAlreadyExist e){
            return ResponseEntity.status(400).body("already exist");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.ok(brandService.getAll());
        }catch (BrandNotFoundException e){
            return ResponseEntity.status(404).body("Brand not found");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(brandService.findById(id));
        }catch (BrandNotFoundException e){
            return ResponseEntity.status(404).body("Not Found");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id){
        try {
            if (brandService.deleteModel(id)){
                return ResponseEntity.ok("Succesfull progress");
            }
            return ResponseEntity.status(409).body("Error occured");
        }catch (BrandNotFoundException e){
            return ResponseEntity.status(404).body("Brand not found");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update")
    public ResponseEntity<?> updateBrand(@RequestBody UpdateBrandRequest updateBrandRequest){
        try {
                return ResponseEntity.ok(brandService.updateModel(updateBrandRequest));
        }catch (BrandNotFoundException e){
            return ResponseEntity.status(404).body("Brand not found");
        }catch (BrandAlreadyExist e){
            return ResponseEntity.status(400).body("Already exist");
        }
    }


}

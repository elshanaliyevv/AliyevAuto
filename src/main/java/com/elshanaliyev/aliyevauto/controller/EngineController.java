package com.elshanaliyev.aliyevauto.controller;

import com.elshanaliyev.aliyevauto.Exceptions.EngineAlreadyExistException;
import com.elshanaliyev.aliyevauto.Exceptions.EngineNotFoundException;
import com.elshanaliyev.aliyevauto.model.request.UpdateEngineRequest;
import com.elshanaliyev.aliyevauto.service.EngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("engine")
public class EngineController {
    private final EngineService engineService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(engineService.findById(id));
        }catch (EngineNotFoundException e){
            return ResponseEntity.status(404).body("Engine not found");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.ok(engineService.getAll());
        }catch (EngineNotFoundException e){
            return ResponseEntity.status(404).body("Engine not found");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addEngine(@RequestHeader String engineCode) {
        try{
            engineService.addEngine(engineCode);
            return ResponseEntity.ok("Engine added");
        }catch (EngineAlreadyExistException e){
            return ResponseEntity.status(400).body("Already exist");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/enginecode/{engineCode}")
    public ResponseEntity<?> deleteEngineByEngineCode(@PathVariable String engineCode){
        try {
            engineService.deleteEngineByEngineCode(engineCode);
            return ResponseEntity.ok("Successfull deleted");
        }catch (EngineNotFoundException e){
            return ResponseEntity.status(404).body("Engine not found");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<?> deleteEngineById(@PathVariable Long id){
        try {
            engineService.deleteEngineById(id);
            return ResponseEntity.ok("Succesfully deleted");
        }catch (EngineNotFoundException e){
            return ResponseEntity.status(404).body("Engine not found");
        }

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update")
    public ResponseEntity<?> updateEngine(@RequestBody UpdateEngineRequest updateEngineRequest){
        try {
            return ResponseEntity.ok(engineService.updateEngine(updateEngineRequest));
        }catch (EngineNotFoundException e){
            return ResponseEntity.status(404).body("Engine not found");
        }catch (EngineAlreadyExistException e){
            return ResponseEntity.status(400).body("Engine already exist");
        }
    }
}

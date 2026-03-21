package com.elshanaliyev.aliyevauto.service;


import com.elshanaliyev.aliyevauto.Exceptions.BrandNotFoundException;
import com.elshanaliyev.aliyevauto.Exceptions.ColorAlreadyExistException;
import com.elshanaliyev.aliyevauto.Exceptions.ColorNotFoundException;
import com.elshanaliyev.aliyevauto.Exceptions.EngineNotFoundException;
import com.elshanaliyev.aliyevauto.model.entity.Brand;
import com.elshanaliyev.aliyevauto.model.entity.Color;
import com.elshanaliyev.aliyevauto.model.entity.Engine;
import com.elshanaliyev.aliyevauto.model.request.UpdateColorRequest;
import com.elshanaliyev.aliyevauto.repository.ColorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ColorService {

    private final ColorRepo colorRepo;

    public Color findById(Long id){
        Optional<Color> colorOptional = colorRepo.findById(id);
        if (colorOptional.isEmpty()){
            throw new ColorNotFoundException("Brand not found");
        }
        return colorOptional.get();
    }

    public void addColor(String colorCode) {
        Optional<Color> colorOptional = colorRepo.findByColorCode(colorCode);
        if (colorOptional.isPresent()) {
            throw new ColorAlreadyExistException("Color is already exist");
        }
        Color color = new Color();
        color.setColorCode(colorCode);
        colorRepo.save(color);
    }

    public void deleteColorById(Long id) {
        Optional<Color> colorOptional = colorRepo.findById(id);
        if (colorOptional.isEmpty()) {
            throw new ColorNotFoundException("Color not found");
        }
        colorRepo.deleteColorById(id);
    }

    public void deleteColorByColorCode(String colorCode) {
        Optional<Color> colorOptional = colorRepo.findByColorCode(colorCode);
        if (colorOptional.isEmpty()) {
            throw new ColorNotFoundException("Color not found");
        }
        colorRepo.deleteByColorCode(colorCode);
    }

    public Color updateColor(UpdateColorRequest updateColorRequest) {
        Optional<Color> colorOptional = colorRepo.findById(updateColorRequest.getId());
        if (colorOptional.isEmpty()) {
            throw new ColorNotFoundException("Color not found");
        }
        Optional<Color> colorOptional1 = colorRepo.findByColorCode(updateColorRequest.getColorCode());
        if (colorOptional1.isPresent()){
            throw new ColorAlreadyExistException("Already exist");
        }
        Color color = colorOptional.get();
        color.setColorCode(updateColorRequest.getColorCode());
        colorRepo.save(color);
        return color;

    }
    public List<Color> getAll(){
        List<Color> colorList = colorRepo.findAll();
        if (colorList.isEmpty()){
            throw new ColorNotFoundException("Color not found");
        }
        return colorList;
    }
}

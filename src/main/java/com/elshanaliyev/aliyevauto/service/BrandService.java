package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.Exceptions.BrandAlreadyExist;
import com.elshanaliyev.aliyevauto.Exceptions.BrandNotFoundException;
import com.elshanaliyev.aliyevauto.Exceptions.EngineNotFoundException;
import com.elshanaliyev.aliyevauto.model.entity.Brand;
import com.elshanaliyev.aliyevauto.model.entity.Engine;
import com.elshanaliyev.aliyevauto.model.request.UpdateBrandRequest;
import com.elshanaliyev.aliyevauto.repository.BrandRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepo brandRepo;

    public Brand findById(Long id){
        Optional<Brand> brandOptional = brandRepo.findById(id);
        if (brandOptional.isEmpty()){
            throw new BrandNotFoundException("Brand not found");
        }
        return brandOptional.get();
    }

    public void addModel(String brandName) {
        Optional<Brand> optionalBrand = brandRepo.findByName(brandName);
        if (optionalBrand.isPresent()) {
            throw new BrandAlreadyExist("Brand already exist");
        }
        Brand brand = new Brand();
        brand.setName(brandName);
        brandRepo.save(brand);
    }

    public Boolean deleteModel(Long id){
        Optional<Brand> optionalBrand = brandRepo.findById(id);
        if (optionalBrand.isEmpty()) {
            throw new BrandNotFoundException("Brand not found");
        }
        Optional<Brand>deletedBrand = brandRepo.deleteBrandById(id);
        if (deletedBrand.isPresent()){
            return true;
        }return false;
    }
    public Brand updateModel(UpdateBrandRequest updateBrandRequest){
        Optional<Brand> optionalBrand = brandRepo.findById(updateBrandRequest.getId());
        if (optionalBrand.isEmpty()){
            throw new BrandNotFoundException("Not Found");
        }
        Optional<Brand> optionalBrand1 = brandRepo.findByName(updateBrandRequest.getBrand());
        if (optionalBrand1.isPresent()){
            throw new BrandAlreadyExist("Already exist");
        }
        Brand brand=optionalBrand.get();
        brand.setName(updateBrandRequest.getBrand());
        brandRepo.save(brand);
        return brand;
    }
    public List<Brand> getAll(){
        List<Brand> brandList = brandRepo.findAll();
        if (brandList.isEmpty()){
            throw new BrandNotFoundException("Brand not found");
        }
        return brandList;
    }
}

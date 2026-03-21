package com.elshanaliyev.aliyevauto.service;

import com.elshanaliyev.aliyevauto.Exceptions.EngineAlreadyExistException;
import com.elshanaliyev.aliyevauto.Exceptions.EngineNotFoundException;
import com.elshanaliyev.aliyevauto.model.entity.Engine;
import com.elshanaliyev.aliyevauto.model.request.UpdateEngineRequest;
import com.elshanaliyev.aliyevauto.repository.EngineRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EngineService {

    private final EngineRepo engineRepo;

    public Engine findById(Long id){
        Optional<Engine> engineOptional = engineRepo.findById(id);
        if (engineOptional.isEmpty()){
            throw new EngineNotFoundException("engine not found");
        }
        return engineOptional.get();
    }

    public void addEngine(String engineCode) {
        Optional<Engine> colorOptional = engineRepo.findByEngineCode(engineCode);
        if (colorOptional.isPresent()) {
            throw new EngineAlreadyExistException("Engine already exist");
        }
        Engine engine = new Engine();
        engine.setEngineCode(engineCode);
        engineRepo.save(engine);
    }

    public void deleteEngineById(Long id) {
        Optional<Engine> engineOptional = engineRepo.findById(id);
        if (engineOptional.isEmpty()) {
            throw new EngineNotFoundException("Engine not found");
        }
        engineRepo.deleteEngineById(id);
    }

    public void deleteEngineByEngineCode(String engineCode) {
        Optional<Engine> engineOptional = engineRepo.findByEngineCode(engineCode);
        if (engineOptional.isEmpty()) {
            throw new EngineNotFoundException("Engine not found");
        }
        engineRepo.deleteByEngineCode(engineCode);
    }

    public Engine updateEngine(UpdateEngineRequest updateEngineRequest) {
        Optional<Engine>  engineOptional = engineRepo.findById(updateEngineRequest.getId());
        if (engineOptional.isEmpty()) {
            throw new EngineNotFoundException("Engine not found");
        }
        Optional<Engine> engineOptional1 = engineRepo.findByEngineCode(updateEngineRequest.getEngineCode());
        if (engineOptional1.isPresent()){
            throw new EngineAlreadyExistException("Engine already exist");
        }
        Engine engine = engineOptional.get();
        engine.setEngineCode(updateEngineRequest.getEngineCode());
        engineRepo.save(engine);
        return engine;

    }

    public List<Engine> getAll(){
        List<Engine> engineList = engineRepo.findAll();
        if (engineList.isEmpty()){
            throw new EngineNotFoundException("Engine not found");
        }
        return engineList;
    }
}

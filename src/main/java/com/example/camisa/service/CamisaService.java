package com.example.camisa.service;

import com.example.camisa.domain.Camisa;
import com.example.camisa.repository.CamisaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CamisaService {

    private final CamisaRepository repository;

    public CamisaService(CamisaRepository repository) {
        this.repository = repository;
    }

    public Camisa create(Camisa c){
        return repository.save(c);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Camisa update(Camisa c){
        return repository.saveAndFlush(c);
    }

    public Camisa findById(Long id){
        Optional<Camisa> ComputadorOptional = repository.findById(id);
        return ComputadorOptional.orElse(null);
    }

    public List<Camisa> findAll(){
        return repository.findAll();
    }
}

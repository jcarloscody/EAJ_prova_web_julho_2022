package com.example.demo.services;

import org.springframework.stereotype.Service;
import com.example.demo.domain.Camisa;
import com.example.demo.repository.CamisaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CamisaService {

    private final CamisaRepository repository;

    public CamisaService(CamisaRepository repository) {
        this.repository = repository;
    }

    public Camisa create(Camisa u) {
        return repository.save(u);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Camisa update(Camisa u) {
        return repository.saveAndFlush(u);
    }

    public Camisa findById(Long id) {
        Optional<Camisa> CamisaOptional = repository.findById(id);
        return CamisaOptional.orElse(null);
    }

    public List<Camisa> findAll() {
        return repository.findAll();
    }
}

package com.example.camisa.repository;

import com.example.camisa.domain.Camisa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CamisaRepository extends JpaRepository<Camisa, Long> {
}

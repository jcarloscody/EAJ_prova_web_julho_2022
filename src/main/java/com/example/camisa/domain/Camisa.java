package com.example.camisa.domain;

import com.example.camisa.errorhandling.ApiMenssages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Camisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = ApiMenssages.ERRO_VAZIO)
    String modelo;
    String marca;
    String preco;
    String imagem;
    String descricao;

    Boolean deletd;
}

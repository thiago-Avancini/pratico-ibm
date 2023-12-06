package com.teste.pratico.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class ContaRequest {

    @NotBlank(message = "O nome do usuário é obrigatório")
    private String nome;

    @NotBlank(message = "O sobrenome do usuário é obrigatório")
    private String sobrenome;

    @NotBlank(message = "O CPF do usuário é obrigatório")
    @CPF(groups = CPF.class)
    private String cpf;
}

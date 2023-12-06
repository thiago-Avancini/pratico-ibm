package com.teste.pratico.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Accessors(chain = true)
public class UsuarioRequest {

  @NotBlank(message = "O nome do usuário é obrigatório")
  private String nome;

  @NotBlank(message = "O sobrenome do usuário é obrigatório")
  private String sobrenome;

  @NotBlank(message = "O CPF do usuário é obrigatório")
  @CPF(groups = CPF.class)
  @CNPJ(groups = CNPJ.class)
  private String cpf;

  @NotBlank(message = "O Tipo de usuário deve ser informado: GERENTE ou CLIENTE)")
  private String tipoUsuario;

}

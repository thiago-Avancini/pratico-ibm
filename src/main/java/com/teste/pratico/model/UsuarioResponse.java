package com.teste.pratico.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponse {

  private String nomeCompleto;

  private String cpf;

  private String senhaTemporaria;

}

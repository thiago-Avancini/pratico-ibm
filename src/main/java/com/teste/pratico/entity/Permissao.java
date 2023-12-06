package com.teste.pratico.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissao {
  GERENTE_READ("gerente:read"),
  GERENTE_WRITE("gerente:write"),
  GERENTE_UPDATE("gerente:update"),
  GERENTE_DELETE("gerente:delete");

  @Getter
  private final String permissao;
}

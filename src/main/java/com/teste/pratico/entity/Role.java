package com.teste.pratico.entity;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static com.teste.pratico.entity.Permissao.GERENTE_DELETE;
import static com.teste.pratico.entity.Permissao.GERENTE_READ;
import static com.teste.pratico.entity.Permissao.GERENTE_UPDATE;
import static com.teste.pratico.entity.Permissao.GERENTE_WRITE;

@RequiredArgsConstructor
public enum Role {

  ADMIN(Collections.emptySet()),
  CLIENTE(Collections.emptySet()),
  GERENTE(Set.of(GERENTE_READ,GERENTE_WRITE, GERENTE_UPDATE, GERENTE_DELETE));

  @Getter
  private final Set<Permissao> permissoes;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissoes()
        .stream()
        .map(permissao -> new SimpleGrantedAuthority(permissao.getPermissao()))
        .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + name()));
    return authorities;
  }
}

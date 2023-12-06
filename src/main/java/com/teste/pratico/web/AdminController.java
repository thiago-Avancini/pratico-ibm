package com.teste.pratico.web;

import com.google.common.base.Preconditions;
import com.teste.pratico.enums.Role;
import com.teste.pratico.model.UsuarioRequest;
import com.teste.pratico.model.UsuarioResponse;
import com.teste.pratico.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;
  @PostMapping("/novo-usuario")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<UsuarioResponse> registrarNovoUsuario(@RequestBody UsuarioRequest request) {
    Preconditions.checkArgument(request.getTipoUsuario().equals(Role.GERENTE.name()), "Administradores podem apenas criar gerentes");
    return ResponseEntity.ok(adminService.registrarNovoUsuario(request));
  }
}

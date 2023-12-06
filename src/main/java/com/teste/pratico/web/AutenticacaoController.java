package com.teste.pratico.web;

import com.teste.pratico.authentication.LoginRequest;
import com.teste.pratico.authentication.LoginResponse;
import com.teste.pratico.service.AutenticacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

  private final AutenticacaoService autenticacaoService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(autenticacaoService.login(loginRequest));
  }

}
